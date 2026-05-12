#!/bin/bash

# ============================================================
# AUTOMATED TEST RUNNER FOR MAC/LINUX
# This script will automatically download and run all tests
# ============================================================

echo ""
echo "============================================================"
echo "TEST AUTOMATION FRAMEWORK - AUTOMATED SETUP & RUN"
echo "============================================================"
echo ""

# Check if Java is installed
echo "Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo ""
    echo "ERROR: Java is NOT installed!"
    echo "Please download and install Java JDK from:"
    echo "https://www.oracle.com/java/technologies/downloads/"
    echo ""
    exit 1
fi
echo "[OK] Java is installed"
echo ""

# Check if Maven is installed
echo "Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo ""
    echo "ERROR: Maven is NOT installed!"
    echo "Please download and install Maven from:"
    echo "https://maven.apache.org/download.cgi"
    echo ""
    exit 1
fi
echo "[OK] Maven is installed"
echo ""

# Step 1: Clean
echo "============================================================"
echo "STEP 1: Cleaning Previous Builds"
echo "============================================================"
echo ""
mvn clean
if [ $? -ne 0 ]; then
    echo "ERROR: Maven clean failed!"
    exit 1
fi
echo ""

# Step 2: Install Dependencies
echo "============================================================"
echo "STEP 2: Installing Dependencies"
echo "============================================================"
echo "This may take 2-5 minutes on first run..."
echo ""
mvn install -DskipTests
if [ $? -ne 0 ]; then
    echo "ERROR: Dependency installation failed!"
    exit 1
fi
echo ""

# Step 3: Run Tests
echo "============================================================"
echo "STEP 3: Running All Tests"
echo "============================================================"
echo ""
echo "Browser will open automatically. Please wait..."
echo ""
mvn test
if [ $? -ne 0 ]; then
    echo ""
    echo "Some tests may have failed. Check the report."
fi
echo ""

echo "============================================================"
echo "TESTS COMPLETED!"
echo "============================================================"
echo ""
echo "RESULTS:"
echo "  - Console Output: Above"
echo "  - Test Report: test-output/index.html"
echo "  - Logs: logs/test-automation.log"
echo "  - Screenshots: screenshots/ (if any test failed)"
echo ""
echo "Opening test report in browser..."
echo ""

if [ -f "test-output/index.html" ]; then
    if [[ "$OSTYPE" == "darwin"* ]]; then
        open test-output/index.html
    else
        xdg-open test-output/index.html
    fi
else
    echo "Test report not found."
fi

echo ""
echo "============================================================"
echo "ALL DONE!"
echo "============================================================"
echo ""
