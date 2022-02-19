package SeleniumAPI;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Frame_Iframe {
	WebDriver driver;
	Select select;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Iframe() {
		driver.get("https://kyna.vn/");
		sleepInSecond(1);
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='fanpage ']//iframe")));

		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText(), "167K likes");
		driver.switchTo().defaultContent();
		sleepInSecond(1);
		driver.findElement(By.cssSelector("div#cs-live-chat")).click();
		driver.switchTo().frame(driver.findElement(By.cssSelector("#cs_chat_iframe")));
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("JohnCena");
		sleepInSecond(1);
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0123456789");
		sleepInSecond(1);
		select = new Select(driver.findElement(By.cssSelector("#serviceSelect")));
		select.selectByVisibleText("TƯ VẤN TUYỂN SINH");
		sleepInSecond(1);
		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys("abc");
		sleepInSecond(1);

		driver.findElement(By.xpath("//input[@value='Gửi tin nhắn']")).click();

		driver.switchTo().defaultContent();
		driver.findElement((By.cssSelector("input#live-search-bar"))).sendKeys("excel");
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()=\"'excel'\"]")).isDisplayed());
	}

	@Test
	public void TC_02_Iframe() {
		String currentPageUrl;
		driver.get("https://automationfc.com/");
		currentPageUrl = driver.getCurrentUrl();
		System.out.println("Parent = " + currentPageUrl);
		driver.findElement(By.xpath("//h2[@class='post-title']//a[contains(text(),'Fullstack Selenium')]")).click();
		sleepInSecond(1);

		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe.youtube-player")));
		currentPageUrl = driver.getCurrentUrl();
		System.out.println("iframe = " + currentPageUrl);
		sleepInSecond(1);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ytp-title-text a.ytp-title-link")).getText(), "[Online 23] - Topic 01 (Introduction about Course and Target)");
		driver.switchTo().defaultContent();
		sleepInSecond(1);
		Assert.assertEquals(driver.findElement(By.cssSelector("h1.post-title")).getText(), "[Training Online] – Fullstack Selenium WebDriver Framework in Java (Livestream)");
	}

	@Test
	public void TC_03_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='login_page']")));
		sleepInSecond(1);
		driver.findElement(By.xpath("//input[@name='fldLoginUserId']")).sendKeys("automationfc");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//input[@name='fldPassword']")).isDisplayed());

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