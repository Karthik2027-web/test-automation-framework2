# Test Automation Framework

> A professional Selenium WebDriver test automation framework built with **Java**, **TestNG**, and the **Page Object Model (POM)** design pattern — demonstrating end-to-end browser automation on Google Search and Google Images.

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://adoptium.net/)
[![Selenium](https://img.shields.io/badge/Selenium-4.15.0-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.7.0-red.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue.svg)](https://maven.apache.org/)
[![CI](https://github.com/Karthik2027-web/test-automation-framework2/actions/workflows/test.yml/badge.svg?branch=initialize-repo)](https://github.com/Karthik2027-web/test-automation-framework2/actions)

---

## Project Overview

This framework automates browser-based testing of **Google Search** and **Google Images** using Selenium WebDriver 4.
It demonstrates core QA engineering skills including:

- **Page Object Model** design for clean, maintainable test code
- **Multi-browser support** (Chrome, Firefox, Edge) via a single config property
- **Anti-bot detection handling** so Google does not block the session
- **Smart browser session management** — one browser opens for all 8 tests, no repeated launches
- **Sunset photo selection** from Google Images results with aria-label and alt-text scanning
- **CI/CD pipeline** via GitHub Actions running headless Chrome on Ubuntu

The project validates 8 test scenarios, from a simple homepage check up to selecting a specific
nature/sunset photo from Google Images and verifying its detail view.

---

## Tech Stack

| Tool / Library            | Version  | Purpose                                   |
|---------------------------|----------|-------------------------------------------|
| **Java**                  | 11       | Programming language                      |
| **Selenium WebDriver**    | 4.15.0   | Browser automation                        |
| **TestNG**                | 7.7.0    | Test framework, runner, and reporting     |
| **WebDriverManager**      | 5.6.3    | Automatic ChromeDriver / GeckoDriver mgmt |
| **Apache Log4j2**         | 2.20.0   | Structured test execution logging         |
| **Apache Commons Config** | 2.8.0    | Properties file configuration             |
| **Apache Maven**          | 3.6+     | Build tool and dependency management      |
| **GitHub Actions**        | —        | CI/CD pipeline                            |

---

## Framework Architecture

```
Design Pattern  :  Page Object Model (POM)
Test Runner     :  TestNG 7.7.0
Browser Layer   :  Selenium WebDriver 4 + WebDriverManager (auto driver setup)
Logging         :  Log4j2  →  console + logs/automation.log
Configuration   :  Java .properties file  (config.properties)
Build Tool      :  Apache Maven
CI/CD           :  GitHub Actions  (headless Chrome on Ubuntu)
```

### Layer Diagram

```
┌──────────────────────────────────────────────────┐
│              Test Layer                          │
│  SampleTest.java  — 8 @Test methods              │
│  (what to test + assertions)                     │
├──────────────────────────────────────────────────┤
│           Base Test Layer                        │
│  BaseTest.java  — browser lifecycle              │
│  @BeforeClass (open browser once)                │
│  @BeforeMethod (navigate / skip for image flow)  │
│  @AfterClass  (close browser once)               │
├──────────────────────────────────────────────────┤
│          Page Object Layer                       │
│  SamplePage.java  — how to interact with pages   │
│  (locators, actions, search, click sunset photo) │
├──────────────────────────────────────────────────┤
│           Base Page Layer                        │
│  BasePage.java  — reusable helpers               │
│  (waitForElement, clickElement, sendText, etc.)  │
├──────────────────────────────────────────────────┤
│            Utility Layer                         │
│  ConfigReader.java  — reads config.properties    │
└──────────────────────────────────────────────────┘
```

---

## Features

- **Single browser session** — `@BeforeClass` / `@AfterClass` opens and closes Chrome once for all 8 tests
- **Smart `@BeforeMethod`** — navigates to the base URL only for tests 1–5; tests 6–8 continue on the same Google Images results page (one search, three verifications)
- **Chained tests** — tests 6–8 use TestNG `dependsOnMethods` so they are skipped automatically if an earlier step fails
- **Anti-bot Chrome flags** — `--disable-blink-features=AutomationControlled`, removes the "controlled by automation" banner, hides `navigator.webdriver` via JavaScript
- **Sunset photo selection** — `clickSunsetPhoto()` scans every thumbnail's `aria-label` and `alt` text for "sunset" before falling back to the first result
- **Explicit + Implicit waits** — `WebDriverWait` (configurable) + `implicitlyWait` prevent flaky timing failures
- **Headless mode** — pass `-Dheadless=true` (or set env `CI=true`) to run without a visible browser window; used automatically in GitHub Actions
- **Centralised config** — browser, base URL, and timeouts all live in one `config.properties` file
- **Structured logging** — every page action is logged to console and `logs/` via Log4j2
- **Cross-browser** — switch between Chrome, Firefox, and Edge with one property change

---

## Test Cases

| #  | Test Method              | Description                                             | Key Assertion                          |
|----|--------------------------|---------------------------------------------------------|----------------------------------------|
| 1  | `testGoogleHomePage`     | Verify Google homepage loads                            | Search box is visible                  |
| 2  | `testPageTitle`          | Verify the browser tab title                            | Title equals "Google"                  |
| 3  | `testCurrentURL`         | Verify the browser URL                                  | URL contains "google"                  |
| 4  | `testSearchBox`          | Verify search box element is present                    | Element is displayed                   |
| 5  | `testSearchNaturePhoto`  | Navigate to Google Images and search "nature photo"     | Images results page is displayed       |
| 6  | `testCountNaturePhotos`  | Count thumbnails on the results page (no new search)    | Thumbnail count > 0                    |
| 7  | `testClickSunsetPhoto`   | Select a sunset photo from the nature results           | Photo detail / preview view opens      |
| 8  | `testVerifyPhotoDetails` | Read the detail title after selecting the photo         | Title is non-null and non-empty        |

> **Tests 6–8** are chained via `dependsOnMethods` and continue on the same results page opened by test 5.
> The search runs exactly **once**; no browser restart between tests.

---

## Folder Structure

```
test-automation-framework2/
├── .github/
│   └── workflows/
│       └── test.yml                      # GitHub Actions CI/CD pipeline
│
├── src/
│   ├── main/
│   │   └── java/com/automation/
│   │       ├── pages/
│   │       │   ├── BasePage.java         # Reusable helpers: wait, click, sendText, getTitle
│   │       │   └── SamplePage.java       # Google Search & Images page actions + locators
│   │       └── utils/
│   │           └── ConfigReader.java     # Reads config.properties
│   │
│   └── test/
│       ├── java/com/automation/tests/
│       │   ├── BaseTest.java             # Browser setup/teardown, @BeforeClass/@AfterClass
│       │   └── SampleTest.java           # 8 test cases with TestNG annotations
│       │
│       └── resources/
│           ├── config.properties         # Browser, URL, and timeout settings
│           ├── testng.xml                # TestNG suite definition
│           └── log4j2.xml                # Log4j2 logging configuration
│
├── logs/                                 # Runtime log files  (git-ignored)
├── target/                               # Maven build output  (git-ignored)
├── pom.xml                               # Maven dependencies and plugin config
├── .gitignore
└── README.md
```

---

## How to Run

### Prerequisites

| Requirement | Version | Download |
|---|---|---|
| Java JDK | 11+ | https://adoptium.net |
| Apache Maven | 3.6+ | https://maven.apache.org/download.cgi |
| Google Chrome | Latest | https://www.google.com/chrome |

> WebDriverManager automatically downloads the matching ChromeDriver — no manual setup needed.

---

### 1. Clone the repository

```bash
git clone https://github.com/Karthik2027-web/test-automation-framework2.git
cd test-automation-framework2
```

### 2. Run all 8 tests (browser window visible)

```bash
mvn test
```

### 3. Run in headless mode (no browser window)

```bash
mvn test -Dheadless=true
```

### 4. Run a specific test class

```bash
mvn test -Dtest=SampleTest
```

### 5. Run a specific test method

```bash
mvn test -Dtest=SampleTest#testSearchNaturePhoto
```

### 6. Change the browser

Edit `src/test/resources/config.properties`:

```properties
browser=chrome      # Options: chrome | firefox | edge
```

---

## Configuration

`src/test/resources/config.properties`

```properties
# Browser to use for testing — chrome | firefox | edge
browser=chrome

# Base URL navigated to before each test (tests 1-5)
base_url=https://www.google.com

# How long Selenium waits for an element to appear (seconds)
implicit_wait=10

# How long WebDriverWait polls before timing out (seconds)
explicit_wait=15
```

---

## Test Reports & Logs

After running `mvn test`, reports are available at:

| Report | Location | How to open |
|---|---|---|
| **TestNG HTML Report** | `test-output/index.html` | Open in any browser |
| **Surefire XML Results** | `target/surefire-reports/` | Used by CI tools |
| **Execution Log** | `logs/automation.log` | Open in any text editor |

**Sample console output:**

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running TestSuite

  PASS  testGoogleHomePage
  PASS  testPageTitle
  PASS  testCurrentURL
  PASS  testSearchBox
  PASS  testSearchNaturePhoto
  PASS  testCountNaturePhotos      (found 5 thumbnails)
  PASS  testClickSunsetPhoto       (title → "nature sunset photo")
  PASS  testVerifyPhotoDetails     (title verified non-empty)

Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS — Total time: ~28 seconds
```

---

## CI/CD Integration

The project ships with a **GitHub Actions** workflow at `.github/workflows/test.yml`.

### What the pipeline does

| Step | Action |
|---|---|
| Trigger | Every `push` and `pull_request` to `main` or `initialize-repo` |
| Environment | Ubuntu Latest + Java 11 (Temurin) + Google Chrome |
| Cache | Maven `~/.m2` repository cached per `pom.xml` hash |
| Test run | `mvn test -Dheadless=true` (no visible window needed) |
| Artifacts | Test results uploaded and kept for 30 days |

### View pipeline runs

```
https://github.com/Karthik2027-web/test-automation-framework2/actions
```

### Triggering a run manually

Push any change to the repository:

```bash
git add .
git commit -m "trigger CI run"
git push origin initialize-repo
```

---

## Author

**Karthik Hiremath**  
QA Engineer — Test Automation

- GitHub : [@Karthik2027-web](https://github.com/Karthik2027-web)
- Email  : Karthik.Hiremath@act.com

---

*Built with Selenium WebDriver 4 · TestNG · Java 11 · Maven · GitHub Actions*
