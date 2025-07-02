# Hazelcast EX-03: Distributed Map Operations with 10,000 Person Objects

This exercise demonstrates putting 10,000 dummy Person objects into a Hazelcast distributed map using a for loop, then retrieving all objects from the map. Uses the Hazelcast server from HAZELCAST-EX-02.

## 🔧 Requirements

- Java 11 or higher installed
- Maven 3.6 or higher installed
- Docker installed and running
- HAZELCAST-EX-02 cluster setup completed

## ⚙️ Setup Steps

1. **Ensure HAZELCAST-EX-02 is Running**
   ```bash
   # Start Hazelcast node with proper port mapping
   docker run -d --name hazelcast-node \
   -p 5701:5701 \
   -e HZ_CLUSTERNAME=hazelcast-ex-02-i2i \
   hazelcast/hazelcast:latest
   ```

2. **Verify Docker Container Status**
   ```bash
   docker ps | grep hazelcast-node
   ```
   Expected output:
   ```
   CONTAINER ID   IMAGE                    PORTS                    NAMES
   xxxxxxxx       hazelcast/hazelcast      0.0.0.0:5701->5701/tcp   hazelcast-node
   ```

3. **Navigate to Project Directory**
   ```bash
   cd hazelcast-ex-03
   ```

4. **Compile the Project**
   ```bash
   mvn clean compile
   ```

5. **Run the Exercise**
   ```bash
   mvn exec:java
   ```

## 📊 Expected Output

```
╔════════════════════════════════════════╗
║        HAZELCAST EXERCISE 03           ║
║   10,000 Person Objects Demo          ║
╚════════════════════════════════════════╝

📡 Connecting to HAZELCAST-EX-02 Docker server...
✅ Successfully connected to Hazelcast cluster!
🧹 Map cleared for demo (previous size: 0)

📦 STEP 3: Putting 10000 dummy Person objects into map
▶️  Using for loop as required...
   ✓ Put 2000 Person objects into map
   ✓ Put 4000 Person objects into map
   ✓ Put 6000 Person objects into map
   ✓ Put 8000 Person objects into map
   ✓ Put 10000 Person objects into map
✅ PUT OPERATION COMPLETED!
   📊 Total objects put: 10000
   ⏱️  Time taken: 7311 ms
   📈 Current map size: 10000

📥 STEP 4: Getting 10000 Person objects from map
   📄 Retrieved person_1: Person{name='DummyPerson_1', age=21, email='dummy1@example.com', city='DummyCity_2'}
   📄 Retrieved person_2: Person{name='DummyPerson_2', age=22, email='dummy2@example.com', city='DummyCity_3'}
   📄 Retrieved person_3: Person{name='DummyPerson_3', age=23, email='dummy3@example.com', city='DummyCity_4'}
   ✓ Retrieved 2000 Person objects from map
   ✓ Retrieved 4000 Person objects from map
   ✓ Retrieved 6000 Person objects from map
   ✓ Retrieved 8000 Person objects from map
   ✓ Retrieved 10000 Person objects from map
✅ GET OPERATION COMPLETED!
   📊 Total objects retrieved: 10000/10000
   ⏱️  Time taken: 6752 ms
   🎯 Success rate: 100.0%

==================================================
📋 FINAL SUMMARY - HAZELCAST-EX-03
==================================================
✅ Connected to HAZELCAST-EX-02 Docker server
✅ Created 10000 dummy Person objects
✅ Put all objects into Hazelcast map using for loop
✅ Retrieved all objects from Hazelcast map
📊 Final map size: 10000
🏆 Exercise requirements satisfied!
==================================================

🎉 HAZELCAST-EX-03 COMPLETED SUCCESSFULLY!
```

## 🗂️ Project Structure

```
hazelcast-ex-03/
├── pom.xml                                     # Maven configuration
├── README.md                                   # This file
└── src/
    ├── main/java/com/bilalkaya/hazelcastdemo/
    │   ├── App.java                            # Main application class
    │   └── Person.java                         # Person model (with private constructor)
    └── test/java/com/bilalkaya/hazelcastdemo/
        └── AppTest.java                        # Unit tests
```

## 🧪 Running Tests

```bash
mvn test
```

## 🔧 Troubleshooting

If you encounter connection issues:

1. **Check Docker container status:**
   ```bash
   docker ps | grep hazelcast-node
   ```

2. **Check container logs:**
   ```bash
   docker logs hazelcast-node
   ```

3. **Restart container with port mapping:**
   ```bash
   docker stop hazelcast-node
   docker rm hazelcast-node
   docker run -d --name hazelcast-node -p 5701:5701 -e HZ_CLUSTERNAME=hazelcast-ex-02-i2i hazelcast/hazelcast:latest
   ```

## ✅ Exercise Requirements Met

- [x] Used Hazelcast server from HAZELCAST-EX-02
- [x] Created Person class with private constructor
- [x] Put 10,000 dummy Person objects using for loop
- [x] Retrieved all objects from Hazelcast map
- [x] Demonstrated distributed map functionality
- [x] Provided performance metrics and success rate

## 📈 Performance Results

- **PUT Operation:** 10,000 objects in ~7.3 seconds
- **GET Operation:** 10,000 objects in ~6.8 seconds  
- **Success Rate:** 100%
- **Map Size:** 10,000 objects stored successfully

---

> **Dependencies:**  
> - Hazelcast Client: 5.3.6  
> - JUnit: 4.13.2  
> - Java: 11+  
> 
> **Docker Images:**  
> - Hazelcast: https://hub.docker.com/r/hazelcast/hazelcast  
> - Management Center: https://hub.docker.com/r/hazelcast/management-center
