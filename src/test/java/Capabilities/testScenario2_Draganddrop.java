package Capabilities;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class testScenario2_Draganddrop {
    public static final String USERNAME = "pramqa2025";
    public static final String ACCESS_KEY = "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i";
    public static final String GRID_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    WebDriver driver;

    @Parameters({ "browser", "platform", "browserVersion" })
    @Test
    public void testDragAndDropSlider(String browser, String platform, String browserVersion) {
        try {
            // Create Options object based on browser
            MutableCapabilities options;
            if (browser.equalsIgnoreCase("Chrome")) {
                options = new ChromeOptions();
            } else if (browser.equalsIgnoreCase("Safari")) {
                options = new SafariOptions();
            } else {
                throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            // LambdaTest options
            Map<String, Object> ltOptions = new HashMap<String, Object>();
            ltOptions.put("build", "LambdaTest Selenium Playground Build");
            ltOptions.put("name", "Drag & Drop Slider Test");
            ltOptions.put("platformName", platform);
            ltOptions.put("browserVersion", browserVersion);
            ltOptions.put("project", "Accessibility 101");
            ltOptions.put("selenium_version", "4.0.0");
            ltOptions.put("w3c", true);
            options.setCapability("LT:Options", ltOptions);

            // Set capability if not done via LT:Options (optional)
            options.setCapability("browserVersion", browserVersion);

            // Start driver
            driver = new RemoteWebDriver(new URL(GRID_URL), options);

            // Open Playground
            driver.get("https://www.lambdatest.com/selenium-playground");

            // Click "Drag & Drop Sliders"
            driver.findElement(By.linkText("Drag & Drop Sliders")).click();

            // Locate slider and its output
            WebElement slider = driver.findElement(By.xpath("//input[@value='15']"));
            WebElement rangeText = driver.findElement(By.id("range"));

            // Move the slider
            Actions actions = new Actions(driver);
            actions.dragAndDropBy(slider, 80, 0).perform();
            Thread.sleep(1000);

            // Validate
            String value = rangeText.getText();
            if ("95".equals(value)) {
                System.out.println("✅ Slider moved to 95");
            } else {
                System.out.println("❌ Expected 95 but found " + value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) driver.quit();
        }
    }
}
