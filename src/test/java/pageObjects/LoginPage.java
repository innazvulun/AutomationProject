package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends HeaderSection{
//	@FindBy(css="#email")
//	private WebElement emailField;
//	@FindBy(css="input#password")
//	private WebElement passwordField;
//	@FindBy(css="[data-testid='submit-button']")
//	private WebElement loginBtn;
	@FindBy(css=".sign-in-form__footer>a")
	private WebElement forgotPasswordLink;	
	@FindBy(css=".modal-v2__close-button")
	private WebElement closeLoginWindowBtn;
	@FindBy(css=".form-error[data-fieldname='non_field_errors']")
	private WebElement notExistsUserError;
	@FindBy(css="div.sign-in-form__fields > div:nth-child(1) > div.form-error-message")
	private WebElement emailFieldError;
	@FindBy(css=".input.input--password > .form-error-message")
	private WebElement passwordFieldError;
	
	@FindBy(css=".sign-in-form__signup-button>a")
	private WebElement signUpLink;
	@FindBy(css="form.sign-up-form")
	private WebElement signUpForm;
	@FindBy(css=".social-login__buttons > button:nth-child(1)")
	private WebElement facebookBtn;
	@FindBy(css=".social-login__buttons > button:nth-child(2)")
	private WebElement googleBtn;

	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
//	public void openLoginWindow() {
//		click(headerLoginBtn);
//	}
	
	public void closeLoginWindow() {
		click(closeLoginWindowBtn);
	}
	
//	public void login(String user, String password) {
//		if(isLoggedIn()) {
//			logOut();		
//		}
//		openLoginWindow();
//		fillText(emailField, user);
//		fillText(passwordField, password);
//		click(loginBtn);
//		waiting(6000);
//	}
	
	public void logOutAfterLogin(String user, String password) {
		if(!isLoggedIn()) {
			login(user, password);
		}		
		logOut();
	}
	
//	//perform login without checking logged in and close/open window. Used for Invalid login test set.
//	public void loginSimple(String user, String password) {
//		fillText(emailField, user);
//		fillText(passwordField, password);
//		click(loginBtn);
//	}
	
//	public void logOut() {
//		waiting(1000);
//		Actions a = new Actions(driver);
//		a.moveToElement(userDataHeader).perform();
//		click(logOutBtn);
//		click(logOutConfirmBtn);
//	}
	
	
	//Validation
	public String getUserName() {
		String name = getText(userDataHeader).replace("Hi, ", "");
		return name;
	}
	
	public String getErrorMsg() {
		return getText(notExistsUserError);
	}
	
	public String getEmailFieldErrorMsg() {
		return getText(emailFieldError);
	}
	
	public String getPasswordFieldErrorMsg() {
		return getText(passwordFieldError);
	}
	
	public boolean isSignUpForm() {	
		if(isLoggedIn()) {
			logOut();		
		}
		openLoginWindow();
		click(signUpLink);
		if(signUpForm.isDisplayed()) {
			pageRefresh();
			return true;
		}else return false;		
	}
	
	public String getForgotPasswordLinkURL() {
		if(isLoggedIn()) {
			logOut();		
		}
		openLoginWindow();
		return getLinkPageUrl(forgotPasswordLink);
	}
	
}
