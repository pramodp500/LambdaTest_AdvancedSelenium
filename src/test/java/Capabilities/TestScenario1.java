package Capabilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class TestScenario1 {

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
            case "internet explorer":
            case "ie":
                capabilities = new InternetExplorerOptions();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // LambdaTest W3C-compliant LT:Options
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("username", "pramqa2025");
        ltOptions.setCapability("accessKey", "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i");
        ltOptions.setCapability("project", "Selenium Grid Demo");
        ltOptions.setCapability("build", "Build 001");
        ltOptions.setCapability("name", "Test Scenario 1");
        ltOptions.setCapability("platformName", platform);
        ltOptions.setCapability("browserVersion", version);
        ltOptions.setCapability("selenium_version", "4.21.0");

        capabilities.setCapability("LT:Options", ltOptions);

        // LambdaTest Hub URL
        String gridURL = "https://hub.lambdatest.com/wd/hub";
        driver = new RemoteWebDriver(new URL(gridURL), capabilities);

        driver.manage().window().maximize();
        driver.get(url);
    }

    @Test
    public void testPageTitleWithExplicitWaitAndSoftAssert() {
        // Wait for the DOM to fully load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Soft Assertion for title (expecting failure intentionally)
        SoftAssert softAssert = new SoftAssert();
        String actualTitle = driver.getTitle();
        System.out.println("Actual Title: " + actualTitle);

        softAssert.assertEquals(actualTitle, "Selenium Grid Online | Run Selenium Test On Cloud");
        System.out.println("Continuing test after soft assertion...");

        softAssert.assertAll(); // Triggers failure at the end if assertion failed
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
