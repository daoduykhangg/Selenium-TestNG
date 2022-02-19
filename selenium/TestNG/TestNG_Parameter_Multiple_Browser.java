package TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class TestNG_Parameter_Multiple_Browser {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    @Parameters({"Url", "Browser"})
    @BeforeClass
    public void beforeClass(String url, String browserName) {
        switch (browserName) {
            case "Firefox":
                System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "Edge":
                System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Browser not support");
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);

    }

    @Test(dataProvider = "userandpass")
    public void TC_01_Login(String username, String password) {
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@id='send2']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains(username));
        driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
        driver.findElement(By.xpath("//div[@id='header-account']//a[text()='Log Out']")).click();
    }

    @DataProvider(name = "userandpass")
    public Object[][] UsernameAndPassData() {
        return new Object[][]{
                {"selenium_11_01@gmail.com", "111111"}, {"selenium_11_02@gmail.com", "111111"}, {"selenium_11_03@gmail.com", "111111"}
        };
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        driver.quit();
    }
}
