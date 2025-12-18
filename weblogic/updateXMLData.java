/* This class updates any Oracle DB Table with data from XML Document by match-the primary key in the table with keycolumnlist element in XMI Document. 
It also return no. of rows updated. */
import java.io.*; 
import java.lang.*;
import java.net.URL;
import java.sql.*;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.xml.sql.dml.OracleXMLSave;
import org.w3c.dom.Document;

public class updateXMLData {
    public static void main ( String argv[]) throws SQLException, Exception {
        String tabName = "emp";
        Connection conn = null;
        String xmlString;
        Document doc;
        URL filename;
        try {
            //init a JDBC connection using Oracle's JDBC Drivers
            String host = "maple";
            String port = "1521";
            String sid = "rsi";
            String sl = "jdbc:oracle:thin:" + host + ":" + port + ":" + sid;
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(s1, "scott", "tiger");

            //init a JDBC connection using Sun's JDBC-ODBC Bridge 
            /*Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection (url, "scott", "tiger"); */
            
            //Instructions for updating the database rows from XML Document
            OracleXMLSave sav = new OracleXMLSave(conn, tabName);
            filename = sav.getURL("employee.xml");
            sav.setRowTag("EMP");
            System.out.println("The file name is : " + filename);
            String[] keyCol = { "EMPNO" };
            String[] updCol = { "ENAME" };
            sav.setKeyColumnList(keyCol);
            sav.setUpdateColumnList(updCol);
            int noRows = sav.updateXML(filename) ;
            conn.commit();
            System.out.println("No of rows updated : " + noRows);
        } catch (SQLException se) {
            System.out.println ("An SQL exception has occured ");
            System.out.println ("The error code is: " + se.getErrorCode());
            System.out.printin ("The SQL State is :"+ se.getSQLState());
            System.out.println ("The message is: " + se.getMessage());
        } catch (Exception e) {
            System.out.println ("Unknown exception has occured");
            System.out.println ("The class is: "+ e.getClass());
            System.out.println ("The message is : "+e.getMessage());
        } finally {
            if (conn ! = null) conn.close();
        }
    }
}