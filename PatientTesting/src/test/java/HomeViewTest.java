import static org.junit.jupiter.api.Assertions.*;

import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;

import junit.framework.Assert;

class HomeViewTest extends BaseTest {

	@BeforeClass
    public void setUp() throws Exception {
        setup();  // Calls BaseTest setup method to initialize Appium driver
    }
	@Test
    public void testDoctorListDisplay1() {
        // Verify that the list of doctors is displayed
        WebElement doctorList = driver.findElement(By.id("com.example.capstone:id/doctorList"));
        Assert.assertNotNull(doctorList, "Doctor list should be visible");
        
        // Verify a few doctor names are visible
        WebElement doctor1 = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Smith']"));
        WebElement doctor2 = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Johnson']"));
        Assert.assertTrue(doctor1.isDisplayed(), "Dr. Smith should be displayed in the list");
        Assert.assertTrue(doctor2.isDisplayed(), "Dr. Johnson should be displayed in the list");
    }

	@Test
    public void testDoctorListDisplay() {
        // Verify that the list of doctors is displayed
        WebElement doctorList = driver.findElement(By.id("com.example.capstone:id/doctorList"));
        assertNotNull(doctorList, "Doctor list should be visible");
        
        // Verify a few doctor names are visible
        WebElement doctor1 = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Smith']"));
        WebElement doctor2 = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Johnson']"));
        assertTrue(doctor1.isDisplayed(), "Dr. Smith should be displayed in the list");
        assertTrue(doctor2.isDisplayed(), "Dr. Johnson should be displayed in the list");
    }

    @Test
    public void testBookButtonAvailability() {
        // Find the 'Book' button for an available doctor
        WebElement availableDoctorButton = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Smith']//following-sibling::android.widget.Button"));
        assertTrue(availableDoctorButton.isEnabled(), "Book button should be enabled for available doctor");

        // Find the 'Book' button for an unavailable doctor
        WebElement unavailableDoctorButton = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Wilson']//following-sibling::android.widget.Button"));
        assertFalse(unavailableDoctorButton.isEnabled(), "Book button should be disabled for unavailable doctor");
    }

    @Test
    public void testBookingNavigation() {
        // Click on the 'Book' button for Dr. Smith (an available doctor)
        WebElement bookButton = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Smith']//following-sibling::android.widget.Button"));
        bookButton.click();

        // Wait for the navigation to BookingView
        WebElement bookingView = driver.findElement(By.id("com.example.capstone:id/bookingView"));
        assertTrue(bookingView.isDisplayed(), "Booking view should be displayed after booking button click");

        // Verify the doctor details are passed to the BookingView
        WebElement doctorName = driver.findElement(By.id("com.example.capstone:id/doctorName"));
        assertEquals("Dr. Smith", doctorName.getText(), "Booking view should show correct doctor name");
    }

    @Test
    public void testUnavailableDoctorNavigation() {
        // Click on the 'Book' button for Dr. Wilson (an unavailable doctor)
        WebElement bookButton = driver.findElement(By.xpath("//android.widget.TextView[@text='Dr. Wilson']//following-sibling::android.widget.Button"));
        bookButton.click();

        // Check if there is an alert or no navigation since the doctor is unavailable
        WebElement alertMessage = driver.findElement(By.id("com.example.capstone:id/alertMessage"));
        assertTrue(alertMessage.isDisplayed(), "Alert message should be displayed for unavailable doctor");
        assertEquals("Doctor is not available", alertMessage.getText(), "Correct alert message should be shown for unavailable doctor");
    }

    @AfterAll
    public void tearDown() {
        // Close the app
        if (driver != null) {
            driver.quit();
        }
    }

}
