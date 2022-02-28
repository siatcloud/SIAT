# -*- coding: utf-8 -*-
"""
Created on Tue Aug 25 18:53:36 2020

@author: user
"""


import numpy as np

import imutils
import os
import json
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

from keras import backend as K

def TrainFeature_CNN1D(TrainFeature1,LabelData1,NumTrainData,numFeature, classs, model_path, epochs, batch_size, FC_nodes, validData, validLabel, Vdata):
    K.clear_session()
    root_url = "D:/siat/"
    NumTrainData = int(NumTrainData)
    numFeature = int(numFeature)
    classes = int(classs)
    epochs = int(epochs)
    batch_size = int(batch_size)
    FC_nodes = int(FC_nodes)
    Vdata = int(Vdata)
    data_train = pd.read_csv(root_url + TrainFeature1, header=None)
    X_train = np.array(data_train)
    X_train = X_train.reshape((X_train.shape[0], X_train.shape[1], 1))
    
    label_train = pd.read_csv(root_url + LabelData1,header=None)
    label_train = np.array(label_train)
    label_train = label_train.reshape(NumTrainData)
    label_train = to_categorical(label_train)
    
    Vdata_train = pd.read_csv(root_url + validData, header=None)
    V_train = np.array(Vdata_train)
    V_train = V_train.reshape((V_train.shape[0], V_train.shape[1], 1))
    
    Vlabel_train = pd.read_csv(root_url + validLabel, header=None)
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
    valaccuracy = history.history['val_acc'][epochs - 1]
    model.save(root_url + model_path)
    lists = valaccuracy.tolist()
    json_str = json.dumps(lists)
    output2 = "\nValidation Accuracy :" + json_str
    print(output2)

    return output2


def TestFeature_CNN1D(TestFeature,TestLabelData, numFeature,NumTestData, model_path):
    #f = open(Result, "w")
    root_url = "D:/siat/"
    numFeature = int(numFeature)
    NumTestData =int(NumTestData)
    data_test = pd.read_csv(root_url + TestFeature,header=None)
    X_test = np.array(data_test)



    X_test = X_test.reshape(X_test.shape[0], X_test.shape[1], 1)
    
    label_test = pd.read_csv(root_url + TestLabelData,header=None)
    label_test2 = np.array(label_test)
    #label_test2 = np.transpose(label_test2)
    label_test = np.array(label_test)
    
    

    label_test = label_test.reshape(NumTestData)
    label_test = to_categorical(label_test)
    
    model = keras.models.load_model(root_url + model_path)
    
    _, accuracy = model.evaluate(X_test, label_test, verbose=0)
    test_classes = model.predict_classes(X_test)
    #print(test_classes)
    #print(label_test2)
    
    accuracy1 = accuracy * 100;


    print("Accuracy = %s" % accuracy1 + "%")
    
    precision = precision_score(test_classes, label_test2,average='micro')*100
    print("Precision = %s" % precision + "%" )
    
    #recall = recall_score(test_classes, label_test2,average='micro')*100
    #print("Recall = %s" % recall + "%")
    
    #f1 = f1_score(test_classes, label_test2,average='micro')*100
    #print("F1 score = %s" %f1 + "%")
    
    error_rate = (1 - accuracy) * 100
    
    print("Error rate = %s" %error_rate + "%")

    lists = accuracy.tolist()
    json_str = json.dumps(lists)

    lists_cls = test_classes.tolist()
    json_cls_str = json.dumps(lists_cls)

    lists_cls2 = error_rate.tolist()
    json_cls_err = json.dumps(lists_cls2)

    lists_cls3 = precision.tolist()
    json_cls_precision = json.dumps(lists_cls3)

    output = "Accuracy:" + json_str + "\npredicted class:" + json_cls_str + "\nError rate: " + json_cls_err + "\nPrecision: " + json_cls_precision
    # print("**********************************")
    # print("**********************************")
    # print("**********************************")
    # print(output)
    # print("**********************************")
    # print("**********************************")
    # print("**********************************")

    return output
    

    
    
if __name__ == '__main__':
    TrainFeature = "traindata.csv"
    LabelData = "trainlabel.csv"
    TestFeature = "testdata.csv"
    TestLabelData = "testlabel.csv"
    model_path = "model/CNN1D"
    validData = "validationData.csv"
    validLabel = "validationLabel.csv"
    
    NumTrainData = 84
    NumTestData = 24
    numFeature = 18
    classes = 4
    epochs = 400
    Vdata = 12
    batch_size = 6
    FC_nodes = 200
    start = timeit.default_timer()
    TrainFeature_CNN1D(TrainFeature,LabelData,NumTrainData,numFeature, classes, model_path, epochs, batch_size, FC_nodes, validData, validLabel, Vdata)
    stop = timeit.default_timer()
    print('Training Time: ', stop - start)
    
    start2 = timeit.default_timer()
    TestFeature_CNN1D(TestFeature,TestLabelData, numFeature,NumTestData, model_path)
    stop2 = timeit.default_timer()
    print('Testing Time: ', stop2 - start2)