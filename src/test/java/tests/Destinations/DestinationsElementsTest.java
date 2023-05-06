package tests.Destinations;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DestinationsPage;
import tests.BaseTest;

public class DestinationsElementsTest extends BaseTest{
	@Test(description = "Check Destinations Services section Links.")
	public void tc01_DestinationsServices() {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		boolean allLinks = des.checkDestServicesLinksExternal();
		Assert.assertEquals(allLinks, true, "Any of Services link is not valid.");
	}
	
	//We have a bug here: after going to 2nd page of destinations section, the click on 
	//any destination item and back, the browser returned to previous page, 
	//but not to 2nd page of destinations section. 
	//So, this test will be failed.
	
	@Test(description = "Check Popular Destinations items Links.")
	public void tc02_DestinationsPopular() {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		boolean allDestinations = des.checkPopularDestinationsItems();
		Assert.assertEquals(allDestinations, true, "Any of destination links is not valid or problem with back to Destinations page.");
	}
}
