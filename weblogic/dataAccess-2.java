C: \ATG \Dynamo4.5.1 \doc\lib\pbsi classes\dataAccess. java import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
/**The instance of this class can be used to display data in a jhtml page
*using Page Compilation
*/
public class dataAccess
public String url= null;
public String query = null;
public Connection conn = null;
public Statement stmt = null;
public ResultSet rs = null;
public String [] data = new String [7];
public dataAccess (String url, String query) {
    this.url = url;
    this. query = query;
}

public String[] getData () {
    try {
        this.conn = DriverManager.getConnection (url);
        this.stmt = conn.createStatement();
        rs = stmt.executeQuery (query);
        while (rs.next()) {
            this.data [0] = rs.getString(1);
            this.data [1] = rs.getString(2);
            this.data [2] = rs.getString(3);
            this.data [3] = rs.getString(4);
            this.data [4] = rs.getString(5);
            this.data [5] = rs.getString(6);
            this.data [6] = rs.getString(7);
        }
    } catch (SQLException se) {
        se. printStackTrace () ;
    }
    return this.data;
}