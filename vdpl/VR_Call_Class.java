package siat.dpl;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class VR_Call_Class {

	public static String video_retrieval(String data_path) throws Exception, IOException {
		
		//Need to change
		System.setProperty("hadoop.home.dir", "/usr/bin/hadoop");
		long startTime = System.currentTimeMillis();
		
		siat.dpl.Video_retrieval_Feature_Extraction.Feature_Extractor(data_path, "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VR_result/VR_KTH.txt");
//		Video_retrieval_similarityMeasure.similarityMeasure("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/KTH_VR_MP4_Query/", "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VR_result/VR_KTH.txt","/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VR_result/similarityResult.txt");
		siat.dpl.Video_retrieval_similarityMeasure.similarityMeasure("/home/user/projects/siat_intigrated/src/Web Service Layer/src/main/webapp/resources/batch_data/user_files/query_video/", "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VR_result/VR_KTH.txt","/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VR_result/similarityResult.txt");

		// TODO Auto-generated method stub
	
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);

		return  "/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/VR_result/similarityResult.txt";

	}
}
