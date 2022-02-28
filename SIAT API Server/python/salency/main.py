import numpy as np
import cv2

WIDTH = 128   # has a great influence on the result


def video2image(path):
    # Opens the Video file
    cap = cv2.VideoCapture(path)
    i = 1
    while (cap.isOpened()):
        ret, frame = cap.read()
        if ret == False:
            break
        # cv2.imwrite('frame/frame' + str(i) + '.jpg', frame)
        i += 1
        saliency(frame, i)

    cap.release()
    cv2.destroyAllWindows()


def saliency(frame, counter):
    img = cv2.imread('frame/frame0.jpg', 0)
    img = cv2.resize(img, (WIDTH, int(WIDTH * img.shape[0] / img.shape[1])))
    eps = 1e-7

    c = cv2.dft(np.float32(img), flags=cv2.DFT_COMPLEX_OUTPUT)
    mag = np.sqrt(c[:,:,0]**2 + c[:,:,1]**2)
    spectralResidual = np.exp(np.log(mag) - cv2.boxFilter(np.log(mag), -1, (3, 3)))

    c[:, :, 0] = c[:, :, 0] * spectralResidual / mag
    c[:, :, 1] = c[:, :, 1] * spectralResidual / mag
    c = cv2.dft(c, flags=(cv2.DFT_INVERSE | cv2.DFT_SCALE))
    mag = c[:,:,0]**2 + c[:,:,1]**2
    cv2.normalize(cv2.GaussianBlur(mag, (9, 9), 3, 3), mag, 0., 1., cv2.NORM_MINMAX)

    cv2.imwrite('frame/frame' + str(counter) + '.jpg', mag)
    (hist, _) = np.histogram(mag, bins=np.arange(0, 255), range=(0, 30))

    # normalize the histogram
    hist = hist.astype("float")
    hist /= (hist.sum() + eps)

    return hist


if __name__ == '__main__':
    video_path = "1.avi"
    video2image(video_path)
