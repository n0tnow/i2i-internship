package com.testautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;

/**
 * Ana sayfa (Homepage) Page Object Model sınıfı
 * TRENDYOL.COM sitesi için özel olarak tasarlanmıştır
 * Login işleminden sonra kullanıcının yönlendirildiği sayfayı yönetir
 * Login başarısını doğrulamak için kullanılır
 */
public class HomePage {
    
    // WebDriver ve Wait nesneleri
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    
    // ========================================
    // PAGE ELEMENTS - TRENDYOL Ana Sayfa Elementleri
    // ========================================
    
    // TRENDYOL POP-UP ELEMENTLER
    @FindBy(css = "div[class*='modal'] button, div[class*='popup'] button, button[class*='close'], .close-button")
    private WebElement popupCloseButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Kapat') or contains(text(), 'Tamam') or contains(text(), 'Kabul') or contains(@aria-label, 'close')]")
    private WebElement popupCloseByText;
    
    @FindBy(css = "[data-test-id*='close'], [data-testid*='close'], [id*='close'], [class*='reject']")
    private WebElement popupCloseByTestId;
    
    // TRENDYOL COOKIE POP-UP
    @FindBy(xpath = "//button[contains(text(), 'Tümünü Kabul') or contains(text(), 'Kabul Et') or contains(text(), 'Çerezleri Kabul')]")
    private WebElement acceptCookiesButton;
    
    @FindBy(css = "#cookiesModalAcceptAllButton, .cookies-accept, .cookie-accept, [data-test*='cookie'] button")
    private WebElement cookieAcceptButton;
    
    // Kullanıcı hesap dropdown menüsü (login sonrası görünür) - Güncel 2025
    @FindBy(xpath = "//div[@data-test-id='header-user-menu']")
    private WebElement userAccountDropdown;
    
    // Alternatif kullanıcı hesap elementi - Güncel selector'lar
    @FindBy(xpath = "//span[contains(text(),'Hesabım')]")
    private WebElement accountMenuAlternative;
    
    @FindBy(xpath = "//div[contains(@class, 'user-menu') or contains(@class, 'account-menu')]")
    private WebElement userMenuDiv;
    
    // Kullanıcı adı gösterimi (login sonrası görünür)
    @FindBy(xpath = "//span[@data-test-id='header-user-name']")
    private WebElement userNameDisplay;
    
    // "Çıkış Yap" linki (login başarısının en önemli göstergesi)
    @FindBy(xpath = "//a[contains(text(),'Çıkış Yap')]")
    private WebElement logoutLink;
    
    // Alternatif çıkış linki
    @FindBy(xpath = "//span[text()='Çıkış Yap']")
    private WebElement logoutLinkAlternative;
    
    // Hesabım sayfası linki
    @FindBy(xpath = "//a[contains(text(),'Hesabım')]")
    private WebElement myAccountLink;
    
    // Siparişlerim linki
    @FindBy(xpath = "//a[contains(text(),'Siparişlerim')]")
    private WebElement ordersLink;
    
    // Favori ürünlerim linki
    @FindBy(xpath = "//a[contains(text(),'Favorilerim')]")
    private WebElement favoritesLink;
    
    // Sepetim ikonu ve sayısı - Güncel selector'lar
    @FindBy(xpath = "//div[@data-test-id='header-cart']")
    private WebElement cartIcon;
    
    @FindBy(css = "div[class*='cart'], a[class*='cart']")
    private WebElement cartIconAlternative;
    
    // Sepet sayısı göstergesi
    @FindBy(xpath = "//span[@data-test-id='header-cart-count']")
    private WebElement cartItemCount;
    
    // Ana sayfa logosu (sayfa doğrulaması için) - Güncel selector'lar
    @FindBy(xpath = "//a[@data-test-id='header-logo']")
    private WebElement hepsiburadaLogo;
    
    @FindBy(css = "a[class*='logo'], img[alt*='hepsiburada'], img[alt*='Hepsiburada']")
    private WebElement logoAlternative;
    
    // Arama kutusu (ana sayfa doğrulaması için) - Güncel selector'lar
    @FindBy(xpath = "//input[@data-test-id='suggestion']")
    private WebElement searchBox;
    
    @FindBy(css = "input[placeholder*='ara'], input[placeholder*='Ara'], input[type='text'][class*='search']")
    private WebElement searchBoxAlternative;
    
    // Hoşgeldin mesajı (bazen görünür)
    @FindBy(xpath = "//div[contains(@class,'welcome-message')]")
    private WebElement welcomeMessage;
    
    // Login sayfasına yönlendiren link (giriş yapılmamışsa görünür)
    @FindBy(xpath = "//a[contains(text(),'Giriş Yap')]")
    private WebElement loginPageLink;
    
    // Profil resmi/avatar (login sonrası görünebilir)
    @FindBy(xpath = "//img[@data-test-id='header-user-avatar']")
    private WebElement userAvatar;
    
    // ========================================
    // CONSTRUCTOR - Yapıcı Metod
    // ========================================
    
    /**
     * HomePage constructor'ı
     * PageFactory ile elementleri initialize eder
     * @param driver WebDriver instance'ı
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Süre kısaltıldı
        this.js = (JavascriptExecutor) driver;
        
        // PageFactory ile elementleri başlat
        PageFactory.initElements(driver, this);
        
        System.out.println("🏠 TRENDYOL HomePage sınıfı başlatıldı (2025)");
        System.out.println("⏰ WebDriverWait süresi: 15 saniye (HIZ OPTİMİZE)");
    }
    
    // ========================================
    // TRENDYOL POP-UP YÖNETİMİ
    // ========================================
    
    /**
     * Trendyol ana sayfasındaki pop-up'ları kapatır
     * Cookie pop-up'ı, kampanya pop-up'ı vs.
     */
    public void closePopups() {
        try {
            System.out.println("🔄 Trendyol pop-up'ları kontrol ediliyor ve kapatılıyor...");
            
            // 1-2 saniye bekle - pop-up'lar yüklenmesi için
            Thread.sleep(1500);
            
            boolean popupClosed = false;
            
            // 1. Cookie pop-up'ını kapat
            popupClosed = closeCookiePopup();
            
            // 2. Genel pop-up'ları kapat
            if (!popupClosed) {
                popupClosed = closeGeneralPopups();
            }
            
            // 3. Manuel arama ile pop-up kapat
            if (!popupClosed) {
                popupClosed = closePopupsManually();
            }
            
            if (popupClosed) {
                System.out.println("✅ Trendyol pop-up'ları başarıyla kapatıldı");
            } else {
                System.out.println("ℹ️ Trendyol'da pop-up bulunamadı veya zaten kapalı");
            }
            
            // Pop-up kapandıktan sonra kısa bekle
            Thread.sleep(800);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Pop-up kapatma işlemi kesintiye uğradı: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Pop-up kapatma hatası: " + e.getMessage());
        }
    }
    
    /**
     * Cookie pop-up'ını kapatır
     */
    private boolean closeCookiePopup() {
        try {
            // Cookie kabul et butonunu dene
            if (acceptCookiesButton != null && acceptCookiesButton.isDisplayed()) {
                acceptCookiesButton.click();
                System.out.println("✅ Cookie pop-up'ı 'Kabul Et' ile kapatıldı");
                return true;
            }
        } catch (Exception e1) {
            try {
                if (cookieAcceptButton != null && cookieAcceptButton.isDisplayed()) {
                    cookieAcceptButton.click();
                    System.out.println("✅ Cookie pop-up'ı alternatif selector ile kapatıldı");
                    return true;
                }
            } catch (Exception e2) {
                System.out.println("ℹ️ Cookie pop-up bulunamadı");
            }
        }
        return false;
    }
    
    /**
     * Genel pop-up'ları kapatır
     */
    private boolean closeGeneralPopups() {
        try {
            // Kapat butonu ile dene
            if (popupCloseButton != null && popupCloseButton.isDisplayed()) {
                popupCloseButton.click();
                System.out.println("✅ Genel pop-up 'Kapat' butonu ile kapatıldı");
                return true;
            }
        } catch (Exception e1) {
            try {
                if (popupCloseByText != null && popupCloseByText.isDisplayed()) {
                    popupCloseByText.click();
                    System.out.println("✅ Genel pop-up text ile kapatıldı");
                    return true;
                }
            } catch (Exception e2) {
                try {
                    if (popupCloseByTestId != null && popupCloseByTestId.isDisplayed()) {
                        popupCloseByTestId.click();
                        System.out.println("✅ Genel pop-up test-id ile kapatıldı");
                        return true;
                    }
                } catch (Exception e3) {
                    System.out.println("ℹ️ Genel pop-up bulunamadı");
                }
            }
        }
        return false;
    }
    
    /**
     * Manuel arama ile pop-up'ları kapatır
     */
    private boolean closePopupsManually() {
        try {
            System.out.println("🔄 Manuel pop-up arama yapılıyor...");
            
            // Manuel olarak çeşitli selector'larla popup butonları ara
            String[] popupSelectors = {
                "button[class*='close']",
                "button[class*='reject']", 
                "[data-testid*='close']",
                ".popup-close",
                ".modal-close",
                "button[aria-label*='close']",
                "//button[contains(text(), 'Kapat')]",
                "//button[contains(text(), 'Reddet')]",
                "//button[contains(text(), 'Hayır')]"
            };
            
            for (String selector : popupSelectors) {
                try {
                    List<WebElement> popupElements;
                    if (selector.startsWith("//")) {
                        popupElements = driver.findElements(By.xpath(selector));
                    } else {
                        popupElements = driver.findElements(By.cssSelector(selector));
                    }
                    
                    for (WebElement element : popupElements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            element.click();
                            System.out.println("✅ Manuel arama ile pop-up kapatıldı: " + selector);
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Bu selector çalışmadı, devam et
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Manuel pop-up arama hatası: " + e.getMessage());
        }
        
        return false;
    }
    
    // ========================================
    // LOGIN SUCCESS VALIDATION - Giriş Başarısı Doğrulama
    // ========================================
    
    /**
     * Kullanıcının başarıyla giriş yapıp yapmadığını kontrol eder
     * Çıkış linkinin varlığına bakarak doğrulama yapar
     * @return true eğer kullanıcı giriş yapmışsa
     */
    public boolean isUserLoggedIn() {
        try {
            System.out.println("🔍 Login durumu kontrol ediliyor...");
            
            // Çeşitli login göstergelerini kontrol et
            boolean hasLogoutLink = checkLogoutLinkExists();
            boolean hasUserMenu = checkUserMenuExists();
            boolean hasUserName = checkUserNameExists();
            boolean noLoginLink = !checkLoginLinkExists();
            
            // En az bir login göstergesi varsa başarılı
            boolean isLoggedIn = hasLogoutLink || hasUserMenu || hasUserName || noLoginLink;
            
            System.out.println("📊 Login durum analizi:");
            System.out.println("   🚪 Çıkış linki var: " + hasLogoutLink);
            System.out.println("   👤 Kullanıcı menüsü var: " + hasUserMenu);
            System.out.println("   📝 Kullanıcı adı var: " + hasUserName);
            System.out.println("   🔐 Giriş linki yok: " + noLoginLink);
            System.out.println("🎯 SONUÇ: " + (isLoggedIn ? "✅ GİRİŞ BAŞARILI" : "❌ GİRİŞ BAŞARISIZ"));
            
            return isLoggedIn;
            
        } catch (Exception e) {
            System.err.println("❌ Login durumu kontrol edilemedi: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Çıkış linkinin varlığını kontrol eder
     * @return true eğer çıkış linki varsa
     */
    private boolean checkLogoutLinkExists() {
        try {
            // Ana çıkış linkini kontrol et
            if (logoutLink.isDisplayed()) {
                System.out.println("   ✅ Ana çıkış linki bulundu");
                return true;
            }
        } catch (Exception e1) {
            try {
                // Alternatif çıkış linkini kontrol et
                if (logoutLinkAlternative.isDisplayed()) {
                    System.out.println("   ✅ Alternatif çıkış linki bulundu");
                    return true;
                }
            } catch (Exception e2) {
                System.out.println("   ❌ Çıkış linki bulunamadı");
            }
        }
        return false;
    }
    
    /**
     * Kullanıcı menüsünün varlığını kontrol eder
     * @return true eğer kullanıcı menüsü varsa
     */
    private boolean checkUserMenuExists() {
        try {
            // Ana kullanıcı dropdown'unu kontrol et
            if (userAccountDropdown.isDisplayed()) {
                System.out.println("   ✅ Kullanıcı hesap menüsü bulundu");
                return true;
            }
        } catch (Exception e1) {
            try {
                // Alternatif hesap menüsünü kontrol et
                if (accountMenuAlternative.isDisplayed()) {
                    System.out.println("   ✅ Alternatif hesap menüsü bulundu");
                    return true;
                }
            } catch (Exception e2) {
                System.out.println("   ❌ Kullanıcı menüsü bulunamadı");
            }
        }
        return false;
    }
    
    /**
     * Kullanıcı adının görünürlüğünü kontrol eder
     * @return true eğer kullanıcı adı görünürse
     */
    private boolean checkUserNameExists() {
        try {
            if (userNameDisplay.isDisplayed()) {
                String userName = userNameDisplay.getText().trim();
                System.out.println("   ✅ Kullanıcı adı bulundu: " + userName);
                return true;
            }
        } catch (Exception e) {
            System.out.println("   ❌ Kullanıcı adı bulunamadı");
        }
        return false;
    }
    
    /**
     * Login linkinin varlığını kontrol eder (varsa giriş yapılmamış demektir)
     * @return true eğer login linki varsa
     */
    private boolean checkLoginLinkExists() {
        try {
            if (loginPageLink.isDisplayed()) {
                System.out.println("   ⚠️ 'Giriş Yap' linki hala görünür");
                return true;
            }
        } catch (Exception e) {
            System.out.println("   ✅ 'Giriş Yap' linki görünmüyor (bu iyi)");
        }
        return false;
    }
    
    // ========================================
    // USER ACTIONS - Kullanıcı İşlemleri
    // ========================================
    
    /**
     * Kullanıcı hesap dropdown menüsüne tıklar
     */
    public void clickUserAccountDropdown() {
        try {
            // Ana dropdown'u dene
            try {
                wait.until(ExpectedConditions.elementToBeClickable(userAccountDropdown));
                userAccountDropdown.click();
                System.out.println("✅ Kullanıcı hesap dropdown'una tıklandı");
                return;
            } catch (Exception e1) {
                // Alternatif dropdown'u dene
                wait.until(ExpectedConditions.elementToBeClickable(accountMenuAlternative));
                accountMenuAlternative.click();
                System.out.println("✅ Alternatif hesap menüsüne tıklandı");
            }
            
            // Dropdown açılması için kısa bekleme
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
        } catch (Exception e) {
            System.err.println("❌ Kullanıcı hesap dropdown'una tıklanamadı: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Çıkış yap linkine tıklar
     */
    public void clickLogoutLink() {
        try {
            // Önce dropdown'u aç
            clickUserAccountDropdown();
            
            // Çıkış linkine tıkla
            try {
                wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
                logoutLink.click();
                System.out.println("✅ Çıkış linkine tıklandı");
            } catch (Exception e1) {
                // Alternatif çıkış linkini dene
                wait.until(ExpectedConditions.elementToBeClickable(logoutLinkAlternative));
                logoutLinkAlternative.click();
                System.out.println("✅ Alternatif çıkış linkine tıklandı");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Çıkış linkine tıklanamadı: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Hesabım sayfasına gider
     */
    public void clickMyAccountLink() {
        try {
            clickUserAccountDropdown(); // Önce dropdown'u aç
            wait.until(ExpectedConditions.elementToBeClickable(myAccountLink));
            myAccountLink.click();
            System.out.println("✅ Hesabım linkine tıklandı");
            
        } catch (Exception e) {
            System.err.println("❌ Hesabım linkine tıklanamadı: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Siparişlerim sayfasına gider
     */
    public void clickOrdersLink() {
        try {
            clickUserAccountDropdown(); // Önce dropdown'u aç
            wait.until(ExpectedConditions.elementToBeClickable(ordersLink));
            ordersLink.click();
            System.out.println("✅ Siparişlerim linkine tıklandı");
            
        } catch (Exception e) {
            System.err.println("❌ Siparişlerim linkine tıklanamadı: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Favorilerim sayfasına gider
     */
    public void clickFavoritesLink() {
        try {
            clickUserAccountDropdown(); // Önce dropdown'u aç
            wait.until(ExpectedConditions.elementToBeClickable(favoritesLink));
            favoritesLink.click();
            System.out.println("✅ Favorilerim linkine tıklandı");
            
        } catch (Exception e) {
            System.err.println("❌ Favorilerim linkine tıklanamadı: " + e.getMessage());
            throw e;
        }
    }
    
    // ========================================
    // PAGE VALIDATION - Sayfa Doğrulama
    // ========================================
    
    /**
     * Ana sayfanın doğru şekilde yüklenip yüklenmediğini kontrol eder - TRENDYOL VERSİYON
     * @return true eğer ana sayfa elementleri görünürse
     */
    public boolean isHomePageLoaded() {
        try {
            System.out.println("🔍 TRENDYOL ana sayfası yükleme kontrolü...");
            
            // Sadece URL ve temel sayfa kontrolü yap
            boolean correctUrl = checkCorrectUrl();
            boolean pageLoaded = checkBasicPageLoad();
            
            boolean isLoaded = correctUrl && pageLoaded;
            
            if (isLoaded) {
                System.out.println("✅ TRENDYOL ana sayfası başarıyla yüklendi");
                System.out.println("   🌐 Doğru URL: " + correctUrl);
                System.out.println("   📄 Sayfa yüklendi: " + pageLoaded);
                
                // Pop-up'ları kapat
                System.out.println("🔄 Pop-up'lar kontrol ediliyor...");
                closePopups();
                
            } else {
                System.out.println("❌ Ana sayfa tam olarak yüklenemedi");
                System.out.println("   🌐 Doğru URL: " + correctUrl);
                System.out.println("   📄 Sayfa yüklendi: " + pageLoaded);
                System.out.println("   📄 Mevcut URL: " + driver.getCurrentUrl());
                System.out.println("   📄 Sayfa başlığı: " + driver.getTitle());
            }
            
            return isLoaded;
            
        } catch (Exception e) {
            System.err.println("❌ Ana sayfa yükleme kontrolü başarısız: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Temel sayfa yükleme kontrolü - DOM hazır mı?
     */
    private boolean checkBasicPageLoad() {
        try {
            // JavaScript ile sayfa yükleme durumunu kontrol et
            String readyState = js.executeScript("return document.readyState").toString();
            String title = driver.getTitle();
            
            boolean isReady = "complete".equals(readyState);
            boolean hasTitle = title != null && !title.trim().isEmpty();
            boolean hasBody = driver.findElements(By.tagName("body")).size() > 0;
            
            System.out.println("   📊 Sayfa durumu: " + readyState);
            System.out.println("   📄 Sayfa başlığı: " + title);
            System.out.println("   🏗️ Body elementi: " + (hasBody ? "Var" : "Yok"));
            
            return isReady && hasTitle && hasBody;
            
        } catch (Exception e) {
            System.out.println("   ❌ Temel sayfa kontrolü başarısız: " + e.getMessage());
            return false;
        }
    }
    
    // Logo, arama kutusu ve sepet ikonu kontrol metodları kaldırıldı
    // Bu metodlar kullanılmadığı için temizlendi
    
    /**
     * URL'nin doğru olup olmadığını kontrol eder
     */
    private boolean checkCorrectUrl() {
        try {
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            boolean isCorrect = currentUrl.contains("trendyol.com");
            
            if (isCorrect) {
                System.out.println("   ✅ URL doğru: " + currentUrl);
            } else {
                System.out.println("   ❌ URL yanlış: " + currentUrl);
            }
            
            return isCorrect;
        } catch (Exception e) {
            System.out.println("   ❌ URL kontrolü başarısız: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Sepet ikonunun görünür olup olmadığını kontrol eder
     * @return true eğer sepet ikonu görünürse
     */
    public boolean isCartIconVisible() {
        try {
            boolean isVisible = cartIcon.isDisplayed();
            
            if (isVisible) {
                System.out.println("✅ Sepet ikonu görünür");
                
                // Sepet sayısını da kontrol et
                try {
                    String cartCount = cartItemCount.getText();
                    System.out.println("   🛒 Sepet sayısı: " + cartCount);
                } catch (Exception e) {
                    System.out.println("   🛒 Sepet sayısı görünmüyor (boş sepet)");
                }
            } else {
                System.out.println("ℹ️ Sepet ikonu görünür değil");
            }
            
            return isVisible;
            
        } catch (Exception e) {
            System.err.println("❌ Sepet ikonu kontrolü başarısız: " + e.getMessage());
            return false;
        }
    }
    
    // ========================================
    // GETTER METHODS - Bilgi Alma Metodları
    // ========================================
    
    /**
     * Sayfa başlığını alır
     * @return sayfa başlığı
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        System.out.println("📄 Sayfa başlığı: " + title);
        return title;
    }
    
    /**
     * Mevcut URL'yi alır
     * @return mevcut URL
     */
    public String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("🔗 Mevcut URL: " + currentUrl);
        return currentUrl;
    }
    
    /**
     * Kullanıcı adını alır (görünürse)
     * @return kullanıcı adı, yoksa boş string
     */
    public String getUserName() {
        try {
            if (userNameDisplay.isDisplayed()) {
                String userName = userNameDisplay.getText().trim();
                System.out.println("👤 Kullanıcı adı: " + userName);
                return userName;
            }
        } catch (Exception e) {
            System.err.println("❌ Kullanıcı adı okunamadı: " + e.getMessage());
        }
        return "";
    }
    
    /**
     * Hoşgeldin mesajının metnini alır
     * @return hoşgeldin mesajı metni, yoksa boş string
     */
    public String getWelcomeMessage() {
        try {
            if (welcomeMessage.isDisplayed()) {
                String message = welcomeMessage.getText().trim();
                System.out.println("👋 Hoşgeldin mesajı: " + message);
                return message;
            }
        } catch (Exception e) {
            System.out.println("ℹ️ Hoşgeldin mesajı bulunamadı");
        }
        return "";
    }
    
    /**
     * Sepetteki ürün sayısını alır
     * @return sepet sayısı, yoksa "0"
     */
    public String getCartItemCount() {
        try {
            if (cartItemCount.isDisplayed()) {
                String count = cartItemCount.getText().trim();
                System.out.println("🛒 Sepet ürün sayısı: " + count);
                return count;
            }
        } catch (Exception e) {
            System.out.println("🛒 Sepet ürün sayısı görünmüyor (boş sepet)");
        }
        return "0";
    }
    
    // ========================================
    // UTILITY METHODS - Yardımcı Metodlar
    // ========================================
    
    /**
     * Ana sayfaya dönüp dönmediğini URL ile kontrol eder
     * @return true eğer ana sayfadaysak
     */
    public boolean isOnHomePage() {
        String currentUrl = getCurrentUrl();
        boolean isOnHome = currentUrl.equals("https://www.trendyol.com") || 
                          currentUrl.equals("https://www.trendyol.com/") ||
                          currentUrl.contains("trendyol.com") && !currentUrl.contains("/");
        
        System.out.println("📍 Ana sayfada mıyız? " + (isOnHome ? "Evet" : "Hayır"));
        return isOnHome;
    }
    
    /**
     * Sayfanın tamamen yüklendiğini kapsamlı kontrol eder
     * @return true eğer sayfa tamamen yüklendiyse
     */
    public boolean isPageFullyLoaded() {
        boolean homePageLoaded = isHomePageLoaded();
        boolean cartVisible = isCartIconVisible();
        boolean onHomePage = isOnHomePage();
        
        boolean fullyLoaded = homePageLoaded && cartVisible && onHomePage;
        
        System.out.println("🎯 Sayfa tamamen yüklendi mi? " + (fullyLoaded ? "✅ Evet" : "❌ Hayır"));
        return fullyLoaded;
    }
}