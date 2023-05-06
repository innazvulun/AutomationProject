package tests.CreateATrip;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CreateATripPage;
import tests.BaseTest;

public class CreateATripTest extends BaseTest{
	@Test(description = "Check Create a Trip page title.")
	public void tc01_CreateATripPageTitle() {	
		CreateATripPage create = new CreateATripPage(driver);
		create.goToCreateATripPage();
		String createPageTitle = create.getStepTitle();
		Assert.assertEquals(createPageTitle, "So, what’s next?");
	}
}
