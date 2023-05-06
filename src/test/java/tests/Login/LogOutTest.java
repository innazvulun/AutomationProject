package tests.Login;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import tests.BaseTest;

public class LogOutTest extends BaseTest{
	@Test
	public void tc01_logOut() {	
		LoginPage lp = new LoginPage(driver);
		lp.logOutAfterLogin("inko.loga123456@gmail.com", "Aa12345678");		
		boolean isLogged = lp.isLoggedIn();
		Assert.assertEquals(isLogged, false);
	}
}
