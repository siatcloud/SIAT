import numpy as np

import imutils
import os

from csv import reader
import pandas as pd
import json

from keras.preprocessing.text import one_hot
from keras.preprocessing.sequence import pad_sequences
from keras.models import Sequential
from keras.layers.core import Activation, Dropout, Dense
from keras.layers import Flatten, LSTM
from keras.layers import GlobalMaxPooling1D
from keras.models import Model
from keras.layers.embeddings import Embedding

from keras.preprocessing.text import Tokenizer
from keras.layers import Input
from keras.layers.merge import Concatenate
from keras.layers import Bidirectional
from keras.utils import to_categorical

from tensorflow import keras
from keras import backend as K

def TrainFeature_LSTM(TrainFeature,LabelData,noVideo,no_frame, no_feature, no_classes, model_path):
    #print(TrainFeature + "\n" + LabelData + "\n" + noVideo + "\n" + no_frame + "\n" + numFeature + "\n" + classes + "\n" + model_path)
    K.clear_session()
    root_url = "D:/siat/"
    trainVideo = int(noVideo)
    timeStamp = int(no_frame)
    numFeature = int(no_feature)
    classes = int(no_classes)
    data_train = pd.read_csv(root_url + TrainFeature, header=None)
    #print(data_train)
    X_train = np.array(data_train)
    X_train = X_train.reshape(trainVideo,timeStamp,numFeature)
    #print(X_train)
    # print(X_train.shape[0])
    
    label_train = pd.read_csv(root_url + LabelData,header=None)
    label_train = np.array(label_train)
    #label_train = np.transpose(label_train)
    label_train = label_train.reshape(trainVideo)
    label_train = to_categorical(label_train)
    #print(label_train)
    #print(label_train.shape[1])
    
    
    #print(label_test)
    #print(label_train)
    
    model = Sequential()
    model.add(LSTM(50, activation='relu', input_shape=(timeStamp, numFeature)))
    model.add(Dropout(0.2))
    model.add(Dense(100, activation='relu'))
    model.add(Dense(classes, activation='softmax'))
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    model.fit(X_train, label_train, epochs=50, verbose=1)
    model.save(root_url + model_path)
    
    
def TestFeature_LSTM(TestFeature,TestLabelData,no_frame, no_feature,no_video, model_path):
    timeStamp = int(no_frame)
    numFeature = int(no_feature)
    testVideo = int(no_video)
    root_url = "D:/siat/"
    # f = open(Result, "w")
    data_test = pd.read_csv(root_url + TestFeature,header=None)
    X_test = np.array(data_test)
    X_test = X_test.reshape(testVideo,timeStamp,numFeature)
    #print(X_test)
    
    label_test = pd.read_csv(root_url + TestLabelData,header=None)
    label_test = np.array(label_test)
    #label_train = np.transpose(label_train)
    label_test = label_test.reshape(testVideo)
    label_test = to_categorical(label_test)
    
    model = keras.models.load_model(root_url + model_path)
    
    _, accuracy = model.evaluate(X_test, label_test, verbose=1)
    test_classes = model.predict_classes(X_test)

    lists = accuracy.tolist()
    json_str = json.dumps(lists)

    lists_cls = test_classes.tolist()
    json_cls_str = json.dumps(lists_cls)
    output = "Accuracy:" + json_str + "\npredicted class:" + json_cls_str
    print(output)
    # f.write("Accuracy=%s" %accuracy)
    # f.write("\n")
    # for i in range(len(X_test)):
    #     print("Test_video_id=%s, Predicted=%s" % (i+1, test_classes[i]))
    #     f.write("Test_video_id=%s, Predicted=%s" % (i+1, test_classes[i]))
    #     f.flush()
    #     f.write("\n")
    #
    # f.close()
    return output
    

if __name__ == '__main__':
    TrainFeature = "feature_up.csv"
    LabelData = "label_up.csv"
    TestFeature = "feature_test_up.csv"
    TestLabelData = "Testlabel_up.csv"
    # Result = "result.csv"
    model_path = "model/LSTMModel"
    trainVideo = 40
    timeStamp = 90
    numFeature = 1000
    testVideo = 12
    classes = 2
    TrainFeature_LSTM(TrainFeature,LabelData,trainVideo,timeStamp, numFeature, classes, model_path)
    TestFeature_LSTM(TestFeature,TestLabelData,timeStamp, numFeature,testVideo, model_path)
