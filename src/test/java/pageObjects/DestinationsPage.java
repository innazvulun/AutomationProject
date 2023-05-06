package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DestinationsPage extends HeaderSection{	

	@FindBy(css=".destinations-header__title")
	private WebElement destinationTitle;
	@FindBy(css=".destinations-header__input")
	private WebElement destinationSearchField;
	@FindBy(css=".destinations-header__btn")
	private WebElement destinationSearchBtn;
	@FindBy(css=".destinations-page-services__list li")
	private List<WebElement> destinationServicesList;
	@FindBy(css=".destinations-page-list-wrap h3")
	private WebElement destinationsListTitle;
	@FindBy(css=".destinations-page-item")
	private List<WebElement> destinationsList;
	@FindBy(css=".app-header__desktop  .header-nav__list a[href=\"/en/destinations\"]")
	private WebElement linkToDestinationsPage;
	@FindBy(css="ul.react-autosuggest__suggestions-list li")
	private List<WebElement> searchResultsDDList;
	@FindBy(css=".form-error")
	private WebElement searchErroMsg;
	@FindBy(css=".destinations-page__no-results-message")
	private WebElement searchNotFoundMsg;
	@FindBy(css=".pagination__item")
	private List<WebElement> pagesList;
	
	public DestinationsPage(WebDriver driver) {
		super(driver);
	}
	
	public void goToDestinationPage(){
		click(linkToDestinationsPage);		
	}

	public String getPageTitle() {
		return getText(destinationTitle);
	}
	
	public void performSearchDestination(String searchText) {
		fillText(destinationSearchField, searchText);	
	}	
	
	public void SearchEnterBtn() {
		click(destinationSearchBtn);
	}
	
	public boolean checkResultsList(String searchText) {
		String text="";
		int goodRes=0;
		searchText = searchText.toLowerCase();
		waiting(1000);
			
	    //check if all received results are contains searched string
		for (WebElement el : searchResultsDDList) {
			text = el.findElement(By.cssSelector(" span:nth-child(2)")).getText();
			text = text.toLowerCase();
			if(text.contains(searchText)) {
				goodRes++;
			}
		}
		if (goodRes==searchResultsDDList.size()) {
			return true;
		}else return false;
	}
	
	public WebElement findDestInList(String destination){
		String text="";
		waiting(1000);
		WebElement destEl = searchResultsDDList.get(0);	
		
		for(int i=0; i<searchResultsDDList.size(); i++) {
			text = searchResultsDDList.get(i).findElement(By.cssSelector("span:nth-child(2)")).getText();
			if(text.contains(destination)) {
				destEl = searchResultsDDList.get(i);
     			break;
			}
		}
		return destEl;
	}
	
	public void selectDestFromList(String destination){
		WebElement destEl = findDestInList(destination);
		click(destEl);
		waiting(5000);
	}
	
	public String getSearchError() {
		return getText(searchErroMsg);
	}
	
	public String getSearchNotFoundError() {
		return getText(searchNotFoundMsg);
	}

	//Destination services section
	public boolean checkDestServicesLinksExternal() {
		int goodLink = 0;
		String hrefExpected="";
		String hrefActual="";
		int listLinksSize = destinationServicesList.size();
		
		for (int i=0; i<listLinksSize; i++) {
			if(i>0) {
				pageRefresh();
				waiting(1000);
			}
			List<WebElement> listExternalLinks = driver.findElements(By.cssSelector(".destinations-page-services__list a[target='_blank']"));
			WebElement el = listExternalLinks.get(i);
			hrefExpected = getHrefOfLink(el);
			
			String originalWindow = driver.getWindowHandle();
			click(el);
			
			for (String windowHandle : driver.getWindowHandles()) {
			    if(!originalWindow.contentEquals(windowHandle)) {
			        driver.switchTo().window(windowHandle);
			        hrefActual = driver.getCurrentUrl();
			        driver.close();
			        driver.switchTo().window(originalWindow);
			    }
			    if((hrefExpected.compareTo(hrefActual) == 0)&& !(hrefActual=="")){
					goodLink++;
					hrefExpected=hrefActual="";			
				}else if(!(hrefActual=="")){
					hrefExpected = hrefExpected.substring(0,hrefExpected.indexOf("en/"));
					if(hrefActual.contains(hrefExpected)) {
						goodLink++;
						hrefExpected=hrefActual="";	
					}
				}
			}			
		}
		
		if (goodLink == listLinksSize) return true;
		else return false;
	}
	
	public boolean checkPopularDestinationsItems() {	
		int goodLink = 0;
		String itemTitle="";
		String referenceTitle="";
		int linksSize = 0;
		int pageNum = 1;
		
		do {
			List<WebElement> popularDestLinksList = driver.findElements(By.cssSelector("a.destinations-page-item__link"));
			linksSize = linksSize + popularDestLinksList.size();
			
			for (int i = 0; i < linksSize; i++) {
				waiting(3000);
				popularDestLinksList = driver.findElements(By.cssSelector("a.destinations-page-item__link"));
				WebElement el = popularDestLinksList.get(i);
				scrollTo(el);
				String destName = el.getText();
				destName = destName.substring(0, destName.indexOf(","));
				click(el);
				waiting(4000);
				WebElement destTitleEl = driver.findElement(By.cssSelector(".destination-description__heading"));
				referenceTitle = destTitleEl.getText();				
				if (referenceTitle.contains(itemTitle)) {
					goodLink++;
				}
				back();
			} 
			click(pagesList.get(pageNum));
			waiting(3000);
			pageNum++;
			
		}while(pageNum <= pagesList.size());
		
		if (goodLink == linksSize) return true;
		else return false;
	}
}
