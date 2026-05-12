# Test Automation Framework

A robust Selenium WebDriver automation framework built with **Java**, **TestNG**, and **Page Object Model (POM)** pattern.

## 🎯 Features

- ✅ **Page Object Model** - Organized and maintainable test structure
- ✅ **TestNG** - Powerful test execution and reporting
- ✅ **Selenium WebDriver** - Cross-browser testing support (Chrome, Firefox, Edge)
- ✅ **WebDriverManager** - Automatic driver management
- ✅ **Apache Log4j** - Comprehensive logging
- ✅ **Base Classes** - Reusable test and page components
- ✅ **Configuration Management** - Centralized settings via properties file

## 📋 Prerequisites

- **Java JDK 11** or higher
- **Maven 3.6** or higher
- **Git**

## 🚀 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Karthik2027-web/test-automation-framework2.git
   cd test-automation-framework2
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run all tests**
   - Option 1: Use the Windows helper script
     ```bat
     .\run-tests-windows.bat
     ```
   - Option 2: Run directly with Maven
     ```bash
     mvn test
     ```

4. **Run specific test class**
   ```bash
   mvn test -Dtest=SampleTest
   ```

## 📁 Project Structure

```
test-automation-framework2/
├── src/
│   ├── main/java/com/automation/
│   │   ├── pages/              # Page Object classes
│   │   │   ├── BasePage.java
│   │   │   └── SamplePage.java
│   │   └── utils/              # Utility classes
│   │       └── ConfigReader.java
│   ├── test/java/com/automation/tests/
│   │   ├── BaseTest.java       # Base test configuration
│   │   └── SampleTest.java     # Example test
│   └── test/resources/
│       ├── config.properties   # Configuration file
│       └── testng.xml          # TestNG configuration
├── pom.xml                     # Maven dependencies
├── logs/                       # Test execution logs
├── screenshots/                # Screenshot storage
└── README.md                   # This file
```

## 🔧 Configuration

Edit **config.properties** to change settings:
```properties
browser=chrome
base_url=https://www.google.com
implicit_wait=10
explicit_wait=15
```

## 📝 Writing Tests

1. Create Page Object in `src/main/java/com/automation/pages/`
2. Create Test in `src/test/java/com/automation/tests/`
3. Extend `BaseTest` class
4. Write your test methods

## 📊 Test Reports

- **TestNG Report**: `test-output/index.html`
- **Logs**: `logs/` directory
- **Screenshots**: `screenshots/` directory

## 🎉 Ready to Test!

Happy Testing! 🚀
