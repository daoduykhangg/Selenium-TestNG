
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Handle_Windows_Tabs {
	WebDriver driver;
	WebDriverWait explicitwait;
	Alert alert;
	String projectPath = System.getProperty("user.dir");
	String parentID;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		explicitwait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Window_Tab() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		parentID = driver.getWindowHandle();

		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		switchToWindowByTitle("Google");
		Assert.assertEquals(driver.getTitle(), "Google");
		System.out.println("Google = " + driver.getCurrentUrl());

		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
		System.out.println("Parent = " + driver.getCurrentUrl());

		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		switchToWindowByTitle("Facebook – log in or sign up");
		Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
		System.out.println("Facebook = " + driver.getCurrentUrl());

		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
		System.out.println("Parent = " + driver.getCurrentUrl());

		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		System.out.println("Tiki = " + driver.getCurrentUrl());

		closeAllWindowsWithoutParentPage(parentID);
		sleepInSecond(2);
	}

//	@Test
	public void TC_02_Window_Tab() {
		driver.get("https://kyna.vn/");
		parentID = driver.getWindowHandle();
		System.out.println("Kyna Title= " + driver.getTitle());
		System.out.println("Kyna Url= " + driver.getCurrentUrl());

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight);");
		sleepInSecond(2);

		driver.findElement(By.xpath("//div[@id='k-footer']//img[@alt='facebook']")).click();
		switchToWindowByTitle("Kyna.vn - Home | Facebook");
		System.out.println("Facebook Title= " + driver.getTitle());
		System.out.println("Facebook Url= " + driver.getCurrentUrl());

		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		System.out.println("Kyna Title= " + driver.getTitle());
		System.out.println("Kyna Url= " + driver.getCurrentUrl());

		driver.findElement(By.xpath("//div[@id='k-footer']//img[@alt='youtube']")).click();
		switchToWindowByTitle("Kyna.vn - YouTube");
		System.out.println("Youtube Title= " + driver.getTitle());
		System.out.println("Youtube Url= " + driver.getCurrentUrl());

		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		System.out.println("Kyna Title= " + driver.getTitle());
		System.out.println("Kyna Url= " + driver.getCurrentUrl());

		driver.findElement(By.xpath("//div[@id='k-footer-copyright']//a[contains(@href,'HomePage')]")).click();
		switchToWindowByTitle("Thông tin website thương mại điện tử - Online.Gov.VN");
		System.out.println("Youtube Title= " + driver.getTitle());
		System.out.println("Youtube Url= " + driver.getCurrentUrl());
		
		closeAllWindowsWithoutParentPage(parentID);
	}

	@Test
	public void TC_03_Window_Tab() {
		driver.get("http://live.techpanda.org/");
		parentID = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		sleepInSecond(1);
		
		driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Sony Xperia has been added to comparison list.']")).isDisplayed());
		
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Samsung Galaxy has been added to comparison list.']")).isDisplayed());
		
		driver.findElement(By.xpath("//span[text()='Compare']")).click();
		sleepInSecond(2);
		
		switchToWindowByID(parentID);
		System.out.println("Youtube Title= " + driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		
		closeAllWindowsWithoutParentPage(parentID);
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		
		alert = explicitwait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The comparison list was cleared.']")).isDisplayed());
	}

	public void switchToWindowByID(String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String window : allWindows) {
			if (!window.equals(parentID)) {
				driver.switchTo().window(window);
				break;
			}
		}
	}

	public void switchToWindowByTitle(String expectedPageTitle) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String window : allWindows) {
			driver.switchTo().window(window);
			sleepInSecond(2);
			String actualPageTitle = driver.getTitle();
			if (actualPageTitle.equals(expectedPageTitle)) {
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParentPage(String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String window : allWindows) {
			if (!window.equals(parentID)) {
				driver.switchTo().window(window);
				sleepInSecond(1);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
		sleepInSecond(1);
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