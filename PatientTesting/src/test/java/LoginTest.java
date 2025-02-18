import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @BeforeClass
    public void setUp() throws Exception {
        setup();  // Calls BaseTest setup method to initialize Appium driver
    }

    @Test
    public void testLoginSuccess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Enter email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeTextField")));
        emailField.sendKeys("test@example.com"); // Replace with a valid test email

        // Enter password
        WebElement passwordField = driver.findElement(By.xpath("//XCUIElementTypeSecureTextField"));
        passwordField.sendKeys("password123"); // Replace with valid test password

        // Click login button
        WebElement loginButton = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Login']"));
        loginButton.click();

        // Check if navigated to HomeView
        WebElement homeView = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='Home']")));
        Assert.assertTrue(homeView.isDisplayed(), "Login failed, HomeView not displayed!");
    }

    @Test
    public void testLoginFailure() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Enter incorrect email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeTextField")));
        emailField.sendKeys("invalid@example.com");

        // Enter incorrect password
        WebElement passwordField = driver.findElement(By.xpath("//XCUIElementTypeSecureTextField"));
        passwordField.sendKeys("wrongpassword");

        // Click login button
        WebElement loginButton = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Login']"));
        loginButton.click();

        // Verify alert message appears
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[contains(@name, 'Invalid email or password')]")));
        Assert.assertTrue(alert.isDisplayed(), "Error alert not displayed for incorrect credentials!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit(); // Close the Appium driver
    }
}
