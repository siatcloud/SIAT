import requests
# url = 'http://163.180.116.64:5000/multi/'
# payload = open("5")
# headers = {'content-type': 'application/json', 'Accept-Charset': 'UTF-8'}
# r = requests.post(url, data=5, headers=headers)


# curl http://163.180.116.64:5000/ -d "data_path=data/test/1.mp4" -X POST -v

# headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}
# r = requests.post(url, data={"data_path": "data/test/1.mp4"}, headers=headers)
# data = r.json()
# print(data)

# Get data
num = 6
url = 'http://163.180.116.64:5000/multi/' + str(num)
get_r = requests.get(url)
data = get_r.json()
print(data)


payload = 5
url_2 = 'http://163.180.116.64:5000/multi/' + str(payload)
pr = requests.post(url_2)
post_data = pr.json()
print(post_data)

url_4 = 'http://163.180.116.64:5000/test/'
get_r2 = requests.get(url_4, data={'num': 2})
data_get = get_r2.json()
print(data_get)


url_3 = 'http://163.180.116.64:5000/test/'
# headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}
payload_3 = {'data_path': 'data/test/1.mp4'}
r_post = requests.post(url_3, data=payload_3)
data_for_post = r_post.json()
print(data_for_post)


url_od = 'http://163.180.116.64:5000/ObjectDetection/'
# headers = {"content-type": "application/json", "Accept-Charset": "UTF-8"}
payload_od = {'data_path': 'data/1.mp4', 'output_path': 'data/output.mp4'}
r_post = requests.post(url_od, data=payload_od)
data_for_post = r_post.json()
print(data_for_post)
