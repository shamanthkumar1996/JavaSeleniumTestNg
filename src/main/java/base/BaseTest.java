package base;

public class BaseTest {
     @BeforeMethod
    @Parameters("browser")
    public void setup(String browser) {
        DriverFactory.initDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
