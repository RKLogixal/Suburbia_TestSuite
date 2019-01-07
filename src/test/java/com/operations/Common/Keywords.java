package com.operations.Common;

import static org.testng.Assert.fail;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Keywords {

	private SoftAssert softAssert = new SoftAssert();
	private static Object LOCK = new Object();

	public By getObject(Properties p,String objectName,String objectType) throws Exception {
		//Find by xpath
		if(objectType.equalsIgnoreCase("XPATH")){

			return By.xpath(p.getProperty(objectName));
		}
		//find by class
		else if(objectType.equalsIgnoreCase("CLASSNAME")){

			return By.className(p.getProperty(objectName));

		}
		//find by name
		else if(objectType.equalsIgnoreCase("NAME")){

			return By.name(p.getProperty(objectName));

		}
		//Find by css
		else if(objectType.equalsIgnoreCase("CSS")){

			return By.cssSelector(p.getProperty(objectName));

		}
		//find by link
		else if(objectType.equalsIgnoreCase("LINK")){

			return By.linkText(p.getProperty(objectName));

		}
		//find by partial link
		else if(objectType.equalsIgnoreCase("PARTIALLINK")){

			return By.partialLinkText(p.getProperty(objectName));

		}
		else if(objectType.equalsIgnoreCase("ID")){

			return By.id(p.getProperty(objectName));

		}
		else
		{
			throw new Exception("Wrong object type");
		}
	}

	public void waitForPageLoadComplete(WebDriver driver, int specifiedTimeout) {
		Wait<WebDriver> wait = new WebDriverWait(driver, specifiedTimeout);
		wait.until(driver1 -> String
				.valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
				.equals("complete"));
	}

	public void CLICK(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log) throws Exception{
		//Perform click




		/*FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>()  {
			public WebElement apply(WebDriver driver) {
				WebElement ele = null;
				Keywords key =new Keywords();
				//key.getObject(p, objectName, objectType);

				try {
					ele = driver.findElement(key.getObject(p, objectName, objectType));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ele;


			}
		});*/

		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));

		//waitForPageLoadComplete(driver, 10);
		//((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
		myWaitVar.until(ExpectedConditions.elementToBeClickable(this.getObject(p, objectName, objectType)));
		Boolean Click = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
		if (Click.booleanValue()==true){

			synchronized (LOCK) {
				LOCK.wait(5000);
			//	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
				driver.findElement(this.getObject(p, objectName, objectType)).click();

				//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
				log.info("WebElement "+ objectName+" Successfully identified"  );
			}
		}
		else{

			test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
			log.info("WebElement "+ objectName+" Not found..."  );
			Assert.fail();

		}
	}

	public void CLICK_WAIT(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log) throws Exception{
		//Perform click

		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));

		//myWaitVar.until(ExpectedConditions.elementToBeClickable(driver.findElement(this.getObject(p, objectName, objectType))));
		Boolean Click = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();

		if (Click.booleanValue()==true){


			//driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
			driver.findElement(this.getObject(p, objectName, objectType)).click();
			Thread.sleep(5000);
			//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
			log.info("WebElement "+ objectName+" Successfully identified"  );
		}
		else{

			test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
			log.info("WebElement "+ objectName+" Not found..."  );
			Assert.fail();

		}
	}


	public void NAVIGATE_URL(WebDriver driver,String value,ExtentTest test,Logger log){
		if(value!=null){

			//driver.manage().window().maximize();
			driver.get(value);	


			log.info("Successfully navigated to URL : " + value );



		}

		else{

			test.fail(MarkupHelper.createLabel("Test step Navigate URL Failed", ExtentColor.RED));
			log.info("No URL Found...");
		}

	}
	public void ENTERTEXT(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
		driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
	}

	public void VERIFY_BROKEN_IMAGE(WebDriver driver,ExtentTest test,Logger log,String Sitename,String browser) throws Exception{

		Thread.sleep(5000);
		String Imageload=Sitename+"_"+browser+"_"+"Valid_images";
		String ImageBroke=Sitename+"_"+browser+"_"+"Broken_images";
		System.out.println();
		FileWriter writer = new FileWriter("./AutoGendata/Images_verification/"+Imageload+".txt", true);
		FileWriter writer_broke = new FileWriter("./AutoGendata/Images_verification/"+ImageBroke+".txt", true);
		List<WebElement> allImages = driver.findElements(By.tagName("img"));
		int size=allImages.size();
		log.info("Total Images found :" +size);


		for (WebElement image : allImages) {

			String validateimage = image.getAttribute("src");

			if (!(validateimage.equalsIgnoreCase(""))){


				Boolean imageLoaded1 = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image);
				if (!imageLoaded1)
				{

					writer_broke.write("Broken Image : " + validateimage);
					writer_broke.write("\r\n"); 
					//System.out.println("Broken Image : " + validateimage);
				}
				else
				{

					writer.write("Image Successfully loaded :" + validateimage);
					writer.write("\r\n"); 
					//System.out.println("Image Successfully loaded :" + validateimage);
				}
			}
			else{
				String validateimagealt = image.getAttribute("alt");
				Boolean imageLoaded1 = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image);
				if (!imageLoaded1)
				{

					writer_broke.write("Broken Image : " + validateimagealt);
					writer_broke.write("\r\n"); 
					//System.out.println("Broken Image : " + validateimagealt);
				}
				else
				{

					writer.write("Image Successfully loaded :" + validateimagealt);
					writer.write("\r\n"); 
					//System.out.println("Image Successfully loaded :" + validateimagealt);
				}
			}

		}

		writer.close();
		writer_broke.close();

	}

	public void VERIFY_BROKEN_LINK(WebDriver driver,ExtentTest test,Logger log,String Sitename,String browser) throws Exception{


		FileWriter Valid_links = new FileWriter("./AutoGendata/Links_verification/"+Sitename+"_"+browser+"_"+"validLinks.txt", true);
		FileWriter broken_links = new FileWriter("./AutoGendata/Links_verification/"+Sitename+"_"+browser+"_"+"brokenLinks.txt", true);

		List<WebElement> links=driver.findElements(By.tagName("a"));

		log.info("Total links are : "+links.size());

		Iterator<WebElement> it = links.iterator();

		while(it.hasNext()){
			String url =  it.next().getAttribute("href");

			if(!(url == null || url.isEmpty())){

				if(!url.startsWith("https://www.suburbia.com.mx")){
					log.info(url + " : This URL belongs to another domain, skipping it.");

				}
				else {
					URL url_1 = new URL(url);

					HttpURLConnection httpURLConnect=(HttpURLConnection)url_1.openConnection();

					httpURLConnect.setConnectTimeout(3000);

					httpURLConnect.connect();

					//httpURLConnect.c

					if(httpURLConnect.getResponseCode()==200)
					{

						Valid_links.write(url+" - "+httpURLConnect.getResponseMessage());
						Valid_links.write("\r\n");
						System.out.println(url+" - "+httpURLConnect.getResponseMessage());

					}
					if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)  

					{
						broken_links.write(url+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
						broken_links.write("\r\n");
						System.out.println(url+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
					}

				}
			}


		}	
		Valid_links.flush();
		Valid_links.close();
		broken_links.flush();
		broken_links.close();


	}

	public void MATH_VERIFCATION(WebDriver driver,ExtentTest test,Logger log,String Sitename,String browser,String value){

		String Final_price = driver.findElement(By.xpath(".//dd[contains(@class,'fl total omniTotalPrice')]")).getText();
		Boolean ap= driver.findElement(By.xpath(".//dd[contains(@class,'fl')]/span[contains(@class,'highlight')]")).isDisplayed();
		String Actaul_price =driver.findElement(By.xpath(".//dd[contains(@class,'fl')]/span[contains(@class,'highlight')]")).getText();
		//System.out.println(Final_price);
		//System.out.println(Actaul_price);

		String[] Generate_expected =Actaul_price.split("\\$");
		String part1 = Generate_expected[0]; 
		String part2 = Generate_expected[1]; 
		//System.out.println(part1);
		//System.out.println(part2);

		//String removedecimal_expected=part2.replaceAll(".","");

		String[] Generate_Actual =Final_price.split("\\$");
		String part11 = Generate_Actual[0]; 
		String part21 = Generate_Actual[1]; 
		//System.out.println(part11);
		//System.out.println(part21);

		//String removedecimal_Actual=part21.replaceAll(",","");

		float j= Float.parseFloat(part2);
		float k= Float.parseFloat(part21);
		int i= Integer.parseInt(value);
		double expected=j*i;
		DecimalFormat df = new DecimalFormat("0.00");
		String Formated = df.format(expected);

		log.info("Expected Price is :" + Formated);
		if(expected==k){

			log.info("Actual price is matching with expected price.Testcase passed..!!!");
			//logger.log(LogStatus.PASS, "Test Step Passed");
			//test.pass(MarkupHelper.createLabel("Test Step Passed", ExtentColor.GREEN));
		}
		else {

			log.info("Actual price is not matching with expected price.Testcase Failed..!!!");
			//logger.log(LogStatus.FAIL, "Test Step Failed");
			test.fail(MarkupHelper.createLabel("Math verfication Failed", ExtentColor.RED));

		}



	}
	public void VERIFY_TEXT_PRESENT(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log,String value){
		String Gettext;
		try {
			WebDriverWait myWaitVar = new WebDriverWait(driver,20);
			myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
			Gettext = driver.findElement(this.getObject(p,objectName,objectType)).getText();
			// Assert.assertEquals(Gettext,value);
			if (Gettext.equalsIgnoreCase(value))	{
				//test.pass(MarkupHelper.createLabel("TEXT : "+value+"Which you are looking for Webelement "+objectName+" has been found successfully..." , ExtentColor.GREEN));
				log.info("TEXT : "+value+"Which you are looking for Webelement "+objectName+" has been found successfully...");

			}	

			else {
				Assert.fail();
				throw new NoSuchFieldException("TEXT : "+value+"Which you are looking for Webelement"+objectName+"NOT found...!!!!");

			}


		}	catch (Exception e) {
			// TODO Auto-generated catch block
			//test.fail(MarkupHelper.createLabel("TEXT : "+value+"Which you are looking for Webelement"+objectName+"NOT found...!!!!", ExtentColor.RED));
			log.error("TEXT : "+value+"Which you are looking for Webelement"+objectName+"NOT found...!!!!");
			//log.error(e.printStackTrace());
		}

	}

	public void WAIT(WebDriver driver){

		WebDriverWait wait = new WebDriverWait(driver, 5);

	}

	public void WAITFORLOAD(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public void SLEEPWAIT() throws InterruptedException {
		Thread.sleep(5000);
	}



	public void NAVIGATE_BACK(WebDriver driver){

		driver.navigate().back();

	}
	public void CLOSE_BROWSER(WebDriver driver){

		driver.close();

	}

	public void QUIT_BROWSER(WebDriver driver){

		driver.quit();

	}

	public void VERIFY_WEBELEMENT_PRESENT(WebDriver driver,Properties p,String objectName,String objectType) throws Exception{

		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
		Boolean sss=driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
		System.out.println(objectName+objectType+ sss.booleanValue());

	}

	public void CLEAR_TEXTBOX(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log){

		try {
			driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
			driver.findElement(this.getObject(p,objectName,objectType)).clear();
			test.pass(MarkupHelper.createLabel("Textbox : "+objectName+"Which you are looking for has been found & cleared successfully " , ExtentColor.GREEN));
			log.info("Textbox : "+objectName+"Which you are looking for has been found & cleared successfully ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void GETATTRIBUTE(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{

		String Verify_text=	driver.findElement(this.getObject(p,objectName,objectType)).getAttribute("value");
		if(Verify_text.equalsIgnoreCase(value)){

		}
	}
	public void PRESS_ESC(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{

		Actions ESC = new Actions(driver);
		ESC.sendKeys(Keys.ESCAPE).build().perform();

	}

	public void REFRESH_PAGE(WebDriver driver)
	{

		driver.navigate().refresh();
	}


}


