package VideoProcessing.algorithms;

import java.io.BufferedWriter;
import java.io.IOException;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;

public class LBP_Gray {
	private Mat Image;
	private BufferedWriter out;
	private int Levelid;


	public LBP_Gray(Mat img, BufferedWriter output, int levelid) {
		Image = img;
		out = output;
		Levelid = levelid;
	}

	// compute the gradient in vertical direction
	public int[] rgradient(int[][] array) throws IOException {
		int w = array.length;
		int h = array[0].length;
		int[][] I3 = new int[array.length][array[0].length];
		int[][] LBP = new int[array.length][array[0].length];

		for (int rt = 0; rt < array.length; rt++)
			for (int tr = 0; tr < array[0].length; tr++)
				LBP[rt][tr] = 0;

		int count = 1;
		int j0 = 0;
		for (int i = 2; i <= w - 2; i++) {
			for (int j = 2; j <= h - 2; j++) {
				j0 = array[i][j];
				if (array[i - 1][j - 1] > j0) {
					I3[i - 1][j - 1] = (int) (1 * Math.pow(2, 0));
					// out.write(I3[i-1][j-1]+ " ");
				}
				if (array[i - 1][j] > j0) {
					I3[i - 1][j] = (int) (1 * Math.pow(2, 7));
					// out.write(I3[i-1][j]+ " ");
				}
				if (array[i - 1][j + 1] > j0) {
					I3[i - 1][j + 1] = (int) (1 * Math.pow(2, 6));
					// out.write(I3[i-1][j+1]+ " ");
				}
				if (array[i][j + 1] > j0) {
					I3[i][j + 1] = (int) (1 * Math.pow(2, 5));
					// out.write(I3[i][j+1]+ " ");
				}
				if (array[i + 1][j + 1] > j0) {
					I3[i + 1][j + 1] = (int) (1 * Math.pow(2, 4));
					// out.write(I3[i+1][j+1]+ " ");

				}
				if (array[i + 1][j] > j0) {
					I3[i + 1][j] = (int) (1 * Math.pow(2, 3));
					// out.write(I3[i+1][j]+ " ");
				}
				if (array[i + 1][j - 1] > j0) {
					I3[i + 1][j - 1] = (int) (1 * Math.pow(2, 2));
					// out.write(I3[i+1][j-1]+ " ");
				}
				if (array[i][j - 1] > j0) {
					I3[i][j - 1] = (int) (1 * Math.pow(2, 1));
					// out.write( I3[i][j-1]+ " ");
				}

				LBP[i][j] = I3[i - 1][j - 1] + I3[i - 1][j] + I3[i - 1][j + 1] + I3[i][j + 1] + I3[i + 1][j + 1]
						+ I3[i + 1][j] + I3[i + 1][j - 1] + I3[i][j - 1];
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

		int[] f = new int[256];
		for (int kkk = 0; kkk < 255; kkk++)
			f[kkk] = 0;

		for (int ii = 0; ii < w - 1; ii++) {
			for (int jj = 0; jj < h - 1; jj++) {
				f[LBP[ii][jj]]++;
			}
		}
		
		int cout = 1;
		String feature_out = Levelid + "";
		for (int k : f) {
			feature_out += " " + cout + ":" + k;
			cout++;
		}
		feature_out += "\n";
		out.append(feature_out);
		out.flush();
//		writeFeature(f, out, Levelid);

		return f;

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

	public int[][] MatToBytes(Mat matImage) {
		int width = matImage.size().width(), height = matImage.size().height(), channels = matImage.channels();
		int[][] output = new int[height][width];
		UByteRawIndexer indexer = matImage.createIndexer();

		int index = 0;
		for (int i = 0; i < matImage.rows(); i++) {
			for (int j = 0; j < matImage.cols(); j++) {

				output[i][j] = (int) indexer.get(i, j);
				// index++;

			}
		}

		return output;
	}

	public int[] finaldes() throws IOException {

		int[][] array = MatToBytes(Image);

		int[] descriptor = rgradient(array);

		return descriptor;

	}

}