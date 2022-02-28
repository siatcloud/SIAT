#This API extract the CNN feature and takes video dataset as input
#Capture the features from trained data to train LSTM

import requests
import cv2
import numpy as np
import base64
import json
import pickle
import time
import os



url_od = 'http://163.180.116.234:5000/CNNFeatureExtractionVideo/'
# headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}

#data_path is video dataset containing the videos from which the feature will be extracted
#output_path is the feature dataset which will be generated

#ext is extension of video data

#no_frame is the number of frames from where feature will be extracted

payload_fe = {'data_path': 'khu/azher006/trainData/', 'output_path': 'khu/azher006/others/trainFeature.csv',
               'ext': 'avi', 'no_frame': 90}
r_post = requests.post(url_od, data=payload_fe)
data_for_post = r_post.json()
print(data_for_post)

