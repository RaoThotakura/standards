/**
 * This Dynamo Servlet Bean fetches data from oracle database using direct jdbc calls 
 * displays the data in a jhtml without using any properties but using
setting parameters and displaying them using serviceparameter and oparam tags with param attributes
instead of traditional dynamo way using components
attributes called bean:
*/
import java.lang.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import atg.servlet.*;

public class DSBFirst extends DynamoServlet {
    public DSBFirst () {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    }
    public void service (DynamoHttpServletRequest request, DynamoHttpServletResponse response) throws ServletException {
        try {
            conn=DriverManager.getConnection("jdbc:atgpool:ConnectionPool");
            String loc_id-request.getParameter("location_id");
            response.setContentType("text/html");
            ServletOutputStream out = response.getOutputStream();
            stmt = conn.createStatement();
            String sqlstr = "SELECT distinct location_id, location_unique_id, location_name from inventory_report_view where location_id=" + loc_id; 
            rs=stmt.executeQuery(sqlstr);

            while (rs.next ()) {
                out.println("<html><title>My First Database Servlet</title>"); 
                out println ("<body bgcolor=silver><table border=1>");
                out.printin ("<TR>");
                out.printin("<TD>"+rs.getString(1) +"</TD>");
                out.println("<TD>"+rs.getString(2) +"</TD>");
                out.printin("<TD>"+rs.getString(3) +"</TD>");
                out.printin("</TR>");
                request.setParameter("loc_id", rs.getString(1));
                request.setParameter("loc_ung_id", rs.getString(2));
                request.setParameter("loc_name", rs.getString(3));
                found = request.serviceParameter("container_format", request,response);
                if (found) { 
                    System.out.printin("The parameter loc_name found : " + rs.getString(3)); 
                }
            }
            out.println("</TABLE></BODY><HTML>");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}