package trifacta;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ProductInfo {

	public static WebDriver driver;

	public static void captureScreenshot(String filename) throws IOException {
		String fileName = filename + ".jpg";
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(".//screenshot//" + fileName));
	}

	public static void main(String[] args) throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.trifacta.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		captureScreenshot("Website");

		Set<String> windowId = driver.getWindowHandles();
		Iterator<String> windowIterator = windowId.iterator();
		String cloudWindow = windowIterator.next();

		String loginLink = null;

		List<WebElement> loginButton = driver.findElements(By.xpath("//*[@id=\"menu-item-48574\"]/a"));

		for (int i = 0; i < loginButton.size(); i++) {
			loginLink = loginButton.get(i).getAttribute("href");
		}

		// The login screen
		driver.get(loginLink);
		

		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div[2]/div/div/div[3]/div[1]/div/input")));
		captureScreenshot("Login");

		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div[3]/div[1]/div/input"))
				.sendKeys("sun4mugam@gmail.com");
		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div[3]/div[2]/div/input"))
				.sendKeys("Test.12345");
		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div[4]/div")).click();
		
		// Cloud screen

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 

		driver.findElement(
		By.xpath("//*[@id=\"page-container\"]/div[3]/div[3]/div/div/div/div/div[2]/div[5]/div[4]/a/div"))
		.click();
		captureScreenshot("Cloud screen"); 

		// Community screen

		windowId = driver.getWindowHandles();
		windowIterator = windowId.iterator();
		windowIterator.next();
		String communityWindow = windowIterator.next();
		driver.switchTo().window(communityWindow);

		wait = new WebDriverWait(driver,50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"19:98;a\"]/div/div/button")));
		captureScreenshot("Community screen");

		driver.findElement(By.xpath("//*[@id=\"19:98;a\"]/div/div/button")).click();
		driver.findElement(By.xpath(
				"/html/body/div[3]/div[1]/div/div/div/div[3]/div[2]/div/div/nav/ul/li[4]/div/div[2]/div/ul/li[4]"))
				.click();

		// Product version screen
		windowId = driver.getWindowHandles();
		windowIterator = windowId.iterator();
		windowIterator.next();
		windowIterator.next();
		String productVersionWindow = windowIterator.next();
		driver.switchTo().window(productVersionWindow);

		captureScreenshot("Produce version");

		driver.switchTo().window(productVersionWindow).close();
		driver.switchTo().window(communityWindow).close();
		driver.switchTo().window(cloudWindow);

		driver.findElement(By.xpath("//*[@id=\"page-container\"]/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div"))
				.click();
		captureScreenshot("Logout");
		driver.findElement(By.xpath("/html/body/div[7]/div[6]/div")).click();

		driver.close();

	}

}
