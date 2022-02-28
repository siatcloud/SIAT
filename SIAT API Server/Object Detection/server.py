from flask import Flask, request, Response
from flask_restful import reqparse, Resource, Api
from callFile import callable
from ObjectDetectionDeepLearnedSSD import objectDetectionSDD, generate
from ObjectDetection import objectDetectionFrame
from ObjectDetectionJson import objectDetectionFrameJson
import cv2
import numpy as np

import base64
import json
import pickle

app = Flask(__name__)
api = Api(app)

parser = reqparse.RequestParser()
parser.add_argument('data_path')
parser.add_argument('output_path')
# parser.add_argument('batch_size')
parser.add_argument('num')


class HelloWorld(Resource):
    def get(self):
        return {'about': 'hello world'}

    def post(self):
        json_data = request.get_json()
        args = parser.parse_args()
        return {'post data sent': args['data_path']}


class MathOperation(Resource):
    def get(self, num):
        data = callable(num)  # call to other python file
        return {'result': str(data)}

    def post(self, num):
        return {'data sent': str(num)}


class TestOperation(Resource):
    def get(self):
        args = parser.parse_args()
        return {'test result': args['num']}

    def post(self):
        args = parser.parse_args()
        return {'post data sent': args['data_path']}


class ObjectDetection(Resource):
    def get(self):
        filename = objectDetectionSDD()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        filename = objectDetectionSDD(args['data_path'], args['output_path'])
        return {'output': filename, 'input': args['data_path']}


class ObjectDetectionFrame(Resource):
    def get(self):
        args = parser.parse_args()
        filename = objectDetectionFrame(args['data_path'])
        return filename

    def post(self):
        filename = objectDetectionFrame()
        return Response(response=filename, status=200, mimetype="application/json")


def json2im(jstr):
    """Convert a JSON string back to a Numpy array"""
    load = json.loads(jstr)
    imdata = base64.b64decode(load['image'])
    im = pickle.loads(imdata)
    return im


class ObjectDetectionFrameJson(Resource):
    def get(self):
        args = parser.parse_args()
        filename = objectDetectionFrame(args['data_path'])
        return filename

    def post(self):
        r = request
        # convert string of image data to uint8
        nparr = np.fromstring(r.data, np.uint8)
        # decode image
        img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
        filename = objectDetectionFrameJson(img)
        return filename


api.add_resource(HelloWorld, '/')
api.add_resource(MathOperation, '/multi/<int:num>')
api.add_resource(TestOperation, '/test/')
api.add_resource(ObjectDetection, '/ObjectDetection/')
api.add_resource(ObjectDetectionFrame, '/ObjectDetectionFrame/')
api.add_resource(ObjectDetectionFrameJson, '/ODFrameJson/')


if __name__ == '__main__':
    app.run(debug=True, host='163.180.116.64')