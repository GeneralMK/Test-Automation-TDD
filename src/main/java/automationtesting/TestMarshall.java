package main.java.automationtesting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Predicate;

//import com.gargoylesoftware.htmlunit.javascript.host.file.File;
import main.java.automationtesting.entities.Device;
import main.java.automationtesting.entities.Enums;
import main.java.automationtesting.entities.TestClass;
import main.java.automationtesting.entities.TestEntity;
import main.java.automationtesting.entities.testresult.TestResult;
import main.java.automationtesting.reporting.Narrator;
import main.java.automationtesting.utilities.ExcelReaderUtility;
import main.java.automationtesting.utilities.SeleniumDriverUtility;
import org.apache.xpath.operations.String;
import java.lang.*;

/**
 * @author masixole
 */

public class TestMarshall {

    private static List<TestEntity> TESTDATALIST;
    private static final List<TestResult> resultsList = new ArrayList();
    private final List<Device> DeviceList;
    private static Boolean _isDataSet;
    private static TestEntity testData;
    private String testName;
    private static int iteration;
    private static boolean isJenkins = false;
    private static Enums.TestType curTestType;

    public static void setTestType(Enums.TestType type) {
        curTestType = type;
    }

    public static Enums.TestType getTestType() {
        return curTestType;
    }

    public static void setIsJenkins(boolean jenkinsRun) {
        isJenkins = jenkinsRun;
    }

    public static boolean getIsJenkins() {
        return isJenkins;
    }

    public static void setIsDataSet(Boolean isDataSet) {
        _isDataSet = isDataSet;
    }

    public static void testData(TestEntity testdata) {
        testData = testdata;
    }

    public void testName(String testname) {
        this.testName = testname;
    }

    public void iteration(int Iteration) {
        iteration = Iteration;
    }

    public void addToTestDataList(TestEntity data) {
        TESTDATALIST.add(data);
    }

    public static void addToTestResultsList(TestResult data) {
        resultsList.add(data);
    }

    public List<TestResult> getResultsList() {
        return this.resultsList;
    }

    public static List<TestEntity> getTestDataList() {
        return TESTDATALIST;
    }

    public static int getIteration() {
        return iteration;
    }
    private static Enums.ResultStatus previousTestResult;

    public static Boolean getIsDataSet() {
        return _isDataSet;
    }

    public static TestEntity getTestData() {
        return testData;
    }

    public String getTestName() {
        return this.testName;
    }

    {
        readProjectProps();
        iteration = 0;
    }

    public TestMarshall(java.lang.String ExcelPath) {
        Narrator.setup(ExcelPath);
        TESTDATALIST = ExcelReaderUtility.getTestDataFromExcelFile(ExcelPath);
        this.DeviceList = null;
    }

//    public TestMarshall(String ExcelPath, List<Device> Devices) {
//        Narrator.setup(ExcelPath);
//        TESTDATALIST = ExcelReaderUtility.getTestDataFromExcelFile(ExcelPath);
//        this.DeviceList = Devices;
//    }

    public TestMarshall(java.lang.String ExcelPath, String Test) {
        Narrator.setup(ExcelPath);
        this.testName = Test;
        TESTDATALIST = ExcelReaderUtility.getTestSet(ExcelPath);
        this.DeviceList = null;
    }

    public TestMarshall(java.lang.String ExcelPath, String Test, List<Device> Devices) {
        Narrator.setup(ExcelPath);
        this.testName = Test;
        TESTDATALIST = ExcelReaderUtility.getTestSet(ExcelPath);
        this.DeviceList = Devices;
    }

    public void runTests() {
        for (TestEntity TestData : TESTDATALIST) {

            iteration++;

            //Will skip test if the keyword CONTAINS ';'
            if(TestData.getTestMethod().contains(";")) continue;
            testData = TestData;
            TestClass currentTest = null;
            TestResult testResult = null;

            Narrator.addTest(testData.getTestCaseID(), testData.getTestDescription());
            currentTest = new TestClass(testData);
            testResult = currentTest.runTest();

            if (previousTestResult == Enums.ResultStatus.FAIL && currentTest.getBlockableStatus()) {
                Narrator.blockedTest();
                continue;
            }

            previousTestResult = (testResult == null) ? Enums.ResultStatus.FAIL : testResult.getResultStatus();
            resultsList.add(testResult);
        }
        tearDown();
    }

    public void tearDown() {
        if (SeleniumDriverUtility.getDriver() != null) {
            SeleniumDriverUtility.getDriver().quit();
        }

        if (getIsJenkins()) {
            //copyReportsDirectoryToNetworkDrive();
        }
    }

    private static void readProjectProps() {
        Properties prop = new Properties();
        InputStream input = null;
        generateProjectProp();
        try {
            input = new FileInputStream("project.properties");
            prop.load(input);

            Narrator.setProjectName(prop.getProperty("projectName"));
        } catch (Exception e) {
            Narrator.logError("Could not find project.properties file with projectName field");
            Narrator.setProjectName("Unclaimed");
        }
    }

    private static void generateProjectProp() {
        try {
            File f = new File(System.getProperty("user.dir") + "\\project.properties");
            if (!f.exists()) {
                f.createNewFile();
                java.lang.String projectDefault = "projectName=Unclaimed";
                BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\project.properties"));
                writer.write(projectDefault);

                writer.close();
            }

        } catch (Exception e) {
            Narrator.logError("Could not generate project.properties");
        }
    }

    public TestResult checkResults() {
        Predicate<TestResult> checkStatus = c -> c.getResultStatus().equals(Enums.ResultStatus.FAIL);
        Optional<TestResult> statusStream = resultsList.stream().filter(checkStatus).findFirst();
        if (!statusStream.isPresent()) {
            return new TestResult(resultsList.get(0).getTestEntity(), Enums.ResultStatus.PASS, "Passed", resultsList.get(0).getTestDuration());
        } else {
            return new TestResult(resultsList.get(0).getTestEntity(), Enums.ResultStatus.FAIL, "At least 1 device run failed", resultsList.get(0).getTestDuration());
        }
    }
}
