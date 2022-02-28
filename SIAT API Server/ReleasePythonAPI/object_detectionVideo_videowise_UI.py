# This code detect the object from each frame in SIAT cloud and the video data is read from SIAT cloud 
# The Processing is done on SIAT cloud


import requests
import cv2
import numpy as np
import base64
import json
import pickle
import time
import os

#Object Detection API
url_od = 'http://163.180.116.234:5000/ObjectDetection/'
 # headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}
 
#data_path is the path you upload the video data get this path from UI

#output_path is the path where the output video data is saved get this path from UI

payload_od = {'data_path': 'khu/azher006/obd/cars.mp4', 'output_path': 'output.mp4'}
r_post = requests.post(url_od, data=payload_od)
data_for_post = r_post.json()
print(data_for_post)