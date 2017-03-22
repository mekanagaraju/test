package test.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AppiumDriver
{
   protected static final Logger log = LoggerFactory.getLogger(AppiumDriver.class);
   AndroidDriver<WebElement> driver;
   AppiumDriverLocalService appiumService;
   String appiumServiceUrl, deviceName;


   @BeforeTest
   public void setUp()
   {
      appiumService = AppiumDriverLocalService.buildDefaultService();
      appiumService.start();
      appiumServiceUrl = appiumService.getUrl().toString();
      deviceName = getDeviceName();
      log.info("Appium Service Address : {}", appiumServiceUrl);
      log.info("Connected device UDID {}", deviceName);
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("deviceName", deviceName);
      capabilities.setCapability("platformName", "Android");
      capabilities.setCapability("appPackage", "com.android.calculator2");
      capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
      try
      {
         driver = new AndroidDriver<WebElement>(new URL(appiumServiceUrl), capabilities);
         driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
      } catch (MalformedURLException e)
      {}
   }


   @Test
   public void testSum()
   {
      log.info("Calculate sum of two numbers");
      // Locate elements to enter data and click +/= buttons
      driver.findElement(By.xpath("//*[@text='1']")).click();
      driver.findElement(By.xpath("//*[@text='+']")).click();
      driver.findElement(By.xpath("//*[@text='2']")).click();
      driver.findElement(By.xpath("//*[@content-desc='equals']")).click();
      // Get the result text
      WebElement sumOfNumbersEle = driver.findElement(By.className("android.widget.EditText"));
      String sumOfNumbers = sumOfNumbersEle.getText();
      // verify if result is 3
      Assert.assertTrue(sumOfNumbers.endsWith("3"));
   }


   @AfterTest
   public void end()
   {
      log.info("Stop driver");
      driver.quit();
      log.info("Stop appium service");
      appiumService.stop();
   }


   private String getDeviceName()
   {
      Process process = null;
      String deviceName = null;
      try
      {
         String osName = System.getProperty("os.name").toLowerCase();
         if (osName.startsWith("windows"))
         {
            process = new ProcessBuilder("cmd.exe", "/C", "adb devices").start();
         }
         else if (osName.startsWith("mac os"))
         {
            process = new ProcessBuilder("/bin/bash", "-l", "-c", "adb devices").start();
         }
         try
         {
            process.waitFor();
         } catch (Exception e)
         {}
         BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
         deviceName = in.readLine();
         deviceName = in.readLine().replaceAll("device", "").replaceAll("\t", "").trim();
      } catch (IOException e)
      {}
      return deviceName;
   }
}
