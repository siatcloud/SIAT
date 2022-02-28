package siat.dml;


import java.util.Arrays;
//$example on$
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
//$example off$

/**
 * <h1>SIAT Project</h1> <h2>Layer: Data Mining Layer</h2> <h3>@class Train_RandomForest</h3>
 * <p>
 *
 * @author Md Azher Uddin <azher@dke.khu.ac.kr>
 * @version 1.0
 * @Project This file is part of SIAT project.
 * @Description: </p>
 * <p>
 * <p>
 * @Description: This class is used to train the Random forest model based on the feature generated from the training video.
 * <p>
 * </p>
 * @since 2017-10-01
 **/

public class Train_RandomForest {

	public static double levs[][];
	public static int i;

	/**
	 * train the Random forest model based on the feature generated from the training videos.
	 *
	 * @param string data_url path of the feature data generated using feature extraction algorithm
	 * @author Md Azher Uddin <azher@dke.khu.ac.kr>
	 * @since 2017-10-01
	 */
	public static void trainRandomForest(String data_url) {
		// $example on$
		SparkConf sparkConf = new SparkConf().setAppName("JavaRandomForestClassificationExample").setMaster("local[2]").set("spark.ui.port", "4040" ).set("spark.driver.allowMultipleContexts", "true");
		//SparkConf sparkConf = new SparkConf().setAppName("JavaRandomForestClassificationExample").setMaster("local[4]").set("spark.driver.allowMultipleContexts", "true");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		// JavaSparkContext jsc2 = new JavaSparkContext(sparkConf);
		// Load and parse the data file.
		//String datapath = "data/mllib/wezman_data_4f_10.txt";
		//String datapath = "data/ucf/SIAT/training/ucf_data_lbp_AD_motion.txt";
		//System.out.println(data_url);
		String datapath = data_url;
		//String datapath2 = "data/mllib/output.csv";

		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), datapath).toJavaRDD();
		// Split the data into training and test sets (30% held out for testing)
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] { 1, 0 });



		// Test on separate test set 
		//JavaRDD<LabeledPoint> data2 = MLUtils.loadLibSVMFile(jsc.sc(), datapath2).toJavaRDD();
		//JavaRDD<LabeledPoint>[] splits2 = data2.randomSplit(new double[] { 1, 0 });
		//JavaRDD<LabeledPoint> testData = splits2[0];
		
		JavaRDD<LabeledPoint> trainingData = splits[0];		
		JavaRDD<LabeledPoint> testData = splits[1];

		// Train a RandomForest model.
		// Empty categoricalFeaturesInfo indicates all features are continuous.
		Integer numClasses = 11;
		HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		Integer numTrees = 100; // Use more in practice.
		String featureSubsetStrategy = "sqrt"; // Let the algorithm choose.
		String impurity = "gini";
		Integer maxDepth = 20;
		Integer maxBins = 34;
		Integer seed = 12345;



		final RandomForestModel model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
				numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins, seed);



		// Evaluate model on test instances and compute test error
		JavaPairRDD<Double, Double> predictionAndLabel = testData
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
					}
				});


		levs = new double[(int) testData.count()][2];
		Double testErr = 1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
			public Boolean call(Tuple2<Double, Double> pl) {

				levs[i][0] = pl._1();
				levs[i][1] = pl._2();
				i++;
				return !pl._1().equals(pl._2());
			}
		}).count() / testData.count();

		// System.out.println(pl.1());
		System.out.println("Learned classification forest model:\n" + model.toDebugString());
		System.out.println("------ Predicted Labels------");
		System.out.println(Arrays.deepToString(levs));

//		for (int i = 0; i < levs.length; i++) {
//			 if (levs[i][0] != levs[i][1])
//		System.out.println((levs[i][0] != levs[i][1] ? "-" : "+") + Arrays.toString(levs[i]));
//		}
		
	//	System.out.println("------ Number of Samples -------" + i);
	//	System.out.println("Accuracy of RF classifier: " + (1-testErr));

		// Save and load model
		model.save(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
		//RandomForestModel sameModel = RandomForestModel.load(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
		// $example off$

		jsc.stop();
		//System.out.print("Here");
	}
}