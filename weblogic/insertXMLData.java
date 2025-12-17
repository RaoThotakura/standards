PENTGEAR
/**
 *  This class performs the INSERT operation with any Oracle DB Table by taking data from XML Document and 
 * inserts the date into rous of table bu matching the xml tags with column names in table. 
 * */
import java.io.*;
import java.lang.*;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.xml.sql.dml.OracleXMLSave;
import oracle.xml.sql.OracleXMLSQLException;
import org.w3c.dom.Document;

public class insertXMLData {
    public static void main(String argv[]) throws SQLException, Exception {
        String tabName = "emp";
        Connection conn = null;
        String xmlString, errorCode;
        Document doc;
        URL filename;
        
        try {
            //init a JDBC connection using Oracle's JDBC Drivers
            String host = "maple";
            String port = "1521";
            String sid = "rsi";
            String s1 = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection( "scott", "scott", "tiger");
            //init a JDBC connection using Sun's JDBC-ODBC Bridge
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jabc:odbc:xmldbms";
            conn = DriverManager.getConnection(url, "scott", "tiger");
            OracleXMLSave sav = new OracleXMLSave(conn, tabName);
            sav.setRowTag("EMP");
            filename = sav.getURL("employee.xml");
            int noRows = sav.insertXML(filename);
            conn.commit();
            System.out.printin("No of rows inserted : " + noRows);

        } catch (SQLException se) {
            System.out.println("An SQL error has occured ") ;
            System.out.println("The vendor code is :" + se.getErrorCode());
            System.out.println("The SQL State is " + se.getSQLState());
            System.out.println("The reason is " + se.getMessage());
        } catch (OracleXMLSQLException oxse) {
            System.out.println("OracleXMLSQLException exception has occured");
            oxse.getMessage().substring(oxse.getMessage().indexOf(":")+1, oxse.getMessage().lastIndexof(":"));
            System.out.println(errorCode);

            if (errorCode.equals("ORA-01401")) {
                System.out.println("Field contains more characters than it can accomodate");
                System.out.println("The error code:" + errorCode);
            }
            if (errorCode.equals("ORA-00001")) {
                System.out.println ("The record already exists i.e. Unique constraint violated");
                System.out.println ("The error code is :" + errorCode);
            }
        } finally {
            if (conn != null) conn.close();
        }
    }
}