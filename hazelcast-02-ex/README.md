# Hazelcast EX-02: Local Cluster Setup with Management Center

This exercise demonstrates how to set up a basic Hazelcast cluster node along with the Management Center using Docker.

## 🐳 Requirements
- Docker must be installed on your local machine.

## ⚙️ Setup Steps

1. **Pull Hazelcast Node Image**
   ```bash
   docker pull hazelcast/hazelcast:latest
   ```

2. **Pull Hazelcast Management Center Image**
   ```bash
   docker pull hazelcast/management-center:latest
   ```

3. **(Optional) Create a Docker Network**
   ```bash
   docker network create hazelcast-net
   ```

4. **Run Hazelcast Node**
   ```bash
   docker run -d --name hazelcast-node --network hazelcast-net \
   -e HZ_CLUSTERNAME=hazelcast-ex-02-i2i \
   hazelcast/hazelcast:latest
   ```

5. **Run Management Center**
   ```bash
   docker run --rm --name hazelcast-mc -p 8080:8080 --network hazelcast-net \
   hazelcast/management-center:latest
   ```

6. **Access the Web UI**
   - Open your browser and go to: [http://localhost:8080](http://localhost:8080)
   - Connect to the cluster using:
     ```
     Cluster Name: hazelcast-ex-02-i2i
     Cluster Member: hazelcast-node:5701
     ```

## ✅ Result
Hazelcast Node and Management Center are successfully running and connected inside a shared Docker network. You can now monitor and manage the cluster via the web interface.

---

> Docker Hub:  
> - Hazelcast: https://hub.docker.com/r/hazelcast/hazelcast  
> - Management Center: https://hub.docker.com/r/hazelcast/management-center
