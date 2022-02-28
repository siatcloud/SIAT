package siat.dml;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
 * <h1>SIAT Project</h1> <h2>Layer: Data Mining Layer</h2> <h3>@class Test_RandomForest</h3>
 * <p>
 *
 * @author Md Azher Uddin <azher@dke.khu.ac.kr>
 * @version 1.0
 * @Project This file is part of SIAT project.
 * @Description: </p>
 * <p>
 * <p>
 * @Description: This class is used to test the Random forest model based on the feature generated from the test videos.
 * <p>
 * </p>
 * @since 2017-10-01
 **/
public class Test_RandomForest {

    public static double levs[][] = new double[100][2];;
    public static int i = 0;

    /**
     * Test the Random forest model based on the feature generated from the test videos.
     *
     * @param string data_url path of the feature data generated using feature extraction algorithm from the training videos
     * @param string result_url path of the feature data generated using feature extraction algorithm from the test videos
     * @param string FILENAME path of the result generated using Random Forest Classifier from the test videos
     * @author Md Azher Uddin <azher@dke.khu.ac.kr>
     * @since 2017-10-01
     */
    public static void testRandomForest(String data_url, String result_url, String FILENAME) throws IOException {

        //BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME));
        FILENAME = FILENAME;
        // $example on$
        SparkConf sparkConf = new SparkConf().setAppName("JavaRandomForestClassificationExample").setMaster("local[2]").set("spark.ui.port", "44040" ).set("spark.driver.allowMultipleContexts", "true");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        //BufferedWriter out1 = new BufferedWriter(new FileWriter("data/SIAT/output.CSV"));
        //out1.write("Hello ");
        //final RandomForestModel sameModel = RandomForestModel.load(jsc.sc(), "target/tmp/myRandomForestClassificationModel5");
        // JavaSparkContext jsc2 = new JavaSparkContext(sparkConf);
        // Load and parse the data file.
        //String datapath = "data/mllib/wezman_data_4f_10.txt";
        //String datapath2 = "data/mllib/wezman_data_updated_4f_10_testing.txt";

        String datapath = data_url;
        String datapath2 = result_url;

        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), datapath).toJavaRDD();
        // Split the data into training and test sets (30% held out for testing)
        JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{1, 0});

        // Test on separate test set
        JavaRDD<LabeledPoint> data2 = MLUtils.loadLibSVMFile(jsc.sc(), datapath2).toJavaRDD();
        JavaRDD<LabeledPoint>[] splits2 = data2.randomSplit(new double[]{1, 0});
        JavaRDD<LabeledPoint> testData = splits2[0];

        JavaRDD<LabeledPoint> trainingData = splits[0];
        //JavaRDD<LabeledPoint> testData = splits[1];

        // Train a RandomForest model.
        // Empty categoricalFeaturesInfo indicates all features are continuous.
        Integer numClasses = 11;
        HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
        Integer numTrees = 100; // Use more in practice.
        String featureSubsetStrategy = "sqrt"; // Let the algorithm choose.
        String impurity = "gini";
        Integer maxDepth = 7;
        Integer maxBins = 32;
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

       // levs = new double[100][2];
        Double testErr = 1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
            public Boolean call(Tuple2<Double, Double> pl) {

                levs[i][0] = pl._1();
                levs[i][1] = pl._2();
                i++;
                return !pl._1().equals(pl._2());
            }
        }).count() / testData.count();

        // System.out.println(pl.1());
        //System.out.println("Learned classification forest model:\n" + model.toDebugString());
        //System.out.println("------ Pairs of actual and predicted labels------");


        //System.out.println(Arrays.deepToString(levs));
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME));
            for (int i = 0; i < testData.count(); i++) {
                //if (levs[i][0] != levs[i][1])
                //System.out.println((levs[i][0] != levs[i][1] ? "-" : "+") + Arrays.toString(levs[i]));
                if (levs[i][0] == 1.0) {
                    bw.write((levs[i][1] + "=Walking#"));
                }
                if (levs[i][0] == 2.0) {
                    bw.write((levs[i][1] + "=Running#"));
                }
                if (levs[i][0] == 3.0) {
                    bw.write((levs[i][1] + "=Jump#"));
                }
                if (levs[i][0] == 4.0) {
                    bw.write((levs[i][1] + "=Gallop sideways#"));
                }
                if (levs[i][0] == 5.0) {
                    bw.write((levs[i][1] + "=Bend#"));
                }
                if (levs[i][0] == 6.0) {
                    bw.write((levs[i][1] + "=One-hand wave#"));
                }
                if (levs[i][0] == 7.0) {
                    bw.write((levs[i][1] + "=Two-hands wave#"));
                }
                if (levs[i][0] == 8.0) {
                    bw.write((levs[i][1] + "=Jump in place#"));
                }
                if (levs[i][0] == 9.0) {
                    bw.write((levs[i][1] + "=Jumping Jack#"));
                }
                if (levs[i][0] == 10.0) {
                    bw.write((levs[i][1] + "=Skip#"));
                }
                //bw.write(levs[i][1] +"="+ levs[i][0]);
                bw.newLine();

            }
            bw.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        //System.out.println("------ Number of Samples -------" + i);
        //System.out.println("Test Error: " + testErr);

        // Save and load model
        //model.save(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
        //RandomForestModel sameModel = RandomForestModel.load(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
        // $example off$

        jsc.stop();
    }
}
