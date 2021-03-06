package SeleniumAPI;

import static org.testng.Assert.assertThrows;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_User_Interactions {
	WebDriver driver;
	Actions action;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Hover_Element() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		action.moveToElement(driver.findElement(By.cssSelector("#age"))).perform();
		sleepInSecond(1);

		Assert.assertEquals(driver.findElement(By.cssSelector(".ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
	}

	@Test
	public void TC_02_Hover_Element() {
		driver.get("http://www.myntra.com/");
		action.moveToElement(driver.findElement(By.xpath("//nav[@class='desktop-navbar']//a[text()='Kids']"))).perform();
		sleepInSecond(1);
		driver.findElement(By.xpath("//nav[@class='desktop-navbar']//a[text()='Home & Bath']")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("h1.title-title")).getText(), "Kids Home Bath");
	}

	@Test
	public void TC_03_Hover_Element() {
		driver.get("https://www.fahasa.com/");

		action.moveToElement(driver.findElement(By.xpath("(//span[text()='S??ch Trong N?????c'])[2]"))).perform();
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("(//span[text()='S??ch Trong N?????c'])[2]/parent::a/following-sibling::div//a[text()='K??? N??ng S???ng']")).isDisplayed());

	}

	@Test
	public void TC_04_Click_And_Hover() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> element = driver.findElements(By.xpath("//ol/li"));
		action.clickAndHold(element.get(0)).moveToElement(element.get(3)).release().perform();
		sleepInSecond(2);

		Assert.assertEquals(driver.findElements(By.xpath("//li[contains(@class,'ui-selected')]")).size(), 4);
	}

	@Test
	public void TC_05_Click_And_Select_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> element = driver.findElements(By.xpath("//ol/li"));
		action.keyDown(Keys.CONTROL).click(element.get(0)).click(element.get(2)).click(element.get(5)).click(element.get(10)).keyUp(Keys.CONTROL).perform();
		sleepInSecond(2);

		Assert.assertEquals(driver.findElements(By.xpath("//li[contains(@class,'ui-selected')]")).size(), 4);
	}

	@Test
	public void TC_06_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		By doubleclickButton = By.xpath("//button[text()='Double click me']");
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(doubleclickButton));
		action.doubleClick(driver.findElement(doubleclickButton)).perform();
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("#demo")).getText(), "Hello Automation Guys!");
	}

	@Test
	public void TC_07_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		sleepInSecond(1);
		action.moveToElement(driver.findElement(By.cssSelector(".context-menu-icon-quit"))).perform();
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(By.cssSelector(".context-menu-icon-quit.context-menu-hover")).isDisplayed());

		action.click(driver.findElement(By.cssSelector(".context-menu-icon-quit"))).perform();
		sleepInSecond(1);
		driver.switchTo().alert().accept();
		
		Assert.assertFalse(driver.findElement(By.cssSelector(".context-menu-icon-quit")).isDisplayed());
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