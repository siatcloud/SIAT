package siat.vdml.videoAnnotaionOC;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;

import siat.vdml.videoAnnotaionOC.Dynamic.DynamicAnnotation;
import siat.vdml.videoAnnotaionOC.SimilarityMeasure.SearchNearestClips;
import siat.vdml.videoAnnotaionOC.SimilarityMeasure.SearchNearestFrames;
import siat.vdml.videoAnnotaionOC.Spatial.spatialAnnotation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception, IOException
    {
    	String DynamicOuputUrl = "data/DynamicQueryData.txt";
//		String SpatialOuputUrl = "data/SpatialQueryData.txt";
		String SpatialOuputUrl = "data/SpatialDataset.txt";
//		String DataPath_url = "data/testData";
		String DataPath_url = "D:/Data/Annotation/partDataset/allData";
//		String Dataset_url = "D:/Data/Dynamic Texture/UCF50_Full/";
//		DynamicAnnotation.annotate(DataPath_url, DynamicOuputUrl);
//		SearchNearestClips.main(DynamicOuputUrl);
		spatialAnnotation.annotate(DataPath_url, SpatialOuputUrl);
//		SearchNearestFrames.main(SpatialOuputUrl);
    }
}
