package ActionRecognition.Algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class callClass {
	public static void call(String path, String outputFile) throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
		
		File dataFile = new File(path);
		for (File fileEntry : dataFile.listFiles()) {
			System.out.println(fileEntry.getAbsolutePath());
			VLBP_algo.VLBP(fileEntry.getAbsolutePath(), output);
		}
		output.close();
	}
}
