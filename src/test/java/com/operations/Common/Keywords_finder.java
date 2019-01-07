	package com.operations.Common;
	
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.text.DecimalFormat;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Properties;
	import java.util.concurrent.TimeUnit;
	
	import org.apache.log4j.Logger;
	import org.apache.log4j.PropertyConfigurator;
	import org.openqa.selenium.Alert;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	
	import com.aventstack.extentreports.ExtentTest;
	import com.aventstack.extentreports.markuputils.ExtentColor;
	import com.aventstack.extentreports.markuputils.MarkupHelper;
	import com.testcases.driverscripts.Execute_MainScript;
	
	public class Keywords_finder {
	
		WebDriver driver;
		String parentWindowHandle;
		ExtentTest test;
		//ExtentReports extent;
		Logger log;
		//ExtentTest logger;
		public Keywords_finder(WebDriver driver,Logger log ){
			this.driver = driver;
			this.log=log;
			this.test=test;
			//this.extent=extent;
			//this.logger=logger;
		}
	
		Keywords key = new Keywords();
		Execute_MainScript exe = new Execute_MainScript();
	
		public void perform(Properties p,String operation,String objectName,String objectType,String value,String Sitename,String browser) throws Exception{
	
			switch (operation.toUpperCase()) {
			case "CLICK":
	
				key.CLICK(driver, p, objectName, objectType,test,log);
	
				break;
				
			case "CLICKS":
				
				key.CLICK_WAIT(driver, p, objectName, objectType,test,log);
	
				break;
	
			case "PRESS_ENTER" :
	
				driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(Keys.RETURN);
	
				break;
	
			case "PRESS_BACKSPACE" :
	
				driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(Keys.BACK_SPACE);
	
				break;
	
			case "NAVIGATE_URL":
	
				key.NAVIGATE_URL(driver, value, test, log);
	
				break;
	
			case "ENTERTEXT":
	
				key.ENTERTEXT(driver, p, objectName, objectType, value, test, log);
	
				break;
	
			case "GETTEXT" :
	
				key.GETATTRIBUTE(driver, p, objectName, objectType, value, test, log);
	
			case "VERIFY_TEXT_PRESENT":
	
				key.VERIFY_TEXT_PRESENT(driver, p, objectName, objectType, test, log, value);
	
				break;
	
			case "PRESS_ESC_BUTTON" :
				key.PRESS_ESC(driver, p, objectName, objectType, value, test, log);
	
				break;
	
			case "WAIT":
	
				key.WAIT(driver);
	
				break;
			case "":
				key.SLEEPWAIT();
				break;
	
			case "WAITFORLOAD":
	
				key.WAITFORLOAD(driver);
	
				break;
	
	
			case "BACK":
	
				key.NAVIGATE_BACK(driver);
	
				break;
	
			case "REFRESH_PAGE":
	
				key.REFRESH_PAGE(driver);
	
	
			case "CLOSE":
	
				key.CLOSE_BROWSER(driver);
	
				break;
	
			case "QUIT":
	
				driver.quit();
	
				break;
	
			case "VERIFY_WEBELEMENT_PRESENT":
	
				key.VERIFY_WEBELEMENT_PRESENT(driver, p, objectName, objectType);
	
				break;
			case "CLEAR_TEXT":
	
				driver.findElement(this.getObject(p,objectName,objectType)).clear();
	
				break;
	
			case "RIGHT_CLICK":
				Actions Right_click = new Actions(driver);
				Right_click.contextClick(driver.findElement(this.getObject(p,objectName,objectType)));
				Right_click.build().perform();
				break;
	
			case"VERIFY_ENABLED" :
	
				driver.findElement(this.getObject(p,objectName,objectType)).isEnabled();
	
	
			case "VERIFY_CHECK":
				driver.findElement(this.getObject(p,objectName,objectType)).isSelected();
				break;
	
	
			case "DROPDOWN_DESELECT" :
				WebElement mySelectElm = driver.findElement(this.getObject(p,objectName,objectType)); 
				Select mySelect= new Select(mySelectElm);
				mySelect.deselectAll();
	
	
				break;
	
			case "DROPDOWN_SELECT" :
				WebElement Drselect = driver.findElement(this.getObject(p,objectName,objectType)); 
				Boolean tt=Drselect.isDisplayed();
				Select mySelect1= new Select(Drselect);
				mySelect1.selectByVisibleText(value);
				System.out.println();
	
	
	
				break;
			case "CONTROL_BUTTON":
				Actions act = new Actions(driver);
				Keys.chord(Keys.CONTROL);
	
				break;
	
			case "SCROLL_UP" :
				JavascriptExecutor jse_UP = (JavascriptExecutor) driver;
				jse_UP.executeScript("window.scrollTo(0,750)", "");
				break;
	
			case "VERIFY_BROKEN_IMAGE" :
	
				key.VERIFY_BROKEN_IMAGE(driver, test, log, Sitename, browser);
	
				break;
	
	
			case"VERIFY_BROKEN_LINK" :
	
				key.VERIFY_BROKEN_LINK(driver, test, log, Sitename, browser);
	
				break;
	
			case "SCROLL_DOWN":
	
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				//jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				jse.executeScript("window.scrollBy(0,550)", "");
				break;
	
			case "RADIO_BUTTON_SELECT" :
				List<WebElement> radiolist = driver.findElements(By.xpath(".//input[contains(@type,'radio')]"));	
				System.out.println(radiolist.size());
				for (int i=0;i<radiolist.size();i++){
					// System.out.println(dr.get(i).getAttribute("id"));
					String temp_radio = radiolist.get(i).getAttribute("id");
					if (temp_radio.equals(value)){
						radiolist.get(i).click();
						if(radiolist.get(i).isSelected()==false)
						{
							radiolist.get(i).click();
						}
					}
				}
				break;
	
			case "MOUSEOVER" :
	
				WebDriverWait myWaitMouse = new WebDriverWait(driver,20);
				myWaitMouse.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p,objectName,objectType)));
				Boolean MOUSEOVER = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
				if (MOUSEOVER.booleanValue()==true){
	
					Actions Mouseaction = new Actions(driver);
					WebElement ele = driver.findElement(this.getObject(p,objectName,objectType));
					Mouseaction.moveToElement(ele).build().perform();
	
					//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
					log.info("WebElement "+ objectName+" Successfully identified"  );
				}
				else{
	
					test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
					log.info("WebElement "+ objectName+" Not found..."  );
					Assert.fail();
	
				}
				break;	
	
	
			case "MOUSEOVER_CLICK" :
				WebDriverWait myWaitmouseClick = new WebDriverWait(driver,20);
				myWaitmouseClick.until(ExpectedConditions.elementToBeClickable(this.getObject(p,objectName,objectType)));
				Boolean MOUSEOVER_CLICK = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
				if (MOUSEOVER_CLICK.booleanValue()==true){
	
					Actions MouseactionC = new Actions(driver);
					WebElement eleC = driver.findElement(this.getObject(p,objectName,objectType));
					MouseactionC.moveToElement(eleC).click().perform();
	
					//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
					log.info("WebElement "+ objectName+" Successfully identified"  );
				}
				else{
	
					test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
					log.info("WebElement "+ objectName+" Not found..."  );
					Assert.fail();
	
				}
	
				break;
	
			case "COMPARE_STRING" :
				List <WebElement> lst = driver.findElements(this.getObject(p,objectName,objectType));
				for (int j=0;j<lst.size();j++){
					String SS =  lst.get(j).getText();
					if(SS.startsWith("$"))
					{
						String[] parts = SS.split("");
						String part1 = parts[0]; 
						String part2 = parts[1]; 
	
						if(!part2.equals(value))
						{
							//	return false;
	
						}
	
					}
					else {
						String[] parts = SS.split(" ");
						String part1 = parts[0]; 
						String part2 = parts[1]; 
						System.out.println(part1);
						if(!part1.equals(value))
						{
							//return false;
	
						}
	
					}
				}
				break;
	
			case "MATH_VERIFICATION":
	
				key.MATH_VERIFCATION(driver, test, log, Sitename, browser, value);
	
				break;
	
	
			case "SWITCH_CHILD_WINDOW":
				parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
	
				WebDriver popup = null;
				Iterator<String> windowIterator = driver.getWindowHandles().iterator();
				while(windowIterator.hasNext()) { 
					String windowHandle = windowIterator.next(); 
					popup = driver.switchTo().window(windowHandle);
				}
				/*String Getpagettitle=driver.getTitle();
				if (!Getpagettitle.equals(value)){
					//return false;
	
				}
				driver.close();
				driver.switchTo().window(parentWindowHandle);
	
				 */
				break;
	
	
	
			case "SWITCH_WIN_CLOSE_POPUP":
				parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
				WebDriver Cpopup = null;
				Iterator<String> windowIterator_close = driver.getWindowHandles().iterator();
				while(windowIterator_close.hasNext()) { 
					String windowHandle_close = windowIterator_close.next(); 
					popup = driver.switchTo().window(windowHandle_close);
	
				}
				Boolean close =driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
				driver.findElement(this.getObject(p,objectName,objectType)).click();
				driver.switchTo().window(parentWindowHandle);
				break;
	
			case "SWITCH_CLOSE_ALERT":
	
				driver.switchTo().alert().dismiss();
	
				break;
	
			case "VERIFY_LINK":
				Boolean Verify_link_isDisplayed = driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
				if (Verify_link_isDisplayed.booleanValue()==true){
					driver.findElement(this.getObject(p,objectName,objectType)).click();
					Boolean Verify_element_isDisplayed = driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
					//return true;
				}
				else{
					//return false;
				}
	
	
			default:
	
	
				break;
			}
			//return true;
		}
	
		public By getObject(Properties p,String objectName,String objectType) throws Exception{
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
	}
