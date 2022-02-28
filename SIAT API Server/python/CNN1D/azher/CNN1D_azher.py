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

def TrainFeature_CNN(TrainFeature,LabelData,NumTrainData,numFeature, classes, model_path, epochs, batch_size, FC_nodes):
    data_train = pd.read_csv(TrainFeature,header=None)
    X_train = np.array(data_train)
    X_train = X_train.reshape((X_train.shape[0], X_train.shape[1], 1))
    
    label_train = pd.read_csv(LabelData,header=None)
    label_train = np.array(label_train)
    label_train = label_train.reshape(NumTrainData)
    label_train = to_categorical(label_train)
    
    
    
    
    verbose, epochs, batch_size = 1, epochs, batch_size
    FC_nodes = FC_nodes

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
    model.fit(X_train, label_train, epochs=epochs, batch_size=batch_size, verbose=verbose)
    
    #print("Validation Accuracy = %s" % history.history['val_accuracy'][epochs-1])
    model.save(model_path+'CNNModel')
    
    
def TestFeature_CNN(TestFeature, model_path):
    data_test = pd.read_csv(TestFeature,header=None)
    X_test = np.array(data_test)

    X_test = X_test.reshape(X_test.shape[0], X_test.shape[1], 1)
    

    
    

    
    model = keras.models.load_model(model_path+'CNNModel')

    model.trainable = False

    inputs = model.inputs

    x = model(inputs, training=False)



    outputs = keras.layers.Dense(200)(x)

    model2 = keras.Model(inputs, outputs)
    model2.summary()




    yhat = model2.predict(X_test)
    print(yhat)

    
    
if __name__ == '__main__':
    TrainFeature = "TrainData.csv"
    LabelData = "trainlabel1.csv"
    TestFeature = "TrainData.csv"
    #TestLabelData = "E:/Research_Paper/SIAT/PythonAPI/classification/ClassificationLSTM/CNN1D/SWUData/testlabel1.csv"
    #Result = "E:/Research_Paper/SIAT/PythonAPI/classification/ClassificationLSTM/CNN1D/result.csv"
    model_path = "model2/"
   
    NumTrainData = 320
    NumTestData = 80
    numFeature = 768
    classes = 2
    epochs = 20
    batch_size = 4
    FC_nodes = 200
    #TrainFeature_CNN(TrainFeature,LabelData,NumTrainData,numFeature, classes, model_path, epochs, batch_size, FC_nodes)
    
    #start2 = timeit.default_timer()
    TestFeature_CNN(TestFeature, model_path)
    #stop2 = timeit.default_timer()
    #print('Testing Time: ', stop2 - start2)