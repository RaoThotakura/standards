/**
Copyright (c) 1998-2000 TIBCO Software Inc.
rights reserved.
TIB/Rendezvous protected under US Patent No. 5,187,787.
For more information, please TIBCO Software Inc., Palo Alto, California, USA
@ (#) tibrusend.java 1.3
*/
/*
tibrusend - sample Rendezvous message publisher
This program publishes one or more string messages on a specified subject.
A field named "DATA" will be created to hold the string in each message.
Normally a listener such as tibrvlisten should be started first.
*/
import java.util.*;
import java.io.*;
import oracle.xml.sql.query.OracleXMLQuery; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import com.tibco.tibrv.*;

class SalesData {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String host = "maple";
    String port = "1521";
    String sid = "RSI";
    s1 = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
    xmlString = "";
    String sqlstr = "";

    public SalesData (String sqlstr) {
        try {
            this.sqlstr = sqlstr;
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(s1, "rsi", "rsi");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlstr);
            OracleXMLQuery qry = new OracleXMLQuery (conn, rs);
            this.xmlString = qry.getXMLString(0);
        } catch (SQLException se) {
                String errmsg = "An SQL exception has occured \n";
                errmsg = errmsg + "The error code is " + se.getErrorCode() +"\n";
                errmsg = errmsg + "The SQL State is: " + se.getSQLState() +"\n" ;
                errmsg = errmsg + "The message is: " + se.getMessage() +"\n" ;
                JOptionPane.showMessageDialog (null, errmsg, "SQL Error", 2);
                System.exit(0);
        }
    }
}

public class tibrusend {
    String service = null;
    String network = null;
    String daemon = null;

    String FIELD_NAME = "DATA";
    String subject = "a.b.c";
    public tibrvsend (String args []) {
        try {
            // open Tibrv in native implementation
            Tibrv.open(Tibrv.IMPL_NATIVE);
            SalesData firstmsg = new SalesData ("select unique custnumber, numbers from sales");
            // Create RVD transport
            TibrvRvdTransport transport = null;
            transport = new TibrvRvdTransport();
            // Create the message
            TibrMsg msg = new TibrvMsg();
            // Set send subject into the message
            msg.setSendSubject(subject);
            // Send first message in sequence
            msg.update(FIELD_NAME, firstmsg, xmlString);
            transport.send(msg);
            JOptionPane.showMessageDialog (null, msg.toString(), "First TIB/RV Message Sent", 0);
            System.exit(0);

        } catch (TibrvException e) {
            System.err.println("Failed to open Tibrv in native implementation:");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main (String args []) {
        new tibrusend(args);
    }
}