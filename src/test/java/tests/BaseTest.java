package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;


import pageObjects.BasePage;

public class BaseTest {
	public WebDriver driver;
	public String promoBanner = ".hanukkah-popup__popup";
	public String promoBannerClose = "use[href='#close']";
	public String cookiesBanner = ".cookies-policy-banner";
	public String cookiesBannerBtn = ".cookies-policy-banner>button";
	
//	public void deleteScreenShots() {
//		try {
//			File dirFile = new File("./ScreenShots");
//			FileUtils.deleteDirectory(dirFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	@BeforeSuite
	public void initialization() {
		try {
			File dirFile = new File("./ScreenShots");
			FileUtils.deleteDirectory(dirFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public void setup() {
		ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.get("https://www.shichor.co.il/en");
		
		BasePage bp = new BasePage(driver);	
		bp.closePopupsBanners(promoBanner, promoBannerClose, 10);
		bp.closePopupsBanners(cookiesBanner, cookiesBannerBtn, 5);		
	}	
		
	@AfterClass
	public void treatDown() {
		driver.quit();
	}

	// This method will run after each test,
	// it will take screen shot only for tests that failed
	@AfterMethod
	public void failedTest(ITestResult result) {
	  //check if the test failed
		if (result.getStatus() == ITestResult.FAILURE ){
			TakesScreenshot ts = (TakesScreenshot)driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("./ScreenShots/"+ result.getName() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//result.getname() method will give you current test case name. 
			//./ScreenShots/ tell you that, in your current directory, create folder ScreenShots. dot represents current directory
		}
	}
}
