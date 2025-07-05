package com.testautomation.tests;

import com.testautomation.base.BaseTest;
import com.testautomation.pages.HomePage;
import com.testautomation.pages.LoginPage;
import com.testautomation.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TRENDYOL login işlemlerini test eden ana test sınıfı
 * Farklı login senaryolarını kapsamlı olarak test eder
 * BaseTest sınıfından extend edilir ve tüm browser yönetimi otomatik yapılır
 */
public class LoginTests extends BaseTest {
    
    // ========================================
    // TEST 1: BAŞARILI LOGIN TESTİ
    // ========================================
    
    @Test(priority = 1, description = "TRENDYOL Geçerli kullanıcı bilgileri ile başarılı login testi")
    public void testSuccessfulLogin() {
        
        // Test başlangıcını logla
        TestUtils.logTestStart("testSuccessfulLogin", 
                              "Geçerli email ve şifre ile TRENDYOL'a başarılı giriş testi");
        
        try {
            // Test adımı 1: Page Object'leri oluştur
            TestUtils.logTestStep(1, "LoginPage ve HomePage nesneleri oluşturuluyor");
            LoginPage loginPage = new LoginPage(driver);
            HomePage homePage = new HomePage(driver);
            
            // Başlangıç screenshot'ı
            TestUtils.captureInfoScreenshot(driver, "testSuccessfulLogin_Start");
            
            // Test adımı 2: Konfigürasyondan test bilgilerini al
            TestUtils.logTestStep(2, "Konfigürasyon dosyasından test bilgileri alınıyor");
            String testEmail = getConfigProperty("valid.email");
            String testPassword = getConfigProperty("valid.password");
            
            System.out.println("📧 Test Email: " + testEmail);
            System.out.println("🔒 Test Password: " + "*".repeat(testPassword.length()));
            
            // Test adımı 3: Ana sayfanın yüklendiğini doğrula
            TestUtils.logTestStep(3, "Ana sayfanın yüklendiği kontrol ediliyor");
            boolean homePageLoaded = homePage.isHomePageLoaded();
            Assert.assertTrue(homePageLoaded, "Ana sayfa yüklenemedi!");
            System.out.println("✅ Ana sayfa başarıyla yüklendi");
            
            // Test adımı 4: Login işlemini başlat
            TestUtils.logTestStep(4, "Login işlemi başlatılıyor");
            loginPage.performCompleteLogin(testEmail, testPassword);
            
            // Login işlemi sonrası kısa bekleme
            TestUtils.waitInSeconds(2);
            
            // Login sonrası screenshot
            TestUtils.captureInfoScreenshot(driver, "testSuccessfulLogin_AfterLogin");
            
            // Test adımı 5: Login başarısını doğrula
            TestUtils.logTestStep(5, "Login başarısı kontrol ediliyor");
            boolean isLoggedIn = homePage.isUserLoggedIn();
            
            if (isLoggedIn) {
                System.out.println("✅ LOGIN BAŞARILI - Kullanıcı giriş yapmış");
                
                // Kullanıcı bilgilerini al
                String userName = homePage.getUserName();
                String pageTitle = homePage.getPageTitle();
                
                System.out.println("👤 Giriş yapan kullanıcı: " + (userName.isEmpty() ? "Bilinmiyor" : userName));
                System.out.println("📄 Sayfa başlığı: " + pageTitle);
                
                // Başarı screenshot'ı
                TestUtils.captureSuccessScreenshot(driver, "testSuccessfulLogin");
                
                // Test sonucunu logla
                TestUtils.logTestResult("testSuccessfulLogin", true, 
                                      "Login başarılı! Kullanıcı: " + testEmail);
                
            } else {
                System.out.println("❌ LOGIN BAŞARISIZ - Kullanıcı giriş yapamamış");
                
                // Hata screenshot'ı
                TestUtils.captureFailureScreenshot(driver, "testSuccessfulLogin");
                
                // Test sonucunu logla
                TestUtils.logTestResult("testSuccessfulLogin", false, 
                                      "Login başarısız! Email: " + testEmail);
                
                Assert.fail("Login işlemi başarısız! Kullanıcı giriş yapamadı.");
            }
            
            // Test assertion'ı
            Assert.assertTrue(isLoggedIn, "Kullanıcı başarıyla giriş yapmalıydı!");
            
        } catch (Exception e) {
            System.err.println("❌ Test sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
            
            // Hata screenshot'ı
            TestUtils.captureFailureScreenshot(driver, "testSuccessfulLogin_Error");
            
            // Test sonucunu logla
            TestUtils.logTestResult("testSuccessfulLogin", false, 
                                  "Test hatası: " + e.getMessage());
            
            Assert.fail("Test sırasında hata oluştu: " + e.getMessage());
        }
    }
    
    // ========================================
    // TEST 2: GEÇERSİZ EMAIL TESTİ
    // ========================================
    
    @Test(priority = 2, description = "Geçersiz email adresi ile login başarısızlık testi")
    public void testLoginWithInvalidEmail() {
        
        TestUtils.logTestStart("testLoginWithInvalidEmail", 
                              "Geçersiz email adresi ile login başarısızlık testi");
        
        try {
            // Page Object'leri oluştur
            TestUtils.logTestStep(1, "Test nesneleri oluşturuluyor");
            LoginPage loginPage = new LoginPage(driver);
            HomePage homePage = new HomePage(driver);
            
            // Test verileri
            String invalidEmail = TestUtils.generateRandomEmail();
            String validPassword = getConfigProperty("valid.password");
            
            System.out.println("📧 Geçersiz Email: " + invalidEmail);
            System.out.println("🔒 Geçerli Password: " + "*".repeat(validPassword.length()));
            
            // Login denemesi
            TestUtils.logTestStep(2, "Geçersiz email ile login denemesi");
            loginPage.performCompleteLogin(invalidEmail, validPassword);
            
            // Bekleme
            TestUtils.waitInSeconds(2);
            
            // Sonuç kontrolü
            TestUtils.logTestStep(3, "Login sonucu kontrol ediliyor");
            boolean isLoggedIn = homePage.isUserLoggedIn();
            boolean hasErrorMessage = loginPage.isErrorMessageDisplayed();
            
            if (!isLoggedIn) {
                System.out.println("✅ BEKLENEN SONUÇ - Login başarısız oldu");
                
                if (hasErrorMessage) {
                    String errorMessage = loginPage.getErrorMessageText();
                    System.out.println("📋 Hata mesajı: " + errorMessage);
                }
                
                TestUtils.captureSuccessScreenshot(driver, "testLoginWithInvalidEmail");
                TestUtils.logTestResult("testLoginWithInvalidEmail", true, 
                                      "Geçersiz email ile login başarısız oldu (beklenen davranış)");
            } else {
                System.out.println("❌ BEKLENMEYEN SONUÇ - Geçersiz email ile login başarılı oldu!");
                TestUtils.captureFailureScreenshot(driver, "testLoginWithInvalidEmail");
                TestUtils.logTestResult("testLoginWithInvalidEmail", false, 
                                      "Geçersiz email ile login başarılı olmamalıydı!");
            }
            
            // Test assertion'ı
            Assert.assertFalse(isLoggedIn, "Geçersiz email ile login başarılı olmamalıydı!");
            
        } catch (Exception e) {
            System.err.println("❌ Test sırasında hata: " + e.getMessage());
            TestUtils.captureFailureScreenshot(driver, "testLoginWithInvalidEmail_Error");
            TestUtils.logTestResult("testLoginWithInvalidEmail", false, "Test hatası: " + e.getMessage());
            Assert.fail("Test sırasında hata oluştu: " + e.getMessage());
        }
    }
    
    // ========================================
    // TEST 3: GEÇERSİZ ŞİFRE TESTİ
    // ========================================
    
    @Test(priority = 3, description = "Geçersiz şifre ile login başarısızlık testi")
    public void testLoginWithInvalidPassword() {
        
        TestUtils.logTestStart("testLoginWithInvalidPassword", 
                              "Geçersiz şifre ile login başarısızlık testi");
        
        try {
            // Page Object'leri oluştur
            LoginPage loginPage = new LoginPage(driver);
            HomePage homePage = new HomePage(driver);
            
            // Test verileri
            String validEmail = getConfigProperty("valid.email");
            String invalidPassword = TestUtils.generateRandomPassword(10);
            
            System.out.println("📧 Geçerli Email: " + validEmail);
            System.out.println("🔒 Geçersiz Password: " + "*".repeat(invalidPassword.length()));
            
            // Login denemesi
            TestUtils.logTestStep(1, "Geçersiz şifre ile login denemesi");
            loginPage.performCompleteLogin(validEmail, invalidPassword);
            
            TestUtils.waitInSeconds(2);
            
            // Sonuç kontrolü
            boolean isLoggedIn = homePage.isUserLoggedIn();
            boolean hasErrorMessage = loginPage.isErrorMessageDisplayed();
            
            if (!isLoggedIn) {
                System.out.println("✅ BEKLENEN SONUÇ - Login başarısız oldu");
                
                if (hasErrorMessage) {
                    String errorMessage = loginPage.getErrorMessageText();
                    System.out.println("📋 Hata mesajı: " + errorMessage);
                }
                
                TestUtils.captureSuccessScreenshot(driver, "testLoginWithInvalidPassword");
                TestUtils.logTestResult("testLoginWithInvalidPassword", true, 
                                      "Geçersiz şifre ile login başarısız oldu (beklenen davranış)");
            } else {
                System.out.println("❌ BEKLENMEYEN SONUÇ - Geçersiz şifre ile login başarılı oldu!");
                TestUtils.captureFailureScreenshot(driver, "testLoginWithInvalidPassword");
                TestUtils.logTestResult("testLoginWithInvalidPassword", false, 
                                      "Geçersiz şifre ile login başarılı olmamalıydı!");
            }
            
            Assert.assertFalse(isLoggedIn, "Geçersiz şifre ile login başarılı olmamalıydı!");
            
        } catch (Exception e) {
            System.err.println("❌ Test sırasında hata: " + e.getMessage());
            TestUtils.captureFailureScreenshot(driver, "testLoginWithInvalidPassword_Error");
            TestUtils.logTestResult("testLoginWithInvalidPassword", false, "Test hatası: " + e.getMessage());
            Assert.fail("Test sırasında hata oluştu: " + e.getMessage());
        }
    }
    
    // ========================================
    // TEST 4: BOŞ ALANLAR TESTİ
    // ========================================
    
    @Test(priority = 4, description = "Boş email ve şifre alanları ile login testi")
    public void testLoginWithEmptyFields() {
        
        TestUtils.logTestStart("testLoginWithEmptyFields", 
                              "Boş email ve şifre alanları ile login başarısızlık testi");
        
        try {
            // Page Object'leri oluştur
            LoginPage loginPage = new LoginPage(driver);
            HomePage homePage = new HomePage(driver);
            
            // Boş verilerle login denemesi
            TestUtils.logTestStep(1, "Boş alanlarla login denemesi");
            System.out.println("📧 Email: (boş)");
            System.out.println("🔒 Password: (boş)");
            
            loginPage.performCompleteLogin("", "");
            
            TestUtils.waitInSeconds(2);
            
            // Sonuç kontrolü
            boolean isLoggedIn = homePage.isUserLoggedIn();
            boolean hasErrorMessage = loginPage.isErrorMessageDisplayed();
            
            if (!isLoggedIn) {
                System.out.println("✅ BEKLENEN SONUÇ - Boş alanlarla login başarısız oldu");
                
                if (hasErrorMessage) {
                    String errorMessage = loginPage.getErrorMessageText();
                    System.out.println("📋 Hata mesajı: " + errorMessage);
                }
                
                TestUtils.captureSuccessScreenshot(driver, "testLoginWithEmptyFields");
                TestUtils.logTestResult("testLoginWithEmptyFields", true, 
                                      "Boş alanlarla login başarısız oldu (beklenen davranış)");
            } else {
                System.out.println("❌ BEKLENMEYEN SONUÇ - Boş alanlarla login başarılı oldu!");
                TestUtils.captureFailureScreenshot(driver, "testLoginWithEmptyFields");
                TestUtils.logTestResult("testLoginWithEmptyFields", false, 
                                      "Boş alanlarla login başarılı olmamalıydı!");
            }
            
            Assert.assertFalse(isLoggedIn, "Boş alanlarla login başarılı olmamalıydı!");
            
        } catch (Exception e) {
            System.err.println("❌ Test sırasında hata: " + e.getMessage());
            TestUtils.captureFailureScreenshot(driver, "testLoginWithEmptyFields_Error");
            TestUtils.logTestResult("testLoginWithEmptyFields", false, "Test hatası: " + e.getMessage());
            Assert.fail("Test sırasında hata oluştu: " + e.getMessage());
        }
    }
    
    // ========================================
    // TEST 5: BENİ HATIRLA ÖZELLİĞİ TESTİ
    // ========================================
    
    @Test(priority = 5, description = "Beni Hatırla özelliği ile login testi")
    public void testLoginWithRememberMe() {
        
        TestUtils.logTestStart("testLoginWithRememberMe", 
                              "Beni Hatırla özelliği ile login testi");
        
        try {
            // Page Object'leri oluştur
            LoginPage loginPage = new LoginPage(driver);
            HomePage homePage = new HomePage(driver);
            
            // Test verileri
            String testEmail = getConfigProperty("valid.email");
            String testPassword = getConfigProperty("valid.password");
            
            System.out.println("📧 Email: " + testEmail);
            System.out.println("🔒 Password: " + "*".repeat(testPassword.length()));
            System.out.println("☑️ Beni Hatırla: Aktif");
            
            // Beni Hatırla ile login
            TestUtils.logTestStep(1, "Beni Hatırla özelliği ile login yapılıyor");
            loginPage.performLoginWithRememberMe(testEmail, testPassword);
            
            TestUtils.waitInSeconds(2);
            
            // Sonuç kontrolü
            boolean isLoggedIn = homePage.isUserLoggedIn();
            
            if (isLoggedIn) {
                System.out.println("✅ BENİ HATIRLA İLE LOGIN BAŞARILI");
                
                String userName = homePage.getUserName();
                System.out.println("👤 Giriş yapan kullanıcı: " + (userName.isEmpty() ? "Bilinmiyor" : userName));
                
                TestUtils.captureSuccessScreenshot(driver, "testLoginWithRememberMe");
                TestUtils.logTestResult("testLoginWithRememberMe", true, 
                                      "Beni Hatırla özelliği ile login başarılı");
                
                // Çıkış yap (temizlik için)
                try {
                    TestUtils.logTestStep(2, "Test temizliği için çıkış yapılıyor");
                    homePage.clickLogoutLink();
                    System.out.println("🚪 Çıkış işlemi tamamlandı");
                } catch (Exception e) {
                    System.out.println("⚠️ Çıkış yapılamadı, bu normal olabilir: " + e.getMessage());
                }
                
            } else {
                System.out.println("❌ BENİ HATIRLA İLE LOGIN BAŞARISIZ");
                TestUtils.captureFailureScreenshot(driver, "testLoginWithRememberMe");
                TestUtils.logTestResult("testLoginWithRememberMe", false, 
                                      "Beni Hatırla özelliği ile login başarısız");
            }
            
            Assert.assertTrue(isLoggedIn, "Beni Hatırla özelliği ile login başarılı olmalıydı!");
            
        } catch (Exception e) {
            System.err.println("❌ Test sırasında hata: " + e.getMessage());
            TestUtils.captureFailureScreenshot(driver, "testLoginWithRememberMe_Error");
            TestUtils.logTestResult("testLoginWithRememberMe", false, "Test hatası: " + e.getMessage());
            Assert.fail("Test sırasında hata oluştu: " + e.getMessage());
        }
    }
    
    // ========================================
    // TEST 6: LOGIN SAYFASI YÜKLENMESİ TESTİ
    // ========================================
    
    @Test(priority = 6, description = "Login sayfasının elementlerinin doğru yüklenme testi")
    public void testLoginPageElementsLoading() {
        
        TestUtils.logTestStart("testLoginPageElementsLoading", 
                              "Login sayfası elementlerinin doğru yüklenme testi");
        
        try {
            // Page Object oluştur
            LoginPage loginPage = new LoginPage(driver);
            
            // Login sayfasına git
            TestUtils.logTestStep(1, "Login sayfasına yönlendiriliyor");
            loginPage.navigateToLoginPage();
            
            // Sayfa elementlerini kontrol et
            TestUtils.logTestStep(2, "Login sayfası elementleri kontrol ediliyor");
            boolean pageLoaded = loginPage.isLoginPageLoaded();
            
            // Sayfa bilgilerini al
            String pageTitle = loginPage.getPageTitle();
            boolean isOnLoginPage = loginPage.isOnLoginPage();
            
            System.out.println("📄 Sayfa başlığı: " + pageTitle);
            System.out.println("📍 Login sayfasında mıyız: " + (isOnLoginPage ? "Evet" : "Hayır"));
            System.out.println("🔍 Sayfa elementleri yüklendi mi: " + (pageLoaded ? "Evet" : "Hayır"));
            
            if (pageLoaded && isOnLoginPage) {
                System.out.println("✅ LOGIN SAYFASI ELEMENTLER BAŞARIYLA YÜKLENDİ");
                TestUtils.captureSuccessScreenshot(driver, "testLoginPageElementsLoading");
                TestUtils.logTestResult("testLoginPageElementsLoading", true, 
                                      "Login sayfası elementleri başarıyla yüklendi");
            } else {
                System.out.println("❌ LOGIN SAYFASI ELEMENTLER EKSİK VEYA YANLIŞ SAYFA");
                TestUtils.captureFailureScreenshot(driver, "testLoginPageElementsLoading");
                TestUtils.logTestResult("testLoginPageElementsLoading", false, 
                                      "Login sayfası elementleri yüklenemedi");
            }
            
            Assert.assertTrue(pageLoaded, "Login sayfası elementleri tam olarak yüklenmedi!");
            Assert.assertTrue(isOnLoginPage, "Login sayfasında değiliz!");
            
        } catch (Exception e) {
            System.err.println("❌ Test sırasında hata: " + e.getMessage());
            TestUtils.captureFailureScreenshot(driver, "testLoginPageElementsLoading_Error");
            TestUtils.logTestResult("testLoginPageElementsLoading", false, "Test hatası: " + e.getMessage());
            Assert.fail("Test sırasında hata oluştu: " + e.getMessage());
        }
    }
}