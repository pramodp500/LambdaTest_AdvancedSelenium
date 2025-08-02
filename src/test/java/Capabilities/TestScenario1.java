package Capabilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestScenario1 {
    public static final String USERNAME = "pramqa2025";
    public static final String ACCESS_KEY = "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i";
    public static final String GRID_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Create ChromeOptions
            ChromeOptions options = new ChromeOptions();

            // LambdaTest-specific options under "LT:Options"
            Map<String, Object> ltOptions = new HashMap<String, Object>();
            ltOptions.put("build", "LambdaTest Selenium Playground Build");
            ltOptions.put("name", "Simple Form Demo Test");
            ltOptions.put("platformName", "Windows 10");
            ltOptions.put("project", "Accessibility 101");
            ltOptions.put("selenium_version", "4.0.0");
            ltOptions.put("w3c", true); // optional but safe

            // Set LT options into ChromeOptions
            options.setCapability("LT:Options", ltOptions);
            options.setCapability("browserVersion", "latest");

            // Create remote WebDriver session
            driver = new RemoteWebDriver(new URL(GRID_URL), options);

            // 1. Open Selenium Playground
            driver.get("https://www.lambdatest.com/selenium-playground");

            // 2. Click “Simple Form Demo”
            WebElement simpleForm = driver.findElement(By.linkText("Simple Form Demo"));
            simpleForm.click();

            // 3. Validate URL
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("simple-form-demo")) {
                System.out.println("✅ URL contains 'simple-form-demo'");
            } else {
                System.out.println("❌ URL validation failed.");
            }

            // 4. Create message variable
            String message = "Welcome to LambdaTest";

            // 5. Enter value in the text box
            driver.findElement(By.id("user-message")).sendKeys(message);

            // 6. Click Get Checked Value
            driver.findElement(By.id("showInput")).click();

            // 7. Validate the displayed message
            String displayed = driver.findElement(By.id("message")).getText();
            if (displayed.equals(message)) {
                System.out.println("✅ Message displayed correctly: " + displayed);
            } else {
                System.out.println("❌ Message mismatch. Expected: " + message + ", Found: " + displayed);
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
