package VideoProcessing.classification;

//$example on$
import java.util.HashMap;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;

//$example off$

public class Train_RandomForest {

	public static double levs[][];
	public static int i;

	@SuppressWarnings("resource")
	public static RandomForestModel TrainData(String data_url,
			int number_class, int number_tree, int depth, int bins) {
		System.setProperty("hadoop.home.dir",
				"C:\\Hadoop\\hadooponwindows-master");

		SparkConf sparkConf = new SparkConf()
				.setAppName("Random Forest").setMaster("local[*]");
		JavaSparkContext context = new JavaSparkContext(sparkConf);

		// Load and parse the data file.
		String trainingDataPath = data_url;
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(context.sc(),
				trainingDataPath).toJavaRDD();

		// Split the data into training and test sets (30% held out for testing)
		JavaRDD<LabeledPoint>[] splitTrainData = data.randomSplit(new double[] {
				1, 0 });
		JavaRDD<LabeledPoint> trainingData = splitTrainData[0];

		// Train a RandomForest model.
		// Empty categoricalFeaturesInfo indicates all features are continuous.
		Integer numClasses = number_class;
		HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		Integer numTrees = number_tree; // Use more in practice.
		String featureSubsetStrategy = "sqrt"; // Let the algorithm choose.
		String impurity = "gini";
		Integer maxDepth = depth;
		Integer maxBins = bins;
		Integer seed = 12345;

		final RandomForestModel model = RandomForest.trainClassifier(
				trainingData, numClasses, categoricalFeaturesInfo, numTrees,
				featureSubsetStrategy, impurity, maxDepth, maxBins, seed);

		context.stop();
		// System.out.print("Here");
		return model;
	}
}