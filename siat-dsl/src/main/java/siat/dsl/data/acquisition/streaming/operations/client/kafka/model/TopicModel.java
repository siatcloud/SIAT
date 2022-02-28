package siat.dsl.data.acquisition.streaming.operations.client.kafka.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KafkaProdcuerModel")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * <h1>SIAT Project</h1>
 * <h2>Layer: Data Curation Layer</h2>
 * <h3>Package Name: siat.dsl.model</h3>
 * <h3>Class Name: TopicModel</h3>
 * <p>
 * 
 * @Project This file is part of SIAT project.
 *          </p>
 *          <p>
 * @Description: This class is used as a Topic model.
 *               </p>
 * 
 * @author Aftab Alam <aftab@khu.ac.kr>
 * 
 * @version 4.0
 * @since 2018-07-27
 **/

public class TopicModel {

	private String topicName = "";
	private int partitions = 0;
	private int replicationFactor = 0;
	private boolean isDeleted = false; 



	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public int getPartitions() {
		return partitions;
	}

	public void setPartitions(int partitions) {
		this.partitions = partitions;
	}

	public int getReplicationFactor() {
		return replicationFactor;
	}

	public void setReplicationFactor(int replicationFactor) {
		this.replicationFactor = replicationFactor;
	}

}
