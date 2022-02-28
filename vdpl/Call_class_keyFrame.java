package siat.dpl;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class Call_class_keyFrame {
	public static void main(String[] args) throws Exception, IOException {
		
		//Need to change
		System.setProperty("hadoop.home.dir", "/usr/bin/hadoop");
		long startTime = System.currentTimeMillis();
		
		KeyFrame_Extraction.KeyFrameExtractor("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/video_data_2/","/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/Key_frame/");

		//KeyFrame_Extraction_Hog.KeyFrameExtractor("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/video_data_2/","/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/Key_frame/");

		// TODO Auto-generated method stub
	
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}
