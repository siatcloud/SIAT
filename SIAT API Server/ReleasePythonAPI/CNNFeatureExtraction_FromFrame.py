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


data_url_1 = "1.jpg"
url_od = ' http://163.180.116.234:5000/CNNFeatureExtractionFrame/'
headers = {"content-type": "image/jpeg", "Accept-Charset": "UTF-8"}

img = cv2.imread(data_url_1)
_, img_encoded = cv2.imencode('.jpg', img)
# send http request with image and receive response
r_post = requests.post(url_od, data=img_encoded.tostring(), headers=headers)
data_for_post = r_post.json()
print(data_for_post)