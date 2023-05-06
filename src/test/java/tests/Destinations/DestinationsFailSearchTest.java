package tests.Destinations;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.DestinationsPage;
import tests.BaseTest;

public class DestinationsFailSearchTest extends BaseTest{
	@DataProvider
	public Object[][] getSearchWrongDataShort(){
		Object[][] dataSearch = {
				{" "},
				{"h"}
		};
		return dataSearch;
	}
	
	@Test(description = "Check error message display in case of search for less than 2 chars.", dataProvider = "getSearchWrongDataShort")
	public void tc01_DestinationsSearchEmpty(String dataSearch) {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		des.performSearchDestination(dataSearch);
		des.SearchEnterBtn();
		des.waiting(2000);
		String error = des.getSearchError();
		Assert.assertEquals(error, "Required 2 or more symbols to search");
	}
	
	@DataProvider
	public Object[][] getSearchWrongData(){
		Object[][] dataSearch = {
				{"#*#&#@"},
				{"dfgdfgdg"},
				{"lkjls sdflsdjf sskdfslf sfjsl dfsfjewrowier sdkfjsdfj sljf lseirsfsdf sfskdf skdfwe rw erekfjhds"},
				{"{}[]\\/\"\";.,"}
		};
		return dataSearch;
	}
	
	@Test(description = "Check search message display in case of wrong search data.", dataProvider = "getSearchWrongData")
	public void tc02_DestinationsSearchWrongData(String dataWrongSearch) {	
		DestinationsPage des = new DestinationsPage(driver);
		des.goToDestinationPage();
		des.performSearchDestination(dataWrongSearch);
		des.SearchEnterBtn();
		des.waiting(5000);
		String error = des.getSearchNotFoundError();
		Assert.assertEquals(error, "Sorry, we can not find "+ "\"" + dataWrongSearch +"\"" +  ".\nPlease, check the spelling.");
	}
}
