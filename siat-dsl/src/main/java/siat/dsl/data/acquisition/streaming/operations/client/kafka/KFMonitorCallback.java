package siat.dsl.data.acquisition.streaming.operations.client.kafka;


/**
 * <h1>SIAT Project</h1>
 * <h2>Layer: Data Storage Layer: Streaming Acquisition</h2>
 * <h3>Package Name: siat.dsl.data.acquisition.streaming.operations.client.kafka</h3>
 * <h3>Class Name: KFMonitorCallback</h3>
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


public interface KFMonitorCallback {

    /**
     * It is called when a Kafka broker has been started
     *
     * @param zkBrokerName Kafka broker name
     */
    void onBrokerUp(final String zkBrokerName);

    /**
     * It is called when a Kafka broker is down
     *
     * @param zkBrokerName Kafka broker name
     */
    void onBrokerDown(final String zkBrokerName);

    /**
     * It is called when a Kafka broker is up but it is not registered in Zookeeper
     *
     * @param zkBrokerName Kafka broker name
     */
    void onBrokerWarning(final String zkBrokerName);
}
