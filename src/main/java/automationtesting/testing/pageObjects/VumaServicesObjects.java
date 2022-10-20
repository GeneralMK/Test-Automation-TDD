package main.java.automationtesting.testing.pageObjects;
/**
 *
* @author masixole
*/

public class VumaServicesObjects {

    public static  String menuTab ()
    {
        return "//div[@class='v-toolbar__title toolbar-title ml-1 mt-0']//i";
    }

    public static String servicesDropDown()
    {
        return "//div[contains(text(),' Services')]";
    }

    public static String searchForService()
    {
        return "//div[@class='v-list-item__icon']//i[contains(text(),'search')]";
    }

    public static String searchByLocation()
    {
        return  "//div[@class='v-slide-group__wrapper']//span[contains(text(),'Search by Location')]";
    }
    public static String locationInputField()
    {
        return "//div[@class='ais-InstantSearch']//input";
    }

    public static String locationInputField(String text)
    {
        return "//div[@class='map-results scrolling-flex-wrapper elevation-1 ais-Hits']//a[contains(text(),'"+text+"')]";
    }


}
