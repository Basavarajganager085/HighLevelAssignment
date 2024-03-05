package Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class SchedulingMeeting {

    WebDriver driver;
    SoftAssert softAssert;
    WebDriverWait wait;

    @BeforeClass
    public void NavigateToSchedulingMeetingPage(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://link.gohighlevel.com/widget/bookings/mathsclass");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        softAssert = new SoftAssert();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(testName="Verify that the shared Scheduling Link is working and able to navigate to Scheduling page")
    public void TC_001(){
        boolean b = driver.findElement(By.xpath("//h4[text()='Select Date & Time']")).isDisplayed();
        softAssert.assertTrue(b,"The Scheduling Meeting link is not working");
    }

    @Test(testName = "Verify that the End-user is able to create or schedule a meeting with valid details such as time, First Name, Last name, Phone, Email, and select the checkbox for terms and condition")
    public void TC_002() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='booking-info-value text-capitalize']")));
        WebElement Date_6 = driver.findElement(By.xpath("//td[@data-id='2024-3-6']//div[@class='vdpCellContent' and text()='6']"));
        wait.until(ExpectedConditions.elementToBeClickable(Date_6)).click();
        driver.findElement(By.xpath("//li[@class='widgets-time-slot'][5]")).click();
        WebElement select = driver.findElement(By.xpath("//button[@class='btn selected-slot']"));
        wait.until(ExpectedConditions.elementToBeClickable(select));
        driver.findElement(By.xpath("//button[@class='btn selected-slot']")).click();
        driver.findElement(By.xpath("//input[@id='first_name']")).sendKeys("basu");
        driver.findElement(By.xpath("//input[@id='last_name']")).sendKeys("Ganager");
        driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("9731477531");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("ganagerbasavaraj@gmail.com");
        driver.findElement(By.xpath("//input[@name='terms_and_conditions']")).click();
        driver.findElement(By.xpath("//button[@id='schedule-meeting-button']")).click();
        String confirmationMessage = driver.findElement(By.xpath("//h5[@class='confirmation-message']")).getText();
        String googleCalendar = driver.findElement(By.xpath("(//a[@class='add-calendar-button'])[1]")).getText();
        String outlookCalender = driver.findElement(By.xpath("(//a[@class='add-calendar-button'])[2]")).getText();
        softAssert.assertEquals(confirmationMessage,"Your Meeting has been Scheduled");
        softAssert.assertEquals(googleCalendar," Google Calendar");
        softAssert.assertEquals(outlookCalender," Outlook Calendar");
    }
    @AfterClass
    public void TearDown(){
        driver.close();
    }
}
