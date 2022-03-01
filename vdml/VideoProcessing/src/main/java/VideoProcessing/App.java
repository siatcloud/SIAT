package VideoProcessing;

import java.io.IOException;
import java.util.Scanner;

import VideoProcessing.classification.randomForestClassifier;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		// Need to change
		System.setProperty("hadoop.home.dir", "C:\\Hadoop\\hadooponwindows-master\\");

		
		System.out.println("Enter integer number for the following \n"
				+ "1:- Action Recognition\n"
				+ "2:- Gender Classification");
		Scanner input = new Scanner(System.in);
		int cat_flag = input.nextInt();
		

		
		if (cat_flag == 1) {
			System.out.println("Action Rdcognition Started -----");
			
			String dataset_name = "KTH"; // name of the dataset
			String path = "C:/Users/AnwarAbir/kepler_workspace/Abir/data/kth_dataset_AIP"; // dataset path
			String result_url = "data/action_data.txt"; // output url used for feature extraction 
			String outputPath = "data/action_classification.txt"; // classification output
			int classes = 6;
			
			// call spark job
//			sparkJob.job(path, result_url, dataset_name);

//			Classifier 
			randomForestClassifier.main(result_url, classes);
		} else if (cat_flag == 2) {
			System.out.println("Gender Classification Started -----");
			String dataset_name = "gender"; // name of the dataset
			String path = "data/videos/"; // dataset path
			String result_url = "data/face_data.txt"; // output url used for feature extraction 
			String outputPath = "data/gender_classification.txt"; // classification output
			int classes = 2;
			sparkJob.genderJob(path, result_url, dataset_name);

			randomForestClassifier.main(result_url, classes);
		}
	}
}
