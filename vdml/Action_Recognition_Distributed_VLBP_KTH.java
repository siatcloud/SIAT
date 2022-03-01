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
import org.bytedeco.javacpp.opencv_core.CvType;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.PointVector;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.indexer.UByteBufferIndexer;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.javacpp.opencv_objdetect.*;
import org.bytedeco.javacv.FFmpegFrameGrabber;
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



public class Action_Recognition_Distributed_VLBP_KTH {
	
	private static BufferedWriter output;

	public static void main(String data_url,final String result_url) throws Exception, IOException {
	
		//Need to change
	//System.setProperty("hadoop.home.dir", "C://Users//azher_dke//workspace1//com.siat.batchprocessing//data//hadooponwindows-master//hadooponwindows-master");
	
	//Need to change
	
	//System.setProperty("hadoop.home.dir", "data/winnn/winutils.exe");
	//SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.executor.instances", "2").set("spark.executor.memory", "512M").set("spark.executor.cores", "2").set("spark.driver.allowMultipleContexts", "true");
		//SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.executor.instances", "2").set("spark.executor.memory", "512M").set("spark.executor.cores", "2").set("spark.driver.allowMultipleContexts", "true");
		SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.driver.allowMultipleContexts", "true");
	JavaSparkContext sc = new JavaSparkContext(conf);
	
	int bufferSize = (16384 * 2 * 1024);
	//int bufferSize = 85000000;
	final FileWriter FW = (new FileWriter(result_url, true));
	output = new BufferedWriter(FW, bufferSize);
    //final BufferedWriter out = new BufferedWriter(new FileWriter(result_url));

    JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 4);
    
 
  
    JavaPairRDD<String, String> videosRDD = videos_.mapToPair(
        new PairFunction<Tuple2<String, PortableDataStream>,String,String>() {
            @Override
            public Tuple2<String, String> call(Tuple2<String, PortableDataStream> kv) throws Exception, IOException {  
            	//System.out.print("Hello");
            	
            	//final FileWriter FW = (new FileWriter(result_url, true));
            	//output = new BufferedWriter(FW);
            	
            	  int levelid = 0;
                String filename = kv._1.toString();
                //System.out.print(filename);
                String jj=filename;
                
                String reverse = new StringBuffer(jj).reverse().toString();
                String[] parts = reverse.split("\\/");
                        
                
                int len = parts[0].length();
                             
                String resultStr = jj.substring(6, jj.length());
                String dataid = parts[0].substring(4, len);
               
                
                String rever = new StringBuffer(dataid).reverse().toString();
                //System.out.println(rever);
               
               // int Video_dataid = Integer.valueOf(rever);
              
				if((Integer.valueOf(rever)>=1 && Integer.valueOf(rever)<=100))levelid=0;
                else if(Integer.valueOf(rever)>=101 && Integer.valueOf(rever)<=200)levelid=1;
                else if(Integer.valueOf(rever)>=201 && Integer.valueOf(rever)<=300)levelid=2;
                else if(Integer.valueOf(rever)>=301 && Integer.valueOf(rever)<=400)levelid=3;
                else if(Integer.valueOf(rever)>=401 && Integer.valueOf(rever)<=500)levelid=4;
                else if(Integer.valueOf(rever)>=501 && Integer.valueOf(rever)<=600)levelid=5;
                
				/*
				
				SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[2]").set("spark.driver.allowMultipleContexts", "true");
				JavaSparkContext sc2 = new JavaSparkContext(conf);
				int aaaa[][] = {{1,2}, {3,4}};

				JavaRDD<int[]> lines = sc2.parallelize(Arrays.asList(aaaa));
				//JavaRDD<String> lines = sc2.parallelize(Arrays.asList("pandas", "i like pandas"));*/
				
			//	output.write(levelid+" ");
				
				//SparkConf conff = new SparkConf().setAppName("sd").setMaster("local[2]").set("spark.driver.allowMultipleContexts", "true");
			//	JavaSparkContext sc2 = new JavaSparkContext(conff);
				

				/*JavaPairRDD<String, PortableDataStream> single_video = sc.binaryFiles(resultStr);
				
				JavaPairRDD<String, String> single_videosRDD = single_video.mapToPair(
				        new PairFunction<Tuple2<String, PortableDataStream>,String,String>() {
				            @Override
				            public Tuple2<String, String> call(Tuple2<String, PortableDataStream> single_videosRDD_kv) throws Exception, IOException { 
				            	String single_videosRDD_filename = single_videosRDD_kv._1.toString();
				                System.out.print(single_videosRDD_filename);
				                //String jj=single_videosRDD_filename;

				                
				                return new Tuple2<String, String> (single_videosRDD_filename, " "+"");
				            }
				        });   
				single_videosRDD.collect();
				  sc2.stop();*/
                
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);
                 
                long[] tmp2 = new long[16384];
                int frame_flag=0;
                int t3=1;
               // int iiii=0;
                IplImage img=null;
                
           

                
                try {      
                	  
                	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
             
                    grabber.start();
                    int frame_count = grabber.getLengthInFrames();
                    //System.out.println("Number of Frame: " + frame_count);
                   
                	   IplImage img1 = null;
                	   IplImage img2 = null;
                	   Mat matImage = null;  Mat matImage2= null;  Mat matImage3=null;
                	   Mat mat3= null; Mat mat2=null;
                	   
                    for(int k= 0; k<frame_count; k++){	
                    	
                    	
                    	img = converter.convert(grabber.grab());
                    	 if (img == null)
                    		 break;
                    	 else{
                    	
                    			
                    	if(frame_flag==0){
                    	img2 = img;
                    	 matImage3 = new Mat(img2);
                    	 mat3 = new Mat(matImage3.arrayHeight(),matImage3.arrayWidth());
                    	 cvtColor(matImage3, mat3, COLOR_RGB2GRAY);
                    	}        	

                    	else if(frame_flag==1){
                    	img1 = img;
                    	 matImage2 = new Mat(img1);
                    	 mat2 = new Mat(matImage2.arrayHeight(),matImage2.arrayWidth());
                    	   cvtColor(matImage2, mat2, COLOR_RGB2GRAY);
                    	}        	
                    	
                    
                    	else if(frame_flag>1 && frame_flag<frame_count){ 
            
                    	 matImage = new Mat(img);
                    	 Mat mat1 = new Mat(matImage.arrayHeight(),matImage.arrayWidth());
                    	
                    	   
                    	   cvtColor(matImage, mat1, COLOR_RGB2GRAY);
                    	   VLBP_4n poshogimage =  new VLBP_4n(mat3, mat2, mat1);
                    	   
                    	   long[] tmp1 = poshogimage.finaldes();

                      	  for(int iii=0;iii<4096;iii++) 
                      	  {
                      		  tmp2[iii]=tmp2[iii]+tmp1[iii];
                      		 
                      	  }
                    	   
                     	 
                    	   
                    	 mat3=mat2;
                    	 mat2=mat1;
                    	 
                    	 
                     }
                     frame_flag++;
                     }
                    }
                    
                    output.append(levelid+" ");
                    for(int iii=0;iii<4096;iii++)
                    {
               		output.append(t3+":" +tmp2[iii]+" ");
               		 t3++;
                    
                    }
                    output.newLine();
                  
                    
                    
                    //FW.close();
                    //output.close();
                    tmp2=null;
                    frame_flag=0;
                    }
                   catch (Exception e) {      
                   }
                
                
                
                return new Tuple2<String, String> (filename, filename+"");
            }
        });   
    
    //System.out.println(videosRDD.collect());
    videosRDD.collect();
    //videosRDD.saveAsTextFile("C://Users//azher_dke//workspace1//com.siat.batchprocessing//data//abcd.txt");
  
   /* FW.flush();
    FW.close();
    */
    output.close();
    sc.stop();
  
	}

	
	
}
