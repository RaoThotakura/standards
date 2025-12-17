/**
sendobject - sends Java objects via TIB/Rendezvous messages
This example demonstrates how to send and receive Java objects.
Note: this can only be used when both the sender and the receiver are Java applications. 
Demonstrated technique must not be used when it is required to exchange messages with applications implemented in other languages.
This example uses NATIVE implementation and does not have any parameters. It can be run with a simple command:
This example program sends and receives objects of PersonalData class.
java sendobject
--*/

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
import com.tibco.tibrv.*;

class PersonalData {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String host = "maple";
    String port = "1521";
    String sid = "rsi";
    String s1 = "jdbc:oracle:thin:" + host + ":" + port + ":" + sid;
    String xmlString = "";
    String sqlstr = "";

    public PersonalData (String sqlstr) {
        try { 
            this.sqlstr = sqlstr;
            DriverManager.registerDriver(new oracle.jdoc.driver.OracleDriver());
            conn = DriverManager.getConnection(s1, "rsi", "rsi");
            stmt = conn.createStatement();
            rs = stmt.executeQuery (sqlstr);
            OracleXMLQuery qry = new OracleXMLQuery (conn, rs) ;
            this.xmlString = qry.getXMLString(0);
        } catch (SQLException se) {
            System.out.println ("An SQL exception has occured ");
            System.out.println ("The error code is : "+ se.getErrorCode());
            System.out.println ("The SQL State:" + se.getSQLState());
            System.out.println ("The message is: " + se.getMessage());
        }
    }

    public String toString() {
        return xmlString;
    }
}
/**
sendobject is a simple example to send Java objects via TIB/Rendezvous messages.
This program creates a simple Tibrv environment with a single queue and one listener. 
It creates an object of PersonalData class, converts it into a byte array and
sends it on the subject.The listener receives the message,recovers the object and quits the program.
*/
public class sendobject implements TibrvMsgCallback {
    // Our test subject
    String subject = "test.send.java.object";
    // Field name we use to add object into TibrvMsg
    String fieldName = "employee";

    public sendobject (String[] args) {
        try {
            // open Tibrv
            Tibrv.open(Tibrv.IMPL_NATIVE);
            // Create queue, dispatcher, simple transport
            TibrvQueue queue = new TibrvQueue();
            TibrvDispatcher = new TibrvDispatcher(queue);
            TibrvRvdTransport tport = new TibrvRvdTransport();
            // create listener
            TibrvListener listener = new Tibrvlistener (queue, this, tport, subject, null) i
            // create an object we want to send as a field in TibrMsg
            PersonalData data = new PersonalData ("select unique custnumber, numbers, dated from sales");
            // create the message
            TibrvMsg msg = TibrvMsg();
            msg.setSendSubject(subject);
            // add object as a field
            boolean ok = addObject(msg,fieldName, data.xmlString);
            if (!ok) {
                System.err.printin ("Failed to add object into message");
                System.exit(0);
            }
            // send the message
            tport.send(msg);
            // wait until the listener receives the messages and closes Tibrv
            try {
                disp.join();
            } catch (InterruptedException e) {
                System.exit(0);
            } 
        } catch (TibrvException e) {
            e. printStackTrace (System.err);
            System.exit(0);
        }
    }
    /**    
    * Example of the method which adds Java object into TibrvMsg
    * as a field with specified name. This method assumes the
    * object implements Serializable interface.
    --*/
    public boolean addObject (TibrvMsg msg, String fieldName, String object) {
        try {
            String array = object;
            msg.add(fieldName, array);
            return true;
        } catch (TibrvException e) {
            e.printStackTrace (System.err);
        }
        return false;
    }
    public Object getObject (TibrvMsg msg, String fieldName) {
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
    /**
    Listener callback.
    * Upon receiving the message this callback tries to recover
    * the Java object sent in a field of the message and then
    * closes Tibrv.
    *--*/
    public void onMsg (TibrvListener listener, TibrvMsg msg) {
        // try to retrieve the object
        try {
            Object object = getObject(msg, fieldName);
            String message = object.toString();
            String sub = msg.getSendSubject();
            String rep = msg.getReplySubject();
            byte [] recMsg = msg.getAsBytes();
            String strType = msg.getTypeName (msg. STRING);
            if (object == null) {
                System.err.println ("Error: object not found in message or exception occurred");
            } else {
                System.err.println ("Retrieved object: class="+object.getClass().getName());
                System.err.println ("Send subject: " + sub);
                System.err.println ("Reply subject : " +rep) ;
                System.err.println ("No. of messages: "+msg.MSG) ;
                System.err.println ("No. of fields in the message: "+msg.getNumFields()) ;
                System.out.println ("The received message is :" + message);
            }
            Tibrv.close();
        } catch (TibrvException e) {
            System.err.println(e.toString()) ;
        }
    }
    /*-
    * main
    */
    public static void main (String[] args) {
        sendobject t = new sendobject(args);
    }
}

