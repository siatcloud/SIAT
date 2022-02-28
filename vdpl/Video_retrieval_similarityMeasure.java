package siat.dpl;

import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.input.PortableDataStream;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.FrameGrabber.Exception;

import scala.Tuple2;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Arrays;
import java.util.Scanner;

public class Video_retrieval_similarityMeasure {
	public static void similarityMeasure(String query_url,String data_url,String result_url) throws Exception, IOException {
	//Need to change
	//System.setProperty("hadoop.home.dir", "C://Users//azher_dke//workspace1//com.siat.batchprocessing//data//hadooponwindows-master//hadooponwindows-master");
	
	//Need to change
	    long[] tmp2 = new long[16384];
	//System.setProperty("hadoop.home.dir", "data/winnn/winutils.exe");
	SparkConf conf = new SparkConf().setAppName("sd").setMaster("local[4]").set("spark.executor.instances", "2").set("spark.executor.memory", "512M").set("spark.executor.cores", "2").set("spark.driver.allowMultipleContexts", "true");
	JavaSparkContext sc = new JavaSparkContext(conf);
//	long[][] posdescriptors = new long[27][16384];
 //   long[] fea_vec = new long[442368]; 
    //int label=1;
    //int flag = 0;
    
    int levelid=0;
    //int[] ED = new int[6915];
    //int[][] query_video_data = new int[2][6915];
    //int[][] mat_video_data = new int[100][6915];
    
   // JavaPairRDD<String, PortableDataStream> videos = sc.binaryFiles(data_url);
    JavaPairRDD<String, PortableDataStream> videos = sc.binaryFiles(query_url, 1);
    
    videos.getCheckpointFile();
	List<Tuple2<String, PortableDataStream>> a;
	
	a = videos.collect();
	int tem = 0;
	   
	for(int Vdata=0;Vdata<videos.count();Vdata++){
		
    Tuple2<String, PortableDataStream> abc = a.get(Vdata);
    String jj=abc._1();
    //int i=0;
    //out.write(jj+" ");
   // System.out.println(jj);
    String reverse = new StringBuffer(jj).reverse().toString();
    //System.out.println(reverse);
    String[] parts = reverse.split("\\/");
    //System.out.println(parts[0]);
 
    
    int len = parts[0].length();
    //System.out.print(len);
    
    String resultStr = jj.substring(5, jj.length());
    String dataid = parts[0].substring(4, len);
    
    
    String rever = new StringBuffer(dataid).reverse().toString();

    int Video_dataid = Integer.valueOf(rever);
    //if(Integer.valueOf(rever)>=1 && Integer.valueOf(rever)<=15)levelid=1;
    //else if(Integer.valueOf(rever)>=16 && Integer.valueOf(rever)<=200)levelid=2;
   // else if(Integer.valueOf(rever)>=201 && Integer.valueOf(rever)<=300)levelid=3;
    //else if(Integer.valueOf(rever)>=301 && Integer.valueOf(rever)<=400)levelid=4;
    //else if(Integer.valueOf(rever)>=401 && Integer.valueOf(rever)<=500)levelid=5;
  //  else if(Integer.valueOf(rever)>=501 && Integer.valueOf(rever)<=600)levelid=6;
    //else if(Integer.valueOf(rever)>=49 && Integer.valueOf(rever)<=56)levelid=7;
    //else if(Integer.valueOf(rever)>=57 && Integer.valueOf(rever)<=64)levelid=8;
    //else if(Integer.valueOf(rever)>=65 && Integer.valueOf(rever)<=72)levelid=9;
    //else if(Integer.valueOf(rever)>=73 && Integer.valueOf(rever)<=80)levelid=10;
    
  //  int t3=0;
   // out.write(rever+" ");
    //out.write(resultStr+" ");
   // System.out.println(dataid);
  //  OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
   // FrameGrabber grabber = FrameGrabber.createDefault(resultStr);
    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(resultStr);
   // long[] tmp2 = new long[16384];
    int frame_flag=0;
    int t3=1;
    int iiii=0;
    IplImage img=null;
    try {      
    	  
    	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
       // AndroidFrameConverter convertToBitmap = new AndroidFrameConverter();
        grabber.start();
        
    	//out.write("I am here ");
        //grabber.start();      
       
    	   //IplImage img;
    	   IplImage img1 = null;
    	   IplImage img2 = null;
    	   Mat matImage = null;  Mat matImage2= null;  Mat matImage3=null;
    	   Mat mat3= null; Mat mat2=null;
    	   
       // while (true) {
        for(int k= 1; k<50; k++){	
        	
        	
         //	out.write("I am here 2");
        	img = converter.convert(grabber.grab());
        	//IplImage img2 = converter.convert(grabber.grab());
        	//IplImage img3 = converter.convert(grabber.grab());
        	//IplImage img4 = converter.convert(grabber.grab());
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
        	
        
        	else if(frame_flag>1 && frame_flag<500){ 
        	 	//out.write("I am here 3");
        	 matImage = new Mat(img);
        	 Mat mat1 = new Mat(matImage.arrayHeight(),matImage.arrayWidth());
        	
        	// matImage2 = new Mat(img1);
        	// Mat mat2 = new Mat(matImage2.arrayHeight(),matImage2.arrayWidth());
        	

        	 //matImage3 = new Mat(img2);
        	 //Mat mat3 = new Mat(matImage3.arrayHeight(),matImage3.arrayWidth());
        	 
        	   //System.out.println(matImage.channels());
        	   
        	   cvtColor(matImage, mat1, COLOR_RGB2GRAY);
        	 //  cvtColor(matImage2, mat2, COLOR_RGB2GRAY);
        	  // cvtColor(matImage3, mat3, COLOR_RGB2GRAY);
        	   
        	   //System.out.println("Here123"+ mat1.data());
           	   //System.out.println("Here456"+ mat2.data());
           
           	   //opencv_imgcodecs.imwrite("data/frames/"+Vdata+"_"+(k-2)+".jpg", mat3);
          	 
           	  // opencv_imgcodecs.imwrite("data/frames/"+Vdata+"_"+(k-1)+".jpg", mat2);
           	   
           	  // opencv_imgcodecs.imwrite("data/frames/"+Vdata+"_"+(k)+".jpg", mat1);
        	   
           	   
        	  // MBP poshogimage =  new MBP(mat3, mat2, mat1);
        	   VLGTP poshogimage =  new VLGTP(mat3, mat2, mat1);
        	   
        	   long[] tmp1 = poshogimage.finaldes();

          	  for(int iii=0;iii<8192;iii++) {tmp2[iii]=tmp2[iii]+tmp1[iii];}
        	   
         	 /* for(int iii=0;iii<16384;iii++)
              {
         		 posdescriptors[tem][iii] = tmp1[iii];
         		out.write(t3+":" +posdescriptors[tem][iii]+" ");
         		 t3++;
               // System.out.println(tmp1[iii]+" ");
              }
        	   tem = tem+1;*/
        	   
        
        	 iiii++;
        	 mat3=mat2;
        	 mat2=mat1;
        	 
        	 
         //cvSaveImage("data/Final_data/training/frame/"+vid+"_"+(i++) + ".jpg", img);
         //cvSaveImage("data/Final_data/testing/frame/"+vid+"_"+(i++) + ".jpg", img);.
        	 
         }
         frame_flag++;
         }
        }
       // for(int iii=0;iii<16384;iii++)
       // {
   	//	out.write(tmp2[iii]+" ");
   		 //t3++;
         // System.out.println(tmp1[iii]+" ");
       // }
      //  tmp2=null;
        frame_flag=0;
        }
       catch (Exception e) {      
       }
    
    /*
    String rever = new StringBuffer(dataid).reverse().toString();

    int Video_dataid = Integer.valueOf(rever);
    if(Integer.valueOf(rever)>=1 && Integer.valueOf(rever)<=8)levelid=1;
    else if(Integer.valueOf(rever)>=9 && Integer.valueOf(rever)<=16)levelid=2;
    else if(Integer.valueOf(rever)>=17 && Integer.valueOf(rever)<=24)levelid=3;
    else if(Integer.valueOf(rever)>=25 && Integer.valueOf(rever)<=32)levelid=4;
    else if(Integer.valueOf(rever)>=33 && Integer.valueOf(rever)<=40)levelid=5;
    else if(Integer.valueOf(rever)>=41 && Integer.valueOf(rever)<=48)levelid=6;
    else if(Integer.valueOf(rever)>=49 && Integer.valueOf(rever)<=56)levelid=7;
    else if(Integer.valueOf(rever)>=57 && Integer.valueOf(rever)<=64)levelid=8;
    else if(Integer.valueOf(rever)>=65 && Integer.valueOf(rever)<=72)levelid=9;
    else if(Integer.valueOf(rever)>=73 && Integer.valueOf(rever)<=80)levelid=10;
    
    int t3=0;
    out.write(levelid+" ");
    for (int t1 = 0; t1<=1;t1++){
   	 for(int t2=0;t2<=255;t2++)
   	 {
   		 fea_vec[t3] = posdescriptors[t1][t2];
   		 t3++;
   		//System.out.print(Video_dataid+ " "+ t3+": " +fea_vec[t3]+" ");
   		
   		out.write(t3+":" +fea_vec[t3]+" ");
   	 }
    }
    */
   // out.newLine();
    tem = 0;
    
	}
	 Scanner vv2 = new Scanner(new BufferedReader(new FileReader(data_url)));
	 int lines = 0;
	 while (vv2.hasNextLine()) {
		 lines++;
	 vv2.nextLine();}
	 //System.out.println(lines);
	 
	  BufferedWriter out = new BufferedWriter(new FileWriter(result_url));
	 
	 Scanner vv1 = new Scanner(new BufferedReader(new FileReader(data_url)));
     int rows = lines;
     int columns = 8193;
     int [][] myArray = new int[rows][columns];
     while(vv1.hasNextLine()) {
        for (int i=0; i<myArray.length; i++) {
           String[] line = vv1.nextLine().trim().split(" ");
           for (int j=0; j<line.length; j++) {
              myArray[i][j] = Integer.parseInt(line[j]);
             // if(i==0)
             // System.out.print(myArray[i][j]+" ");
              //out.write(myArray[i][j]+" ");
           }
        }
     }
   
     //System.out.println(Arrays.deepToString(myArray));
 /*    for(int iii=2;iii<16380;iii++){
    	 out.write(tmp2[iii]+" ");
        out.write(myArray[39][iii+1]+" ");
        
        }*/
        
     //out.newLine();
     long[] ED = new long[50];
     for(int all_data=0;all_data<lines;all_data++){
     	int innerSum = 0;
         for (int ii = 2; ii <= 8190; ii++) {
             innerSum += Math.pow(myArray[all_data][ii+1] - tmp2[ii], 2.0);
        	// System.out.println(Math.abs(myArray[all_data][ii+1]-tmp2[ii]));
            // innerSum = (int) (innerSum+Math.abs(myArray[all_data][ii+1] - tmp2[ii]));
         }
         ED[all_data]=(int) Math.sqrt(innerSum);
        // ED[all_data]=(int) (innerSum);
         System.out.println(ED[all_data]);
         //out5.write(all_data+":" +(ED[all_data])+" ");
         //out5.write(all_data+":");
         //out5.newLine();
     }
     
     //System.out.println("Similar video ID:");
     long small = 10000000;
     long index = 0,new_i=0,new_i1=0,new_i2=0;
     for (int i = 0; i < lines; i++){
         if (ED[i] < small)
         {
             small = ED[i];
             index = myArray[i][0] ;
            new_i=i;
             //System.out.println("Here");
         }
     }
     out.write((int) index+"#");

     small = 10000000;
     int index1 = 0;
     for (int i = 0; i < lines; i++){
         if (ED[i] < small && new_i!=i)
         {
             small = ED[i];
            
             index1 = myArray[i][0];
             new_i1=i;
             //System.out.println("Here");
         }
     }
     out.write((int) index1+"#");

     small = 10000000;
     int index2 = 0;
     for (int i = 0; i < lines; i++){
         if (ED[i] < small && new_i!=i && new_i1!=i)
         {
             small = ED[i];
             
             index2 = myArray[i][0];
             new_i2=i;
             //System.out.println("Here");
         }
     }
     out.write((int) index2+"#");
     small = 10000000;
     int index3 = 0;
     for (int i = 0; i < lines; i++){
         if (ED[i] < small && new_i!=i && new_i1!=i&& new_i2!=i)
         {
             small = ED[i];
             
             index3 = myArray[i][0];
            
             //System.out.println("Here");
         }
     }
     out.write((int) index3+"#");
     
     
     out.close();
     sc.stop();
     
  }
//	out.close();
	
}
