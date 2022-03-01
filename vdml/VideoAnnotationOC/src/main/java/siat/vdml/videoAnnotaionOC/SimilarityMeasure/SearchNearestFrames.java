package siat.vdml.videoAnnotaionOC.SimilarityMeasure;


/*
 * 
 * Search the nearest Score from Cosine Similarity between frames
 * 
 * @param source of train feature file  
 * @param source of test feature file  
 * @param output file path
 * 
 * 
 * @return label of data
 * @return maximum similar frame tag
 * 
 * @author Md Anwarul Islam
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

public class SearchNearestFrames {
	/** Location to save Cosine data */
	// public static final String DATA_PATH = "D:/Data/Annotation/video data/";
	public static final String DATA_PATH = "data/";
	public static final String DATA_PATH_ANNOTATION = "data/annotation.txt";
	public static final File outputFile = new File(DATA_PATH
			+ "spatialAnnotation.txt");
	public static BufferedWriter bw = null;

	public static void main(String testData) throws IOException {
		long startTime = System.currentTimeMillis();
		FileWriter FW = new FileWriter(outputFile, true);
		bw = new BufferedWriter(FW);
		INDArray TestArr = null;

		try (BufferedReader br = new BufferedReader(new FileReader(testData))) {
			String sCurrentLine;
			String[] sCurrentFullStrings;
			while ((sCurrentLine = br.readLine()) != null) {
				String currentLine = sCurrentLine.replace("[[", "");
				String finalLine = currentLine.replace("]]", "");
				// System.out.println(finalLine);
				sCurrentFullStrings = finalLine.split("->");
				TestArr = CreateND4JFromFeature(sCurrentFullStrings[1]);
				Map<String, Double> CosineSimilarityMap = CreateND4JArrayFromFeature(
						DATA_PATH + "SpatialDataset.txt", TestArr);

				Map.Entry<String, Double> maxEntry;
				String[] outputData = new String[10];
				String[] outputMap = new String[10];
				int outputRange = 10;
				String[] metaID = new String[outputRange];
				for (int i = 0; i < outputRange; i++) {
					maxEntry = FindMaximumMapPair(CosineSimilarityMap);
					// Double result =
					// CosineSimilarityMap.get(maxEntry.getKey());
					CosineSimilarityMap.remove(maxEntry.getKey());
					metaID[i] = maxEntry.getKey();
					// System.out.println(metaID[i]);
					String[] parts = metaID[i].split(":");
					outputData[i] = parts[0];
					outputMap[i] = metaID[i];

				}
				Map<String, Long> map = Arrays.stream(outputData).collect(
						Collectors.groupingBy(s -> s, Collectors.counting()));
				String max = map.entrySet().stream()
						.max(Map.Entry.comparingByValue()).get().getKey();
				for (String string : outputMap) {
					if (string.contains(max)) {
						// System.out.println(string);
						String out = getAnnotation(string);
						bw.append(out);
						bw.newLine();
						break;
					}
				}
				// System.out.println(max);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw.close();
		// System.out.println(maxEntry);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}

	@SuppressWarnings("unused")
	private static String getLabelData(Integer key) {
		int perKey = key;
		String Label = null;
		if (perKey <= 60) {
			Label = "aluminium_foil";
		} else if (perKey <= 120) {
			Label = "brown_bread";
		} else if (perKey <= 180) {
			Label = "corduroy";
		} else if (perKey <= 240) {
			Label = "cotton";
		} else if (perKey <= 300) {
			Label = "cracker";
		} else if (perKey <= 360) {
			Label = "linen";
		} else if (perKey <= 420) {
			Label = "orange_peel";
		} else if (perKey <= 480) {
			Label = "sandpaper";
		} else if (perKey <= 540) {
			Label = "sponge";
		} else if (perKey <= 600) {
			Label = "styrofoam";
		} else {
			Label = "Nothing: Failed";
		}

		return Label;
	}

	/*
	 * Find the maximum value from a Map Here Finding the biggest value from
	 * cosine similarity
	 * 
	 * @param Cosine Similarity Map value
	 * 
	 * @returns The Dataset Frame ID(Number) & Cosine Value
	 */
	private static Entry<String, Double> FindMaximumMapPair(
			Map<String, Double> cosineSimilarityMap) {
		Entry<String, Double> maxEntry = null;
		for (Map.Entry<String, Double> entry : cosineSimilarityMap.entrySet()) {
			if (maxEntry == null
					|| entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return maxEntry;
	}

	/*
	 * Measure Cosine Similarity between train and test data
	 * 
	 * @param trainData source
	 * 
	 * @param test ND4J array from feature
	 * 
	 * Return Training Feature as ND4J Array and Frame ID as Label
	 */
	private static Map<String, Double> CreateND4JArrayFromFeature(
			String DataPath, INDArray testArr) {
		Map<String, Double> CosineSimilarityMap = new HashMap<>();
		String DataLabel;
		double CosineValue;

		INDArray myArr = null;
		try (BufferedReader br = new BufferedReader(new FileReader(DataPath))) {
			String sCurrentLine;
			String[] sCurrentFullStrings;
			String[] sCurrentiStrings;

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);

				sCurrentFullStrings = sCurrentLine.split("->");
				DataLabel = sCurrentFullStrings[0];
				// System.out.println(label);
				sCurrentiStrings = sCurrentFullStrings[1].split(",");

				double[] nums = new double[sCurrentiStrings.length];
				for (int i = 0; i < nums.length; i++) {
					nums[i] = Double.parseDouble(sCurrentiStrings[i]);
				}

				myArr = Nd4j.create(nums);
				CosineValue = Transforms.cosineSim(myArr, testArr);
				CosineSimilarityMap.put(DataLabel, CosineValue);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return CosineSimilarityMap;
	}

	/*
	 * Create ND4J array from feature
	 * 
	 * @param Data Source (Test Data/Single frame/Image)
	 * 
	 * Return the test feature data
	 */
	private static INDArray CreateND4JFromFeature(String sCurrentLine) {
		INDArray myArr = null;
		String[] sCurrentiStrings;

		// System.out.println(sCurrentLine);

		sCurrentiStrings = sCurrentLine.split(",");

		double[] nums = new double[sCurrentiStrings.length];
		for (int i = 0; i < nums.length; i++) {
			// System.out.println(sCurrentiStrings[i]);
			nums[i] = Double.parseDouble(sCurrentiStrings[i]);
		}

		myArr = Nd4j.create(nums);

		return myArr;
	}

	private static String getAnnotation(String data) {
		// System.out.println(data);
		String string = "";
		String videoID = "";
		int frameID = 0;
		String[] dataParts = data.split(":");
		videoID = dataParts[0].replaceAll("\\s+", "");
		frameID = Integer.parseInt(dataParts[1].replaceAll("\\s+", ""));

		// System.out.println("videoID: " + videoID + " frame: " + frameID);
		try (BufferedReader br = new BufferedReader(new FileReader(
				DATA_PATH_ANNOTATION))) {
			String sCurrentLine;
			String[] sCurrentFullStrings;
			String[] sCurrentiStrings;

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentFullStrings = sCurrentLine.split("-");
				String DataLabel = sCurrentFullStrings[0]
						.replaceAll("\\s+", "");
				// System.out.println(DataLabel + "videoID:" + videoID);
				if (DataLabel.equalsIgnoreCase(videoID)) {
					sCurrentiStrings = sCurrentFullStrings[1].split(",");
					// for (String string2 : sCurrentiStrings) {
					// System.out.println(string2);
					// }
					string = sCurrentiStrings[frameID];
					System.out.println(string);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return string;
	}

}
