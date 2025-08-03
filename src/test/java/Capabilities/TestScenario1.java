package Capabilities;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import static org.testng.Assert.*;

public class TestScenario1 {
    private WebDriver driver;
    private String expectedUrl = "https://www.lambdatest.com/integrations";
    private String LT_USERNAME = "pramqa2025";
    private String LT_ACCESS_KEY = "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i";
    
    @Parameters({"browser", "version", "platform"})
    @BeforeMethod
    public void setUp(String browser, String version, String platform) throws Exception {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("browserVersion", version);

        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("platformName", platform);
        ltOptions.setCapability("build", "LambdaTest Advanced Scenario");
        ltOptions.setCapability("name", "IntegrationTabHandlingTest");
        ltOptions.setCapability("selenium_version", "4.21.0");

        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(
                new URL("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + "@hub.lambdatest.com/wd/hub"),
                capabilities);
    }

    @Test
    public void runScenario() throws InterruptedException {
        driver.get("https://www.lambdatest.com");
        
        Thread.sleep(2000);

        // Scroll to and click "Explore all Integrations"
        WebElement exploreLink = driver.findElement(By.xpath("//a[normalize-space()='Explore all Integrations']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", exploreLink);
        Thread.sleep(1000);

        // Get the href
        String href = exploreLink.getAttribute("href");

        // Open it in a new tab
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", href);

        // Now switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        System.out.println("Window Handles: " + tabs);
        assertEquals(tabs.size(), 2, "Two windows should be open.");
        driver.switchTo().window(tabs.get(1));
        Thread.sleep(2000);

        // Verify URL
        String currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, "https://www.lambdatest.com/integrations", "Unexpected URL!");

        // Scroll to Codeless Automation section
        WebElement codeless = driver.findElement(By.xpath("//a[normalize-space()='Codeless Automation']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", codeless);

        // Click TestingWhiz integration link
        WebElement link = driver.findElement(By.xpath("//a[normalize-space()='Integrate Testing Whiz with LambdaTest']"));
        link.click();
        Thread.sleep(3000);

        // Verify title
        assertEquals(driver.getTitle().trim(), "Running Automation Tests Using TestingWhiz LambdaTest | LambdaTest", "Title mismatch!");

        // Close current tab and switch back
        driver.close();
        driver.switchTo().window(tabs.get(0));
        System.out.println("Window count after close: " + driver.getWindowHandles().size());

        // Navigate to blog
        driver.get("https://www.lambdatest.com/blog");

        // Click Community link
        WebElement community = driver.findElement(By.xpath("//a[@href='https://community.lambdatest.com/'][normalize-space()='Community']"));
     // Scroll it into view (center of the screen)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", community);

        // Wait briefly
        Thread.sleep(1000);

        // Click using JavaScript (bypasses overlay)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", community);

        // Verify URL
        assertEquals(driver.getCurrentUrl(), "https://community.lambdatest.com/", "Community URL mismatch!");
    }

    @AfterMethod
    public void tearDown() {
     if (driver != null) driver.quit();
    }
}
