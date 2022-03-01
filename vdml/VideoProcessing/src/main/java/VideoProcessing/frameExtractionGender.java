package VideoProcessing;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

import java.io.BufferedWriter;
import java.io.IOException;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;

import VideoProcessing.algorithms.LBP_Gray;

public class frameExtractionGender {
	public static String extract(String data_url, BufferedWriter output, String dataset_path) throws IOException {

		// Declare necessary varaibles
		int levelid = 0;
		int feature_size = 256;
		int[] feature = new int[feature_size];
		IplImage img = null;
		IplImage img2 = null;
		Mat matImage3 = null;
		Mat mat3 = null;

		String filename = data_url;
		String[] parts = filename.split("/");
		String[] dataid = parts[parts.length - 1].split("\\.");
		String rever = dataid[0];

		// Get class IDs according to the video number
		classLabel clabel = new classLabel();
		levelid = clabel.getlabel(rever, dataset_path);

		// Get exact File (remove File:/ from the path)
		String main_file = data_url.substring(6);

		// Create grabber object using path
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(main_file);
		grabber.setFormat("avi");
//		System.out.println(filename);
		try {
			// IplImage converter
			OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
			grabber.start();

			int num_frame = grabber.getLengthInFrames();

			for (int k = 0; k < num_frame; k++) {
				// Grab current frame
				img = converter.convert(grabber.grab());
//				System.out.println("frame number: " + k);

				img2 = img;
				matImage3 = new Mat(img2);
				mat3 = new Mat(matImage3.arrayHeight(), matImage3.arrayWidth());
				// Convert RGB to Gary image
				cvtColor(matImage3, mat3, COLOR_RGB2GRAY);

				LBP_Gray lbp = new LBP_Gray(mat3, output, levelid);
				feature = lbp.finaldes();
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return filename;
	}

	private static void writeFeature(int[] feature, BufferedWriter output, int levelid) throws IOException {
		int count = 1;
		output.append(levelid + "");
		for (int i : feature) {
			output.append(" " + count + ":" + i);
			count++;
		}
		output.newLine();
		output.flush();
	}
}
