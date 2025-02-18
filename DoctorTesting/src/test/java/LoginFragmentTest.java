import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginFragmentTest {

    private AndroidDriver driver;
    private WebDriverWait wait;
    @Before
    public void setUp() throws MalformedURLException {
        driver = AppiumSetup.getDriver();
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginFragmentUI() {
        // Find the username input field and verify it's displayed
        
    	WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.myapplication.doctormodule:id/etEmail")));
        assertTrue("Username field is not displayed", username.isDisplayed());

        // Find the password input field and verify it's displayed
        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.myapplication.doctormodule:id/etPassword")));
        assertTrue("Password field is not displayed", pass.isDisplayed());

        // Find the login button and verify it's displayed
        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.myapplication.doctormodule:id/btnLogin")));
        assertTrue("Login button is not displayed", loginBtn.isDisplayed());
    }

   

    @After
    public void tearDown() {
        AppiumSetup.tearDown();
    }
}
