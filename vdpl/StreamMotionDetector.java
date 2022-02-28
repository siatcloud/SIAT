package siat.dpl;

import siat.dao.RealTimeDSDao;
import siat.entities.RealtimeDs;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author siyamul
 * @since 2018-08-02
 */
public class StreamMotionDetector {

    /**
     * this methods apply detection
     */
    public void detect(int serviceSuvscriptionid, int realtimeId) {
        try {
            String topic = "realtime-" + serviceSuvscriptionid;

            RealTimeDSDao realTimeDSDao = new RealTimeDSDao();
            RealtimeDs realtimeDs = realTimeDSDao.getRealTimeSourceById(realtimeId);

            Runtime.getRuntime().exec("sh /home/siyamul/kafka_2.11-0.10.2.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --topic " + topic + " --replication-factor 1 --partitions 3");

            Runtime.getRuntime().exec("mvn clean package exec:java -Dexec.mainClass=com.iot.video.app.spark.processor.VideoStreamProcessor -f /home/siyamul/video-stream-analytics-master/video-stream-processor/pom.xml -Dexec.args=motion_#" + serviceSuvscriptionid + "_#" + topic);

            Thread.sleep(7000);
//
            Runtime.getRuntime().exec("mvn clean package exec:java -Dexec.mainClass=com.iot.video.app.kafka.collector.VideoStreamCollector -Dexec.cleanupDaemonThreads=false -f /home/siyamul/video-stream-analytics-master/video-stream-collector/pom.xml -Dexec.args=" + realtimeDs.getCameraLink() + "_#cam-" + realtimeDs.getId() + "_#" + topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
