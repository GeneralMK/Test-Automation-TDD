package main.java.automationtesting.testing.tests;

import main.java.automationtesting.entities.KeywordAnnotation;
import main.java.automationtesting.entities.TestEntity;
import main.java.automationtesting.entities.testresult.TestResult;
import main.java.automationtesting.reporting.Narrator;
import main.java.automationtesting.testing.pageObjects.VumaServicesObjects;
import main.java.automationtesting.testing.pageObjects.VumatelReusableObjects;
import main.java.automationtesting.utilities.SeleniumDriverUtility;
import main.java.automationtesting.core.BaseClass;

/**
 * @author masixole
 */
@KeywordAnnotation(
        Keyword ="Services_Search_Service",
        createNewBrowserInstance = true
)

public class Vuma_Services extends BaseClass {

    private TestEntity testData;

    public Vuma_Services()
    {
        SeleniumDriverInstance = new SeleniumDriverUtility(SelectedBrowser);
    }

    public TestResult executeTest()
    {

        if(!SeleniumDriverUtility.navigateToURL(VumatelReusableObjects.URL())) {
            return Narrator.testFailed("Failed to navigate to "+VumatelReusableObjects.URL());
        }

        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.emailInput(), testData.getData("Email"))) {
            return Narrator.testFailed("Failed to enter email ");
        }

        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.passwordInput(), testData.getData("Password"))) {
            return Narrator.testFailed("Failed to enter password");
        }

        if(!SeleniumDriverUtility.clickElement(VumatelReusableObjects.loginBtn())) {
            return Narrator.testFailed("Failed to click search button ");
        }

        if (!SeleniumDriverUtility.clickElement(VumatelReusableObjects.vumaPortalDiv())) {
            return Narrator.testFailed("Failed to click 'Vumatel Portal Page' div ");
        }

        Narrator.stepPassedWithScreenShot("Navigated to Vumatel portal page");

        if(SeleniumDriverUtility.waitForElement(VumaServicesObjects.menuTab()))
        {
            if(!SeleniumDriverUtility.clickElement(VumaServicesObjects.menuTab()))
            {
                return Narrator.testFailed("Failed to click menu tab");
            }
        }



        if(SeleniumDriverUtility.waitForElement(VumaServicesObjects.servicesDropDown()))
        {
            if(!SeleniumDriverUtility.clickElement(VumaServicesObjects.servicesDropDown()))
            {
                return Narrator.testFailed("Failed to click services Drop Down");
            }
        }


        if(SeleniumDriverUtility.waitForElement(VumaServicesObjects.searchForService()))
        {
            if(!SeleniumDriverUtility.clickElement(VumaServicesObjects.searchForService()))
            {
                return Narrator.testFailed("Failed to click search For Service");
            }
        }

        if(SeleniumDriverUtility.waitForElement(VumaServicesObjects.searchByLocation()))
        {
            if(!SeleniumDriverUtility.clickElement(VumaServicesObjects.searchByLocation()))
            {
                return Narrator.testFailed("Failed to click search By Location");
            }
        }

        if(SeleniumDriverUtility.waitForElement(VumaServicesObjects.locationInputField()))
        {
            if(!SeleniumDriverUtility.enterText(VumaServicesObjects.locationInputField(),testData.getData("City")))
            {
                return Narrator.testFailed("Failed to click search By NetworkInputField");
            }
        }

        if(SeleniumDriverUtility.waitForElement(VumaServicesObjects.locationInputField()))
        {
            if(!SeleniumDriverUtility.clickElement(VumaServicesObjects.locationInputField(testData.getData("City"))))
            {
                return Narrator.testFailed("Failed to click search By NetworkInputField");
            }
        }




        return Narrator.finalizeTest("");

    }



}
