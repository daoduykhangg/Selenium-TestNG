package SeleniumAPI;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Alert {
	WebDriver driver;
	Alert alert;
	WebDriverWait explicitwait;
	String projectPath = System.getProperty("user.dir");
	String firefoxAuthen = projectPath + "\\autoIT\\authen_firefox.exe";
	String chromeAuthen = projectPath + "\\autoIT\\authen_chrome.exe";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
//		driver = new ChromeDriver();
		explicitwait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		sleepInSecond(2);

		alert = explicitwait.until(ExpectedConditions.alertIsPresent());

		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();

		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked an alert successfully");

	}

	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(2);
		alert = explicitwait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked: Cancel");

		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		sleepInSecond(2);
		alert = explicitwait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked: Ok");
	}

	public void TC_03_Prompt_alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		String fullName = "DaoDuyKhang";
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(2);
		alert = explicitwait.until(ExpectedConditions.alertIsPresent());

		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys(fullName);
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You entered: null");

		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		sleepInSecond(2);
		alert = explicitwait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys(fullName);
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You entered: " + fullName);
	}

//	@Test
	public void TC_04_Authentication_alert() {
		String username = "admin";
		String password = "admin";
		String url = "http://" + username + ":" + password + "@the-internet.herokuapp.com/basic_auth";

		driver.get(url);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}

//	@Test
	public void TC_05_Authentication_alert_Click_Other_Link() {
		driver.get("https://the-internet.herokuapp.com/");
		String username = "admin";
		String password = "admin";
		String url = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");

		driver.get(getLinkUserPass(url, username, password));

		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}

	@Test
	public void TC_06_Authentication_alert_By_AutoIT() throws IOException {
		String username = "admin";
		String password = "admin";

		if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { firefoxAuthen, username, password });
		} else if (driver.toString().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] { chromeAuthen, username, password });
		}
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		sleepInSecond(10);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}

	public String getLinkUserPass(String link, String username, String password) {
		String[] links = link.split("//");
		return links[0] + "//" + username + ":" + password + "@" + links[1];
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