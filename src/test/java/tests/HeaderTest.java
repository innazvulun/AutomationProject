package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HeaderSection;

public class HeaderTest extends BaseTest{
	@Test(description = "Check Header Menu Inner Links.")
	public void tc01_linksHeaderMenuInnerLinks() {	
		HeaderSection hs = new HeaderSection(driver);
		boolean allLinksGood = hs.checkHeaderMenuLinksInner();
		Assert.assertEquals(allLinksGood, true);
	}
	
	@Test(description = "Check Header Menu Outer Links.")
	public void tc02_linksHeaderMenuOuterLinks() {	
		HeaderSection hs = new HeaderSection(driver);
		boolean allLinksGood = hs.checkHeaderMenuLinksExternal();
		Assert.assertEquals(allLinksGood, true);
	}
}
