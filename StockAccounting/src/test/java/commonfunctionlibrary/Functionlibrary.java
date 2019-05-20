package commonfunctionlibrary;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;


public class Functionlibrary
{
	
	public static WebDriver startBrowser(WebDriver driver) throws Throwable, Throwable
	{
		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			driver=new FirefoxDriver();
		}else
			if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
			{
			   System.setProperty("webdriver.chrome.driver", "C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\EXEFiles\\chromedriver.exe");
			   driver=new ChromeDriver();
		    }else 
		    	if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("IE"))
		    	{
		    		 System.setProperty("webdriver.ie.driver", "C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\EXEFiles\\IEDriverServer.exe");
					   driver=new InternetExplorerDriver();
			    }
				
		return driver;
				
	}
	
	public static void openApplication(WebDriver driver) throws Throwable, Throwable
	{
		//driver.manage().window().maximize();
		driver.get(PropertyFileUtil.getValueForKey("URL"));
	}
	
	public static void clickAction(WebDriver driver,String locatorType,String locatorValue)
	{
		if (locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).click();
		}else 
			if (locatorType.equalsIgnoreCase("name")) 
			{
				driver.findElement(By.name(locatorValue)).click();
			}
		else 
			if (locatorType.equalsIgnoreCase("xpath")) 
			{
				driver.findElement(By.xpath(locatorValue)).click();
		    }
	}
	
	public static void typeAction(WebDriver driver,String locatorType,String locatorValue,String data) 
	{
	
		if (locatorType.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(locatorValue)).clear();
			driver.findElement(By.id(locatorValue)).sendKeys(data);
		}else 
			if (locatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(locatorValue)).clear();
				driver.findElement(By.name(locatorValue)).sendKeys(data);
			}else 
				if (locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorValue)).clear();
					driver.findElement(By.xpath(locatorValue)).sendKeys(data);
				}
	}
	
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	
	public static void waitForElement(WebDriver driver,String locatorType,String locatorValue,String waittime)
	{
		WebDriverWait myWait=new WebDriverWait(driver, Integer.parseInt(waittime));
		if (locatorType.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		}else
			if (locatorType.equalsIgnoreCase("name"))
			{
				myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			}else 
				if (locatorType.equalsIgnoreCase("xpath"))
				{
					myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				}
	}
	
	public static void titleValidation(WebDriver driver,String exp_data)
	{
		String act_data=driver.getTitle();
		if ((exp_data).contains(act_data))
		{
			
			System.out.println("Title Matched");
		}else
		{
			
			System.out.println("Title Mismatched");
		}
	}
	
	public static String generateDate()
	{
		Date dt=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
		return sdf.format(dt);
		
	}

	public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable
	{
		String data="";
		if (locatortype.equalsIgnoreCase("id"))
		{
			data=driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}else
			if (locatortype.equalsIgnoreCase("xpath"))
			{
			
				data=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
			}
		
		FileWriter fw = new FileWriter("C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\CaptureData\\Data.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(data);
		bw.flush();
		bw.close();
	}
	
	public static void tableValidation(WebDriver driver,String column) throws Throwable
	{
		FileReader fr = new FileReader("C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\CaptureData\\Data.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		
		int column1 = Integer.parseInt(column);
		if (driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).isDisplayed()) 
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).sendKeys(exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.btn"))).click();
		}else
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.panel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).sendKeys(exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.btn"))).click();
			//driver.findElement(By.xpath("//*[@id='btnsubmit']")).click();
		Thread.sleep(2000);
		}
		
		WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("webtable.path")));
		List<WebElement> rows=table.findElements(By.tagName("tr"));
		
		for (int i=1; i<=rows.size(); i++)
		{
			String act_data=driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div/div//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column1+"]/div/span/span")).getText();
			System.out.println(act_data);
			Assert.assertEquals(act_data, exp_data);
			break;
		}
		
	}
	
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		
		WebElement stockItem=driver.findElement(By.xpath("//*[@id='mi_a_stock_items']/a"));
		Thread.sleep(2000);
		WebElement stockCategory=driver.findElement(By.xpath("//*[@id='mi_a_stock_categories']/a"));
		Thread.sleep(2000);
		Actions act=new Actions(driver);
		act.moveToElement(stockItem).build().perform();
		act.moveToElement(stockCategory).click().build().perform();
				
	}
	
	public static void tableValidation1(WebDriver driver,String exp_data) throws Throwable
	{
		
		if (driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).isDisplayed()) 
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).sendKeys(exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.btn1"))).click();
		}else
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.panel1"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).sendKeys(exp_data);
			//driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.btn1"))).click();
			driver.findElement(By.xpath("//*[@id='btnsubmit']")).click();
		Thread.sleep(2000);
		}
		    
		WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("webtable.path1")));
		List<WebElement> rows=table.findElements(By.tagName("tr"));
		
		for (int i=1; i<=rows.size(); i++)
		{
			String act_data=driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div/div//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
			System.out.println(act_data);
			Assert.assertEquals(act_data, exp_data);
			break;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*WebDriver driver;
	String res;
	
	public String launchApp(String url)
	{
		driver=new FirefoxDriver();
		driver.get(url);
		driver.manage().window().maximize();
		if (driver.findElement(By.id("btnsubmit")).isDisplayed())
		{
			res="pass";
		}else
		{
			res="fail";
		}
		return res;
	}
	
	
	public String login(String username,String password) throws Throwable
	{
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("btnsubmit")).click();
		Thread.sleep(3000);
		if (driver.findElement(By.id("logout")).isDisplayed())
		{
			res="pass";
			
		}else
		{
			res="fail";
			
		}
		return res;
		
	}
	
	public String createSuppliers(String spname,String add) throws Throwable
	{
		
		driver.findElement(By.xpath("//*[@id='mi_a_suppliers']/a")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/div[1]/div[1]/div[1]/div/a/span")).click();
		Thread.sleep(5000);
		String exp_data=driver.findElement(By.xpath("//*[@id='x_Supplier_Number']")).getAttribute("value");
		driver.findElement(By.id("x_Supplier_Name")).sendKeys(spname);
		driver.findElement(By.id("x_Address")).sendKeys(add);
		driver.findElement(By.id("x_City")).sendKeys("Hyderabad");
		Thread.sleep(3000);
		driver.findElement(By.id("x_Country")).sendKeys("India");
		driver.findElement(By.id("x_Contact_Person")).sendKeys("Praveen");
		driver.findElement(By.id("x_Phone_Number")).sendKeys("9834567890");
		driver.findElement(By.id("x__Email")).sendKeys("ravi123@gmail.com");
		Thread.sleep(3000);
		driver.findElement(By.id("x_Mobile_Number")).sendKeys("9848123123");
		driver.findElement(By.id("x_Notes")).sendKeys("Done");
		driver.findElement(By.id("btnAction")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[text()='OK!']")).click();
		driver.findElement(By.xpath("//*[text()='OK']")).click();
		
		driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[2]/div[2]/div/button/span")).click();
		driver.findElement(By.id("psearch")).sendKeys(exp_data);
		driver.findElement(By.id("btnsubmit")).click();
		return exp_data;
		
		String act_data=driver.findElement(By.xpath(".//*[@id='ewContentColumn']/div[3]/form/div/div//table[@class='tableewTable']/tbody/tr[1]/td[6]/div/spam/span")).getText();
		if (exp_data.equals(act_data))
        {
			res="pass";
		}
		else
		{
			res="fail";
		}
		return res;
	
	}
	
	public String logout() throws Throwable
	{
		driver.findElement(By.id("logout")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("/html/body/div[17]/div[2]/div/div[4]/div[2]/button[1]")).click();
		
		if (driver.findElement(By.id("btnsubmit")).isDisplayed())
		{
			res="pass";
			
		}else
		{
			res="fail";
			
		}
		return res;
	}
	
    public void close()
    {
    	driver.close();
    }*/
	
}
