# Amazon Testing Framework

A comprehensive Selenium-based testing framework for Amazon website with support for data-driven testing, Page Object Model, Allure reports, and detailed logging.

## 📋 Framework Overview

This framework is built with the following technologies and best practices:

- **Selenium WebDriver 4.16.1** - Web automation
- **TestNG 7.9.0** - Test execution and management
- **Maven** - Build and dependency management
- **Allure Reports 2.25.0** - Test reporting
- **Log4j2 2.22.1** - Logging framework
- **OpenCSV 5.9** - CSV data handling
- **Page Object Model (POM)** - Maintainable test code
- **Data-Driven Testing** - CSV-based test data

## 📁 Project Structure

```
selenium-framework/
│
├── pom.xml                           # Maven configuration with all dependencies
├── testng.xml                        # TestNG suite configuration
├── README.md                         # Project documentation
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── base/
│   │   │   │   ├── BaseTest.java                # Base class for all tests
│   │   │   │   └── DriverFactory.java          # WebDriver factory & management
│   │   │   │
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.java              # Amazon login page object
│   │   │   │   ├── HomePage.java               # Amazon home page object
│   │   │   │   ├── SearchResultsPage.java      # Search results page object
│   │   │   │   └── ProductDetailsPage.java     # Product details page object
│   │   │   │
│   │   │   └── utils/
│   │   │       ├── ConfigReader.java           # Configuration properties reader
│   │   │       ├── CSVUtils.java               # CSV file handling
│   │   │       ├── WebUtils.java               # Common WebDriver utilities
│   │   │       ├── FileUtils.java              # File operations
│   │   │       ├── TestDataUtils.java          # Test data generation
│   │   │       └── ScreenshotUtils.java        # Screenshot capture utilities
│   │   │
│   │   ├── listeners/
│   │   │   └── TestListener.java               # Test execution listener & reporting
│   │   │
│   │   └── resources/
│   │       ├── config.properties               # Application configuration
│   │       └── log4j2.xml                      # Log4j2 configuration
│   │
│   └── test/
│       ├── java/
│       │   └── tests/
│       │       ├── LoginTests.java             # Login test cases
│       │       ├── SearchTests.java            # Search test cases
│       │       └── ProductTests.java           # Product test cases
│       │
│       └── resources/
│           └── testdata/
│               ├── loginData.csv               # Login test data
│               └── productData.csv             # Product test data
│
├── logs/                                       # Test execution logs
├── screenshots/                                # Captured screenshots on failure
└── test-output/                                # Allure report results
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher (JDK)
- Maven 3.8.1 or higher
- Git
- Any modern web browser (Chrome, Firefox, Edge)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd WatsonInterview
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Configure WebDriver**
   - WebDriverManager automatically downloads compatible driver versions
   - No manual driver setup needed!

## ⚙️ Configuration

### config.properties

Update `src/main/resources/config.properties`:

```properties
# Application URL
app.url=https://www.amazon.in/

# Browser (chrome, firefox, edge)
browser=chrome

# Timeouts
implicit.wait=10
explicit.wait=15
page.load.timeout=20

# Features
screenshot.on.failure=true
headless.mode=false
```

### testng.xml

Configure test suites in `testng.xml`:

```xml
<suite name="Amazon Testing Framework Suite" parallel="tests" thread-count="1">
    <test name="Login Tests">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="tests.LoginTests"/>
        </classes>
    </test>
</suite>
```

## 🧪 Writing Tests

### Page Object Model (POM)

Example page object:

```java
public class LoginPage {
    private WebDriver driver;
    
    @FindBy(id = "ap_email")
    private WebElement emailField;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void enterEmail(String email) {
        WebUtils.typeText(driver, emailField, email);
    }
}
```

### Writing Test Cases

```java
@Feature("Login")
public class LoginTests extends BaseTest {
    
    @Test
    @Story("Valid Login")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.enterEmail("test@example.com");
        // More test steps...
    }
}
```

### Data-Driven Testing with CSV

```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return CSVUtils.getCSVDataForTestNG("src/test/resources/testdata/loginData.csv");
}

@Test(dataProvider = "loginData")
public void testWithCSVData(Map<String, String> testData) {
    String email = testData.get("email");
    String password = testData.get("password");
    // Test implementation...
}
```

## 🏃 Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test class
```bash
mvn test -Dtest=LoginTests
```

### Run specific test method
```bash
mvn test -Dtest=LoginTests#testValidLogin
```

### Run with specific browser
```bash
mvn test -Dbrowser=firefox
```

### Run tests in parallel
Update `testng.xml`:
```xml
<suite parallel="tests" thread-count="3">
```

## 📊 Allure Reports

### Generate Allure Report

```bash
mvn allure:report
```

### Open Allure Report

```bash
mvn allure:serve
```

This will open the Allure report in your default browser.

## 📝 Logging

Logs are configured using Log4j2 with the following outputs:

- **Console**: Real-time test execution logs
- **logs/selenium-test.log**: All test logs
- **logs/error.log**: Only error logs
- **logs/debug.log**: Debug level logs

Log levels can be configured in `src/main/resources/log4j2.xml`.

## 📊 Common Utilities

### WebUtils - Common WebDriver Operations

```java
// Wait and click element
WebUtils.click(driver, loginButton);

// Type text
WebUtils.typeText(driver, searchBox, "iPhone");

// Get element text
String title = WebUtils.getText(driver, productTitle);

// Check element visibility
boolean isDisplayed = WebUtils.isElementDisplayed(driver, searchBox);

// Navigate to URL
WebUtils.navigateToUrl(driver, "https://www.amazon.in/");

// Execute JavaScript
WebUtils.executeScript(driver, "window.scrollTo(0, 0);");
```

### CSVUtils - Handle CSV Test Data

```java
// Read CSV file
List<Map<String, String>> testData = CSVUtils.readCSVFile("path/to/file.csv");

// Get specific column
List<String> emails = CSVUtils.getCSVColumnData("file.csv", "email");

// Get row count
int rows = CSVUtils.getRowCount("file.csv");
```

### ScreenshotUtils - Capture Screenshots

```java
// Capture screenshot
ScreenshotUtils.captureScreenshot(driver, "screenshot_name");

// Capture on failure
ScreenshotUtils.captureScreenshotOnFailure(driver, "testName");

// Attach to Allure
ScreenshotUtils.captureScreenshotForAllure(driver, "screenshot");
```

### ConfigReader - Read Configuration

```java
// Get property
String appUrl = ConfigReader.getApplicationUrl();
String browser = ConfigReader.getBrowser();

// Get timeout
int waitTime = ConfigReader.getImplicitWait();
```

## 🏗️ Test Scenarios

### Included Test Cases

#### LoginTests.java
- Login with valid credentials
- Login with CSV data (data-driven)
- Verify login page elements
- Login with invalid credentials
- Login with empty fields

#### SearchTests.java
- Search for products
- Multiple product searches
- Verify search results page
- Click product from results
- Navigate to next page

#### ProductTests.java
- View product details
- Get product information
- Add product to cart
- Browse multiple products
- Verify product ratings

## 🔍 Test Execution Flow

1. **Setup Phase** (@BeforeMethod)
   - Initialize WebDriver
   - Maximize browser window
   - Set implicit/explicit waits

2. **Test Execution**
   - Execute test steps using Page Objects
   - Verify expected results

3. **Teardown Phase** (@AfterMethod)
   - Close WebDriver
   - Clean up resources

4. **Reporting**
   - Generate Allure report
   - Capture screenshots on failure
   - Log test execution details

## 📈 Parallel Execution

### Run tests in parallel by test class

Update `testng.xml`:
```xml
<suite parallel="classes" thread-count="3">
```

### Run tests in parallel by method

```xml
<suite parallel="methods" thread-count="3">
```

### Run tests in parallel by instance

```xml
<suite parallel="instances" thread-count="3">
```

## 🛠️ Troubleshooting

### WebDriver Issues

If WebDriverManager fails to download drivers:

```bash
# Clear WebDriverManager cache
rm -rf ~/.wdm

# Reinstall dependencies
mvn clean install
```

### Log Configuration

Adjust log levels in `log4j2.xml`:

```xml
<Root level="DEBUG">  <!-- Change DEBUG for more detail -->
    <AppenderRef ref="ConsoleAppender"/>
</Root>
```

### Browser Compatibility

Update Selenium version in `pom.xml` for newer browser versions:

```xml
<selenium.version>4.16.1</selenium.version>
```

## 📚 Best Practices

1. **Use Page Object Model** - Keep page locators in separate classes
2. **Data-Driven Testing** - Use CSV/Excel for test data
3. **Meaningful Assertions** - Use clear assertion messages
4. **Logging** - Log important test steps
5. **DRY Principle** - Create reusable utility methods
6. **Wait Strategies** - Use explicit waits for dynamic elements
7. **Test Organization** - Group related tests in test classes
8. **Reporting** - Use Allure annotations for better reports

## 🤝 Contributing

1. Follow the existing code structure
2. Add JavaDoc comments for new methods
3. Use meaningful test names
4. Update README for new features

## 📄 License

This project is licensed under the MIT License.

## 📞 Support

For issues or questions:
1. Check existing test examples
2. Review log files in `logs/` directory
3. Check Allure reports for detailed execution flow

---

**Framework Version**: 1.0.0  
**Last Updated**: January 2024
