package test.screen;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import test.tests.SingleSeleniumSolution;

public class SubAccountUserMgntWithGmailScreen
{
   protected SingleSeleniumSolution webController;
   public static final String LINK_GMAILSIGNIN = "//a[@data-g-label='Sign in']";
   public static final String GMAIL_USERNAME = "//*[@id='Email']";
   public static final String GMAIL_NEXT = "//*[@id='next']";
   public static final String GMAIL_PASSWORD = "//*[@id='Passwd']";
   public static final String GMAIL_SIGNIN_BUTTON = "css=input[id='signIn']";
   public static final String GMAIL_SIGNINREMENBER = "//label[@class='remember']/input";
   public static final String GMAIL_SWITCHTODIFFERENTACOUNT = "//*[@class='switch-account']/a[1]";
   public static final String GMAIL_ADDACOUNT = "//ul[@id='account-chooser-options']/li[1]/a[1]";
   public static final String GMAIL_BACKTOINBOX = "css=div[data-tooltip='Back to Inbox']";
   public static final String GMAIL_TWC_VERIFYEMAILADDRESS = "//*[@alt='Register for a TWC ID']";
   public static final String TWC_RIGESTER = "css=a[class='btn btn-large btn-blue']";
   public static final String TWC_RIGESTER_USERNAME = "css=input[name='username']";
   public static final String TWC_RIGESTER_PASSWORD = "css=input[name='password']";
   public static final String TWC_RIGESTER_CONFIRMPASSWORD = "css=input[name='confirmPassword']";
   public static final String TWC_ACCEPTTERMS_CONDITIONS = "css=input[name='termsAndConditionsAcceptance']";
   public static final String TWC_BUTTON_CONTINUE = "css=button[name='_eventId_continue']";
   public static final String TWC_ERRORCONTENT = "//*[@id='error-section']/div/p[1]";
   public static final String TWC_ERRORCODE = "//*[@id='error-section']/div/div/span[1]";
   public static final String TWC_ERRORNAME = "//*[@id='error-section']/h2";
   public static final String GMAIL_CURRENTMAIL_TIME = "//table//tbody//tr[1]//td[8]//b";
   public static final String TWC_SECURITY_QUESTIONONE = "css=input[name='answer1']";
   public static final String TWC_SECURITY_QUESTIONTWO = "css=input[name='answer2']";
   public static final String BUTTON_TWC_SECURITY_CONTINUE = "css=button[name='_eventId_continue']";
   public static final String TWC_SECURITY_SUCCESSMESSAGE = "//div[class='form-columns confirmation-info']//legend";
   public static final String GMAIL_DELETE_CURRENTMAIL = "css=div[data-tooltip='Delete']";
   public static final String GMAIL_CURRENTMAIL_NAME = "//div[@role='banner']//div[4]";
   public static final String GMAIL_SIGNOUT = "//div[@role='banner']//div[4]//div[3]//div[2]/a";
   public static final String ADD_USERSTAB_FIRSTNAME_ERROR = "//li[contains(text(), 'This username is already in use. Please try again.')]";
   public static final String TWC_CONFIRMATION_SUCCESS = "//*[@id='main-section']/div/section/form/fieldset[2]/button";
   protected static final Logger log = LoggerFactory.getLogger(SubAccountUserMgntWithGmailScreen.class);


   public void gmailSignin(String gmailUserName, String gmailPassword, String searchSubjectContent, String nameToRigester, String passwordTOSet, String securityQuestionOne, String securityQuestionTwo)
   {
      String systemTime = getSystemTime();
      log.info("Current System Time: " + systemTime);
      systemTime = systemTime.replace(":", "").replace(" am", "").replace(" pm", "").replace(" ", "");
      if (webController.isElementVisible(GMAIL_CURRENTMAIL_NAME))
      {
         log.info("Logout the existing user...");
         webController.waitForElementVisible(GMAIL_CURRENTMAIL_NAME);
         webController.click(GMAIL_CURRENTMAIL_NAME);
         webController.waitForElementVisible(GMAIL_SIGNOUT);
         webController.click(GMAIL_SIGNOUT);
         webController.delay(5);
      }
      boolean flag = false;
      if (webController.isElementVisible(LINK_GMAILSIGNIN))
      {
         log.info("Clicking on Gmail Sign in Link");
         webController.click(LINK_GMAILSIGNIN);
         webController.waitForElementVisible(GMAIL_USERNAME, 60);
         flag = true;
      }
      else if (webController.isElementVisible(GMAIL_SWITCHTODIFFERENTACOUNT))
      {
         log.info("Some other user has logged in, switching to {} account", gmailUserName);
         log.info("Clicking on Switch to different account");
         webController.click(GMAIL_SWITCHTODIFFERENTACOUNT);
         webController.delay(3);
         webController.waitForElementVisible(GMAIL_ADDACOUNT, 60);
         log.info("Clicking on Add Account button");
         webController.click(GMAIL_ADDACOUNT);
         webController.waitForElementVisible(GMAIL_USERNAME, 60);
         flag = true;
      }
      else if (webController.isElementVisible(GMAIL_USERNAME))
      {
         flag = true;
      }
      /* String loggedinUserName = webController.getText("css=span[id='reauthEmail']");
       * if (!(loggedinUserName.equalsIgnoreCase(gmailUserName)))
       * {
       * webController.waitForElementVisible(GMAIL_SWITCHTODIFFERENTACOUNT, 60);
       * log.info("Some other user had logged in, switching to {}", gmailUserName);
       * webController.click(GMAIL_SWITCHTODIFFERENTACOUNT);
       * webController.waitForElementVisible(GMAIL_ADDACOUNT, 60);
       * webController.click(GMAIL_ADDACOUNT);
       * flag = true;
       * }
       * else
       * {
       * log.info("Setting the password for user: {}", gmailUserName);
       * webController.type(GMAIL_PASSWORD, gmailPassword);
       * log.info("Clicking on Sign in Button");
       * webController.setCheckBoxState(GMAIL_SIGNINREMENBER, false);
       * webController.click(GMAIL_SIGNIN_BUTTON);
       * log.info("login to the Gmail: {} account successfully", gmailUserName);
       * getEmailSubject(searchSubjectContent, nameToRigester, passwordTOSet, securityQuestionOne, securityQuestionTwo,
       * systemTime);
       * flag = false;
       * } */
      if (flag == true)
      {
         setGmailIDPassword(gmailUserName, gmailPassword, searchSubjectContent, nameToRigester, passwordTOSet, securityQuestionOne, securityQuestionTwo, systemTime);
      }
   }


   public void setGmailIDPassword(String gmailUserName, String gmailPassword, String subjectContentNeedtoFind, String nameToRigester, String passwordTOSet, String securityQuestionOne, String securityQuestionTwo, String systemTime)
   {
      log.info("Setting the user name: {}", gmailUserName);
      webController.waitForElementVisible(GMAIL_USERNAME);
      webController.type(GMAIL_USERNAME, gmailUserName);
      if (webController.isElementVisible(GMAIL_NEXT))
      {
         webController.click(GMAIL_NEXT);
      }
      webController.waitForElementVisible(GMAIL_PASSWORD, 60);
      log.info("Setting the password for user: {}", gmailUserName);
      webController.type(GMAIL_PASSWORD, gmailPassword);
      log.info("Clicked on Sign in Button");
      webController.setCheckBoxState(GMAIL_SIGNINREMENBER, false);
      log.info("Sign in Please Wait...");
      webController.click(GMAIL_SIGNIN_BUTTON);
      webController.delay(5);
      if (webController.isElementPresentWithWait("//table//tbody//tr[1]//td[6]", 60))
      {
         log.info("login to the Gmail: {} account successfully", gmailUserName);
      }
      else
      {
         log.info("Not able to login to the Gmail: {} account", gmailUserName);
      }
      getEmailSubject(subjectContentNeedtoFind, nameToRigester, passwordTOSet, securityQuestionOne, securityQuestionTwo, systemTime);
   }


   public void getEmailSubject(String subjectContentNeedtoFind, String nameToRigester, String passwordTOSet, String securityQuestionOne, String securityQuestionTwo, String systemTime)
   {
      checkMailTime(systemTime);
      mainLoop: for (int i = 1; i <= 5; i++)
      {
         mailCheck: for (int z = 1; z <= 6; z++)
         {
            String contentNeedtoFind = subjectContentNeedtoFind.substring(0, 17);
            String subject = webController.getText("//table//tbody//tr[" + z + "]//td[6]");
            String element = "//table//tbody//tr[" + z + "]//td[6]";
            String mainSubject = webController.getText("//table//tbody//tr[" + z + "]//td[6]//span[1]");
            int currentMailTime = Integer.parseInt(validateCurrentMailTime());
            int sysTime = Integer.parseInt(systemTime);
            printMailTime();
            if ((contentNeedtoFind.equalsIgnoreCase(mainSubject)) && (currentMailTime >= sysTime))
            {
               if (subjectContentNeedtoFind.equalsIgnoreCase(subject))
               {
                  webController.click(element);
                  log.info("Opening the mail with subject: " + subject);
                  openVerifyLink(nameToRigester, passwordTOSet, securityQuestionOne, securityQuestionTwo);
                  break mainLoop;
               }
            }
            else
            {
               webController.reload();
               webController.delay(40);
               int q = z + 1;
               z = 1;
               if (q == 4)
               {
                  currentMailTime = Integer.parseInt(validateCurrentMailTime());
                  if ( !(currentMailTime >= sysTime))
                  {
                     printMailTime();
                     log.info("Mail not came, Exiting the test");
                     Assert.fail("Mail not came, Exiting the test");
                  }
                  else
                  {
                     z = 1;
                     continue mailCheck;
                  }
               }
               log.info("Refreshing the mail box looking for the email with subject..." + subjectContentNeedtoFind);
            }
            log.info("Validating the subject of mail: {} in inbox ", z);
         }
         webController.reload();
         webController.delay(50);
         log.info("Refreshing the mail box..." + i);
      }
   }


   public void openVerifyLink(String nameToRigester, String passwordTOSet, String securityQuestionOne, String securityQuestionTwo)
   {
      webController.waitForElementVisible(GMAIL_TWC_VERIFYEMAILADDRESS, 60);
      int countBefore = webController.getNumberOfOpenWindows();
      log.info("Opening new window to rigester the TWC request");
      webController.click(GMAIL_TWC_VERIFYEMAILADDRESS);
      if ( !(webController.getBrowserName().equals("internet explorer")))
      {
         webController.delay(10);
      }
      int countAfter = webController.getNumberOfOpenWindows();
      if (countAfter > countBefore)
      {
         webController.switchToNewlyOpenedWindow();
         webController.switchToDefaultContent();
         log.info("Switching to newly opened window");
      }
      if (webController.getBrowserName().equalsIgnoreCase("Chrome"))
      {
         log.info("Switched to newly opened window");
         webController.switchToNewlyOpenedWindow();
      }
      webController.windowMaximize();
      webController.delay(10);
      if ( !(webController.isElementPresentWithWait(TWC_RIGESTER_USERNAME, 60)))
      {
         String text = null;
         System.out.println("");
         webController.waitForElementVisible(TWC_ERRORCONTENT, 60);
         text = webController.getText(TWC_ERRORCONTENT);
         log.info("Error Code: " + text);
         text = webController.getText(TWC_ERRORCODE);
         log.info("Error Message: " + text);
         text = webController.getText(TWC_ERRORNAME);
         System.out.println("");
         Assert.fail("Error While Regestring the TWC Email: " + text);
      }
      webController.waitForElementVisible(TWC_RIGESTER_USERNAME, 90);
      log.info("Setting the new username as: {}", nameToRigester);
      webController.type(TWC_RIGESTER_USERNAME, nameToRigester);
      log.info("Setting the new password as: {}", passwordTOSet);
      webController.type(TWC_RIGESTER_PASSWORD, passwordTOSet);
      log.info("Confirming the new password as: {}", passwordTOSet);
      webController.type(TWC_RIGESTER_CONFIRMPASSWORD, passwordTOSet);
      log.info("Accepting Terms and Conditions");
      webController.setCheckBoxState(TWC_ACCEPTTERMS_CONDITIONS, true);
      log.info("Clicking on Continue Button");
      webController.click(TWC_BUTTON_CONTINUE);
      webController.delay(5);
      if (webController.isElementVisible(ADD_USERSTAB_FIRSTNAME_ERROR))
      {
         nameToRigester = nameToRigester + "1";
         log.info("Setting First Name: " + nameToRigester);
         webController.clearField(TWC_RIGESTER_USERNAME);
         webController.type(TWC_RIGESTER_USERNAME, nameToRigester);
         log.info("Setting the new password as: {}", passwordTOSet);
         webController.type(TWC_RIGESTER_PASSWORD, passwordTOSet);
         log.info("Confirming the new password as: {}", passwordTOSet);
         webController.type(TWC_RIGESTER_CONFIRMPASSWORD, passwordTOSet);
         log.info("Accepting Terms and Conditions");
         webController.setCheckBoxState(TWC_ACCEPTTERMS_CONDITIONS, true);
         log.info("Clicking on Continue Button");
         webController.click(TWC_BUTTON_CONTINUE);
      }
      webController.waitForElementVisible(TWC_SECURITY_QUESTIONONE);
      log.info("Setting security question 1 with: {}", securityQuestionOne);
      webController.type(TWC_SECURITY_QUESTIONONE, securityQuestionOne);
      log.info("Setting security question 2 with: {}", securityQuestionTwo);
      webController.type(TWC_SECURITY_QUESTIONTWO, securityQuestionTwo);
      log.info("Clicking on Continue Button");
      webController.click(BUTTON_TWC_SECURITY_CONTINUE);
      webController.delay(5);
      String successmessage = webController.getTitle();
      if ( !(successmessage.contains("")))
      {
         Assert.fail("TWC user registration succesfull message not present");
      }
      log.info("Message contains: Congratulations. With your new TWC ID");
      if ( !(webController.isElementPresentWithWait(TWC_CONFIRMATION_SUCCESS, 240)))
      {
         log.info("Please wait as we create your account.");
         webController.reload();
         webController.delay(5);
         webController.dismissAlert();
         try
         {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
         } catch (AWTException e)
         {
            e.printStackTrace();
         }
      }
      log.info("Mail Registration done successfully");
   }


   public void checkMailTime(String systemTime)
   {
      int sysTime = Integer.parseInt(systemTime);
      webController.reload();
      log.info("Initating the mail checking process...");
      webController.delay(15);
      for (int q = 1; q <= 4; q++)
      {
         int mailTime = Integer.parseInt(validateCurrentMailTime());
         if (validateCurrentMailTime() == null)
         {
            mailTime = Integer.parseInt(validateCurrentMailTime());
         }
         boolean flag = false;
         String VMailCal = returnTimeFormat(printMailTime());
         String VSysCal = returnTimeFormat(getSystemTime());
         if (VMailCal.equals(VSysCal)) // am == am or pm == pm
         {
            flag = true;
         }
         if ((mailTime < sysTime) || (flag == false))
         {
            webController.reload();
            log.info("Refreshing the mail box for New Mails...");
            webController.delay(25);
            webController.reload();
            webController.delay(25);
         }
         else if ((mailTime >= sysTime) && (flag == true))
         {
            log.info("New Mail came, Validating the subject");
            break;
         }
      }
   }


   public String printMailTime()
   {
      String mailTim = "";
      String mailTime = "";
      if ( !(webController.isElementPresentWithWait(GMAIL_CURRENTMAIL_TIME)))
      {
         webController.reload();
         webController.delay(5);
      }
      webController.waitForElementVisible(GMAIL_CURRENTMAIL_TIME, 60);
      mailTime = webController.getText(GMAIL_CURRENTMAIL_TIME);
      mailTim = mailTime.replace(" am", "").replace(" pm", "").replace(":", "");
      if (mailTim == null)
      {
         webController.reload();
         webController.delay(5);
         webController.waitForElementVisible(GMAIL_CURRENTMAIL_TIME, 60);
         mailTime = webController.getText(GMAIL_CURRENTMAIL_TIME);
      }
      log.info("Current Mail Time: " + mailTime);
      return mailTime;
   }


   public String validateCurrentMailTime()
   {
      String mailTim = "";
      if ( !(webController.isElementPresentWithWait(GMAIL_CURRENTMAIL_TIME)))
      {
         webController.reload();
         webController.delay(15);
      }
      webController.waitForElementVisible(GMAIL_CURRENTMAIL_TIME, 60);
      mailTim = webController.getText(GMAIL_CURRENTMAIL_TIME);
      mailTim = mailTim.replace(" am", "").replace(" pm", "").replace(":", "");
      if (mailTim == null)
      {
         webController.reload();
         webController.delay(15);
         webController.waitForElementVisible(GMAIL_CURRENTMAIL_TIME, 60);
         mailTim = webController.getText(GMAIL_CURRENTMAIL_TIME);
         mailTim = mailTim.replace(" am", "").replace(" pm", "").replace(":", "");
      }
      return mailTim;
   }


   public String getSystemTime()
   {
      Date date = new Date();
      String strDateFormat = "h:mm a";
      SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
      String systemTime = sdf.format(date);
      systemTime = systemTime.toLowerCase();
      return systemTime;
   }


   public String returnTimeFormat(String text)
   {
      text = text.replace(":", "").replaceAll("\\d", "").replace(".", "").replace(" ", "");
      return text;
   }
}