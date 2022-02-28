package siat.dpl;

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



public class DistributedFeatureExtractor {
	
	private static BufferedWriter output;

	public static void featureExtraction(String data_url,final String result_url) throws Exception, IOException {
	
			SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.driver.allowMultipleContexts", "true");
	JavaSparkContext sc = new JavaSparkContext(conf);
	
	int bufferSize = (16384 * 2 * 1024);

	final FileWriter FW = (new FileWriter(result_url, true));
	output = new BufferedWriter(FW, bufferSize);


    JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 2);
    
  
    JavaPairRDD<String, String> videosRDD = videos_.mapToPair(
        new PairFunction<Tuple2<String, PortableDataStream>,String,String>() {
            @Override
            public Tuple2<String, String> call(Tuple2<String, PortableDataStream> kv) throws Exception, IOException {  

            	
            	  int levelid = 0;
                String filename = kv._1.toString();

                String jj=filename;
                
                String reverse = new StringBuffer(jj).reverse().toString();
                String[] parts = reverse.split("\\/");
                        
                
                int len = parts[0].length();
                             
                String resultStr = jj.substring(5, jj.length());
                String dataid = parts[0].substring(4, len);
               
                
                String rever = new StringBuffer(dataid).reverse().toString();

              
				if((Integer.valueOf(rever)>=1 && Integer.valueOf(rever)<=7))levelid=1;
                else if(Integer.valueOf(rever)>=8 && Integer.valueOf(rever)<=14)levelid=2;
                else if(Integer.valueOf(rever)>=15 && Integer.valueOf(rever)<=21)levelid=3;
                else if(Integer.valueOf(rever)>=22 && Integer.valueOf(rever)<=28)levelid=4;
                else if(Integer.valueOf(rever)>=29 && Integer.valueOf(rever)<=35)levelid=5;
                else if(Integer.valueOf(rever)>=36 && Integer.valueOf(rever)<=42)levelid=6;
                else if(Integer.valueOf(rever)>=43 && Integer.valueOf(rever)<=49)levelid=7;
                else if(Integer.valueOf(rever)>=50 && Integer.valueOf(rever)<=56)levelid=8;
                else if(Integer.valueOf(rever)>=57 && Integer.valueOf(rever)<=63)levelid=9;
                else if(Integer.valueOf(rever)>=64 && Integer.valueOf(rever)<=70)levelid=10;
				
                
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);
                 
                long[] tmp2 = new long[16384];
                int frame_flag=0;
                int t3=1;

                IplImage img=null;
                
           
                
                try {      
                	  
                	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
             
                    grabber.start();
                    int frame_count = grabber.getLengthInFrames();

                   
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
                    	   VLBP4n poshogimage =  new VLBP4n(mat3, mat2, mat1);
                    	   
                    	   long[] tmp1 = poshogimage.finaldes();

                      	  for(int iii=0;iii<4096;iii++) {tmp2[iii]=tmp2[iii]+tmp1[iii];}
                    	   
                     	 
                    	   
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
                  

                    tmp2=null;
                    frame_flag=0;
                    }
                   catch (Exception e) {      
                   }
                
                
                
                return new Tuple2<String, String> (filename, filename+"");
            }
        });   
    

    videosRDD.collect();

    output.close();
    sc.stop();
  
	}

	
	
}
