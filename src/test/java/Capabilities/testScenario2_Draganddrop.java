package Capabilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class testScenario2_Draganddrop {
    public static final String USERNAME = "pramqa2025";
    public static final String ACCESS_KEY = "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i";
    public static final String GRID_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // LambdaTest ChromeOptions + W3C-compliant LT options
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> ltOptions = new HashMap<String, Object>();
            ltOptions.put("build", "LambdaTest Selenium Playground Build");
            ltOptions.put("name", "Drag & Drop Slider Test");
            ltOptions.put("platformName", "Windows 10");
            ltOptions.put("selenium_version", "4.0.0");
            ltOptions.put("w3c", true);
            options.setCapability("LT:Options", ltOptions);
            options.setCapability("browserVersion", "latest");

            driver = new RemoteWebDriver(new URL(GRID_URL), options);

            // Step 1: Open Playground
            driver.get("https://www.lambdatest.com/selenium-playground");

            // Step 2: Click "Drag & Drop Sliders"
            driver.findElement(By.linkText("Drag & Drop Sliders")).click();

            // Step 3: Locate slider with default value 15
            WebElement slider = driver.findElement(By.xpath("//input[@value='15']"));
            WebElement rangeText = driver.findElement(By.id("range"));

            // Step 4: Drag the slider using Actions
            Actions actions = new Actions(driver);

            // Move the slider handle towards right — 80 units typically covers from 15 to 95
            actions.dragAndDropBy(slider, 80, 0).perform();

            // Wait a bit to let it update visually
            Thread.sleep(1000);

            // Step 5: Validate if the range value is 95
            String value = rangeText.getText();
            if ("95".equals(value)) {
                System.out.println("✅ Slider moved successfully to 95");
            } else {
                System.out.println("❌ Slider value mismatch. Found: " + value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
