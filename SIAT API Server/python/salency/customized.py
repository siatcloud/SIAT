import numpy as np
import cv2

WIDTH = 128   # has a great influence on the result


def video2image(path):
    # Opens the Video file
    cap = cv2.VideoCapture(path)
    i = 0
    while (cap.isOpened()):
        ret, frame = cap.read()
        if ret == False:
            break
        # cv2.imwrite('frame' + str(i) + '.jpg', frame)
        i += 1
        saliency(frame, i)

    cap.release()
    cv2.destroyAllWindows()


def saliency(frame, counter):
    img = cv2.imread('1.jpg', 0)
    dim = (WIDTH, int(WIDTH * img.shape[0] / img.shape[1]))
    img = cv2.resize(img, dim)
    eps = 1e-7

    dft = cv2.dft(np.float32(img), flags=cv2.DFT_COMPLEX_OUTPUT)
    dft_shift = np.fft.fftshift(dft)

    magnitude_spectrum = 20 * np.log(cv2.magnitude(dft_shift[:, :, 0], dft_shift[:, :, 1]))

    rows, cols = img.shape
    crow, ccol = rows / 2, cols / 2

    # create a mask first, center square is 1, remaining all zeros
    mask = np.zeros((rows, cols, 2), np.uint8)
    mask[int(crow) - 30:int(crow) + 30, int(ccol) - 30:int(ccol) + 30] = 1

    # apply mask and inverse DFT
    fshift = dft_shift * mask
    f_ishift = np.fft.ifftshift(fshift)
    img_back = cv2.idft(f_ishift)
    img_back = cv2.magnitude(img_back[:, :, 0], img_back[:, :, 1])

    cv2.imwrite('frame/frame' + str(counter) + '.jpg', img_back)
    (hist, _) = np.histogram(img_back, bins=np.arange(0, 255), range=(0, 30))

    # normalize the histogram
    hist = hist.astype("float")
    hist /= (hist.sum() + eps)

    return hist


if __name__ == '__main__':
    video_path = "1.avi"
    video2image(video_path)
