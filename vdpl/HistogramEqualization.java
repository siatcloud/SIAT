package com.siat.batchprocessing.com.siat.batchprocessing;


import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.input.PortableDataStream;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.FrameGrabber.Exception;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import scala.Tuple2;

import org.bytedeco.javacpp.opencv_video.*;



public class HistogramEqualization {
public static void Equalization(String data_url, final String result_url) throws Exception, IOException {
		
		//Need to change
	//System.setProperty("hadoop.home.dir", "C://Users//azher_dke//workspace1//com.siat.batchprocessing//data//hadooponwindows-master//hadooponwindows-master");
	
	//Need to change
	
	//System.setProperty("hadoop.home.dir", "data/winnn/winutils.exe");
	SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.executor.instances", "2").set("spark.executor.memory", "512M").set("spark.executor.cores", "2").set("spark.driver.allowMultipleContexts", "true");
	JavaSparkContext sc = new JavaSparkContext(conf);

    JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 4);
    

    

    JavaPairRDD<String, String> videosRDD = videos_.mapToPair(
        new PairFunction<Tuple2<String, PortableDataStream>,String,String>() {
            @Override
            public Tuple2<String, String> call(Tuple2<String, PortableDataStream> kv) throws Exception, IOException {  
    
       
                 String filename = kv._1.toString();

                 String jj=filename;
                 

    String reverse = new StringBuffer(jj).reverse().toString();

    String[] parts = reverse.split("\\/");

 
    
    int len = parts[0].length();

    
    String resultStr = jj.substring(6, jj.length());
    String dataid = parts[0].substring(4, len);
    

    
    
    String rever = new StringBuffer(dataid).reverse().toString();

    //int Video_dataid = Integer.valueOf(rever);

    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);

   
    //BackgroundSubtractorMOG2 backgroundSubtractorMOG = createBackgroundSubtractorMOG2(100, 10 , false); 
    

    int count=0;
    IplImage img;
    try {      
    	  
    	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

        grabber.start();
        int frame_count = grabber.getLengthInFrames();

    	   IplImage img1;
    	   IplImage img2;
    	   Mat matImage = new Mat();  Mat matImage2= new Mat();  Mat matImage3=new Mat();
    	   Mat mat3 = new Mat(); Mat mat2 = new Mat();
    	    int frame_flag=0;
    	   
        for(int k= 0; k<frame_count; k++){	
        	

        	img = converter.convert(grabber.grab());

       	
        			
        	
        	img2 = img;
        	 matImage3 = new Mat(img2);
        	 mat3 = new Mat(matImage3.arrayHeight(),matImage3.arrayWidth());
        	// Mat fgMask = new Mat(matImage3.arrayHeight(),matImage3.arrayWidth());
        	 
        	 cvtColor(matImage3, mat3, COLOR_RGB2GRAY);
        	 //cvtColor(mat3, mat2, COLOR_GRAY2RGB);
        	 
        	
        	 
        	 
        	   IplImage sourceSingle = cvCreateImage(cvGetSize(img), IPL_DEPTH_8U, 1);
        	   
        	   
        	   Mat fgMask = new Mat(sourceSingle);
        	   
        	   equalizeHist(mat3, fgMask);
        	 
        	   //Sobel(mat3, fgMask, CV_16S, 1, 1);
        	   //Sobel(mat3, fgMask, CV_16S, 0, 1, 3, 1, 0, BORDER_DEFAULT);
        	   
        	   
        	   //backgroundSubtractorMOG.apply(mat2, fgMask);        
        	   IplImage img5 = new IplImage(fgMask);
        	   //fgMask.release();
        	   //mat3.close();
        	   //fgMask.close();
        	   //backgroundSubtractorMOG.close();
        	 
      	   cvSaveImage(result_url+rever+"_"+count + ".jpg", img5);
      	   
      	   
      	   count++;
   

        }
       
       
        frame_flag=0;
        }
       catch (Exception e) {      
       }
    
  
    

            return new Tuple2<String, String> (filename, filename+"");
        }
    });   

//System.out.println(videosRDD.collect());
videosRDD.collect();

	}

}

