package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BookmarksPage extends HeaderSection{
	@FindBy(css=".bookmarks-trips-list .trip-item")
	private List<WebElement> tripItems;
	@FindBy(css=".confirm-modal__buttons > .btn-blue.btn-small.btn")
	private WebElement removeBtn;
	
	
	public BookmarksPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean tripFound(String tripDestination, String tripDates) {
		boolean tripFound = false;
		String currentTitle="";
		String currentDate="";
		for (WebElement el : tripItems) {
			currentTitle = el.findElement(By.cssSelector(".trip-item__title")).getText();
			currentDate = el.findElement(By.cssSelector(".trip-item__dates")).getText();
			if((currentTitle.compareTo(tripDestination) == 0 ) && (currentDate.compareTo(tripDates) == 0)) {
				tripFound = true; 
				break;
			}
		}
		return tripFound;
	}

	public void tripDelete(String tripDestination, String tripDates) {
		String tripTitle="";
		String tripDate="";
		for (WebElement el : tripItems) {
			tripTitle = el.findElement(By.cssSelector(".trip-item__title")).getText();
			tripDate = el.findElement(By.cssSelector(".trip-item__dates")).getText();
			if((tripTitle.compareTo(tripDestination) == 0 ) && (tripDate.compareTo(tripDates) == 0)) {
				click(el.findElement(By.cssSelector(".trip-item__control--delete")));
				waiting(1000);
				click(removeBtn);
				waiting(2000);
				break;
			}
		}
	}
}
