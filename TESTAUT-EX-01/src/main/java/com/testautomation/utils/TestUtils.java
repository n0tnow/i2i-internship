package com.testautomation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Test sürecinde kullanılacak yardımcı fonksiyonları içeren utility sınıfı
 * Screenshot alma, test verileri oluşturma, dosya işlemleri, loglama vb.
 */
public class TestUtils {
    
    // Tarih formatları
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private static final SimpleDateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    // Screenshot klasörü
    private static final String SCREENSHOT_FOLDER = "screenshots";
    
    // Test verileri için sabitler
    private static final String[] TURKISH_FIRST_NAMES = {
        "Ahmet", "Mehmet", "Ayşe", "Fatma", "Ali", "Veli", "Zeynep", "Elif", 
        "Mustafa", "Hatice", "Hüseyin", "Emine", "İbrahim", "Merve", "Yusuf", "Özlem"
    };
    
    private static final String[] TURKISH_LAST_NAMES = {
        "Yılmaz", "Kaya", "Demir", "Şahin", "Çelik", "Öztürk", "Arslan", "Doğan",
        "Koç", "Aydın", "Özdemir", "Türk", "Güneş", "Çetin", "Balcı", "Özer"
    };
    
    // ========================================
    // SCREENSHOT METHODS - Ekran Görüntüsü Metodları
    // ========================================
    
    /**
     * Test ekranının screenshot'ını alır ve screenshots klasörüne kaydeder
     * @param driver WebDriver instance'ı
     * @param testName test adı (dosya adında kullanılır)
     * @return screenshot dosyasının tam yolu
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        return captureScreenshot(driver, testName, "GENERAL");
    }
    
    /**
     * Test ekranının screenshot'ını alır ve belirtilen kategoride kaydeder
     * @param driver WebDriver instance'ı
     * @param testName test adı
     * @param category screenshot kategorisi (SUCCESS, FAILURE, INFO vs.)
     * @return screenshot dosyasının tam yolu
     */
    public static String captureScreenshot(WebDriver driver, String testName, String category) {
        String timestamp = DATE_FORMAT.format(new Date());
        String fileName = testName + "_" + category + "_" + timestamp + ".png";
        String screenshotPath = System.getProperty("user.dir") + File.separator + 
                               SCREENSHOT_FOLDER + File.separator + fileName;
        
        try {
            // Screenshot alma
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotPath);
            
            // Klasör yoksa oluştur
            createDirectoryIfNotExists(destinationFile.getParentFile());
            
            // Dosyayı hedef konuma kopyala
            FileHandler.copy(sourceFile, destinationFile);
            
            System.out.println("📸 Screenshot başarıyla alındı:");
            System.out.println("   📁 Dosya: " + fileName);
            System.out.println("   📂 Konum: " + screenshotPath);
            
            return screenshotPath;
            
        } catch (IOException e) {
            System.err.println("❌ Screenshot alınamadı: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Test başarısı için screenshot alır
     * @param driver WebDriver instance'ı
     * @param testName test adı
     * @return screenshot dosyasının tam yolu
     */
    public static String captureSuccessScreenshot(WebDriver driver, String testName) {
        return captureScreenshot(driver, testName, "SUCCESS");
    }
    
    /**
     * Test hatası için screenshot alır
     * @param driver WebDriver instance'ı
     * @param testName test adı
     * @return screenshot dosyasının tam yolu
     */
    public static String captureFailureScreenshot(WebDriver driver, String testName) {
        return captureScreenshot(driver, testName, "FAILURE");
    }
    
    /**
     * Test bilgi için screenshot alır
     * @param driver WebDriver instance'ı
     * @param testName test adı
     * @return screenshot dosyasının tam yolu
     */
    public static String captureInfoScreenshot(WebDriver driver, String testName) {
        return captureScreenshot(driver, testName, "INFO");
    }
    
    // ========================================
    // TEST DATA GENERATION - Test Verisi Oluşturma
    // ========================================
    
    /**
     * Rastgele Türkçe email adresi oluşturur
     * @return rastgele email adresi
     */
    public static String generateRandomEmail() {
        String randomString = generateRandomString(6).toLowerCase();
        String email = "test_" + randomString + "@example.com";
        System.out.println("📧 Rastgele email oluşturuldu: " + email);
        return email;
    }
    
    /**
     * Belirli domain ile rastgele email adresi oluşturur
     * @param domain email domain'i (örn: "gmail.com")
     * @return rastgele email adresi
     */
    public static String generateRandomEmail(String domain) {
        String randomString = generateRandomString(6).toLowerCase();
        String email = "test_" + randomString + "@" + domain;
        System.out.println("📧 Domain'li rastgele email oluşturuldu: " + email);
        return email;
    }
    
    /**
     * Hepsiburada formatında telefon numarası oluşturur
     * @return rastgele telefon numarası (5XXXXXXXXX formatında)
     */
    public static String generateRandomPhoneNumber() {
        Random random = new Random();
        String phoneNumber = "5" + String.format("%09d", random.nextInt(1000000000));
        System.out.println("📱 Rastgele telefon numarası oluşturuldu: " + phoneNumber);
        return phoneNumber;
    }
    
    /**
     * Rastgele şifre oluşturur
     * @return rastgele şifre (8 karakter)
     */
    public static String generateRandomPassword() {
        return generateRandomPassword(8);
    }
    
    /**
     * Belirtilen uzunlukta rastgele şifre oluşturur
     * @param length şifre uzunluğu
     * @return rastgele şifre
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        String generatedPassword = password.toString();
        System.out.println("🔐 Rastgele şifre oluşturuldu (uzunluk: " + length + ")");
        return generatedPassword;
    }
    
    /**
     * Güçlü rastgele şifre oluşturur (büyük harf, küçük harf, rakam, özel karakter)
     * @return güçlü rastgele şifre
     */
    public static String generateStrongRandomPassword() {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%&*";
        
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        
        // En az bir tane her kategoriden karakter ekle
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        
        // Kalan karakterleri rastgele ekle
        String allChars = upperCase + lowerCase + numbers + specialChars;
        for (int i = 4; i < 10; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        
        // Karakterleri karıştır
        String result = shuffleString(password.toString());
        System.out.println("🔒 Güçlü rastgele şifre oluşturuldu");
        return result;
    }
    
    /**
     * Rastgele Türkçe isim oluşturur
     * @return rastgele tam isim
     */
    public static String generateRandomTurkishName() {
        Random random = new Random();
        String firstName = TURKISH_FIRST_NAMES[random.nextInt(TURKISH_FIRST_NAMES.length)];
        String lastName = TURKISH_LAST_NAMES[random.nextInt(TURKISH_LAST_NAMES.length)];
        String fullName = firstName + " " + lastName;
        
        System.out.println("👤 Rastgele Türkçe isim oluşturuldu: " + fullName);
        return fullName;
    }
    
    /**
     * Rastgele string oluşturur
     * @param length string uzunluğu
     * @return rastgele string
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return result.toString();
    }
    
    // ========================================
    // WAIT UTILITIES - Bekleme Fonksiyonları
    // ========================================
    
    /**
     * Belirtilen süre kadar bekler (saniye cinsinden)
     * @param seconds bekleme süresi
     */
    public static void waitInSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            System.out.println("⏳ " + seconds + " saniye beklendi");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Bekleme işlemi kesintiye uğradı: " + e.getMessage());
        }
    }
    
    /**
     * Belirtilen süre kadar bekler (milisaniye cinsinden)
     * @param milliseconds bekleme süresi
     */
    public static void waitInMilliseconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
            System.out.println("⏳ " + milliseconds + " milisaniye beklendi");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Bekleme işlemi kesintiye uğradı: " + e.getMessage());
        }
    }
    
    /**
     * 1-3 saniye arası rastgele bekler (insan benzeri davranış için)
     */
    public static void waitRandomly() {
        Random random = new Random();
        int waitTime = 1 + random.nextInt(3); // 1-3 saniye arası
        waitInSeconds(waitTime);
    }
    
    // ========================================
    // FILE UTILITIES - Dosya İşlemleri
    // ========================================
    
    /**
     * Belirtilen klasörü oluşturur (yoksa)
     * @param directory oluşturulacak klasör
     */
    public static void createDirectoryIfNotExists(File directory) {
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("📁 Klasör oluşturuldu: " + directory.getAbsolutePath());
            } else {
                System.err.println("❌ Klasör oluşturulamadı: " + directory.getAbsolutePath());
            }
        }
    }
    
    /**
     * Proje ana klasöründeki belirtilen alt klasörü oluşturur
     * @param folderName klasör adı
     */
    public static void createProjectSubFolder(String folderName) {
        String folderPath = System.getProperty("user.dir") + File.separator + folderName;
        File folder = new File(folderPath);
        createDirectoryIfNotExists(folder);
    }
    
    /**
     * Dosya uzantısını döndürür
     * @param fileName dosya adı
     * @return dosya uzantısı
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    // ========================================
    // DATE/TIME UTILITIES - Tarih/Zaman İşlemleri
    // ========================================
    
    /**
     * Mevcut tarih ve saati belirtilen formatta döndürür
     * @return formatlanmış tarih ve saat
     */
    public static String getCurrentTimestamp() {
        return DATE_FORMAT.format(new Date());
    }
    
    /**
     * Okunabilir formatta mevcut tarih ve saati döndürür
     * @return okunabilir tarih ve saat
     */
    public static String getCurrentReadableTimestamp() {
        return READABLE_DATE_FORMAT.format(new Date());
    }
    
    /**
     * Belirtilen formatta mevcut tarih ve saati döndürür
     * @param format tarih formatı
     * @return formatlanmış tarih
     */
    public static String getCurrentTimestamp(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
    
    // ========================================
    // STRING UTILITIES - String İşlemleri
    // ========================================
    
    /**
     * String'in null veya boş olup olmadığını kontrol eder
     * @param str kontrol edilecek string
     * @return true eğer null veya boşsa
     */
    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * String'in sadece boşluk karakterleri içerip içermediğini kontrol eder
     * @param str kontrol edilecek string
     * @return true eğer sadece boşluksa
     */
    public static boolean isStringBlank(String str) {
        return str == null || str.trim().length() == 0;
    }
    
    /**
     * String'in karakterlerini karıştırır
     * @param input karıştırılacak string
     * @return karıştırılmış string
     */
    private static String shuffleString(String input) {
        char[] chars = input.toCharArray();
        Random random = new Random();
        
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        
        return new String(chars);
    }
    
    // ========================================
    // LOGGING UTILITIES - Loglama İşlemleri
    // ========================================
    
    /**
     * Test başlangıcını loglar
     * @param testName test adı
     * @param testDescription test açıklaması
     */
    public static void logTestStart(String testName, String testDescription) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🚀 TEST BAŞLADI: " + testName);
        System.out.println("📝 AÇIKLAMA: " + testDescription);
        System.out.println("⏰ BAŞLAMA ZAMANı: " + getCurrentReadableTimestamp());
        System.out.println("=".repeat(80));
    }
    
    /**
     * Test sonucunu loglar
     * @param testName test adı
     * @param isSuccess test başarılı mı?
     * @param message ek mesaj
     */
    public static void logTestResult(String testName, boolean isSuccess, String message) {
        String status = isSuccess ? "✅ BAŞARILI" : "❌ BAŞARISIZ";
        System.out.println("\n" + "-".repeat(80));
        System.out.println("🏁 TEST BİTTİ: " + testName);
        System.out.println("📊 SONUÇ: " + status);
        if (!isStringNullOrEmpty(message)) {
            System.out.println("💬 MESAJ: " + message);
        }
        System.out.println("⏰ BİTİŞ ZAMANı: " + getCurrentReadableTimestamp());
        System.out.println("-".repeat(80) + "\n");
    }
    
    /**
     * Test adımını loglar
     * @param stepNumber adım numarası
     * @param stepDescription adım açıklaması
     */
    public static void logTestStep(int stepNumber, String stepDescription) {
        System.out.println("📋 ADIM " + stepNumber + ": " + stepDescription);
    }
    
    /**
     * Test sonucu özetini loglar
     * @param totalTests toplam test sayısı
     * @param passedTests başarılı test sayısı
     * @param failedTests başarısız test sayısı
     */
    public static void logTestSummary(int totalTests, int passedTests, int failedTests) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📊 TEST SONUÇ ÖZETİ");
        System.out.println("=".repeat(80));
        System.out.println("🔢 Toplam Test: " + totalTests);
        System.out.println("✅ Başarılı: " + passedTests);
        System.out.println("❌ Başarısız: " + failedTests);
        System.out.println("📈 Başarı Oranı: " + String.format("%.1f%%", (passedTests * 100.0 / totalTests)));
        System.out.println("⏰ Tamamlanma Zamanı: " + getCurrentReadableTimestamp());
        System.out.println("=".repeat(80));
    }
    
    // ========================================
    // VALIDATION UTILITIES - Doğrulama İşlemleri
    // ========================================
    
    /**
     * Email formatının geçerli olup olmadığını basit kontrolle doğrular
     * @param email kontrol edilecek email
     * @return true eğer geçerli formattaysa
     */
    public static boolean isValidEmailFormat(String email) {
        if (isStringNullOrEmpty(email)) return false;
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
    
    /**
     * Telefon numarasının geçerli olup olmadığını kontrol eder
     * @param phoneNumber kontrol edilecek telefon numarası
     * @return true eğer geçerli formattaysa
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isStringNullOrEmpty(phoneNumber)) return false;
        return phoneNumber.matches("^[5][0-9]{9}$"); // 5XXXXXXXXX formatı
    }
    
    /**
     * Şifre güçlülüğünü kontrol eder
     * @param password kontrol edilecek şifre
     * @return true eğer güçlü şifreyse
     */
    public static boolean isStrongPassword(String password) {
        if (isStringNullOrEmpty(password) || password.length() < 8) return false;
        
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        
        return hasUpper && hasLower && hasDigit;
    }
}