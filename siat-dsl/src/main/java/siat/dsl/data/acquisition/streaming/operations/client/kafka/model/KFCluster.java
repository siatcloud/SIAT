package siat.dsl.data.acquisition.streaming.operations.client.kafka.model;

/**
 * <h1>SIAT Project</h1>
 * <h2>Layer: Data Storage Layer: Streaming Acquisition</h2>
 * <h3>Package Name: siat.dsl.data.acquisition.streaming.operations.client.kafka.entities</h3>
 * <h3>Class Name: KFCluster</h3>
 * <p>
 * 
 * @Project This file is part of SIAT project.
 *          </p>
 * @author Aftab Alam <aftab@khu.ac.kr>
 * 
 * @version 1.0
 * @since 2018-27-11
 * 
 */

import siat.dsl.data.acquisition.streaming.operations.client.kafka.KFClusterStatusName;

import java.util.List;

/**
 * It represents a Kafka cluster
 */
public class KFCluster {

    private KFClusterStatusName kfClusterStatus;
    private List<KFBroker> kfBrokers;

    /**
     *
     * @param kfClusterStatus Kafka cluster status
     * @param kfBrokers List of kafka brokers
     */
    public KFCluster(final KFClusterStatusName kfClusterStatus,
                     final List<KFBroker> kfBrokers) {
        this.kfClusterStatus = kfClusterStatus;
        this.kfBrokers = kfBrokers;
    }

    public List<KFBroker> getKFBrokers() {
        return kfBrokers;
    }

    public KFClusterStatusName getKfClusterStatus() {
        return kfClusterStatus;
    }
}
