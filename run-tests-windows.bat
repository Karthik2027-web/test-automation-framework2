@echo off
setlocal
REM ============================================================
REM AUTOMATED TEST RUNNER FOR WINDOWS
REM This script will automatically download and run all tests
REM ============================================================

echo.
echo ============================================================
echo TEST AUTOMATION FRAMEWORK - AUTOMATED SETUP & RUN
echo ============================================================
echo.

if not exist logs mkdir logs

REM Check if Java is installed
echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Java is NOT installed!
    echo Please download and install Java JDK from:
    echo https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)
echo [OK] Java is installed
echo.

REM Check if Maven is installed
echo Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Maven is NOT installed!
    echo Please download and install Maven from:
    echo https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)
echo [OK] Maven is installed
echo.

REM Show project information
echo ============================================================
echo STEP 1: Cleaning Previous Builds
echo ============================================================
echo.
call mvn clean
if %errorlevel% neq 0 (
    echo ERROR: Maven clean failed!
    pause
    exit /b 1
)
echo.

echo ============================================================
echo STEP 2: Installing Dependencies
echo ============================================================
echo This may take 2-5 minutes on first run...
echo.
call mvn install -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Dependency installation failed!
    pause
    exit /b 1
)
echo.

echo ============================================================
echo STEP 3: Running All Tests
echo ============================================================
echo.
echo Browser will open automatically. Please wait...
echo.
call mvn test
if %errorlevel% neq 0 (
    echo.
    echo Some tests may have failed. Check the report.
)
echo.

echo ============================================================
echo TESTS COMPLETED!
echo ============================================================
echo.
echo RESULTS:
echo   - Console Output: Above
echo   - Test Report: test-output/index.html
echo   - Logs: logs/test-automation.log
echo   - Screenshots: screenshots/ (if any test failed)
echo.
echo Opening test report in browser...
echo.

if exist test-output\index.html (
    start "" test-output\index.html
) else (
    echo Test report not found. Please inspect the console output.
)
exit /b 0

echo.
echo ============================================================
echo ALL DONE! Press any key to close this window.
echo ============================================================
pause
