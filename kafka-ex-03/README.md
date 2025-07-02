Kafka Exercise 03 - Student Producer-Consumer
This project demonstrates sending Java Student objects to Kafka using Producer and reading them with Consumer, printing to console.
Project Structure
kafka-ex-03/
├── README.md
├── pom.xml
└── src/main/java/com/kafka/student/
    ├── Student.java          # Student model class
    ├── StudentProducer.java  # Producer application
    └── StudentConsumer.java  # Consumer application
Requirements

Java 11+
Maven 3.6+
Docker with running Kafka cluster
Kafka broker at localhost:9092

Quick Start
1. Compile Project
bashmvn clean compile
2. Create Kafka Topic
bashdocker exec -it broker kafka-topics --create --topic student-topic --partitions 3 --replication-factor 1 --bootstrap-server localhost:9092
3. Start Consumer (Terminal 1)
bashmvn exec:java -Dexec.mainClass="com.kafka.student.StudentConsumer"
4. Run Producer (Terminal 2)
bashmvn exec:java -Dexec.mainClass="com.kafka.student.StudentProducer"
Sample Output
Producer:
*** Starting Student Producer ***
### Sending Student 1 ###
Key: student-1
Value: {"id":1,"name":"Ali Yılmaz"}
### All students sent successfully! ###
Consumer:
📚 Received Student:
   offset = 0, key = student-1
   Student ID: 1
   Student Name: Ali Yılmaz
   JSON: {"id":1,"name":"Ali Yılmaz"}
   --------------------------------
Sample Data
The project sends 5 student objects:

Ali Yılmaz (ID: 1)
Ayşe Demir (ID: 2)
Mehmet Kaya (ID: 3)
Fatma Özkan (ID: 4)
Can Şahin (ID: 5)

Technologies Used

Apache Kafka 3.5.0
Jackson for JSON serialization
Maven for build management

Exercise completed successfully! ✅
