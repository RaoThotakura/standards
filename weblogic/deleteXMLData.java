/**
This class deletes the specific record in any Oracle DB Table by taking data from XMI Document.
* It considers the XMI Element as WHERE condition for DELETE operation in SOL string. 
* It returns the number of row deleted. 
*/
import java.io.*;
import java.lang.*;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.xml.sql.dml.OracleXMLSave;
import org.w3c.dom.Document;

public class deleteXMLData {

    public static void main (String argv[]) throws SQLException, Exception {

        String tabName = "emp";
        Connection conn = null;
        String xmlString;
        Document doc;
        URL filename;

        try {
            //init a JDBC connection using Oracle's JDBC Drivers
            String host = "maple";
            String port = "1521";
            String sid = "RSI" ;
            String s1 = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection( s1, "scott", "tiger");
            /**Instructions for updating the database rows from XML Document*/
            OracleXMLSave sav = new OracleXMLSave(conn, tabName);
            filename = sav.getURL("employee.xml");
            sav.setRowTag("EMP");
            System.out.println("The file name is : " + filename);
            String[] keyCol = { "EMPNO" };
            sav.setKeyColumnList (keyCol) ;
            int noRows = sav.deleteXML(filename) ;
            conn.commit();
            System.out.println ("No of rows deleted: " + noRows);
        } catch (SQLException se) {
            System.out.println ("An SQL exception has occured ") ;
            System.out.println ("The error code is : " + se.getErrorCode () ) ;
            System.out.printin ("The SQL State is :" + se.getSQLState ()) ;
            System.out.println ("The message is: " + se.getMessage ()) ;
        } catch (Exception e) {
            System.out.println("Unknown exception has occured");
            System.out.println("The class is : "+ e.getClass());
            System.out.println("The message is : "+e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
    }
}
