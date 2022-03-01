package com.siat.batchprocessing.com.siat.batchprocessing;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkFiles;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.input.PortableDataStream;
import org.apache.spark.mllib.linalg.distributed.BlockMatrix;
import org.apache.spark.sql.SQLContext;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacpp.opencv_core.CvType;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.PointVector;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.indexer.UByteBufferIndexer;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.javacpp.opencv_objdetect.*;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.json4s.reflect.ObjectDescriptor;

import static org.bytedeco.javacpp.opencv_imgproc.*;
import scala.Array;
import scala.Tuple2;

import java.util.*;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import breeze.linalg.Vector;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;



public class Distributed_VideoSplit {
	
	

	public static void main(String data_url,final String result_url) throws Exception, IOException {
	
		//Need to change
	//System.setProperty("hadoop.home.dir", "C://Users//azher_dke//workspace1//com.siat.batchprocessing//data//hadooponwindows-master//hadooponwindows-master");
	
	//Need to change
	
	//System.setProperty("hadoop.home.dir", "data/winnn/winutils.exe");
	//SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.executor.instances", "2").set("spark.executor.memory", "512M").set("spark.executor.cores", "2").set("spark.driver.allowMultipleContexts", "true");
		//SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.executor.instances", "2").set("spark.executor.memory", "512M").set("spark.executor.cores", "2").set("spark.driver.allowMultipleContexts", "true");
		SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.driver.allowMultipleContexts", "true");
	JavaSparkContext sc = new JavaSparkContext(conf);
	

    
    //final BufferedWriter out = new BufferedWriter(new FileWriter(result_url));

    JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 10);
    
  
    JavaPairRDD<String, String> videosRDD = videos_.mapToPair(
        new PairFunction<Tuple2<String, PortableDataStream>,String,String>() {
            @Override
            public Tuple2<String, String> call(Tuple2<String, PortableDataStream> kv) throws Exception, IOException, org.bytedeco.javacv.FrameRecorder.Exception {  
            	//System.out.print("Hello");
            	
     
                String filename = kv._1.toString();
              //  System.out.print(filename);
                String jj=filename;
                
                String reverse = new StringBuffer(jj).reverse().toString();
                String[] parts = reverse.split("\\/");
                        
                
                int len = parts[0].length();
                             
                String resultStr = jj.substring(6, jj.length());
                String dataid = parts[0].substring(4, len);
               
                
                String rever = new StringBuffer(dataid).reverse().toString();
                //System.out.println(rever);
               
               // int Video_dataid = Integer.valueOf(rever);
              
				
				
				
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);
             
          

                IplImage img=null;
                
           

                
                try {      
                	  
                	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
             
                    grabber.start();
                    int frame_count = grabber.getLengthInFrames();
                    //System.out.println("Number of Frame: " + frame_count);
                   
                	   
                	  int Num_of_Split = frame_count / 150;
                	  int updated_Num_of_Split = Num_of_Split;
                	  int remaining_Frame = frame_count - Num_of_Split*150;
                	  
                	  int counter = 0;
                	  
                	  if(remaining_Frame>50){
                		  updated_Num_of_Split = Num_of_Split+1;}
                	  
                	  //System.out.println(updated_Num_of_Split);
                	  
                	  
                	  for(int kk=0; kk < Num_of_Split; kk++){
                	  
                		String videoFilePath = result_url + rever + "_" + kk + ".avi";
                		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(videoFilePath, 160, 120);
                		  
                		
                		   	//recorder.setVideoCodec(grabber.getVideoCodec());
                			//recorder.setVideoCodec(28); //AV_CODEC_ID_H264 = 28,
                			recorder.setVideoCodec(13); //AV_CODEC_ID_MPEG4 = 13,
                			//recorder.setVideoCodec(5); //AV_CODEC_ID_H263 = 5,
                		   	
                			recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
                	        recorder.setVideoBitrate(25 * 160 * 120);
                	        recorder.setFrameRate(25);
                	        //recorder.setVideoQuality(0.1);
                	        recorder.setFormat("avi");
                	        recorder.start();
                	      
                      	 // counter++;
                      	  int limitofFrame = 150;
                      	  /*
                      	  if(counter == updated_Num_of_Split){
                      		limitofFrame = remaining_Frame-1;
                      	  }
                      	  */
                	        
                		for(int k= 0; k<limitofFrame; k++){	
                        			img = converter.convert(grabber.grab());
			                    	Frame frame = converter.convert(img);
			                    	recorder.record(frame);
                		}
                		
                		recorder.stop();
                }
               
                   
                    }
                   catch (Exception e) {      
                   }
    
               
                
                
                return new Tuple2<String, String> (filename, filename+"");
            }
        });   
    
    //System.out.println(videosRDD.collect());
    videosRDD.collect();

    sc.stop();
  
	}

	
	
}
