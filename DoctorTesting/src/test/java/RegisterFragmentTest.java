import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class RegisterFragmentTest {

    private AndroidDriver driver;

    //@Before
    public void setUp() throws MalformedURLException {
        // Initialize the driver
        driver = AppiumSetup.getDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testRegisterFragmentUI() {
        // Find and verify the Full Name input field is displayed
        WebElement nameField = driver.findElement(By.id("com.myapplication.doctormodule:id/etName"));
        assertTrue("Full Name field is not displayed", nameField.isDisplayed());

        // Find and verify the Email input field is displayed
        WebElement emailField = driver.findElement(By.id("com.myapplication.doctormodule:id/etEmail"));
        assertTrue("Email field is not displayed", emailField.isDisplayed());

        // Find and verify the Password input field is displayed
        WebElement passwordField = driver.findElement(By.id("com.myapplication.doctormodule:id/etPassword"));
        assertTrue("Password field is not displayed", passwordField.isDisplayed());

        // Find and verify the Register button is displayed
        WebElement registerButton = driver.findElement(By.id("com.myapplication.doctormodule:id/btnRegister"));
        assertTrue("Register button is not displayed", registerButton.isDisplayed());
    }

   

    //@After
    public void tearDown() {
        // Close the app and driver
        AppiumSetup.tearDown();
    }
}
