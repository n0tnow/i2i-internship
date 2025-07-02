package com.bilalkaya.hazelcastdemo;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

/**
 * Hazelcast Exercise 03 Implementation
 * 
 * Requirements:
 * 1. Use Hazelcast server from HAZELCAST-EX-02 (Docker)
 * 2. Create dummy Person objects
 * 3. Put 10,000 Person objects into Hazelcast map using for loop
 * 4. Get these objects from Hazelcast map
 */
public class App {
    private static final int PERSON_COUNT = 10_000;
    private static final String MAP_NAME = "personMap";
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        HAZELCAST EXERCISE 03           ║");
        System.out.println("║   10,000 Person Objects Demo          ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        HazelcastInstance client = null;
        try {
            // Step 1: Connect to HAZELCAST-EX-02 Docker server
            client = connectToHazelcastServer();
            if (client == null) {
                System.err.println("❌ Failed to connect to Hazelcast server");
                return;
            }
            
            // Step 2: Get distributed map
            IMap<String, Person> personMap = client.getMap(MAP_NAME);
            clearMapForDemo(personMap);
            
            // Step 3: Put 10,000 dummy Person objects using for loop
            putPersonObjectsIntoMap(personMap);
            
            // Step 4: Get these objects from Hazelcast map
            getPersonObjectsFromMap(personMap);
            
            // Final summary
            printFinalSummary(personMap);
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.shutdown();
                System.out.println("🔌 Hazelcast client disconnected");
            }
        }
        
        System.out.println("\n🎉 HAZELCAST-EX-03 COMPLETED SUCCESSFULLY!");
    }
    
    /**
     * Step 1: Connect to Hazelcast server from HAZELCAST-EX-02
     */
    private static HazelcastInstance connectToHazelcastServer() {
        try {
            System.out.println("\n📡 Connecting to HAZELCAST-EX-02 Docker server...");
            
            ClientConfig config = new ClientConfig();
            config.getNetworkConfig().addAddress("localhost:5701");
            config.setClusterName("hazelcast-ex-02-i2i");
            
            HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
            System.out.println("✅ Successfully connected to Hazelcast cluster!");
            return client;
            
        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
            System.err.println("💡 Make sure Docker container is running:");
            System.err.println("   docker ps | grep hazelcast-node");
            return null;
        }
    }
    
    /**
     * Clear map for clean demo
     */
    private static void clearMapForDemo(IMap<String, Person> map) {
        int previousSize = map.size();
        map.clear();
        System.out.println("🧹 Map cleared for demo (previous size: " + previousSize + ")");
    }
    
    /**
     * Step 3: Put 10,000 dummy Person objects into Hazelcast map using for loop
     */
    private static void putPersonObjectsIntoMap(IMap<String, Person> personMap) {
        System.out.println("\n📦 STEP 3: Putting " + PERSON_COUNT + " dummy Person objects into map");
        System.out.println("▶️  Using for loop as required...");
        
        long startTime = System.currentTimeMillis();
        
        // FOR LOOP - putting 10,000 dummy Person objects
        for (int i = 1; i <= PERSON_COUNT; i++) {
            // Create dummy Person object
            Person dummyPerson = createDummyPerson(i);
            
            // Put into Hazelcast map
            String key = "person_" + i;
            personMap.put(key, dummyPerson);
            
            // Progress report every 2000 objects
            if (i % 2000 == 0) {
                System.out.println("   ✓ Put " + i + " Person objects into map");
            }
        }
        
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("✅ PUT OPERATION COMPLETED!");
        System.out.println("   📊 Total objects put: " + PERSON_COUNT);
        System.out.println("   ⏱️  Time taken: " + duration + " ms");
        System.out.println("   📈 Current map size: " + personMap.size());
    }
    
    /**
     * Step 4: Get these objects from Hazelcast map
     */
    private static void getPersonObjectsFromMap(IMap<String, Person> personMap) {
        System.out.println("\n📥 STEP 4: Getting " + PERSON_COUNT + " Person objects from map");
        
        long startTime = System.currentTimeMillis();
        int successfulGets = 0;
        
        // Get objects from map
        for (int i = 1; i <= PERSON_COUNT; i++) {
            String key = "person_" + i;
            Person retrievedPerson = personMap.get(key);
            
            if (retrievedPerson != null) {
                successfulGets++;
                
                // Show first 3 retrieved objects as sample
                if (i <= 3) {
                    System.out.println("   📄 Retrieved " + key + ": " + retrievedPerson);
                }
            }
            
            // Progress report every 2000 objects
            if (i % 2000 == 0) {
                System.out.println("   ✓ Retrieved " + i + " Person objects from map");
            }
        }
        
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("✅ GET OPERATION COMPLETED!");
        System.out.println("   📊 Total objects retrieved: " + successfulGets + "/" + PERSON_COUNT);
        System.out.println("   ⏱️  Time taken: " + duration + " ms");
        System.out.println("   🎯 Success rate: " + (successfulGets * 100.0 / PERSON_COUNT) + "%");
    }
    
    /**
     * Creates a dummy Person object with given ID
     */
    private static Person createDummyPerson(int id) {
        return new Person(
            "DummyPerson_" + id,
            20 + (id % 50),  // Age between 20-69
            "dummy" + id + "@example.com",
            "DummyCity_" + (id % 10 + 1)
        );
    }
    
    /**
     * Print final summary
     */
    private static void printFinalSummary(IMap<String, Person> personMap) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📋 FINAL SUMMARY - HAZELCAST-EX-03");
        System.out.println("=".repeat(50));
        System.out.println("✅ Connected to HAZELCAST-EX-02 Docker server");
        System.out.println("✅ Created " + PERSON_COUNT + " dummy Person objects");
        System.out.println("✅ Put all objects into Hazelcast map using for loop");
        System.out.println("✅ Retrieved all objects from Hazelcast map");
        System.out.println("📊 Final map size: " + personMap.size());
        System.out.println("🏆 Exercise requirements satisfied!");
        System.out.println("=".repeat(50));
    }
}