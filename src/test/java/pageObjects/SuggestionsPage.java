package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class SuggestionsPage extends HeaderSection{
	@FindBy(css=".go-go-page__title")
	private WebElement pageTtl;
	@FindBy(css=".step-value")
	private List<WebElement> tripData;
	@FindBy(css=".sb-desktop__bar-items > div:nth-child(3) > button")
	private WebElement compBtn;
	@FindBy(css=".parties-input__field")
	private List<WebElement> agesRows;
	@FindBy(css=".sb__form-content > div > div > div:nth-child(1) > div:nth-child(2) > div > div:nth-child(1) > div.parties-input__field-input > button:nth-child(1)")
	private WebElement defaultAdultValueBtnMinus;
	@FindBy(css=".sb-desktop__bar-items > div:nth-child(4) > button")
	private WebElement financeBtn;
	@FindBy(css=".search-budget-standards__button")
	private List<WebElement> financeStandards;
	@FindBy(css=".rc-slider-handler-tooltip > div")
	private WebElement slider;
	@FindBy(css=".sb-desktop__bar-items > div:nth-child(5) > button")
	private WebElement interestsBtn;
	@FindBy(css=".search-purpose__item")
	private List<WebElement> interestsList;
	@FindBy(css=".apply-filters-button")
	private WebElement filtersBtn;
		
	Actions action = new Actions(driver);
	
	public SuggestionsPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkSubmitedData(String startMonth, String startDate, String endMonth, String endDate, String who, String howMuch, String what) {
		boolean dataValid = false;
		
		String displayedDates = tripData.get(1).getText();
		String startFullDate = startDate + " " + startMonth + " ";
		String endFullDate = endDate + " " + endMonth;		
		boolean datesValid = (displayedDates.compareTo(startFullDate + "- " + endFullDate)==0);		
		boolean whoValid = (tripData.get(2).getText().compareTo(who)==0);
		boolean howMuchValid = (tripData.get(3).getText().compareTo(howMuch)==0);
		boolean whatValid = (tripData.get(4).getText().compareTo(what)==0);
		
		
		if(whoValid && datesValid && howMuchValid && whatValid) {
			dataValid = true;
		}
	
		return dataValid;
	}
	
	//define trip participants ages
	public void tripComposition(List<Integer> ages) {
		int minAge = 0;
		int maxAge = 0;
		click(compBtn);
		//remove default value
		defaultAdultValueBtnMinus.click();
		
		for (Integer age : ages) {
			for(int i=0; i < agesRows.size(); i++) {
				String ageStr = agesRows.get(i).findElement(By.cssSelector(".parties-input__field-label")).getText();
				//replace + with 120 in "65+" row
				if(ageStr.contains("+")) {
					ageStr = ageStr.replace("+", " - 120");
				}
				String minAgeStr = ageStr.substring(0 , ageStr.indexOf(" "));
				try{
					minAge = Integer.parseInt(minAgeStr);
		        }
		        catch (NumberFormatException ex){
		            ex.printStackTrace();
		        }
				
				ageStr = ageStr.replace(" y.o.", "");
				String maxAgeStr = ageStr.substring(ageStr.lastIndexOf(" "), ageStr.length()).trim();
				
				try{
					maxAge = Integer.parseInt(maxAgeStr);
		        }
		        catch (NumberFormatException ex){
		            ex.printStackTrace();
		        }
				
				if((minAge <= age)&& (age <= maxAge) ) {
					agesRows.get(i).findElement(By.cssSelector(".parties-input__field-input >span+button")).click();
					break;
				}
			}
		}
		//close composition window
		clickout();
	}

	//define trip financial standard
	public void tripFinancialStandard(String standard) {
		click(financeBtn);
		for (WebElement el : financeStandards) {
			String standStr = el.findElement(By.cssSelector(".search-budget-standards__button-title")).getText();
			if(standStr.compareTo(standard)==0) {
				el.click();
				waiting(2000);
				break;
			}
		}
		//close Financial Standards window
		clickout();
	}
	
	public void movePriceSlider(int moveTo){
		click(financeBtn);		
		action.dragAndDropBy(slider, moveTo, 0).perform(); 
		waiting(500);
		clickout();
	}
	
	public void changeInterest(String interest){
		click(interestsBtn);	
		for (WebElement el : interestsList) {
			if(el.getText().compareTo(interest)==0) {
				click(el);
				break;
			}
		}
		click(filtersBtn);
		waiting(2000);		
	}
}
