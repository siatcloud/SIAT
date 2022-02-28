package siat.dpl;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

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

public class KeyFrame_Extraction_Hog {
public static void KeyFrameExtractor(String data_url, final String result_url) throws Exception, IOException {
		
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

    
    String resultStr = jj.substring(5, jj.length());
    String dataid = parts[0].substring(4, len);
    

    
    
    String rever = new StringBuffer(dataid).reverse().toString();

    //int Video_dataid = Integer.valueOf(rever);

    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);




    int count=0;
    IplImage img=null;
    try {      
    	  
    	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

        grabber.start();
        int frame_count = grabber.getLengthInFrames();

    	   IplImage img1 = null;
    	   IplImage img2 = null;
    	   Mat matImage = null;  Mat matImage2= null;  Mat matImage3=null;
    	   Mat mat3= null; Mat mat2=null;
    	    int frame_flag=0;
    	   
        for(int k= 0; k<frame_count; k++){	
        	

        	img = converter.convert(grabber.grab());

       	
        			
        	if(frame_flag==0){
        	img2 = img;
        	 matImage3 = new Mat(img2);
        	 mat3 = new Mat(matImage3.arrayHeight(),matImage3.arrayWidth());
        	 cvtColor(matImage3, mat3, COLOR_RGB2GRAY);
      	   cvSaveImage(result_url+rever+"_"+count + ".jpg", img);
      	   
      	   count++;
        	}        	

        	/*
        	else if(frame_flag==1){
        	img1 = img;
        	 matImage2 = new Mat(img1);
        	 mat2 = new Mat(matImage2.arrayHeight(),matImage2.arrayWidth());
        	   cvtColor(matImage2, mat2, COLOR_RGB2GRAY);
        	}        	
        	*/
        
        	else if(frame_flag>0 && frame_flag<frame_count){ 
  
        	 matImage = new Mat(img);
        	 Mat mat1 = new Mat(matImage.arrayHeight(),matImage.arrayWidth());
        	
        
        	   cvtColor(matImage, mat1, COLOR_RGB2GRAY);
        	
        	   HOG Frame1 = new HOG(mat3);
        	   HOG Frame2 = new HOG(mat1);
        	   double[] Frame1_tmp1 = Frame1.finaldes();
        	   double[] Frame2_tmp2 = Frame2.finaldes();
        	   double innerSum = 0.0,difference=0.0;
        	   for (int ii = 0; ii < 3780; ii++) {
                   innerSum += Math.pow(Frame1_tmp1[ii] - Frame2_tmp2[ii], 2.0);
              	// System.out.println(Math.abs(myArray[all_data][ii+1]-tmp2[ii]));
                  // innerSum = (int) (innerSum+Math.abs(myArray[0all_data][ii+1] - tmp2[ii]));
               }
        	   difference= Math.sqrt(innerSum);
        	   //System.out.println(difference);        	   
        	   if(difference>=2.85){        	   
        	   cvSaveImage(result_url+rever+"_"+count + ".jpg", img);
        	   count++;
        	   
        	   mat3=mat1;
        	}
        	   
        
        	   //count++;
        

        	 //mat3=mat2;
        	 //mat2=mat1;
        	 
        	 
         }
         frame_flag++;

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
