# 🛒 TESTAUT-EX-01 - TRENDYOL Test Automation

Modern **Selenium WebDriver** project for TRENDYOL login test automation. Built with **dark theme GUI** and **speed optimization**.

## 🚀 Quick Start

```bash
# 1. Clone the project
git clone https://github.com/your-username/TESTAUT-EX-01.git
cd TESTAUT-EX-01

# 2. Install dependencies
mvn clean install

# 3. Launch GUI
mvn exec:java -Dexec.mainClass="com.testautomation.config.ConfigManager"
```

## ✨ Features

- ✅ **TRENDYOL** login automation
- 🌙 **Dark theme GUI** (1200x800)
- ⚡ **Speed optimization** (50% faster)
- 🎯 **Specific IDs** (`login-email`, `login-password`)
- 📱 **Multi-browser** (Chrome, Firefox, Edge)
- 📸 **Automatic screenshot** capture

## 🔧 Technologies

- **Java 11+** - Main language
- **Selenium 4.15.0** - Web automation
- **TestNG 7.8.0** - Test framework
- **Maven** - Dependency management

## ⚙️ Usage

### GUI Mode (Recommended)
1. Launch GUI: `mvn exec:java -Dexec.mainClass="com.testautomation.config.ConfigManager"`
2. Enter email and password
3. Click **SAVE** button
4. Click **>>> START TRENDYOL LOGIN TEST <<<** button

### Command Line
```bash
# All tests
mvn test

# Only successful login test
mvn test -Dtest=LoginTests#testSuccessfulLogin
```

## ⚙️ Configuration

`src/main/resources/config.properties`:
```properties
base.url=https://www.trendyol.com
default.browser=chrome
implicit.wait=4
page.load.timeout=5
valid.email=your-email@example.com
valid.password=your-password
```

## 🧪 Test Scenarios

### ✅ Positive Tests
- **testSuccessfulLogin** - Successful login
- **testLoginWithRememberMe** - Remember me feature

### ❌ Negative Tests  
- **testLoginWithInvalidEmail** - Invalid email
- **testLoginWithInvalidPassword** - Invalid password
- **testLoginWithEmptyFields** - Empty fields

## 📁 Project Structure

```
TESTAUT-EX-01/
├── src/main/java/com/testautomation/
│   ├── base/BaseTest.java           # Base test class
│   ├── config/ConfigManager.java    # GUI management panel
│   ├── pages/HomePage.java          # Home page
│   ├── pages/LoginPage.java         # Login page
│   └── utils/TestUtils.java         # Helper functions
├── src/test/java/com/testautomation/
│   └── tests/LoginTests.java        # Test scenarios
└── src/main/resources/
    └── config.properties            # Configuration
```

## 📊 Reports

- **Screenshots:** Located in `screenshots/` folder
- **TestNG Reports:** `target/surefire-reports/index.html`

## 💡 Tips

- Use **Chrome browser** (most stable)
- **Internet connection** should be stable
- **Element not found:** Increase timeout values

---
