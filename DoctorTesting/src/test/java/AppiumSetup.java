import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Platform;
import java.net.URL;
import java.time.Duration;
import java.net.MalformedURLException;

public class AppiumSetup {
    protected static AndroidDriver driver;
    
    @BeforeAll
    public static AndroidDriver getDriver() throws MalformedURLException {
        if (driver == null) {
            UiAutomator2Options caps = new UiAutomator2Options();
            caps.setPlatformName("Android");
            caps.setDeviceName("realme RMX3430"); // Update with your device name
            caps.setAutomationName("UiAutomator2");
            caps.setAppActivity("com.myapplication.doctormodule.ui.MainActivity"); // Update with correct main activity
            caps.setAppPackage("com.myapplication.doctormodule");

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        }
        return driver;
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
