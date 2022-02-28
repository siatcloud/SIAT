package ActionRecognition.Algorithms;

import java.io.IOException;

import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.bytedeco.javacv.FrameGrabber.Exception;

import ActionRecognition.Classification.Test_RandomForest;
import ActionRecognition.Classification.Train_RandomForest;

/**
 * Hello world!
 *
 */
public class Apps {
	public static void main(String[] args) throws Exception, IOException {
		// Need to change
		System.setProperty("hadoop.home.dir", "C:\\Hadoop\\hadooponwindows-master\\");

//		String dataPath = "hdfs:///user/siat/data/";
		String dataPath = "data/kth_dataset_AIP/";
		String featureDataPath = "data/feature/";
		String trainDataPath = dataPath + "train/";
		String testDataPath = dataPath + "test/";
		String trainFeature = featureDataPath + "kth_trainModel.txt";
		String testFeature = featureDataPath + "kth_testModel.txt";
		String accuracyOutputPath = "data/output/randomForest.txt";
		int number_class = Integer.parseInt(args[0]);
		RandomForestModel model;

		callSpark.featureExtraction(trainDataPath, trainFeature, true);
		callSpark.featureExtraction(testDataPath, testFeature, false);
		
//		callClass.call(trainDataPath, trainFeature);
//		callClass.call(testDataPath, testFeature);

		// parameter(trainFeature_data, number_of_class, number_of_tree, Depth,
		// number_of_bins)
		model = Train_RandomForest.TrainData(trainFeature, number_class, 200, 20, 24);
		Test_RandomForest.classify(testFeature, model, accuracyOutputPath);
	}
}