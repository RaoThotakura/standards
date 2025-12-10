import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import atg.servlet.*;
import atg.rview.*;

public class InventoryAdjustHandler {
    RelationalViewManager RVManager;
    InventoryAdjust[] inventory;
    String query;
    String submit;

    public String getSubmit() { return submit; }
    public void setSubmit (String sub) {submit = sub;}
    public String getQuery() { return query; }
    public void setQuery (String sub){ query = sub; }
    public void setRVManager (RelationalViewManager pRVManager) { this. RVManager = pRVManager;}
    public RelationalViewManager getRVManager () {return RVManager; }

    public void setInventory (InventoryAdjust[] inventory) { this.inventory = inventory; }
    public InventoryAdjust[] getInventory() { return inventory; }
    public boolean handleQuery (DynamoHttpServletRequest Req, DynamoHttpServletResponse Res ) throws Exception
    {
        try {
            RelationalView mRView = RVManager.getRelationalView ("InventoryAdjustRview");
            inventory = (InventoryAdjust []) mRView.select();
            System.out println ("Selected array length*** " + inventory.length+ "..."+ inventory[0]);
        } catch (Exception e) {
            e.printStackTrace(); return false;
        }
        return true;
    }
}