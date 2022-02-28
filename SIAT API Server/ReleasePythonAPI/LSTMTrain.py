#Train LSTM
import requests


#LSTM API
url_lstm = 'http://163.180.116.234:5000/TrainingLSTM/'
# # headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}
#root path from UI
root_url = "khu/azher006/others/"

#TrainFeature file 
TrainFeature = root_url + "trainFeature.csv"

#TrainFeature label file 
LabelData = root_url + "TrainedDataLabel.csv"

#Save Model path with Model name
model_path = root_url + "LSTMModel"

#Number of trained video
trainVideo = 40

#Number of frames from ehere feature is extracted
timeStamp = 90

#Number of Features
numFeature = 1000

#Nunber of classes
classes = 2

payload_fe = {'TrainFeature': TrainFeature, 'LabelData': LabelData, 'no_trainVideo': trainVideo,
              'no_frame': timeStamp, 'no_feature': numFeature, 'classes': classes, 'model_path': model_path}

r_post = requests.post(url_lstm, data=payload_fe)

data_for_post = r_post.json()
print(type(data_for_post))