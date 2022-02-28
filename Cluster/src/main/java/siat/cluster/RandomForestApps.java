package siat.cluster;

import java.util.HashMap;

import scala.Tuple2;

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

public class RandomForestApps {
	public static double levs[][];
	public static int i=0;
	@SuppressWarnings({ "serial", "resource" })
	public static void main(String[] arg) {
		
		System.setProperty(
				"hadoop.home.dir",
				"C://Users//Anwar Abir//Documents//My_project_kepler//Abir//data//hadooponwindows-master");

		SparkConf sparkConf = new SparkConf()
		.setAppName("JavaRandomForestClassificationExample")
		.setMaster("yarn-cluster")
//		.setMaster("local[*]")
		.set("spark.driver.allowMultipleContexts", "true");
		JavaSparkContext context = new JavaSparkContext(sparkConf);

		// Load and parse the data file.
//		String trainingDataPath = "data/histoLBP.txt";
//		String testDataPath = "data/histoLBP_Test.txt";
//		String trainingDataPath = "data/histoLTP.txt";
//		String testDataPath = "data/histoLTP_Test.txt";
//		String trainingDataPath = "data/histoLDP.txt";
//		String testDataPath = "data/histoLDP_Test.txt";
//		String trainingDataPath = "data/histoLDTP.txt";
//		String testDataPath = "data/histoLDTP_Test.txt";
//		String trainingDataPath = "data/histoDLDP.txt";
//		String testDataPath = "data/histoDLDP_Test.txt";
//		String trainingDataPath = "data/histoDLDTP.txt";
//		String testDataPath = "data/histoDLDTP_Test.txt";
//		String trainingDataPath = "data/histoEDLDTP.txt";
//		String testDataPath = "data/histoEDLDTP_Test.txt";
//		String trainingDataPath = "data/histoLTTP.txt";
//		String testDataPath = "data/histoLTTP_Test.txt";
//		String datapath = "data/histoLDP_full.txt";
		
//		String datapath = "data/histoLBP_full.txt";

//		String datapath = "data/histoLTP_full.txt";
//		String datapath = "data/histoLTTP_full.txt";
//		String datapath = "data/histoLDTP_full.txt";
//		String datapath = "data/histoLDTP_full.txt";

//		String datapath = "data/histoLDTP_CNN_full_no_avg.txt";
//		String datapath = "data/histoDLDTP_full.txt";
//		String datapath = "data/histoEDLDTP_full.txt";
//		String datapath = "data/histoComEDLDTP_full.txt";
//		String datapath = "data/histoComAvgEDLDTP_full.txt";
		
//		String datapath = "data/histoLDP_Colour_full.txt";
//		String datapath = "data/histoLBP_Colur_full_avg.txt";
//		String datapath = "data/histoLBP_CNN_full.txt";
//		String datapath = "data/histoLDTP_CNN_full.txt";
//		String datapath = "data/histoLDTP_Colour_pool.txt";
//		String datapath = "data/histoLBP_colour_withGray.txt";
		
//		String datapath = "data/histoLBP_colour_KTH-TIPS2b.txt";
//		String datapath = "data/histoLDP_Colour_KTH-TIPS2b.txt";
//		String datapath = "data/histoLDTP_color_KTH-TIPS2b.txt";
//		String datapath = "data/histoLTP_color_KTH-TIPS2b.txt";
//		String datapath = "data/histoLBP_KTH-TIPS2b.txt";
//		String datapath = "data/histoLDP_KTH-TIPS2b.txt";
//		String datapath = "data/histoLDTP_KTH-TIPS2b.txt";
		String datapath = "hdfs:///data/abir/histoLDTP_KTH-TIPS2b.txt";
		
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(context.sc(), datapath).toJavaRDD();
		// Split the data into training and test sets (30% held out for testing)
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.7, 0.3});
		JavaRDD<LabeledPoint> trainingData = splits[0];
		JavaRDD<LabeledPoint> testData = splits[1];
		
		

//		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(context.sc(),
//				trainingDataPath).toJavaRDD();
//		JavaRDD<LabeledPoint> T_Data = MLUtils.loadLibSVMFile(context.sc(),
//				testDataPath).toJavaRDD();
//
//		// Split the data into training and test sets (30% held out for testing)
//		JavaRDD<LabeledPoint>[] splitTrainData = data.randomSplit(new double[] {
//				1, 0 });
//		JavaRDD<LabeledPoint> trainingData = splitTrainData[0];
//
//		JavaRDD<LabeledPoint>[] splitTestData = T_Data
//				.randomSplit(new double[] { 1, 0 });
//		JavaRDD<LabeledPoint> testData = splitTestData[0];

		// Train a RandomForest model.
		// Empty categoricalFeaturesInfo indicates all features are continuous.
		Integer numClasses = 11;
		HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		Integer numTrees = 200; // Use more in practice.
		String featureSubsetStrategy = "auto"; // Let the algorithm choose.
		String impurity = "gini";
		Integer maxDepth = 20;
		Integer maxBins = 34;
		Integer seed = 12345;

		final RandomForestModel model = RandomForest.trainClassifier(
				trainingData, numClasses, categoricalFeaturesInfo, numTrees,
				featureSubsetStrategy, impurity, maxDepth, maxBins, seed);

		JavaPairRDD<Double, Double> predictionAndLabel = testData
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
					@Override
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(model.predict(p
								.features()), p.label());
					}
				});

		levs = new double[(int) testData.count()][2];
		Double testErr = 1.0
				* predictionAndLabel.filter(
						new Function<Tuple2<Double, Double>, Boolean>() {
							@Override
							
							public Boolean call(Tuple2<Double, Double> pl) {
								//levs[i][0] = pl._1();
								//levs[i][1] = pl._2();
								//System.out.println(levs[i][0]+" " + levs[i][1]);
								//i++;
								return !pl._1().equals(pl._2());
							}
						}).count() / testData.count();
		System.out.println("Test Error: " + testErr);
		System.out.println("Accuracy: " + (1 - testErr));
//		System.out.println("Learned classification forest model:\n"
//				+ model.toDebugString());

		// Save and load model
//		model.save(context.sc(), "data/tmp/myRandomForestClassificationModel");
//		RandomForestModel sameModel = RandomForestModel.load(context.sc(),
//				"data/tmp/myRandomForestClassificationModel");

	}

}
