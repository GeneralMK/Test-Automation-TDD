package test.java;

import main.java.automationtesting.TestMarshall;
import main.java.automationtesting.core.BaseClass;
import main.java.automationtesting.entities.Enums;
import org.testng.annotations.Test;

public class ServicesTestSuite extends BaseClass
{
    @Test
    public void services()
    {
        String testPack = System.getProperty("user.dir")+"\\testpacks\\VumaService_Search.xlsx";
        SelectedBrowser = Enums.BrowserType.CHROME;
        TestMarshall instance = new TestMarshall(testPack);
        instance.runTests();
    }

}
