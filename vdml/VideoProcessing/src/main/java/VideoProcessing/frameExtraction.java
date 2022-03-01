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

import VideoProcessing.algorithms.VLBP;

public class frameExtraction {
	public static String extract(String data_url, BufferedWriter output, String dataset_path) throws IOException {

		// Declare necessary varaibles
		int levelid = 0;
		int feature_size = 4096;
		long[] tmp2 = new long[feature_size];
		int frame_flag = 0;
		int t3 = 1;
		IplImage img = null;

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

		try {
			// IplImage converter
			OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
			grabber.start();

			int num_frame = grabber.getLengthInFrames();

			IplImage img1 = null;
			IplImage img2 = null;
			Mat matImage = null;
			Mat matImage2 = null;
			Mat matImage3 = null;
			Mat mat3 = null;
			Mat mat2 = null;

			for (int k = 0; k < num_frame; k++) {
				// Grab current frame
				img = converter.convert(grabber.grab());

				if (frame_flag == 0) {
					img2 = img;
					matImage3 = new Mat(img2);
					mat3 = new Mat(matImage3.arrayHeight(), matImage3.arrayWidth());
					// Convert RGB to Gary image
					cvtColor(matImage3, mat3, COLOR_RGB2GRAY);
				}

				else if (frame_flag == 1) {
					img1 = img;
					matImage2 = new Mat(img1);
					mat2 = new Mat(matImage2.arrayHeight(), matImage2.arrayWidth());
					// Convert RGB to Gary image
					cvtColor(matImage2, mat2, COLOR_RGB2GRAY);
				}

				else if (frame_flag > 1 && frame_flag < num_frame) {
					matImage = new Mat(img);
					Mat mat1 = new Mat(matImage.arrayHeight(), matImage.arrayWidth());
					// Convert RGB to Gary image
					cvtColor(matImage, mat1, COLOR_RGB2GRAY);
					
					// Call VLBP Function for feature extraction
					VLBP poshogimage = new VLBP(mat3, mat2, mat1);

					long[] tmp1 = poshogimage.finaldes();

					for (int iii = 0; iii < feature_size; iii++) {
						tmp2[iii] = tmp2[iii] + tmp1[iii];
					}

					mat3 = mat2;
					mat2 = mat1;

				}
				frame_flag++;
			}
			
			// Write Feature
			output.append(levelid + "");
			for (int iii = 0; iii < feature_size; iii++) {
				output.append(" " + t3 + ":" + tmp2[iii]);
				t3++;
			}
			tmp2 = null;
			frame_flag = 0;
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		output.newLine();
		output.flush();
		return filename;
	}
}
