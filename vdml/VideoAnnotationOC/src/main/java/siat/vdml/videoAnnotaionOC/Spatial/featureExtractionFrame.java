package siat.vdml.videoAnnotaionOC.Spatial;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import org.opencv.core.Mat;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.VGG16;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;

public class featureExtractionFrame {
	/** Location to save and extract the training/testing data */

	public static void main(String fileName, int frameNumber, Mat frame,
			BufferedWriter bw) throws IOException {

		ComputationGraph vgg16transfer = getComputationGraph();

		Map<String, INDArray> stringINDArrayMap = extractTwo(frame,
				vgg16transfer);

		bw.append(fileName + ":" + frameNumber + "->"
				+ stringINDArrayMap.get("predictions").toString() + "\n");
		bw.flush();
		 System.out.println(frameNumber);
		// stringINDArrayMap.get("predictions").toString());
		// try {
		// siat.cluster.hdfsReadWrite.app.main(fileName + " ");
		// siat.cluster.hdfsReadWrite.app.main(" "
		// + stringINDArrayMap.get("predictions").toString() + "\n");
		// } catch (Exception e) {
		// try {
		// siat.cluster.hdfsReadWrite.app
		// .main("Error for writing CNN Feature: " + e + "\n");
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// }

	}

	@SuppressWarnings("rawtypes")
	public static ComputationGraph getComputationGraph() throws IOException {
		ZooModel zooModel = VGG16.builder().build();
		// ZooModel zooModel = AlexNet.builder().build();
		// ZooModel zooModel = Darknet19.builder().build();

		// return (ComputationGraph) zooModel.initPretrained();
		return (ComputationGraph) zooModel
				.initPretrained(PretrainedType.IMAGENET);
	}

	/**
	 * Given an input image and a ComputationGraph it calls the feedForward
	 * method after rescaling the image.
	 * 
	 * @param imageFile
	 *            the image whose features need to be extracted
	 * @param vgg16
	 *            the ComputationGraph to be used.
	 * @return a map of activations for each layer
	 * @throws IOException
	 */
	public static Map<String, INDArray> extractTwo(Mat imageFile,
			ComputationGraph vgg16) throws IOException {
		// Convert file to INDArray
		NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
		INDArray image = loader.asMatrix(imageFile);

		// Mean subtraction pre-processing step for VGG
		DataNormalization scaler = new VGG16ImagePreProcessor();
		scaler.transform(image);

		// Call the feedForward method to get a map of activations for each
		// layer
		return vgg16.feedForward(image, true);
	}
}
/**
 * 
 * 
 * block2_conv2
 * 
 * block1_pool
 * 
 * block2_conv1
 * 
 * block3_conv3
 * 
 * block3_pool
 * 
 * block3_conv1
 * 
 * predictions
 * 
 * block3_conv2
 * 
 * block5_conv2
 * 
 * block5_pool
 * 
 * flatten
 * 
 * block5_conv1
 * 
 * block2_pool
 * 
 * block4_conv1
 * 
 * fc2
 * 
 * fc1
 * 
 * block4_conv3
 * 
 * block4_conv2
 * 
 * input_1
 * 
 * block4_pool
 * 
 * block1_conv1
 * 
 * block1_conv2
 * 
 * block5_conv3
 */

