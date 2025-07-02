package com.kafka.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class StudentProducer {
    public static final String TOPIC = "student-topic";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        System.out.println("*** Starting Student Producer ***");
        
        Properties settings = new Properties();
        settings.put("client.id", "student-producer");
        settings.put("bootstrap.servers", "localhost:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(settings)) {
            // Create sample students
            Student[] students = {
                new Student(1, "Ali Yılmaz"),
                new Student(2, "Ayşe Demir"), 
                new Student(3, "Mehmet Kaya"),
                new Student(4, "Fatma Özkan"),
                new Student(5, "Can Şahin")
            };
            
            for (int i = 0; i < students.length; i++) {
                Student student = students[i];
                final String key = "student-" + student.getId();
                
                try {
                    // Convert Student object to JSON string
                    final String value = objectMapper.writeValueAsString(student);
                    
                    System.out.println("### Sending Student " + (i + 1) + " ###");
                    System.out.println("Key: " + key);
                    System.out.println("Value: " + value);
                    
                    producer.send(new ProducerRecord<>(TOPIC, key, value));
                    
                    // Small delay between messages
                    Thread.sleep(1000);
                    
                } catch (Exception e) {
                    System.err.println("Error serializing student: " + e.getMessage());
                }
            }
            
            System.out.println("### All students sent successfully! ###");
            
        } catch (Exception e) {
            System.err.println("Producer error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}