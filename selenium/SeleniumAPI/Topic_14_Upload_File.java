package SeleniumAPI;

import java.util.List;
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

public class Topic_14_Upload_File {
	WebDriver driver;
	WebDriverWait explicitwait;
	String projectPath = System.getProperty("user.dir");
	String dalatName = "Dalat.jpg";
	String hueName = "Hue.jpg";
	String sapaName = "Sapa.jpg";

	String dalatPath = projectPath + "\\uploadFiles\\" + dalatName;
	String huePath = projectPath + "\\uploadFiles\\" + hueName;
	String sapaPath = projectPath + "\\uploadFiles\\" + sapaName;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		explicitwait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Upload_Single_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(dalatPath);
		sleepInSecond(1);

		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(huePath);
		sleepInSecond(1);

		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(sapaPath);
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + dalatName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + hueName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + sapaName + "']")).isDisplayed());

		List<WebElement> startButtons = driver.findElements(By.cssSelector("tr.template-upload button.start"));
		for (WebElement startButton : startButtons) {
			startButton.click();
			sleepInSecond(1);
		}
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + dalatName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + hueName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sapaName + "']")).isDisplayed());
	}

//	@Test
	public void TC_02_Upload_Multiple_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(dalatPath + "\n" + huePath + "\n" + sapaPath);
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + dalatName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + hueName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + sapaName + "']")).isDisplayed());

		List<WebElement> startButtons = driver.findElements(By.cssSelector("tr.template-upload button.start"));
		for (WebElement startButton : startButtons) {
			startButton.click();
			sleepInSecond(1);
		}
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + dalatName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + hueName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sapaName + "']")).isDisplayed());
	}

	@Test
	public void TC_03_Upload_File() {
		driver.get("https://gofile.io/uploadFiles");
		sleepInSecond(1);

		driver.findElement(By.xpath("//input[@id='uploadFile-Input']")).sendKeys(dalatPath + "\n" + huePath + "\n" + sapaPath);
		sleepInSecond(1);
		List<WebElement> progressbars = driver.findElements(By.xpath("//div[@class='progress-bar bg-primary']"));
		explicitwait.until(ExpectedConditions.invisibilityOfAllElements(progressbars));

		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='text-center']//i[@class='fas fa-spinner fa-spin']")));

		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));

		String url = driver.findElement(By.xpath("//a[@id='rowUploadSuccess-downloadPage']")).getAttribute("href");
		driver.get(url);

		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='text-center']//i[@class='fas fa-spinner fa-spin']")));

		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dalatName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + hueName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + sapaName + "']")).isDisplayed());
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