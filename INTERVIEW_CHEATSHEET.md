# 🎯 Selenium & TestNG Interview Cheatsheet

**A comprehensive guide covering all Selenium and TestNG interview questions with framework-based examples from the Watson Interview project**

---

## 📑 Table of Contents

1. [Selenium Basics](#selenium-basics)
2. [WebDriver & Different Classes](#webdriver--different-classes)
3. [Exceptions in Selenium](#exceptions-in-selenium)
4. [Handling Multiple Windows & Tabs](#handling-multiple-windows--tabs)
5. [Handling Alerts](#handling-alerts)
6. [Wait Mechanisms](#wait-mechanisms)
7. [Page Object Model (POM)](#page-object-model-pom)
8. [Page Factory & LazyInitialization](#page-factory--lazyinitialization)
9. [TestNG Framework](#testng-framework)
10. [Logs & Screenshots](#logs--screenshots)
11. [Data-Driven Testing](#data-driven-testing)
12. [Best Practices & Common Interview Questions](#best-practices--common-interview-questions)

---

## Selenium Basics

### Q1: What is Selenium WebDriver?
**Answer:** Selenium WebDriver is a powerful open-source tool for automating web application testing across different browsers and platforms. It provides a simple programming interface for writing functional/automated tests.

**Key Features:**
- Supports multiple programming languages (Java, Python, C#, Ruby, etc.)
- Works across multiple browsers (Chrome, Firefox, Edge, Safari)
- Native interaction with browser automation
- No server required (unlike Selenium RC)

### Q2: What is the difference between Selenium 3 and Selenium 4?
**Answer:**

| Feature | Selenium 3 | Selenium 4 |
|---------|-----------|-----------|
| WebDriver | Uses standard | Fully compatible W3C protocol |
| Syntax | Old syntax | Simplified and modernized |
| Chrome DevTools Protocol | Not supported | Fully supported |
| Relative Locators | Not available | Available (NEW) |
| Service classes | Not available | Available for better driver management |
| BiDi Protocol | Not supported | Experimental support |

**Framework Usage:** Your framework uses **Selenium 4.16.1** as defined in pom.xml

### Q3: What is WebDriver Architecture?
**Answer:** WebDriver uses a client-server architecture:

```
Test Script (JSON Wire Protocol / W3C WebDriver Protocol)
    ↓
WebDriver API (Browser Driver)
    ↓
Browser
```

**Process Flow:**
1. Test script sends REST API calls to WebDriver server
2. WebDriver converts commands to specific browser protocol
3. Browser executes the command
4. Response sent back to test

---

## WebDriver & Different Classes

### Q4: What are different classes in Selenium WebDriver?

#### **1. WebDriver Interface**
The main interface for controlling web browsers.

```java
// Framework Example: From DriverFactory.java
WebDriver webDriver = null;
driver.set(webDriver);  // Using ThreadLocal for thread-safe WebDriver
```

#### **2. RemoteWebDriver**
Base class for all WebDriver implementations. Used for remote test execution.

```java
// Example
RemoteWebDriver driver = new RemoteWebDriver(serviceUrl, capabilities);
```

#### **3. ChromeDriver**
Implementation for Google Chrome browser.

```java
// Framework Example: DriverFactory.java
case "chrome":
    WebDriverManager.chromedriver().setup();
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--start-maximized");
    chromeOptions.addArguments("--disable-notifications");
    webDriver = new ChromeDriver(chromeOptions);
    logger.info("Chrome browser initialized");
    break;
```

#### **4. FirefoxDriver**
Implementation for Mozilla Firefox browser.

```java
// Framework Example: DriverFactory.java
case "firefox":
    WebDriverManager.firefoxdriver().setup();
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.addArguments("--start-maximized");
    webDriver = new FirefoxDriver(firefoxOptions);
    logger.info("Firefox browser initialized");
    break;
```

#### **5. EdgeDriver**
Implementation for Microsoft Edge browser.

```java
// Framework Example: DriverFactory.java
case "edge":
    WebDriverManager.edgedriver().setup();
    EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.addArguments("--start-maximized");
    webDriver = new EdgeDriver(edgeOptions);
    logger.info("Edge browser initialized");
    break;
```

#### **6. SafariDriver**
Implementation for Apple Safari browser (macOS only).

```java
WebDriver driver = new SafariDriver();
```

### Q5: What is ChromeOptions?

**Answer:** ChromeOptions allows you to configure Chrome driver with specific settings and capabilities.

**Common Options:**

```java
ChromeOptions options = new ChromeOptions();

// Start browser maximized
options.addArguments("--start-maximized");

// Disable notifications popup
options.addArguments("--disable-notifications");

// Disable popup blocking
options.addArguments("--disable-popup-blocking");

// Headless mode (run without GUI)
options.addArguments("--headless");

// Disable images for faster execution
options.excludeSwitches("disable-blink-features");

// Accept insecure certificates
options.setAcceptInsecureCerts(true);

// Add User agent
options.addArguments("user-agent=...");

// Disable extensions
options.addArguments("--disable-extensions");

// Disable JavaScript
options.addArguments("--disable-javascript");

// Set proxy
Proxy proxy = new Proxy();
proxy.setHttpProxy("localhost:8080");
options.setProxy(proxy);

// Add experimental feature
options.addArguments("--enable-features=VizDisplayCompositor");
```

### Q6: What is WebDriverManager?

**Answer:** WebDriverManager is a library that automatically manages WebDriver binaries for different browsers.

**Advantages:**
- No manual driver setup needed
- Automatically downloads compatible driver versions
- Works across different OS
- Supports all browsers

**Framework Usage:**
```java
// From DriverFactory.java
WebDriverManager.chromedriver().setup();
WebDriverManager.firefoxdriver().setup();
WebDriverManager.edgedriver().setup();
```

---

## Exceptions in Selenium

### Q7: What are the different types of Exceptions in Selenium?

#### **1. NoSuchElementException**
Thrown when element is not found on the page.

```java
// Example
try {
    driver.findElement(By.id("invalid_id"));
} catch (NoSuchElementException e) {
    logger.error("Element not found", e);
}

// Framework Usage: From WebUtils.java
@Step("Wait for element to be visible: {locator}")
public static WebElement waitForElement(WebDriver driver, By locator, int timeoutInSeconds) {
    try {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.info("Element found and visible: " + locator);
        return element;
    } catch (TimeoutException e) {
        logger.error("Element not found or not visible: " + locator, e);
        throw new TimeoutException("Element not found: " + locator, e);
    }
}
```

#### **2. TimeoutException**
Thrown when an element is not found within the specified time.

```java
try {
    WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));
} catch (TimeoutException e) {
    System.out.println("Element was not found within timeout");
}
```

#### **3. NoSuchWindowException**
Thrown when window or tab handle is not found.

```java
try {
    driver.switchTo().window("invalid_handle");
} catch (NoSuchWindowException e) {
    System.out.println("Window not found");
}
```

#### **4. NoSuchFrameException**
Thrown when frame is not found in the page.

```java
try {
    driver.switchTo().frame("invalid_frame");
} catch (NoSuchFrameException e) {
    System.out.println("Frame not found");
}
```

#### **5. NoAlertPresentException**
Thrown when alert is not present on the page.

```java
try {
    Alert alert = driver.switchTo().alert();
    alert.accept();
} catch (NoAlertPresentException e) {
    System.out.println("No alert present");
}
```

#### **6. StaleElementReferenceException**
Thrown when element is no longer attached to the DOM.

```java
// Problem scenario
WebElement element = driver.findElement(By.id("element"));
// Page reloads or DOM changes
element.click();  // StaleElementReferenceException

// Solution: Re-find the element
driver.navigate().refresh();
element = driver.findElement(By.id("element"));
element.click();
```

#### **7. InvalidSelectorException**
Thrown when XPath or CSS selector is invalid.

```java
try {
    driver.findElement(By.xpath("@@invalid_xpath@@"));
} catch (InvalidSelectorException e) {
    System.out.println("Invalid selector provided");
}
```

#### **8. ElementNotInteractableException**
Thrown when element exists but cannot be interacted with.

```java
try {
    // Element might be hidden or behind another element
    element.click();
} catch (ElementNotInteractableException e) {
    // Solution: Scroll to element or make it visible
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    element.click();
}
```

#### **9. SessionNotCreatedException**
Thrown when WebDriver session cannot be created.

```java
// Usually happens when:
// 1. Browser version doesn't match driver version
// 2. WebDriver binary is not accessible
// 3. Incompatible capabilities
```

#### **10. IllegalArgumentException**
Thrown when an illegal argument is passed.

```java
// Framework Example: From DriverFactory.java
default:
    logger.error("Browser: " + browser + " is not recognized");
    throw new IllegalArgumentException("Browser not supported: " + browser);
```

#### **11. WebDriverException** (Base Exception)
Base exception for all WebDriver exceptions.

```java
try {
    // WebDriver operation
} catch (WebDriverException e) {
    System.out.println("WebDriver error: " + e.getMessage());
}
```

---

## Handling Multiple Windows & Tabs

### Q8: How do you handle multiple windows in Selenium?

**Answer:** Use `switchTo().window()` method along with window handles.

```java
// Get current window handle
String parentWindowHandle = driver.getWindowHandle();

// Get all window handles
Set<String> allWindowHandles = driver.getWindowHandles();

// Switch to child window
for (String handle : allWindowHandles) {
    if (!handle.equals(parentWindowHandle)) {
        driver.switchTo().window(handle);
        break;
    }
}

// Get window title of current window
String title = driver.getTitle();

// Get URL of current window
String url = driver.getCurrentUrl();

// Switch back to parent window
driver.switchTo().window(parentWindowHandle);
```

### Q9: Write a complete program to handle multiple windows

```java
public class MultipleWindowsHandling {
    
    public static void handleMultipleWindows(WebDriver driver, String parentWindowTitle) {
        // Get all window handles
        Set<String> allWindows = driver.getWindowHandles();
        
        // Iterate through all windows
        for (String windowHandle : allWindows) {
            driver.switchTo().window(windowHandle);
            
            // Get current window title
            if (driver.getTitle().equals(parentWindowTitle)) {
                System.out.println("Switched to parent window");
                break;
            }
        }
    }
    
    public static void closeAllWindowsExceptParent(WebDriver driver, String parentWindowHandle) {
        Set<String> allWindows = driver.getWindowHandles();
        
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(parentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                driver.close();
            }
        }
        
        // Switch back to parent window
        driver.switchTo().window(parentWindowHandle);
    }
    
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        String parentHandle = driver.getWindowHandle();
        
        // Wait for new window to open
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(expectedConditionNumberOfWindowsToBe(2));
        
        // Handle windows
        handleMultipleWindows(driver, driver.getTitle());
        closeAllWindowsExceptParent(driver, parentHandle);
        
        driver.quit();
    }
}
```

---

## Handling Alerts

### Q10: How do you handle alerts in Selenium?

**Answer:** Use `switchTo().alert()` method to handle JavaScript alerts.

**Types of Alerts:**

#### **1. Simple Alert (Alert Box)**
```java
// Go to alert
Alert alert = driver.switchTo().alert();

// Get alert text
String alertText = alert.getText();

// Click OK button
alert.accept();
```

#### **2. Confirmation Alert (Confirm Box)**
```java
Alert alert = driver.switchTo().alert();

// Click OK button
alert.accept();

// Or Click Cancel button
alert.dismiss();
```

#### **3. Prompt Alert (Input Box)**
```java
Alert alert = driver.switchTo().alert();

// Type text in input box
alert.sendKeys("Your text here");

// Click OK button
alert.accept();

// Or Click Cancel button
alert.dismiss();
```

### Q11: Write a complete program to handle alerts

```java
public class AlertHandling {
    
    /**
     * Wait for alert to be present and return alert object
     */
    public static Alert waitForAlert(WebDriver driver, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            System.out.println("Alert not found within timeout");
            return null;
        }
    }
    
    /**
     * Handle simple alert
     */
    public static void handleSimpleAlert(WebDriver driver) {
        Alert alert = waitForAlert(driver, 5);
        if (alert != null) {
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
        }
    }
    
    /**
     * Handle confirmation alert
     */
    public static void handleConfirmationAlert(WebDriver driver, boolean clickOK) {
        Alert alert = waitForAlert(driver, 5);
        if (alert != null) {
            if (clickOK) {
                alert.accept();
                System.out.println("Clicked OK");
            } else {
                alert.dismiss();
                System.out.println("Clicked Cancel");
            }
        }
    }
    
    /**
     * Handle prompt alert
     */
    public static void handlePromptAlert(WebDriver driver, String textToEnter) {
        Alert alert = waitForAlert(driver, 5);
        if (alert != null) {
            alert.sendKeys(textToEnter);
            alert.accept();
            System.out.println("Entered: " + textToEnter);
        }
    }
    
    /**
     * Complete example
     */
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        
        try {
            driver.get("https://example.com");
            
            // Handle simple alert
            handleSimpleAlert(driver);
            
            // Handle confirmation alert
            handleConfirmationAlert(driver, true);  // true = OK, false = Cancel
            
            // Handle prompt alert
            handlePromptAlert(driver, "User input text");
            
        } finally {
            driver.quit();
        }
    }
}
```

---

## Wait Mechanisms

### Q12: What are different types of waits in Selenium?

**Answer:** There are 3 main types of waits:

#### **1. Implicit Wait**
Applies to all elements globally throughout the WebDriver session.

```java
// Framework Example: From DriverFactory.java
driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);

// Set implicit wait
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

// Once set, waits 10 seconds for any element before throwing NoSuchElementException
WebElement element = driver.findElement(By.id("dynamicElement"));
```

**Advantages:**
- Simple to implement
- Applies globally

**Disadvantages:**
- Can slow down tests
- Not suitable for all scenarios
- Cannot configure for specific elements

#### **2. Explicit Wait (Fluent Wait)**
Wait for specific conditions on specific elements.

```java
// Framework Example: From WebUtils.java
public static WebElement waitForElement(WebDriver driver, By locator, int timeoutInSeconds) {
    try {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.info("Element found and visible: " + locator);
        return element;
    } catch (TimeoutException e) {
        logger.error("Element not found or not visible: " + locator, e);
        throw new TimeoutException("Element not found: " + locator, e);
    }
}

// Common Wait Conditions
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Wait for element to be visible
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));

// Wait for element to be clickable
wait.until(ExpectedConditions.elementToBeClickable(By.id("button")));

// Wait for element to be present in DOM
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("element")));

// Wait for number of elements
wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("item"), 5));

// Wait for element to be selected
wait.until(ExpectedConditions.elementToBeSelected(By.id("checkbox")));

// Wait for element to have specific text
wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("element"), "Expected Text"));

// Wait for alert to be present
wait.until(ExpectedConditions.alertIsPresent());

// Wait for URL to contain specific text
wait.until(ExpectedConditions.urlContains("amazon.com"));

// Custom wait condition
wait.until(customCondition());
```

**Advantages:**
- More flexible and reliable
- Can specify timeout for specific elements
- Better control over wait conditions

**Disadvantages:**
- More code to write
- Requires proper exception handling

#### **3. Page Load Wait (Implicit)**
Additional wait for page to load completely.

```java
// Framework Example: From DriverFactory.java
webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);

// Set page load timeout
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

// Or wait for JavaScript to be ready
public static void waitForPageLoad(WebDriver driver) {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    jsExecutor.executeScript("return document.readyState").equals("complete");
}
```

### Q13: Difference between Implicit and Explicit Wait

| Feature | Implicit Wait | Explicit Wait |
|---------|--------------|---------------|
| Scope | Global to all elements | Specific to element |
| Declaration | Once per session | Multiple times |
| Condition | Only for element presence | Any custom condition |
| Timeout Behavior | Throws exception immediately | Can retry until timeout |
| Flexibility | Low | High |
| Performance | Slower (waits for all finds) | Faster (waits only when needed) |
| Best Practice | Not recommended alone | Recommended with Implicit |

---

## Page Object Model (POM)

### Q14: What is Page Object Model?

**Answer:** POM is a design pattern that creates an object repository for web elements. Each web page is represented by a class, and web elements are defined as variables in the class.

**Benefits:**
- Maintainability: Changes need to be made only in POM class
- Reusability: Methods can be reused across tests
- Readability: Test code is more readable and understandable
- Scalability: Easy to scale and maintain large test suites

### Q15: How is POM implemented in your framework?

**Framework Example: LoginPage.java**
```java
public class LoginPage {
    
    // Web elements (element locators)
    private static final By EMAIL_INPUT = By.id("ap_email");
    private static final By PASSWORD_INPUT = By.id("ap_password");
    private static final By LOGIN_BUTTON = By.id("signInSubmit");
    private static final By ERROR_MESSAGE = By.className("a-alert-content");
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    // Methods for user interactions
    public LoginPage enterEmail(String email) {
        WebUtils.waitForElement(driver, EMAIL_INPUT, 10).sendKeys(email);
        return this;  // For fluent interface
    }
    
    public LoginPage enterPassword(String password) {
        WebUtils.waitForElement(driver, PASSWORD_INPUT, 10).sendKeys(password);
        return this;
    }
    
    public HomePage login() {
        WebUtils.waitForElementToBeClickable(driver, LOGIN_BUTTON, 10).click();
        return new HomePage(driver);  // Page chain
    }
    
    public String getErrorMessage() {
        return WebUtils.getText(driver, ERROR_MESSAGE);
    }
}
```

**Test Class Using POM:**
```java
public class LoginTest extends BaseTest {
    
    private LoginPage loginPage;
    private HomePage homePage;
    
    @Test
    public void testValidLogin() {
        // Create page object
        loginPage = new LoginPage(DriverFactory.getDriver());
        
        // Use page object methods (Fluent interface)
        homePage = loginPage
            .enterEmail("test@example.com")
            .enterPassword("password123")
            .login();
        
        // Assertions
        Assert.assertTrue(homePage.isUserLoggedIn());
    }
    
    @Test
    public void testInvalidLogin() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        
        loginPage.enterEmail("invalid@test.com")
                .enterPassword("wrongPassword")
                .login();
        
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("incorrect"));
    }
}
```

---

## Page Factory & LazyInitialization

### Q16: What is Page Factory?

**Answer:** Page Factory is an extension of POM provided by Selenium that uses @FindBy annotations for element locators. It automatically initializes web elements.

```java
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageFactory {
    
    private WebDriver driver;
    
    // Using @FindBy annotation
    @FindBy(id = "ap_email")
    private WebElement emailInput;
    
    @FindBy(id = "ap_password")
    private WebElement passwordInput;
    
    @FindBy(id = "signInSubmit")
    private WebElement loginButton;
    
    // Constructor
    public LoginPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize elements
    }
    
    // Methods
    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }
    
    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }
    
    public void clickLogin() {
        loginButton.click();
    }
}
```

### Q17: What is LazyInitialization in Page Factory?

**Answer:** LazyInitialization means elements are not initialized until they are first used (lazy loading).

**Example:**
```java
@FindBy(id = "element1")
private WebElement element1;

// element1 is not initialized here
WebElement elem = element1;  // element1 is initialized here (when accessed)
```

### Q18: What are the drawbacks of Page Factory?

**Drawbacks:**

#### **1. Lazy Initialization Issues**
```java
// Problem: Element is only initialized when accessed
@FindBy(id = "element")
private WebElement element;

// This line initializes the element (not when class is instantiated)
element.click();

// If element is not found, exception is thrown only here
// This can make debugging difficult
```

#### **2. Handling Stale Element Reference**
```java
// Problem: If page refreshes or DOM changes
@FindBy(id = "element")
private WebElement element;

driver.navigate().refresh();
element.click();  // StaleElementReferenceException
// Solution: Re-initialize Page Factory
PageFactory.initElements(driver, this);
```

#### **3. Cannot Handle Dynamic Elements**
```java
// Problem: Cannot handle elements that change dynamically
@FindBy(id = "dynamicId_" + someVariable)  // DOESN'T WORK
private WebElement dynamicElement;
```

#### **4. Complex Locator Strategies**
```java
// Problem: Complex conditions are hard to implement
@FindBy(xpath = "//button[text()='Click' and @type='submit']")  // Works but complex
private WebElement complexElement;

// Conditional locators don't work well
//@FindBy(xpath = "//button[condition1 or condition2]")  // Limited support
```

#### **5. Performance Overhead**
```java
// Problem: Each @FindBy creates a proxy object
// Multiple proxy objects can cause memory issues
@FindBy(id = "element1")
private WebElement element1;

@FindBy(id = "element2")
private WebElement element2;

@FindBy(id = "element3")
private WebElement element3;
// Multiple proxy objects in memory
```

#### **6. No Explicit Waits**
```java
// Problem: Page Factory doesn't support explicit waits
@FindBy(id = "element")
private WebElement element;

// If element is not immediately available, exception is thrown
// (Unless implicit wait is set)
```

**Solution - Enhanced Page Factory with Explicit Waits:**
```java
public class LoginPageFactoryEnhanced {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(id = "ap_email")
    private WebElement emailInput;
    
    public LoginPageFactoryEnhanced(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    // Method with explicit wait
    public void enterEmail(String email) {
        // Wait for element before interacting
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
    }
}
```

### Q19: Why your framework doesn't use Page Factory?

**Your Framework Uses Traditional POM Instead of Page Factory:**

**Framework Approach (WebUtils-based):**
```java
public class LoginPage {
    
    private static final By EMAIL_INPUT = By.id("ap_email");
    
    private WebDriver driver;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void enterEmail(String email) {
        // Explicit wait is used here
        WebUtils.waitForElement(driver, EMAIL_INPUT, 10).sendKeys(email);
    }
}
```

**Advantages Over Page Factory:**

1. **Full Control:** Explicit waits are embedded in each method
2. **Better Readability:** Locators are clearly visible
3. **Flexibility:** Can add custom logic to each method
4. **No Lazy Initialization Issues:** Elements are found when method is called
5. **Better Error Handling:** Exceptions are clear and specific
6. **Performance:** No proxy object overhead

---

## TestNG Framework

### Q20: What is TestNG?

**Answer:** TestNG is a testing framework inspired by JUnit and NUnit. It provides advanced features for test organization, execution, and reporting.

**Key Features:**
- Annotations (@BeforeMethod, @AfterMethod, @Test, etc.)
- Parameterization (DataProvider)
- Grouping and Prioritization
- Dependency management
- Parallel execution
- Easy reporting

### Q21: What are important TestNG annotations?

**Answer:**

#### **Setup & Teardown Annotations**
```java
// Framework Example: From BaseTest.java

@BeforeTest
public void beforeTest() {
    // Runs before first test method
}

@BeforeMethod
@Parameters({"browser"})
public void setup(String browser) {
    // Runs before each test method
    logger.info("Starting test setup with browser: " + browser);
    DriverFactory.initDriver(browser);
}

@AfterMethod
public void tearDown() {
    // Runs after each test method
    logger.info("Starting test teardown");
    DriverFactory.quitDriver();
}

@AfterTest
public void afterTest() {
    // Runs after last test method
}

@BeforeSuite
public void beforeSuite() {
    // Runs before entire test suite
}

@AfterSuite
public void afterSuite() {
    // Runs after entire test suite
}

@BeforeGroups
public void beforeGroups() {
    // Runs before group of tests
}

@AfterGroups
public void afterGroups() {
    // Runs after group of tests
}
```

#### **Test Annotations**
```java
@Test
public void testCase1() {
    // Marks method as test case
}

@Test(enabled = false)
public void testCase2() {
    // Test is disabled and won't execute
}

@Test(priority = 1)
public void testHighPriority() {
    // Runs before priority 2
}

@Test(priority = 2)
public void testLowPriority() {
    // Runs after priority 1
}

@Test(groups = {"smoke"})
public void testSmoke() {
    // Belongs to smoke group
}

@Test(groups = {"regression"})
public void testRegression() {
    // Belongs to regression group
}

@Test(dependsOnMethods = {"testCase1"})
public void testCase3() {
    // Runs only if testCase1 passes
}

@Test(expectedExceptions = {NoSuchElementException.class})
public void testExpectedException() {
    // Test passes if exception is thrown
}

@Test(timeOut = 5000)
public void testTimeout() {
    // Test fails if takes more than 5 seconds
}

@Test(invocationCount = 3)
public void testMultipleInvocations() {
    // Test runs 3 times
}

@Test(threadPoolSize = 3, invocationCount = 6)
public void testParallel() {
    // Test runs 6 times in 3 parallel threads
}
```

### Q22: What is DataProvider in TestNG?

**Answer:** DataProvider is used for parameterized testing. It provides test data to test methods.

```java
// Framework Example: From CSVUtils.java

@DataProvider(name = "loginData")
public static Object[][] getLoginData() {
    return new Object[][] {
        { "test1@example.com", "password1" },
        { "test2@example.com", "password2" },
        { "test3@example.com", "password3" }
    };
}

@Test(dataProvider = "loginData")
public void testLogin(String email, String password) {
    LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    loginPage.enterEmail(email)
            .enterPassword(password)
            .login();
    // Assertions...
}
```

**Using CSV DataProvider:**
```java
// Framework Example: From CSVUtils.java

@DataProvider(name = "csvLoginData")
public static Object[][] getCsvLoginData() {
    return CSVUtils.getCSVDataForTestNG("testdata/loginData.csv");
}

@Test(dataProvider = "csvLoginData")
public void testLoginWithCSV(String email, String password) {
    // Test implementation
}
```

### Q23: What are TestNG groups?

**Answer:** Groups allow you to run specific tests or skip certain tests.

```java
public class TestngGroupsExample {
    
    @Test(groups = {"smoke"})
    public void testSmoke1() {
        System.out.println("Smoke Test 1");
    }
    
    @Test(groups = {"smoke"})
    public void testSmoke2() {
        System.out.println("Smoke Test 2");
    }
    
    @Test(groups = {"regression"})
    public void testRegression1() {
        System.out.println("Regression Test 1");
    }
    
    @Test(groups = {"regression", "sanity"})
    public void testBoth() {
        System.out.println("Belongs to both regression and sanity");
    }
}
```

**testng.xml Configuration:**
```xml
<!-- Run only smoke tests -->
<test name="Smoke Tests">
    <groups>
        <run>
            <include name="smoke"/>
        </run>
    </groups>
    <classes>
        <class name="tests.TestngGroupsExample"/>
    </classes>
</test>

<!-- Run smoke tests but exclude sanity -->
<test name="Exclude Sanity">
    <groups>
        <run>
            <include name="smoke"/>
            <exclude name="sanity"/>
        </run>
    </groups>
</test>
```

### Q24: What is testng.xml?

**Answer:** testng.xml is the configuration file that defines which tests to run and how to run them.

**Framework Example:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Selenium Suite" parallel="tests" thread-count="2">
    
    <!-- Parameters applicable to all tests -->
    <parameter name="browser" value="chrome"/>
    
    <!-- Smoke Test Suite -->
    <test name="Smoke Tests" preserve-order="true">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="tests.LoginTests">
                <methods>
                    <include name="testValidLogin"/>
                </methods>
            </class>
        </classes>
    </test>
    
    <!-- Regression Test Suite -->
    <test name="Regression Tests">
        <classes>
            <class name="tests.SearchTests"/>
            <class name="tests.ProductTests"/>
        </classes>
    </test>
    
    <!-- Group-based Test Suite -->
    <test name="Group Tests">
        <groups>
            <run>
                <include name="smoke"/>
                <include name="regression"/>
            </run>
        </groups>
        <packages>
            <package name="tests.*"/>
        </packages>
    </test>
    
</suite>
```

### Q25: What is the use of Listeners in TestNG?

**Answer:** Listeners listen to test execution events and perform actions based on test events.

**Framework Example: TestListener.java**
```java
public class TestListener implements ITestListener {
    
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: " + result.getMethod().getMethodName());
        
        // Capture screenshot on failure
        String screenshotPath = ScreenshotUtils.captureScreenshotOnFailure(
            DriverFactory.getDriver(),
            result.getMethod().getMethodName()
        );
        
        logger.info("Screenshot saved at: " + screenshotPath);
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: " + context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: " + context.getName());
    }
}
```

**Register Listener in testng.xml:**
```xml
<suite name="Suite">
    <listeners>
        <listener class-name="listeners.TestListener"/>
    </listeners>
    <test name="Test">
        <classes>
            <class name="tests.LoginTests"/>
        </classes>
    </test>
</suite>
```

---

## Logs & Screenshots

### Q26: How does your framework handle logging?

**Answer:** Framework uses Log4j2 for comprehensive logging.

**Log4j2 Configuration:**
```xml
<!-- src/main/resources/log4j2.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <!-- Console Appender -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        
        <!-- File Appender -->
        <File name="File" fileName="logs/test.log">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    
    <Loggers>
        <Logger name="base" level="info"/>
        <Logger name="utils" level="debug"/>
        <Logger name="tests" level="info"/>
        
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```

**Usage in Framework:**
```java
// DriverFactory.java
private static final Logger logger = LogManager.getLogger(DriverFactory.class);

public static void initDriver(String browser) {
    try {
        // ... driver initialization code
        logger.info("Chrome browser initialized");
    } catch (Exception e) {
        logger.error("Failed to initialize WebDriver for browser: " + browser, e);
    }
}

// WebUtils.java
public static WebElement waitForElement(WebDriver driver, By locator, int timeoutInSeconds) {
    try {
        // ... wait code
        logger.info("Element found and visible: " + locator);
        return element;
    } catch (TimeoutException e) {
        logger.error("Element not found or not visible: " + locator, e);
    }
}
```

### Q27: How does your framework capture screenshots?

**Answer:** Framework uses ScreenshotUtils for screenshot capture.

```java
// ScreenshotUtils.java
public class ScreenshotUtils {
    
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String path = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(srcFile, new File(path));
            
            return path;
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public static String captureScreenshotOnFailure(WebDriver driver, String testName) {
        return captureScreenshot(driver, testName + "_FAILURE");
    }
}
```

**Used in TestListener:**
```java
@Override
public void onTestFailure(ITestResult result) {
    String screenshotPath = ScreenshotUtils.captureScreenshotOnFailure(
        DriverFactory.getDriver(),
        result.getMethod().getMethodName()
    );
    logger.info("Screenshot saved at: " + screenshotPath);
}
```

---

## Data-Driven Testing

### Q28: How is data-driven testing implemented in your framework?

**Answer:** Framework uses CSV files for data-driven testing with CSVUtils.

**CSV Test Data File (testdata/loginData.csv):**
```csv
email,password
test1@example.com,password1
test2@example.com,password2
test3@example.com,password3
admin@example.com,admin123
```

**CSVUtils Implementation:**
```java
public class CSVUtils {
    
    private static final Logger logger = LogManager.getLogger(CSVUtils.class);
    
    /**
     * Read CSV file and return data as Object array for TestNG DataProvider
     */
    public static Object[][] getCSVDataForTestNG(String filePath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            List<String[]> allData = reader.readAll();
            
            if (allData.isEmpty()) {
                logger.warn("CSV file is empty: " + filePath);
                return new Object[0][0];
            }
            
            // Skip header row
            String[] headers = allData.get(0);
            int totalRows = allData.size() - 1;
            int totalCols = headers.length;
            
            Object[][] data = new Object[totalRows][totalCols];
            
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);
                for (int j = 0; j < row.length; j++) {
                    data[i - 1][j] = row[j];
                }
            }
            
            logger.info("CSV data loaded successfully from: " + filePath);
            return data;
            
        } catch (Exception e) {
            logger.error("Error reading CSV file: " + filePath, e);
            return new Object[0][0];
        }
    }
    
    /**
     * Read CSV file and return data as List of Maps
     */
    public static List<Map<String, String>> getCSVDataAsMaps(String filePath) {
        try {
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(filePath));
            return reader.readAll();
        } catch (Exception e) {
            logger.error("Error reading CSV file: " + filePath, e);
            return new ArrayList<>();
        }
    }
}
```

**Data-Driven Test Implementation:**
```java
public class DataDrivenLoginTests extends BaseTest {
    
    @DataProvider(name = "csvLoginData")
    public Object[][] getLoginData() {
        return CSVUtils.getCSVDataForTestNG("testdata/loginData.csv");
    }
    
    @Test(dataProvider = "csvLoginData")
    public void testLoginWithMultipleData(String email, String password) {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        
        loginPage.enterEmail(email)
                .enterPassword(password)
                .login();
        
        // Assertions based on email
        if (email.equals("admin@example.com")) {
            // Admin assertions
        } else {
            // User assertions
        }
    }
}
```

---

## Best Practices & Common Interview Questions

### Q29: What are best practices in Selenium automation?

**Answer:**

1. **Use Page Object Model (POM)**
   - Organize page-specific elements and methods in separate classes
   - Improves maintainability and reusability

2. **Use Explicit Waits Instead of Implicit Waits**
   ```java
   // Recommended
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));
   ```

3. **Separate Test Data from Test Logic**
   - Use CSV, Excel, JSON files for test data
   - Makes tests more maintainable

4. **Use Relative XPath Instead of Absolute XPath**
   ```java
   // Bad
   /html/body/div[1]/div[2]/div[3]/button
   
   // Good
   //button[@id='submit']
   ```

5. **Implement Listeners for Test Reporting**
   - Capture screenshots on failure
   - Log test execution events

6. **Use Logging Framework (Log4j)**
   - Helps in debugging test failures
   - Provides audit trail

7. **Parallel Test Execution**
   - Use ThreadLocal for WebDriver
   - Configure testng.xml for parallel execution

8. **Handle Exceptions Properly**
   - Catch specific exceptions
   - Provide meaningful error messages

9. **Use Configuration Files**
   - Store URLs, credentials, timeout values
   - Easy to switch between environments

10. **Clean Up Resources**
    - Always close WebDriver in tearDown
    - Clean up temporary files

### Q30: How do you handle synchronization issues?

**Answer:** Synchronization is handled using wait mechanisms.

```java
// Implicit Wait (Global)
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

// Explicit Wait (Specific)
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));

// Page Load Wait
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

// Wait for JavaScript to complete
public static void waitForJavaScript(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.until(webDriver -> ((JavascriptExecutor) driver)
        .executeScript("return document.readyState").equals("complete"));
}

// Wait for jQuery to complete
public static void waitForJquery(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.until(webDriver -> (Boolean) ((JavascriptExecutor) driver)
        .executeScript("return (typeof jQuery != 'undefined')"
            + " && (jQuery.active == 0)"));
}

// Wait for Angular to complete
public static void waitForAngular(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.until(webDriver -> (Boolean) ((JavascriptExecutor) driver)
        .executeScript("return (window.getAllAngularTestabilities()"
            + ".findIndex(x => !x.isStable()) === -1)"));
}
```

### Q31: How do you overcome StaleElementReferenceException?

**Answer:** StaleElementReferenceException occurs when element is no longer attached to DOM.

```java
// Solution 1: Re-find the element
WebElement element = driver.findElement(By.id("element"));
driver.navigate().refresh();
element = driver.findElement(By.id("element"));  // Re-find
element.click();

// Solution 2: Use explicit wait
By locator = By.id("element");
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();

// Solution 3: Retry mechanism
public static void clickWithRetry(WebDriver driver, By locator, int retries) {
    for (int i = 0; i < retries; i++) {
        try {
            driver.findElement(locator).click();
            break;
        } catch (StaleElementReferenceException e) {
            if (i == retries - 1) {
                throw e;
            }
        }
    }
}

// Solution 4: Use ExpectedConditions with presenceOfAllElementsLocatedBy
wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

// Solution 5: Avoid storing element references
// Bad
WebElement element = driver.findElement(By.id("element"));
// Do other things
element.click();  // Might be stale

// Good
driver.findElement(By.id("element")).click();
```

### Q32: How do you handle dynamic elements?

**Answer:** Dynamic elements are elements that appear/disappear or change IDs dynamically.

```java
// Solution 1: Use flexible XPath
// Bad - depends on exact position
//div[@id='parent']/div[2]/button

// Good - uses attributes to identify
//button[@class='submit-btn' and @type='submit']

// Solution 2: Use CSS Selectors
// XPath: //input[@placeholder='Enter name']
// CSS: input[placeholder='Enter name']

// Solution 3: Use partial attribute matching
// XPath: //button[contains(@id, 'submit')]
// CSS: button[id*='submit']

// Solution 4: Wait for element with explicit wait
public static WebElement waitForDynamicElement(WebDriver driver, By locator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
}

// Solution 5: Use JavaScript to find elements
public static WebElement findElementByJs(WebDriver driver, String script) {
    return (WebElement) ((JavascriptExecutor) driver).executeScript(script);
}

// Usage
String js = "return document.querySelector('[data-id=\"123\"]')";
WebElement element = findElementByJs(driver, js);
```

### Q33: How do you handle file downloads?

**Answer:**

```java
// Solution 1: Using WebDriver options
ChromeOptions options = new ChromeOptions();
HashMap<String, Object> chromePrefs = new HashMap<>();
chromePrefs.put("download.default_directory", "/path/to/download");
chromePrefs.put("download.prompt_for_download", false);
options.setExperimentalOption("prefs", chromePrefs);

WebDriver driver = new ChromeDriver(options);

// Solution 2: Verify file download
public static boolean isFileDownloaded(String downloadPath, String fileName, int timeoutInSeconds) {
    long endTime = System.currentTimeMillis() + (timeoutInSeconds * 1000);
    
    while (System.currentTimeMillis() < endTime) {
        File file = new File(downloadPath + "\\" + fileName);
        if (file.exists()) {
            return true;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    return false;
}

// Solution 3: Get latest downloaded file
public static File getLatestDownloadedFile(String downloadPath) {
    File directory = new File(downloadPath);
    File[] files = directory.listFiles();
    
    long lastModified = Long.MIN_VALUE;
    File lastModifiedFile = null;
    
    for (File file : files) {
        if (file.lastModified() > lastModified) {
            lastModified = file.lastModified();
            lastModifiedFile = file;
        }
    }
    
    return lastModifiedFile;
}
```

### Q34: How do you handle SSL certificate errors?

**Answer:**

```java
// Method 1: Using ChromeOptions
ChromeOptions options = new ChromeOptions();
options.setAcceptInsecureCerts(true);
WebDriver driver = new ChromeDriver(options);

// Method 2: Using FirefoxOptions
FirefoxOptions firefoxOptions = new FirefoxOptions();
firefoxOptions.setAcceptInsecureCerts(true);
WebDriver driver = new FirefoxDriver(firefoxOptions);

// Method 3: Using Capabilities
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setAcceptInsecureCerts(true);
WebDriver driver = new ChromeDriver(capabilities);

// Framework Example: From DriverFactory.java
ChromeOptions chromeOptions = new ChromeOptions();
chromeOptions.setAcceptInsecureCerts(true);  // Accept insecure certificates
webDriver = new ChromeDriver(chromeOptions);
```

### Q35: How do you handle browser cookies?

**Answer:**

```java
// Add cookie
Cookie cookie = new Cookie("cookieName", "cookieValue");
driver.manage().addCookie(cookie);

// Get cookie
Cookie myCookie = driver.manage().getCookieNamed("cookieName");
System.out.println("Cookie Value: " + myCookie.getValue());

// Get all cookies
Set<Cookie> cookies = driver.manage().getCookies();
for (Cookie cookie : cookies) {
    System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue());
}

// Delete specific cookie
driver.manage().deleteCookie("cookieName");

// Delete all cookies
driver.manage().deleteAllCookies();

// Save and restore cookies
public static void saveCookies(WebDriver driver, String filePath) {
    try {
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
        out.writeObject(cookies);
        out.close();
    } catch (Exception e) {
        System.out.println("Error saving cookies: " + e.getMessage());
    }
}

@SuppressWarnings("unchecked")
public static void loadCookies(WebDriver driver, String filePath) {
    try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
        Set<Cookie> cookies = (Set<Cookie>) in.readObject();
        in.close();
        
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    } catch (Exception e) {
        System.out.println("Error loading cookies: " + e.getMessage());
    }
}
```

### Q36: How do you interact with hidden elements?

**Answer:**

```java
// Solution 1: Make element visible using JavaScript
public static void clickHiddenElement(WebDriver driver, By locator) {
    WebElement element = driver.findElement(locator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='visible';", element);
    element.click();
}

// Solution 2: Send keys to hidden element
public static void typeInHiddenElement(WebDriver driver, By locator, String text) {
    WebElement element = driver.findElement(locator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('type','text');", element);
    element.sendKeys(text);
}

// Solution 3: Execute JavaScript click
public static void jsClick(WebDriver driver, By locator) {
    WebElement element = driver.findElement(locator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
}

// Solution 4: Scroll to element
public static void scrollToElement(WebDriver driver, By locator) {
    WebElement element = driver.findElement(locator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
}
```

### Q37: How do you perform actions like drag and drop?

**Answer:**

```java
import org.openqa.selenium.interactions.Actions;

// Drag and Drop
public static void dragAndDrop(WebDriver driver, By sourceLocator, By targetLocator) {
    WebElement source = driver.findElement(sourceLocator);
    WebElement target = driver.findElement(targetLocator);
    
    Actions actions = new Actions(driver);
    actions.dragAndDrop(source, target).perform();
}

// Hover Over Element
public static void hover(WebDriver driver, By locator) {
    WebElement element = driver.findElement(locator);
    Actions actions = new Actions(driver);
    actions.moveToElement(element).perform();
}

// Double Click
public static void doubleClick(WebDriver driver, By locator) {
    WebElement element = driver.findElement(locator);
    Actions actions = new Actions(driver);
    actions.doubleClick(element).perform();
}

// Right Click (Context Menu)
public static void rightClick(WebDriver driver, By locator) {
    WebElement element = driver.findElement(locator);
    Actions actions = new Actions(driver);
    actions.contextClick(element).perform();
}

// Click and Hold
public static void clickAndHold(WebDriver driver, By locator) {
    WebElement element = driver.findElement(locator);
    Actions actions = new Actions(driver);
    actions.clickAndHold(element).perform();
}

// Keyboard Actions
public static void keyboardAction(WebDriver driver) {
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ENTER).perform();
    actions.sendKeys(Keys.TAB).perform();
    actions.sendKeys(Keys.ESCAPE).perform();
    actions.keyDown(Keys.SHIFT).sendKeys("a").keyUp(Keys.SHIFT).perform();
}
```

### Q38: What is the difference between findElement and findElements?

**Answer:**

| Feature | findElement | findElements |
|---------|-----------|-------------|
| Return Type | Single WebElement | List of WebElements |
| Exception | Throws NoSuchElementException | Returns empty list |
| Count | Returns first matching element | Returns all matching elements |
| Null Check | N/A | Need to check if list is empty |

```java
// findElement
try {
    WebElement element = driver.findElement(By.id("element"));
    element.click();
} catch (NoSuchElementException e) {
    System.out.println("Element not found");
}

// findElements
List<WebElement> elements = driver.findElements(By.className("product"));
if (elements.isEmpty()) {
    System.out.println("No elements found");
} else {
    System.out.println("Found " + elements.size() + " elements");
    for (WebElement element : elements) {
        System.out.println(element.getText());
    }
}
```

### Q39: How do you work with frames in Selenium?

**Answer:**

```java
// Switch to frame by index
driver.switchTo().frame(0);  // First frame
driver.switchTo().frame(1);  // Second frame

// Switch to frame by name or ID
driver.switchTo().frame("frameName");
driver.switchTo().frame("frameId");

// Switch to frame by WebElement
WebElement frameElement = driver.findElement(By.id("frame"));
driver.switchTo().frame(frameElement);

// Switch to default content (parent)
driver.switchTo().defaultContent();

// Switch to parent frame
driver.switchTo().parentFrame();

// Wait for frame and then switch
public static void waitForFrameAndSwitch(WebDriver driver, By frameLocator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement frame = wait.until(ExpectedConditions.presenceOfElementLocated(frameLocator));
    driver.switchTo().frame(frame);
}

// Complete example
public static void handleIframeContent(WebDriver driver) {
    // Switch to iframe
    driver.switchTo().frame("myFrame");
    
    // Find element inside iframe
    WebElement element = driver.findElement(By.id("elementInsideIframe"));
    element.click();
    
    // Switch back to main content
    driver.switchTo().defaultContent();
    
    // Now interact with main page elements
    driver.findElement(By.id("mainPageElement")).click();
}
```

### Q40: What is the difference between close() and quit()?

**Answer:**

| Feature | close() | quit() |
|---------|---------|--------|
| Closes | Current window/tab | Entire browser instance |
| Sessions | WebDriver session remains | Closes WebDriver session |
| Use Case | Multiple windows | End of test |
| Exception | Throws exception if no window | N/A |

```java
// close() - closes current window
driver.close();  // Closes current browser window
// If there are other windows, WebDriver still controls them

// quit() - closes all windows
driver.quit();  // Closes all windows and ends WebDriver session
// WebDriver session is terminated

// Framework Example: From DriverFactory.java and BaseTest.java
@AfterMethod
public void tearDown() {
    logger.info("Starting test teardown");
    DriverFactory.quitDriver();  // Using quit() to close all windows
    logger.info("Test teardown completed");
}

public static void quitDriver() {
    if (driver.get() != null) {
        driver.get().quit();  // Closes browser
        driver.remove();  // Removes from ThreadLocal
        logger.info("WebDriver instance closed");
    }
}
```

### Q41: How do you test responsive design?

**Answer:**

```java
// Set different screen sizes
driver.manage().window().setSize(new Dimension(1920, 1080));  // Desktop
driver.manage().window().setSize(new Dimension(768, 1024));   // Tablet
driver.manage().window().setSize(new Dimension(375, 667));    // Mobile

// Or use maximize/minimize
driver.manage().window().maximize();
driver.manage().window().minimize();

// Get current window size
Dimension size = driver.manage().window().getSize();
System.out.println("Width: " + size.getWidth() + ", Height: " + size.getHeight());

// Complete responsive testing
public static void testResponsiveDesign(WebDriver driver) {
    int[][] screenSizes = {
        {1920, 1080},  // Desktop
        {768, 1024},   // Tablet
        {375, 667}     // Mobile
    };
    
    for (int[] size : screenSizes) {
        driver.manage().window().setSize(new Dimension(size[0], size[1]));
        System.out.println("Testing at: " + size[0] + "x" + size[1]);
        
        // Run your assertions for each screen size
        WebElement element = driver.findElement(By.id("element"));
        Assert.assertTrue(element.isDisplayed());
    }
}
```

---

## Summary Table: Common Selenium Operations

| Operation | Code Example | Use Case |
|-----------|-------------|----------|
| Click Element | `driver.findElement(By.id("btn")).click();` | Click buttons, links |
| Enter Text | `element.sendKeys("text");` | Fill input fields |
| Get Text | `String text = element.getText();` | Verify displayed text |
| Wait for Element | `wait.until(ExpectedConditions.visibilityOf(element));` | Synchronization |
| Switch to Alert | `driver.switchTo().alert().accept();` | Handle alerts |
| Switch to Window | `driver.switchTo().window(handle);` | Multiple windows |
| Switch to Frame | `driver.switchTo().frame("frameName");` | Handle frames |
| Drag and Drop | `new Actions(driver).dragAndDrop(source, target).perform();` | Drag operations |
| Execute JavaScript | `((JavascriptExecutor) driver).executeScript("script");` | Complex interactions |
| Take Screenshot | `((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);` | Capture state |

---

## Interview Tips

1. **Show Framework Knowledge:** Reference your actual framework implementation
2. **Explain Best Practices:** Discuss why you chose certain approaches
3. **Demonstrate Problem-Solving:** Explain how you overcome common issues
4. **Mention Tools:** Log4j, Allure Reports, WebDriverManager, etc.
5. **Discuss Performance:** Parallel execution, efficient waits
6. **Talk About Maintenance:** How your framework is scalable and maintainable
7. **Code Quality:** How you handle exceptions, logging, and error messages
8. **Testing Strategies:** Data-driven testing, different test types
9. **CI/CD Integration:** How tests are executed in pipeline
10. **Metrics & Reporting:** Allure reports, test coverage

---

**Good Luck with Your Interview! 🚀**

*This cheatsheet covers all major Selenium and TestNG concepts with practical examples from the Watson Interview framework.*
