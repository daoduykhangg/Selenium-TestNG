
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Wait_part_I_Condition_Status {
	WebDriver driver;
	WebDriverWait explicitwait;

	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		explicitwait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Visible() {
		driver.get("https://www.facebook.com/");
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create New Account']"))).click();

		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("khang@gmail.com");
		sleepInSecond(1);

		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}

//	@Test
	public void TC_02_Invisible_In_DOM() {
		driver.get("https://www.facebook.com/");
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create New Account']"))).click();
		sleepInSecond(1);

		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}

//	@Test
	public void TC_03_Invisible_Not_In_DOM() {
		driver.get("https://www.facebook.com/");
		sleepInSecond(1);

		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}

//	@Test
	public void TC_04_Presence() {
		driver.get("https://www.facebook.com/");
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create New Account']"))).click();

		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("khang@gmail.com");
		sleepInSecond(1);

		explicitwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}

//	@Test
	public void TC_05_Presence_Not_In_UI() {
		driver.get("https://www.facebook.com/");
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create New Account']"))).click();
		sleepInSecond(1);

		explicitwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}

	@Test
	public void TC_06_Staleness() {
		driver.get("https://www.facebook.com/");
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create New Account']"))).click();
		sleepInSecond(1);

//		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("khang@gmail.com");
//		sleepInSecond(1);
//
		WebElement confirmEmailTextbox = driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']"));
//		explicitwait.until(ExpectedConditions.visibilityOf(confirmEmailTextbox));

		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		sleepInSecond(1);
		
		explicitwait.until(ExpectedConditions.stalenessOf(confirmEmailTextbox));
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