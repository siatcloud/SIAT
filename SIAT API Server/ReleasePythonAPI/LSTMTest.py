#Test LSTM
import requests


#LSTM test API
url_lstm_test = 'http://163.180.116.234:5000/TestingLSTM/'
# # headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}
#root path from UI
root_url = "khu/azher006/others/"

#Test Feature file 
TestFeature = root_url + "testFeature.csv"

#Test Feature label file 
TestLabelData = root_url + "TestDataLabel.csv"

#Load Model path with Model name
model_path = root_url + "LSTMModel"

#Number of Test video
testVideo = 12

#Number of frames from ehere feature is extracted
timeStamp = 90

#Number of Features
numFeature = 1000

#Nunber of classes
classes = 2

payload_lstm_test = {'TestFeature': TestFeature, 'TestLabelData': TestLabelData, 'no_frame': timeStamp,
              'no_feature': numFeature, 'no_testVideo': testVideo,  'model_path': model_path}

r_post = requests.post(url_lstm_test, data=payload_lstm_test)

data_for_post = r_post.json()

print(data_for_post)