import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;

import junit.framework.Assert;

class RegisterTest extends BaseTest {

	@BeforeClass
    public void setUp() throws Exception {
        setup();  // Calls BaseTest setup method to initialize Appium driver
    }
	
	@Test
    public void testValidRegistration() {
        // Find the UI elements and perform actions

        // Find the Email, Contact, Password, Confirm Password fields
		WebElement emailField = driver.findElement(By.id("com.example.capstone:id/email"));
		WebElement contactField = driver.findElement(By.id("com.example.capstone:id/contact"));
		WebElement passwordField = driver.findElement(By.id("com.example.capstone:id/password"));
		WebElement confirmPasswordField = driver.findElement(By.id("com.example.capstone:id/confirmPassword"));
		WebElement registerButton = driver.findElement(By.id("com.example.capstone:id/registerButton"));

        // Input valid details
        emailField.sendKeys("test@example.com");
        contactField.sendKeys("9876543210");
        passwordField.sendKeys("Test1234");
        confirmPasswordField.sendKeys("Test1234");

        // Click Register
        registerButton.click();

        // Verify the alert message after successful registration
        WebElement alertMessage = driver.findElement(By.id("com.example.capstone:id/alertMessage"));
        Assert.assertTrue(alertMessage.isDisplayed());
        Assert.assertEquals(alertMessage.getText(), "Registration Successful");
    }


}
