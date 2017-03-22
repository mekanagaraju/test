package test.tests;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang3.text.WordUtils;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class QuickTest
{
   protected static final Logger log = LoggerFactory.getLogger(QuickTest.class);
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution webController = new SingleSeleniumSolution(driver);


   @Test
   public void reversing()
   {
      int num = 12345;
      int reversenum = 0;
      while (num != 0)
      {
         reversenum = reversenum * 10;
         reversenum = reversenum + num % 10;
         num = num / 10;
      }
      System.out.println("" + reversenum);
      num = reversenum;
      reversenum = 0;
      for (; num != 0;)
      {
         reversenum = reversenum * 10;
         reversenum = reversenum + num % 10;
         num = num / 10;
      }
      System.out.println("" + reversenum);
   }


   @Test
   public void circumferenceOfCircle()
   {
      // double radius = 1;
      int radius = 1;
      double area = Math.PI * (radius * radius);
      System.out.println("The area of circle is: " + area);
      double circumference = Math.PI * 2 * radius;
      System.out.println("The circumference of the circle is: " + circumference);
   }


   @Test
   public void calculateAreaOfTriangle()
   {
      double base = 20.0;
      double height = 110.5;
      double area = (base * height) / 2;
      System.out.println("Area of Triangle is: " + area);
   }


   @Test
   public void sumOfArray()
   {
      // Advanced for loop
      int[] array = { 10, 20, 30, 40, 50, 10 };
      int sum = 0;
      for (int num : array)
      {
         sum = sum + num;
      }
      System.out.println("Sum of array elements is: " + sum);
      // simple if loop
      int[] arrays = { 10, 20, 30, 40, 50, 60 };
      int sums = 0;
      System.out.println("Enter the elements: ");
      for (int num : arrays)
      {
         sums = sums + num;
      }
      System.out.println("Sum of array elements is: " + sums);
   }


   @Test
   public void checkPrimeNumber()
   {
      int temp;
      boolean isPrime = true;
      Scanner scan = new Scanner(System.in);
      System.out.println("Enter a number for check:");
      // capture the input in an integer
      int num = scan.nextInt();
      for (int i = 2; i <= num / 2; i++)
      {
         temp = num % i;
         if (temp == 0)
         {
            isPrime = false;
            break;
         }
      }
      // If isPrime is true then the number is prime else not
      if (isPrime)
      {
         System.out.println(num + " is Prime Number");
      }
      else
      {
         System.out.println(num + " is not Prime Number");
      }
   }


   @Test
   private void checkEvenOrOddNumber()
   {
      int num;
      num = 109;
      if (num % 2 == 0)
      {
         System.out.println(num + " number is even");
      }
      else
      {
         System.out.println(num + " number is odd");
      }
   }


   @Test
   private void arraySort()
   {
      int iArr[] = { 2, 1, 9, 6, 4 };
      for (int number : iArr)
      {
         System.out.println("Number = " + number);
      }
      Arrays.sort(iArr);
      System.out.println("The sorted int array is:");
      for (int number : iArr)
      {
         System.out.println("Number = " + number);
      }
   }


   @Test
   public void largestSmallestNumberInArray()
   {
      int numbers[] = new int[] { 32, 43, 53, 54, 32, 65, 63, 98, 43, 23 };
      int smallest = numbers[0];
      int largetst = numbers[0];
      for (int i = 1; i < numbers.length; i++)
      {
         if (numbers[i] > largetst)
         {
            largetst = numbers[i];
         }
         else if (numbers[i] < smallest)
         {
            smallest = numbers[i];
         }
      }
      System.out.println("Largest Number is : " + largetst);
      System.out.println("Smallest Number is : " + smallest);
      int[] number = { 88, 33, 55, 23, 64, 123 };
      int x = 3;
      String s = "3.0";
      float f = 3.0f;
      try
      {
         x = 10 / Integer.parseInt(s);
      } catch (IllegalArgumentException e)
      {
         f = 10 / Float.parseFloat(s);
      }
      System.out.println("Smallest Number is : " + x + " " + f);
      int largest = Integer.MIN_VALUE;
      int smalest = Integer.MAX_VALUE;
      for (int element : number)
      {
         if (element > largest)
         {
            largest = element;
         }
         if (element < smalest)
         {
            smalest = element;
         }
      }
      System.out.println("Largest number in array is : " + largest);
      System.out.println("Smallest number in array is : " + smalest);
   }


   @Test(enabled = true)
   public void splitTheString()
   {
      String text = "12/08/2014 04:08 PM";
      String[] q = text.split(" ");
      int p = q.length;
      if (p <= 2)
      {
         System.out.println(text);
      }
      else if (p >= 3)
      {
         String[] r = text.split(" ", 3);
         System.out.println(r[0] + " " + r[2]);
      }
   }
   static Workbook wbook;
   static WritableWorkbook wwbCopy;
   static String ExecutedTestCasesSheet;
   static WritableSheet shSheet;
   static String sheetName;
   static String sheetNameCreated, excelPath, excelName;


   @Test
   public void testSplit()
   {
      String appName = "AAD";
      SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy h-mm a");
      SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
      excelName = appName + " Jenkins failures on " + sdf.format(new Date());
      String location = "C:/apps/JenkinsFailures/" + sdf1.format(new Date()).toString() + "/" + excelName + ".xls";
      File theDir = new File(location);
      excelPath = theDir.toString();
      try
      {
         wwbCopy = Workbook.createWorkbook(new File(excelPath));
         shSheet = wwbCopy.createSheet("AAD", 0);
      } catch (Exception e)
      {}
   }


   @Test
   public void Quick()
   {
      String sysEnv = System.getenv("env");
      log.info(" {}", sysEnv);
      System.getenv("ENV");
      String day = "15-07-2016";
      day.split("-");
   }


   @Test
   public void testNull()
   {
      String a = selectDropDown("", "", "");
      if (a == null || a.length() == 0)
      {
         log.info("Done");
      }
   }


   private String selectDropDown(String... locatores)
   {
      String element = null;
      for (String each : locatores)
      {
         if (each.equals("1"))
         {
            log.info("Selecting the Account Type as: {}", each);
            element = each;
            break;
         }
      }
      return element;
   }


   @Test
   public void testDate()
   {
      try
      {
         SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
         Date date1 = sdf.parse("2009-12-31");
         Date date2 = sdf.parse("2010-01-31");
         System.out.println(sdf.format(date1));
         System.out.println(sdf.format(date2));
         if (date1.compareTo(date2) > 0)
         {
            System.out.println("Date1 is after Date2");
         }
         else if (date1.compareTo(date2) < 0)
         {
            System.out.println("Date1 is before Date2");
         }
         else if (date1.compareTo(date2) == 0)
         {
            System.out.println("Date1 is equal to Date2");
         }
         else
         {
            System.out.println("How to get here?");
         }
      } catch (ParseException ex)
      {
         ex.printStackTrace();
      }
   }


   @Test
   public void testDates()
      throws ParseException
   {
      SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
      Date date1 = sdf.parse("Oct 12");
      Date date2 = sdf.parse("Sep 8");
      log.info(sdf.format(date1));
      log.info(sdf.format(date2));
      if (date1.compareTo(date2) == 0)
      {
         System.out.println("The emails in Inbox are arranged by new to old by dates " + date1 + " " + date2);
      }
      else if (date1.compareTo(date2) > 0)
      {
         System.out.println("The emails in Inbox are arranged by new to old by date " + date1 + " " + date2);
      }
      else if (date1.compareTo(date2) < 0)
      {
         System.out.println("The emails in Inbox are arranged by old to new by date " + date1 + " " + date2);
      }
   }


   @Test
   public void splitTest()
   {
      String each = "css=input[name='slackNotifyAborted']";
      each = each.replaceAll("css=input[name='", "").replaceAll("']", "");
      log.info(each);
      String a = "@org.testng.annotations.Test(expectedExceptions=[], skipFailedInvocations=false, dependsOnGroups=[], sequential=false, invocationTimeOut=0, groups=[hi], description=Nagaraju, retryAnalyzer=class java.lang.Class, alwaysRun=false, priority=0, enabled=true, successPercentage=100, timeOut=0, expectedExceptionsMessageRegExp=.*, ignoreMissingDependencies=false, singleThreaded=false, threadPoolSize=0, dataProviderClass=class java.lang.Object, dependsOnMethods=[], invocationCount=1, dataProvider=, parameters=[], suiteName=, testName=)";
      String b = a.toString().split("description=")[1].split(", retryAnalyzer=")[0];
      log.info(b);
   }


   @Test
   public void getMailSubject()
   {
      String a = WordUtils.capitalize("naga raju");
      log.info(a);
      int mailNum = 20;
      String page = String.valueOf(mailNum);
      if ((page.length() >= 2) && !(mailNum == 10))
      {
         int length = page.length() - 1;
         page = page.substring(0, length);
      }
      if (mailNum == 0)
      {
         mailNum = 1;
      }
   }


   @Test
   public void testSample()
   {
      String msg = "Wait %ss for [%s] on page [%s]";
      log.info(String.format(msg, 0, "//.", this.getClass().getSimpleName()));
   }


   @Test
   public void testTime()
   {
      ArrayList<String> values = new ArrayList<String>();
      String pMailTime = "2:50 PM";
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
      String pZone = a[2];
      String pTime = a[1];
      values.add(pDay);
      values.add(pTime);
      values.add(pZone);
      values.add(pTime.replace(":", ""));
      log.info(":" + values);
   }


   @Test
   public void printDate()
      throws ParseException
   {
      ArrayList<String> fDate = new ArrayList<String>();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
      Date date = sdf.parse(sdf.format(new Date()));
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
      String formatedDate = fDate.get(0) + fDate.get(1) + fDate.get(2) + fDate.get(3) + fDate.get(4);
      System.out.println(formatedDate);
   }


   @Test
   public void testDateTime()
   {
      SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
      String a = sdf1.format(new Date());
      log.info(a);
   }
}