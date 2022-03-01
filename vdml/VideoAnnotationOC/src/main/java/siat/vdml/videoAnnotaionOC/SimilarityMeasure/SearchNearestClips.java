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

public class SearchNearestClips {
	/** Location to save Cosine data */
	// public static final String DATA_PATH = "D:/Data/Annotation/video data/";
	public static final String DATA_PATH = "data/";
	public static final File outputFile = new File(DATA_PATH
			+ "DynamicAnnotation.txt");
	public static BufferedWriter bw = null;
	

	public static void main(String testData) throws IOException {
		long startTime = System.currentTimeMillis();
		INDArray TestArr = null;
		FileWriter FW = new FileWriter(outputFile);
		bw = new BufferedWriter(FW);

		TestArr = CreateND4JFromFeature(testData);
		Map<Integer, Double> CosineSimilarityMap = CreateND4JArrayFromFeature(
				DATA_PATH + "DynamicDataset.txt", TestArr);
		// System.out.println(Transforms.cosineSim(TrainArr, TrainArr));

		// System.out.println(TrainArr);
		// for (Integer string : CosineSimilarityMap.keySet()) {
		// int a = string;
		// Double b = CosineSimilarityMap.get(string);
		// System.out.println(a + " " + b);
		// }

		Map.Entry<Integer, Double> maxEntry = null;
		String[] outputData = new String[10];

		int outputRange = 10;
		int[] metaID = new int[outputRange];
		for (int i = 0; i < outputRange; i++) {
			maxEntry = FindMaximumMapPair(CosineSimilarityMap);
			// Double result = CosineSimilarityMap.get(maxEntry.getKey());
			CosineSimilarityMap.remove(maxEntry.getKey());
			metaID[i] = maxEntry.getKey();
			String output = getLabelData(metaID[i]);
			String Label = getLabelData(maxEntry.getKey());
//			System.out.println(output);
			outputData[i] = output;
		}

//		for (int i = 0; i < outputData.length; i++) {
//			System.out.println(outputData[i]);
//		}
		Map<String, Long> map = Arrays.stream(outputData)
			    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
		String max=map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
		bw.append(max);
		bw.close();

//		 System.out.println(max);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}

	private static String getLabelData(Integer key) {
		int perKey = key;
		String Label = null;
		if (perKey <= 40) {
			Label = "BandMarching in Road";
		} else if (perKey <= 80) {
			Label = "Car Running on Road";
		} else if (perKey <= 120) {
			Label = "Explosion in Building, smoke";
		} else if (perKey <= 155) {
			Label = "Tornado";
		} else {
			Label = "Not Found";
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
	private static Entry<Integer, Double> FindMaximumMapPair(
			Map<Integer, Double> cosineSimilarityMap) {
		Map.Entry<Integer, Double> maxEntry = null;
		for (Map.Entry<Integer, Double> entry : cosineSimilarityMap.entrySet()) {
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
	private static Map<Integer, Double> CreateND4JArrayFromFeature(
			String DataPath, INDArray testArr) {
		Map<Integer, Double> CosineSimilarityMap = new HashMap<>();
		int DataLabel;
		double CosineValue;

		INDArray myArr = null;
		try (BufferedReader br = new BufferedReader(new FileReader(DataPath))) {
			String sCurrentLine;
			String[] sCurrentFullStrings;
			String[] sCurrentiStrings;

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);

				sCurrentFullStrings = sCurrentLine.split(":");
				DataLabel = Integer.parseInt(sCurrentFullStrings[0]);
				// System.out.println(label);
				sCurrentiStrings = sCurrentFullStrings[1].split(",");

				double[] nums = new double[sCurrentiStrings.length];
				for (int i = 0; i < nums.length; i++) {
					// System.out.println(sCurrentiStrings[i]);
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
	private static INDArray CreateND4JFromFeature(String DataPath) {
		INDArray myArr = null;
		try (BufferedReader br = new BufferedReader(new FileReader(DataPath))) {
			String sCurrentLine;
			String[] sCurrentFullStrings;
			String[] sCurrentiStrings;

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);

				sCurrentFullStrings = sCurrentLine.split(":");
				sCurrentiStrings = sCurrentFullStrings[1].split(",");
				// for (String string : sCurrentiStrings) {
				// System.out.println(string);
				// }

				double[] nums = new double[sCurrentiStrings.length];
				for (int i = 0; i < nums.length; i++) {
					// System.out.println(sCurrentiStrings[i]);
					nums[i] = Double.parseDouble(sCurrentiStrings[i]);
				}

				myArr = Nd4j.create(nums);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return myArr;
	}

}
