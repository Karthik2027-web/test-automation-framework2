# 🚀 QUICK START GUIDE - Run Tests Automatically

## 🎯 The Easiest Way (Recommended)

### **For Windows Users:**

1. **Download the automation script:**
   - Find file: `run-tests-windows.bat` in your project folder

2. **Double-click to run:**
   - Just double-click `run-tests-windows.bat` or run it from the project folder with PowerShell:
     ```bat
     .\run-tests-windows.bat
     ```
   - That's it! The script will do everything automatically:
     - ✅ Check Java installation
     - ✅ Check Maven installation
     - ✅ Download dependencies
     - ✅ Run all tests
     - ✅ Open test report in browser

3. **Watch the magic happen:**
   - Browser will open and tests will run automatically
   - You'll see results in the command window
   - Test report will open in your browser

---

### **For Mac/Linux Users:**

1. **Open Terminal** and navigate to your project:
   ```bash
   cd /path/to/test-automation-framework2
   ```

2. **Make script executable:**
   ```bash
   chmod +x run-tests-mac-linux.sh
   ```

3. **Run the script:**
   ```bash
   ./run-tests-mac-linux.sh
   ```

4. **Watch the magic happen:**
   - Browser will open and tests will run automatically
   - You'll see results in the terminal
   - Test report will open in your browser

---

## ⚠️ Before You Run

**Make sure you have these installed:**

### **1. Java JDK (Required)**
- Download: https://www.oracle.com/java/technologies/downloads/
- After install, verify by opening Command Prompt/Terminal:
  ```bash
  java -version
  ```
- You should see version number

### **2. Maven (Required)**
- Download: https://maven.apache.org/download.cgi
- After install, verify by opening Command Prompt/Terminal:
  ```bash
  mvn -version
  ```
- You should see version number

---

## 📊 What You'll See

### **Step 1: Checking Requirements**
```
============================================================
TEST AUTOMATION FRAMEWORK - AUTOMATED SETUP & RUN
============================================================

Checking Java installation...
[OK] Java is installed

Checking Maven installation...
[OK] Maven is installed
```

### **Step 2: Installing Dependencies**
```
============================================================
STEP 2: Installing Dependencies
============================================================
This may take 2-5 minutes on first run...

[INFO] Scanning for projects...
[INFO] BUILD SUCCESS
```

### **Step 3: Running Tests**
```
============================================================
STEP 3: Running All Tests
============================================================

Browser will open automatically. Please wait...

[INFO] Running com.automation.tests.SampleTest
✅ Test 1: testGoogleHomePage - PASSED
✅ Test 2: testPageTitle - PASSED  
✅ Test 3: testCurrentURL - PASSED
✅ Test 4: testSearchBox - PASSED

[INFO] Tests run: 4, Failures: 0
[INFO] BUILD SUCCESS
```

### **Step 4: View Results**
```
============================================================
TESTS COMPLETED!
============================================================

RESULTS:
  - Console Output: Above
  - Test Report: test-output/index.html
  - Logs: logs/test-automation.log
  - Screenshots: screenshots/ (if any test failed)

Opening test report in browser...
```

---

## 📂 Where to Find Results

After tests complete:

1. **Test Report** → `test-output/index.html`
   - Opens automatically in your browser
   - Shows all test results with pass/fail status

2. **Test Logs** → `logs/test-automation.log`
   - Detailed log of everything that happened
   - Open with any text editor

3. **Screenshots** → `screenshots/`
   - Created if any test failed
   - Shows what the page looked like when it failed

4. **Console Output** → Command Prompt/Terminal window
   - Shows live test execution
   - Final summary

---

## 🔧 Manual Commands (If You Prefer)

If you want to run commands manually:

### **Clean Previous Build:**
```bash
mvn clean
```

### **Install Dependencies:**
```bash
mvn install
```

### **Run All Tests:**
```bash
mvn test
```

### **Run Specific Test:**
```bash
mvn test -Dtest=SampleTest
```

### **Run Specific Test Method:**
```bash
mvn test -Dtest=SampleTest#testGoogleHomePage
```

---

## 🚨 Troubleshooting

### **Error: Java not recognized**
- **Solution:** Java not installed. Download from: https://www.oracle.com/java/technologies/downloads/

### **Error: Maven not recognized**
- **Solution:** Maven not installed. Download from: https://maven.apache.org/download.cgi

### **Error: Browser not opening**
- **Solution:** Normal. Browser may open in background. Check Task Manager/Activity Monitor

### **Tests timing out**
- **Solution:** Increase timeout in `src/test/resources/config.properties`

### **Tests fail but I don't know why**
- **Solution:** Check `logs/test-automation.log` for detailed error messages

---

## ✅ Success Checklist

- [ ] Java installed and verified
- [ ] Maven installed and verified
- [ ] Run the automated script (`.bat` or `.sh`)
- [ ] Wait for browser to open
- [ ] See tests running in browser
- [ ] See "BUILD SUCCESS" in command window
- [ ] Test report opens in browser
- [ ] All tests show as PASSED (green)

---

## 🎉 That's It!

Your tests are now running! You can see:
- ✅ Real browser testing
- ✅ Automated test execution
- ✅ Detailed reports
- ✅ Comprehensive logs

**Congratulations! Your Test Automation Framework is working!** 🚀

---

## 📞 Need Help?

Check the logs for details:
```bash
type logs/test-automation.log        (Windows)
cat logs/test-automation.log         (Mac/Linux)
```

Or open the test report:
```bash
test-output/index.html
```
