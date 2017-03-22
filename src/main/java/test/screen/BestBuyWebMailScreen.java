package test.screen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import test.tests.SingleSeleniumSolution;

public class BestBuyWebMailScreen
{
   SingleSeleniumSolution webController;
   protected static final Logger log = LoggerFactory.getLogger("WebMailScreen");
   protected static boolean exceed = true;
   protected static int mailNumber = 0;
   protected static String pMailTime = null;
   protected static boolean mailtoDelete = false;
   private static final String INPUT_USERNAME = "//table[@class='UsernamePasswordTable']//tr[1]//td[2]/input";
   private static final String INPUT_PASSWORD = "//table[@class='UsernamePasswordTable']//tr[2]//td[2]/input";
   private static final String BUTTON_SIGNIN = "css=input[value='Sign In']";
   private static final String NEWMAIL = "//button[@title='Write a new message (N)']//span[contains(text(), 'New')]";
   private static final String INBOX = "css=span[title='Inbox']";
   private static final String MAILSCOUNT = "//div[@aria-label='conversation']//div[starts-with(@id, '_ariaId_')][@tabindex='-1']";
   private static final String SETTINGS = "//div[2]//div[3]/div[1]/div[2]/div[2]//div[1]//td[2]//span";
   private static final String SETTINGS_OPTIONS = "//span[contains(text(), 'Options')]";
   private static final String SETTINGS_OPTIONS_SETTINGS = "//a[contains(text(), 'settings')]";
   private static final String SETTINGS_OPTIONS_SETTINGS_REGIONAL = "//a[contains(text(), 'regional')]";
   private static final String SETTINGS_FRAME = "//div[@id='mainFrame']/iframe[1]";
   private static final String SETTINGS_CURRENT_TIME_ZONE = "css=select[class='autoWidth minWidth multiLanguangeChar']";
   private static final String SETTINGS_CURRENT_TIME_ZONE_US = SETTINGS_CURRENT_TIME_ZONE + " option[value='Eastern Standard Time']";
   private static final String SETTINGS_SAVE = "//button[contains(text(), 'save')]";
   private static final String ORDERNUMBER = "//table[@class='x_wrapper']/tbody/tr[3]//div[2]//span";
   private static final String CREATEDDATE = "//table[@class='x_wrapper']/tbody/tr[3]//div[1]//td[3]/span";
   private static final String SKUDETAILS = "//table[@class='x_wrapper']//tr[6]//tr[2]//td[2]//div[1]//tr[1]/td[1]";
   private static final String SKU_QUANTITY = "//table[@class='x_wrapper']//tr[6]//tr[2]//td[2]//div[2]//tr[3]/td[1]";
   private static final String SHIPTYPE = "//table[@class='x_wrapper']//tr[4]/td/table/tbody/tr/td[4]";
   private static final String DELIVERDATE = "//table[@class='x_wrapper']//tr[6]//div[2]//tr[2]//td[2]//tr[3]";
   private static final String MAILDATE = "//span[@class='allowTextSelection']";
   public String user = "A1326949";
   public String password = "Bestby12$";
   AutomationBaseTest baseTest = new AutomationBaseTest();


   public BestBuyWebMailScreen(WebDriver webDriver)
   {
      this.webController = new SingleSeleniumSolution(webDriver);
   }


   public void openMail(String timeZone, String mailSubject)
   {
      boolean continues = false;
      boolean mailBox = isMailBoxUpdated(timeZone);
      int i = getMailsCount();
      log.info("Mail box is updated with new mails: " + mailBox);
      main: for (int z = 1; z <= i; z++)
      {
         boolean mTime = checkMailTime(timeZone, z);
         boolean mSubject = checkMailSubject(mailSubject, z);
         ArrayList<String> sMailDay = getMailDay();
         if ((mTime) || (sMailDay.get(4).equals("Today")))
         {
            continues = openMails(z, mSubject, mTime);
            if (continues)
            {
               break main;
            }
         }
         else
         {
            if (exceed)
            {
               z = 1;
            }
            if (( !mTime) && (z == 1))
            {
               mailBox = isMailBoxUpdated(timeZone);
               log.info("Mail box is updated with new mails: " + mailBox);
               continue main;
            }
            if (( !mTime) && (z == 2))
            {
               Assert.fail("Mail not came, failing the test script");
            }
         }
      }
      if (mailtoDelete)
      {
         String mailNeedToDelete = MAILSCOUNT + "[" + mailNumber + "]//button[@title='Delete']";
         String mail = getMailSubject(mailNumber);
         log.info("Deleting the mail with subject {} " + mail);
         webController.click(mailNeedToDelete);
      }
   }


   private boolean openMails(int z, boolean mSubject, boolean mTime)
   {
      boolean flag = false;
      // log.info("Mail No: '{}' time is acceptable: '{}'. Checking the subject", z, mTime);
      if (mSubject)
      {
         // log.info("Mail No: '{}' subject is matching: '{}', opening the mail", z, mSubject);
         log.info("Mail No: '{}' time is acceptable: '{}'. Subject is expected: '{}'. Opening the mail", z, mTime, mSubject);
         String mailNeedToOpen = MAILSCOUNT + "[" + z + "]";
         mailtoDelete = false;
         mailNumber = z;
         webController.click(mailNeedToOpen);
         flag = true;
      }
      else
      {
         // log.info("Mail No: '{}' subject is not matching: '{}'. Checking the next mail", z, mSubject);
         log.info("Mail No: '{}' time is acceptable: '{}'. Subject is expected: '{}'. Checking the next mail", z, mTime, mSubject);
      }
      return flag;
   }


   protected void deleteMail(boolean flag)
   {
      if (flag)
      {
         if (mailtoDelete)
         {
            String mailNeedToDelete = MAILSCOUNT + "[" + mailNumber + "]//button[@title='Delete']";
            String mail = getMailSubject(mailNumber);
            log.info("Deleting the mail with subject {}" + mail);
            webController.click(mailNeedToDelete);
         }
         else
         {
            log.info("Unable to delete the mail {}" + mailNumber);
         }
      }
   }


   public void doLoginToWebMail()
   {
      String url = "https://outlook.office365.com/owa/?realm=bestbuy.com";
      webController.navigateTo(url);
      log.info("Navigating to: " + url);
      webController.waitForElementVisible(INPUT_USERNAME, 30);
      log.info("Login into outlook account using User name: {" + user + "} with Password: { } ");
      webController.type(INPUT_USERNAME, user);
      webController.type(INPUT_PASSWORD, password);
      webController.click(BUTTON_SIGNIN);
      webController.waitForElementNotFound(BUTTON_SIGNIN, 30);
      webController.waitForElementVisible(NEWMAIL, 90);
      webController.click(INBOX);
   }


   private int getMailsCount()
   {
      webController.waitForElementVisible(MAILSCOUNT, 30);
      return webController.getElementsCount(MAILSCOUNT);
   }


   protected ArrayList<String> getMailTime(int mailValue)
   {
      ArrayList<String> values = new ArrayList<String>();
      String pMailTimeElement = MAILSCOUNT + "[" + mailValue + "]//div[2]/div[4]/span";
      String pMailTime = webController.getText(pMailTimeElement).trim().toLowerCase();
      String[] a = pMailTime.split(" ");
      String pDay = a[0];
      if (pDay.contains(":"))
      {
         Date dNow = new Date();
         SimpleDateFormat ft = new SimpleDateFormat("E");
         pMailTime = ft.format(dNow) + " " + pMailTime;
         a = pMailTime.split(" ");
         pDay = a[0];
      }
      values.add(pDay);
      values.add(a[1]);
      values.add(a[2]);
      values.add(a[1].replace(":", ""));
      return values;
   }


   private boolean checkMailSubject(String mailSubject, int z)
   {
      boolean flag = false;
      String pMailSubjectElement = MAILSCOUNT + "[" + z + "]//div[4]/div[3]/span[1]";
      String pMailSubject = webController.getText(pMailSubjectElement).trim();
      if (mailSubject.contains(pMailSubject))
      {
         flag = true;
      }
      return flag;
   }


   private String getMailSubject(int z)
   {
      String pMailSubjectElement = MAILSCOUNT + "[" + z + "]//div[4]/div[3]/span[1]";
      return webController.getText(pMailSubjectElement).trim();
   }


   private boolean checkMailTime(String timeZone, int z)
   {
      boolean flag = false;
      ArrayList<String> pTime = getMailTime(z);
      pTime.get(0);
      pMailTime = pTime.get(1);
      String pMailZone = pTime.get(2);
      int pMailTimeI = Integer.parseInt(pTime.get(3));
      String[] systemTime = timeZone.split(" ");
      if (systemTime[0].contains("/"))
      {
         exceed = true;
      }
      else
      {
         int sTime = Integer.parseInt(systemTime[0].replace(":", ""));
         String sTimeZone = systemTime[1];
         if (pMailZone.equalsIgnoreCase(sTimeZone))
         {
            if (pMailTimeI >= sTime)
            {
               // log.info("Mail No: {} time is {}", z, str);
               flag = true;
            }
            else
            {
               flag = false;
            }
         }
         else
         {
            flag = false;
         }
      }
      return flag;
   }


   private boolean isMailBoxUpdated(String timeZone)
   {
      boolean flag = false;
      for (int i = 1; i <= 10; i++)
      {
         ArrayList<String> pTime = getMailTime(1);
         String pMailZone = pTime.get(2);
         int pMailTimeI = Integer.parseInt(pTime.get(3));
         String[] systemTime = timeZone.split(" ");
         int sTime = Integer.parseInt(systemTime[0].replace(":", ""));
         String sTimeZone = systemTime[1];
         ArrayList<String> sMailDay = getMailDay();
         if (pMailZone.equalsIgnoreCase(sTimeZone))
         {
            if (pMailTimeI >= sTime)
            {
               flag = true;
               break;
            }
            else
            {
               reload();
               flag = false;
            }
         }
         else if (pTime.get(0).equals(sMailDay.get(0)) && sMailDay.get(4).equals("Today"))
         {
            flag = true;
            break;
         }
         else
         {
            reload();
            flag = false;
         }
      }
      return flag;
   }


   private ArrayList<String> getMailDay()
   {
      String temp[] = null;
      String var = null;
      ArrayList<String> date = new ArrayList<String>();
      String mailNeedToOpen = MAILSCOUNT + "[1]";
      webController.click(mailNeedToOpen);
      var = webController.getAttribute(MAILDATE, "title");
      temp = var.split(" ");
      date.add(temp[0]);
      date.add(temp[1]);
      date.add(temp[2]);
      date.add(temp[3].toLowerCase());
      var = webController.getText(MAILDATE).trim();
      temp = null;
      temp = var.split(", ");
      date.add(temp[0]);
      date.add(temp[1]);
      return date;
   }


   private void reload()
   {
      webController.reload();
      webController.delay(30);
      webController.waitForElementVisible(MAILSCOUNT, 90);
   }


   public String getTimeZone()
   {
      TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
      String timeFormat = "h:mm a";
      DateFormat dateFormat = new SimpleDateFormat(timeFormat);
      Calendar cal = Calendar.getInstance(timeZone);
      dateFormat.setTimeZone(cal.getTimeZone());
      return dateFormat.format(cal.getTime()).toLowerCase();
   }


   public void setTimeZoneToUSEastren()
   {
      webController.waitForElementVisible(SETTINGS, 60);
      webController.click(SETTINGS);
      webController.waitForElementVisible(SETTINGS_OPTIONS, 20);
      webController.click(SETTINGS_OPTIONS);
      webController.waitForElementVisible(SETTINGS_OPTIONS_SETTINGS, 20);
      webController.click(SETTINGS_OPTIONS_SETTINGS);
      webController.waitForElementVisible(SETTINGS_OPTIONS_SETTINGS_REGIONAL, 20);
      webController.click(SETTINGS_OPTIONS_SETTINGS_REGIONAL);
      webController.switchToFrame(SETTINGS_FRAME);
      webController.click(SETTINGS_CURRENT_TIME_ZONE_US);
      webController.select(SETTINGS_CURRENT_TIME_ZONE, SETTINGS_CURRENT_TIME_ZONE_US);
      webController.click(SETTINGS_SAVE);
   }


   public void validateDOTCOMOrder(String createdOrderNumber, String createdOrderDate, String orderType, String SKU, String orderDeliverDate)
   {
      String temp = null;
      String[] orderDate = null;
      webController.waitForElementVisible(ORDERNUMBER, 30);
      String orderNumber = webController.getText(ORDERNUMBER).trim().replaceAll("ORDER #", "").replaceAll("\n", "");
      baseTest.assertEquals(createdOrderNumber, orderNumber, "Order Number is not matching");
      orderDate = webController.getText(CREATEDDATE).trim().split("thanks for your order on");
      temp = orderDate[1].replace(".", "");
      baseTest.assertEquals(createdOrderDate, temp.trim(), "Order Creation date is not matching");
      temp = webController.getText(SHIPTYPE).trim();
      baseTest.assertEquals(orderType, temp, "Order type is not matching");
      temp = webController.getText(SKUDETAILS);
      orderDate = temp.split("SKU: ");
      temp = orderDate[1].trim();
      baseTest.assertEquals(SKU, temp, "SKU is not matching");
      temp = webController.getText(SKU_QUANTITY);
      baseTest.assertSame(Integer.parseInt(temp), 1, "SKU quantity is not matching");
      temp = webController.getText(DELIVERDATE);
      baseTest.assertEquals(orderDeliverDate, temp, "Deliver date is not matching");
   }


   public String getDeliverDate()
   {
      ArrayList<String> fDate = new ArrayList<String>();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
      Date date;
      try
      {
         date = sdf.parse(sdf.format(new Date()));
         String newDate = date.toString();
         String[] pDate = newDate.split(" ");
         fDate.add(pDate[0].toUpperCase());
         SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM");
         String a = sdf1.format(new Date());
         String[] dDate = a.split("-");
         fDate.add(" ");
         fDate.add(dDate[1]);
         fDate.add("/");
         fDate.add(dDate[0]);
      } catch (ParseException e)
      {}
      return fDate.get(0) + fDate.get(1) + fDate.get(2) + fDate.get(3) + fDate.get(4);
   }


   public String getOrderCreatedDate()
   {
      SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
      return sdf1.format(new Date());
   }
}