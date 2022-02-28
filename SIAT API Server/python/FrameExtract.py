import cv2
import os


def Video2Image(path, output_url, no_frame):
    cap = cv2.VideoCapture(path)
    root_url = "D:/siat/"
    directory = root_url + output_url

    if not os.path.exists(directory):
        os.makedirs(directory)

    count = 0
    while True:
        # print('About to start the Read command')
        ret, frame = cap.read()

        if not frame is None:
            # print('About to show frame of Video.')
            # cv2.imshow("Capturing", frame)
            cv2.imwrite(directory + "/%d.jpg" % count, frame)
            count += 1

            if count > int(no_frame):
                break
            # print('Running..')
            # if cv2.waitKey(1) & 0xFF == ord('q'):
            #     break
        else:
            break

    cap.release()
    cv2.destroyAllWindows()
    return output_url

if __name__ == '__main__':
    path = "rtmp://server.siat.kr/cam1"
    #path="1.avi"
    url = "khu/AnwarAbir/output/frame"
    no_frame = 50
    Video2Image(path, url, no_frame)