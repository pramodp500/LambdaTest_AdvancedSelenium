package Capabilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AccessibilityTest {
 
   public static final String GRID_URL = "https://pramqa2025:LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i@hub.lambdatest.com/wd/hub";

    public static void runTest(String testName, String urlToTest) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", "Chrome");
            caps.setCapability("browserVersion", "latest");

            Map<String, Object> ltOptions = new HashMap<String, Object>();
            ltOptions.put("platformName", "Windows 10");
            ltOptions.put("project", "Accessibility Automation");
            ltOptions.put("build", "LambdaTest WCAG Build");
            ltOptions.put("name", testName);
            ltOptions.put("accessibility", true);
            ltOptions.put("plugin", "Java-Maven");

            Map<String, Object> accessibilityOptions = new HashMap<String, Object>();
            accessibilityOptions.put("wcagVersion", "2.1AA");
            accessibilityOptions.put("includeBestPractice", true);
            accessibilityOptions.put("includeNeedsReview", true);
            ltOptions.put("accessibilityOptions", accessibilityOptions);

            caps.setCapability("LT:Options", ltOptions);

            WebDriver driver = new RemoteWebDriver(new URL(GRID_URL), caps);
            driver.get(urlToTest);

            System.out.println("Opened: " + urlToTest);
            Thread.sleep(7000); // Let scan run

            String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
            System.out.println("Session ID for " + testName + ": " + sessionId);

            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        runTest("Homepage Accessibility", "https://ecommerce-playground.lambdatest.io/");
        runTest("Login Page Accessibility", "https://ecommerce-playground.lambdatest.io/index.php?route=account/login");
        runTest("Product Page Accessibility", "https://ecommerce-playground.lambdatest.io/index.php?route=product/product&product_id=40");
    }
}

