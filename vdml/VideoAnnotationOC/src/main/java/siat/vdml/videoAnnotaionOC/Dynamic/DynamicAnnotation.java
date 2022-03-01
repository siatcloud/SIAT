package siat.vdml.videoAnnotaionOC.Dynamic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class DynamicAnnotation {
	private static BufferedWriter output;

	public static void annotate(String data_url, String output_url)
			throws Exception, IOException {

		long startTime = System.currentTimeMillis();
		output = new BufferedWriter(new FileWriter(output_url));

		File file = new File(data_url);
		String[] files = file.list();
		for (String data : files) {
			String dataPath = data_url + "/" + data;
//			System.out.println(data_url + "/" + data);
			VLBP_algo_OPENCV.VLBP(dataPath, output);
		}
		

		// Train_RandomForest.main(data_url);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Time for Dynamic: " + totalTime + " ms");
	}
}
