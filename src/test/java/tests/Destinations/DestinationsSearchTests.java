package tests.Destinations;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DestinationsPage;
import tests.BaseTest;

public class DestinationsSearchTests extends BaseTest {
	@Test(description = "Check Destinations page title.")
	public void tc01_DestinationsPageTitle() {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		String destPageTitle = des.getPageTitle();
		Assert.assertEquals(destPageTitle, "Travel anywhere");
	}
	
	@Test(description = "Check Destinations search DD list for contains required string (2 chars).")
	public void tc02_DestinationsPageSearch_2_chars() {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		des.performSearchDestination("de");
		boolean listContains = des.checkResultsList("de");
		Assert.assertEquals(true, listContains);
	}
	
	//in current test we have a bug: search "Vienna" receives in results Vietnam. So this test will be failed.
	@Test(description = "Check Destinations search DD list for contains required string (more than 2 chars).")
	public void tc03_DestinationsPageSearch_more_than_2_chars() {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		des.performSearchDestination("Vienna");
		boolean listContains = des.checkResultsList("Vienna");
		Assert.assertEquals(true, listContains);
	}
	

	@Test(description = "Check Destinations search - find requested string")
	public void tc04_DestinationsPageSearchResult() {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		des.performSearchDestination("Vienna");
		String str = des.findDestInList("Vienna").getText();
		Assert.assertEquals(str, "Vienna, Austria");
	}
	
	@Test(description = "Check Destinations search - select one result.")
	public void tc05_DestinationsPageSearchSelectOneResult() {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		des.performSearchDestination("Berlin");
		des.selectDestFromList("Berlin");
		String url = des.getCurrentPageUrl();
		boolean urlContains = url.contains("berlin");
		des.back();
		Assert.assertEquals(urlContains, true);
	}	
	
}
