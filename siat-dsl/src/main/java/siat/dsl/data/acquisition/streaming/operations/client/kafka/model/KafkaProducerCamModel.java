package siat.dsl.data.acquisition.streaming.operations.client.kafka.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KafkaProdcuerModel")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * <h1>SIAT Project</h1>
 * <h2>Layer: Data Storage Layer</h2>
 * <h3>Package Name: siat.dsl.model</h3>
 * <h3>Class Name: ALGORITHM</h3>
 * 
 * <p>
 * 
 * @Project This file is part of SIAT project.
 *          </p>
 *          <p>
 * @Description: This class is used as an ALGORITHM model.
 *               </p>
 * 
 * @author Aftab Alam <aftab@khu.ac.kr>
 * 
 * @version 1.0
 * @since 2018-07-27
 **/

public class KafkaProducerCamModel {
	private String topicName = "";
	private String cameraId = "";
	private String cameraUrl = "";

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getCameraUrl() {
		return cameraUrl;
	}

	public void setCameraUrl(String cameraUrl) {
		this.cameraUrl = cameraUrl;
	}
}
