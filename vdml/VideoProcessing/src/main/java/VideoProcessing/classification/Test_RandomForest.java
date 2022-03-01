package VideoProcessing.classification;

import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

//$example off$

public class Test_RandomForest {

	public static void classify(String data_url, final RandomForestModel model, String outputPath)
			throws IOException {

		// $example on$
		SparkConf sparkConf = new SparkConf()
				.setAppName("RandomForest").setMaster("local[*]");
//				.setMaster("yarn")
//				.set("spark.driver.allowMultipleContexts", "true");
		@SuppressWarnings("resource")
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		String datapath = data_url;

		// Test on separate test set
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), datapath)
				.toJavaRDD();
		JavaRDD<LabeledPoint>[] splits = data
				.randomSplit(new double[] { 1, 0 });
		JavaRDD<LabeledPoint> testData = splits[0];

		// Evaluate model on test instances and compute test error
		@SuppressWarnings("serial")
		JavaPairRDD<Double, Double> predictionAndLabel = testData
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(model.predict(p
								.features()), p.label());
					}
				});


		@SuppressWarnings("serial")
		double testErr = 1.0
				* predictionAndLabel.filter(
						new Function<Tuple2<Double, Double>, Boolean>() {
							public Boolean call(Tuple2<Double, Double> pl) {
								return !pl._1().equals(pl._2());
							}
						}).count() / testData.count();

		System.out.println("Learned classification forest model:\n"
				+ model.toDebugString());
		System.out.println("------ Pairs of actual and predicted labels------");
		

//		System.out.println("########################------ Number of Samples -------########################");
		System.out.println("Test Error: " + testErr);
		System.out.println("Test Accuracy: " + (1 - testErr));
//		predictionAndLabel.saveAsTextFile("hdfs:///user/siat/data/output.txt");
		
//		WriteData.append("Test Error: " + testErr, "randomForest.txt");
//		WriteData.append("\n", "randomForest.txt");
//		WriteData.append("Test Accuracy: " + (1 - testErr) + "", "randomForest.txt");
		jsc.stop();
	}
}
