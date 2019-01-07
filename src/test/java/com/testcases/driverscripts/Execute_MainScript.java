package com.testcases.driverscripts;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.operations.Common.Readconfig;
import com.operations.Common.Script_executor;
import com.operations.Common.Xls_writer;
import com.operations.suburbia.Master_data1;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Execute_MainScript {

	Platform macOS;
	WebDriver webdriver=null;
	public Logger Applog;
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	Script_executor scre = new Script_executor();
	String browser_name;
	String Testcasenumber;
	String Sitename;
	Date date;
	Xls_writer xls_writer=new Xls_writer();
	Readconfig rc =new Readconfig();
	SimpleDateFormat dateFormat;
	Map<Integer, Object[]> Testcase_skipresults = new LinkedHashMap<Integer, Object[]>();
	Map<Integer, Object[]> Testscase_failresults = new LinkedHashMap<Integer, Object[]>();
	private SoftAssert softAssert = new SoftAssert();
	@BeforeTest()
	public void Pre_requisite() throws IOException{
		Applog=Logger.getLogger("Suburbia");
		PropertyConfigurator.configure("./resources/Log4j.properties");
		htmlreporter= new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/STMExtentReport_Suburbia.html");
		extent = new ExtentReports ();
		extent.attachReporter(htmlreporter);
		rc.getObjectRepository();
		date = new Date() ;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
	}

	@Parameters("browser")
	@BeforeTest

	public void beforeTest(String browser) throws IOException
	{

		if (browser.equalsIgnoreCase("firefox"))
		{

			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"/Browser_files/geckodriver-v0.23.0-win64/geckodriver.exe");
			webdriver = new FirefoxDriver();
			this.browser_name="Firefox";

			Applog.info("Execution started on Firefox" + dateFormat.format(date));


		} else if (browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"/Browser_files/chromedriver_win32/chromedriver.exe");
			webdriver = new ChromeDriver();
			Dimension d = new Dimension(414, 736);
			webdriver.manage().window().setSize(d);
			//webdriver.manage().window().maximize();

			this.browser_name="Chrome";

			Applog.info(" Execution started on Chrome" + dateFormat.format(date));

		}
		else if (browser.equalsIgnoreCase("safari"))
		{
			this.browser_name="Safari";

			Applog.info(" Execution started on Safari" + dateFormat.format(date));
			DesiredCapabilities cap = DesiredCapabilities.safari();

			cap.setBrowserName("safari");
			cap.setPlatform(Platform.MAC);
			String hub = rc.hub;

			webdriver = new RemoteWebDriver(new URL(hub), cap);
			webdriver.manage().window().maximize();

		}
		else if (browser.equalsIgnoreCase("IE"))
		{
			this.browser_name="IE";

			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			capabilities.setCapability(InternetExplorerDriver.
					INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") +"/Browser_files/IEDriverServer.exe");

			webdriver = new InternetExplorerDriver(capabilities);
			webdriver.manage().window().maximize();
			Applog.info(" Execution started on Internet explorer" + dateFormat.format(date));

		}

		else if (browser.equalsIgnoreCase("EDGE"))
		{
			this.browser_name="IE_EDGE";

			/*	DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			capabilities.setCapability(InternetExplorerDriver.
					INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			 */
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") +"/Browser_files/Edgedriver/MicrosoftWebDriver.exe");

			//webdriver = new InternetExplorerDriver(capabilities);
			WebDriver driver = new EdgeDriver();
			Applog.info(" Execution started on Internet explorer" + dateFormat.format(date));

		}
		else
		{
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}



	}	




	@Test(priority =1 ,dataProvider = "Fetch_Master_data",dataProviderClass=Master_data1.class)
	public void ExecuteTest(String Section,String Functionality,String Testcasenumber, String Testcase_description , String Executionmode,String Severity) throws Throwable  
	{

		this.Testcasenumber=Testcasenumber;
		this.Sitename="Suburbia";

		if(Executionmode.equalsIgnoreCase("Yes")){
			try {
				scre.Execute_script(Sitename,browser_name,"./Input_files/Actual_testcases/Suburbia/","./Output_files/"+dateFormat.format(date)+"/"+Sitename+"/"+browser_name+"/",
						"./Screenshots/"+dateFormat.format(date)+"/"+"Suburbia/"+browser_name+"/", webdriver,Section,Functionality, Testcasenumber, Testcase_description, Executionmode, Severity,extent,Applog);

			} catch (Exception e) {
				StringWriter stack = new StringWriter();
				e.printStackTrace(new PrintWriter(stack));
				xls_writer.GenerateFailReport(Testscase_failresults, "Suburbia", browser_name, Functionality, Testcasenumber, Severity,"./Failed_Reports/"+dateFormat.format(date)+"/"+"Suburbia/"+browser_name+"/");
				Assert.fail(stack.toString());
				Applog.error(stack.toString());
				stack.flush();
				softAssert.assertAll();

			}


		}
		else{

			xls_writer.GenearateSkipFile(Testcase_skipresults,Functionality, Testcasenumber, Severity,"./Output_files/"+dateFormat.format(date)+"/"+"Suburbia/"+browser_name+"/");
			Applog.info(Testcasenumber + " has been skipped for this execution...");
			throw new SkipException(Testcasenumber +" has been skipped..");
		}
	}

	@AfterMethod
	public void TestResults(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {

			test = extent.createTest(Sitename+"_"+browser_name+"_"+Testcasenumber);
			test.fail(MarkupHelper.createLabel(Testcasenumber+" has been failed....", ExtentColor.RED));

		}        
		else if (result.getStatus() == ITestResult.SKIP) {
			test = extent.createTest(Sitename+"_"+browser_name+"_"+Testcasenumber);
			test.skip(MarkupHelper.createLabel(Testcasenumber+" has been skipped for this execution...", ExtentColor.YELLOW));

		}
		else if (result.getStatus() == ITestResult.SUCCESS) {
			test = extent.createTest(Sitename+"_"+browser_name+"_"+Testcasenumber);
			test.pass(MarkupHelper.createLabel(Testcasenumber + "has been passed", ExtentColor.GREEN));

		}
	}


	@AfterSuite

	public void close() {
		Applog.info(" Execution started on"+ browser_name + dateFormat.format(date));
		extent.flush();


	}  


	@Parameters("browser")
	@AfterSuite

	public void closebrowser(String browser) throws IOException{
		if (browser.equalsIgnoreCase("firefox")){
			webdriver.close();	
			Runtime rt = Runtime.getRuntime();
			rt.exec("taskkill /F /IM geckodriver.exe");
		}
		else if(browser.equalsIgnoreCase("chrome")){
			webdriver.close();
			Runtime rt = Runtime.getRuntime();
			rt.exec("taskkill /F /IM chromedriver.exe");

		}
		else if(browser.equalsIgnoreCase("ie")){
			webdriver.close();
			Runtime rt = Runtime.getRuntime();
			rt.exec("taskkill /F /IM IEDriverServers.exe");
		}
		else if(browser.equalsIgnoreCase("safari")){
			webdriver.close();
		}

	}
}