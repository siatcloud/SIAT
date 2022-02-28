package siat.dpl;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class Call_class_BS {
public static void main(String[] args) throws Exception, IOException {
		
		//Need to change
	System.setProperty("hadoop.home.dir", "/usr/bin/hadoop");
		long startTime = System.currentTimeMillis();
		
		BackgroundSubtraction_MOG.BackgroundSubtraction("/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/video_data_2/","/home/user/projects/siat_intigrated/src/Web Service Layer/src/data/dml/BGS_frame/");
		// TODO Auto-generated method stub
	
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}
