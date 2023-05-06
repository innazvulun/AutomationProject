package tests.Login;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import tests.BaseTest;

public class LoginWindowLinks extends BaseTest{
	@Test (description = "Check for Sign Up form display by click on signUp link.")
	public void tc01_signUpForm_Display() {	
		LoginPage lp = new LoginPage(driver);	
		boolean formDisplayed = lp.isSignUpForm();
		Assert.assertEquals(formDisplayed,true);
	}
	
	@Test (description = "Check for Forgot Password page display by click on Forgot Password link.")
	public void tc02_forgotPasswordPage_Display() {	
		LoginPage lp = new LoginPage(driver);	
		String currentURL = lp.getForgotPasswordLinkURL();
		Assert.assertEquals(currentURL,"https://www.shichor.co.il/en/auth/forgot-password");
	}
}
