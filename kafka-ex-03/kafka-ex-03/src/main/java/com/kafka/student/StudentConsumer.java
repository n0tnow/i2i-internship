package com.kafka.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class StudentConsumer {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        System.out.println("*** Starting Student Consumer ***");
        
        Properties settings = new Properties();
        settings.put(ConsumerConfig.GROUP_ID_CONFIG, "student-consumer");
        settings.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        settings.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        settings.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        settings.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        settings.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(settings)) {
            consumer.subscribe(Collections.singletonList("student-topic"));
            
            System.out.println("Subscribed to student-topic. Waiting for messages...");
            System.out.println("Press Ctrl+C to stop");
            System.out.println("----------------------------------------");
            
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        // Parse JSON string back to Student object
                        Student student = objectMapper.readValue(record.value(), Student.class);
                        
                        System.out.printf("📚 Received Student:%n");
                        System.out.printf("   offset = %d, key = %s%n", record.offset(), record.key());
                        System.out.printf("   Student ID: %d%n", student.getId());
                        System.out.printf("   Student Name: %s%n", student.getName());
                        System.out.printf("   JSON: %s%n", record.value());
                        System.out.println("   --------------------------------");
                        
                    } catch (Exception e) {
                        System.err.println("Error deserializing student: " + e.getMessage());
                        System.err.println("Raw value: " + record.value());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Consumer error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}