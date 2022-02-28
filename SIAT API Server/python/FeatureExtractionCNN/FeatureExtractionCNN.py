import numpy as np
import cv2
import imutils
import os
from tensorflow.keras.applications.resnet50 import ResNet50
from tensorflow.keras.preprocessing import image
from tensorflow.keras.applications.resnet50 import preprocess_input, decode_predictions


def FeatureExtractionCNN_ResNet(input, Write_filepath, extension, no_frame):
    # Read_filepath = "data/train/"
    root_url = "D:/siat/"
    Read_filepath = root_url + input
    print(Read_filepath)
    list = os.listdir(Read_filepath)  # dir is your directory path
    number_files = len(list)

    model = ResNet50(weights='imagenet')
    f = open(root_url + Write_filepath, "a+")
    for videodata in range(1, number_files + 1):
        # for videodata in range (1,2):
        data = Read_filepath + str(videodata) + "." + extension
        cap = cv2.VideoCapture(data)
        count = 1

        # while(count<=90):

        while count < int(no_frame) + 1:
            # Capture frame-by-frame
            (grabbed, frame) = cap.read()
            if not grabbed:
                break
            # cv2.imwrite("output/"+str(count)+".jpg",frame)
            img_path = frame
            newsize = (224, 224)
            img = cv2.resize(img_path, newsize, interpolation=cv2.INTER_AREA)

            # img = image.load_img(img_path, target_size=(224, 224))
            x = image.img_to_array(img)
            x = np.expand_dims(x, axis=0)
            x = preprocess_input(x)
            features = model.predict(x)
            temp = 0

            # f.write(str(videodata) + "," + str(count) + ",")
            for featuredata in features[0, :]:
                if(temp < 999):
                    f.write(str(featuredata) + ",")
                else:
                    f.write(str(featuredata) + "\n")
                temp = temp + 1

            temp = 0
            f.flush()
            #print(features)

            # cv2.imshow('frame',frame)
            count = count + 1
            #print(count)

        cap.release()
        cv2.destroyAllWindows()
    f.close()


if __name__ == '__main__':
    Read_filepath = "data/test/"
    Write_filepath = "feature_test.csv"
    FeatureExtractionCNN_ResNet(Read_filepath, Write_filepath)
