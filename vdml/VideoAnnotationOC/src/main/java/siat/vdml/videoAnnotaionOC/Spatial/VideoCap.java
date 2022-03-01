package siat.vdml.videoAnnotaionOC.Spatial;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;


public class VideoCap {

	static {
		nu.pattern.OpenCV.loadShared();
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String input, BufferedWriter bw) throws IOException {
		long startTime = System.currentTimeMillis();


		VideoCapture cap = new VideoCapture();

//		String input = "C:\\Users\\AnwarAbir\\kepler_workspace\\VideoAnnotation\\data\\testData\\5.mp4";
		String output = "data/pic/";
		
		String filename = input;
		String[] parts = filename.split("\\/");
		String[] dataid = parts[parts.length - 1].split("\\.");

		String rever = dataid[0];
		System.out.println(rever);
		

		cap.open(input);

		int video_length = (int) cap.get(Videoio.CAP_PROP_FRAME_COUNT);
		int frames_per_second = (int) cap.get(Videoio.CAP_PROP_FPS);
		int frame_number = (int) cap.get(Videoio.CAP_PROP_POS_FRAMES);

		System.out.println("Video is opened");
		System.out.println("Number of Frames: " + video_length);
		System.out.println(frames_per_second + " Frames per Second");
		System.out.println("Converting Video...");

		Mat frame = new Mat();

		for (frame_number = 1; frame_number <= video_length; frame_number++) {
			if (cap.isOpened()) {
				cap.read(frame);
				System.out.println(frame_number + "frame Height: "
						+ frame.height() + " width: " + frame.width());

				if (frame.empty() || frame == null)
					continue;
				else {
					Imgcodecs.imwrite(output + "/" + frame_number + ".jpg",
							frame);
					featureExtractionFrame.main(rever, frame_number, frame, bw);
				}

				System.out.println(video_length + " Frames extracted");

			} else {
				System.out.println("Fail");
			}
		}
		cap.release();


		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Time: " + totalTime);
	}
}