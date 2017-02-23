package AutomationFramework;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class QuriEx {


	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.gecko.driver", "C:\\Users\\ajain\\geckodriver-v0.14.0-win64\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

		driver.get("http://www.quri.com");
		WebElement header=	driver.findElement(By.xpath("//img[@src='http://quri.com/wp-content/themes/quri/includes/images/logo.png']"));
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(header.getAttribute("src"));
			HttpResponse response = client.execute(request);
			// 
			if (response.getStatusLine().getStatusCode() != 200){
				System.out.println("Image is broken");
			}
			else{
				System.out.println("Image has been loaded");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread.sleep(10000);
		driver.findElement(By.id("menu-item-27")).click();

		String parentWindow = driver.getWindowHandle();
		Set<String> handles =  driver.getWindowHandles();
		for(String windowHandle  : handles)
		{
			if(!windowHandle.equals(parentWindow))
			{
				driver.switchTo().window(windowHandle);
				WebElement newBrowserEle = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.presenceOfElementLocated(By
								.id("login-header")));

				Thread.sleep(5000);
				Assert.assertEquals("https://customer.quri.com/dashboard/login", "https://customer.quri.com/dashboard/login", "Correct Page URL has been loaded");

				driver.close(); 
				Thread.sleep(5000);
				driver.switchTo().window(parentWindow); 
			}
		}
		driver.findElement(By.cssSelector("a[href*='request-demo']")).click();
		//Thread.sleep(5000);
		driver.findElement(By.id("FirstName")).sendKeys("QA TEST");
		//Thread.sleep(5000);
		driver.findElement(By.id("LastName")).sendKeys("QA TEST");
		//Thread.sleep(5000);
		driver.findElement(By.id("Company")).sendKeys("QA TEST");
		//Thread.sleep(5000);
		driver.findElement(By.id("Email")).sendKeys("qa@quri.com");
		//Thread.sleep(5000);
		driver.findElement(By.id("Phone")).sendKeys("1234567890");

		driver.findElement(By.className("mktoButton")).click();
		driver.findElement(By.xpath(".//*[@id='contact']/div/header/h1"));

	}

}
