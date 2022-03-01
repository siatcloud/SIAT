package VideoProcessing.classification;

//$example on$
import java.util.HashMap;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

//$example off$

public class randomForestClassifier {

	public static double levs[][];
	public static int i;

	@SuppressWarnings("resource")
	public static void main(String data_url, int no_class) {
//		public static void main(String data_url, String testData_url) {
		System.setProperty("hadoop.home.dir", "C:\\Hadoop\\hadooponwindows-master");

		SparkConf sparkConf = new SparkConf().setAppName("Random Forest").setMaster("local[*]");
		JavaSparkContext context = new JavaSparkContext(sparkConf);

		/*
		 * ********************************** **********************************
		 * ******** Random Data ************* **********************************
		 * **********************************
		 */

		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(context.sc(), data_url).toJavaRDD();
		// Split the data into training and test sets (30% held out for
//		 testing)
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] { 0.8, 0.2 });

		JavaRDD<LabeledPoint> trainingData = splits[0];
		JavaRDD<LabeledPoint> testData = splits[1];

		/*
		 * ********************************** **********************************
		 * ******** Random Data ************* **********************************
		 * **********************************
		 */

		// Train a RandomForest model.
		// Empty categoricalFeaturesInfo indicates all features are continuous.
		Integer numClasses = no_class;
		HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		Integer numTrees = 100; // Use more in practice.
		String featureSubsetStrategy = "sqrt"; // Let the algorithm choose.
		String impurity = "gini";
		Integer maxDepth = 25;
		Integer maxBins = 30;
		Integer seed = 12345;

		// model training
		final RandomForestModel model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
				numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins, seed);

		// Evaluate model on test instances and compute test error
		@SuppressWarnings("serial")
		JavaPairRDD<Double, Double> predictionAndLabel = testData
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
					}
				});

		levs = new double[(int) testData.count()][2];
		@SuppressWarnings("serial")
		Double testErr = 1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
			public Boolean call(Tuple2<Double, Double> pl) {

				levs[i][0] = pl._1();
				levs[i][1] = pl._2();
				i++;
				return !pl._1().equals(pl._2());
			}
		}).count() / testData.count();

		System.out.println("------ Number of Samples -------");
		System.out.println("Accuracy of RF classifier: " + (1 - testErr));
		System.out.println("Error: " + testErr);

		context.stop();

	}
}