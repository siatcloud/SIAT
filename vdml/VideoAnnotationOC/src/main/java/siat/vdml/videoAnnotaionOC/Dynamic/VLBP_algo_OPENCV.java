package siat.vdml.videoAnnotaionOC.Dynamic;

import java.io.BufferedWriter;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class VLBP_algo_OPENCV {

	static {
		nu.pattern.OpenCV.loadShared();
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}

	public static String VLBP(String data_url, BufferedWriter output)
			throws IOException {

		int levelid = 0;
		Mat mat3 = null;
		Mat mat2 = null;
		int feature_size = 4096;
		long[] tmp2 = new long[feature_size];
		int frame_flag = 0;
		String filename = data_url;

		String[] parts = filename.split("\\/");

		String resultStr = filename;
		String[] dataid = parts[parts.length - 1].split("\\.");
		System.out.println(data_url);

		String rever = dataid[0];
		System.out.println(rever);
		ClassLabel clabel = new ClassLabel();
		levelid = clabel.getlabel(rever, "KTH");
		/*
		 * OpenCV
		 */
		VideoCapture cap = new VideoCapture();

		String input = data_url;

		cap.open(input);

		int video_length = (int) cap.get(Videoio.CAP_PROP_FRAME_COUNT);
		int frames_per_second = (int) cap.get(Videoio.CAP_PROP_FPS);
		int frame_number = (int) cap.get(Videoio.CAP_PROP_POS_FRAMES);

		System.out.println("Video is opened and Video is " + rever);
		System.out.println("Number of Frames: " + video_length);
		System.out.println(frames_per_second + " Frames per Second");
		System.out.println("Converting Video...");

		Mat frame = new Mat();

		for (frame_number = 0; frame_number < video_length - 3; frame_number++) {
			if (cap.isOpened()) {
				cap.read(frame);
				Mat gray = new Mat(frame.height(), frame.width(),
						frame.channels());
				Imgproc.cvtColor(frame, gray, Imgproc.COLOR_RGB2GRAY);

				if (frame_flag == 0) {
					mat3 = new Mat(gray.height(), gray.width(), gray.channels());
					mat3 = gray;
					Imgcodecs
							.imwrite("data/pic/" + frame_number + ".jpg", mat3);
					// System.out.println("1st Frame");
				} else if (frame_flag == 1) {
					mat2 = new Mat(gray.height(), gray.width(), gray.channels());
					mat2 = gray;
					Imgcodecs
							.imwrite("data/pic/" + frame_number + ".jpg", mat2);
				} else if (frame_flag > 1 && frame_flag < video_length) {
					// System.out.println("Frame Number: " + frame_number);
					Mat mat1 = new Mat(gray.height(), gray.width(),
							gray.channels());
					mat1 = gray;
					VLBP poshogimage = new VLBP(mat3, mat2, mat1);
					Imgcodecs
							.imwrite("data/pic/" + frame_number + ".jpg", mat1);

					long[] tmp1 = poshogimage.finaldes();

					for (int iii = 0; iii < feature_size; iii++) {
						tmp2[iii] = tmp2[iii] + tmp1[iii];
					}

					mat3 = mat2;
					mat2 = mat1;
				}
				frame_flag++;

				System.out.println(video_length + " Frames extracted");

			} else {
				System.out.println("Fail");
			}
		}
		cap.release();

		output.append(rever + ": ");
		for (int iii = 0; iii < feature_size; iii++) {
			if (iii + 1 == feature_size) {
				output.append(tmp2[iii] + "");
			} else {
				output.append(tmp2[iii] + ", ");
			}
		}
		tmp2 = null;
		frame_flag = 0;

		output.newLine();
		return filename;
	}
}
