import java.sql.Connection;
import java. sql. DriverManager;
import java. sql.ResultSet;
import java.sql.ResultSetMetaData;
import java. sql. Statement;
import java.sql.SQLException;
/**The instance of this class can be
* using Page Compilation Version 0.2
* used to display data in a jhtml page
*/
public class dataAccess {
    public String url = null;
    public String query = null;
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;
    public String (] data = new String [7];
    public int norows=0;
    public String [] cities;
    public dataAccess (String url, String query) {
        this.url = url;
        this.query = query;
        public String [] getData()
        try {
            this.conn = DriverManager.getConnection(url);
            this.stmt = conn.createStatement();
            stmt.executeQuery(query);
            while (rs.next ())
            {
            this.data[0] = rs.getString(1);
            this.data[1] = rs.getString(2);
            this.data[2] = rs.getString(3);
            this.data[3] = rs.getString(4);
            this.data[4] = rs.getString(5);
            this.data[5] = rs.getString(6);
            this.data[6] = rs.getString(7);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return this.data;
    }

    public String [] getCities ()
        try {
            this.conn = DriverManager.getConnection(url);
            this.stmt = conn.createStatement();
            this.stmt.executeQuery(query);
            int k=0;
            while (rs.next ()) { k++; }
            rs.close();
            this.norows = k;
            this.cities= new String[this.norows];
            stmt.executeQuery(query) ;
            int i=0,j=1;
            while (rs.next()) {
                this.cities(i] = rs.getString(j);
                i++;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return this.cities;
    }
}

