package tests.CreateATrip;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CreateATripPage;
import tests.BaseTest;

public class CreateAtripFlowDestination_1 extends BaseTest{
	
//Check Create a Trip Flow for Popular Destination and Flexible dates. Without Save trip.
	@Test(description = "Check Create a Trip title of step 2.")
	public void tc01_CreateATripKnowDestination() {	
		CreateATripPage create = new CreateATripPage(driver);
		create.goToCreateATripPage();
		create.goToCreateATripSelectDest();
		String createPageTitle = create.getStepTitle();
		Assert.assertEquals(createPageTitle, "Where do you want to go?");
	}
	
	@Test(description = "Check select popular destination and go to next step.")
	public void tc02_SelectDestinationGoToNextStep() {	
		CreateATripPage create = new CreateATripPage(driver);
		create.selectDestPopular();
		String createPageTitle = create.getStepTitle();
		Assert.assertEquals(createPageTitle, "When do you plan on going?");
	}
	
	@Test(description = "Check flexible dates for trip and go to next step.")
	public void tc03_SelectDestinationGoToNextStepDates() {	
		CreateATripPage create = new CreateATripPage(driver);
		create.selectFlexibleDates();
		String createPageTitle = create.getStepTitle();
		boolean titleContains = createPageTitle.contains("Your personalized trip to");
		Assert.assertEquals(true,titleContains );
	}
	
}


