package test.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class DataBaseTest
{
   private static final Logger log = LoggerFactory.getLogger(DataBaseTest.class);
   public static Connection connection = null;
   public static Statement querry = null;
   public static ResultSet rst = null;
   public ResultSetMetaData rsMd_Lable;
   public ResultSet data;
   public Statement stmnt = null;
   public PreparedStatement stm = null;


   @Test
   public String getOrderHeaderKeyFromOMSDB(String orderNumber)
   {
      String result = null;
      try
      {
         String dbURL = "jdbc:oracle:thin:@(description=(load_balance=on)(failover=on)(address=(protocol=tcp)(host=dlocdb06)(port=50000))(connect_data=(service_name=ot01eodbsvc.world)(failover_mode=(type=select)(method=basic))(server=dedicated)))";
         String strUserID = "omsreadonly";
         String strPassword = "support";
         Connection myConnection = DriverManager.getConnection(dbURL, strUserID, strPassword);
         Statement sqlStatement = myConnection.createStatement();
         String readRecordSQL = "select order_header_key from yfs_order_header where order_no='" + orderNumber + "'";
         ResultSet myResultSet = sqlStatement.executeQuery(readRecordSQL);
         while (myResultSet.next())
         {
            result = myResultSet.getString("F");
            System.out.println("ORDER_HEADER_KEY value: " + myResultSet.getString("ORDER_HEADER_KEY"));
         }
         myResultSet.close();
         myConnection.close();
      } catch (Exception e)
      {
         System.out.println(e);
      }
      return result;
   }


   public void getArcDB()
   {
      try
      {
         String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ENABLE=BROKEN)(ADDRESS=(PROTOCOL=TCP)(HOST=dlocdb05)(PORT=50000))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=t01arcap01.world)))";
         String strUserID = "ARCQA_APP_01";
         String strPassword = "arcdev01okdu13jndd12^jhr";
         Connection myConnection = DriverManager.getConnection(dbURL, strUserID, strPassword);
         Statement sqlStatement = myConnection.createStatement();
         String readRecordSQL = "select ent_nm from arc_ent_parm where ent_nm = 'LOS_MAPPING'";
         ResultSet myResultSet = sqlStatement.executeQuery(readRecordSQL);
         while (myResultSet.next())
         {
            System.out.println("Record values: " + myResultSet.getString("ent_nm"));
         }
         myResultSet.close();
         myConnection.close();
      } catch (Exception e)
      {
         System.out.println(e);
      }
   }


   public void getRMSDB()
   {
      try
      {
         String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=dlocdb06)(PORT=50000)))(CONNECT_DATA=(SERVICE_NAME=oq03rtdbsvc.world)))";
         String strUserID = "a918558";
         String strPassword = "Network6";
         Connection myConnection = DriverManager.getConnection(dbURL, strUserID, strPassword);
         Statement sqlStatement = myConnection.createStatement();
         String readRecordSQL = "select sku from win_store where sku='8225039'";
         ResultSet myResultSet = sqlStatement.executeQuery(readRecordSQL);
         while (myResultSet.next())
         {
            System.out.println("Record values: " + myResultSet.getString("sku"));
         }
         myResultSet.close();
         myConnection.close();
      } catch (Exception e)
      {
         System.out.println(e);
      }
   }


   public ArrayList<String> getSKU()
   {
      String connectionUrl = "jdbc:oracle:thin:@ldap://p01imap01.na.bestbuy.com:3060/od03eodb,cn=OracleContext,dc=world ldap://p01imap02.na.bestbuy.com:3060/od03eodb,cn=OracleContext,dc=world";
      String userName = "Odin_readonly";
      String password = "changemenow123454678";
      String qury = "select sku from ODIN_SCH01.ODIN_TDID_TEAM_SKUS where Name = 'DOTCOM_ISP_PRESOURCED' and SWIMLANE='Ecomm-QA1'";
      ResultSet resultSet;
      ArrayList<String> sku = new ArrayList<String>();
      try
      {
         Connection myConnection = DriverManager.getConnection(connectionUrl, userName, password);
         Statement sqlStatement = myConnection.createStatement();
         resultSet = sqlStatement.executeQuery(qury);
         while (resultSet.next())
         {
            sku.add(resultSet.getString("SKU"));
         }
      } catch (Exception e)
      {}
      return sku;
   }


   public ArrayList<String> removeDuplicates(ArrayList<String> SKU)
   {
      Object[] st = SKU.toArray();
      for (Object s : st)
      {
         if (SKU.indexOf(s) != SKU.lastIndexOf(s))
         {
            SKU.remove(SKU.lastIndexOf(s));
         }
      }
      return SKU;
   }


   public String getStoreNumber(String key)
   {
      String result = null;
      try
      {
         String dbURL = "jdbc:oracle:thin:@(description=(load_balance=on)(failover=on)(address=(protocol=tcp)(host=dlocdb06)(port=50000))(connect_data=(service_name=ot01eodbsvc.world)(failover_mode=(type=select)(method=basic))(server=dedicated)))";
         String strUserID = "omsreadonly";
         String strPassword = "support";
         Connection myConnection = DriverManager.getConnection(dbURL, strUserID, strPassword);
         Statement sqlStatement = myConnection.createStatement();
         String readRecordSQL = "select SHIPNODE_KEY from yfs_order_line where ORDER_HEADER_KEY='" + key + "'";
         ResultSet myResultSet = sqlStatement.executeQuery(readRecordSQL);
         while (myResultSet.next())
         {
            result = myResultSet.getString("SHIPNODE_KEY");
            System.out.println("SHIPNODE_KEY value: " + result);
         }
         myResultSet.close();
         myConnection.close();
      } catch (Exception e)
      {
         System.out.println(e);
      }
      return result;
   }
}
