# 💻 Complete Installation Guide

## Step 1: Install Java JDK

### For Windows:
1. Go to: https://www.oracle.com/java/technologies/downloads/
2. Click "Download" for Windows x64
3. Accept license and download
4. Run the installer (.exe file)
5. Follow the installation wizard
6. Click "Next" → "Next" → "Install" → "Finish"

### For Mac:
1. Go to: https://www.oracle.com/java/technologies/downloads/
2. Click "Download" for macOS
3. Open the .dmg file
4. Follow the installation wizard

### For Linux (Ubuntu/Debian):
```bash
sudo apt-get update
sudo apt-get install openjdk-11-jdk
```

### Verify Installation:
```bash
java -version
```

You should see:
```
java version "11.0.x" 2021-10-19 LTS
```

---

## Step 2: Install Maven

### For Windows:
1. Go to: https://maven.apache.org/download.cgi
2. Download: `apache-maven-3.x.x-bin.zip`
3. Extract to: `C:\Program Files\Apache\maven`
4. Add to Environment Variables:
   - Open "Environment Variables"
   - Add `C:\Program Files\Apache\maven\bin` to PATH
5. Restart Command Prompt

### For Mac:
```bash
brew install maven
```

### For Linux (Ubuntu/Debian):
```bash
sudo apt-get update
sudo apt-get install maven
```

### Verify Installation:
```bash
mvn -version
```

You should see:
```
Apache Maven 3.x.x
```

---

## Step 3: Clone Your Project

### Using Git:
```bash
git clone https://github.com/Karthik2027-web/test-automation-framework2.git
cd test-automation-framework2
```

### Without Git (Manual Download):
1. Go to: https://github.com/Karthik2027-web/test-automation-framework2
2. Click "Code" → "Download ZIP"
3. Extract the ZIP file
4. Open folder: `test-automation-framework2`

---

## Step 4: Run Tests

### Automatic Method (Easiest):
**Windows:**
- Double-click: `run-tests-windows.bat`

**Mac/Linux:**
```bash
chmod +x run-tests-mac-linux.sh
./run-tests-mac-linux.sh
```

### Manual Method:
```bash
# Clean previous builds
mvn clean

# Install dependencies (first time only)
mvn install

# Run tests
mvn test
```

---

## ✅ Expected Output

```
[INFO] Scanning for projects...
[INFO] 
[INFO] --------< com.automation:test-automation-framework >--------
[INFO] Building test-automation-framework 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-surefire-plugin:2.18:test (default-test) @ test-automation-framework ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.automation.tests.SampleTest
✅ Test 1: testGoogleHomePage - PASSED
✅ Test 2: testPageTitle - PASSED
✅ Test 3: testCurrentURL - PASSED
✅ Test 4: testSearchBox - PASSED
[INFO] Tests run: 4, Failures: 0, Skipped: 0
[INFO] 
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS ✅
[INFO] -------------------------------------------------------
```

---

## 📊 View Results

1. **Test Report:** Open `test-output/index.html` in browser
2. **Logs:** Check `logs/test-automation.log`
3. **Screenshots:** Check `screenshots/` folder

---

## 🎉 You're All Set!

Your test framework is ready to run! 🚀
