package com.testautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;

/**
 * TRENDYOL Login sayfası - Güncel 2025 yapısı
 * Modern selector'lar ve çoklu yaklaşım kullanır
 * Basit header button sistemi kullanır (dropdown yok)
 */
public class LoginPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;
    
    // ========================================
    // GÜNCEL TRENDYOL SELECTOR'LAR - 2025
    // ========================================
    
    // TRENDYOL GİRİŞ YAP DİREKT BUTONU - Dropdown yok, direkt header'da
    @FindBy(xpath = "//span[contains(text(), 'Giriş Yap')]")
    private WebElement girisYapDirectButton;
    
    @FindBy(xpath = "//a[contains(text(), 'Giriş Yap')]")
    private WebElement girisYapLinkButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Giriş Yap')]")
    private WebElement girisYapButton;
    
    // TRENDYOL LOGIN BUTTON SELECTOR'LAR - Multiple approaches
    @FindBy(css = "a[href*='giris'], a[href*='login']")
    private WebElement loginLink;
    
    @FindBy(css = "button[class*='login'], button[class*='account'], button[class*='user']")
    private WebElement loginButtonByClass;
    
    @FindBy(css = "[data-test-id*='login'], [data-testid*='login']")
    private WebElement loginButtonByTestId;
    
    // TRENDYOL HEADER LOGIN AREA
    @FindBy(css = "div[class*='account-nav'], div[class*='user-nav'], div[class*='login-nav']")
    private WebElement headerLoginArea;
    
    @FindBy(css = "div[class*='header'] a[href*='giris']")
    private WebElement headerLoginLink;
    
    @FindBy(css = "div[class*='header'] button[class*='login']")
    private WebElement headerLoginButton;
    
    // TRENDYOL Email Input - SPESİFİK ID'LER
    @FindBy(id = "login-email")
    private WebElement trendyolEmailById;
    
    @FindBy(name = "email")
    private WebElement emailByName;
    
    @FindBy(css = "input[type='email']")
    private WebElement emailByType;
    
    @FindBy(css = "input[data-test-id='email-input']")
    private WebElement emailByTestId;
    
    @FindBy(css = "input[placeholder*='mail' i], input[placeholder*='e-posta' i]")
    private WebElement emailByPlaceholder;
    
    @FindBy(xpath = "//input[@type='text' and (contains(@placeholder, 'mail') or contains(@placeholder, 'Mail') or contains(@placeholder, 'e-posta'))]")
    private WebElement emailByXpath;
    
    // TRENDYOL Password Input - SPESİFİK ID'LER
    @FindBy(id = "login-password")
    private WebElement trendyolPasswordById;
    
    @FindBy(name = "password")
    private WebElement passwordByName;
    
    @FindBy(css = "input[type='password']")
    private WebElement passwordByType;
    
    @FindBy(css = "input[data-test-id='password-input']")
    private WebElement passwordByTestId;
    
    @FindBy(css = "input[placeholder*='şifre' i], input[placeholder*='parola' i]")
    private WebElement passwordByPlaceholder;
    
    // Submit Button - Çoklu Yaklaşım
    @FindBy(css = "button[type='submit']")
    private WebElement submitByType;
    
    @FindBy(css = "button[data-test-id='login-button']")
    private WebElement submitByTestId;
    
    @FindBy(xpath = "//button[contains(text(), 'Giriş') or contains(text(), 'Oturum')]")
    private WebElement submitByText;
    
    @FindBy(css = "input[type='submit']")
    private WebElement submitInput;
    
    // Error Messages
    @FindBy(css = "div[class*='error'], div[class*='alert'], div[class*='message']")
    private WebElement errorMessage;
    
    @FindBy(css = "div[data-test-id='error-message']")
    private WebElement errorByTestId;
    
    @FindBy(xpath = "//div[contains(@class, 'error') or contains(@class, 'alert')]//*[contains(text(), 'hata') or contains(text(), 'yanlis') or contains(text(), 'geçersiz')]")
    private WebElement errorByXpath;
    
    // Remember Me Checkbox - Beni Hatırla
    @FindBy(css = "input[type='checkbox'][id*='remember'], input[type='checkbox'][name*='remember']")
    private WebElement rememberMeCheckbox;
    
    @FindBy(css = "input[data-test-id='remember-me-checkbox']")
    private WebElement rememberMeByTestId;
    
    @FindBy(xpath = "//input[@type='checkbox' and (contains(@id, 'remember') or contains(@name, 'remember'))]")
    private WebElement rememberMeByXpath;
    
    @FindBy(xpath = "//label[contains(text(), 'Beni hatırla') or contains(text(), 'Hatırla')]//input[@type='checkbox']")
    private WebElement rememberMeByLabel;
    
    // ========================================
    // CONSTRUCTOR
    // ========================================
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Süre kısaltıldı
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        
        PageFactory.initElements(driver, this);
        System.out.println("🔐 Güncel TRENDYOL LoginPage sınıfı başlatıldı (2025)");
        System.out.println("⏰ WebDriverWait süresi: 15 saniye (HIZ OPTİMİZE)");
        System.out.println("🚀 Basit header button sistemi kullanılıyor (dropdown yok)");
        System.out.println("🎯 Spesifik ID'ler: login-email, login-password");
    }
    
    // ========================================
    // GELIŞMIŞ ELEMENT BULMA METODLARI
    // ========================================
    
    /**
     * TRENDYOL "Giriş Yap" direkt buttonunu bulur - Dropdown yok, direkt header'da
     */
    private WebElement findTrendyolGirisYapButton() {
        System.out.println("🔍 TRENDYOL 'Giriş Yap' butonu aranıyor (Direkt header'da)...");
        
        // TRENDYOL direkt buton selector'ları
        WebElement[] loginElements = {
            girisYapDirectButton, girisYapLinkButton, girisYapButton, loginLink,
            loginButtonByClass, loginButtonByTestId, headerLoginArea, headerLoginLink, headerLoginButton
        };
        
        String[] descriptions = {
            "Giriş Yap Direct Button", "Giriş Yap Link Button", "Giriş Yap Button", "Login Link",
            "Login Button By Class", "Login Button By Test ID", "Header Login Area", "Header Login Link", "Header Login Button"
        };
        
        for (int i = 0; i < loginElements.length; i++) {
            try {
                if (loginElements[i] != null && loginElements[i].isDisplayed() && loginElements[i].isEnabled()) {
                    System.out.println("   ✅ " + descriptions[i] + " ile bulundu");
                    return loginElements[i];
                }
            } catch (Exception e) {
                System.out.println("   ❌ " + descriptions[i] + " ile bulunamadı");
            }
        }
        
        // Manuel arama - "Giriş Yap" text'i ara
        try {
            System.out.println("   🔄 TRENDYOL Manuel 'Giriş Yap' text arama yapılıyor...");
            
            List<WebElement> textElements = driver.findElements(By.xpath("//*[text()='Giriş Yap' or contains(text(), 'Giriş Yap')]"));
            for (WebElement element : textElements) {
                if (element.isDisplayed() && element.isEnabled()) {
                    System.out.println("   ✅ Manuel 'Giriş Yap' text bulundu: " + element.getText());
                    return element;
                }
            }
            
        } catch (Exception e) {
            System.out.println("   ❌ Manuel arama başarısız: " + e.getMessage());
        }
        
        System.err.println("❌ TRENDYOL 'Giriş Yap' butonu hiçbir yöntemle bulunamadı!");
        return null;
    }
    
    /**
     * TRENDYOL login sayfasını açmak için direkt button'a tıklar
     * Dropdown yok, direkt header'daki butona basılacak
     */
    private void clickTrendyolLoginButton() {
        try {
            System.out.println("🔄 TRENDYOL login butonuna tıklanıyor...");
            
            // Login buttonunu bul
            WebElement loginButton = findTrendyolGirisYapButton();
            if (loginButton == null) {
                throw new RuntimeException("TRENDYOL login butonu bulunamadı!");
            }
            
            // Butonun tıklanabilir olmasını bekle
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            
            // Scroll to button if needed
            js.executeScript("arguments[0].scrollIntoView(true);", loginButton);
            Thread.sleep(300);
            
            // Login butonuna tıkla - Çoklu yaklaşım
            boolean clickSuccess = false;
            
            // Yöntem 1: Normal tıklama
            try {
                loginButton.click();
                System.out.println("✅ TRENDYOL login butonuna normal tıklama ile başarıyla tıklandı");
                clickSuccess = true;
            } catch (Exception e) {
                System.out.println("⚠️ Normal tıklama başarısız: " + e.getMessage());
            }
            
            // Yöntem 2: JavaScript tıklama
            if (!clickSuccess) {
                try {
                    js.executeScript("arguments[0].click();", loginButton);
                    System.out.println("✅ TRENDYOL login butonuna JavaScript ile başarıyla tıklandı");
                    clickSuccess = true;
                } catch (Exception e) {
                    System.out.println("❌ JavaScript tıklama da başarısız: " + e.getMessage());
                }
            }
            
            // Yöntem 3: Actions ile tıklama
            if (!clickSuccess) {
                try {
                    actions.moveToElement(loginButton).click().perform();
                    System.out.println("✅ TRENDYOL login butonuna Actions ile başarıyla tıklandı");
                    clickSuccess = true;
                } catch (Exception e) {
                    System.out.println("❌ Actions tıklama da başarısız: " + e.getMessage());
                }
            }
            
            if (!clickSuccess) {
                throw new RuntimeException("TRENDYOL login butonuna hiçbir yöntemle tıklanamadı!");
            }
            
            // Tıklama sonrası yönlendirme için bekle
            System.out.println("⏳ Login sayfasına yönlendirme bekleniyor...");
            Thread.sleep(1000);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ TRENDYOL login butonu tıklama işlemi kesintiye uğradı: " + e.getMessage());
            throw new RuntimeException("TRENDYOL login butonu tıklama işlemi kesintiye uğradı", e);
        } catch (Exception e) {
            System.err.println("❌ TRENDYOL login butonu tıklama hatası: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("TRENDYOL login butonuna tıklanamadı", e);
        }
    }
    
    /**
     * TRENDYOL Email input alanını bulur - SPESİFİK ID'LER
     */
    private WebElement findEmailInput() {
        System.out.println("🔍 TRENDYOL Email input alanı aranıyor...");
        
        // Önce spesifik Trendyol ID'sini dene
        try {
            if (trendyolEmailById != null && trendyolEmailById.isDisplayed() && trendyolEmailById.isEnabled()) {
                System.out.println("   ✅ TRENDYOL Email ID='login-email' ile bulundu");
                return trendyolEmailById;
            }
        } catch (Exception e) {
            System.out.println("   ❌ TRENDYOL Email ID bulunamadı: " + e.getMessage());
        }
        
        WebElement[] emailElements = {
            emailByName, emailByType, emailByTestId, emailByPlaceholder, emailByXpath
        };
        
        String[] descriptions = {
            "Email By Name", "Email By Type", "Email By Test ID", "Email By Placeholder", "Email By XPath"
        };
        
        for (int i = 0; i < emailElements.length; i++) {
            try {
                if (emailElements[i] != null && emailElements[i].isDisplayed() && emailElements[i].isEnabled()) {
                    System.out.println("   ✅ " + descriptions[i] + " ile bulundu");
                    return emailElements[i];
                }
            } catch (Exception e) {
                System.out.println("   ❌ " + descriptions[i] + " ile bulunamadı");
            }
        }
        
        // Manuel arama - Trendyol spesifik
        try {
            System.out.println("   🔄 Manuel TRENDYOL email input arama...");
            
            // Önce ID ile dene
            WebElement emailByIdManual = driver.findElement(By.id("login-email"));
            if (emailByIdManual.isDisplayed() && emailByIdManual.isEnabled()) {
                System.out.println("   ✅ Manuel ID='login-email' ile bulundu");
                return emailByIdManual;
            }
        } catch (Exception e) {
            System.out.println("   ❌ Manuel ID araması başarısız: " + e.getMessage());
        }
        
        try {
            List<WebElement> inputs = driver.findElements(By.xpath("//input[@type='text' or @type='email']"));
            for (WebElement input : inputs) {
                if (input.isDisplayed() && input.isEnabled()) {
                    System.out.println("   ✅ Manuel email input bulundu");
                    return input;
                }
            }
        } catch (Exception e) {
            System.out.println("   ❌ Manuel email input araması başarısız");
        }
        
        System.err.println("❌ Email input alanı bulunamadı!");
        return null;
    }
    
    /**
     * TRENDYOL Password input alanını bulur - SPESİFİK ID'LER
     */
    private WebElement findPasswordInput() {
        System.out.println("🔍 TRENDYOL Password input alanı aranıyor...");
        
        // Önce spesifik Trendyol ID'sini dene
        try {
            if (trendyolPasswordById != null && trendyolPasswordById.isDisplayed() && trendyolPasswordById.isEnabled()) {
                System.out.println("   ✅ TRENDYOL Password ID='login-password' ile bulundu");
                return trendyolPasswordById;
            }
        } catch (Exception e) {
            System.out.println("   ❌ TRENDYOL Password ID bulunamadı: " + e.getMessage());
        }
        
        WebElement[] passwordElements = {
            passwordByName, passwordByType, passwordByTestId, passwordByPlaceholder
        };
        
        String[] descriptions = {
            "Password By Name", "Password By Type", "Password By Test ID", "Password By Placeholder"
        };
        
        for (int i = 0; i < passwordElements.length; i++) {
            try {
                if (passwordElements[i] != null && passwordElements[i].isDisplayed() && passwordElements[i].isEnabled()) {
                    System.out.println("   ✅ " + descriptions[i] + " ile bulundu");
                    return passwordElements[i];
                }
            } catch (Exception e) {
                System.out.println("   ❌ " + descriptions[i] + " ile bulunamadı");
            }
        }
        
        // Manuel arama - Trendyol spesifik
        try {
            System.out.println("   🔄 Manuel TRENDYOL password input arama...");
            
            // Önce ID ile dene
            WebElement passwordByIdManual = driver.findElement(By.id("login-password"));
            if (passwordByIdManual.isDisplayed() && passwordByIdManual.isEnabled()) {
                System.out.println("   ✅ Manuel ID='login-password' ile bulundu");
                return passwordByIdManual;
            }
        } catch (Exception e) {
            System.out.println("   ❌ Manuel ID araması başarısız: " + e.getMessage());
        }
        
        try {
            List<WebElement> inputs = driver.findElements(By.xpath("//input[@type='password']"));
            for (WebElement input : inputs) {
                if (input.isDisplayed() && input.isEnabled()) {
                    System.out.println("   ✅ Manuel password input bulundu");
                    return input;
                }
            }
        } catch (Exception e) {
            System.out.println("   ❌ Manuel password input araması başarısız");
        }
        
        System.err.println("❌ Password input alanı bulunamadı!");
        return null;
    }
    
    /**
     * Submit buttonunu bulur
     */
    private WebElement findSubmitButton() {
        System.out.println("🔍 Submit button aranıyor...");
        
        WebElement[] submitElements = {
            submitByType, submitByTestId, submitByText, submitInput
        };
        
        String[] descriptions = {
            "Submit By Type", "Submit By Test ID", "Submit By Text", "Submit Input"
        };
        
        for (int i = 0; i < submitElements.length; i++) {
            try {
                if (submitElements[i] != null && submitElements[i].isDisplayed() && submitElements[i].isEnabled()) {
                    System.out.println("   ✅ " + descriptions[i] + " ile bulundu");
                    return submitElements[i];
                }
            } catch (Exception e) {
                System.out.println("   ❌ " + descriptions[i] + " ile bulunamadı");
            }
        }
        
        // Manuel arama
        try {
            List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(text(), 'Giriş') or contains(text(), 'Oturum') or @type='submit'] | //input[@type='submit']"));
            for (WebElement button : buttons) {
                if (button.isDisplayed() && button.isEnabled()) {
                    System.out.println("   ✅ Manuel submit button bulundu: " + button.getText());
                    return button;
                }
            }
        } catch (Exception e) {
            System.out.println("   ❌ Manuel submit button araması başarısız");
        }
        
        System.err.println("❌ Submit button bulunamadı!");
        return null;
    }
    
    // ========================================
    // ANA NAVİGASYON METODU - GELİŞTİRİLMİŞ
    // ========================================
    
    /**
     * TRENDYOL ana sayfasından login sayfasına yönlendirme işlemi
     * Basit - direkt header'daki "Giriş Yap" butonuna tıklar
     */
    public void navigateToLoginPage() {
        try {
            System.out.println("🔄 TRENDYOL Login sayfasına yönlendirme işlemi başlıyor...");
            
            // Basit - direkt login butonuna tıkla
            System.out.println("📍 ADIM 1: TRENDYOL 'Giriş Yap' butonuna tıklanıyor...");
            clickTrendyolLoginButton();
            
            // Yönlendirme sonucunu kontrol et
            String currentUrl = driver.getCurrentUrl();
            System.out.println("🔗 Yönlendirme sonrası URL: " + currentUrl);
            
            if (isOnLoginPage()) {
                System.out.println("✅ TRENDYOL LOGIN SAYFASINA BAŞARIYLA YÖNLENDİRİLDİ!");
                System.out.println("📄 Sayfa başlığı: " + driver.getTitle());
            } else {
                System.out.println("⚠️ Login sayfasında değiliz veya sayfanın yüklenmesi bekleniyor...");
                System.out.println("📄 Mevcut sayfa başlığı: " + driver.getTitle());
            }
            
        } catch (Exception e) {
            System.err.println("❌ TRENDYOL Login sayfasına yönlendirme hatası: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("TRENDYOL Login sayfasına yönlendirilemedi", e);
        }
    }
    
    /**
     * TRENDYOL için bu metod artık kullanılmaz - dropdown yok
     * Eski Hepsiburada uyumluluğu için bırakıldı
     */
    public void openGirisYapDropdown() {
        System.out.println("ℹ️ TRENDYOL'da dropdown sistem yok - bu metod artık kullanılmaz");
        System.out.println("✅ Direkt login butonuna tıklama sistemi kullanılıyor");
    }
    
    // ========================================
    // INPUT METHODS - GELİŞTİRİLMİŞ
    // ========================================
    
    /**
     * TRENDYOL Email alanına email girer - İYİLEŞTİRİLMİŞ
     */
    public void enterEmail(String email) {
        try {
            System.out.println("📧 TRENDYOL Email girişi yapılıyor: " + email);
            
            WebElement emailField = findEmailInput();
            if (emailField == null) {
                throw new RuntimeException("TRENDYOL Email input alanı bulunamadı!");
            }
            
            // Alanı temizle ve odakla
            wait.until(ExpectedConditions.elementToBeClickable(emailField));
            
            // Önce JavaScript ile tamamen temizle
            js.executeScript("arguments[0].value = '';", emailField);
            
            // Normal clear() işlemi
            emailField.clear();
            
            // Tekrar temizle (güvenlik için)
            js.executeScript("arguments[0].value = '';", emailField);
            
            // Alana tıkla ve odakla
            emailField.click();
            
            // Kısa bekle
            Thread.sleep(200);
            
            // Email'i gir
            emailField.sendKeys(email);
            
            // Doğrulama - değer doğru mu?
            String currentValue = (String) js.executeScript("return arguments[0].value;", emailField);
            System.out.println("✅ Email başarıyla girildi: " + email);
            System.out.println("📋 Alanda görünen değer: " + currentValue);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Email giriş işlemi kesintiye uğradı: " + e.getMessage());
            throw new RuntimeException("Email giriş işlemi kesintiye uğradı", e);
        } catch (Exception e) {
            System.err.println("❌ Email girilemedi: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * TRENDYOL Şifre alanına şifre girer - İYİLEŞTİRİLMİŞ
     */
    public void enterPassword(String password) {
        try {
            System.out.println("🔒 TRENDYOL Şifre girişi yapılıyor...");
            
            WebElement passwordField = findPasswordInput();
            if (passwordField == null) {
                throw new RuntimeException("TRENDYOL Password input alanı bulunamadı!");
            }
            
            // Alanı temizle ve odakla
            wait.until(ExpectedConditions.elementToBeClickable(passwordField));
            
            // Önce JavaScript ile tamamen temizle
            js.executeScript("arguments[0].value = '';", passwordField);
            
            // Normal clear() işlemi
            passwordField.clear();
            
            // Tekrar temizle (güvenlik için)
            js.executeScript("arguments[0].value = '';", passwordField);
            
            // Alana tıkla ve odakla
            passwordField.click();
            
            // Kısa bekle
            Thread.sleep(200);
            
            // Şifre'yi gir
            passwordField.sendKeys(password);
            
            // Doğrulama - değer doğru mu?
            String currentValue = (String) js.executeScript("return arguments[0].value;", passwordField);
            System.out.println("✅ Şifre başarıyla girildi: " + "*".repeat(password.length()));
            System.out.println("📋 Alanda görünen değer uzunluğu: " + currentValue.length());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Şifre giriş işlemi kesintiye uğradı: " + e.getMessage());
            throw new RuntimeException("Şifre giriş işlemi kesintiye uğradı", e);
        } catch (Exception e) {
            System.err.println("❌ Şifre girilemedi: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Login butonuna tıklar - Gelişmiş
     */
    public void clickLoginButton() {
        try {
            System.out.println("🔘 Login butonuna tıklanıyor...");
            
            WebElement submitButton = findSubmitButton();
            if (submitButton == null) {
                throw new RuntimeException("Submit button bulunamadı!");
            }
            
            // Butonun aktif olmasını bekle
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            
            // Scroll to button
            js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
            Thread.sleep(200);
            
            // Tıkla
            try {
                submitButton.click();
                System.out.println("✅ Login butonuna tıklandı");
            } catch (Exception e) {
                // JavaScript ile tıklamayı dene
                js.executeScript("arguments[0].click();", submitButton);
                System.out.println("✅ Login butonuna JavaScript ile tıklandı");
            }
            
            // Login işleminin tamamlanması için bekle
            Thread.sleep(2000);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Login butonu tıklama işlemi kesintiye uğradı: " + e.getMessage());
            throw new RuntimeException("Login butonu tıklama işlemi kesintiye uğradı", e);
        } catch (Exception e) {
            System.err.println("❌ Login butonuna tıklanamadı: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * "Beni Hatırla" checkbox'ına tıklar
     */
    public void clickRememberMeCheckbox() {
        try {
            System.out.println("☑️ Beni Hatırla checkbox'ı aranıyor...");
            
            WebElement rememberMeElement = findRememberMeCheckbox();
            if (rememberMeElement == null) {
                System.out.println("⚠️ Beni Hatırla checkbox'ı bulunamadı, bu normal olabilir");
                return;
            }
            
            // Checkbox'a tıkla
            try {
                wait.until(ExpectedConditions.elementToBeClickable(rememberMeElement));
                rememberMeElement.click();
                System.out.println("✅ Beni Hatırla checkbox'ı işaretlendi");
            } catch (Exception e) {
                // JavaScript ile tıklamayı dene
                js.executeScript("arguments[0].click();", rememberMeElement);
                System.out.println("✅ Beni Hatırla checkbox'ı JavaScript ile işaretlendi");
            }
            
            // Kısa bekleme
            Thread.sleep(300);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Beni Hatırla checkbox işlemi kesintiye uğradı: " + e.getMessage());
            throw new RuntimeException("Beni Hatırla checkbox işlemi kesintiye uğradı", e);
        } catch (Exception e) {
            System.err.println("❌ Beni Hatırla checkbox'ına tıklanamadı: " + e.getMessage());
            // Bu hata kritik değil, devam et
        }
    }
    
    /**
     * "Beni Hatırla" checkbox'ını bulur
     */
    private WebElement findRememberMeCheckbox() {
        System.out.println("🔍 Beni Hatırla checkbox aranıyor...");
        
        WebElement[] rememberMeElements = {
            rememberMeCheckbox, rememberMeByTestId, rememberMeByXpath, rememberMeByLabel
        };
        
        String[] descriptions = {
            "Remember Me Checkbox", "Remember Me By Test ID", "Remember Me By XPath", "Remember Me By Label"
        };
        
        for (int i = 0; i < rememberMeElements.length; i++) {
            try {
                if (rememberMeElements[i] != null && rememberMeElements[i].isDisplayed() && rememberMeElements[i].isEnabled()) {
                    System.out.println("   ✅ " + descriptions[i] + " ile bulundu");
                    return rememberMeElements[i];
                }
            } catch (Exception e) {
                System.out.println("   ❌ " + descriptions[i] + " ile bulunamadı");
            }
        }
        
        // Manuel arama
        try {
            System.out.println("   🔄 Manuel checkbox arama yapılıyor...");
            List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
            for (WebElement checkbox : checkboxes) {
                if (checkbox.isDisplayed() && checkbox.isEnabled()) {
                    System.out.println("   ✅ Manuel checkbox bulundu");
                    return checkbox;
                }
            }
        } catch (Exception e) {
            System.out.println("   ❌ Manuel checkbox arama başarısız");
        }
        
        System.out.println("   ⚠️ Beni Hatırla checkbox bulunamadı");
        return null;
    }
    
    // ========================================
    // COMPLETE LOGIN METHODS - GELİŞTİRİLMİŞ
    // ========================================
    
    /**
     * TRENDYOL tam login işlemini gerçekleştirir
     */
    public void performCompleteLogin(String email, String password) {
        try {
            System.out.println("\n🔐 TRENDYOL GELİŞMİŞ LOGIN İŞLEMİ BAŞLADI");
            System.out.println("👤 Email: " + email);
            System.out.println("🕒 Timestamp: " + java.time.LocalDateTime.now());
            
            System.out.println("\n📍 ADIM 1: Login sayfasına yönlendirme");
            navigateToLoginPage();
            
            System.out.println("\n📍 ADIM 2: Email girişi");
            enterEmail(email);
            
            System.out.println("\n📍 ADIM 3: Şifre girişi");
            enterPassword(password);
            
            System.out.println("\n📍 ADIM 4: Login butonuna tıklama");
            clickLoginButton();
            
            System.out.println("\n✅ TRENDYOL LOGIN İŞLEMİ TAMAMLANDI!");
            System.out.println("🔗 Son URL: " + driver.getCurrentUrl());
            
        } catch (Exception e) {
            System.err.println("❌ TRENDYOL login işlemi başarısız: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("TRENDYOL Complete login failed", e);
        }
    }
    
    /**
     * TRENDYOL "Beni Hatırla" özelliği ile login işlemi gerçekleştirir
     */
    public void performLoginWithRememberMe(String email, String password) {
        try {
            System.out.println("\n🔐 TRENDYOL BENİ HATIRLA İLE LOGIN İŞLEMİ BAŞLADI");
            System.out.println("👤 Email: " + email);
            System.out.println("☑️ Beni Hatırla: Aktif");
            System.out.println("🕒 Timestamp: " + java.time.LocalDateTime.now());
            
            System.out.println("\n📍 ADIM 1: Login sayfasına yönlendirme");
            navigateToLoginPage();
            
            System.out.println("\n📍 ADIM 2: Email girişi");
            enterEmail(email);
            
            System.out.println("\n📍 ADIM 3: Şifre girişi");
            enterPassword(password);
            
            System.out.println("\n📍 ADIM 4: Beni Hatırla seçeneğini işaretle");
            clickRememberMeCheckbox();
            
            System.out.println("\n📍 ADIM 5: Login butonuna tıklama");
            clickLoginButton();
            
            System.out.println("\n✅ TRENDYOL BENİ HATIRLA İLE LOGIN İŞLEMİ TAMAMLANDI!");
            System.out.println("🔗 Son URL: " + driver.getCurrentUrl());
            
        } catch (Exception e) {
            System.err.println("❌ TRENDYOL Beni Hatırla ile login işlemi başarısız: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("TRENDYOL Login with Remember Me failed", e);
        }
    }
    
    // ========================================
    // VALIDATION METHODS - GELİŞTİRİLMİŞ
    // ========================================
    
    /**
     * Login sayfasının yüklenip yüklenmediğini kontrol eder
     */
    public boolean isLoginPageLoaded() {
        try {
            System.out.println("🔍 Login sayfası yükleme kontrolü...");
            
            boolean emailExists = (findEmailInput() != null);
            boolean passwordExists = (findPasswordInput() != null);
            boolean submitExists = (findSubmitButton() != null);
            
            boolean isLoaded = emailExists && passwordExists && submitExists;
            
            System.out.println("📊 Login sayfası element analizi:");
            System.out.println("   📧 Email field: " + (emailExists ? "✅ Var" : "❌ Yok"));
            System.out.println("   🔒 Password field: " + (passwordExists ? "✅ Var" : "❌ Yok"));
            System.out.println("   🔘 Submit button: " + (submitExists ? "✅ Var" : "❌ Yok"));
            System.out.println("   📋 Genel Durum: " + (isLoaded ? "✅ Yüklendi" : "❌ Eksik"));
            
            return isLoaded;
            
        } catch (Exception e) {
            System.err.println("❌ Login sayfası kontrolü hatası: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Hata mesajı kontrol eder
     */
    public boolean isErrorMessageDisplayed() {
        try {
            if (errorMessage != null && errorMessage.isDisplayed()) {
                System.out.println("⚠️ Hata mesajı bulundu (Class ile)");
                return true;
            }
        } catch (Exception e1) {
            try {
                if (errorByTestId != null && errorByTestId.isDisplayed()) {
                    System.out.println("⚠️ Hata mesajı bulundu (Test ID ile)");
                    return true;
                }
            } catch (Exception e2) {
                try {
                    if (errorByXpath != null && errorByXpath.isDisplayed()) {
                        System.out.println("⚠️ Hata mesajı bulundu (XPath ile)");
                        return true;
                    }
                } catch (Exception e3) {
                    System.out.println("ℹ️ Hata mesajı bulunamadı");
                }
            }
        }
        return false;
    }
    
    /**
     * Hata mesajının metnini alır
     */
    public String getErrorMessageText() {
        try {
            if (errorMessage != null && errorMessage.isDisplayed()) {
                String errorText = errorMessage.getText().trim();
                System.out.println("📄 Hata mesajı: " + errorText);
                return errorText;
            }
        } catch (Exception e) {
            System.out.println("ℹ️ Hata mesajı metni okunamadı");
        }
        return "";
    }
    
    /**
     * TRENDYOL Login sayfasında olup olmadığını kontrol eder
     */
    public boolean isOnLoginPage() {
        String currentUrl = driver.getCurrentUrl().toLowerCase();
        boolean isOnLoginPage = currentUrl.contains("giris") || 
                               currentUrl.contains("login") || 
                               currentUrl.contains("hesap") ||
                               currentUrl.contains("uye") ||
                               currentUrl.contains("trendyol.com/giris") ||
                               currentUrl.contains("sign-in");
        
        System.out.println("🔗 Mevcut URL: " + driver.getCurrentUrl());
        System.out.println("📍 TRENDYOL Login sayfasında mıyız? " + (isOnLoginPage ? "✅ Evet" : "❌ Hayır"));
        
        return isOnLoginPage;
    }
    
    /**
     * Sayfa başlığını alır
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        System.out.println("📄 Sayfa başlığı: " + title);
        return title;
    }
    

}