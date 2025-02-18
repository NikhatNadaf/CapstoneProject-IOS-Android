import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SplashScreenTest {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws MalformedURLException {
        driver = AppiumSetup.getDriver();
         wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testSplashScreen() {
        // Find and verify the splash screen logo is displayed
        //WebElement logo = driver.findElement(By.id("com.myapplication.doctormodule:id/imgLogo"));  // Replace with your actual logo ID
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.myapplication.doctormodule:id/imgLogo")));
    	assertTrue("Splash screen logo is not displayed", logo.isDisplayed());
        
    	// Wait for 3 seconds to simulate splash screen wait
        try {
            Thread.sleep(3000);  // Simulate the 3-second splash screen delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    @Test
    public void testLoginScreenNavigation() {
        // Find and click on the Login button (Replace with actual element ID)
        WebElement loginButton = driver.findElement(By.id("com.myapplication.doctormodule:id/btnLogin"));
        loginButton.click();
        
        // Find the login screen title or an element to verify navigation
        WebElement loginTitle = driver.findElement(By.id("com.myapplication.doctormodule:id/loginTitle"));  // Replace with actual title ID
        assertTrue("Login screen is not displayed", loginTitle.isDisplayed());
    }

    @After
    public void tearDown() {
        AppiumSetup.tearDown();
    }
}
