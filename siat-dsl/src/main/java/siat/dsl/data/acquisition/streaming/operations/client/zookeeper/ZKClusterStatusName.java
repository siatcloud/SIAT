package siat.dsl.data.acquisition.streaming.operations.client.zookeeper;

/**
 * It enumerates the cluster status names
 */
public enum ZKClusterStatusName {
    /**
     * Zookeeper cluster has quorum. It is working.
     */
    QUORUM,

    /**
     * Zookeeper cluster has no quorum. It means that zookeeper cluster is not working
     */
    NO_QUORUM
}
