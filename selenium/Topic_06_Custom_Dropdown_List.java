
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

public class Topic_06_Custom_Dropdown_List {
	WebDriver driver;
	WebDriverWait explicitwait;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		explicitwait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdownList("//span[@id='number-button']", "//ul[@id='number-menu']//div", "//span[@id='number-button']//span[@class='ui-selectmenu-text']", "8");
		sleepInSecond(2);
		selectItemInCustomDropdownList("//span[@id='number-button']", "//ul[@id='number-menu']//div", "//span[@id='number-button']//span[@class='ui-selectmenu-text']", "2");
		sleepInSecond(2);
		selectItemInCustomDropdownList("//span[@id='number-button']", "//ul[@id='number-menu']//div", "//span[@id='number-button']//span[@class='ui-selectmenu-text']", "16");
		sleepInSecond(2);
	}

	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItemInCustomDropdownList("//div[@role='listbox']", "//div[contains(@class, 'visible menu transition')]//span", "//div[@role='listbox']/div[not(contains(@class,'menu transition'))]", "Elliot Fu");
		sleepInSecond(2);
		selectItemInCustomDropdownList("//div[@role='listbox']", "//div[contains(@class, 'visible menu transition')]//span", "//div[@role='listbox']/div[not(contains(@class,'menu transition'))]", "Matt");
		sleepInSecond(2);
		selectItemInCustomDropdownList("//div[@role='listbox']", "//div[contains(@class, 'visible menu transition')]//span", "//div[@role='listbox']/div[not(contains(@class,'menu transition'))]", "Stevie Feliciano");
		sleepInSecond(2);
	}

	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdownList("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "//li[@class='dropdown-toggle']", "Third Option");
		sleepInSecond(2);
		selectItemInCustomDropdownList("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "//li[@class='dropdown-toggle']", "Second Option");
		sleepInSecond(2);
		selectItemInCustomDropdownList("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "//li[@class='dropdown-toggle']", "Third Option");
		sleepInSecond(2);

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void selectItemInCustomDropdownList(String parentXpath, String childXpath, String actualitemXpath, String expectedItemText) {
//		1 - Click to custom dropdown
		driver.findElement(By.xpath(parentXpath)).click();

//		2 - Wait for allElements isPresent
		explicitwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

		List<WebElement> items = driver.findElements(By.xpath(childXpath));
		for (WebElement item : items) {
//		3 - Click to Element
			if (item.getText().equals(expectedItemText)) {
				item.click();
				break;
			}
		}

//		4 - Verify
		Assert.assertEquals(driver.findElement(By.xpath(actualitemXpath)).getText(), expectedItemText);
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