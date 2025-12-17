import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class dataAccess extends HttpServlet {
    public static void main ( String argv[]) throws SQLException, Exception {
        String url = "jdbc:odbc:xmldbms";
        String sqlstr = "SELECT e. empno, e. ename, e. deptno, d. dname FROM emp e, dept d where e. deptno=d. deptno";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            System.out.println("Connecting to Oracle database...");
            Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");

            System.out.println("Connecting.....") ;
            conn = DriverManager.getConnection (url, "scott", "tiger");
            System.out.println("Connected to Oracle database..");
            stmt = conn.createStatement();

            rs = stmt.executeQuery(sqlstr);
            System.out.println("Result Set returned.•");
            while (rs.next())
                System.out.println(rs.getString(1)+"" +rs.getString(2) +""+rs.getString(3) +""+rs.getString(4));
        } catch (SQLException se) {
            System.out.println("An SQL exception has occured ") ;
            System.out.println("The error code is : " + se. getErrorCode()) ;
            System.out.println("The SQL State is : "+ se.getSQLState());
            System.out.println("The message is: " + se. getMessage());

        } catch (Exception e) {
            System.out.println("Unknown exception has occured");
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
