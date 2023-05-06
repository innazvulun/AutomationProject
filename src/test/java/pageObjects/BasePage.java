package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
	WebDriver driver;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//This function wait for required banner or Promo element  for close it.
	//For reduce waiting time, the function checked if banner or promo element displayed each 1sec and close it when found.
	//Maximum time for waiting is value, defined in waitTime parameter.
	public void closePopupsBanners(String bannerToclose, String closeBannerElem, int waitTime) {
		for(int i=0; i<waitTime; i++) {
			waiting(1000);
			int size= driver.findElements(By.cssSelector(bannerToclose)).size();
			if (size!=0) {
				driver.findElement(By.cssSelector(closeBannerElem)).click();
				break;
			}
		}
	}
	
	public void fillText(WebElement el, String text) {
		highlightElement(el, "blue", "yellow");
		//el.clear();  
		//Sometimes, the clear() method is not working, so used «Ctrl+A+Delete» keys combination instead.
		el.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));		
		el.sendKeys(text);
	}
	
	public void click(WebElement el) {
		waiting(500);
		highlightElement(el, "blue", "yellow");
		el.click();
	}
	
	public String getText(WebElement el) {
		highlightElement(el, "orange", "orange");
		return el.getText();
	}
	
	public String getPageTitle(WebElement el) {
		waiting(2000);
		return getText(el);
	}
	
	//Select
	public void selectByValue(WebElement el, String text){
		highlightElement(el, "blue", "yellow");
		Select s = new Select(el);
		s.selectByValue(text);
	}
	
	public String getLinkPageUrl(WebElement el) {
		click(el);		
		waiting(2000);
		return driver.getCurrentUrl();
	}
	
	public String getCurrentPageUrl() {	
		waiting(1000);
		return driver.getCurrentUrl();
	}
	
	public String getCurrentPageH1TItle() {	
		waiting(1000);
		return driver.findElement(By.cssSelector("h1")).getText();
	}
	
	public String getHrefOfLink(WebElement el){
		String href = el.getAttribute("href");
		return href;
	}
	
	public void waiting(long mill) {
		try {
			Thread.sleep(mill);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void pageRefresh() {
		driver.navigate().refresh();
	}
	
	public void back() {
		driver.navigate().back();
	}
	
	public void scrollTo(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	public void clickout() {
		driver.findElement(By.xpath("//html")).click();
	}
		
	/*
	 * Call this method with your element and a color like (red,green,orange etc...)
	 */
	protected void highlightElement(WebElement element, String color, String bgColor) {
		//keep the old style to change it back
		String originalStyle = element.getAttribute("style");
		String newStyle = "background-color: " + bgColor + ";border: 1px solid " + color + ";" + originalStyle;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Change the style 
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '" + newStyle + "');},0);",
				element);

		// Change the style back after few milliseconds
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
				+ originalStyle + "');},400);", element);

	}
}
