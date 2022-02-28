import numpy as np
import cv2
import imutils
import json
import os
from tensorflow.keras.applications.resnet50 import ResNet50
from tensorflow.keras.preprocessing import image
from tensorflow.keras.applications.resnet50 import preprocess_input, decode_predictions


def FeatureExtractionCNNFrame(frame):
    model = ResNet50(weights='imagenet')
    img_path = frame
    newsize = (224, 224)
    img = cv2.resize(img_path, newsize, interpolation=cv2.INTER_AREA)

    # img = image.load_img(img_path, target_size=(224, 224))
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)
    features = model.predict(x)
    lists = features[0, :].tolist()
    json_str = json.dumps(lists)
    # print(json_str)
    return json_str


if __name__ == '__main__':
    Read_filepath = "data/test/"
    Write_filepath = "feature_test.csv"
    FeatureExtractionCNNFrame(Read_filepath)
