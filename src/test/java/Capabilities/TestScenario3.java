package Capabilities;

import org.openqa.selenium.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.*;

public class TestScenario3 {

    WebDriver driver;

    @Parameters({ "browser", "version", "platform", "url" })
    @BeforeMethod
    public void setup(String browser, String version, String platform, String url) throws MalformedURLException {
        MutableCapabilities capabilities;

        switch (browser.toLowerCase()) {
            case "chrome":
                capabilities = new ChromeOptions();
                break;
            case "firefox":
                capabilities = new FirefoxOptions();
                break;
            case "microsoftedge":
            case "edge":
                capabilities = new EdgeOptions();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // LambdaTest options
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("username", "pramqa2025");
        ltOptions.setCapability("accessKey", "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i");
        ltOptions.setCapability("project", "Selenium Grid Demo");
        ltOptions.setCapability("build", "Build 003");
        ltOptions.setCapability("name", "Test Scenario 3 - JavaScript Alert");
        ltOptions.setCapability("platformName", platform);
        ltOptions.setCapability("browserVersion", version);
        ltOptions.setCapability("selenium_version", "4.21.0");

        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), capabilities);
        driver.manage().window().maximize();
        driver.get(url);
    }

    @Test
    public void testJavaScriptAlert() throws InterruptedException {
        // Step 1: Click on "Javascript Alerts"
        driver.findElement(By.xpath("//a[normalize-space()='Javascript Alerts']")).click();

        // Step 2: Click the "Click Me" button
        WebElement alertButton = driver.findElement(By.xpath("(//button[@type='button'][normalize-space()='Click Me'])[2]"));
        alertButton.click();

        // Step 3: Switch to alert and validate the text
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        assertEquals(alertText, "Press a button!", "Press a button!");

        // Step 4: Accept the alert
        alert.accept();
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
