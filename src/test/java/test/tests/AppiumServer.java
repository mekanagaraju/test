package test.tests;

import java.io.IOException;

public class AppiumServer
{
   private static Process process;
   private static final String APPIUMSERVERSTART = "node /home/adminuser/Java_Projects/Appium/appium";
   public static final String APPIUM_NODE_FILEPATH = "C:/Program Files (x86)/Appium/node.exe";
   public static final String APPIUM_SERVERSCRIPT_FILEPATH = "C:/Program Files (x86)/Appium/node_modules/appium/bin/appium.js";
   public static final String APPIUMSERVER_START = "\"" + APPIUM_NODE_FILEPATH + "\" \"" + APPIUM_SERVERSCRIPT_FILEPATH + "\" ";


   public static void startAppiumServer()
      throws IOException, InterruptedException
   {
      Runtime runtime = Runtime.getRuntime();
      process = runtime.exec(APPIUMSERVERSTART);
      Thread.sleep(5000);
      if (process != null)
      {
         System.out.println("Appium server started");
      }
   }


   public static void stopAppiumServer()
      throws IOException
   {
      if (process != null)
      {
         process.destroy();
      }
      System.out.println("Appium server stop");
   }


   public void testServer()
      throws IOException, InterruptedException
   {
      startAppiumServer();
      stopAppiumServer();
   }


   public void startServer()
      throws IOException, InterruptedException
   {
      Runtime runtime = Runtime.getRuntime();
      process = runtime.exec(APPIUMSERVER_START);
      Thread.sleep(5000);
      if (process != null)
      {
         System.out.println("Appium server started");
      }
   }


   /**
    * DOCUMENT ME!
    */
   public void stopServer()
   {
      if (process != null)
      {
         process.destroy();
      }
      System.out.println("Appium server stop");
   }


   public static void main(String args[])
      throws IOException, InterruptedException
   {
      startAppiumServer();
      stopAppiumServer();
   }
}
