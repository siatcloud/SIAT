package siat.dpl;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;




public class Distributed_cal_classes {

	public static void main(String[] args) throws Exception, IOException {
		
		//Need to change
		System.setProperty("hadoop.home.dir", "/usr/bin/hadoop");
		long startTime = System.currentTimeMillis();
		
		//System.out.println(java.time.LocalDateTime.now());  
		String dateTime= java.time.LocalDateTime.now().toString();

		 String[] Result_filename2= dateTime.split("\\.");
		 String Result_filename = Result_filename2[0];
		 
		 String replaceString=Result_filename.replaceAll(":","_");
	//System.out.println(Result_filename);
		
		// TODO Auto-generated method stub
		//ActionRecognition_FeatureExtraction.main("data/video_data/", "data/training_LBP234.txt");
		//Action_Recognition_VLBP_KTH.main("data/kth_dataset_AIP/", "data/training_VLBP_kTH_AIP1.txt");
	///Distributed_VideoSplit.main("data/kth_dataset_AIP3/", "data/Distibuted_kth_dataset_AIP/");
	///Action_Recognition_Distributed_VLBP_Splitted_KTH.main("data/Distibuted_kth_dataset_AIP/", "data/Distributed_training_VLGTP_Splitted_kTH_test20180724.txt");
		siat.vdpl.DistributedFeatureExtractor.featureExtraction("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/video_data/", "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VLBP_TestingFeature"+replaceString+".dat");
		//Action_Recognition_VLBP.main("data/video_data/", "data/training_VLBP234.txt");
		//ActionRecognition_FeatureExtraction_testing.main("data/testing_video/", "data/action_recognition_testing_LBP.txt");
		//ActionRecognition_FeatureExtraction_testing.main("data/testing_video2/", "data/action_recognition_testing_LBP.txt");
///Train_RandomForest.main("data/Distributed_training_VLGTP_kTH_test2018-08-02T17_01_36.dat");
		//Train_RandomForest.main("data/training_VLBP234.txt");
		
		//Test_RandomForest.main("data/training_LBP.txt", "data/action_recognition_testing_LBP.txt", "data/result002.txt");
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}

}
