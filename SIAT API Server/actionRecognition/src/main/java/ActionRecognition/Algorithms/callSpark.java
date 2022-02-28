package ActionRecognition.Algorithms;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.input.PortableDataStream;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;

import scala.Tuple2;

public class callSpark {

	private static BufferedWriter output;

	@SuppressWarnings({ "resource", "serial" })
	public static void featureExtraction(String data_url, final String result_url, final boolean trainType)
			throws Exception, IOException {
		System.setProperty("hadoop.home.dir", "C:\\Hadoop\\hadooponwindows-master\\");

		SparkConf conf = new SparkConf().setAppName("Action Recognition").setMaster("local[*]")
				.set("spark.driver.allowMultipleContexts", "true");
		JavaSparkContext sc = new JavaSparkContext(conf);
//		int feature_size = 16384 * 1024;
//		int bufferSize = (feature_size * 1024);

		final FileWriter FW = (new FileWriter(result_url, false));
		output = new BufferedWriter(FW);

//		output = new BufferedWriter(new FileWriter(result_url));
//		System.out.println("Flag 0");

		JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 4);

//		System.out.println("JavaPair RDD");

		JavaPairRDD<String, String> videosRDD = videos_
				.mapToPair(new PairFunction<Tuple2<String, PortableDataStream>, String, String>() {
					public Tuple2<String, String> call(Tuple2<String, PortableDataStream> kv)
							throws Exception, IOException {
//						System.out.print("Hello");

						int levelid = 0;
						String filename = kv._1.toString();
						String ouputFile = "";
//						System.out.print(filename);

						String jj = filename;

						String reverse = new StringBuffer(jj).reverse().toString();
						// System.out.println("Reverse: " + reverse);
						String[] parts = reverse.split("\\/");

						// for (int i = 0; i < parts.length; i++) {
						// System.out.print(parts[i] + " + ");
						// }

						int len = parts[0].length();

						String resultStr = jj.substring(6, jj.length());

//						System.out.println(resultStr);
						String dataid = parts[0].substring(4, len);

						String rever = new StringBuffer(dataid).reverse().toString();
						ClassLabel clabel = new ClassLabel();
						levelid = clabel.getlabel(rever, trainType);

						long[] tmp2 = new long[16384];
						int frame_flag = 0;
						// int iiii=0;
						IplImage img = null;
						try {

							OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

							FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(filename);
//							grabber.setFormat("mp4");
							grabber.start();
							int num_frame = grabber.getLengthInFrames();
//							System.out.println("***********grabber:***********" + num_frame);

							IplImage img1 = null;
							IplImage img2 = null;
							Mat matImage = null;
							Mat matImage2 = null;
							Mat matImage3 = null;
							Mat mat3 = null;
							Mat mat2 = null;

							for (int k = 0; k < num_frame; k++) {

								img = converter.convert(grabber.grab());
								if (img == null)
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

//										System.out.println("width: " + matImage.arrayWidth() + " height: "
//												+ matImage.arrayHeight());
										cvtColor(matImage, mat1, COLOR_RGB2GRAY);
										VLBP poshogimage = new VLBP(mat3, mat2, mat1);

										long[] tmp1 = poshogimage.finaldes();

										for (int iii = 0; iii < 16384; iii++) {
											tmp2[iii] = tmp2[iii] + tmp1[iii];
										}

										mat3 = mat2;
										mat2 = mat1;

									}
									frame_flag++;
								}
							}
//							System.out.println("feature Data: " + tmp2[10]);
//							WriteData.append("Test Error: " + tmp2[10], "feature.txt");
//							output.write(levelid + " ");
							ouputFile = levelid + " ";
							int count = 1;
							for (int iii = 0; iii < 16384; iii++) {
								ouputFile = ouputFile + (iii + 1) + ":" + tmp2[iii] + " ";
//								output.write(count + ":" + );
							}
							output.append(ouputFile);
							tmp2 = null;
							frame_flag = 0;
						} catch (Exception e) {
//							WriteData.append("Skipped error is: " + e, "error.txt");
							System.out.println("error: " + e + " -- " + filename);
						}
						output.newLine();
						output.flush();

						return new Tuple2<String, String>(ouputFile + " some data", filename + " some data");
					}
				});
		videosRDD.collect();

		output.close();
//		WriteData.append("Task finished is: ", "finished.txt");
		sc.stop();

	}

}