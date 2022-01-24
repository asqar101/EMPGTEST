package TestCases;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import java.nio.charset.Charset;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EMPGTest {

	public static Logger Logger;

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		RemoteWebDriver driver = new ChromeDriver();

		Logger = org.apache.log4j.Logger.getLogger("EMPGTEST");
		PropertyConfigurator.configure("log4j.properties");

		driver.get("https://www.olx.com.pk/mall/");
		driver.manage().window().setSize(new Dimension(1382, 744));
		Logger.info("OLX Site is Opened");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[normalize-space()='All categories']")).click();
		Logger.info("Navigated to All Categories");
		Thread.sleep(3000);
		driver.findElement(By.linkText("Mobiles & Tablets")).click();
		Logger.info("Navigated to Category: Mobiles & Tablets");
		Thread.sleep(3000);
		driver.findElement(By.linkText("Mobiles")).click();
		Logger.info("Navigated to Sub Category: Mobiles");
		Thread.sleep(3000);

		WebElement InputPriceMin = driver.findElement(By.xpath("//input[@id='minPrice']"));
		InputPriceMin.click();
		InputPriceMin.sendKeys("40,000");
		Logger.info("Minimum Price is Added");
		InputPriceMin.click();

		WebElement InputPriceMax = driver.findElement(By.xpath("//input[@id='maxPrice']"));
		InputPriceMax.click();
		InputPriceMax.clear();
		InputPriceMax.sendKeys("120,000");
		Logger.info("Maximum Price is Added");
		InputPriceMax.click();
		Thread.sleep(5000);
		{

			for (int i = 1; i < 11; i++)
				
			{
				Logger.info("Product No: " + i);
				WebElement OpenProduct = driver.findElement(By.cssSelector(
						"div[class='card-listing_gridCards__2Q-ZR layout-grid_layoutGrid__lo7JX'] a:nth-child(" + i
								+ ")"));
				OpenProduct.click();
				Thread.sleep(5000);

				boolean ProductCategory = driver.findElement(By.xpath("//a[normalize-space()='Mobiles']"))
						.isDisplayed();
				Thread.sleep(2000);
				
				if (ProductCategory == true) {
					Assert.assertTrue(true);
					Logger.info("Product Belongs to Mobile Category");
				} else {
					Logger.info("Product Belongs to other Category");
					Assert.assertTrue(false);
				}

				WebElement ProductPrice = driver
						.findElement(By.cssSelector("div[class='product-price_productPrice__1KrKT'] span"));
				Thread.sleep(2000);
				String PriceAmount = ProductPrice.getText();
				String PriceRange = new String(PriceAmount.getBytes(Charset.forName("utf-8"))).replace("₨ ", "")
						.replaceAll(",", "");
				Thread.sleep(2000);
				int PriceRangeInt = Integer.valueOf(PriceRange);

				if (PriceRangeInt >= 40000 && PriceRangeInt <= 120000)

				{
					Assert.assertTrue(true);
					Logger.info("Product Price PKR " + PriceRangeInt + " & it is Under Given Price Range");
				} else {
					Assert.assertTrue(false);
					Logger.info("Product Price is Not Under Given Range");
				}

				driver.navigate().back();
				Thread.sleep(3000);

			}

		}
	}
}
