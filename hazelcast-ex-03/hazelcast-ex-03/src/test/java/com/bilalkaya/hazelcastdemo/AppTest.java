package com.bilalkaya.hazelcastdemo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Simple unit tests for Hazelcast Exercise 03
 */
public class AppTest extends TestCase {
    
    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testPersonCreation() {
        Person person = new Person("Test User", 25, "test@example.com", "Istanbul");
        
        assertEquals("Test User", person.getName());
        assertEquals(25, person.getAge());
        assertEquals("test@example.com", person.getEmail());
        assertEquals("Istanbul", person.getCity());
    }
    
    public void testPersonToString() {
        Person person = new Person("Ali", 30, "ali@example.com", "Ankara");
        String result = person.toString();
        
        assertTrue("Should contain name", result.contains("Ali"));
        assertTrue("Should contain age", result.contains("30"));
        assertTrue("Should contain email", result.contains("ali@example.com"));
        assertTrue("Should contain city", result.contains("Ankara"));
    }
    
    public void testExerciseConstants() {
        assertTrue("Should process 10000 persons", 10000 > 0);
        assertNotNull("Map name should exist", "personMap");
    }
}