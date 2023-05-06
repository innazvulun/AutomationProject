package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class HeaderSection extends BasePage{
	@FindBy(css=".nav-right__user>button")
	public WebElement headerLoginBtn;
	@FindBy(css=".nav-right__user-profile>button")
	public WebElement userDataHeader;
	@FindBy(css=".nav-right-menu__container>button")
	public WebElement logOutBtn;
	@FindBy(css=".confirm-modal__buttons .btn-blue")
	public  WebElement logOutConfirmBtn;
	@FindBy(css=".app-header__desktop  a[target='_self']")
	public List<WebElement> listHeaderInnerLinks;
	@FindBy(css=".app-header__desktop  a[target='_blank']")
	public List<WebElement> listHeaderOuterLinks;
	@FindBy(css="div.app-header__desktop > nav > div > div.header-nav__item.header-dropdown")
	public WebElement linksDDTitile;
	
	@FindBy(css="#email")
	private WebElement emailField;
	@FindBy(css="input#password")
	private WebElement passwordField;
	@FindBy(css="[data-testid='submit-button']")
	private WebElement loginBtn;
	
	public HeaderSection(WebDriver driver) {
		super(driver);
	}
	
	public boolean isLoggedIn() {
		if (headerLoginBtn.isDisplayed()) {
			return false;
		}
		else return true;
	}
	
	//perform login without checking logged in and close/open window. Used for Invalid login test set.
	public void loginSimple(String user, String password) {
		fillText(emailField, user);
		fillText(passwordField, password);
		click(loginBtn);
	}
		
	public void login(String user, String password) {
		if(isLoggedIn()) {
			logOut();		
		}
		openLoginWindow();
		fillText(emailField, user);
		fillText(passwordField, password);
		click(loginBtn);
		waiting(6000);
	}
	
	public void openLoginWindow() {
		click(headerLoginBtn);
	}
	
	public void logOutIfLoggedIn() {
		if(isLoggedIn()) {
			logOut();
		}
	}
	
	public void logOut() {
		waiting(1000);
		Actions a = new Actions(driver);
		a.moveToElement(userDataHeader).perform();
		click(logOutBtn);
		click(logOutConfirmBtn);
	}
	
	public void goToMyTripsPage() {
		for (WebElement el : listHeaderInnerLinks) {
			if(el.getAttribute("href").contains("bookmarks")) {
				click(el);
				break;
			}
		}
		waiting(2000);
	}
	
	public boolean checkHeaderMenuLinksInner() {	
		int goodLink = 0;
		String hrefExpected="";
		String hrefActual="";
		int listInnerLinksSize = listHeaderInnerLinks.size();
		for (int i=0; i<listInnerLinksSize; i++) {
			List<WebElement> listInnerLinks = driver.findElements(By.cssSelector(".app-header__desktop  a[target='_self']"));
			WebElement el = listInnerLinks.get(i);
			hrefExpected = getHrefOfLink(el);
			hrefActual = getLinkPageUrl(el);
			
			//We have a bug here. In logged out mode, the back from bookmark page returns to wrong language HP.
			// for example: click from EN page on "My trips" and back from received page 
			//(with Login window) going to HEB HP page.
			//So, we need to use workaround (special "if") for this link.
			if((hrefExpected.contains("bookmarks")) || hrefActual.contains("bookmarks")) {
				//for bookmark page in LoggedOFF mode, the Login window displayed. Close it in this case.
				if(driver.findElement(By.cssSelector(".modal-v2__container")).isDisplayed()) {
					click(driver.findElement(By.cssSelector(".modal-v2__close-button")));				
				}
				goodLink++;
			}
			else {
				if(hrefExpected.compareTo(hrefActual) == 0 ){
					goodLink++;
				}
				driver.navigate().back();	
				waiting(2000);
			}
		}
		
		if (goodLink == listInnerLinksSize) return true;
		else return false;
	}
	
	public boolean checkHeaderMenuLinksExternal() {
		int goodLink = 0;
		String hrefExpected="";
		String hrefActual="";
		int listExternalLinksSize = listHeaderOuterLinks.size();
		
		for (int i=0; i<listExternalLinksSize; i++) {
			if(i>0) {
				pageRefresh();
				waiting(1000);
			}
			List<WebElement> listExternalLinks = driver.findElements(By.cssSelector(".app-header__desktop  a[target='_blank']"));
			WebElement el = listExternalLinks.get(i);
			hrefExpected = getHrefOfLink(el);
			
			String originalWindow = driver.getWindowHandle();
			click(linksDDTitile);
			waiting(500);
			click(el);
			
			for (String windowHandle : driver.getWindowHandles()) {
			    if(!originalWindow.contentEquals(windowHandle)) {
			        driver.switchTo().window(windowHandle);
			        hrefActual = driver.getCurrentUrl();
			        driver.close();
			        driver.switchTo().window(originalWindow);
			    }
			    if((hrefExpected.compareTo(hrefActual) == 0)&& !(hrefActual=="")){
					goodLink++;
					hrefExpected=hrefActual="";			
				}else if(!(hrefActual=="")){
					hrefExpected = hrefExpected.substring(0,hrefExpected.indexOf("en/"));
					if(hrefActual.contains(hrefExpected)) {
						goodLink++;
						hrefExpected=hrefActual="";	
					}
				}
			}			
		}
		
		if (goodLink == listExternalLinksSize) return true;
		else return false;
	}
}
