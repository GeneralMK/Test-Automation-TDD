package test.java;

import main.java.automationtesting.TestMarshall;
import main.java.automationtesting.core.BaseClass;
import main.java.automationtesting.entities.Enums;
//import org.junit.Test;
import org.testng.annotations.Test;

/**
 *
 * @author masixole kondile
 */
public class TestSuite extends BaseClass {
    @Test
    public void Test() {
        String testPack = System.getProperty("user.dir")+"\\testpacks\\VumatelPack.xlsx";
        SelectedBrowser = Enums.BrowserType.CHROME;
        TestMarshall instance = new TestMarshall(testPack);
        instance.runTests();
    }

}
