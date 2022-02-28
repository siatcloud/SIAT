from flask import Flask, request, Response
from flask_restful import reqparse, Resource, Api
from callFile import callable
from ObjectDetectionDeepLearnedSSD import objectDetectionSDD, generate
from ObjectDetection import objectDetectionFrame
from ObjectDetectionJson import objectDetectionFrameJson
from FeatureExtractionCNN.FeatureExtractionCNN import FeatureExtractionCNN_ResNet
from FeatureExtractionCNN.FeatureExtractionCNNFrame import FeatureExtractionCNNFrame
from ClassificationLSTM.ClassificationLSTM import TrainFeature_LSTM, TestFeature_LSTM
from ClassificationLSTM.CNN1D import TrainFeature_CNN1D, TestFeature_CNN1D
from FrameExtract import Video2Image
from mySql_connection import validate_user
from mySql_connection import getVideoList
from mySql_connection import get_cctv

import cv2
import numpy as np
from json import JSONEncoder

import base64
import json
import pickle
import numpy

app = Flask(__name__)
api = Api(app)

parser = reqparse.RequestParser()
parser.add_argument('data_path')
parser.add_argument('email')
parser.add_argument('username')
parser.add_argument('password')
parser.add_argument('institute')
parser.add_argument('dbname')
parser.add_argument('output_path')
# parser.add_argument('batch_size')
parser.add_argument('num')
parser.add_argument('ext')
parser.add_argument('no_frame')

parser.add_argument('TrainFeature')
parser.add_argument('LabelData')
parser.add_argument('TestFeature')
parser.add_argument('TestLabelData')
parser.add_argument('model_path')
parser.add_argument('no_trainVideo')
parser.add_argument('no_frame')
parser.add_argument('no_feature')
parser.add_argument('no_testVideo')
parser.add_argument('classes')
parser.add_argument('TrainFeature1')
parser.add_argument('LabelData1')
parser.add_argument('NumTrainData')
parser.add_argument('numFeature')
parser.add_argument('epochs')
parser.add_argument('classs')
parser.add_argument('batch_size')
parser.add_argument('FC_nodes')
parser.add_argument('validData')
parser.add_argument('validLabel')
parser.add_argument('Vdata')
parser.add_argument('numFeature')
parser.add_argument('NumTestData')






class HelloWorld(Resource):
    def get(self):
        return {'about': 'Welcome to SIAT'}

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

class NumpyArrayEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, numpy.ndarray):
            return obj.tolist()
        return JSONEncoder.default(self, obj)

class ObjectDetection(Resource):
    def get(self):
        filename = objectDetectionSDD()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        filename = objectDetectionSDD(args['data_path'], args['output_path'])
        encodedNumpyData = json.dumps(filename, cls=NumpyArrayEncoder)
        return {'Bounding Box of the object are': encodedNumpyData}


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
        frame = objectDetectionFrameJson(img)
        return frame


class FeatureExtraction(Resource):
    def get(self):
        filename = FeatureExtractionCNN_ResNet()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        print(args['data_path'] + args['output_path'])
        FeatureExtractionCNN_ResNet(args['data_path'], args['output_path'], args['ext'], args['no_frame'])
        return {'output': args['output_path']}


class FeatureExtractionFrame(Resource):
    def get(self):
        args = parser.parse_args()
        filename = FeatureExtractionCNNFrame(args['data_path'])
        return filename

    def post(self):
        r = request
        # convert string of image data to uint8
        nparr = np.fromstring(r.data, np.uint8)
        # decode image
        img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
        feature = FeatureExtractionCNNFrame(img)
        return feature


class LSTM_training(Resource):
    def get(self):
        filename = TrainFeature_LSTM()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        TrainFeature_LSTM(args['TrainFeature'], args['LabelData'], args['no_trainVideo'], args['no_frame'],
                          args['no_feature'], args['classes'], args['model_path'])
        return {'output': args['model_path']}


class LSTM_testing(Resource):
    def get(self):
        filename = TrainFeature_LSTM()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        output = TestFeature_LSTM(args['TestFeature'], args['TestLabelData'], args['no_frame'], args['no_feature'],
                         args['no_testVideo'], args['model_path'])
        return {'output': output}


class CNN1D_training(Resource):
    def get(self):
        filename = TrainFeature_CNN1D()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        output2 = TrainFeature_CNN1D(args['TrainFeature1'], args['LabelData1'], args['NumTrainData'], args['numFeature'], args['classs'], args['model_path'], args['epochs'], args['batch_size'], args['FC_nodes'], args['validData'], args['validLabel'], args['Vdata'])
        return {'output': args['model_path']}


class CNN1D_testing(Resource):
    def get(self):
        filename = TrainFeature_CNN1D()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        output = TestFeature_CNN1D(args['TestFeature'], args['TestLabelData'], args['numFeature'], args['NumTestData'], args['model_path'])
        return {'output': output}


class FrameExtraction(Resource):
    def get(self):
        filename = objectDetectionSDD()
        return {'output': filename}

    def post(self):
        args = parser.parse_args()
        filename = Video2Image(args['data_path'], args['output_path'], args['no_frame'])
        return {'output_url': filename}


class getUser(Resource):
    def get(self):
        args = parser.parse_args()
        user_info = validate_user(args['username'], args['password'])
        return {'output': user_info}

    def post(self):
        args = parser.parse_args()
        user_info = validate_user(args['username'], args['password'])
        return {'authentication': user_info}


class dbInfo(Resource):
    def get(self):
        args = parser.parse_args()
        user_info = validate_user(args['username'], args['password'])
        return {'output': user_info}

    def post(self):
        args = parser.parse_args()
        video_info = getVideoList(args['institute'], args['username'], args['dbname'])
        return {'videoList': video_info}


class cctvInfo(Resource):
    def get(self):
        args = parser.parse_args()
        user_info = validate_user(args['username'], args['password'])
        return {'output': user_info}

    def post(self):
        args = parser.parse_args()
        cctv_info = get_cctv(args['username'], args['institute'])
        return {'cctv IPs': cctv_info}


api.add_resource(HelloWorld, '/')
api.add_resource(MathOperation, '/multi/<int:num>')
api.add_resource(TestOperation, '/test/')
api.add_resource(ObjectDetection, '/ObjectDetection/')
api.add_resource(ObjectDetectionFrame, '/ObjectDetectionFrame/')
api.add_resource(ObjectDetectionFrameJson, '/ODFrameJson/')
api.add_resource(FeatureExtraction, '/CNNFeatureExtractionVideo/')
api.add_resource(FeatureExtractionFrame, '/CNNFeatureExtractionFrame/')
api.add_resource(LSTM_training, '/TrainingLSTM/')
api.add_resource(LSTM_testing, '/TestingLSTM/')
api.add_resource(CNN1D_training, '/TrainingCNN1D/')
api.add_resource(CNN1D_testing, '/TestingCNN1D/')
api.add_resource(FrameExtraction, '/Video2Image/')
api.add_resource(getUser, '/login/')
api.add_resource(dbInfo, '/dbinfo/')
api.add_resource(cctvInfo, '/cctvInfo/')


if __name__ == '__main__':
    app.run(debug=True, host='163.180.116.234', port=7777)