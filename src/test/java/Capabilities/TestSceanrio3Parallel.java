package Capabilities;

import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TestSceanrio3Parallel {

    WebDriver driver;
	@Parameters({"browser", "platform", "browserVersion"})
    @BeforeMethod
    public void setUp(String browser, String platform, String browserVersion) throws Exception {
        String username = "pramqa2025";
        String accessKey = "LT_rcCzH9ULzk30W21Z9oVvBnfN0CZp5CSg9ETGb5IyTsDkF7i";
        String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";

        MutableCapabilities capabilities;

        if (browser.equalsIgnoreCase("Chrome")) {
            capabilities = new ChromeOptions();
        } else if (browser.equalsIgnoreCase("Safari")) {
            capabilities = new SafariOptions();
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        Map<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("build", "LambdaTest Parallel Build");
        ltOptions.put("name", "Input Form Submit Test - " + browser);
        ltOptions.put("platformName", platform);
        ltOptions.put("browserVersion", browserVersion);
        ltOptions.put("selenium_version", "4.0.0");

        capabilities.setCapability("LT:Options", ltOptions);
        driver = new RemoteWebDriver(new URL(gridURL), capabilities);
    }

    @Test
    public void testInputFormSubmission() throws InterruptedException {
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.findElement(By.linkText("Input Form Submit")).click();

        // 1. Submit without filling the form
        driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();

        // 2. Validate the error message for first required field (Name)
        WebElement nameField = driver.findElement(By.name("name"));
        String validationMsg = nameField.getAttribute("validationMessage");
        if (!validationMsg.isEmpty()) {
            System.out.println("✅ Validation message displayed: " + validationMsg);
        } else {
            System.out.println("❌ No validation message found.");
        }

        // 3. Fill out form
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Pramod");
        driver.findElement(By.xpath("//input[@id='inputEmail4']")).sendKeys("pramod@example.com");
        driver.findElement(By.xpath("//input[@id='inputPassword4']")).sendKeys("securePass123");
        driver.findElement(By.xpath("//input[@id='company']")).sendKeys("LambdaTest");
        driver.findElement(By.xpath("//input[@id='websitename']")).sendKeys("https://lambdatest.com");
        driver.findElement(By.xpath("//input[@id='inputCity']")).sendKeys("Mumbai");
        driver.findElement(By.xpath("//input[@id='inputAddress1']")).sendKeys("Street 1");
        driver.findElement(By.xpath("//input[@id='inputAddress2']")).sendKeys("Area 2");
        driver.findElement(By.xpath("//input[@id='inputState']")).sendKeys("MH");
        driver.findElement(By.xpath("//input[@id='inputZip']")).sendKeys("400001");

        // 4. Select "United States" from Country dropdown
        WebElement countryDropdown = driver.findElement(By.xpath("//select[@name='country']"));
        countryDropdown.sendKeys("United States");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement submitBtn = wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='Submit']")));

        try {
            submitBtn.click(); // First try normal click
        } catch (ElementNotInteractableException e) {
            // Fallback for Safari
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
        }


        // 6. Wait and validate success message
        Thread.sleep(2000);
        WebElement successMsg = driver.findElement(By.className("success-msg"));
        String msgText = successMsg.getText();

        if (msgText.contains("Thanks for contacting us")) {
            System.out.println("✅ Success message verified.");
        } else {
            System.out.println("❌ Expected success message not found.");
        }
    }

	@AfterMethod
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
