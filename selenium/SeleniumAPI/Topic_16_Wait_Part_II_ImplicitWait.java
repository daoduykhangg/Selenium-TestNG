package SeleniumAPI;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Wait_Part_II_ImplicitWait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
//		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
//	@Test
	public void TC_01_ImplicitWait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.xpath("//div[@id='start']//button")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='finish']//h4")).isDisplayed());
	}

	@Test
	public void TC_01_StaticWait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		sleepInSecond(1);
		
		driver.findElement(By.xpath("//div[@id='start']//button")).click();
		sleepInSecond(10);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='finish']//h4")).isDisplayed());
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