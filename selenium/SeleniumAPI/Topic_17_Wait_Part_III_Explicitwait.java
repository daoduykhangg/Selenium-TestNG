package SeleniumAPI;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_III_Explicitwait {
	WebDriver driver;
	WebDriverWait explicitwait;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		explicitwait = new WebDriverWait(driver, 15);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Id() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		explicitwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='ctl00_ContentPlaceholder1_Panel1']")));
		
		WebElement selectedDate = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']"));
		
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")));
		Assert.assertEquals(selectedDate.getText(), "No Selected Dates to display.");
		
		explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[text()='16']")));
		driver.findElement(By.xpath("//td/a[text()='16']")).click();
		
		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("div[id$='ContentPlaceholder1_RadCalendar1'] div[class='raDiv']")));
		
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='16']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[@class='rcSelected']/a[text()='16']")).isDisplayed());
		sleepInSecond(1);
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")));
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText(), "Wednesday, February 16, 2022");
	}

	 

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int randomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}