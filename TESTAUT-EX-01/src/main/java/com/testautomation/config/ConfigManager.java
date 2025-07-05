package com.testautomation.config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

/**
 * Modern GUI ile Test Konfigürasyon Yöneticisi
 * Daha büyük boyut, basit textler ve fonksiyonel tasarım
 * Hepsiburada login testlerini yönetir ve çalıştırır
 */
public class ConfigManager extends JFrame {
    
    // Ana renkler - KOYU TEMA
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DARK_COLOR = new Color(44, 62, 80);
    private static final Color LIGHT_COLOR = new Color(236, 240, 241);
    private static final Color BACKGROUND_COLOR = new Color(45, 52, 54);
    private static final Color PANEL_COLOR = new Color(55, 66, 68);
    private static final Color TEXT_COLOR = new Color(220, 221, 222);
    private static final Color INPUT_COLOR = new Color(70, 77, 79);
    
    // GUI Bileşenleri - Konfigürasyon
    private JTextField urlField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> browserComboBox;
    private JSpinner implicitWaitSpinner;
    private JSpinner pageLoadTimeoutSpinner;
    
    // GUI Bileşenleri - Test İşlemleri
    private JButton saveButton;
    private JButton loadButton;
    private JButton defaultButton;
    private JButton runTestButton;
    private JButton stopTestButton;
    private JButton clearLogButton;
    
    // Test Sonuçları ve İlerleme
    private JTextArea testResultArea;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JScrollPane scrollPane;
    
    // Test işlemi için
    private Process testProcess;
    private Thread testThread;
    private boolean testRunning = false;
    
    // Properties dosyası yolu
    private static final String CONFIG_PATH = "src/main/resources/config.properties";
    
    /**
     * ConfigManager constructor - Modern GUI'yi başlatır
     */
    public ConfigManager() {
        initializeModernGUI();
        loadCurrentSettings();
    }
    
    /**
     * KOYU TEMA GUI bileşenlerini oluşturur ve düzenler - KOMPAKT BOYUT
     */
    private void initializeModernGUI() {
        // Ana pencere ayarları - KOMPAKT BOYUT
        setTitle("TESTAUT-EX-01 - TRENDYOL Test Otomasyon Yoneticisi - DARK");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        setBackground(BACKGROUND_COLOR);
        
        // Ana panel - Koyu tema tasarım
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Konfigürasyon paneli
        JPanel configPanel = createModernConfigPanel();
        
        // Test kontrol paneli
        JPanel testControlPanel = createTestControlPanel();
        
        // Test sonuçları paneli
        JPanel resultsPanel = createModernResultsPanel();
        
        // Status bar
        JPanel statusPanel = createStatusPanel();
        
        // Panelleri ana panele ekle
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Orta panel - Config ve Test Controls
        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(configPanel, BorderLayout.NORTH);
        centerPanel.add(testControlPanel, BorderLayout.CENTER);
        centerPanel.add(resultsPanel, BorderLayout.SOUTH);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        // Ana paneli pencereye ekle
        add(mainPanel, BorderLayout.CENTER);
        
        // Pencere boyutu ve konumu - KOMPAKT BOYUT
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Minimum boyut ayarla
        setMinimumSize(new Dimension(1000, 700));
        
        // Icon ayarla (varsa)
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        } catch (Exception e) {
            // Icon yoksa varsayılan kalır
        }
    }
    
    /**
     * KOYU TEMA header panel oluşturur - KOMPAKT
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_COLOR);
        headerPanel.setBorder(new EmptyBorder(12, 15, 12, 15));
        
        // Başlık - Kompakt boyut
        JLabel titleLabel = new JLabel(">>> TRENDYOL TEST OTOMASYON YONETICISI <<<");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_COLOR);
        
        // Alt başlık
        JLabel subtitleLabel = new JLabel("Modern selenium test otomasyonu - 2025 guncel - TRENDYOL Login testi - HIZ OPTİMİZE");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(180, 180, 180));
        
        // Başlık paneli
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(DARK_COLOR);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(Box.createVerticalStrut(3), BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    /**
     * KOYU TEMA konfigürasyon paneli oluşturur - KOMPAKT
     */
    private JPanel createModernConfigPanel() {
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.setBackground(PANEL_COLOR);
        configPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1), 
            " [AYARLAR] Test Konfigurasyonu ",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), TEXT_COLOR));
        
        // Konfigürasyon alt panelleri
        JPanel row1 = createConfigRow1();
        JPanel row2 = createConfigRow2();
        JPanel row3 = createConfigRow3();
        JPanel buttonRow = createConfigButtonRow();
        
        configPanel.add(Box.createVerticalStrut(8));
        configPanel.add(row1);
        configPanel.add(Box.createVerticalStrut(10));
        configPanel.add(row2);
        configPanel.add(Box.createVerticalStrut(10));
        configPanel.add(row3);
        configPanel.add(Box.createVerticalStrut(12));
        configPanel.add(buttonRow);
        configPanel.add(Box.createVerticalStrut(8));
        
        return configPanel;
    }
    
    /**
     * Konfigürasyon satır 1 - URL - KOMPAKT
     */
    private JPanel createConfigRow1() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        row.setBackground(PANEL_COLOR);
        
        JLabel urlLabel = createStyledLabel("[URL] Website:", 12, Font.BOLD);
        urlField = createStyledTextField(50);
        urlField.setToolTipText("Test edilecek TRENDYOL sitesinin URL'si");
        
        row.add(urlLabel);
        row.add(urlField);
        
        return row;
    }
    
    /**
     * Konfigürasyon satır 2 - Email ve Password - KOMPAKT
     */
    private JPanel createConfigRow2() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        row.setBackground(PANEL_COLOR);
        
        JLabel emailLabel = createStyledLabel("[EMAIL]:", 12, Font.BOLD);
        emailField = createStyledTextField(25);
        emailField.setToolTipText("Giris yapilacak email adresi");
        
        JLabel passwordLabel = createStyledLabel("[SIFRE]:", 12, Font.BOLD);
        passwordField = createStyledPasswordField(25);
        passwordField.setToolTipText("Giris yapilacak sifre");
        
        row.add(emailLabel);
        row.add(emailField);
        row.add(Box.createHorizontalStrut(15));
        row.add(passwordLabel);
        row.add(passwordField);
        
        return row;
    }
    
    /**
     * Konfigürasyon satır 3 - Browser ve Timeout'lar - KOMPAKT
     */
    private JPanel createConfigRow3() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        row.setBackground(PANEL_COLOR);
        
        JLabel browserLabel = createStyledLabel("[BROWSER]:", 12, Font.BOLD);
        String[] browsers = {"chrome", "firefox", "edge"};
        browserComboBox = new JComboBox<>(browsers);
        browserComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        browserComboBox.setPreferredSize(new Dimension(100, 28));
        styleComboBox(browserComboBox);
        
        JLabel waitLabel = createStyledLabel("[BEKLEME]:", 12, Font.BOLD);
        implicitWaitSpinner = new JSpinner(new SpinnerNumberModel(8, 1, 60, 1));
        styleSpinner(implicitWaitSpinner);
        
        JLabel loadLabel = createStyledLabel("[YUKLEME]:", 12, Font.BOLD);
        pageLoadTimeoutSpinner = new JSpinner(new SpinnerNumberModel(20, 5, 120, 5));
        styleSpinner(pageLoadTimeoutSpinner);
        
        // Saniye labelları da koyu tema ile
        JLabel snLabel1 = createStyledLabel("sn", 10, Font.PLAIN);
        JLabel snLabel2 = createStyledLabel("sn", 10, Font.PLAIN);
        
        row.add(browserLabel);
        row.add(browserComboBox);
        row.add(Box.createHorizontalStrut(10));
        row.add(waitLabel);
        row.add(implicitWaitSpinner);
        row.add(snLabel1);
        row.add(Box.createHorizontalStrut(10));
        row.add(loadLabel);
        row.add(pageLoadTimeoutSpinner);
        row.add(snLabel2);
        
        return row;
    }
    
    /**
     * Konfigürasyon buton satırı - KOMPAKT
     */
    private JPanel createConfigButtonRow() {
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonRow.setBackground(PANEL_COLOR);
        
        saveButton = createStyledButton("KAYDET", SUCCESS_COLOR, 100, 32);
        saveButton.setToolTipText("Ayarlari config.properties dosyasina kaydet");
        saveButton.addActionListener(new SaveButtonListener());
        
        loadButton = createStyledButton("YUKLE", PRIMARY_COLOR, 100, 32);
        loadButton.setToolTipText("Mevcut ayarlari config.properties'den yukle");
        loadButton.addActionListener(new LoadButtonListener());
        
        defaultButton = createStyledButton("VARSAYILAN", WARNING_COLOR, 120, 32);
        defaultButton.setToolTipText("Varsayilan degerleri geri yukle");
        defaultButton.addActionListener(new DefaultButtonListener());
        
        buttonRow.add(saveButton);
        buttonRow.add(loadButton);
        buttonRow.add(defaultButton);
        
        return buttonRow;
    }
    
    /**
     * Test kontrol paneli oluşturur - KOMPAKT KOYU TEMA
     */
    private JPanel createTestControlPanel() {
        JPanel testPanel = new JPanel(new BorderLayout(8, 8));
        testPanel.setBackground(PANEL_COLOR);
        testPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SUCCESS_COLOR, 1), 
            " [TEST] TRENDYOL Login Testi ",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), TEXT_COLOR));
        
        // Test butonları paneli
        JPanel testButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        testButtonPanel.setBackground(PANEL_COLOR);
        
        runTestButton = createStyledButton(">>> TRENDYOL LOGIN TESTI BASLAT <<<", SUCCESS_COLOR, 300, 40);
        runTestButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        runTestButton.setToolTipText("Chrome acilir, TRENDYOL'a gider ve login yapar");
        runTestButton.addActionListener(new RunTestListener());
        
        stopTestButton = createStyledButton("TESTI DURDUR", DANGER_COLOR, 130, 40);
        stopTestButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        stopTestButton.setToolTipText("Calisan testi durdur");
        stopTestButton.addActionListener(new StopTestListener());
        stopTestButton.setEnabled(false);
        
        testButtonPanel.add(runTestButton);
        testButtonPanel.add(stopTestButton);
        
        // İlerleme paneli
        JPanel progressPanel = createProgressPanel();
        
        testPanel.add(testButtonPanel, BorderLayout.NORTH);
        testPanel.add(progressPanel, BorderLayout.SOUTH);
        
        return testPanel;
    }
    
    /**
     * İlerleme paneli oluşturur - KOMPAKT KOYU TEMA
     */
    private JPanel createProgressPanel() {
        JPanel progressPanel = new JPanel(new BorderLayout(8, 8));
        progressPanel.setBackground(PANEL_COLOR);
        progressPanel.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        statusLabel = new JLabel("[DURUM] Test bekleniyor...");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(TEXT_COLOR);
        
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Hazir");
        progressBar.setPreferredSize(new Dimension(400, 25));
        progressBar.setForeground(PRIMARY_COLOR);
        progressBar.setBackground(INPUT_COLOR);
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        
        progressPanel.add(statusLabel, BorderLayout.NORTH);
        progressPanel.add(Box.createVerticalStrut(5), BorderLayout.CENTER);
        progressPanel.add(progressBar, BorderLayout.SOUTH);
        
        return progressPanel;
    }
    
    /**
     * KOYU TEMA test sonuçları paneli oluşturur - KOMPAKT
     */
    private JPanel createModernResultsPanel() {
        JPanel resultsPanel = new JPanel(new BorderLayout(8, 8));
        resultsPanel.setBackground(PANEL_COLOR);
        resultsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1), 
            " [SONUCLAR] Test Sonuclari ve Loglar ",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), TEXT_COLOR));
        
        // Test sonuçları text area - Kompakt dark theme
        testResultArea = new JTextArea(15, 100);
        testResultArea.setEditable(false);
        testResultArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        testResultArea.setBackground(new Color(35, 39, 41));
        testResultArea.setForeground(new Color(220, 221, 222));
        testResultArea.setLineWrap(true);
        testResultArea.setWrapStyleWord(true);
        testResultArea.setCaretColor(Color.WHITE);
        
        // Başlangıç metni
        testResultArea.setText(
            "[INFO] TRENDYOL Login Test Sonuclari burada gorunecek...\n" +
            "[ADIM] Once test bilgilerinizi girin ve 'KAYDET' butonuna basin.\n" +
            "[ADIM] Hazir oldugunuzda '>>> TRENDYOL LOGIN TESTI BASLAT <<<' butonuna basin.\n" +
            "[ISLEM] Chrome otomatik acilacak ve login islemi yapilacak.\n" +
            "[ISLEM] Pop-up'lar kapatilip 'Giris Yap' butonuna basilacak.\n" +
            "[ISLEM] Email ve sifre alanlari doldurulup giris yapilacak.\n\n" +
            "[TIP] Basit header button sistemi kullanildigi icin daha stabil calisir.\n" +
            "[TIP] Guncel TRENDYOL yapisina uygun tasarlanmistir.\n" +
            "[TIP] Tum islemler console'da detayli gorunur.\n" +
            "[HIZ] Optimizasyon: Spesifik ID'ler ve kisaltilmis sureler!\n" +
            "[TEMA] KOYU TEMA ve KOMPAKT TASARIM aktif!\n\n"
        );
        
        scrollPane = new JScrollPane(testResultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 77, 79), 1));
        
        // Temizle butonu paneli
        JPanel resultButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        resultButtonPanel.setBackground(PANEL_COLOR);
        
        clearLogButton = createStyledButton("LOGLARI TEMIZLE", WARNING_COLOR, 140, 28);
        clearLogButton.setToolTipText("Test sonuclarini temizle");
        clearLogButton.addActionListener(e -> clearTestResults());
        
        resultButtonPanel.add(clearLogButton);
        
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        resultsPanel.add(resultButtonPanel, BorderLayout.SOUTH);
        
        return resultsPanel;
    }
    
    /**
     * Status bar oluşturur - KOYU TEMA KOMPAKT
     */
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(DARK_COLOR);
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel versionLabel = new JLabel("TESTAUT-EX-01 v1.0 | Java 11+ | Selenium 4.15.0 | TestNG 7.8.0 | TRENDYOL 2025 | DARK THEME");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        versionLabel.setForeground(new Color(160, 160, 160));
        
        JLabel timeLabel = new JLabel("Hazir - " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        timeLabel.setForeground(new Color(160, 160, 160));
        
        statusPanel.add(versionLabel, BorderLayout.WEST);
        statusPanel.add(timeLabel, BorderLayout.EAST);
        
        return statusPanel;
    }
    
    // ========================================
    // HELPER METHODS - Stil Yardımcıları
    // ========================================
    
    /**
     * Styled label oluşturur - KOYU TEMA
     */
    private JLabel createStyledLabel(String text, int fontSize, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", fontStyle, fontSize));
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    /**
     * Styled text field oluşturur - KOYU TEMA KOMPAKT
     */
    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 28));
        textField.setBackground(INPUT_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return textField;
    }
    
    /**
     * Styled password field oluşturur - KOYU TEMA KOMPAKT
     */
    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passwordField.setPreferredSize(new Dimension(passwordField.getPreferredSize().width, 28));
        passwordField.setBackground(INPUT_COLOR);
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setCaretColor(TEXT_COLOR);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return passwordField;
    }
    
    /**
     * Styled button oluşturur - KOMPAKT
     */
    private JButton createStyledButton(String text, Color bgColor, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover efekti
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    /**
     * ComboBox stilini ayarlar - KOYU TEMA
     */
    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(INPUT_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));
        comboBox.setToolTipText("Test icin kullanilacak tarayici");
    }
    
    /**
     * Spinner stilini ayarlar - KOYU TEMA KOMPAKT
     */
    private void styleSpinner(JSpinner spinner) {
        spinner.setPreferredSize(new Dimension(70, 28));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Spinner'ın text field'ini koyu tema ile ayarla
        JTextField textField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBackground(INPUT_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));
    }
    
    // ========================================
    // UTILITY METHODS
    // ========================================
    
    /**
     * Test sonuçlarına metin ekler
     */
    private void appendToResults(String text) {
        SwingUtilities.invokeLater(() -> {
            testResultArea.append(text + "\n");
            testResultArea.setCaretPosition(testResultArea.getDocument().getLength());
        });
    }
    
    /**
     * İlerleme çubuğunu günceller
     */
    private void updateProgress(int value, String text) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(value);
            progressBar.setString(text);
        });
    }
    
    /**
     * Durum etiketini günceller
     */
    private void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("[DURUM] " + status);
        });
    }
    
    /**
     * Test sonuçlarını temizler
     */
    private void clearTestResults() {
        testResultArea.setText("[INFO] Test sonuclari temizlendi. Yeni teste hazir.\n\n");
        progressBar.setValue(0);
        progressBar.setString("Hazir");
        statusLabel.setText("[DURUM] Test bekleniyor...");
        appendToResults("[TEMIZLIK] Log alani temizlendi - " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
    
    /**
     * Mevcut ayarları yükler
     */
    private void loadCurrentSettings() {
        try {
            Properties prop = new Properties();
            File configFile = new File(CONFIG_PATH);
            
            if (configFile.exists()) {
                try (FileInputStream fis = new FileInputStream(configFile)) {
                    prop.load(fis);
                    
                    urlField.setText(prop.getProperty("base.url", "https://www.trendyol.com"));
                    emailField.setText(prop.getProperty("valid.email", "test@example.com"));
                    passwordField.setText(prop.getProperty("valid.password", "Test123!"));
                    browserComboBox.setSelectedItem(prop.getProperty("default.browser", "chrome"));
                    implicitWaitSpinner.setValue(Integer.parseInt(prop.getProperty("implicit.wait", "8")));
                    pageLoadTimeoutSpinner.setValue(Integer.parseInt(prop.getProperty("page.load.timeout", "20")));
                    
                    appendToResults("[BASARILI] TRENDYOL test ayarlari basariyla yuklendi!");
                }
            } else {
                loadDefaultSettings();
                appendToResults("[UYARI] Config dosyasi bulunamadi. Varsayilan degerler yuklendi.");
            }
        } catch (Exception e) {
            appendToResults("[HATA] Ayarlar yuklenirken hata: " + e.getMessage());
            loadDefaultSettings();
        }
    }
    
    /**
     * Varsayılan ayarları yükler
     */
    private void loadDefaultSettings() {
        urlField.setText("https://www.trendyol.com");
        emailField.setText("test@example.com");
        passwordField.setText("Test123!");
        browserComboBox.setSelectedItem("chrome");
        implicitWaitSpinner.setValue(8);
        pageLoadTimeoutSpinner.setValue(20);
    }
    
    /**
     * Mesaj gösterir
     */
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    // ========================================
    // EVENT LISTENERS
    // ========================================
    
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Properties prop = new Properties();
                
                prop.setProperty("base.url", urlField.getText().trim());
                prop.setProperty("valid.email", emailField.getText().trim());
                prop.setProperty("valid.password", new String(passwordField.getPassword()));
                prop.setProperty("default.browser", (String) browserComboBox.getSelectedItem());
                prop.setProperty("implicit.wait", implicitWaitSpinner.getValue().toString());
                prop.setProperty("page.load.timeout", pageLoadTimeoutSpinner.getValue().toString());
                prop.setProperty("explicit.wait", "10");
                prop.setProperty("screenshot.path", "screenshots/");
                prop.setProperty("reports.path", "reports/");
                prop.setProperty("test.data.path", "test-data/");
                
                File configFile = new File(CONFIG_PATH);
                configFile.getParentFile().mkdirs();
                
                try (FileOutputStream fos = new FileOutputStream(configFile)) {
                    prop.store(fos, "TESTAUT-EX-01 Test Konfigurasyonu - Guncelleme: " + new java.util.Date());
                }
                
                appendToResults("[BASARILI] TRENDYOL test ayarlari basariyla kaydedildi!");
                appendToResults("[DOSYA] Konum: " + CONFIG_PATH);
                showMessage("Test ayarlari basariyla kaydedildi!", "Basarili", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                String errorMsg = "[HATA] Kaydetme hatasi: " + ex.getMessage();
                appendToResults(errorMsg);
                showMessage(errorMsg, "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadCurrentSettings();
        }
    }
    
    private class DefaultButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadDefaultSettings();
            appendToResults("[GERI YUKLEME] Varsayilan degerler yuklendi!");
            showMessage("Varsayilan degerler yuklendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private class RunTestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            runTest("LoginTests#testSuccessfulLogin", "TRENDYOL Login Testi");
        }
    }
    
    private class StopTestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stopTest();
        }
    }
    
    /**
     * Test çalıştırma ana metodu
     */
    private void runTest(String testClass, String testDescription) {
        if (testRunning) {
            showMessage("Zaten bir test calisiyor! Lutfen once durdurun.", "Uyari", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Test başlangıcı
        testRunning = true;
        runTestButton.setEnabled(false);
        stopTestButton.setEnabled(true);
        
        updateStatus("Test baslatiliyor...");
        updateProgress(10, "Hazirlaniyor...");
        appendToResults("\n" + "=".repeat(80));
        appendToResults("[BASLANGIC] " + testDescription + " BASLADI");
        appendToResults("[ZAMAN] Baslama Zamani: " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        appendToResults("=".repeat(80));
        
        // Test thread'i başlat
        testThread = new Thread(() -> {
            try {
                // Maven test komutunu oluştur
                String command = "mvn test -Dtest=" + testClass;
                appendToResults("[KOMUT] Calistirilacak komut: " + command);
                
                updateProgress(20, "Maven komutu hazirlaniyor...");
                
                // Process builder ile komutu çalıştır
                ProcessBuilder pb = new ProcessBuilder();
                pb.command("cmd", "/c", command);
                pb.directory(new File(System.getProperty("user.dir")));
                pb.redirectErrorStream(true);
                
                updateProgress(30, "Browser baslatiliyor...");
                appendToResults("[BROWSER] Browser baslatiliyor ve TRENDYOL login islemi basliyor...");
                
                testProcess = pb.start();
                
                // Output'u oku
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(testProcess.getInputStream()))) {
                    String line;
                    int progress = 40;
                    
                    while ((line = reader.readLine()) != null && testRunning) {
                        final String outputLine = line;
                        
                        // Maven çıktısını filtrele ve anlamlı olanları göster
                        if (shouldDisplayLine(outputLine)) {
                            appendToResults("[MAVEN] " + outputLine);
                            
                            // İlerleme güncelle
                            if (outputLine.contains("Tests run:")) {
                                updateProgress(90, "Test tamamlaniyor...");
                            } else if (outputLine.contains("BUILD SUCCESS")) {
                                updateProgress(100, "BASARILI!");
                            } else if (outputLine.contains("BUILD FAILURE")) {
                                updateProgress(100, "BASARISIZ!");
                            } else if (progress < 80) {
                                progress += 5;
                                updateProgress(progress, "Test devam ediyor...");
                            }
                        }
                    }
                }
                
                // Test sonucu
                int exitCode = testProcess.waitFor();
                
                appendToResults("-".repeat(80));
                if (exitCode == 0) {
                    appendToResults("[BASARILI] TEST BASARIYLA TAMAMLANDI!");
                    updateStatus("Test basarili");
                    updateProgress(100, "BASARILI!");
                } else {
                    appendToResults("[BASARISIZ] TEST BASARISIZ OLDU! (Exit code: " + exitCode + ")");
                    updateStatus("Test basarisiz");
                    updateProgress(100, "BASARISIZ!");
                }
                appendToResults("[ZAMAN] Bitis Zamani: " + java.time.LocalDateTime.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                appendToResults("[DOSYALAR] Screenshot'lar screenshots/ klasorunde");
                appendToResults("-".repeat(80));
                
            } catch (Exception ex) {
                appendToResults("[HATA] Test calistirma hatasi: " + ex.getMessage());
                updateStatus("Hata olustu");
                updateProgress(0, "Hata!");
            } finally {
                // Test bitti, butonları aktif et
                SwingUtilities.invokeLater(() -> {
                    testRunning = false;
                    runTestButton.setEnabled(true);
                    stopTestButton.setEnabled(false);
                });
            }
        });
        
        testThread.start();
    }
    
    /**
     * Çalışan testi durdurur
     */
    private void stopTest() {
        if (testProcess != null) {
            testProcess.destroyForcibly();
        }
        if (testThread != null) {
            testThread.interrupt();
        }
        
        testRunning = false;
        runTestButton.setEnabled(true);
        stopTestButton.setEnabled(false);
        
        updateStatus("Test durduruldu");
        updateProgress(0, "Durduruldu");
        appendToResults("[DURDURMA] Test kullanici tarafindan durduruldu.");
    }
    
    /**
     * Maven çıktısından hangi satırların gösterileceğini belirler
     */
    private boolean shouldDisplayLine(String line) {
        if (line == null || line.trim().isEmpty()) return false;
        
        return line.contains("Running") ||
               line.contains("Tests run:") ||
               line.contains("BUILD SUCCESS") ||
               line.contains("BUILD FAILURE") ||
               line.contains("ERROR") ||
               line.contains("FAILED") ||
               line.contains("BASARILI") ||
               line.contains("BASARISIZ") ||
               line.contains("LOGIN") ||
               line.contains("Screenshot") ||
               line.contains("Browser") ||
               (line.contains("[INFO]") && (line.contains("Test") || line.contains("Browser")));
    }
    
    /**
     * Ana metod - KOYU TEMA KOMPAKT GUI'yi başlatır
     */
    public static void main(String[] args) {
        // Koyu tema Look and Feel ayarla
        try {
            // System Look and Feel kullan
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName()) || "Metal".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Varsayilan Look and Feel kullaniliyor");
            }
        }
        
        // Anti-aliasing aktif et
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        
        SwingUtilities.invokeLater(() -> {
            new ConfigManager().setVisible(true);
        });
    }
}