package siat.vdml.videoAnnotaionOC.Dynamic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

//import org.bytedeco.javacpp.indexer.UByteRawIndexer;

public class VLBP {
	private Mat Image, Image2, Image3;

	public VLBP(Mat img, Mat img2, Mat img3) {
		this.Image = img;
		this.Image2 = img2;
		this.Image3 = img3;
	}

	public long[] motion_binary_pattern(int[][] preFrame, int[][] currentFrame,
			int[][] nextFrame) throws IOException {

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
				// out.write(j0+ " ");

				if (preFrame[i][j] >= j0) {
					sec_lay1[i][j] = 1;
					// out.write(sec_lay1[i-1][j]+ " ");
				}

				/*
				 * if(array[i-1][j-1]>=j0){ sec_lay1[i-1][j-1]=1;
				 * //out.write(array[i-1][j-1]+ " "+j0
				 * +" "+sec_lay1[i-1][j-1]+" "); }
				 */
				if (preFrame[i - 1][j] >= j0) {
					sec_lay1[i - 1][j] = 8;
					// out.write(sec_lay1[i-1][j]+ " ");
				}
				/*
				 * if(array[i-1][j+1]>=j0){ sec_lay1[i-1][j+1]=1;
				 * //out.write(sec_lay1[i-1][j+1]+ " "); }
				 */
				if (preFrame[i][j + 1] >= j0) {
					sec_lay1[i][j + 1] = 16;
					// out.write(sec_lay1[i][j+1]+ " ");
				}
				/*
				 * if(array[i+1][j+1]>=j0){ sec_lay1[i+1][j+1]=1;
				 * //out.write(I3[i+1][j+1]+ " ");
				 * 
				 * }
				 */
				if (preFrame[i + 1][j] >= j0) {
					sec_lay1[i + 1][j] = 2;
					// out.write(I3[i+1][j]+ " ");
				}
				/*
				 * if(array[i+1][j-1]>=j0){ sec_lay1[i+1][j-1]=1;
				 * //out.write(I3[i+1][j-1]+ " "); }
				 */
				if (preFrame[i][j - 1] >= j0) {
					sec_lay1[i][j - 1] = 4;
					// out.write( I3[i][j-1]+ " ");
				}

				/*
				 * if(array2[i-1][j-1]>=j0){ sec_lay2[i-1][j-1]=1;
				 * //out.write(array3[i-1][j-1]+ " "+j0
				 * +" "+sec_lay2[i-1][j-1]+" "); //out.write(I3[i-1][j-1]+ " ");
				 * }
				 */
				if (currentFrame[i - 1][j] >= j0) {
					sec_lay2[i - 1][j] = 128;
					// out.write(I3[i-1][j]+ " ");
				}
				/*
				 * if(array2[i-1][j+1]>=j0){ sec_lay2[i-1][j+1]=1;
				 * //out.write(I3[i-1][j+1]+ " "); }
				 */
				if (currentFrame[i][j + 1] >= j0) {
					sec_lay2[i][j + 1] = 256;
					// out.write(I3[i][j+1]+ " ");
				}
				/*
				 * if(array2[i+1][j+1]>=j0){ sec_lay2[i+1][j+1]=1;
				 * //out.write(I3[i+1][j+1]+ " ");
				 * 
				 * }
				 */
				if (currentFrame[i + 1][j] >= j0) {
					sec_lay2[i + 1][j] = 32;
					// out.write(I3[i+1][j]+ " ");
				}
				/*
				 * if(array2[i+1][j-1]>=j0){ sec_lay2[i+1][j-1]=1;
				 * //out.write(I3[i+1][j-1]+ " "); }
				 */
				if (currentFrame[i][j - 1] >= j0) {
					sec_lay2[i][j - 1] = 64;
					// out.write( I3[i][j-1]+ " ");
				}

				/*
				 * if(array3[i-1][j-1]>=j0){ sec_lay3[i-1][j-1]=1;
				 * //out.write(array3[i-1][j-1]+ " "+j0
				 * +" "+sec_lay2[i-1][j-1]+" "); //out.write(I3[i-1][j-1]+ " ");
				 * }
				 */
				if (nextFrame[i][j] >= j0) {
					sec_lay3[i][j] = 8192;
					// out.write(sec_lay1[i-1][j]+ " ");
				}

				if (nextFrame[i - 1][j] >= j0) {
					sec_lay3[i - 1][j] = 2048;
					// out.write(I3[i-1][j]+ " ");
				}
				/*
				 * if(array3[i-1][j+1]>=j0){ sec_lay3[i-1][j+1]=1;
				 * //out.write(I3[i-1][j+1]+ " "); }
				 */
				if (nextFrame[i][j + 1] >= j0) {
					sec_lay3[i][j + 1] = 4096;
					// out.write(I3[i][j+1]+ " ");
				}
				/*
				 * if(array3[i+1][j+1]>=j0){ sec_lay3[i+1][j+1]=1;
				 * //out.write(I3[i+1][j+1]+ " ");
				 * 
				 * }
				 */
				if (nextFrame[i + 1][j] >= j0) {
					sec_lay3[i + 1][j] = 512;
					// out.write(I3[i+1][j]+ " ");
				}
				/*
				 * if(array3[i+1][j-1]>=j0){ sec_lay3[i+1][j-1]=1;
				 * //out.write(I3[i+1][j-1]+ " "); }
				 */
				if (nextFrame[i][j - 1] >= j0) {
					sec_lay3[i][j - 1] = 1024;
					// out.write( I3[i][j-1]+ " ");
				}

				// LBP[i][j]=I3[i-1][j-1]+I3[i-1][j]+I3[i-1][j+1]+I3[i][j+1]+I3[i+1][j+1]+I3[i+1][j]+I3[i+1][j-1]+I3[i][j-1];

				LBP[i][j] = sec_lay1[i][j] + sec_lay3[i][j]
						+ sec_lay1[i - 1][j] + sec_lay1[i][j + 1]
						+ sec_lay1[i + 1][j] + sec_lay1[i][j - 1]
						+ sec_lay2[i - 1][j] + sec_lay2[i][j + 1]
						+ sec_lay2[i + 1][j] + sec_lay2[i][j - 1]
						+ sec_lay3[i - 1][j] + sec_lay3[i][j + 1]
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
				// out.write(LBP[i][j]+ " ");
				// out.newLine();
				// out.write((LBP[i][j])+" ");
				count = count + 1;

			}
		}
		// System.out.println(Math.pow(2, 5));
		/*
		 * for(int qw=0;qw<w;qw++) for(int wq=0;wq<h;wq++)
		 * System.out.println(LBP[qw][wq]+ " ");
		 */
		/*
		 * out.write(LBP.length+" "+LBP[0].length+""); for(int
		 * qw=0;qw<array.length;qw++){ for(int wq=0;wq<array[0].length;wq++) {
		 * out.newLine(); out.write((LBP[qw][wq])+"");
		 * 
		 * } }
		 */
//		for (int[] ks : LBP) {
//			for (int k : ks) {
//				System.out.print(k + " ");
//			}
//			System.out.println();
//		}

		long[] f = new long[16384];
		for (int kkk = 0; kkk < 16384; kkk++)
			f[kkk] = 0;

		int cout = 0, z = 0;

		for (int ii = 0; ii < w; ii++) {
			for (int jj = 0; jj < h; jj++) {
				// System.out.print(cout+" ");
				cout++;
				// z=LBP[ii][jj];
				// out.write(LBP[ii][jj]+" ");
				// f[z++]=f[z++]+1;

				f[LBP[ii][jj]]++;
				// out.write(z+" ");

			}
			// out.newLine();
		}

		int pixel;
		/*
		 * for (int j = 0; j < 781; j++) { for (int col = 0; col < 144; col++) {
		 * for (int row = 0; row < 180; row++) { pixel = LBP[col][row];
		 * out.write(LBP[col][row]+" "); if (pixel == j) { f[j]++; } }
		 * out.newLine(); } }
		 */
		/*
		 * for(int kkk=1;kkk<=780;kkk++) {out.write(f[kkk]+" ");}
		 */
		return f;

	}

	public int[][] MatToBytes(Mat matImage, BufferedWriter bw) throws IOException {
		int height = matImage.height();
		int width = matImage.width();
		int channel = matImage.channels();
		// int width = matImage.size().width(), height =
		// matImage.size().height(), channels = matImage
		// .channels();
		int[][] output = new int[height][width];
		for (int i = 0; i < matImage.rows(); i++) {
			for (int j = 0; j < matImage.cols(); j++) {
				output[i][j] = (int) matImage.get(i, j)[0];
			}
		}
		
//		for (int i = 0; i < output.length; i++) {
//			for (int j = 0; j < output[0].length; j++) {
//				System.out.print(output[i][j] + " ");
//				bw.append(output[i][j] + ",");
//				bw.flush();
//			}
//			bw.newLine();
//		}
//		int temp = (Double.compare(height, width) > 0)? 1 : 0;
		return output;
	}
	
	static int[] matToIntArray(Mat mRgba) {
        MatOfInt rgb = new MatOfInt(CvType.CV_32S);
        mRgba.convertTo(rgb, CvType.CV_32S);
        int[] rgba = new int[(int)(rgb.total()*rgb.channels())];
        rgb.get(0,0,rgba);
        return rgba;
    }

	public long[] finaldes() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("data/preframe.csv", false));
		BufferedWriter writer1 = new BufferedWriter(new FileWriter("data/frame.csv", false));
		BufferedWriter writer2 = new BufferedWriter(new FileWriter("data/nextframe.csv", false));

		int[][] preFrame = MatToBytes(Image, writer);
		int[][] currentFrame = MatToBytes(Image2, writer1);
		int[][] nextFrame = MatToBytes(Image3, writer2);
		writer.close();
		writer1.close();
		writer2.close();

		long[] descriptor = motion_binary_pattern(preFrame, currentFrame,
				nextFrame);
		// for(int kkk=0;kkk<=4095;kkk++)
		// {System.out.println(descriptor[kkk]+" ");}

		return descriptor;

	}

}
