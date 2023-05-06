package tests.CreateATrip;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CreateATripPage;
import pageObjects.HeaderSection;
import pageObjects.SuggestionsPage;
import tests.BaseTest;

public class CreateATripFlowDestination_3 extends BaseTest{
//Check Create a Trip Flow for NOT Selected Destination and known dates.
//For loogedIn user. With Save trip in last step.
		@Test(description = "Check Create a Trip title of step 2.")
		public void tc01_CreateATripKnowDestination() {	
			CreateATripPage create = new CreateATripPage(driver);
			HeaderSection header = new HeaderSection(driver);
			header.login("inko.loga123456@gmail.com", "Aa12345678");
			create.goToCreateATripPage();
			create.waiting(1000);
			create.goToCreateATripInspire();
			String createPageTitle = create.getStepTitle();
			Assert.assertEquals(createPageTitle, "When do you plan on going?");
		}
		
		@Test(description = "Select trip dates (in different month) selection and go to next step.")
		public void tc02_SelectDestinationDates() {	
			CreateATripPage create = new CreateATripPage(driver);
			create.selectMonthAndYear("July", "2023");
			create.selectDate("25");
			create.selectMonthAndYear("August", "2023");
			create.selectDate("5");
			create.submitDates();
			String createStepTitle = create.getStepTitle();
			Assert.assertEquals(createStepTitle, "What do you want to do?");		
		}
		
		@Test(description = "Check next step page display and select trip purpose.")
		public void tc03_SelectTripPurpose() {	
			CreateATripPage create = new CreateATripPage(driver);
			create.selectPurpose(1);
			create.selectPurpCategory(1);
			create.submitPurpose();
			String createPageTitle = create.getStepTitle();
			Assert.assertEquals(createPageTitle, "Specify your interest");
		}
		
		// We have a bug here. 
		//Sometimes English flow reached the English page with names of Interests in Hebrew,
		//so, the whole test will be failed.
		@Test(description = "Check next step page display and select trip Interests.")
		public void tc04_SelectTripInterests() {	
			CreateATripPage create = new CreateATripPage(driver);
			create.moveSlider("Museums & Galleries", 4);
			create.moveSlider("Sights & Attractions", 3);
			create.moveSlider("Shows & Cultural Events", 5);
			create.moveSlider("Gastronomy", 7);
			create.moveSlider("Shopping", 8);
			create.moveSlider("Nightlife", 1);
			create.moveSlider("Sports", 6);
			create.moveSlider("Recreation", 10);						
			create.submitInterests();
			create.waiting(500);
			String receivedPageTitle = create.getCurrentPageH1TItle();
    		Assert.assertEquals(receivedPageTitle, "Your suggestions");
		}
		
		@Test(description = "Define ages and check selected data correct displayed in last step (suggestions).")
		public void tc05_SuggestionsAccordingToSelections_AgesSelected() {	
			SuggestionsPage suggestions = new SuggestionsPage(driver);
			suggestions.waiting(4000);
			suggestions.tripComposition(Arrays.asList(45, 46, 14, 8));
			suggestions.waiting(1000);
			boolean tripDataVald = suggestions.checkSubmitedData("Jul", "25", "Aug", "5", "2 adults, 2 kids", "Comfort", "Family trip");
			Assert.assertEquals(tripDataVald, true);		
		}
		
		@Test(description = "Define ages and Finance standard and check selected data correct displayed in last step (suggestions).")
		public void tc06_SuggestionsAccordingToSelections_AgesSelected_FinanceSelected() {	
			SuggestionsPage suggestions = new SuggestionsPage(driver);
			suggestions.pageRefresh();
			suggestions.waiting(2000);
			suggestions.tripComposition(Arrays.asList(33, 45, 3, 7, 17));
			suggestions.waiting(1000);
			suggestions.tripFinancialStandard("Economy");
			boolean tripDataVald = suggestions.checkSubmitedData("Jul", "25", "Aug", "5", "2 adults, 3 kids", "Economy", "Family trip");
			Assert.assertEquals(tripDataVald, true);		
		}
	
		@Test(description = "Define ages and Finance in Slider and check selected data correct displayed in last step (suggestions).")
		public void tc07_SuggestionsAccordingToSelections_AgesSelected_FinanceSelected_Slider() {	
			SuggestionsPage suggestions = new SuggestionsPage(driver);
			suggestions.pageRefresh();
			suggestions.waiting(2000);
			suggestions.tripComposition(Arrays.asList(21, 24));
			suggestions.waiting(1000);
			suggestions.movePriceSlider(100);
			boolean tripDataVald = suggestions.checkSubmitedData("Jul", "25", "Aug", "5", "2 adults", "₪23,000", "Family trip");
			Assert.assertEquals(tripDataVald, true);		
		}
		
		@Test(description = "Define ages, Finance in Slider, Change Interest and check selected data correct displayed in last step (suggestions).")
		public void tc08_SuggestionsAccordingToSelections_AgesSelected_FinanceSelected_Slider_Interest() {	
			SuggestionsPage suggestions = new SuggestionsPage(driver);
			suggestions.pageRefresh();
			suggestions.waiting(2000);
			suggestions.tripComposition(Arrays.asList(35, 39, 0));
			suggestions.waiting(1000);
			suggestions.movePriceSlider(150);
			suggestions.changeInterest("Nature");
			suggestions.waiting(2000);
			boolean tripDataVald = suggestions.checkSubmitedData("Jul", "25", "Aug", "5", "2 adults, 1 child", "₪33,000", "Nature");
			Assert.assertEquals(tripDataVald, true);		
		}
}
