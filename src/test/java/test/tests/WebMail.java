package test.tests;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import test.screen.BestBuyWebMailScreen;
import test.screen.TWCWebMailScreen;

public class WebMail
{
   protected static final Log log = LogFactory.getLog("WebMail");
   WebDriver driver = null;
   SingleSeleniumSolution webDriver = new SingleSeleniumSolution(driver);
   private TWCWebMailScreen webMail;
   private BestBuyWebMailScreen bbyMail;


   @BeforeMethod(alwaysRun = true)
   public void beforeMethod(Method method)
   {
      webDriver = SingleSeleniumSolution.buildWebDriver("https://outlook.office365.com/owa/?realm=bestbuy.com", "chrome", method);
      bbyMail = new BestBuyWebMailScreen(webDriver.getWebDriver());
   }


   @AfterMethod(alwaysRun = true)
   public void afterMethod()
   {
      webDriver.quit();
   }


   @Test
   public void testMail()
   {
      bbyMail.doLoginToWebMail();
      bbyMail.openMail("5:39 pm", "We’ve received your order, but it's not quite ready! Order #BBY01-783119000301");
      bbyMail.validateDOTCOMOrder("BBY01-783119000301", bbyMail.getOrderCreatedDate(), "Store Pickup Items", "2543144", bbyMail.getDeliverDate());
   }


   @Test
   private void getDeliverDate()
   {
      log.info(bbyMail.getOrderCreatedDate());
      log.info(bbyMail.getDeliverDate());
   }


   @Test
   public void testWebMail()
   {
      webMail = new TWCWebMailScreen(webDriver.getWebDriver());
      webMail.doLoginToWebMail();
      webMail.sendMail();
   }
}
