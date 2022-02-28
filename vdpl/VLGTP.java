package siat.dpl;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.javacpp.opencv_core.Mat;

public class VLGTP {
	private Mat Image, Image2, Image3;
	public VLGTP(Mat img, Mat img2, Mat img3){
		Image = img;
		Image2 = img2;
		Image3 = img3;
		
	}
	
    //convert the color image into grayscale image,
	/*
	public int[][] gray(BufferedImage image){
			
		/*int[][] array = new int[image.getHeight()-32][image.getWidth()-32];
        for (int rPixel = 16; rPixel < image.getHeight() - 16; rPixel++)
     	{
    	 	for (int cPixel = 16; cPixel < image.getWidth() - 16; cPixel++)
	 		{				 
				array[rPixel-26][cPixel-26] = (int)Math.round((0.21)*(image.getRGB(cPixel,rPixel) >> 16 & 0xFF) + 0.72*(image.getRGB(cPixel,rPixel) >> 8 & 0xFF)+(0.07)*(image.getRGB(cPixel,rPixel) & 0xFF)); 
	 		}
     	}
        return array;
        int[][] array = new int[image.getHeight()][image.getWidth()];
       
        for (int rPixel = 0; rPixel < image.getHeight(); rPixel++)
     	{
    	 	for (int cPixel = 0; cPixel < image.getWidth(); cPixel++)
	 		{				 
				array[rPixel][cPixel] = (int)Math.round((0.21)*(image.getRGB(cPixel,rPixel) >> 16 & 0xFF) + 0.72*(image.getRGB(cPixel,rPixel) >> 8 & 0xFF)+(0.07)*(image.getRGB(cPixel,rPixel) & 0xFF)); 
	 		
	 		}
     	}
            
        /*for(int qw=0;qw<image.getHeight();qw++)
       	 for(int wq=0;wq<image.getWidth();wq++)
       		  System.out.println(array[qw][wq]+ " ");*/
	/*
        return array;
        
	}	*/

	//compute the gradient in vertical direction
	
//compute motion binary pattern
	public  long[] motion_binary_pattern(int[][] array, int[][] array2, int[][] array3) throws IOException{
        /*int[][] rmagarray = new int[array.length][array[0].length];
        for (int rPixel = 1; rPixel < array.length - 1; rPixel++)
     	{
    	 	for (int cPixel = 0; cPixel < array[0].length; cPixel++)
	 		{
				rmagarray[rPixel][cPixel] = (array[rPixel+2][cPixel] - array[rPixel-2][cPixel]); 
	 		}
        }
        return rmagarray;*/
	//	 BufferedWriter out = new BufferedWriter(new FileWriter("data/check_mbp.txt"));
        int w = array.length;
        int h = array[0].length;
        //int[][] I3 = new int[array.length][array[0].length];
        int[][] sec_lay1 = new int[array.length][array[0].length];
        int[][] sec_lay2 = new int[array.length][array[0].length];
        int[][] sec_lay3 = new int[array.length][array[0].length];
        int[][] lower_sec_lay1 = new int[array.length][array[0].length];
        int[][] lower_sec_lay2 = new int[array.length][array[0].length];
        int[][] lower_sec_lay3 = new int[array.length][array[0].length];
        int[][] LBP = new int[array.length][array[0].length];
        int[][] LBP2 = new int[array.length][array[0].length];
        
        for(int rt=0;rt<array.length;rt++){
        	for(int tr=0;tr<array[0].length;tr++){
        		LBP[rt][tr]=0;
        		LBP2[rt][tr]=0;
        	}
        }
        
        int count =1;
        int j0=0,jj0=0;
        for (int i=3;i<=w-3;i++)
        {
        	for(int j=3;j<=h-3;j++){
        		
        		jj0=(int)((array2[i-2][j]-array2[i][j])+(array2[i][j+2]-array2[i][j])+(array2[i+2][j]-array2[i][j])+(array2[i][j-2]-array2[i][j])/4);
        		
        		j0=array2[i][j];
        		//out.write(j0+ " ");
        		if((Math.abs(array[i-2][j]-array[i][j])-jj0)>=5){
        			sec_lay1[i-2][j]=1;
             		}
        		else if((Math.abs(array[i-2][j]-array[i][j])-jj0)<=-5){
        			lower_sec_lay1[i-2][j]=1;
             		}
        		if((Math.abs(array[i][j+2]-array[i][j])-jj0)>=5){
        			sec_lay1[i][j+2]=2;
             		}
        		else if((Math.abs(array[i][j+2]-array[i][j])-jj0)<=-5){
        			lower_sec_lay1[i][j+2]=2;
             		}
        		if((Math.abs(array[i+2][j]-array[i][j])-jj0)>=5){
        			sec_lay1[i+2][j]=4;
             		}
        		else if((Math.abs(array[i+2][j]-array[i][j])-jj0)<=-5){
        			lower_sec_lay1[i+2][j]=4;
             		}
        		if((Math.abs(array[i][j-2]-array[i][j])-jj0)>=5){
        			sec_lay1[i][j-2]=8;
             		}
        		else if((Math.abs(array[i][j-2]-array[i][j])-jj0)<=-5){
        			lower_sec_lay1[i][j-2]=8;
             		}
        		
        		
        		if((Math.abs(array2[i-2][j]-array2[i][j])-jj0)>=5){
        			sec_lay2[i-2][j]=16;
             		}
        		else if((Math.abs(array2[i-2][j]-array2[i][j])-jj0)<=-5){
        			lower_sec_lay2[i-2][j]=16;
             		}
        		if((Math.abs(array2[i][j+2]-array2[i][j])-jj0)>=5){
        			sec_lay2[i][j+2]=32;
             		}
        		else if((Math.abs(array2[i][j+2]-array2[i][j])-jj0)<=-5){
        			lower_sec_lay2[i][j+2]=32;
             		}
        		if((Math.abs(array2[i+2][j]-array2[i][j])-jj0)>=5){
        			sec_lay2[i+2][j]=64;
             		}
        		else if((Math.abs(array2[i+2][j]-array2[i][j])-jj0)<=-5){
        			lower_sec_lay2[i+2][j]=64;
             		}
        		if((Math.abs(array2[i][j-2]-array2[i][j])-jj0)>=5){
        			sec_lay2[i][j-2]=128;
             		}
        		else if((Math.abs(array2[i][j-2]-array2[i][j])-jj0)<=-5){
        			lower_sec_lay2[i][j-2]=128;
             		}
        		
        		
        		if((Math.abs(array3[i-2][j]-array3[i][j])-jj0)>=5){
        			sec_lay3[i-2][j]=256;
             		}
        		else if((Math.abs(array3[i-2][j]-array3[i][j])-jj0)<=-5){
        			lower_sec_lay3[i-2][j]=256;
             		}
        		if((Math.abs(array3[i][j+2]-array3[i][j])-jj0)>=5){
        			sec_lay3[i][j+2]=512;
             		}
        		else if((Math.abs(array3[i][j+2]-array3[i][j])-jj0)<=-5){
        			lower_sec_lay3[i][j+2]=512;
             		}
        		if((Math.abs(array3[i+2][j]-array3[i][j])-jj0)>=5){
        			sec_lay3[i+2][j]=1024;
             		}
        		else if((Math.abs(array3[i+2][j]-array3[i][j])-jj0)<=-5){
        			lower_sec_lay3[i+2][j]=1024;
             		}
        		if((Math.abs(array3[i][j-2]-array3[i][j])-jj0)>=5){
        			sec_lay3[i][j-2]=2048;
             		}
        		else if((Math.abs(array3[i][j-2]-array3[i][j])-jj0)<=-5){
        			lower_sec_lay3[i][j-2]=2048;
             		}
        		
        		
        		
        		
        	
        		//LBP[i][j]=I3[i-2][j-2]+I3[i-2][j]+I3[i-2][j+2]+I3[i][j+2]+I3[i+2][j+2]+I3[i+2][j]+I3[i+2][j-2]+I3[i][j-2];
        		
        		LBP[i][j]=sec_lay1[i-2][j]+sec_lay1[i][j+2]+sec_lay1[i+2][j]+sec_lay1[i][j-2]+sec_lay2[i-2][j]+sec_lay2[i][j+2]+sec_lay2[i+2][j]+sec_lay2[i][j-2]+sec_lay3[i-2][j]+sec_lay3[i][j+2]+sec_lay3[i+2][j]+sec_lay3[i][j-2];
        		LBP2[i][j]=lower_sec_lay1[i-2][j]+lower_sec_lay1[i][j+2]+lower_sec_lay1[i+2][j]+lower_sec_lay1[i][j-2]+lower_sec_lay2[i-2][j]+lower_sec_lay2[i][j+2]+lower_sec_lay2[i+2][j]+lower_sec_lay2[i][j-2]+lower_sec_lay3[i-2][j]+lower_sec_lay3[i][j+2]+lower_sec_lay3[i+2][j]+lower_sec_lay3[i][j-2];
            	
        		
        		
        		sec_lay1[i-2][j-2]=0;sec_lay1[i-2][j]=0;sec_lay1[i-2][j+2]=0;sec_lay1[i][j+2]=0;sec_lay1[i+2][j+2]=0;sec_lay1[i+2][j]=0;sec_lay1[i+2][j-2]=0;sec_lay1[i][j-2]=0;
        		sec_lay2[i-2][j-2]=0;sec_lay2[i-2][j]=0;sec_lay2[i-2][j+2]=0;sec_lay2[i][j+2]=0;sec_lay2[i+2][j+2]=0;sec_lay2[i+2][j]=0;sec_lay2[i+2][j-2]=0;sec_lay2[i][j-2]=0;
        		sec_lay3[i-2][j-2]=0;sec_lay3[i-2][j]=0;sec_lay3[i-2][j+2]=0;sec_lay3[i][j+2]=0;sec_lay3[i+2][j+2]=0;sec_lay3[i+2][j]=0;sec_lay3[i+2][j-2]=0;sec_lay3[i][j-2]=0;
                 
        		lower_sec_lay1[i-2][j-2]=0;lower_sec_lay1[i-2][j]=0;lower_sec_lay1[i-2][j+2]=0;lower_sec_lay1[i][j+2]=0;lower_sec_lay1[i+2][j+2]=0;lower_sec_lay1[i+2][j]=0;lower_sec_lay1[i+2][j-2]=0;lower_sec_lay1[i][j-2]=0;
        		lower_sec_lay2[i-2][j-2]=0;lower_sec_lay2[i-2][j]=0;lower_sec_lay2[i-2][j+2]=0;lower_sec_lay2[i][j+2]=0;lower_sec_lay2[i+2][j+2]=0;lower_sec_lay2[i+2][j]=0;lower_sec_lay2[i+2][j-2]=0;lower_sec_lay2[i][j-2]=0;
        		lower_sec_lay3[i-2][j-2]=0;lower_sec_lay3[i-2][j]=0;lower_sec_lay3[i-2][j+2]=0;lower_sec_lay3[i][j+2]=0;lower_sec_lay3[i+2][j+2]=0;lower_sec_lay3[i+2][j]=0;lower_sec_lay3[i+2][j-2]=0;lower_sec_lay3[i][j-2]=0;
                
                //I3[i-2][j-2]=0;I3[i-2][j]=0;I3[i-2][j+2]=0;I3[i][j+2]=0;I3[i+2][j+2]=0;I3[i+2][j]=0;I3[i+2][j-2]=0;I3[i][j-2]=0;
               // out.write(LBP[i][j]+ " ");
                //out.newLine();
                //out.write((LBP[i][j])+" ");
                count=count+2;
        		
        	}
        }
        //System.out.println(Math.pow(2, 5));
        /*
        for(int qw=0;qw<w;qw++)
        	 for(int wq=0;wq<h;wq++)
        		  System.out.println(LBP[qw][wq]+ " ");*/
        /*
        out.write(LBP.length+" "+LBP[0].length+"");
        for(int qw=0;qw<array.length;qw++){
       	 for(int wq=0;wq<array[0].length;wq++)
       	 {
       		out.newLine();
       		 out.write((LBP[qw][wq])+"");
       		
       	 }
	}*/
        
        long[] f= new long[4096];
        for(int kkk=0;kkk<4096;kkk++)
        	f[kkk]=0;
        long[] f2= new long[4096];
        for(int kkk=0;kkk<4096;kkk++)
        	f2[kkk]=0;
        
        int cout=0,cout2=0,z=0;
        
        
        for(int ii=0;ii<w;ii++){
        	for(int jj=0;jj<h;jj++){
        		//System.out.print(cout+" ");
        		cout++;
        		 //z=LBP[ii][jj];
        		 //out.write(LBP[ii][jj]+" ");
        		 //f[z++]=f[z++]+2;  
        		 
        		 
        		f[LBP[ii][jj]]++;
        		//out.write(z+" ");
        		
        	}
        	//out.newLine();
        }
        for(int ii2=0;ii2<w;ii2++){
        	for(int jj2=0;jj2<h;jj2++){
        		//System.out.print(cout+" ");
        		cout2++;
        		 //z=LBP[ii][jj];
        		 //out.write(LBP[ii][jj]+" ");
        		 //f[z++]=f[z++]+2;  
        		 
        		 
        		f2[LBP2[ii2][jj2]]++;
        		//out.write(z+" ");
        		
        	}
        	//out.newLine();
        }
        
        long[] f3= new long[8195];
        int kkk5=0;
        for(int kkk4=0;kkk4<8192;kkk4++){
        	if(kkk4<4096){
        	f3[kkk4]=f[kkk4];}
        	else if(kkk4>=4096){
        		f3[kkk4]=f2[kkk5];
        		kkk5++;
        		}
        }
        	
        //int pixel; 
        /*
        for (int j = 0; j < 781; j++) { 
			for (int col = 0; col < 144; col++) { 
				for (int row = 0; row < 180; row++) { 
					pixel = LBP[col][row]; 
					out.write(LBP[col][row]+" ");
					if (pixel == j) { 
						f[j]++;
					}
				}
				out.newLine();
			}
		}
        */
        /*for(int kkk=1;kkk<=780;kkk++)
        	{out.write(f[kkk]+" ");}
        */
        return f3;
             
        
	}
	
	
	public int[][] MatToBytes(Mat matImage) {
		int width = matImage.size().width(), height = matImage.size().height(), channels = matImage.channels();
		  int[][] output = new int[height][width];
		    UByteRawIndexer indexer = matImage.createIndexer();

		    int index = 0;
		    for (int i = 0; i < matImage.rows(); i ++)
		    {
		        for (int j = 0; j < matImage.cols(); j++)
		        {
		            
		                output[i][j] = (int)indexer.get(i, j);
		               // index++;
		            
		        }
		    }
		   
		    return  output;
	}
	
   
   public long[] finaldes() throws IOException{
	   
	   int[][] array = MatToBytes(Image);
	   int[][] array2 = MatToBytes(Image2);
	   int[][] array3 = MatToBytes(Image3);
	   
	   long[] descriptor = motion_binary_pattern(array, array2, array3);
	   //for(int kkk=0;kkk<=4095;kkk++)
  		//{System.out.println(descriptor[kkk]+" ");}
	   
	   return descriptor;
	   
   }

}
