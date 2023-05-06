package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class CreateATripPage extends HeaderSection{
	@FindBy(css=".flows > div:nth-child(1) > .flows__item-btn> button")
	private WebElement startPlanningBtn;
	@FindBy(css=".flows > div:nth-child(2) > .flows__item-btn> button")
	private WebElement inspireMeBtn;
	@FindBy(css=".container h1")
	private WebElement createTripTtl;
	@FindBy(css="a[href='/en/create-a-trip']")
	private WebElement linkToCreateTripPage;	
	@FindBy(css=".destination-form__select-desktop #search-bar\\.to")
	private WebElement tripDestSearchInput;
	@FindBy(css=".destination-form__select-desktop > div > button")
	private WebElement tripDestSearchNextBtn;
	@FindBy(css=".destination-form__list-items > button:nth-child(1)")
	private WebElement popularDest;
	@FindBy(css=".search-dates-menu >.search-dates-menu__controls > button")
	private WebElement flexibleDatesBtn;
	@FindBy(css="h1.trip2-hero__title")
	private WebElement tripOverviewTtl;
	@FindBy(css=".create-trip-back")
	private WebElement tripStepDestSelectedBack;
	@FindBy(css=".rs-select2__value-container")
	private WebElement inputDiv;
	
	@FindBy(css=".flatpickr-next-month")
	private WebElement nextMonthBtn;
	@FindBy(css=".flatpickr-days > :nth-child(1) >.flatpickr-day")
	private List<WebElement> datesList;
	@FindBy(css=".dates-form__submit >button")
	private WebElement datesSubmitBtn;
	
	@FindBy(css=".search-purpose__item")
	private List<WebElement> purposesList;
	@FindBy(css=".search-purpose__subcategories > li")
	private List<WebElement> purposesCategories;
	@FindBy(css=".purpose-form__submit > button")
	private WebElement purposesNextStepBtn;
	
	@FindBy(css=".interests-list__item")
	private List<WebElement> slidersList;
	@FindBy(css=".interests-form__submit > button")
	private WebElement interestsNextStepBtn;
	
	
	
	
	public CreateATripPage(WebDriver driver) {
		super(driver);
	}
	
	public String getStepTitle() {
		return getPageTitle(createTripTtl);
	}
	
	public String getPageTitleTrip() {
		return getPageTitle(tripOverviewTtl);
	}
	
	public void goToCreateATripPage(){
		click(linkToCreateTripPage);		
	}
	
	public void goToCreateATripSelectDest(){
		click(startPlanningBtn);		
	}
	
	public void goToCreateATripInspire(){
		click(inspireMeBtn);		
	}
	
	public void selectDestPopular(){
		click(popularDest);		
	}
	
	public void selectFlexibleDates() {
		click(flexibleDatesBtn);
	}
	
	public String getStepBackTextDestinationSelected() {
		return getText(tripStepDestSelectedBack);
	}	
	
	public void performSearchDestinationTrip(String searchText) {
		new Actions(driver).moveToElement(tripDestSearchInput).perform();
		fillText(tripDestSearchInput, searchText);
		new Actions(driver).moveToElement(inputDiv).perform();
		waiting(1000);
		new Actions(driver).keyDown(Keys.ARROW_DOWN);
		waiting(1000);
		new Actions(driver).moveToElement(inputDiv).perform();
		waiting(1000);
		new Actions(driver).moveToElement(tripDestSearchNextBtn).perform();
		tripDestSearchInput.sendKeys(Keys.TAB);
		waiting(1000);
		tripDestSearchNextBtn.sendKeys(Keys.ENTER);
	}
	
	public void selectMonthAndYear(String month, String year) {
		boolean monthAndYearFound = false;
		do {			
			List<WebElement> currentMonthList = driver.findElements(By.cssSelector(".flatpickr-current-month"));
			String currentMonth = currentMonthList.get(0).findElement(By.cssSelector(".cur-month")).getText();
			String currentYear = currentMonthList.get(0).findElement(By.cssSelector(".numInput.cur-year")).getAttribute("value");
			if((currentMonth.compareTo(month) + currentYear.compareTo(year))==0) {
				monthAndYearFound=true;
				break;
			}	
			else {
				click(nextMonthBtn);
			}
		}while(monthAndYearFound==false);		
	}
	
	public void selectDate(String date) {
		for (WebElement el : datesList) {
			if(el.getText().compareTo(date)==0) {
				click(el);
				break;
			}
		}	
	}
	
	public void submitDates(){
		click(datesSubmitBtn);
	}
	
	public boolean tripOverviewPageURL() {
		waiting(6000);
		String overviewPageURL = getCurrentPageUrl();
		if (overviewPageURL.contains("trip-overview")) {
			return true;
		}
		else return false;
	}
	
	public void selectPurpose(int purpose){
		click(purposesList.get(purpose));
	}
	
	public void selectPurpCategory(int category){
		click(purposesCategories.get(category));
	}
	
	public void submitPurpose(){
		click(purposesNextStepBtn);
	}
	
	public void moveSlider(String slider, int moveTo){
		for (WebElement el : slidersList) {
			String sliderName = el.findElement(By.cssSelector(".interests-list__item >.interests-list__item--header > .title")).getText();
			if(sliderName.compareTo(slider) == 0) {
				WebElement sliderLine = el.findElement(By.cssSelector(".interests-list__item >.interests-list__item--slider .rc-slider-track"));
				Actions action = new Actions(driver);			
				action.dragAndDropBy(sliderLine, (moveTo-1)*20, 0).perform(); 
				waiting(500);
				break;
			}
		}
	}
	
	public void submitInterests(){
		click(interestsNextStepBtn);
	}
}
