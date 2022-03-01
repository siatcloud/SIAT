package VideoProcessing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.input.PortableDataStream;
import org.bytedeco.javacv.FrameGrabber.Exception;

import scala.Tuple2;

public class sparkJob {
	private static BufferedWriter output;

	@SuppressWarnings("resource")
	public static void job(String data_url, final String result_url, final String dataset_path) throws IOException {
		SparkConf conf = new SparkConf().setAppName("Spark").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		int feature_size = 16384 * 1024;
		int bufferSize = (feature_size * 1024);
		output = new BufferedWriter(new FileWriter(result_url, false));

		// Read data into Spark RDD and partition them, here we use 6, we can further
		// partition using distinct
		JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 6).distinct(4);

		// Map RDD for distribute computation using mapToPair
		@SuppressWarnings("serial")
		JavaPairRDD<String, String> videosRDD = videos_
				.mapToPair(new PairFunction<Tuple2<String, PortableDataStream>, String, String>() {
					public Tuple2<String, String> call(Tuple2<String, PortableDataStream> kv)
							throws Exception, IOException, NoSuchMethodException, SecurityException {

						// read current data which comes randomly
						String filename = kv._1.toString();
						// call video to frame conversion function
						frameExtraction.extract(filename, output, dataset_path);

						return new Tuple2<String, String>(filename, filename + "");
					}
				});
		videosRDD.collect();
		output.close();
		sc.stop();
	}

	@SuppressWarnings("resource")
	public static void genderJob(String data_url, final String result_url, final String dataset_path)
			throws IOException {
		SparkConf conf = new SparkConf().setAppName("Spark").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		int feature_size = 16384 * 1024;
		int bufferSize = (feature_size * 1024);
		output = new BufferedWriter(new FileWriter(result_url, false));

		// Read data into Spark RDD and partition them, here we use 6, we can further
		// partition using distinct
		JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 6).distinct(4);

		// Map RDD for distribute computation using mapToPair
		@SuppressWarnings("serial")
		JavaPairRDD<String, String> videosRDD = videos_
				.mapToPair(new PairFunction<Tuple2<String, PortableDataStream>, String, String>() {
					public Tuple2<String, String> call(Tuple2<String, PortableDataStream> kv)
							throws Exception, IOException, NoSuchMethodException, SecurityException {

						// read current data which comes randomly
						String filename = kv._1.toString();
						// call video to frame conversion function
						frameExtractionGender.extract(filename, output, dataset_path);

						return new Tuple2<String, String>(filename, filename + "");
					}
				});
		videosRDD.collect();
		output.close();
		sc.stop();
	}
}
