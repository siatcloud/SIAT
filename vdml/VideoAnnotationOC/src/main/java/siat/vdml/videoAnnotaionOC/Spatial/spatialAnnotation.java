package siat.vdml.videoAnnotaionOC.Spatial;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class spatialAnnotation {
	private static BufferedWriter output;

	public static void annotate(String data_url, String output_url)
			throws IOException {
		output = new BufferedWriter(new FileWriter(output_url));
		File file = new File(data_url);
		String[] files = file.list();
		for (String data : files) {
			String dataPath = data_url + "/" + data;
			System.out.println(data);
			// VLBP_algo_OPENCV.VLBP(data, output);
			VideoCap.main(dataPath, output);
		}
	}
}
