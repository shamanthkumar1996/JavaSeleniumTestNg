# Framework Quick Reference Guide

## 📚 File Structure Overview

### Base Classes (Inheritance Hierarchy)
```
BaseTest
├── LoginTests
├── SearchTests
└── ProductTests
```

### Page Objects Hierarchy
```
Base Page Operations
├── LoginPage → Amazon login functionality
├── HomePage → Amazon home page functionality
├── SearchResultsPage → Search results handling
└── ProductDetailsPage → Product details handling
```

---

## 🔑 Key Classes and Methods

### 1. DriverFactory
**Purpose**: Manages WebDriver lifecycle

```java
// Initialize driver
DriverFactory.initDriver("chrome");  // chrome, firefox, edge

// Get driver instance
WebDriver driver = DriverFactory.getDriver();

// Quit driver
DriverFactory.quitDriver();

// Check if initialized
boolean isInit = DriverFactory.isDriverInitialized();
```

### 2. WebUtils
**Purpose**: Common WebDriver operations

```java
// Wait and interact
WebUtils.click(driver, locator);
WebUtils.typeText(driver, locator, "text");
WebUtils.getText(driver, locator);

// Navigation
WebUtils.navigateToUrl(driver, "url");

// Visibility checks
WebUtils.isElementDisplayed(driver, locator);
WebUtils.isElementPresent(driver, locator);

// Advanced operations
WebUtils.scrollToElement(driver, element);
WebUtils.executeScript(driver, "script");
WebUtils.waitForPageLoad(driver);
WebUtils.getAttribute(driver, locator, "attrName");
```

### 3. CSVUtils
**Purpose**: Handle CSV test data

```java
// Read all data
List<Map<String, String>> data = CSVUtils.readCSVFile("file.csv");

// For TestNG DataProvider
Object[][] data = CSVUtils.getCSVDataForTestNG("file.csv");

// Get specific column
List<String> emails = CSVUtils.getCSVColumnData("file.csv", "email");

// Get dimensions
int rows = CSVUtils.getRowCount("file.csv");
int cols = CSVUtils.getColumnCount("file.csv");
```

### 4. ConfigReader
**Purpose**: Read configuration properties

```java
// Get properties
String url = ConfigReader.getApplicationUrl();
String browser = ConfigReader.getBrowser();
int wait = ConfigReader.getImplicitWait();
int waitExplicit = ConfigReader.getExplicitWait();
boolean screenshot = ConfigReader.takeScreenshotOnFailure();
boolean headless = ConfigReader.isHeadlessMode();
```

### 5. TestListener
**Purpose**: Monitor test execution and report

```
Automatically handles:
- onStart() → Test suite started
- onFinish() → Test suite finished
- onTestStart() → Individual test started
- onTestSuccess() → Test passed
- onTestFailure() → Test failed + screenshot
- onTestSkipped() → Test skipped
```

### 6. ScreenshotUtils
**Purpose**: Screenshot operations

```java
// Capture screenshot
ScreenshotUtils.captureScreenshot(driver, "name");

// Capture for Allure
ScreenshotUtils.captureScreenshotForAllure(driver, "name");

// On failure
ScreenshotUtils.captureScreenshotOnFailure(driver, "testName");

// Element screenshot
ScreenshotUtils.captureElementScreenshot(driver, element, "name");

// Delete screenshot
ScreenshotUtils.deleteScreenshot("path");
```

### 7. TestDataUtils
**Purpose**: Generate test data

```java
// Random data generation
String email = TestDataUtils.generateRandomEmail();
String password = TestDataUtils.generateRandomPassword();
String phone = TestDataUtils.generateRandomPhoneNumber();
String productName = TestDataUtils.generateRandomProductName();

// Random numbers and strings
int number = TestDataUtils.generateRandomNumber(1, 100);
String str = TestDataUtils.generateRandomString(10);

// Random values
double price = TestDataUtils.generateRandomPrice(100, 1000);
double rating = TestDataUtils.generateRandomRating();  // 1-5
```

---

## 🧪 Writing Tests - Common Patterns

### Pattern 1: Simple Test
```java
@Test
public void testSimple() {
    WebUtils.navigateToUrl(DriverFactory.getDriver(), "https://amazon.in");
    HomePage homePage = new HomePage(DriverFactory.getDriver());
    Assert.assertTrue(homePage.isHomePageDisplayed());
}
```

### Pattern 2: Data-Driven Test
```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return CSVUtils.getCSVDataForTestNG("src/test/resources/testdata/loginData.csv");
}

@Test(dataProvider = "loginData")
public void testLogin(Map<String, String> data) {
    LoginPage page = new LoginPage(DriverFactory.getDriver());
    page.login(data.get("email"), data.get("password"));
}
```

### Pattern 3: Multi-Step Test
```java
@Test
@Story("Search and Purchase")
public void testSearchAndProduct() {
    // Navigate
    WebUtils.navigateToUrl(DriverFactory.getDriver(), "https://amazon.in");
    
    // Search
    HomePage homePage = new HomePage(DriverFactory.getDriver());
    homePage.searchProduct("Laptop");
    
    // View results
    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());
    Assert.assertTrue(results.areSearchResultsDisplayed());
    
    // Click product
    results.clickProductByIndex(0);
    
    // Verify details
    ProductDetailsPage details = new ProductDetailsPage(DriverFactory.getDriver());
    Assert.assertTrue(details.isProductDetailsPageDisplayed());
}
```

### Pattern 4: Test with Logging
```java
@Test
public void testWithLogging() {
    logger.info("Test started");
    
    WebUtils.navigateToUrl(DriverFactory.getDriver(), "url");
    logger.info("Navigated to URL");
    
    // Test steps
    logger.info("Test completed");
}
```

---

## 📊 CSV Test Data Format

### loginData.csv
```
email,password,expectedResult,testCase
valid.user@amazon.com,ValidPassword123!,SUCCESS,Valid credentials login
invalid.user@amazon.com,WrongPassword123,FAIL,Invalid email login
```

### productData.csv
```
productName,searchTerm,expectedPrice,expectedRating,category
Laptop Dell XPS,Dell XPS,90000,4.5,Electronics
iPhone 14,iPhone 14,79999,4.7,Mobile Phones
```

---

## ⚙️ Configuration Quick Reference

### config.properties
```properties
app.url=https://www.amazon.in/
browser=chrome
implicit.wait=10
explicit.wait=15
page.load.timeout=20
screenshot.on.failure=true
headless.mode=false
```

---

## 🏃 Running Tests

### Command Line Options
```bash
# Run all tests
mvn clean test

# Run specific class
mvn test -Dtest=LoginTests

# Run specific method
mvn test -Dtest=LoginTests#testValidLogin

# Run with specific browser
mvn test -Dbrowser=firefox

# Run with skip
mvn test -DskipITs

# Run in parallel (thread-count in testng.xml)
mvn test -Dsuites=testng.xml
```

---

## 📝 Locator Strategies

### In Page Objects
```java
// Using @FindBy annotation
@FindBy(id = "ap_email")
private WebElement emailField;

@FindBy(xpath = "//button[@id='submit']")
private WebElement submitButton;

@FindBy(css = "div.product-title")
private WebElement productTitle;

// Or use By locators
private static final By EMAIL = By.id("ap_email");
private static final By SUBMIT = By.xpath("//button[@id='submit']");
```

---

## 🔄 Test Execution Flow

```
1. @BeforeMethod (BaseTest.setup)
   ├── Initialize WebDriver
   ├── Set timeouts
   └── Maximize window

2. @Test Method
   ├── Create page objects
   ├── Perform actions
   └── Assert results

3. @AfterMethod (BaseTest.tearDown)
   ├── Close WebDriver
   └── Clean up resources

4. TestListener
   ├── Log test results
   ├── Capture screenshots on failure
   └── Update Allure report
```

---

## 🐛 Debugging Tips

### Enable Debug Logging
Edit `log4j2.xml`:
```xml
<Root level="DEBUG">
    <AppenderRef ref="ConsoleAppender"/>
</Root>
```

### Take Manual Screenshots
```java
ScreenshotUtils.captureScreenshot(driver, "debug_screenshot");
```

### Print to Console
```java
logger.debug("Debug message: " + variable);
logger.info("Info message");
logger.error("Error message", exception);
```

### Check Logs
```bash
# View all logs
cat logs/selenium-test.log

# View error logs only
cat logs/error.log

# View debug logs
cat logs/debug.log
```

---

## 📦 Adding New Dependencies

Edit `pom.xml` and add under `<dependencies>`:
```xml
<dependency>
    <groupId>org.example</groupId>
    <artifactId>my-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

Then run:
```bash
mvn clean install
```

---

## ✅ Common Assertions

```java
// TestNG Assertions
Assert.assertTrue(condition);
Assert.assertFalse(condition);
Assert.assertEquals(actual, expected);
Assert.assertNotNull(object);
Assert.fail("Message");

// AssertJ (included)
assertThat(actual).isEqualTo(expected);
assertThat(list).isNotEmpty();
assertThat(string).contains("substring");
```

---

## 🎯 Best Practices Checklist

- ✅ Use Page Object Model
- ✅ Use explicit waits for dynamic elements
- ✅ Add meaningful test names
- ✅ Log important steps
- ✅ Use CSV for test data
- ✅ Capture screenshots on failure
- ✅ Add Allure annotations
- ✅ Handle exceptions properly
- ✅ Keep methods small and focused
- ✅ Use configuration for URLs and timeouts

---

## 📞 Quick Help

**Issue**: WebDriver not initialized
**Solution**: Call `DriverFactory.initDriver("chrome")` in setup

**Issue**: Element not found
**Solution**: Check locator, increase wait time, or scroll to element

**Issue**: Tests fail on CI/CD
**Solution**: Add `headless.mode=true` in config.properties

**Issue**: Screenshots not captured
**Solution**: Ensure `screenshot.on.failure=true` in config

---

**Framework Version**: 1.0.0  
**Last Updated**: January 2024
