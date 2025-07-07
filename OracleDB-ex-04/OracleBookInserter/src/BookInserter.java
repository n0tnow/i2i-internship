import java.sql.*;
import java.util.Random;

public class BookInserter {
    
    // Database connection parameters
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "sys as sysdba";
    private static final String PASSWORD = "ORACLE";
    
    // Arrays for random book names and ISBN generation
    private static final String[] BOOK_TITLES = {
        "The Art of Programming", "Database Design Fundamentals", "Oracle Mastery",
        "Java Complete Guide", "Software Engineering Principles", "Data Structures",
        "Algorithms and Logic", "Web Development Basics", "Machine Learning Intro",
        "Cloud Computing Guide", "Cybersecurity Essentials", "Mobile App Development",
        "DevOps Practices", "Artificial Intelligence", "Python Programming",
        "JavaScript Handbook", "React Development", "Spring Framework",
        "Docker Containerization", "Kubernetes Guide", "Microservices Architecture"
    };
    
    private static final String[] AUTHORS = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
        "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez"
    };
    
    public static void main(String[] args) {
        
        System.out.println("=== Oracle BOOK Table Inserter ===");
        System.out.println("Starting insertion of 100 sample records...\n");
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("✓ Oracle JDBC driver loaded successfully");
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("✓ Connected to Oracle Database: " + DB_URL);
            
            // Prepare INSERT statement
            String insertSQL = "INSERT INTO BOOK (ID, NAME, ISBN) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(insertSQL);
            
            // Insert 100 sample records
            Random random = new Random();
            int successCount = 0;
            
            for (int i = 1; i <= 100; i++) {
                try {
                    // Generate random book data
                    String bookName = generateRandomBookName(random);
                    String isbn = generateRandomISBN(random);
                    
                    // Set parameters
                    statement.setInt(1, i);              // ID
                    statement.setString(2, bookName);    // NAME
                    statement.setString(3, isbn);        // ISBN
                    // CREATE_DATE will be auto-set to SYSDATE
                    
                    // Execute insert
                    int rowsAffected = statement.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        successCount++;
                        System.out.printf("✓ Record %3d: ID=%3d | %-40s | %s%n", 
                                        i, i, bookName, isbn);
                    }
                    
                } catch (SQLException e) {
                    System.err.printf("✗ Error inserting record %d: %s%n", i, e.getMessage());
                }
            }
            
            // Commit transaction
            connection.commit();
            System.out.println("\n" + "=".repeat(70));
            System.out.printf("✓ Successfully inserted %d out of 100 records%n", successCount);
            System.out.println("✓ Transaction committed");
            
            // Verify insertion by counting records
            verifyInsertion(connection);
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Oracle JDBC driver not found: " + e.getMessage());
            System.err.println("Make sure ojdbc11.jar is in your classpath");
            
        } catch (SQLException e) {
            System.err.println("✗ Database error: " + e.getMessage());
            System.err.println("Make sure Oracle container is running and accessible");
            
        } finally {
            // Close resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("✓ Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Generate random book name combining title and author
     */
    private static String generateRandomBookName(Random random) {
        String title = BOOK_TITLES[random.nextInt(BOOK_TITLES.length)];
        String author = AUTHORS[random.nextInt(AUTHORS.length)];
        return title + " by " + author;
    }
    
    /**
     * Generate random ISBN-13 format: 978-X-
     */
    private static String generateRandomISBN(Random random) {
        StringBuilder isbn = new StringBuilder("978-");
        
        // Publisher code (1 digit)
        isbn.append(random.nextInt(10));
        isbn.append("-");
        
        // Title code (3 digits)
        isbn.append(String.format("%03d", random.nextInt(1000)));
        isbn.append("-");
        
        // Item number (5 digits)
        isbn.append(String.format("%05d", random.nextInt(100000)));
        isbn.append("-");
        
        // Check digit (1 digit)
        isbn.append(random.nextInt(10));
        
        return isbn.toString();
    }
    
    /**
     * Verify insertion by counting total records
     */
    private static void verifyInsertion(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM BOOK");
            
            if (rs.next()) {
                int totalRecords = rs.getInt(1);
                System.out.printf("✓ Total records in BOOK table: %d%n", totalRecords);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.err.println("Error verifying insertion: " + e.getMessage());
        }
    }
}