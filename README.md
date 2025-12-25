1) Selenium Automation Framework Assignment

This automation framework is built using *Selenium 4* and *Maven*, and follows the *POM* design pattern. It is designed to be maintainable, and supports cross-browser testing, reporting, logging, parallel execution, and data-driven testing.


```
**Note : Out of all the Test cases, one test cases is intentionally failed by providing Invalid assertion in code validate the screenshot functionality for failed test cases
2. Sometimes the website doesn't respond properly, which can cause the test scripts to fail even if they’re working perfectly. This usually happens because the site is just a demo and might be slow or unstable at times. The issue is with the website, not the automation scripts.**
```

2) Project Structure

```
src/
├── main/java/com/nagp/selenium/assignment/
│   ├── base         # Base classes for pages and tests
│   ├── config       # Configuration management (properties)
│   ├── excel        # Excel utilities for test data
│   ├── pages        # Page object classes
│   ├── reporting    # ExtentReport utilities
│   └── utils        # Common helper functions
├── test/java/
│   └── tests        # Test Cases 
├── main/resources/
│   ├── config.properties  # Framework configurations
│   └── log4j2.xml         # log4j2 xml file
├── test/resources/
│   ├── testng-groups.xml  # testng xml file
│   ├── testng-parallel.xml# testng xml file
│   └── testdata.xlsx      # Input data for tests
Current_Test_Results/      # Latest Extent reports, logs and screenshots
```

3) Getting Started

1. Ensure the following are installed:
   - **Java JDK 11 or higher**
   - **Eclipse IDE**
   - **Apache Maven**

2. Import the project into Eclipse:
   - Copy and unzip the project from the shared location.
   - Open Eclipse → File → Import → Existing Maven Project.

4) Running Tests

You can run tests via **Eclipse** or the **Command Line**.

	Option 1: Run from Eclipse
1. Right-click on the project → **Run As** → **Maven clean**
2. After build success → Right-click again → **Run As** → **Maven test**

	Option 2: Run from Command Line
1. Open Command Prompt
2. Navigate to the project directory
3. Use one of the following commands:

For smoke tests:

```
mvn clean test -DsuiteXmlFile=src/test/resources/testng-groups.xml -Dgroups=smoke
```
For full regression:

```
mvn clean test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```

5) Reports, Screenshots & Logs

After test execution:

1. HTML Test Report (Extent Report) is generated inside the `Current_Test_Results/` folder.
2. Screenshots (for failed tests) are saved in `Current_Test_Results/Screenshots/`.
3. Log files (Overall application logs) are stored in `Current_Test_Results/logs/`.
4. Configuration Management

To modify test configurations:
- Open the `config.properties` file located at: src/main/resources/config.properties
- Configurable properties include:
  - `browserName=chrome` (Supported values: `chrome`, `firefox`, `edge`)

5. Updating Test Data : To modify input data, Edit the Excel file located at below location.This file is used for data-driven tests via Apache POI integration.

```
src/test/resources/testdata.xlsx
```

