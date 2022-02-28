package ActionRecognition.Algorithms;

import java.io.IOException;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;

public class VLBP {
	private Mat Image, Image2, Image3;

	public VLBP(Mat img, Mat img2, Mat img3) {
		this.Image = img;
		this.Image2 = img2;
		this.Image3 = img3;
	}

	public long[] motion_binary_pattern(int[][] preFrame, int[][] currentFrame, int[][] nextFrame) throws IOException {

		int w = preFrame.length;
		int h = preFrame[0].length;
		int[][] I3 = new int[w][h];
		int[][] sec_lay1 = new int[w][h];
		int[][] sec_lay2 = new int[w][h];
		int[][] sec_lay3 = new int[w][h];
		int[][] LBP = new int[w][h];

		for (int rt = 0; rt < w; rt++)
			for (int tr = 0; tr < h; tr++)
				LBP[rt][tr] = 0;

		int count = 1;
		int j0 = 0;
		for (int i = 2; i <= w - 2; i++) {
			for (int j = 2; j <= h - 2; j++) {
				j0 = currentFrame[i][j];

				if (preFrame[i][j] >= j0) {
					sec_lay1[i][j] = 1;
				}

				if (preFrame[i - 1][j] >= j0) {
					sec_lay1[i - 1][j] = 8;
				}
				if (preFrame[i][j + 1] >= j0) {
					sec_lay1[i][j + 1] = 16;
				}
				if (preFrame[i + 1][j] >= j0) {
					sec_lay1[i + 1][j] = 2;
				}
				if (preFrame[i][j - 1] >= j0) {
					sec_lay1[i][j - 1] = 4;
				}

				if (currentFrame[i - 1][j] >= j0) {
					sec_lay2[i - 1][j] = 128;
				}
				if (currentFrame[i][j + 1] >= j0) {
					sec_lay2[i][j + 1] = 256;
				}
				if (currentFrame[i + 1][j] >= j0) {
					sec_lay2[i + 1][j] = 32;
				}
				if (currentFrame[i][j - 1] >= j0) {
					sec_lay2[i][j - 1] = 64;
				}
				if (nextFrame[i][j] >= j0) {
					sec_lay3[i][j] = 8192;
				}

				if (nextFrame[i - 1][j] >= j0) {
					sec_lay3[i - 1][j] = 2048;
				}
				if (nextFrame[i][j + 1] >= j0) {
					sec_lay3[i][j + 1] = 4096;
				}
				if (nextFrame[i + 1][j] >= j0) {
					sec_lay3[i + 1][j] = 512;
				}
				if (nextFrame[i][j - 1] >= j0) {
					sec_lay3[i][j - 1] = 1024;
				}

				LBP[i][j] = sec_lay1[i][j] + sec_lay3[i][j] + sec_lay1[i - 1][j] + sec_lay1[i][j + 1]
						+ sec_lay1[i + 1][j] + sec_lay1[i][j - 1] + sec_lay2[i - 1][j] + sec_lay2[i][j + 1]
						+ sec_lay2[i + 1][j] + sec_lay2[i][j - 1] + sec_lay3[i - 1][j] + sec_lay3[i][j + 1]
						+ sec_lay3[i + 1][j] + sec_lay3[i][j - 1];

				sec_lay1[i][j] = 0;
				sec_lay1[i - 1][j - 1] = 0;
				sec_lay1[i - 1][j] = 0;
				sec_lay1[i - 1][j + 1] = 0;
				sec_lay1[i][j + 1] = 0;
				sec_lay1[i + 1][j + 1] = 0;
				sec_lay1[i + 1][j] = 0;
				sec_lay1[i + 1][j - 1] = 0;
				sec_lay1[i][j - 1] = 0;
				sec_lay2[i - 1][j - 1] = 0;
				sec_lay2[i - 1][j] = 0;
				sec_lay2[i - 1][j + 1] = 0;
				sec_lay2[i][j + 1] = 0;
				sec_lay2[i + 1][j + 1] = 0;
				sec_lay2[i + 1][j] = 0;
				sec_lay2[i + 1][j - 1] = 0;
				sec_lay2[i][j - 1] = 0;
				sec_lay3[i][j] = 0;
				sec_lay3[i - 1][j - 1] = 0;
				sec_lay3[i - 1][j] = 0;
				sec_lay3[i - 1][j + 1] = 0;
				sec_lay3[i][j + 1] = 0;
				sec_lay3[i + 1][j + 1] = 0;
				sec_lay3[i + 1][j] = 0;
				sec_lay3[i + 1][j - 1] = 0;
				sec_lay3[i][j - 1] = 0;

				I3[i - 1][j - 1] = 0;
				I3[i - 1][j] = 0;
				I3[i - 1][j + 1] = 0;
				I3[i][j + 1] = 0;
				I3[i + 1][j + 1] = 0;
				I3[i + 1][j] = 0;
				I3[i + 1][j - 1] = 0;
				I3[i][j - 1] = 0;

				count = count + 1;

			}
		}

		long[] f = new long[16384];
		for (int kkk = 0; kkk < 16384; kkk++)
			f[kkk] = 0;

		for (int ii = 0; ii < w; ii++) {
			for (int jj = 0; jj < h; jj++) {
				f[LBP[ii][jj]]++;
			}
		}

		return f;

	}

	@SuppressWarnings("unused")
	public int[][] MatToBytes(Mat matImage) {
		int width = matImage.size().width(), height = matImage.size().height(), channels = matImage.channels();
		int[][] output = new int[height][width];
		UByteRawIndexer indexer = matImage.createIndexer();

		for (int i = 0; i < matImage.rows(); i++) {
			for (int j = 0; j < matImage.cols(); j++) {
				output[i][j] = (int) indexer.get(i, j);
			}
		}

		return output;
	}

	public long[] finaldes() throws IOException {

		int[][] preFrame = MatToBytes(Image);
		int[][] currentFrame = MatToBytes(Image2);
		int[][] nextFrame = MatToBytes(Image3);

		long[] descriptor = motion_binary_pattern(preFrame, currentFrame, nextFrame);

		return descriptor;

	}

}
