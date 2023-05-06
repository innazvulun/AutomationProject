package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TripOverviewPage extends HeaderSection{
	@FindBy(css=".step-value")
	private List<WebElement> tripValues;
	@FindBy(css=".trip2-hero__button > button")
	private WebElement saveTripBtn;
	@FindBy(css=".sb-desktop")
	private WebElement tripDataArea;
	
	
	public TripOverviewPage(WebDriver driver) {
		super(driver);
	}
	
	public void SaveTrip(){
		pageRefresh();
		waiting(2000);
		click(saveTripBtn);
		waiting(1000);
	}
	
	
	public boolean checkSubmitedData(String destination, String startMonth, String startDate, String endMonth, String endDate ) {
		boolean dataValid = false;
		boolean destinationValid = (tripValues.get(0).getText().compareTo(destination)==0);
		String displayedDates = tripValues.get(1).getText();
		String startFullDate = startDate + " " + startMonth + " ";
		String endFullDate = endDate + " " + endMonth;		
		boolean datesValid = (displayedDates.compareTo(startFullDate + "- " + endFullDate)==0);
		
		if(destinationValid && datesValid) {
			dataValid = true;
		}
	
		return dataValid;
	}

}
