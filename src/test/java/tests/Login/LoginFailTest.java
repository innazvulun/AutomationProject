package tests.Login;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import tests.BaseTest;

public class LoginFailTest extends BaseTest{

	@Test(description = "Check login failed with not exists user")
	public void tc01_loginFail_not_existsUser() {		
		LoginPage lp = new LoginPage(driver);
		lp.login("standard_user@abc.com", "123");
		String errorMsg = lp.getErrorMsg();
		Assert.assertEquals(errorMsg, "Unable to log in with provided credentials.");
	}
	
	@Test(description = "Check login failed with wrong email", dataProvider = "getDataEmail")
	public void tc02_loginFail_Wrong_Email(String emailData, String passwData, String errorMsg) {		
		LoginPage lp = new LoginPage(driver);
		lp.loginSimple(emailData, passwData);
		String errorMsgActual = lp.getEmailFieldErrorMsg();
		Assert.assertEquals(errorMsgActual, errorMsg);
	}
	
	@DataProvider
	public Object[][] getDataEmail(){
		Object[][] dataEmail = {
				{"inko.loga123456gmail.com", "Aa123445678","Please enter a valid email address"},
				{"inko.loga123456@@gmail.com", "Aa123445678","Please enter a valid email address"},
				{"inko.loga123456@gmail", "Aa123445678","Please enter a valid email address"},
				{"inko.loga123456@gmail#com", "Aa123445678","Please enter a valid email address"},
				{"", "123445678","Required"}
		};
		return dataEmail;
	}
	
	@Test(description = "Check login failed with wrong password", dataProvider = "getDataPassword")
	public void tc03_loginFail_Wrong_Password(String emailData2, String passwData2) {		
		LoginPage lp = new LoginPage(driver);
		lp.loginSimple(emailData2, passwData2);
		lp.waiting(800);
		String errorMsg = lp.getErrorMsg();
		Assert.assertEquals(errorMsg, "Unable to log in with provided credentials.");
	}
	
	@DataProvider
	public Object[][] getDataPassword(){
		Object[][] dataPassw = {
				{"inko.loga123456@gmail.com", "Aa12344",},
				{"inko.loga123456@gmail.com", "sdfd",},
				{"inko.loga123456@gmail.com", "00000",},
				{"inko.loga123456@gmail.com", "1",}
		};
		return dataPassw;
	}
	
	@Test(description = "Check login failed with empty Password field")
	public void tc05_loginFail_empty_Password_field() {		
		LoginPage lp = new LoginPage(driver);
		lp.loginSimple("inko.loga123456@gmail.com", "");
		String errorMsgPassw = lp.getPasswordFieldErrorMsg();
		Assert.assertEquals(errorMsgPassw, "Required");
	}
}
