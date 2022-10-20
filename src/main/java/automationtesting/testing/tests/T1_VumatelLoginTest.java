/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.automationtesting.testing.tests;

import main.java.automationtesting.TestMarshall;
import main.java.automationtesting.core.BaseClass;
import main.java.automationtesting.entities.KeywordAnnotation;
import main.java.automationtesting.entities.TestEntity;
import main.java.automationtesting.entities.testresult.TestResult;
import main.java.automationtesting.reporting.Narrator;
import main.java.automationtesting.testing.pageObjects.VumatelReusableObjects;
import main.java.automationtesting.utilities.SeleniumDriverUtility;

/**
 * @author masixole
 */
@KeywordAnnotation(
        Keyword ="VumatelLogin",
        createNewBrowserInstance = true
 )
public class T1_VumatelLoginTest extends BaseClass
{
    private TestEntity testData;

    public T1_VumatelLoginTest() {
        SeleniumDriverInstance = new SeleniumDriverUtility(SelectedBrowser);
    }

    public  TestResult executeTest()  {
        if(!SeleniumDriverUtility.navigateToURL(VumatelReusableObjects.URL())) {
            return Narrator.testFailed("Failed to navigate to "+VumatelReusableObjects.URL());
        }

        Narrator.stepPassedWithScreenShot("Navigated to Vumatel");

        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.emailInput(), TestMarshall.getTestData().getData("Email"))) {
            return Narrator.testFailed("Failed to enter email ");
        }

        Narrator.stepPassedWithScreenShot("Email entered");

        if(!SeleniumDriverUtility.enterText(VumatelReusableObjects.passwordInput(), TestMarshall.getTestData().getData("Password"))) {
            return Narrator.testFailed("Failed to enter password");
        }

        Narrator.stepPassedWithScreenShot("Password entered");

        if(!SeleniumDriverUtility.clickElement(VumatelReusableObjects.loginBtn())) {
            return Narrator.testFailed("Failed to click search button ");
        }

        if (!SeleniumDriverUtility.validateElementText(VumatelReusableObjects.vumaPortalDiv(), "VUMA Admin Portal")) {
            return Narrator.testFailed("Failed to navigate to page selection page ");
        }

        return Narrator.finalizeTest("Login Successful");
    }
}
