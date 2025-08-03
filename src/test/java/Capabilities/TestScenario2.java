package Capabilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.*;

public class TestScenario2 {

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

        // W3C-compliant LambdaTest capabilities
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("username", "pramqa2025");
        ltOptions.setCapability("accessKey", "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i");
        ltOptions.setCapability("project", "Selenium Grid Demo");
        ltOptions.setCapability("build", "Build 002");
        ltOptions.setCapability("name", "Test Scenario 2 - Checkbox Demo");
        ltOptions.setCapability("platformName", platform);
        ltOptions.setCapability("browserVersion", version);
        ltOptions.setCapability("selenium_version", "4.21.0");

        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), capabilities);
        driver.manage().window().maximize();
        driver.get(url);
    }

    @Test
    public void testSingleCheckboxSelection() throws InterruptedException {
        // Step 1: Go to “Checkbox Demo”
        driver.findElement(By.xpath("//a[normalize-space()='Checkbox Demo']")).click();

        // Step 2: Locate label and checkbox
        WebElement label = driver.findElement(By.xpath("//label[normalize-space()='Click on check box']"));
        WebElement checkbox = label.findElement(By.xpath("//label[normalize-space()='Click on check box']"));

        // Step 3: Ensure checkbox is initially unchecked
        if (checkbox.isSelected()) {
            label.click(); // uncheck
        }

        // Step 4: Click label to check
        label.click();
        Thread.sleep(500);
       // assertTrue(checkbox.isSelected(), "Checkbox should be selected after clicking label");

        // Step 5: Validate message is shown
        WebElement message = driver.findElement(By.xpath("//p[normalize-space()='Checked!']"));
        assertTrue(message.isDisplayed(), "Checked!");
        assertEquals(message.getText(), "Checked!");

        // Step 6: Click label again to uncheck
        label.click();
        Thread.sleep(500);
        assertFalse(checkbox.isSelected(), "Checkbox should be unselected after clicking label again");

        // Optional: message may disappear after uncheck
    }


    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
