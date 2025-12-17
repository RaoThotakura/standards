import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.String; 
import java.util.Date;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class firstServlet extends HttpServlet {

    String username, password, query;
    String host, port, sid, sl;
    Connection con = null;
    PrintWriter out = null;

    public void init() throws ServletException {
        super.init();
        username = "scott";
        password = "tiger";
        query = "select empno EMPNO, ename NAME, job JOB, sal SALARY, dname DEPARTMENT, loc PLACE, hiredate HIREDATE FROM emp, dept where emp. deptno=dept. deptno";
        host = "maple";
        port = "1521";
        sid = "PBSI";
        s1 = "jdbc:oracle:thin:" + host + ":" + port + ":" + sid;
    }

    public void service (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Date today; 
        int diff;
        String stoday;
        today = new Date();
        stoday = DateFormat.getDateInstance(0).format(today);
        res.setContentType("text/html");
        out = res.getWriter();
        out.println("<hr><h3>Previous Query</h3>"); 
        out.println("<pre>");
        out.println("JDBC URL : " + s1);
        out.println("User ID :"+ username);
        out.println("Password: " + password);
        out.println("Query: " + query);
        out.println("</pre>");
        try {
            out.println ("<hr>");
            out.println ("<i>JDBC Driver and Database Messages</i>") ;
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection(s1, username, password);
            DatabaseMetaData dma = con.getMetaData();
            out.println("<pre>") ;
            out.println("Connected to : "+ dma.getURL());
            out.println("Driver : " + dma.getDriverName());
            out.println("Version : " + dma.getDriverVersion());
            out.println("</pre>");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            out.println("<hr>");
            out.println("<h3>Query Result</h3>");
            out.println("<h4>Printed on :" + stoday+"</h4>");
            out.println("<table border=1>");
            out.println("<tr>");
            for (i=1; i<=numCols; i++) {
                out.println("<th>" + rsmd.getColumnLabel (i) + "</th>");
            }
            out.println("</tr>");
            while (rs.next()) {
                out.println ("<tr>") ;
                for (i=1; i<=numCols; i++) {       
                    if (1<7) {
                        out.println("<td›"+rs.getString (i)+"</td>") ; 
                    }
                    if (i>6) {
                        stoday=DateFormat.getDateInstance(2).format(rs.getDate(i));
                        out.println("<td>"+stoday+"</td>");
                    }
                }
                out.println ("</tr>");
            }
            rs.close();
            stmt.close();
            con.close();
            out.println("</table><hr>");

        } catch (SQLException ex) {
            out.println("<hr>*** SQLException caught ***<p>");
            while (ex ! = null) {
                out.println("SQLState: " + ex.getSQLState() + "<br>"); 
                out.println ("Message: " + ex.getMessage() + "<br>");
                out.println ("Vendor:" + ex.getErrorCode() + "<br>");
                ex.printStackTrace(out);
                ex = ex.getNextException();
            }
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
        }
    }
}
