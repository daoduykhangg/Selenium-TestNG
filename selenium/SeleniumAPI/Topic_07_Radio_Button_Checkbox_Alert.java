package SeleniumAPI;

import static org.testng.Assert.assertFalse;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Radio_Button_Checkbox_Alert {
	WebDriver driver;
	JavascriptExecutor js;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.xpath("//li//a[text()='Đăng nhập']")).click();
		By loginButton = By.xpath("//button[@class='fhs-btn-login']");

		Assert.assertFalse(driver.findElement(loginButton).isEnabled());

		driver.findElement(By.cssSelector("input#login_username")).sendKeys("Khang@gmail.com");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456");

		Assert.assertTrue(driver.findElement(loginButton).isEnabled());

		Assert.assertEquals(Color.fromString(driver.findElement(loginButton).getCssValue("background-color")).asHex().toUpperCase(), "#C92127");

		driver.navigate().refresh();
		driver.findElement(By.xpath("//li//a[text()='Đăng nhập']")).click();

		js.executeScript("arguments[0].removeAttribute('disabled')", driver.findElement(loginButton));
		driver.findElement(loginButton).click();
		sleepInSecond(2);

		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Số điện thoại/Email']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Mật khẩu']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
	}

	public void TC_02_Default_Checkbox_Radio_button() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");

		By dualZone = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");

		checkToCheckbox(dualZone);
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(dualZone).isSelected());

		uncheckToCheckbox(dualZone);
		sleepInSecond(1);
		Assert.assertFalse(driver.findElement(dualZone).isSelected());

		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");

		By petrol = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");
		checkToCheckbox(petrol);
		sleepInSecond(2);
	}

	@Test
	public void TC_03_Custom_Checkbox_Radio_button() {
		driver.get("https://material.angular.io/components/radio/examples");
		By summerRadioButton = By.xpath("//input[@value='Summer']");

		Assert.assertFalse(driver.findElement(summerRadioButton).isSelected());

		checkToCheckboxByJS(summerRadioButton);
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(summerRadioButton).isSelected());

		driver.get("https://material.angular.io/components/checkbox/examples");
		By checkedCheckbox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		By indeterminateCheckbox = By.xpath("//span[text()='Indeterminate']/preceding-sibling::span/input");

		Assert.assertFalse(driver.findElement(checkedCheckbox).isSelected());
		Assert.assertFalse(driver.findElement(indeterminateCheckbox).isSelected());

		checkToCheckboxByJS(checkedCheckbox);
		checkToCheckboxByJS(indeterminateCheckbox);
		sleepInSecond(1);

		Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());
		Assert.assertTrue(driver.findElement(indeterminateCheckbox).isSelected());

		uncheckToCheckboxByJS(checkedCheckbox);
		uncheckToCheckboxByJS(indeterminateCheckbox);
		sleepInSecond(1);

		Assert.assertFalse(driver.findElement(checkedCheckbox).isSelected());
		Assert.assertFalse(driver.findElement(indeterminateCheckbox).isSelected());
	}

	@Test
	public void TC_04_Custom_Checkbox_Radio_button() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		By hcmRadioButton = By.xpath("//div[@aria-label='Hồ Chí Minh']");
		Assert.assertEquals(driver.findElement(hcmRadioButton).getAttribute("aria-checked"), "false");

		driver.findElement(hcmRadioButton).click();
		Assert.assertEquals(driver.findElement(hcmRadioButton).getAttribute("aria-checked"), "true");

		List<WebElement> checkboxes = driver.findElements(By.xpath("//div[@role='checkbox']"));

		for (WebElement checkbox : checkboxes) {
			checkbox.click();
			sleepInSecond(1);
			Assert.assertEquals(checkbox.getAttribute("aria-checked"), "true");
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void checkToCheckboxByJS(By by) {
		if (!driver.findElement(by).isSelected()) {
			js.executeScript("arguments[0].click();", driver.findElement(by));
		}
	}

	public void checkToCheckbox(By by) {
		if (!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}

	public void uncheckToCheckboxByJS(By by) {
		if (driver.findElement(by).isSelected()) {
			js.executeScript("arguments[0].click();", driver.findElement(by));
		}
	}

	public void uncheckToCheckbox(By by) {
		if (driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
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