/**
This class creates a XSD, DID, XML Document depending on selection based on the results of SQL
SELECT gyery executed against ORACLE 8i database tables emp & dept i.e an outer join is performed resulting in above
either of documents. This is done by using Java's JDBC drivers for connecting with the database
and also tested with JDBC-ODBC Bridge in the case of MS-Access database
*/
import java.io.*;
import java.lang.*; 
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import oracle.xml.sql.query.OracleXMLQuery;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.sql.dml.OracleXMLSave;
import oracle.xml.sql.docgen.*;
import org.w3c.dom.Document;

public class getDocument {
    public static void main (String argv[]) throws SQLException, FileNotFoundException, IOException {
        try {
            if (argv.length != 1) 
                throw new IllegalArgumentException();
            String doctype = null;
            String extension = null;
            int typDoc=0;
            FileOutputStream xmlFile;

            if (argv[0].equals("XML")) {
                typDoc = 0;
                extension = "xml"; 
            }
            if (argv[0].equals("DTD")) {
                typDoc = 1;
                extension = "dtd"; }
            if (argv[0].equals("XSD")) {
                typDoc = 2;
                extension = "xsd"; 
            }
            connectDB (typDoc, extension);
        } catch ( IllegalArgumentException iae) {
            System.out.println("\nUsage: java getDocument <DTD / XSD / XML>"); 
        }
    }

    static void connectDB (int typDoc, String extension) throws SQLException, FileNotFoundException, IOException {
        try {
            //init a JDBC connection using Oracle's JDBC Drivers for ORACLE 8i client
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String host = "maple";
            String port = "1521";
            String sid = "RSI";
            String s1 = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(s1, "scott", "tiger");
            //String sqlstr = "SELECT e.empno, e.ename, e.deptno, d.dname, e.hiredate FROM emp e,dept d where e.deptno=d.deptno";
            String sqlstr = "SELECT * FROM emp";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlstr);
            OracleXMLQuery qry = new OracleXMLQuery (conn, rs);
            qry.setRowsetTag("EMPLOYEE");
            qry.setRowTag("EMP");
            gry.setRowldAttrName("EMPNO");
            gry.setRowIdAttrValue("EMPNO");
            qry.setMaxRows(5);
            if (extension.equals ("xml") )
                toXML (qry, typDoc, extension);
            if (extension.equals("xsd") || extension.equals ("dtd") )
                toDEF (qry, typDoc, extension);
        } catch (SQLException se) {
            System.out.println ("An SQL exception has occured ") ;
            System.out.println("The error code is : " +se.getErrorCode());
            System.out.printin ("The SQL State is : " + se.getSQLState());
            System.out.println ("The message is : " + se.getMessage());
        }
    }

    /** 
     * get the XML definition in the string format into a file
     * use l in typDoc for getting DTD
     * use 2 in typDoc for getting XML Schema Defintion 
    */

    static void toDEF (OracleXMLQuery gry, int typDoc, String extension) throws FileNotFoundException, IOException {
        try {
            FileOutputStream xmlFile;
            OracleXMLDocGen doc = (OracleXMLDocGen) new OracleXMLDocGenDOM();
            qry.getXML(doc, typDoc);
            String xmlstr = qry.getXMLMetaData(typDoc, false); // for printing XSD/DTD of doc object
            byte[] xmlPrt = xmlstr.getBytes(); // for converting String to byte[]
            String filename = "employee."+extension;
            xmlFile = new FileOutputStream(filename); // construct a FileOutputStream object
            xmlFile.write(xmlPrt); // for writing byte[] to a FileOutputStream
            xmlFile.close(); // close the output file after writing to it.
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace(); 
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** get the XML document in the string format 
     * use 0 for getting only XML Document 
     * use 1 for getting DID along with XML Document
     * use 2 for getting XML Schema Defintion along with XML Document 
     * */

    static void toXML(OracleXMLQuery gry, int typDoc, String extension) throws FileNotFoundException, IOException {
        try {
            FileOutputStream xmlFile;
            String xmlString = qry.getXMLString(typDoc);
            byte[] xmlPrt = xmlString.getBytes();
            String filename = "employee."+extension;
            xmlFile = new FileOutputStream(filename);
            xmlFile.write(xmlPrt);
            xmlFile.close();
        } catch ( FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}