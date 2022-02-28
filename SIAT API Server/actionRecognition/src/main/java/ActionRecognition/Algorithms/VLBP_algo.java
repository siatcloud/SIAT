package ActionRecognition.Algorithms;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

import java.io.BufferedWriter;
import java.io.IOException;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class VLBP_algo {
	@SuppressWarnings("null")
	public static String VLBP(String data_url, BufferedWriter output) throws IOException {

		int levelid = 0;
		String filename = data_url;

		String[] parts = filename.split("\\/");

		String resultStr = filename;
		String[] dataid = parts[parts.length - 1].split("\\.");

		String rever = dataid[0];
		System.out.println(data_url);
		ClassLabel clabel = new ClassLabel();
		levelid = clabel.getlabel(rever);

		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);
		int feature_size = 16384;
		long[] tmp2 = new long[feature_size];
		int frame_flag = 0;
		int t3 = 1;
		// int iiii=0;
		IplImage img = null;
		String feature_str = "";
		try {

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

				img = converter.convert(grabber.grab());
				if (img == null && img.isNull())
					break;
				else {

					if (frame_flag == 0) {
						img2 = img;
						matImage3 = new Mat(img2);
						mat3 = new Mat(matImage3.arrayHeight(), matImage3.arrayWidth());
						cvtColor(matImage3, mat3, COLOR_RGB2GRAY);
					}

					else if (frame_flag == 1) {
						img1 = img;
						matImage2 = new Mat(img1);
						mat2 = new Mat(matImage2.arrayHeight(), matImage2.arrayWidth());
						cvtColor(matImage2, mat2, COLOR_RGB2GRAY);
					}

					else if (frame_flag > 1 && frame_flag < num_frame) {

						matImage = new Mat(img);
						Mat mat1 = new Mat(matImage.arrayHeight(), matImage.arrayWidth());

						cvtColor(matImage, mat1, COLOR_RGB2GRAY);
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
			}
			
			output.append(levelid + " ");
			feature_str = feature_str + levelid + " ";
			for (int iii = 0; iii < feature_size; iii++) {
				output.append(t3 + ":" + tmp2[iii] + " ");
				feature_str = feature_str + t3 + ":" + tmp2[iii] + " ";
				t3++;
			}
			tmp2 = null;
			frame_flag = 0;
		} catch (Exception e) {
		}
		output.append(feature_str);
		output.flush();
		output.newLine();
		return filename;
	}

}