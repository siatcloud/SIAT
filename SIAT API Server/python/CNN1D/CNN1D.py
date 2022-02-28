# -*- coding: utf-8 -*-
"""
Created on Tue Aug 25 18:53:36 2020

@author: user
"""


import numpy as np

import imutils
import os

from csv import reader
import pandas as pd

from keras.preprocessing.text import one_hot

from keras.layers.core import Activation, Dropout, Dense

from keras.layers import GlobalMaxPooling1D
from keras.models import Model
from keras.models import Sequential
from keras.layers.convolutional import Conv1D
from keras.layers.convolutional import MaxPooling1D
from keras.layers import Dense, Activation, Flatten, Convolution1D, Dropout
from keras.preprocessing.text import Tokenizer
from keras.layers import Input
from keras.layers.merge import Concatenate
from keras.layers import Bidirectional
from keras.utils import to_categorical
from sklearn.metrics import precision_score, recall_score, f1_score
from tensorflow import keras
import timeit

def TrainFeature_CNN(TrainFeature,LabelData,NumTrainData,numFeature, classes, model_path, epochs, batch_size, FC_nodes, validData, validLabel, Vdata):
    data_train = pd.read_csv(TrainFeature,header=None)
    X_train = np.array(data_train)
    X_train = X_train.reshape((X_train.shape[0], X_train.shape[1], 1))
    
    label_train = pd.read_csv(LabelData,header=None)
    label_train = np.array(label_train)
    label_train = label_train.reshape(NumTrainData)
    label_train = to_categorical(label_train)
    
    Vdata_train = pd.read_csv(validData,header=None)
    V_train = np.array(Vdata_train)
    V_train = V_train.reshape((V_train.shape[0], V_train.shape[1], 1))
    
    Vlabel_train = pd.read_csv(validLabel,header=None)
    Vlabel_train = np.array(Vlabel_train)
    Vlabel_train = Vlabel_train.reshape(Vdata)
    Vlabel_train = to_categorical(Vlabel_train)
    
    
    
    verbose, epochs, batch_size = 1, epochs, batch_size
    FC_nodes = FC_nodes
    n_timesteps =  X_train.shape[1]
    n_features = numFeature
    n_outputs = classes
    model = Sequential()
    model.add(Conv1D(filters=64, kernel_size=2, activation='relu', input_shape=(n_features,1)))
    model.add(MaxPooling1D(pool_size=2))
    model.add(Conv1D(filters=64, kernel_size=2, activation='relu'))
    model.add(Conv1D(filters=128, kernel_size=2, activation='relu'))
    model.add(Conv1D(filters=128, kernel_size=2, activation='relu'))
    model.add(Conv1D(filters=256, kernel_size=2, activation='relu'))
    model.add(MaxPooling1D(pool_size=2))
    model.add(Flatten())
    model.add(Dense(FC_nodes, activation='relu'))
    model.add(Dense(n_outputs, activation='softmax'))
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    # fit network
    history = model.fit(X_train, label_train, validation_data=(V_train, Vlabel_train), epochs=epochs, batch_size=batch_size, verbose=verbose)
    
    print("Validation Accuracy = %s" % history.history['val_acc'][epochs-1])
    model.save(model_path+'CNNModel')
    
    
def TestFeature_CNN(TestFeature,TestLabelData, numFeature,NumTestData, Result, model_path):
    f = open(Result, "w")
    data_test = pd.read_csv(TestFeature,header=None)
    X_test = np.array(data_test)

    X_test = X_test.reshape(X_test.shape[0], X_test.shape[1], 1)
    
    label_test = pd.read_csv(TestLabelData,header=None)
    label_test2 = np.array(label_test)
    #label_test2 = np.transpose(label_test2)
    label_test = np.array(label_test)
    
    

    label_test = label_test.reshape(NumTestData)
    label_test = to_categorical(label_test)
    
    model = keras.models.load_model(model_path+'CNNModel')
    
    _, accuracy = model.evaluate(X_test, label_test, verbose=0)
    test_classes = model.predict_classes(X_test)
    #print(test_classes)
    #print(label_test2)
    
    accuracy1 = accuracy * 100;


    print("Accuracy = %s" % accuracy1 + "%")
    
    precision = precision_score(test_classes, label_test2,average='micro')*100
    print("Precision = %s" % precision + "%" )
    
    recall = recall_score(test_classes, label_test2,average='micro')*100
    print("Recall = %s" % recall + "%")
    
    f1 = f1_score(test_classes, label_test2,average='micro')*100
    print("F1 score = %s" %f1 + "%")
    
    error_rate = (1 - accuracy) * 100
    
    print("Error rate = %s" %error_rate + "%")
    
    
    
    f.write("Accuracy=%s" %accuracy)
    f.write("\n")
    for i in range(len(X_test)):
        print("Test_data_id=%s, Predicted=%s" % (i, test_classes[i]))
        f.write("Test_data_id=%s, Predicted=%s" % (i, test_classes[i]))
        f.flush()
        f.write("\n")
    
    f.close()
    
    
if __name__ == '__main__':
    TrainFeature = "SWUData/traindata.csv"
    LabelData = "SWUData/trainlabel.csv"
    TestFeature = "SWUData/testdata.csv"
    TestLabelData = "SWUData/testlabel.csv"
    Result = "result.csv"
    model_path = "model/"
    validData = "SWUData/validationData.csv"
    validLabel = "SWUData/validationLabel.csv"
    
    NumTrainData = 84
    NumTestData = 24
    numFeature = 18
    classes = 4
    epochs = 500
    Vdata = 12
    batch_size = 6
    FC_nodes = 200
    start = timeit.default_timer()
    TrainFeature_CNN(TrainFeature,LabelData,NumTrainData,numFeature, classes, model_path, epochs, batch_size, FC_nodes, validData, validLabel, Vdata)
    stop = timeit.default_timer()
    print('Training Time: ', stop - start)
    
    start2 = timeit.default_timer()
    TestFeature_CNN(TestFeature,TestLabelData, numFeature,NumTestData, Result, model_path)
    stop2 = timeit.default_timer()
    print('Testing Time: ', stop2 - start2)