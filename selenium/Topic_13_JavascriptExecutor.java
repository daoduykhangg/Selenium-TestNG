
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_JavascriptExecutor {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_Id() {
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(1);
		String domain = (String) executeForBrowser("return document.domain;");
		Assert.assertEquals(domain, "live.techpanda.org");

		String url = (String) executeForBrowser("return document.URL;");
		Assert.assertEquals(url, "http://live.techpanda.org/");

		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(2);

		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div//button");
		sleepInSecond(2);

		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Samsung Galaxy was added to your shopping cart.");

		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(2);

		String title = (String) executeForBrowser("return document.title;");
		Assert.assertEquals(title, "Customer Service");

		scrollToElementOnTop("//input[@id='newsletter']");
		sendkeyToElementByJS("//input[@id='newsletter']", "automationfc@gmail.vn");
		sleepInSecond(1);
		clickToElementByJS("//button[@title='Subscribe']");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for your subscription.");

	}

	@Test
	public void TC_02_Verify_HTML5_Validation_Message() {
		navigateToUrlByJS("https://automationfc.github.io/html5/index.html");
		sleepInSecond(1);

		clickToElementByJS("//input[@class='btn']");
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='fname']"), "Please fill out this field.");

		sendkeyToElementByJS("//input[@id='fname']", "Khang Dao");
		sleepInSecond(1);
		clickToElementByJS("//input[@class='btn']");
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='pass']"), "Please fill out this field.");

		sendkeyToElementByJS("//input[@id='pass']", "123456");
		sleepInSecond(1);
//		clickToElementByJS("//input[@class='btn']");
//		sleepInSecond(1);
//		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please fill out this field.");

		sendkeyToElementByJS("//input[@id='em']", "123@!@#");
		sleepInSecond(1);
		clickToElementByJS("//input[@class='btn']");
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please enter an email address.");

		driver.findElement(By.xpath("//input[@id='em']")).clear();
		sleepInSecond(1);
		driver.findElement(By.xpath("//input[@id='em']")).sendKeys("123@456");
		sleepInSecond(1);
		clickToElementByJS("//input[@class='btn']");
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please match the requested format.");

		driver.findElement(By.xpath("//input[@id='em']")).clear();
		sleepInSecond(1);
		driver.findElement(By.xpath("//input[@id='em']")).sendKeys("daoduykhang@gmail.com");
		sleepInSecond(1);
		clickToElementByJS("//input[@class='btn']");
		sleepInSecond(1);
		Assert.assertEquals(getElementValidationMessage("//select"), "Please select an item in the list.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
		if (status) {
			return true;
		}
		return false;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
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