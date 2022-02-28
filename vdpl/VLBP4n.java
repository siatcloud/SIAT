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

public class VLBP4n {
    private Mat Image, Image2, Image3;
    public VLBP4n(Mat img, Mat img2, Mat img3){
        Image = img;
        Image2 = img2;
        Image3 = img3;

    }

    //compute VLBP feature Map
    public  long[] motion_binary_pattern(int[][] array, int[][] array2, int[][] array3) throws IOException{
      
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



                if(array[i-1][j]>=j0){
                    sec_lay1[i-1][j]=4;

                }

                if(array[i][j+1]>=j0){
                    sec_lay1[i][j+1]=2;

                }

                if(array[i+1][j]>=j0){
                    sec_lay1[i+1][j]=1;

                }

                if(array[i][j-1]>=j0){
                    sec_lay1[i][j-1]=8;

                }



                if(array2[i-1][j]>=j0){
                    sec_lay2[i-1][j]=64;

                }

                if(array2[i][j+1]>=j0){
                    sec_lay2[i][j+1]=32;

                }

                if(array2[i+1][j]>=j0){
                    sec_lay2[i+1][j]=16;

                }

                if(array2[i][j-1]>=j0){
                    sec_lay2[i][j-1]=128;

                }






                if(array3[i-1][j]>=j0){
                    sec_lay3[i-1][j]=1024;

                }

                if(array3[i][j+1]>=j0){
                    sec_lay3[i][j+1]=512;

                }

                if(array3[i+1][j]>=j0){
                    sec_lay3[i+1][j]=256;

                }

                if(array3[i][j-1]>=j0){
                    sec_lay3[i][j-1]=2048;

                }



                LBP[i][j]=sec_lay1[i-1][j]+sec_lay1[i][j+1]+sec_lay1[i+1][j]+sec_lay1[i][j-1]+sec_lay2[i-1][j]+sec_lay2[i][j+1]+sec_lay2[i+1][j]+sec_lay2[i][j-1]+sec_lay3[i-1][j]+sec_lay3[i][j+1]+sec_lay3[i+1][j]+sec_lay3[i][j-1];

                sec_lay1[i][j]=0;sec_lay1[i-1][j-1]=0;sec_lay1[i-1][j]=0;sec_lay1[i-1][j+1]=0;sec_lay1[i][j+1]=0;sec_lay1[i+1][j+1]=0;sec_lay1[i+1][j]=0;sec_lay1[i+1][j-1]=0;sec_lay1[i][j-1]=0;
                sec_lay2[i-1][j-1]=0;sec_lay2[i-1][j]=0;sec_lay2[i-1][j+1]=0;sec_lay2[i][j+1]=0;sec_lay2[i+1][j+1]=0;sec_lay2[i+1][j]=0;sec_lay2[i+1][j-1]=0;sec_lay2[i][j-1]=0;
                sec_lay3[i][j]=0;sec_lay3[i-1][j-1]=0;sec_lay3[i-1][j]=0;sec_lay3[i-1][j+1]=0;sec_lay3[i][j+1]=0;sec_lay3[i+1][j+1]=0;sec_lay3[i+1][j]=0;sec_lay3[i+1][j-1]=0;sec_lay3[i][j-1]=0;

                I3[i-1][j-1]=0;I3[i-1][j]=0;I3[i-1][j+1]=0;I3[i][j+1]=0;I3[i+1][j+1]=0;I3[i+1][j]=0;I3[i+1][j-1]=0;I3[i][j-1]=0;

                count=count+1;

            }
        }


        long[] f= new long[4096];
        for(int kkk=0;kkk<4096;kkk++)
            f[kkk]=0;

        int cout=0,z=0;


        for(int ii=0;ii<w;ii++){
            for(int jj=0;jj<h;jj++){

                cout++;
                f[LBP[ii][jj]]++;


            }

        }

        int pixel;

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

