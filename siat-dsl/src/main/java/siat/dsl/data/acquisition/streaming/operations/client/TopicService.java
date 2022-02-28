package siat.dsl.data.acquisition.streaming.operations.client;

import siat.dsl.data.acquisition.streaming.operations.client.common.ClusterConnection;
import siat.dsl.data.acquisition.streaming.operations.client.common.ClusterTools;
import siat.dsl.data.acquisition.streaming.operations.client.configuration.PropertyNames;
import siat.dsl.data.acquisition.streaming.operations.client.exception.TopicOperationException;
import kafka.utils.ZkUtils;
import org.apache.commons.lang3.StringUtils;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <h1>SIAT Project</h1>
 * <h2>Layer: Data Storage Layer: Streaming Acquisition</h2>
 * <h3>Package Name: siat.dsl.data.acquisition.streaming.operations.client</h3>
 * <h3>Class Name: TopicService</h3>
 * <p>
 * 
 * @Project This file is part of SIAT project.
 *          </p>
 * @author Aftab Alam <aftab@khu.ac.kr>
 * 
 * @version 1.0
 * @since 2018-27-11

 * Topic Management API
 */
public class TopicService implements AutoCloseable {

    private final Map<String, String> configuration;
    private ClusterConnection connection;
    private final ClusterTools clusterTools;

    /**
     * @param topicServiceConfiguration topic service configuration
     */
    public TopicService(final Map<String, String> topicServiceConfiguration) {
        this.configuration = topicServiceConfiguration;
        clusterTools = new ClusterTools();
    }

    /**
     * Verify if the topic exists
     *
     * @param topicName topic name
     * @return true if topic exists otherwise return false
     * @throws siat.dsl.data.acquisition.streaming.operations.client.exception.ConnectionException if Zookeeper connection fails
     */
    public boolean topicExists(final String topicName) {
        return clusterTools.topicExists(getConnection(), topicName);
    }

    /**
     * Create a topic
     *
     * @param topicName         Topic name
     * @param partitions        The number of partitions for the topic being created
     * @param replicationFactor The replication factor for each partition in the topic being created
     * @param topicProperties   A topic configuration override for an existing topic
     * @throws IllegalArgumentException                                                 if any argument is invalid
     * @throws TopicOperationException                                                  when it could not create a topic
     * @throws siat.dsl.data.acquisition.operations.client.exception.ConnectionException if Zookeeper connection fails
     */
    public void createTopic(final String topicName,
                            final int partitions,
                            final int replicationFactor,
                            final Properties topicProperties) {

        validateTopicName(topicName);

        if (topicProperties == null) {
            throw new IllegalArgumentException("Topic properties cannot be null");
        }

        if (topicExists(topicName)) {
            throw new TopicOperationException(topicName, "Topic " + topicName + " already exists", null, this.getClass());
        }

        clusterTools.createTopic(getConnection(),
                topicName,
                partitions,
                replicationFactor,
                topicProperties);
    }


    /**
     * Create a topic
     * @param topicName    Topic name
     * @param partitions   The number of partitions for the topic being created
     * @param replicationFactor The replication factor for each partition in the topic being created
     * @throws IllegalArgumentException if any argument is invalid
     * @throws TopicOperationException  when it could not create a topic
     * @throws siat.dsl.data.acquisition.streaming.operations.client.exception.ConnectionException if Zookeeper connection fails
     */
    public void createTopic(final String topicName,
                            final int partitions,
                            final int replicationFactor) {

        clusterTools.createTopic(getConnection(),
                topicName,
                partitions,
                replicationFactor,
                new Properties());
    }
    
    
    /**
     * Create a topic
     * @param topicName    Topic name
     * @throws IllegalArgumentException if any argument is invalid
     * @throws TopicOperationException  when it could not create a topic
     * @throws siat.dsl.data.acquisition.streaming.operations.client.exception.ConnectionException if Zookeeper connection fails
     */
    public void deleteTopic(final String topicName) {

        clusterTools.deleteTopic(getConnection(), topicName); 
        
    }

    /**
     * Get all the topics
     *
     * @return a list of all topics. If no topics are available, a empty list is returned.
     *
     * @throws siat.dsl.data.acquisition.streaming.operations.client.exception.ConnectionException if Zookeeper connection fails
     */
    public List<String> getAllTopics() {
        List<String> allTopics = new ArrayList<>();
        final Seq<String> allTopicsSeq = getConnection().getAllTopics();
        if (allTopicsSeq != null) {
            allTopics = scala
                    .collection
                    .JavaConversions
                    .seqAsJavaList(allTopicsSeq);
        }
        return allTopics;
    }

    /**
     * Override topic properties.
     *
     * @param topicName topic name
     * @param topicProperties topic properties
     * @throws IllegalArgumentException                                                     when topicName or topicProperties is empty
     *                                                                                      or null.
     * @throws siat.dsl.data.acquisition.streaming.operations.client.exception.TopicOperationException when configuration could not be overridden
     * @throws siat.dsl.data.acquisition.operations.client.exception.ConnectionException     if Zookeeper connection fails
     */
    public void overrideTopicProperties(final String topicName,
                                        final Properties topicProperties) {
        validateTopicName(topicName);

        if (topicProperties == null) {
            throw new IllegalArgumentException("Topic properties cannot be null");
        }
        clusterTools.overrideTopicProperties(getConnection(), topicName, topicProperties);
    }


    /**
     * Close cluster connection
     */
    public void close() {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Get Topic Properties
     *
     * @param topicName topic name
     * @return topic properties
     * @throws IllegalArgumentException                                                     when topicName is empty or null.
     * @throws siat.dsl.data.acquisition.streaming.operations.client.exception.TopicOperationException when topicName does not exist or
     *                                                                                      configuration could not be overridden
     * @throws com.mcafee.dxl.streaming.operations.client.exception.ConnectionException     if Zookeeper connection fails
     */
    public Properties getTopicProperties(final String topicName) {

        validateTopicName(topicName);

        if (!topicExists(topicName)) {
            throw new TopicOperationException(topicName, "Topic " + topicName + " does not exist", null, this.getClass());
        }

        return clusterTools.getTopicProperties(getConnection(), topicName);
    }


    /**
     * Get a Zookeeper connection
     *
     * @return Zookeeper connection
     * @throws siat.dsl.data.acquisition.streaming.operations.client.exception.ConnectionException when Zookeeper connection failed
     */
    private ZkUtils getConnection() {

        if (this.connection == null) {

            String zkServers = configuration.getOrDefault(PropertyNames.ZK_SERVERS.getPropertyName(), null);

            String connectionTimeoutMS = configuration.getOrDefault(PropertyNames.ZK_CONNECTION_TIMEOUT_MS.getPropertyName(),
                    PropertyNames.ZK_CONNECTION_TIMEOUT_MS.getDefaultValue());

            String sessionTimeoutMS = configuration.getOrDefault(PropertyNames.ZK_SESSION_TIMEOUT_MS.getPropertyName(),
                    PropertyNames.ZK_SESSION_TIMEOUT_MS.getDefaultValue());

            this.connection = new ClusterConnection(zkServers, connectionTimeoutMS, sessionTimeoutMS);
        }

        return this.connection.getConnection();
    }


    /**
     * Validate Topic Name.
     *
     * @param topicName Topic Name to be validated
     * @throws IllegalArgumentException when topicName is empty or null
     */
    private void validateTopicName(final String topicName) {
        if (StringUtils.isEmpty(topicName)) {
            throw new IllegalArgumentException("Topic name cannot be null or empty");
        }
    }

}
