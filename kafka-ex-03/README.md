# Kafka Exercise 03: Student Producer-Consumer Application

A Java application demonstrating Apache Kafka Producer-Consumer pattern by sending Student objects as JSON messages and consuming them in real-time.

## Overview

This project implements a complete Kafka messaging system where:
- **Producer** creates Student objects and sends them to Kafka topic as JSON
- **Consumer** reads JSON messages and deserializes them back to Student objects
- **Real-time processing** with console output for monitoring

## Features

- ✅ Java object serialization to JSON
- ✅ Kafka Producer with key-value messaging
- ✅ Kafka Consumer with real-time processing
- ✅ Error handling and logging
- ✅ Docker integration with existing Kafka cluster

## Project Structure

```
kafka-ex-03/
├── README.md
├── pom.xml                                 # Maven dependencies
└── src/
    └── main/
        └── java/
            └── com/
                └── kafka/
                    └── student/
                        ├── Student.java          # POJO model class
                        ├── StudentProducer.java  # Kafka Producer
                        └── StudentConsumer.java  # Kafka Consumer
```

## Prerequisites

- **Java 11** or higher
- **Maven 3.6+** 
- **Docker** with running Kafka cluster
- **Kafka Broker** accessible at `localhost:9092`

## Dependencies

- Apache Kafka Client 3.5.0
- Jackson Databind 2.15.2 (JSON serialization)
- SLF4J Simple 2.0.7 (Logging)

## Installation & Setup

### 1. Clone/Download Project
```bash
cd your-project-directory/kafka-ex-03
```

### 2. Build Project
```bash
mvn clean compile
```

### 3. Create Kafka Topic
```bash
docker exec -it broker kafka-topics \
  --create \
  --topic student-topic \
  --partitions 3 \
  --replication-factor 1 \
  --bootstrap-server localhost:9092
```

### 4. Verify Topic Creation
```bash
docker exec -it broker kafka-topics --list --bootstrap-server localhost:9092
```

## Running the Application

### Step 1: Start Consumer (Terminal 1)
```bash
mvn exec:java -Dexec.mainClass="com.kafka.student.StudentConsumer"
```

**Expected Output:**
```
*** Starting Student Consumer ***
Subscribed to student-topic. Waiting for messages...
Press Ctrl+C to stop
----------------------------------------
```

### Step 2: Run Producer (Terminal 2)
Open a new terminal in the same directory:
```bash
mvn exec:java -Dexec.mainClass="com.kafka.student.StudentProducer"
```

## Sample Execution Results

### Producer Output:
```
*** Starting Student Producer ***
### Sending Student 1 ###
Key: student-1
Value: {"id":1,"name":"Ali Yılmaz"}
### Sending Student 2 ###
Key: student-2
Value: {"id":2,"name":"Ayşe Demir"}
### Sending Student 3 ###
Key: student-3
Value: {"id":3,"name":"Mehmet Kaya"}
### Sending Student 4 ###
Key: student-4
Value: {"id":4,"name":"Fatma Özkan"}
### Sending Student 5 ###
Key: student-5
Value: {"id":5,"name":"Can Şahin"}
### All students sent successfully! ###
```

### Consumer Output:
```
📚 Received Student:
   offset = 0, key = student-1
   Student ID: 1
   Student Name: Ali Yılmaz
   JSON: {"id":1,"name":"Ali Yılmaz"}
   --------------------------------
📚 Received Student:
   offset = 1, key = student-2
   Student ID: 2
   Student Name: Ayşe Demir
   JSON: {"id":2,"name":"Ayşe Demir"}
   --------------------------------
```

## Data Model

The application processes the following student records:

| Student ID | Name |
|------------|------|
| 1 | Ali Yılmaz |
| 2 | Ayşe Demir |
| 3 | Mehmet Kaya |
| 4 | Fatma Özkan |
| 5 | Can Şahin |

## Key Configuration

### Producer Settings:
- **Bootstrap Servers:** `localhost:9092`
- **Key Serializer:** StringSerializer
- **Value Serializer:** StringSerializer (JSON)
- **Client ID:** `student-producer`

### Consumer Settings:
- **Bootstrap Servers:** `localhost:9092`
- **Group ID:** `student-consumer`
- **Key Deserializer:** StringDeserializer
- **Value Deserializer:** StringDeserializer
- **Auto Offset Reset:** `earliest`

## Troubleshooting

### Java Version Error
If you encounter `UnsupportedClassVersionError`:
```bash
# Use Maven exec plugin (recommended)
mvn exec:java -Dexec.mainClass="com.kafka.student.StudentConsumer"
```

### Connection Issues
Verify Kafka cluster is running:
```bash
docker ps
docker exec -it broker kafka-topics --list --bootstrap-server localhost:9092
```

### Topic Not Found
Check if topic exists:
```bash
docker exec -it broker kafka-topics --describe --topic student-topic --bootstrap-server localhost:9092
```

## Learning Outcomes

This exercise demonstrates:
- Kafka Producer-Consumer pattern implementation
- JSON serialization/deserialization in Java
- Kafka topic and partition management
- Real-time message processing
- Error handling in distributed systems
