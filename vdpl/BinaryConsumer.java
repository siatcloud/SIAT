/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siat.dpl;


import siat.dl.HBase;
import siat.dl.Frame;
import siat.dl.FrameStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


/**
 *
 * @author user
 */
public class BinaryConsumer extends Thread{
    Properties props;
    KafkaConsumer<String, byte[]> consumer;
    String outDir;
    String topic;
    
    private volatile boolean running = true;
    
    public BinaryConsumer(String IP, String Topic, String OutDir) throws IOException
    {
        //"163.180.118.172:9092"
        topic = Topic;
        props = new Properties();
        props.put("bootstrap.servers", IP);
        props.put("group.id", "test-consumer-group");
        props.put("enable.auto.commit", "true");
        props.put("compression.type", "snappy");
        props.put("fetch.message.max.bytes", "7340032");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        outDir=OutDir;
        //"/home/dke/NetBeansProjects/outputs"
    }
    
    public void createConsumer()
    {
        consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList(topic));
        
    }
    
    
    public void terminate(){
        running = false;
    }
    
    @Override
    public void run()
    {
        String name = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HBase hbase;

        hbase = new HBase("localhost");
        FrameStore.CreateTable(hbase.hbase);

        int rowkey = 0;
        while(running)
        {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);
            for(ConsumerRecord<String, byte[]> record : records)
            {
                if(record.value()==null)
                {
                    rowkey++;
                    System.out.println("Writing file "+name);
                    try {
                        writeFile(name,bos.toByteArray());
                        Frame f = new Frame("jpg", bos.toString(), "ttt");
                        FrameStore.InsertData(String.valueOf(rowkey), f);
                    } catch (IOException ex) {
                        Logger.getLogger(BinaryConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    bos.reset();
                }
                else
                {
                    name = record.key();
                    try {
                        bos.write(record.value());
                    } catch (IOException ex) {
                        Logger.getLogger(BinaryConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    private void writeFile(String name, byte[] rawdata) throws IOException
    {
        File f = new File(outDir);
        if(!f.exists())
            f.mkdir();
        
        FileOutputStream fos = new FileOutputStream(outDir+File.separator+name);
        fos.write(rawdata);
        fos.flush();
        fos.close();
    }
    
    /*public static void featureExtractor_testData(String[] args) throws ZooKeeperConnectionException, IOException
    {
        
        
     
        
        try{
            BinaryConsumer bc;
            bc = new BinaryConsumer();
            bc.createConsumer();
            bc.start();
        } catch(IOException ex){
            Logger.getLogger(BinaryConsumer.class.getName()).log(Level.SEVERE,null, ex);
        }
    }*/
}
