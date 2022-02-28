from imutils.video import FPS
import numpy as np
import imutils
import cv2
import base64
import json
import pickle
from imutils.video import VideoStream
from flask import Response
from flask import Flask
from flask import render_template
import threading
from flask import Flask, request
from flask_restful import reqparse, Resource, Api

import argparse
import datetime
import time

outputFrame = None
lock = threading.Lock()

app = Flask(__name__)
api = Api(app)

parser = reqparse.RequestParser()
parser.add_argument('datapath')


def objectDetectionFrameJson(frame):
    CLASSES = ["background", "1", "bicycle", "2", "3",
               "4", "bus", "car", "5", "6", "7", "8",
               "9", "10", "motorbike", "person", "11", "12",
               "13", "train", "14"]
    # frame = cv2.imread(imageFile)
    # frame = cv2.imread("data/1.jpg")

    confidence2 = 0.2
    global vs, outputFrame, lock
    COLORS = np.random.uniform(0, 255, size=(len(CLASSES), 3))
    net = cv2.dnn.readNetFromCaffe("MobileNetSSD_deploy.prototxt", "MobileNetSSD_deploy.caffemodel")
    use_gpu = 1
    if use_gpu > 0:
        print("[INFO] setting preferable backend and target to CUDA...")
        net.setPreferableBackend(cv2.dnn.DNN_BACKEND_CUDA)
        net.setPreferableTarget(cv2.dnn.DNN_TARGET_CUDA)

    print("[INFO] accessing video stream...")
    frame = imutils.resize(frame, width=400)
    (h, w) = frame.shape[:2]
    blob = cv2.dnn.blobFromImage(frame, 0.007843, (300, 300), 127.5)
    net.setInput(blob)
    detections = net.forward()
    for i in np.arange(0, detections.shape[2]):
        confidence = detections[0, 0, i, 2]
        if confidence > confidence2:
            idx = int(detections[0, 0, i, 1])
            box = detections[0, 0, i, 3:7] * np.array([w, h, w, h])
            (startX, startY, endX, endY) = box.astype("int")
            # label = "{}: {:.2f}%".format(CLASSES[idx], confidence * 100)
            label = "{}".format(CLASSES[idx])
            cv2.rectangle(frame, (startX, startY), (endX, endY), COLORS[idx], 2)
            y = startY - 15 if startY - 15 > 15 else startY + 15
            cv2.putText(frame, label, (startX, y), cv2.FONT_HERSHEY_SIMPLEX, 0.5, COLORS[idx], 2)
    imdata = pickle.dumps(frame)
    jstr = json.dumps({"image": base64.b64encode(imdata).decode('ascii')})
    return jstr


def generate():
    # grab global references to the output frame and lock variables
    global outputFrame, lock

    # loop over frames from the output stream
    while True:
        # wait until the lock is acquired
        with lock:
            # check if the output frame is available, otherwise skip
            # the iteration of the loop
            if outputFrame is None:
                continue

            # encode the frame in JPEG format
            (flag, encodedImage) = cv2.imencode(".jpg", outputFrame)

            # ensure the frame was successfully encoded
            if not flag:
                continue

        # yield the output frame in the byte format
        yield (b'--frame\r\n' b'Content-Type: image/jpeg\r\n\r\n' +
               bytearray(encodedImage) + b'\r\n')
        return bytearray(encodedImage)


if __name__ == '__main__':
    ap = argparse.ArgumentParser()
    args = vars(ap.parse_args())
    t = threading.Thread(target=objectDetectionSDD())
    args = vars(ap.parse_args())
    t.daemon = True
    t.start()
    # app.run(debug=True, host='163.180.116.64', port='5000', threaded=True, use_reloader=False)

# # vs.stop()







