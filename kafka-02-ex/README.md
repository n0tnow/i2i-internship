# 🌀 Kafka All-in-One Setup with Docker Compose

This project provides a complete Confluent Kafka stack running locally via Docker Compose. It includes essential Kafka components such as Zookeeper, Kafka Broker, Schema Registry, Kafka Connect, Control Center, REST Proxy, and ksqlDB.

---

## 📦 Stack Components

| Service           | Description                                 | Port     |
|------------------|---------------------------------------------|----------|
| Zookeeper         | Kafka coordination service                  | `2181`   |
| Kafka Broker      | Core Kafka broker                           | `9092`, `29092` |
| Schema Registry   | Manages Avro schemas                        | `8081`   |
| Kafka Connect     | Source/Sink connector framework             | `8083`   |
| Control Center    | Kafka monitoring dashboard                  | `9021`   |
| REST Proxy        | REST interface for Kafka                    | `8082`   |
| ksqlDB Server     | Stream processing with SQL                  | `8088`   |
| ksqlDB CLI        | Command-line interface for ksqlDB           | -        |
| Data Generator    | Pre-built topic and data initialization     | -        |

---

## 🚀 Getting Started

### 1. Clone the Repository or Create the Folder

```bash
mkdir kafka-all-in-one
cd kafka-all-in-one
```

### 2. Add the Docker Compose File

Create a file named `docker-compose.yml` and paste the full configuration from Confluent’s official repo (already provided).

### 3. Start the Stack

```bash
docker compose up -d
```

This will pull the required images and start all services in the background.

---

## ✅ Validate Setup

### Check Running Containers

```bash
docker ps --format "table {{.Names}}\t{{.Image}}"
```

### Access Control Center UI

Visit: [http://localhost:9021](http://localhost:9021)

---

## 🧪 Test Kafka with CLI

### Create a Topic

```bash
docker exec -it broker kafka-topics \
  --create --topic test-topic \
  --bootstrap-server broker:29092 \
  --partitions 1 --replication-factor 1
```

### Produce Messages

```bash
docker exec -it broker kafka-console-producer \
  --topic test-topic \
  --bootstrap-server broker:29092
```

Type some messages and press Enter.

### Consume Messages

```bash
docker exec -it broker kafka-console-consumer \
  --topic test-topic \
  --from-beginning \
  --bootstrap-server broker:29092
```

---

## 🔧 Useful Commands

### View All Topics

```bash
docker exec -it broker kafka-topics --list --bootstrap-server broker:29092
```

### Monitor Topic Offsets

```bash
docker exec -it broker kafka-run-class kafka.tools.GetOffsetShell \
  --broker-list broker:29092 \
  --topic test-topic \
  --time -1
```

---

## 📌 Notes

- This stack is for **local development** and testing purposes only.
- Ensure Docker is installed and running on your local machine.
- Schema Registry is integrated with Kafka Connect and ksqlDB.
- Control Center is available at port `9021` for full cluster visibility.

---

## 🧐 Author

Bilal Kaya – for educational and development use.  
Confluent Platform: [https://docs.confluent.io/](https://docs.confluent.io/)
