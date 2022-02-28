package siat.dpl;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import scala.Tuple2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

//import org.bytedeco.javacpp.indexer.UByteRawIndexer;

/**
 * <h1>SIAT Project</h1> <h2>Layer: Data Processing Layer</h2> <h3>@class featureExtractor_testData</h3>
 * <p>
 *
 * @author Md Azher Uddin <azher@dke.khu.ac.kr>
 * @version 1.0
 * @Project This file is part of SIAT project.
 * @Description: </p>
 * <p>
 * <p>
 * @Description: This class is used to extract the features from the test data.
 * <p>
 * </p>
 * @since 2017-10-01
 **/

public class FeatureExtractor_testData {


    /**
     * Extract the frame based feature from the test video data.
     *
     * @param string data_url path of the test video data where they stored
     * @param string result_url path of the feature data generated using feature extraction algorithm from the test video data
     * @author Md Azher Uddin <azher@dke.khu.ac.kr>
     * @since 2017-10-01
     */
	public static void featureExtractor_testData(String data_url, String result_url ) throws Exception, IOException {
	

	SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.ui.port", "44040" ).set("spark.driver.allowMultipleContexts", "true");
	JavaSparkContext sc = new JavaSparkContext(conf);

	int bufferSize = (16384 * 2 * 1024);

	final FileWriter FW = (new FileWriter(result_url, true));
	output = new BufferedWriter(FW, bufferSize);

     JavaPairRDD<String, PortableDataStream> videos_ = sc.binaryFiles(data_url, 2);
    
  
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

    

				

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);
        long[] tmp2 = new long[16385];
        int frame_flag = 0;
        int t3 = 1;
        int iiii = 0;
		
		
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
                    
                    output.append(rever+" ");
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