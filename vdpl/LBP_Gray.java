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

public class LBP_Gray {
	private Mat Image;
	
	public LBP_Gray(Mat img){
		Image = img;
	}
	
    //convert the color image into grayscale image,
	/*
	public int[][] gray(BufferedImage image){
			
		/*int[][] array = new int[image.getHeight()-32][image.getWidth()-32];
        for (int rPixel = 16; rPixel < image.getHeight() - 16; rPixel++)
     	{
    	 	for (int cPixel = 16; cPixel < image.getWidth() - 16; cPixel++)
	 		{				 
				array[rPixel-16][cPixel-16] = (int)Math.round((0.21)*(image.getRGB(cPixel,rPixel) >> 16 & 0xFF) + 0.72*(image.getRGB(cPixel,rPixel) >> 8 & 0xFF)+(0.07)*(image.getRGB(cPixel,rPixel) & 0xFF)); 
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
	public  int[] rgradient(int[][] array) throws IOException{
        /*int[][] rmagarray = new int[array.length][array[0].length];
        for (int rPixel = 1; rPixel < array.length - 1; rPixel++)
     	{
    	 	for (int cPixel = 0; cPixel < array[0].length; cPixel++)
	 		{
				rmagarray[rPixel][cPixel] = (array[rPixel+1][cPixel] - array[rPixel-1][cPixel]); 
	 		}
        }
        return rmagarray;*/
		 //BufferedWriter out = new BufferedWriter(new FileWriter("data/check_LBP.txt"));
        int w = array.length;
        int h = array[0].length;
        int[][] I3 = new int[array.length][array[0].length];
        int[][] LBP = new int[array.length][array[0].length];
        
        for(int rt=0;rt<array.length;rt++)
        	for(int tr=0;tr<array[0].length;tr++)
        		LBP[rt][tr]=0;
        
        int count =1;
        int j0=0;
        for (int i=2;i<=w-2;i++)
        {
        	for(int j=2;j<=h-2;j++){
        		j0=array[i][j];
        		if(array[i-1][j-1]>j0){
        			I3[i-1][j-1]=(int) (1*Math.pow(2, 0));
        			//out.write(I3[i-1][j-1]+ " ");
        			}
        		if(array[i-1][j]>j0){			
        			I3[i-1][j]=(int) (1*Math.pow(2, 7));
        			//out.write(I3[i-1][j]+ " ");
        			}
        		if(array[i-1][j+1]>j0){
        			 I3[i-1][j+1]=(int) (1*Math.pow(2, 6));
        			 //out.write(I3[i-1][j+1]+ " ");
        			 }
        		if(array[i][j+1]>j0){
        			 I3[i][j+1]=(int) (1*Math.pow(2, 5));
        			 //out.write(I3[i][j+1]+ " ");
        			 }
        		if(array[i+1][j+1]>j0){
        			I3[i+1][j+1]=(int) (1*Math.pow(2, 4));
        			 //out.write(I3[i+1][j+1]+ " ");
        	
        			}
        		if(array[i+1][j]>j0){
        			I3[i+1][j]=(int) (1*Math.pow(2, 3));
        			//out.write(I3[i+1][j]+ " ");
        			}
        		if(array[i+1][j-1]>j0){
        			I3[i+1][j-1]=(int) (1*Math.pow(2, 2));
        			//out.write(I3[i+1][j-1]+ " ");
        			}
        		if(array[i][j-1]>j0){
                    I3[i][j-1]=(int) (1*Math.pow(2, 1));
                    //out.write( I3[i][j-1]+ " ");
                    }
        		
                LBP[i][j]=I3[i-1][j-1]+I3[i-1][j]+I3[i-1][j+1]+I3[i][j+1]+I3[i+1][j+1]+I3[i+1][j]+I3[i+1][j-1]+I3[i][j-1];
                I3[i-1][j-1]=0;I3[i-1][j]=0;I3[i-1][j+1]=0;I3[i][j+1]=0;I3[i+1][j+1]=0;I3[i+1][j]=0;I3[i+1][j-1]=0;I3[i][j-1]=0;
                //out.write(LBP[i][j]+ " ");
                //out.newLine();
                //out.write((LBP[i][j])+" ");
                count=count+1;
        		
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
        
        int[] f= new int[256];
        for(int kkk=0;kkk<255;kkk++)
        	f[kkk]=0;
        
        int cout=0;
        for(int ii=0;ii<w-1;ii++){
        	for(int jj=0;jj<h-1;jj++){
        		//System.out.print(cout+" ");
        		cout++;
        		//out.write(LBP[ii][jj]+" ");
        		f[LBP[ii][jj]]++;
        		
        	}
        	//out.newLine();
        }
        
            
        return f;
             
        
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
	
   
   public int[] finaldes() throws IOException{
	   
	   int[][] array = MatToBytes(Image);
	   
	   int[] descriptor = rgradient(array);

	   
	 //  for(int kkk=0;kkk<=255;kkk++)
   		//{System.out.println(descriptor[kkk]+" ");}
	   
	   return descriptor;
	   
   }

}
