package siat.dml;

import java.io.IOException;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.bytedeco.javacv.FrameGrabber.Exception;
import siat.dao.BatchDataDao;
import siat.entities.BatchDs;

/**
 * <h1>SIAT Project</h1> <h2>Layer: Data Processing Layer and Data Mining Layer</h2> <h3>@class cal_classes</h3>
 * <p>
 *
 * @author Md Azher Uddin <azher@dke.khu.ac.kr>
 * @version 1.0
 * @Project This file is part of SIAT project.
 * @Description: </p>
 * <p>
 * <p>
 * @Description: This class is used to call the feature extraction classes for the training data, testing data and Random Forest classification model for training the feature vector and testing the feature vector.
 * <p>
 * </p>
 * @since 2017-10-01
 **/


public class cal_classes {

	public static String action_recognition1(String dataPath) {
		
		//Need to change
		System.setProperty("hadoop.home.dir", "/usr/bin/hadoop");
//		System.setProperty("hadoop.home.dir", "/home/cloudera/ideaProjects/SIAT_old/src/Web Service Layer/src/data/dml/hadooponwindows-master/hadooponwindows-master");

		// TODO Auto-generated method stub
		try {

//			BatchDataDao bsBatchDataDao = new BatchDataDao();
//			BatchDs batchDs = bsBatchDataDao.getBatchDataSourceById(1);
//			String path = batchDs.getFilePath();

			//siat.dpl.FeatureExtractor.FeatureExtractor("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/video_data/", "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VLBP_TrainingFeature.txt");
//			siat.dpl.FeatureExtractor_testData.featureExtractor_testData("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/video_data_2/", "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VLBP_TestingFeature.txt");

			siat.dpl.FeatureExtractor_testData.featureExtractor_testData(dataPath, "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VLBP_TestingFeature.txt");

			//siat.dml.Train_RandomForest.trainRandomForest("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VLBP_TrainingFeature.txt");
			siat.dml.Test_RandomForest.testRandomForest("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VLBP_TrainingFeature.txt", "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VLBP_TestingFeature.txt", "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/ResultbasedVLBP.txt");

		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
//		Train_RandomForest.featureExtractor("/home/cloudera/ideaProjects/SIAT_old/src/Web Service Layer/src/data/dml/training_LBP.txt");

//		Test_RandomForest.featureExtractor("/home/cloudera/ideaProjects/SIAT_old/src/Web Service Layer/src/data/dml/training_LBP.txt", "/home/cloudera/ideaProjects/SIAT_old/src/Web Service Layer/src/data/dml/action_recognition_testing_LBP.txt", "data/result001.txt");


	return "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/ResultbasedVLBP.txt";
	}



}
