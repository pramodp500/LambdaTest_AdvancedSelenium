package Capabilities;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TestScenario1 {
    private WebDriver driver;

    @Parameters({"browser", "platform", "browserVersion"})
    @BeforeMethod
    public void setUp(String browser, String platform, String browserVersion) throws Exception {
        String username = "pramqa2025";
        String accessKey = "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i";
        String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";

        MutableCapabilities options;

        if (browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("browserVersion", browserVersion);
            options = chromeOptions;
        } else if (browser.equalsIgnoreCase("Safari")) {
            SafariOptions safariOptions = new SafariOptions();
            safariOptions.setCapability("browserVersion", browserVersion);
            options = safariOptions;
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // LambdaTest-specific capabilities
        Map<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("platformName", platform);
        ltOptions.put("build", "LambdaTest Parallel Run");
        ltOptions.put("name", "Simple Form Demo Test - " + browser);
        ltOptions.put("project", "Accessibility 101");
        ltOptions.put("selenium_version", "4.0.0");

        options.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(gridURL), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void simpleFormDemoTest() {
        driver.get("https://www.lambdatest.com/selenium-playground");

        driver.findElement(By.linkText("Simple Form Demo")).click();

        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.contains("simple-form-demo") : "URL validation failed.";

        String message = "Welcome to LambdaTest";
        driver.findElement(By.id("user-message")).sendKeys(message);
        driver.findElement(By.id("showInput")).click();

        String displayed = driver.findElement(By.id("message")).getText();
        assert displayed.equals(message) : "Message mismatch. Expected: " + message + ", Found: " + displayed;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
