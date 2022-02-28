import requests
import cv2
import numpy as np
import base64
import json
import pickle
import time
import os

# url_od = 'http://163.180.116.64:5000/fileUpload/'
# # headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}
# payload_od = {'data_path': 'data/1.mp4', 'output_path': 'data/output.mp4'}
# r_post = requests.post(url_od, data=payload_od)
# data_for_post = r_post.json()
# print(data_for_post)


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
    # if key == ord("q"):
    #     break


# data_url = "D:\Data\Annotation\My Datasets\Frames\Car\97"
# for files in os.listdir(data_url):
#     current_image = os.path.join(data_url, files)
#     start = time.time()
#     url_od = 'http://163.180.116.64:5000/ObjectDetectionFrame/'
#     get_od = requests.get(url_od, data={'data_path': current_image})
#     end = time.time()
#     data_get = json2im(get_od.json())
#     imShow(data_get)
#     cv2.imwrite("1.jpg", data_get)
#
#
# start = time.time()
# url_od = 'http://163.180.116.64:5000/ObjectDetectionFrame/'
# get_od = requests.get(url_od, data={'data_path': "data/1.jpg"})
# end = time.time()
# print(end-start)
# data_get = json2im(get_od.json())
# imShow(data_get)
# cv2.imwrite("1.jpg", data_get)
# print(type(get_od.json()))
#
# data_url_1 = "D:/Data/Annotation/My Datasets/Frames/Car/97/1.jpg"
# url_od = 'http://163.180.116.64:5000/ODFrameJson/'
# headers = {"content-type": "image/jpeg", "Accept-Charset": "UTF-8"}
#
# img = cv2.imread(data_url_1)
# _, img_encoded = cv2.imencode('.jpg', img)
# # send http request with image and receive response
# r_post = requests.post(url_od, data=img_encoded.tostring(), headers=headers)
#
# data_for_post = r_post.json()
# print(type(data_for_post))
# data_get = json2im(data_for_post)
# imShow(data_get)
# cv2.imwrite("1.jpg", data_get)
# print(data_for_post)
#
#
# data_url_1 = "data/2.mp4"
# url_od = 'http://163.180.116.64:5000/ODFrameJson/'
# headers = {"content-type": "image/jpeg", "Accept-Charset": "UTF-8"}
# vs = cv2.VideoCapture(data_url_1)
# count = 0
# while True:
#     (grabbed, frame) = vs.read()
#     if not grabbed:
#         break
#     if (count % 2) == 0:
#         _, img_encoded = cv2.imencode('.jpg', frame)
#         # send http request with image and receive response
#         r_post = requests.post(url_od, data=img_encoded.tostring(), headers=headers)
#
#         data_for_post = r_post.json()
#         # print(type(data_for_post))
#         data_get = json2im(data_for_post)
#         imShow(data_get)
#         # cv2.imwrite("1.jpg", data_get)
#         # print(data_for_post)
#     count = count + 1


data_url_1 = "1.jpg"
url_od = ' http://163.180.116.234:5000/FeatureExtractionFrame/'
headers = {"content-type": "image/jpeg", "Accept-Charset": "UTF-8"}

img = cv2.imread(data_url_1)
_, img_encoded = cv2.imencode('.jpg', img)
# send http request with image and receive response
r_post = requests.post(url_od, data=img_encoded.tostring(), headers=headers)
data_for_post = r_post.json()
print(data_for_post)
