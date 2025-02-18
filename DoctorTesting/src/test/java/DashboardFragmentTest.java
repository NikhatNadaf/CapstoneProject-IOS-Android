import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

class DashboardFragmentTest {

	private static AndroidDriver driver;

    //@Before
    public void setUp() throws MalformedURLException {
        // Initialize the driver
        driver = AppiumSetup.getDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCreateSlot() {
        WebElement doctorNameField = driver.findElement(By.id("com.myapplication.doctormodule:id/etDoctorName"));
        doctorNameField.sendKeys("Dr. John Doe");

        WebElement specialityField = driver.findElement(By.id("com.myapplication.doctormodule:id/etSpeciality"));
        specialityField.sendKeys("Cardiologist");

        WebElement slotSpinner = driver.findElement(By.id("com.myapplication.doctormodule:id/spinnerSlot"));
        slotSpinner.click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Morning']")).click();

        WebElement timeSpinner = driver.findElement(By.id("com.myapplication.doctormodule:id/spinnerTimeAvailable"));
        timeSpinner.click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='08:00 AM']")).click();

        WebElement createSlotButton = driver.findElement(By.id("com.myapplication.doctormodule:id/btnCreateSlot"));
        createSlotButton.click();
    }

    @Test
    public void testBackButton() {
        WebElement backButton = driver.findElement(By.id("com.myapplication.doctormodule:id/btnBack"));
        backButton.click();
    }

    @Test
    public void testLogoutButton() {
        WebElement logoutButton = driver.findElement(By.id("com.myapplication.doctormodule:id/btnLogout"));
        logoutButton.click();
    }
    
    @Test
    public void testDoctorListFragment() {
        // Verify the title "List of Doctors"
        WebElement title = driver.findElement(By.xpath("//android.widget.TextView[@text='List of Doctors']"));
        assertNotNull(title);

        // Check if RecyclerView is displayed
        WebElement recyclerView = driver.findElement(By.id("com.myapplication.doctormodule:id/recyclerView"));
        assertNotNull(recyclerView);

        // Click on the Back button
        WebElement backButton = driver.findElement(By.id("com.myapplication.doctormodule:id/backBtn"));
        backButton.click();
    }

}
