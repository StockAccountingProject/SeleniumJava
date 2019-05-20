package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utilities.ExcelfileUtil;
import commonfunctionlibrary.Functionlibrary;

public class DriverScript
{
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	public void startTest() throws Throwable
	{
		ExcelfileUtil xl =new ExcelfileUtil();
		
		for (int i = 1; i <= xl.rowCount("MasterTestCases"); i++) 
		{
			
		 if(xl.getData("MasterTestCases",i,2).equalsIgnoreCase("Y"))
				 {
			 
			 String moduleStatus = "";
			 String TCModule = xl.getData("MasterTestCases", i, 1);
			 report=new ExtentReports("C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\Reports1\\"+TCModule+Functionlibrary.generateDate()+".html");
			 logger=report.startTest(TCModule);	 
					 int rowcount= xl.rowCount(TCModule);
					 for (int j = 1; j <= rowcount ;j++) 
					 {
						String Description =xl.getData(TCModule, j, 0);
						String Object_Type =xl.getData(TCModule, j, 1);
						String Locator_Type =xl.getData(TCModule, j, 2);
						String Locator_Value =xl.getData(TCModule, j, 3);
						String Test_Data =xl.getData(TCModule, j, 4);
						try
						{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver=Functionlibrary.startBrowser(driver);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("openApplication"))
						{
							Functionlibrary.openApplication(driver);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							Functionlibrary.clickAction(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
						
							Functionlibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							Functionlibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO,Description);
						}
						
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							Functionlibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						
						if(Object_Type.equalsIgnoreCase("titleValidation"))
						{
							Functionlibrary.titleValidation(driver, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if (Object_Type.equalsIgnoreCase("captureData")) 
						{
							Functionlibrary.captureData(driver, Locator_Type, Locator_Value);	
							logger.log(LogStatus.INFO,Description);
						}
						if (Object_Type.equalsIgnoreCase("tableValidation"))
						{
							Functionlibrary.tableValidation(driver, Test_Data);	
							logger.log(LogStatus.INFO,Description);
						}
						if (Object_Type.equalsIgnoreCase("mouseClick")) 
						{
							Functionlibrary.mouseClick(driver);
							logger.log(LogStatus.INFO,Description);
						}
						if (Object_Type.equalsIgnoreCase("tableValidation1")) 
						{
							Functionlibrary.tableValidation1(driver, Test_Data);
							logger.log(LogStatus.INFO,Description);
							
						}
						xl.setData(TCModule, j, 5, "Pass");
						moduleStatus="True";
						logger.log(LogStatus.PASS,Description);
						}catch(Exception e)
						{
							xl.setData(TCModule, j, 5, "Fail");
							moduleStatus="False";
							logger.log(LogStatus.FAIL,Description);
							File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
							FileUtils.copyFile(srcFile, new File("C:\\Users\\ravikiran.b\\ravikiran\\StockAccounting\\Screenshots\\"+Description+Functionlibrary.generateDate()+".png"));
							break;
						}
					}
					
					 if (moduleStatus.equalsIgnoreCase("True"))
					 {
						xl.setData("MasterTestCases", i, 3, "Pass");
					 }
					 else if (moduleStatus.equalsIgnoreCase("False")) 
					 {
						 xl.setData("MasterTestCases", i, 3, "Fail");
					 }
					 //report.endTest(logger);
					// report.flush();
				 }
		 else
		 {
			 xl.setData("MasterTestCases", i, 3, "Not Executed");
		 }
		}
		
		report.flush();
	}
		
}
