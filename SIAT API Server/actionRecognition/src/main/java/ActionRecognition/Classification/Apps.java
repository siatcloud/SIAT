package ActionRecognition.Classification;

import java.io.IOException;

import org.apache.spark.mllib.tree.model.RandomForestModel;

public class Apps {
	public static void main(String[] args) throws IOException {
		String trainModelPath = "C:/Users/AnwarAbir/kepler_workspace/Cluster/data/video/vgg16_trainModel.txt";
		String testpath = "C:/Users/AnwarAbir/kepler_workspace/Cluster/data/video/vgg16_testModel.txt";
		String outputPath = "C:/Users/AnwarAbir/kepler_workspace/Cluster/data/video/accuracy.txt";
		
		RandomForestModel model = Train_RandomForest.TrainData(trainModelPath, 20, 10, 6, 5);
		Test_RandomForest.classify(testpath, model, outputPath);
	}
}
