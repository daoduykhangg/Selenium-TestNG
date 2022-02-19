package SeleniumAPI;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Fix_Popup() {
		driver.get("https://ngoaingu24h.vn/");

		By popup = By.xpath("//h4[text()='Đăng nhập']/parent::div/parent::div/parent::div/parent::div");
		Assert.assertFalse(driver.findElement(popup).isDisplayed());

		driver.findElement(By.xpath("//div[@id='button-login-dialog']//button[text()='Đăng nhập']")).click();
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(popup).isDisplayed());

		driver.findElement(By.xpath("//input[@id='account-input']")).sendKeys("automationfc");
		driver.findElement(By.xpath("//input[@id='password-input']")).sendKeys("automationfc");
		driver.findElement(By.xpath("//p[text()='Hoặc đăng nhập bằng']/preceding-sibling::button")).click();
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='row error-login-panel' and text()='Tài khoản không tồn tại!']")).isDisplayed());

	}

//	@Test
	public void TC_02_Random_Popup_In_Dom() {
		driver.get("https://blog.testproject.io/");

		Assert.assertTrue(areJQueryAndJSLoadedSuccess(driver));
		By popup = By.cssSelector("div.mailch-bg");

		if (driver.findElement(popup).isDisplayed()) {
			System.out.println("-------------------Popup displayed and close it--------------------------");
			driver.findElement(By.cssSelector(".close-mailch")).click();
			sleepInSecond(1);
		} else {
			System.out.println("-------------------Popup undisplayed--------------------------");
		}

		Assert.assertFalse(driver.findElement(popup).isDisplayed());
		driver.findElement(By.xpath("//section[@id='search-2']//input[@class='search-field']")).sendKeys("Selenium");
		sleepInSecond(1);
		driver.findElement(By.xpath("//section[@id='search-2']//input[@class='search-field']/following-sibling::span")).click();
		sleepInSecond(1);

		Assert.assertTrue(areJQueryAndJSLoadedSuccess(driver));

		List<WebElement> titles = driver.findElements(By.cssSelector("h3.post-title a"));

		for (WebElement title : titles) {
			System.out.println(title.getText());
			Assert.assertTrue(title.getText().contains("Selenium"));
		}
	}

//	@Test
	public void TC_03_Id() {
		driver.get("https://vnk.edu.vn/");
		sleepInSecond(20);
		Assert.assertTrue(areJQueryAndJSLoadedSuccess(driver));
		By popup = By.cssSelector("div.tve_p_lb_overlay");

		if (driver.findElement(popup).isDisplayed()) {
			System.out.println("-------------------Popup displayed and close it--------------------------");
			driver.findElement(By.cssSelector("div.tcb-icon-display")).click();
			sleepInSecond(1);
		} else {
			System.out.println("-------------------Popup undisplayed--------------------------");
		}
		Assert.assertFalse(driver.findElement(popup).isDisplayed());
	}

//	@Test
	public void TC_04_Random_Popup_Not_IN_DOM() {
		driver.get("https://dehieu.vn/");
		sleepInSecond(10);

		List<WebElement> popup = driver.findElements(By.cssSelector("div.popup-fade"));
		if (popup.size() > 0) {
			System.out.println("-------------------Popup displayed and close it--------------------------");
			driver.findElement(By.cssSelector("button#close-popup")).click();
			sleepInSecond(1);
		} else {
			System.out.println("-------------------Popup undisplayed--------------------------");
		}
		Assert.assertEquals(driver.findElements(By.cssSelector("div.popup-fade")).size(), 0);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 120);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active ===0);");
			}
		};
		return explicitWait.until(jQueryLoad);
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