# This code detect the object from each frame in SIAT cloud and the video data is read from your computer 
# The Processing is done on SIAT cloud

import requests
import cv2
import numpy as np
import base64
import json
import pickle
import time
import os


def json2im(jstr):
    """Convert a JSON string back to a Numpy array"""
    load = json.loads(jstr)
    imdata = base64.b64decode(load['image'])
    im = pickle.loads(imdata)
    return im


def im2json(im):
    """Convert a Numpy array to JSON string"""
    imdata = pickle.dumps(im)
    jstr = json.dumps({"image": base64.b64encode(imdata).decode('ascii')})
    return jstr


def imShow(frame):
    cv2.imshow("Frame", frame)
    key = cv2.waitKey(1) & 0xFF
    

#give the video data path from your computer

data_url_1 = "ObjectDetection_data/cars.mp4"
url_od = 'http://163.180.116.234:5000/ODFrameJson/'
headers = {"content-type": "image/jpeg", "Accept-Charset": "UTF-8"}
vs = cv2.VideoCapture(data_url_1)
count = 0
while True:
    (grabbed, frame) = vs.read()
    if not grabbed:
        break
    if (count % 2) == 0:
        _, img_encoded = cv2.imencode('.jpg', frame)
        # send http request with image and receive response
        r_post = requests.post(url_od, data=img_encoded.tostring(), headers=headers)
        data_for_post = r_post.json()
        # print(type(data_for_post))
        data_get = json2im(data_for_post)
        imShow(data_get)

#         # print(data_for_post)
    count = count + 1
    
