package tests.Login;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import tests.BaseTest;


public class LoginTest extends BaseTest{
	
	@Test(description = "Check login with valid user data.")
	public void tc01_validLogin() {	
		LoginPage lp = new LoginPage(driver);
		lp.login("inko.loga123456@gmail.com", "Aa12345678");	
		String nameActual = lp.getUserName();
		Assert.assertEquals(nameActual, "Inko");
	}
	
	//available to create more valid tests on real environments. 
	//For example: several variants of correct email and several variants of valid password ( min/max characters...).
	
}

