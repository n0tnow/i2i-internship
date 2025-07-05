package com.testautomation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * Tüm test sınıflarının extend edeceği temel sınıf
 * WebDriver yönetimi ve konfigürasyon yükleme işlemlerini yapar
 */
public class BaseTest {
    
    // WebDriver instance'ı - her test için ayrı driver oluşturulacak
    protected WebDriver driver;
    
    // Konfigürasyon dosyasından ayarları okumak için
    protected Properties config;
    
    // Konfigürasyon dosyası yolu
    private static final String CONFIG_PATH = "src/main/resources/config.properties";
    
    /**
     * Her test metodundan önce çalışır
     * WebDriver'ı başlatır ve browser'ı açar
     */
    @BeforeMethod
    public void setUp() {
        try {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("🚀 TEST SETUP BAŞLADI");
            System.out.println("=".repeat(60));
            
            // Konfigürasyon dosyasını yükle
            loadConfiguration();
            
            // Browser'ı başlat
            initializeBrowser();
            
            // Browser ayarları
            configureBrowser();
            
            // Ana sayfaya git
            navigateToBaseUrl();
            
            System.out.println("✅ Setup başarıyla tamamlandı!");
            System.out.println("=".repeat(60));
            
        } catch (Exception e) {
            System.err.println("❌ Setup sırasında hata: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Test setup başarısız!", e);
        }
    }
    
    /**
     * Her test metodundan sonra çalışır
     * WebDriver'ı kapatır
     */
    @AfterMethod
    public void tearDown() {
        try {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("🔄 TEST CLEANUP BAŞLADI");
            
            if (driver != null) {
                System.out.println("🌐 Browser kapatılıyor...");
                driver.quit();
                System.out.println("✅ Browser başarıyla kapatıldı");
            }
            
            System.out.println("✅ Cleanup tamamlandı!");
            System.out.println("-".repeat(60) + "\n");
            
        } catch (Exception e) {
            System.err.println("❌ Cleanup sırasında hata: " + e.getMessage());
        }
    }
    
    /**
     * config.properties dosyasını yükler
     */
    private void loadConfiguration() throws IOException {
        System.out.println("📖 Konfigürasyon dosyası yükleniyor...");
        
        config = new Properties();
        
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            config.load(fis);
            System.out.println("✅ Konfigürasyon başarıyla yüklendi: " + CONFIG_PATH);
            
            // Yüklenen ayarları göster
            System.out.println("📋 Yüklenen ayarlar:");
            System.out.println("   🌐 Base URL: " + config.getProperty("base.url"));
            System.out.println("   📧 Email: " + config.getProperty("valid.email"));
            System.out.println("   🌍 Browser: " + config.getProperty("default.browser"));
            System.out.println("   ⏱️ Implicit Wait: " + config.getProperty("implicit.wait") + " saniye");
            
        } catch (IOException e) {
            System.err.println("❌ Konfigürasyon dosyası yüklenemedi: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Browser'ı başlatır
     */
    private void initializeBrowser() {
        String browserName = config.getProperty("default.browser", "chrome").toLowerCase();
        System.out.println("🌐 Browser başlatılıyor: " + browserName);
        
        switch (browserName) {
            case "chrome":
                setupChromeDriver();
                break;
                
            case "firefox":
                setupFirefoxDriver();
                break;
                
            case "edge":
                setupEdgeDriver();
                break;
                
            default:
                System.err.println("❌ Desteklenmeyen browser: " + browserName);
                System.out.println("🔄 Varsayılan olarak Chrome kullanılıyor...");
                setupChromeDriver();
        }
        
        System.out.println("✅ " + browserName + " WebDriver başarıyla başlatıldı");
    }
    
    /**
     * Chrome WebDriver'ı kurar
     */
    private void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        
        // Chrome seçenekleri
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        // Headless mode isteniyorsa (opsiyonel)
        // options.addArguments("--headless");
        
        driver = new ChromeDriver(options);
    }
    
    /**
     * Firefox WebDriver'ı kurar
     */
    private void setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }
    
    /**
     * Edge WebDriver'ı kurar
     */
    private void setupEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }
    
    /**
     * Browser ayarlarını yapar
     */
    private void configureBrowser() {
        System.out.println("⚙️ Browser ayarları yapılıyor...");
        
        // Pencereyi maksimize et
        driver.manage().window().maximize();
        System.out.println("   📺 Pencere maksimize edildi");
        
        // Implicit wait ayarla - Config'den al ama minimum 5 saniye
        int implicitWait = Math.max(Integer.parseInt(config.getProperty("implicit.wait", "5")), 5);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        System.out.println("   ⏱️ Implicit wait: " + implicitWait + " saniye");
        
        // Page load timeout ayarla - Config'den al ama minimum 15 saniye
        int pageLoadTimeout = Math.max(Integer.parseInt(config.getProperty("page.load.timeout", "15")), 15);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        System.out.println("   📄 Page load timeout: " + pageLoadTimeout + " saniye");
        
        System.out.println("✅ Browser ayarları tamamlandı");
    }
    
    /**
     * Ana sayfaya gider
     */
    private void navigateToBaseUrl() {
        String baseUrl = config.getProperty("base.url");
        System.out.println("🔗 Ana sayfaya gidiliyor: " + baseUrl);
        
        try {
            driver.get(baseUrl);
            System.out.println("✅ Sayfa başarıyla yüklendi");
            
            // Sayfa başlığını kontrol et
            String pageTitle = driver.getTitle();
            System.out.println("📄 Sayfa başlığı: " + pageTitle);
            
        } catch (Exception e) {
            System.err.println("❌ Sayfa yüklenirken hata: " + e.getMessage());
            throw e;
        }
    }
    
    // ========================================
    // GETTER METHODS - Erişim Metodları
    // ========================================
    
    /**
     * WebDriver instance'ını döndürür
     * @return aktif WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Konfigürasyon property'sini döndürür
     * @param key property anahtarı
     * @return property değeri
     */
    public String getConfigProperty(String key) {
        return config.getProperty(key);
    }
    
    /**
     * Konfigürasyon property'sini varsayılan değer ile döndürür
     * @param key property anahtarı
     * @param defaultValue varsayılan değer
     * @return property değeri veya varsayılan değer
     */
    public String getConfigProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }
    
    // ========================================
    // UTILITY METHODS - Yardımcı Metodlar
    // ========================================
    
    /**
     * Belirtilen süre kadar bekler
     * @param seconds bekleme süresi (saniye)
     */
    public void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            System.out.println("⏳ " + seconds + " saniye beklendi");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Bekleme işlemi kesintiye uğradı: " + e.getMessage());
        }
    }
    
    /**
     * Sayfa başlığını alır
     * @return sayfa başlığı
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Mevcut URL'yi alır
     * @return mevcut URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}