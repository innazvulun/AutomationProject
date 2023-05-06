package tests.CreateATrip;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.BookmarksPage;
import pageObjects.CreateATripPage;
import pageObjects.HeaderSection;
import pageObjects.TripOverviewPage;
import tests.BaseTest;

public class CreateATripFlowDestination_2 extends BaseTest{
//Check Create a Trip Flow for Selected Destination and Flexible dates.
//For Not loogedIn user. With Save trip in last step.
		@Test(description = "Check Create a Trip title of step 2.")
		public void tc01_CreateATripKnowDestination() {	
			CreateATripPage create = new CreateATripPage(driver);
			HeaderSection header = new HeaderSection(driver);
			header.logOutIfLoggedIn();
			create.goToCreateATripPage();
			create.waiting(1000);
			create.goToCreateATripSelectDest();
			String createPageTitle = create.getStepTitle();
			Assert.assertEquals(createPageTitle, "Where do you want to go?");
		}
		
		@Test(description = "Check select defined destination and go to next step.")
		public void tc02_SelectDestinationGoToNextStep() {	
			CreateATripPage create = new CreateATripPage(driver);
			create.performSearchDestinationTrip("Vienna");
			String createStepDestBack = create.getStepBackTextDestinationSelected();
			boolean destContains = createStepDestBack.contains("Vienna");
			Assert.assertEquals(destContains, true);
		}
		
		@Test(description = "Check destination dates (in same month) selection and go to next step.")
		public void tc03_SelectDestinationDates() {	
			CreateATripPage create = new CreateATripPage(driver);
			create.selectMonthAndYear("August", "2023");
			create.selectDate("14");
			create.selectMonthAndYear("August", "2023");
			create.selectDate("22");
			create.submitDates();
			boolean overviewPageURLGood = create.tripOverviewPageURL();
			Assert.assertEquals(overviewPageURLGood, true);		
		}
		
		@Test(description = "Check selected destination data correct displayed in last step.")
		public void tc04_CheckDestinationDataDisplay() {	
			TripOverviewPage trip = new TripOverviewPage(driver);
			boolean overviewTripData = trip.checkSubmitedData("Vienna", "Aug", "14", "Aug", "22");
			Assert.assertEquals(overviewTripData, true);		
		}
		
		@Test(description = "Save trip and check if save correctly, incluse login action before.")
		public void tc05_SaveTripWithLogin() {	
			TripOverviewPage trip = new TripOverviewPage(driver);
			trip.SaveTrip();
			trip.loginSimple("inko.loga123456@gmail.com", "Aa12345678");
			trip.waiting(3000);
			HeaderSection header = new HeaderSection(driver);
			header.waiting(2000);
			header.goToMyTripsPage();
			BookmarksPage bookmarks = new BookmarksPage(driver);
			boolean tripSaved = bookmarks.tripFound("Vienna", "14/08/2023 - 22/08/2023");
			Assert.assertEquals(tripSaved, true);		
		}
		
		@Test(description = "Delete saved trip from Bookmarks page and check if deleted.")
		public void tc06_DeleteTripAfterSave() {	
     		BookmarksPage bookmarks = new BookmarksPage(driver);
     		bookmarks.tripDelete("Vienna", "14/08/2023 - 22/08/2023");
			boolean tripFound = bookmarks.tripFound("Vienna", "14/08/2023 - 22/08/2023");
			Assert.assertEquals(tripFound, false);		
		}		
}
