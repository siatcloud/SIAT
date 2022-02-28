package siat.dpl;

import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.io.IOException;

public class VLBP {
	private Mat Image, Image2, Image3;
	public VLBP(Mat img, Mat img2, Mat img3){
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
	
//compute motion binary pattern
	public  long[] motion_binary_pattern(int[][] array, int[][] array2, int[][] array3) throws IOException{
        /*int[][] rmagarray = new int[array.length][array[0].length];
        for (int rPixel = 1; rPixel < array.length - 1; rPixel++)
     	{
    	 	for (int cPixel = 0; cPixel < array[0].length; cPixel++)
	 		{
				rmagarray[rPixel][cPixel] = (array[rPixel+1][cPixel] - array[rPixel-1][cPixel]); 
	 		}
        }
        return rmagarray;*/
	//	 BufferedWriter out = new BufferedWriter(new FileWriter("data/check_mbp.txt"));
        int w = array.length;
        int h = array[0].length;
        int[][] I3 = new int[array.length][array[0].length];
        int[][] sec_lay1 = new int[array.length][array[0].length];
        int[][] sec_lay2 = new int[array.length][array[0].length];
        int[][] sec_lay3 = new int[array.length][array[0].length];
        int[][] LBP = new int[array.length][array[0].length];
        
        for(int rt=0;rt<array.length;rt++)
        	for(int tr=0;tr<array[0].length;tr++)
        		LBP[rt][tr]=0;
        
        
        int count =1;
        int j0=0;
        for (int i=2;i<=w-2;i++)
        {
        	for(int j=2;j<=h-2;j++){
        		j0=array2[i][j];
        		//out.write(j0+ " ");
        		
        		if(array[i][j]>=j0){			
        			sec_lay1[i][j]=1;
        			//out.write(sec_lay1[i-1][j]+ " ");
        			}
        		
        		/*if(array[i-1][j-1]>=j0){
        			sec_lay1[i-1][j-1]=1;
        			//out.write(array[i-1][j-1]+ " "+j0 +" "+sec_lay1[i-1][j-1]+" ");
        			}*/
        		if(array[i-1][j]>=j0){			
        			sec_lay1[i-1][j]=8;
        			//out.write(sec_lay1[i-1][j]+ " ");
        			}
        	/*	if(array[i-1][j+1]>=j0){
        			sec_lay1[i-1][j+1]=1;
        			 //out.write(sec_lay1[i-1][j+1]+ " ");
        			 }*/
        		if(array[i][j+1]>=j0){
        			sec_lay1[i][j+1]=16;
        			 //out.write(sec_lay1[i][j+1]+ " ");
        			 }
        		/*if(array[i+1][j+1]>=j0){
        			sec_lay1[i+1][j+1]=1;
        			 //out.write(I3[i+1][j+1]+ " ");
        	
        			}*/
        		if(array[i+1][j]>=j0){
        			sec_lay1[i+1][j]=2;
        			//out.write(I3[i+1][j]+ " ");
        			}
        		/*if(array[i+1][j-1]>=j0){
        			sec_lay1[i+1][j-1]=1;
        			//out.write(I3[i+1][j-1]+ " ");
        			}*/
        		if(array[i][j-1]>=j0){
        			sec_lay1[i][j-1]=4;
                    //out.write( I3[i][j-1]+ " ");
                    }
        		
        		
        		/*if(array2[i-1][j-1]>=j0){
        			sec_lay2[i-1][j-1]=1;
        			//out.write(array3[i-1][j-1]+ " "+j0 +" "+sec_lay2[i-1][j-1]+" ");
        			//out.write(I3[i-1][j-1]+ " ");
        			}*/
        		if(array2[i-1][j]>=j0){			
        			sec_lay2[i-1][j]=128;
        			//out.write(I3[i-1][j]+ " ");
        			}
        		/*if(array2[i-1][j+1]>=j0){
        			sec_lay2[i-1][j+1]=1;
        			 //out.write(I3[i-1][j+1]+ " ");
        			 }*/
        		if(array2[i][j+1]>=j0){
        			sec_lay2[i][j+1]=256;
        			 //out.write(I3[i][j+1]+ " ");
        			 }
        	/*	if(array2[i+1][j+1]>=j0){
        			sec_lay2[i+1][j+1]=1;
        			 //out.write(I3[i+1][j+1]+ " ");
        	
        			}*/
        		if(array2[i+1][j]>=j0){
        			sec_lay2[i+1][j]=32;
        			//out.write(I3[i+1][j]+ " ");
        			}
        /*		if(array2[i+1][j-1]>=j0){
        			sec_lay2[i+1][j-1]=1;
        			//out.write(I3[i+1][j-1]+ " ");
        			}*/
        		if(array2[i][j-1]>=j0){
        			sec_lay2[i][j-1]=64;
                    //out.write( I3[i][j-1]+ " ");
                    }
        		
        		
        		
        	/*	if(array3[i-1][j-1]>=j0){
        			sec_lay3[i-1][j-1]=1;
        			//out.write(array3[i-1][j-1]+ " "+j0 +" "+sec_lay2[i-1][j-1]+" ");
        			//out.write(I3[i-1][j-1]+ " ");
        			}*/
        		if(array3[i][j]>=j0){			
        			sec_lay3[i][j]=8192;
        			//out.write(sec_lay1[i-1][j]+ " ");
        			}
        		
        		if(array3[i-1][j]>=j0){			
        			sec_lay3[i-1][j]=2048;
        			//out.write(I3[i-1][j]+ " ");
        			}
        	/*	if(array3[i-1][j+1]>=j0){
        			sec_lay3[i-1][j+1]=1;
        			 //out.write(I3[i-1][j+1]+ " ");
        			 }*/
        		if(array3[i][j+1]>=j0){
        			sec_lay3[i][j+1]=4096;
        			 //out.write(I3[i][j+1]+ " ");
        			 }
        	/*	if(array3[i+1][j+1]>=j0){
        			sec_lay3[i+1][j+1]=1;
        			 //out.write(I3[i+1][j+1]+ " ");
        	
        			}*/
        		if(array3[i+1][j]>=j0){
        			sec_lay3[i+1][j]=512;
        			//out.write(I3[i+1][j]+ " ");
        			}
        	/*	if(array3[i+1][j-1]>=j0){
        			sec_lay3[i+1][j-1]=1;
        			//out.write(I3[i+1][j-1]+ " ");
        			}*/
        		if(array3[i][j-1]>=j0){
        			sec_lay3[i][j-1]=1024;
                    //out.write( I3[i][j-1]+ " ");
                    }
        		
        		
        	
        		//LBP[i][j]=I3[i-1][j-1]+I3[i-1][j]+I3[i-1][j+1]+I3[i][j+1]+I3[i+1][j+1]+I3[i+1][j]+I3[i+1][j-1]+I3[i][j-1];
        		
        		LBP[i][j]=sec_lay1[i][j]+sec_lay3[i][j]+sec_lay1[i-1][j]+sec_lay1[i][j+1]+sec_lay1[i+1][j]+sec_lay1[i][j-1]+sec_lay2[i-1][j]+sec_lay2[i][j+1]+sec_lay2[i+1][j]+sec_lay2[i][j-1]+sec_lay3[i-1][j]+sec_lay3[i][j+1]+sec_lay3[i+1][j]+sec_lay3[i][j-1];
        		
        		sec_lay1[i][j]=0;sec_lay1[i-1][j-1]=0;sec_lay1[i-1][j]=0;sec_lay1[i-1][j+1]=0;sec_lay1[i][j+1]=0;sec_lay1[i+1][j+1]=0;sec_lay1[i+1][j]=0;sec_lay1[i+1][j-1]=0;sec_lay1[i][j-1]=0;
        		sec_lay2[i-1][j-1]=0;sec_lay2[i-1][j]=0;sec_lay2[i-1][j+1]=0;sec_lay2[i][j+1]=0;sec_lay2[i+1][j+1]=0;sec_lay2[i+1][j]=0;sec_lay2[i+1][j-1]=0;sec_lay2[i][j-1]=0;
        		sec_lay3[i][j]=0;sec_lay3[i-1][j-1]=0;sec_lay3[i-1][j]=0;sec_lay3[i-1][j+1]=0;sec_lay3[i][j+1]=0;sec_lay3[i+1][j+1]=0;sec_lay3[i+1][j]=0;sec_lay3[i+1][j-1]=0;sec_lay3[i][j-1]=0;
                 
                I3[i-1][j-1]=0;I3[i-1][j]=0;I3[i-1][j+1]=0;I3[i][j+1]=0;I3[i+1][j+1]=0;I3[i+1][j]=0;I3[i+1][j-1]=0;I3[i][j-1]=0;
               // out.write(LBP[i][j]+ " ");
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
        
        long[] f= new long[16384];
        for(int kkk=0;kkk<16384;kkk++)
        	f[kkk]=0;
        
        int cout=0,z=0;
        
        
        for(int ii=0;ii<w;ii++){
        	for(int jj=0;jj<h;jj++){
        		//System.out.print(cout+" ");
        		cout++;
        		 //z=LBP[ii][jj];
        	//	 out.write(LBP[ii][jj]+" ");
        		 //f[z++]=f[z++]+1;  
        		 
        		 
        		f[LBP[ii][jj]]++;
        		//out.write(z+" ");
        		
        	}
        //	out.newLine();
        }
        
        int pixel; 
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
