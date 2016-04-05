package com.example.tests;


import static org.junit.Assert.*;
import java.net.HttpURLConnection;
import java.net.URL;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.server.RemoteControlConfiguration;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


@SuppressWarnings("deprecation")
public class PostDeploymentTests {
	//Variables for Post deploymnet tests
	//can be used across methods.
	private Selenium selenium;
	private String site = "https://311hub.miamidade.gov/";
	private String loginUserID = "c203036";
	private String longPwd = "something"; 
	private String recipients = "rajiv@miamidade.gov";
	private void ln (Object test){
		System.out.println(test);
	}
		
	//private String longPwd = "password";
	//private String pageLoadTime= "50000";
	public class SimpleOnFailed extends TestWatcher {
	    @Override
	    protected void failed(Throwable e, Description description) {
	    	ln("failed");
	     }
	}
		
	//private String longPwd = "password";
	//private String pageLoadTime= "50000";
	
	
	public static boolean isMyServerUp(){
		try {
			URL uri = new URL ("http://localhost:4444/wd/hub/status");
		
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
//			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.getInputStream();
			int HttpResult = connection.getResponseCode();
		    if(HttpResult == HttpURLConnection.HTTP_OK) return true;
		    else return false;
		  
		}catch (Exception e){
			return false;
		} 		
	}
	
	private Thread myThread = new Thread() {
	    public void run() {
	        try {
	        	Process P = Runtime.getRuntime().exec("cmd /c start javaw -jar C:\\users\\angel.martin.MIAMIDADE\\Downloads\\selenium-java-2.52.0\\selenium-2.52.0\\selenium-server-standalone-2.52.0.jar -trustAllSSLCertificates");
	        	P.waitFor();
				System.out.println("Sucessfully started selenium server");
	        	
	        } catch(Exception e) {
	            System.out.println(e);
	        }
	    }  
	};
	 
	
	@Before
	public void startServer () throws Exception {
//		SendEmail.send("rajiv@miamidade.gov","test is starting", "test is starting");
		
		myThread.start();

		int c = 0;
		do {
			Thread.sleep(1000);
			c++;
			} while (!isMyServerUp() && c < 10);
		
		if (c>10) throw new RuntimeException("Failure to contact selenium sever after ten attempts");
	 
		RemoteControlConfiguration settings = new RemoteControlConfiguration();
		settings.setTrustAllSSLCertificates(true);
		
		selenium = new DefaultSelenium("localhost", 4444, "*googlechrome C:/Program Files (x86)/Google/Chrome/Application/chrome.exe" , site);              
         
		
        selenium.start();
	}
	
//	@Test
	public void login() throws Exception {
		try{
		selenium.open(site);
		selenium.type("id=iUsername", "c203036");
		selenium.type("id=iPassword", "hahahaha147");
		selenium.click("id=btnLogin");
		Thread.sleep(8000);
		selenium.isTextPresent("Popular Searches");
		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
//	@Test
	public void ValidateAddress() throws Exception {
		try{
		login();
		selenium.click("//input[@value='']");
		selenium.type("//input[@value='']", "9920 sw 73rd st");
		selenium.click("//input[@value='Search']");
		selenium.isElementPresent("css=#answer_hub > div:nth-child(1) > span > input.ic_valid.button_icon.visibility_visible");
		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
//	@Test
	public void GeoInfoTab() throws Exception {
		try{
		ValidateAddress();
		selenium.click("id=geo_info_district");
		assertTrue(selenium.isTextPresent("District"));
		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
	@Test
	public void OpenSRInAnswerHub() throws Exception {
		try{
		ValidateAddress();
		selenium.type("xpath=(//input[@type='text'])[6]", "Bulky");
		selenium.click("xpath=(//input[@value='Search'])[3]");
		selenium.click("link=BULKY TRASH REQUEST");
		selenium.click("xpath=(//button[@type='button'])[3]");
		assertTrue(selenium.isVisible("css=#sr_details > span > div:nth-child(1) > h4"));
		assertTrue(selenium.isVisible("css=#sr_details > div:nth-child(22) > h4"));
		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}	
	
	@Test
	public void multiple() throws Exception {
		try{
		int times = 15;
		
		for (int i = 0; i < times; i++){
			this.OpenSRInAnswerHub();
			ln(i+1);
		}
		}catch (Exception e){
	System.out.println(e);
    SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
    Assert.fail();
	}}
	
//	@Test
	public void ValidateinWCS() throws Exception {
		try{
		login();
		selenium.type("css=#answer_hub > div:nth-child(1) > span > input.ic_field.h24.address_reset", "9910 sw 73rd st");
		selenium.click("css=#answer_hub > div:nth-child(1) > span > input.submit.h32.button.blue");
		Thread.sleep(5000);
		selenium.click("css=#wcsTabLi > a");
		selenium.click("css=#wcs_right > input:nth-child(1)");
		Thread.sleep(1000);
		selenium.isTextPresent("11558262");
		}catch (Exception e){
			System.out.println(e);
			SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
			Assert.fail();
		}}	
//	@Test
	public void MasterClr() throws Exception {
//		try{
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
		selenium.click("css=#sr_details > div.grid_2.omega > input.button.blue");
		selenium.click("xpath=(//button[@type='button'])[5]");
		selenium.isVisible("css=div.grid_5.alpha > input..error");
		selenium.isVisible("css=div.grid_5.alpha > input..error");
//		}catch (Exception e){
//            System.out.println(e);
//            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
//            Assert.fail();
//        }}
	}
// 	@Test	
 	public void SaveSr() throws Exception {
		try{
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
		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }} 	
//	@Test	
 	public void OpenSrBasicSearch() throws Exception {
 		try{
 		login();
 		selenium.click("link=Basic Search");
 		selenium.click("name=ServiceRequestType");
		selenium.type("name=ServiceRequestType", "BULKY TRASH REQUEST - MD");
		selenium.click("id=createdStartDate");
		selenium.type("id=createdStartDate", "-6");
		selenium.click("css=#advSearch_right > input[name=\"search\"]");
		Thread.sleep(6000);
		selenium.click("css=#advSearchResults > table > tbody > tr:nth-child(1) > td:nth-child(1) > a");
		selenium.click("css=body > div:nth-child(11) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(1) > span");
		Thread.sleep(5000);
		System.out.println(selenium.getValue("css=#editorDiv > div.app_container > div.right_column.grid_2 > span > input.search"));
		selenium.getValue("css=#editorDiv > div.app_container > div.right_column.grid_2 > span > input.search");
 		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
// 	@Test
	public void FeildSort() throws Exception {
		try{
		selenium.open(site);
		selenium.type("id=iUsername", "c203036");
		selenium.type("id=iPassword", "hahahaha147");
		selenium.click("id=btnLogin");
		Thread.sleep(8000);
		selenium.click("css=body > div.container_12 > div.banner.grid_12 > ul > li:nth-child(6) > a");
		selenium.type("name=ServiceRequestType", "POTHOLE - MD");
		selenium.click("id=showMoreFieldsId");
		selenium.select("css=#advsearch_moreDetails > div:nth-child(3) > select", "Locked");
		selenium.type("id=createdStartDate", "-1");
		selenium.click("css=#advSearch_right > input:nth-child(1)");
		Thread.sleep(1000);
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
		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
 //    @Test
 	public void ViewReport() throws Exception {
 		try{
 		login();
 		selenium.click("link=Basic Search");
		selenium.click("name=ServiceRequestType");
		selenium.type("name=ServiceRequestType", "BULKY TRASH REQUEST - MD");
		selenium.click("id=createdStartDate");
		selenium.type("id=createdStartDate", "-1");
		selenium.click("id=showMoreFieldsId");
		selenium.select("css=#advsearch_moreDetails > div:nth-child(3) > select", "Locked");
		selenium.click("css=#advSearch_right > input[name=\"search\"]");
		Thread.sleep(500);
		selenium.click("css=#advSearchResults > table > tbody > tr:nth-child(1) > td:nth-child(1) > a");
		selenium.click("css=body > div:nth-child(11) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(2) > span");
		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
    //	@Test
 	public void Duplicate() throws Exception {
 		try{
 		login();
 		selenium.click("link=Service Hub");
		selenium.click("id=srTypeID");
		selenium.type("id=srTypeID", "BOAT STORAGE - MD");
		selenium.click("xpath=(//input[@value=''])[7]");
		selenium.type("css=#sr_details > div.grid_5.alpha > span.input_clear > input.ic_field.h24", "4405 SW 129 AVE");
		selenium.click("xpath=(//input[@value='Search'])[6]");
		Thread.sleep(8000);
		assertTrue(selenium.isElementPresent("css=#sr_details_right > input.red_button.button.blue"));
 		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
// 	@Test
 	public void OutofServiceArea() throws Exception {
 		try{
 		login();
 		selenium.click("link=Service Hub");
		selenium.click("id=srTypeID");
		selenium.type("id=srTypeID", "BOAT STORAGE - MD");
		selenium.click("css=#srTypeList > span > input.submit.h23_submit.button.blue");
		selenium.type("css=#sr_details > div.grid_5.alpha > span.input_clear > input.ic_field.h24", "1022 adams drive");
		selenium.click("xpath=(//input[@value='Search'])[6]");
		Thread.sleep(7000);
		selenium.isTextPresent("Outside Service Area.");
 		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", ""+e.getMessage());
            Assert.fail();
        }} 	
// 	@Test
 	public void apporvalProcess() throws Exception {
 		try{
 		selenium.open(site);
 		selenium.type("id=iUsername", "c203036");
		selenium.type("id=iPassword", "hahahaha147");
		selenium.click("id=btnLogin");
		Thread.sleep(8000);
 		selenium.click("link=Basic Search");
 		Thread.sleep(3000);
 		selenium.click("id=showMoreFieldsId");
 		selenium.click("css=#advsearch_moreDetails > div.grid_2 > select.f_left");
		selenium.select("css=#advsearch_moreDetails > div.grid_2 > select.f_left", "label=Pending");
		selenium.click("css=#advSearch_right > input:nth-child(1)");
		Thread.sleep(5000);
		selenium.click("css=#advSearchResults > table > tbody > tr:nth-child(3) > td:nth-child(1) > a");
		selenium.click("css=body > div:nth-child(11) > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button:nth-child(1) > span");
		Thread.sleep(5000);
		selenium.isTextPresent("The following Service Request has been identified as a self-service request and is 'Pending Approval'. Please review the request and make appropriate changes. When complete, set the status to 'Open' then save.");
 		}catch (Exception e){
            System.out.println(e);
            SendEmail.send("angel.martin@miamidade.gov", "test", e.getMessage());
            Assert.fail();
        }}
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

// 	@Test
	public void message() throws Exception {
		SendEmail.send("angel.martin@miamidade.gov,rajiv@miamidade.gov","Hello my Friend", "it works jejejejejeje");
	}
	@After
	public void tearDown() throws Exception {
	 selenium.stop();
	 selenium.shutDownSeleniumServer();
	 ln("server successfully shut down.");
	}
}
