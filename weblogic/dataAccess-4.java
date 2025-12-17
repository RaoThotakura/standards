
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java. sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.*;
import java.awt. event.*;
/**
This JAVA class establishes a connection with Oracle Ri database using pure
JDBC drivers for Oracle, fetches the rows after executing a simple SQL select query and finally displays the data
looks somewhat similiar to DataGrid Activex in V.B. 6. 0 on a SWING Component called Table which

*/
public class dataAccess extends JFrame {

    Vector colheads = new Vector () ;
    Vector rows = new Vector () ;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sl, username, password, sid, host, port, query; int numCol, i;

    public dataAccess () throws SQLException, Exception {
        super ("Table Containing Employees of PBSI") ;
        username = "scott";
        password = "tiger";
        query = "select empno EMPNO, ename NAME, job JOB, sal SALARY, dname DEPARTMENT, loc PLACE, to_char (hiredate, ' dd/mm/YVYY') HIREDATE FROM emp, dept where emp. deptno=dept. deptno";
        host = "maple";
        port = "1521";
        s1 = "jdbc: oracle: thin:@" + host + ":" + port + ":" + sid;
        rs = getTable() ;
        ResultSetMetaData rsd = rs.getMetaData();
        numCol=rsmd.getColumnCount();
        for (i=1; i<=numCol; i++) {
            colheads.addElement(rsmd.getColumnLabel(i));
        }
            
        while (rs.next ()) {
            rows. addElement (getCurrentRow (rs, numCol) ):
        }

        displayResultSet(rows, colheads);
    };

    
    public ResultSet getTable() throws SQLException {
        try { 
            DriverManager. registerDriver (new oracle. jdbc.driver .OracleDriver ()) ;
            conn = DriverManager. getConnection (sl, "scott", "tiger") ;
            stmt = conn.createStatement();
            rs = stmt.executeQuery (query);
        } catch (SQLException se) {
            System.out.println("An SQL exception has occured ") ;
            System.out.println("The error code is : " + se.getErrorCode());
            System.out.println("The SQL State" + se.getSQLState());
            System.out.println ("The message is : " + se.getMessage ());
            se.printStackTrace ();
        }
        return rs;
    };

    

    public Vector getCurrentRow (ResultSet rs, int numCol) throws SQLException { 
        Vector currentRow = new Vector ();
        for (i=1; i<=numCol; i++)
            currentRow.addElement(rs.getString(i));
        return currentRow;
    };

    public void displayResultSet (Vector rows, Vector colheads) {
        Table table = new Table (rows, colheads) ;
        ScrollPane scrollPane = new JScrollPane(table);
        getcontentPane().add(scrollPane, BorderLayout. CENTER);
        setSize (1000, 350);
    };


    public static void main ( String args[]) throws Exception {
        try {
            final dataAccess frame = new dataAccess () ;
            frame.show ( ) ;
        } catch (Exception ex) { 
            ex.printStackTrace();
        }
    }
 