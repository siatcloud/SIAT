package com.siat.batchprocessing.com.siat.batchprocessing;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class Call_class_BS {
public static void main(String[] args) throws Exception, IOException {
		
		//Need to change
		System.setProperty("hadoop.home.dir", "C://Users//azher_dke//workspace1//com.siat.batchprocessing//data//hadooponwindows-master//hadooponwindows-master");
		long startTime = System.currentTimeMillis();
		
		String dateTime= java.time.LocalDateTime.now().toString();
		
		 String[] Result_filename2= dateTime.split("\\.");
		 String Result_filename = Result_filename2[0];
		 
		 String replaceString=Result_filename.replaceAll(":","_");
		
		
		
		HistogramEqualization.Equalization("data/HisEq_Dataset/","data/Equalize_frame/");
		
		//motionFeature.Denseopticalflow("data/HisEq_Dataset/","data/Distributed_training_VLGTP_kTH_test"+replaceString+".dat");
		// TODO Auto-generated method stub
	
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}
