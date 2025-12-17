/*
*sendobject - sends Java objects via TIB/Rendezvous messages
This example demonstrates how to send and receive Java objects.
Note: this can only be used when both the sender and the receiver are Java applications. 
Demonstrated technique must not be used when it is required to exchange messages with applications implemented in other languages.
This example uses NATIVE implementation and does not have any parameters. It can be run with a simple command:
java sendobject
*
*
*/
import java.util.*; 
import java.io.*;
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
import javax.swing.JOptionPane;
import com.tibco.tibrv.*;
/* 
This example program sends and receives objects
*.
of PersonalData class.
--*/
class PersonalData {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String host = "maple";
    String port = "1521";
    String sid = "RSI" ;
    String s1 = "jdbc:oracle:thin:" + host + ":" + port + ":" + sid;
    String xmlString =" ";
    String sqlstr = "";

    public PersonalData (String sqlstr) {
        try {
            this.sqlstr = sqlstr;
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection( s1, "rsi", "rsi");
            stmt = conn.createStatement();
            OracleXMLQuery qry = new OracleXMLQuery(conn,rs);
            rs = stmt.executeQuery(sqlstr);
            this.xmlString = qry.getXMLString(3);
        } catch (SQLException se) {
            String errmsg = "An SQL exception has occured \n";
            errmsg = errmsg + "The error code is : " + se.getErrorCode() +"\n" ;
            errmsg = errmsg +"The SQL State is :" + se.getSQLState() +"\n" ;
            errmsg = errmsg +"The message is: " + se.getMessage() +"\n" ;
            JOptionPane.showMessageDialog (null, errmsg, "SQL Error", 2);
            System.exit(0);
        }
    }

    public PersonalData () {
        try {
            DriverManager registerDriver (new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection ( s1, "rsi", "rsi");
        } catch (SQLException se) {
            String errmsg = "An SQL exception has occured \n" ;
            errmsg = errmsg + "The error code is:"+ se.getErrorCode()+"\n" ;
            errmsg = errmsg +"The SQL State is : " + se.getSQLState() +"\n" ;
            errmsg = errmsg +"The message is: " + se.getMessage() +"\n" ;
            JOptionPane.ShowMessageDialog (null, errmsg, "SQL Error", 2) ;
            System.exit(0);
        }
    }

    public String toString() {
        return xmlString;
    }
}

/**
* sendobject is a simple example to send Java objects via TIB/Rendezvous messages.
* This program creates a simple Tibrv environment with a single queue and one listener.  
* It creates an object of PersonalData class, converts it into a byte array and sends it on the subject.
* The listener receives the message, recovers the object and quits the program.
*ーーーー*/

public class sendobject implements TibrvMsgCallback {
    String subject = "test.send.java.object":// Our test subject
    String fieldName = "employee": // Field name we use to add object into TibrMsg
    public sendobject (String[] args) {
        try {
            Tibrv.open(Tibrv.IMPL_NATIVE); // open Tibrv 
            TibrvQueue queue = new TibrvQueue(); // Create queue, dispatcher, simple
            TibrvDispatcher disp = new TibrvDispatcher(queue);
            TibrvRvdTransport tport = new TibrvRvdTransport();
            TibrvListener listener = new TibrvListener (queue, this, tport, subject, null); // Create listener
            // create an object we want to send as a field in TibrvMsg
            PersonalData data = new PersonalData ("select distinct(*) from sales");
            TibrvMsg msg = new TibrMsg():// create the message
            msg.setSendSubject(subject);
            boolean ok = addObject(msg, fieldName, data.xmlString);// add object as a field
            if (!ok) {
                System.err.println ("Failed to add object into message");
                System.exit(0);
            }
            tport.send(msg):// send the message
            try { 
                disp.join();
                // wait until the listener receives the messages and closes Tibrv
            } catch (InterruptedException e) {
                System.exit(0);
            }
        } catch (TibrvException e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

    /**
     * Example of the method which adds Java object into TibrMsg as a field with specified name. 
     * This method assumes the object implements Serializable interface.
    */

    public boolean addObject (TibruMsg msg, String fieldName, String object) {
        try {
            String array = object;
            msg.add(fieldName, array);
            return true;
        } catch (TibrvException e) {
            e.printStackTrace (System.err);
            return false;
        }
    }


    public Object getObject (TibrMsg msg, String fieldName) {
        try {
            Object array = msg.get(fieldName);
            if (array == null) // check if field not found
                return null;
            return array;
        } catch (TibrvException e) {
            e.printStackTrace (System.err);
        }
        return null;
    }

    public int confirmobject (String message) {
        PersonalData data = new PersonalData();
        OracleXMLSave sav = new OracleXMLSave (data.conn,"sales");
        int noRows = sav.insertXML(message);
        return norows;
    }

    /*Listener callback.
    * Upon receiving the message this callback tries to recover
    * the Java object sent in a field of the message and then closes Tibrv.
    */

    public void onMsg (TibrvListener listener, TibrvMsg msg) {
        // try to retrieve the object
        try {
            Object object = getObject(msg,fieldName);
            String message = object.toString();
            String sub = msg.getSendSubject();
            if (object == null) {
                JOptionPane.showMessageDialog (null, "Error: object not found in message or exception occurred", "XML Doc", 1) ;
            } else {
                String errmsg = "Retrieved object: class="+object.getClass().getName() +"\n" ;
                errmsg = errmsg +"Send subject: " +sub+"\n";
                errmsg = errmsg +"No. of messages : "+msg.MSG+"\n";
                errmsg = errmsg + "No. of in the message : "+msg.getNumFields() +"\n";
                JOptionPane.showMessageDialog (null, errmsg+message, "Received XML Document", 1);
                int noRows = confirmObject(message);
                JOptionPane.showMessageDialog (null, noRows+" Row(s) sent back to Oracle", "XMLDoc", 1);
                System.exit(0);
            }
        }
        Tibrv.close();
    }
}