package com.example.tests;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;









import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class PostDeploymentTests {
	//Variables for Post deploymnet tests
	//can be used across methods.
	private Selenium selenium;
	private String loginUserID = "e160616";
	private String longPwd = "password";
	private String pageLoadTime = "50000"; 
	private void ln (Object test){
		System.out.println(test);
	}
	
	@Before
	public void setUp() throws Exception {
//		selenium = new DefaultSelenium("localhost", 4444, "*firefox C:/Users/angel.martin/AppData/Local/Mozilla Firefox/firefox.exe" , "http://cirm.miamidade.gov");
		selenium = new DefaultSelenium("localhost", 4444, "*googlechrome C:/Program Files (x86)/Google/Chrome/Application/chrome.exe" , "http://cirm.miamidade.gov");
//		selenium = new DefaultSelenium("localhost", 4444, "*iexplore C:/Program Files (x86)/Internet Explorer/iexplore.exe" , "http://cirm.miamidade.gov");
	//	selenium = new DefaultSelenium("localhost", 4444, "*googlechrome C:/Program Files (x86)/Google/Chrome/Application/chrome.exe" , "https://311hub.miamidade.gov");
		selenium.start();
	}

	
	public void login() throws Exception {
		selenium.open("http://cirm.miamidade.gov");
	//	selenium.open("https://311hub.miamidade.gov/html/login.html");
		selenium.type("id=iUsername", loginUserID );
		selenium.type("id=iPassword", longPwd);
		selenium.click("id=btnLogin");
		for (int i = 0; i < 3; i++)
		{
			selenium.waitForPageToLoad("5000");
		}
	}
	
	@Test
	public void ValidateAddress() throws Exception {
		login();
		selenium.click("//input[@value='']");
		selenium.type("//input[@value='']", "9910 sw 73rd st");
		selenium.click("//input[@value='Search']");
		assertTrue(selenium.isElementPresent("css=#answer_hub > div:nth-child(1) > span > input.ic_valid.button_icon.visibility_visible"));
		}

	@Test
	public void GeoInfoTab() throws Exception {
		ValidateAddress();
		selenium.click("id=geo_info_district");
		assertTrue(selenium.isTextPresent("District"));
	}
	
	@Test
	public void OpenSRInAnswerHub() throws Exception {
		ValidateAddress();
		selenium.type("xpath=(//input[@type='text'])[6]", "Bulky");
		selenium.click("xpath=(//input[@value='Search'])[3]");
		selenium.click("link=BULKY TRASH REQUEST");
		selenium.click("xpath=(//button[@type='button'])[3]");
		assertTrue(selenium.isVisible("css=#sr_details > span > div:nth-child(1) > h4"));
		assertTrue(selenium.isVisible("css=#sr_details > div:nth-child(22) > h4"));
	 }
	
	@Test
	public void ValidateinWCS() throws Exception {
		OpenSRInAnswerHub();
		selenium.click("css=textarea.tooltip");
		selenium.type("css=textarea.tooltip", "test");
		selenium.click("css=div.grid_3.alpha > input");
		selenium.type("css=div.grid_3.alpha > input", "tester");
		selenium.type("css=div.grid_3.omega > input", "last tester");
		selenium.click("//div[@id='sr_details']/span/div[7]/input");
		selenium.type("//div[@id='sr_details']/span/div[7]/input", "test@test.com");
		selenium.click("css=span > div.grid_2 > input..error");
		selenium.type("css=span > div.grid_2 > input", "3051111111");
		selenium.click("link=WCS");
		selenium.click("css=#wcs_right > input.button.blue");
		Thread.sleep(8000);
		selenium.click("id=sendToSRButtonID");
		assertTrue(selenium.isVisible("css=#sr_details > div.grid_7.alpha.omega.scroller > div > div:nth-child(2) > input"));
	

		}
	
	@Test
	public void MasterClr() throws Exception {
		OpenSRInAnswerHub();
		selenium.click("css=textarea.tooltip");
		selenium.type("css=textarea.tooltip", "test");
		selenium.click("css=div.grid_3.alpha > input");
		selenium.type("css=div.grid_3.alpha > input", "tester");
		selenium.type("css=div.grid_3.omega > input", "last tester");
		selenium.click("//div[@id='sr_details']/span/div[7]/input");
		selenium.type("//div[@id='sr_details']/span/div[7]/input", "test@test.com");
		selenium.click("css=span > div.grid_2 > input..error");
		selenium.type("css=span > div.grid_2 > input", "3051111111");
		selenium.click("link=WCS");
		selenium.click("css=#wcs_right > input.button.blue");
		Thread.sleep(8000);
		selenium.click("id=sendToSRButtonID");
		selenium.click("css=select..error");
		selenium.select("css=div.input_margins > div.grid_5.alpha > select", "label=No");
		selenium.click("xpath=(//button[@type='button'])[3]");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[10]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[10]/select", "label=No");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[14]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[14]/select", "label=Yes");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[18]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[18]/select", "label=Yes");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[22]/select", "label=FR (Front of)");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[26]/select", "label=Yes");
		selenium.click("xpath=(//button[@type='button'])[3]");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[26]/select");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[30]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[30]/select", "label=No");
		selenium.click("css=input.tooltip.error");
		selenium.type("css=input.tooltip", "3331111111");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[38]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[38]/select", "label=Yes");
		selenium.click("//input[contains(@id,'dp')]");
		selenium.type("//input[contains(@id,'dp')]", "04/15/2015");
		selenium.addSelection("css=select[title=\"To select multiple options - press CTRL and select the options you wish to.\"]", "label=Tree Cuttings");
		selenium.click("css=option[value=\"http://www.miamidade.gov/cirm/legacy#BULKYTRA_WHATTYPE_BTPTYPE_BTPTREE\"]");
		selenium.click("xpath=(//button[@type='button'])[3]");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[50]/input");
		selenium.type("//div[@id='sr_details']/div[23]/div/div[50]/input", "3305111234");
		selenium.click("css=#sr_details > div:nth-child(2) > input");
		selenium.click("css=body > div:nth-child(15) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(1) > span");
		assertTrue(selenium.isTextPresent("11558262"));

		
	}
 	@Test	
 	public void SaveSr() throws Exception {
		OpenSRInAnswerHub();
		selenium.click("css=textarea.tooltip");
		selenium.type("css=textarea.tooltip", "test");
		selenium.click("css=div.grid_3.alpha > input");
		selenium.type("css=div.grid_3.alpha > input", "tester");
		selenium.type("css=div.grid_3.omega > input", "last tester");
		selenium.click("//div[@id='sr_details']/span/div[7]/input");
		selenium.type("//div[@id='sr_details']/span/div[7]/input", "test@test.com");
		selenium.click("css=span > div.grid_2 > input..error");
		selenium.type("css=span > div.grid_2 > input", "3051111111");
		selenium.click("link=WCS");
		selenium.click("css=#wcs_right > input.button.blue");
		Thread.sleep(8000);
		selenium.click("id=sendToSRButtonID");
		selenium.click("css=select..error");
		selenium.select("css=div.input_margins > div.grid_5.alpha > select", "label=No");
		selenium.click("xpath=(//button[@type='button'])[3]");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[10]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[10]/select", "label=No");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[14]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[14]/select", "label=Yes");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[18]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[18]/select", "label=Yes");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[22]/select", "label=FR (Front of)");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[26]/select", "label=Yes");
		selenium.click("xpath=(//button[@type='button'])[3]");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[26]/select");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[30]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[30]/select", "label=No");
		selenium.click("css=input.tooltip.error");
		selenium.type("css=input.tooltip", "3331111111");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[38]/select");
		selenium.select("//div[@id='sr_details']/div[23]/div/div[38]/select", "label=Yes");
		selenium.click("//input[contains(@id,'dp')]");
		selenium.type("//input[contains(@id,'dp')]", "04/15/2015");
		selenium.addSelection("css=select[title=\"To select multiple options - press CTRL and select the options you wish to.\"]", "label=Tree Cuttings");
		selenium.click("css=option[value=\"http://www.miamidade.gov/cirm/legacy#BULKYTRA_WHATTYPE_BTPTYPE_BTPTREE\"]");
		selenium.click("xpath=(//button[@type='button'])[3]");
		selenium.click("//div[@id='sr_details']/div[23]/div/div[50]/input");
		selenium.type("//div[@id='sr_details']/div[23]/div/div[50]/input", "3305111234");
		selenium.click("css=#save");
		System.out.println(selenium.getValue("css=#editorDiv > div.app_container > div.right_column.grid_2 > span > input.search"));
		selenium.getValue("css=#editorDiv > div.app_container > div.right_column.grid_2 > span > input.search");
		
 	}
 	
 	
	@Test	
 	public void OpenSrBasicSearch() throws Exception {
 		login();
 		selenium.click("link=Basic Search");
 		selenium.click("name=ServiceRequestType");
		selenium.type("name=ServiceRequestType", "BULKY TRASH REQUEST - MD");
		selenium.click("id=createdStartDate");
		selenium.type("id=createdStartDate", "-60");
		selenium.click("css=#advSearch_right > input[name=\"search\"]");
		Thread.sleep(6000);
		selenium.click("css=#advSearchResults > table > tbody > tr:nth-child(1) > td:nth-child(1) > a");
		selenium.click("css=body > div:nth-child(11) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(1) > span");
		Thread.sleep(5000);
		System.out.println(selenium.getValue("css=#editorDiv > div.app_container > div.right_column.grid_2 > span > input.search"));
		selenium.getValue("css=#editorDiv > div.app_container > div.right_column.grid_2 > span > input.search");
		
 		
 	}
 	
 	@Test
	public void FeildSort() throws Exception {
		selenium.open("http://cirm.miamidade.gov/html/login.html");
		selenium.type("id=iUsername", "c203036");
		selenium.type("id=iPassword", "Pass");
		selenium.click("id=btnLogin");
		Thread.sleep(5000);
		selenium.click("css=body > div.container_12 > div.banner.grid_12 > ul > li:nth-child(6) > a");
		selenium.type("name=ServiceRequestType", "POTHOLE - MD");
		selenium.type("id=createdStartDate", "-60");
		selenium.click("css=#advSearch_right > input:nth-child(1)");
		String sr1 = selenium.getText("css=#advSearchResults > table > tbody > tr:nth-child(1) > td:nth-child(1) > a");
		String[] parts = sr1.split("-");
		ln("Sr1= " + parts[1]);
		int sr01 = Integer.parseInt(parts[1]);

		selenium.click("css=#advSearchResults > table > thead > tr > th:nth-child(1) > img");
		String sr2 = selenium.getText("css=#advSearchResults > table > tbody > tr:nth-child(1) > td:nth-child(1) > a");
		parts = sr2.split("-");
		int sr02 = Integer.parseInt(parts[1]);
		int test= sr01-sr02; 
		if(test >0 ) {
						ln("SUCCESSFUL SORT IS OPERATIONAL.");
		}
		else{ 
			ln("FAIL!!! SORT DID NOT WORK");
	        throw new RuntimeException();
			} 
		ln("FeildSort= Done");	
		}
 	
 	
 	
    @Test
 	public void ViewReport() throws Exception {
 		login();
 		selenium.click("link=Basic Search");
		selenium.click("name=ServiceRequestType");
		selenium.type("name=ServiceRequestType", "BULKY TRASH REQUEST - MD");
		selenium.click("id=createdStartDate");
		selenium.type("id=createdStartDate", "-60");
		selenium.click("css=#advSearch_right > input[name=\"search\"]");
		Thread.sleep(500);
		selenium.click("css=#advSearchResults > table > tbody > tr:nth-child(1) > td:nth-child(1) > a");
		selenium.click("css=body > div:nth-child(11) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(2) > span");
//		Thread.sleep(5000);
//		assertTrue(selenium.isElementPresent("id=plugin"));
		
		
 	}
 	
	@Test
 	public void Duplicate() throws Exception {
 		login();
 		selenium.click("link=Service Hub");
		selenium.click("id=srTypeID");
		selenium.type("id=srTypeID", "BOAT STORAGE - MD");
		selenium.click("xpath=(//input[@value=''])[7]");
		selenium.type("css=#sr_details > div.grid_5.alpha > span.input_clear > input.ic_field.h24", "4405 SW 129 AVE");
		selenium.click("xpath=(//input[@value='Search'])[6]");
		Thread.sleep(8000);
		assertTrue(selenium.isElementPresent("css=#sr_details_right > input.red_button.button.blue"));
		
 	}
 	
 	@Test
 	public void OutofServiceArea() throws Exception {
 		login();
 		selenium.click("link=Service Hub");
		selenium.click("id=srTypeID");
		selenium.type("id=srTypeID", "BOAT STORAGE - MD");
		selenium.click("css=#srTypeList > span > input.submit.h23_submit.button.blue");
		selenium.type("css=#sr_details > div.grid_5.alpha > span.input_clear > input.ic_field.h24", "1022 adams drive");
		selenium.click("xpath=(//input[@value='Search'])[6]");
		Thread.sleep(4000);
		assertTrue(selenium.isTextPresent("Outside"));
 	
 	}
 	
 	
 	@Test
 	public void apporvalProcess() throws Exception {
 		selenium.open("http://cirm.miamidade.gov/html/login.htm");
 		selenium.type("id=iUsername", loginUserID );
		selenium.type("id=iPassword", longPwd);
		selenium.click("id=btnLogin");
		for (int i = 0; i < 3; i++)
		{
			selenium.waitForPageToLoad("5000");
		}
 		selenium.click("link=Basic Search");
 		Thread.sleep(5000);
 		Thread.sleep(5000);
 		Thread.sleep(5000);
 		selenium.click("id=showMoreFieldsId");
 		selenium.click("css=#advsearch_moreDetails > div.grid_2 > select.f_left");
		selenium.select("css=#advsearch_moreDetails > div.grid_2 > select.f_left", "label=Pending");
		selenium.click("css=#advSearch_right > input:nth-child(1)");
		Thread.sleep(5000);
		selenium.click("css=#advSearchResults > table > tbody > tr:nth-child(3) > td:nth-child(1) > a");
		selenium.click("css=body > div:nth-child(11) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(1) > span");
		selenium.click("css=#sr_details_left > ul > li:nth-child(1) > a");
		Thread.sleep(2000);
		assertTrue(selenium.isVisible("css=#ui-dialog-title-sh_dialog_alert"));
		selenium.isTextPresent("The following Service Request has been identified as a self-service request and is 'Pending Approval'. Please review the request and make appropriate changes. When complete, set the status to 'Open' then save.");
		selenium.click("css=body > div:nth-child(12) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button > span");
		
		
 	}
		
//	private boolean isTextPresent(String textToBeVerified) {
//		// TODO Auto-generated method stub
//		   try{
//			   
//		        boolean b = selenium.isTextPresent(textToBeVerified);
//		        return b;
//		    }
//		    catch(Exception e){
//		        return false;
//		    }
//	}


	@After
	public void tearDown() throws Exception {
	 selenium.stop();
	}
}
