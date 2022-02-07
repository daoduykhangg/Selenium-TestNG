
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Default_Dropdown_List {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Dropdown_List() {
		driver.get("https://www.rode.com/wheretobuy");

		Select select = new Select(driver.findElement(By.id("where_country")));

		Assert.assertFalse(select.isMultiple());

		select.selectByVisibleText("Vietnam");

		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");

		driver.findElement(By.name("search_loc_submit")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='result_count']/span")).getText(), "32");

		List<WebElement> elements = driver.findElements(By.xpath("//div[@class='result_item']//div[@class='store_name']"));
		for (WebElement element : elements) {
			System.out.println(element.getText());
		}
	}

	@Test
	public void TC_02_Dropdown_List() {

		String firstname = "automation";
		String lastname = "FC";
		String day = "21";
		String month = "August";
		String year = "1996";
		String email = "automationfc" + randomNumber() + "@gmail.com";
		String password = "123456";

		driver.get("https://demo.nopcommerce.com/");
		driver.findElement(By.xpath("//a[text()='Register']")).click();
		driver.findElement(By.name("FirstName")).sendKeys(firstname);
		driver.findElement(By.name("LastName")).sendKeys(lastname);

		Select dayDropdownList = new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(dayDropdownList.getOptions().size(), 32);
		dayDropdownList.selectByVisibleText(day);

		Select monthDropdownList = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(monthDropdownList.getOptions().size(), 13);
		monthDropdownList.selectByVisibleText(month);

		Select yearDropdownList = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(yearDropdownList.getOptions().size(), 112);
		yearDropdownList.selectByVisibleText(year);

		driver.findElement(By.name("Email")).sendKeys(email);
		driver.findElement(By.name("Password")).sendKeys(password);
		driver.findElement(By.name("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.name("register-button")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='result']")).getText(), "Your registration completed");
		driver.findElement(By.xpath("//a[@class='ico-account']")).click();
		
		dayDropdownList = new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(dayDropdownList.getFirstSelectedOption().getText(), day);

		monthDropdownList = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(monthDropdownList.getFirstSelectedOption().getText(), month);
		
		yearDropdownList = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(yearDropdownList.getFirstSelectedOption().getText(), year);
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