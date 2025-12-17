/**
No copyright, no warranty; use as you will.
Written by Ronald Bourret, Technical University of Darmstadt, 2000
*/
import de.tudarmstadt.ito.xmldbms.DBMSToDOM;
import de.tudarmstadt.ito.xmldbms.DOMToDBMS;
import de.tudarmstadt.ito.xmldbms.Map;
import de.tudarmstadt.ito.xmldbms.mapfactories.MapFactory_MapDocument;
import de.tudarmstadt.ito.mldbms.helpers.KeyGeneratorImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.xml.sax.InputSource;
import org.xml.sax.Parser; 
import org.w3c.dom.Document;
//Imports for the Oracle version 2 parser
import de.tudarmstadt.ito.domutils.DF_Oracle2;
import oracle.xml.parser.v2.SAXParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.DOMParser;

public class InsertResultSet {

    public static void main (String[] argv) {
        String mapFilename = "employee1.map",
        xmIFilename = "employee_out.xml",
        url = "jdbc:odbc:xmldbms";
        try {
            toDBMS (mapFilename, mlFilename, url) ;
        } catch (IllegalArgumentException iae) {
            System.out.println("\nUsage: java InsertResultSet (-t <table-name> | -s ‹SELECT-statement>} ‹map-file> <xl-file>\n\nIf a SELECT statement is used, it must be enclosed in quotes. \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void toDBMS (String mapFilename, String xmlFilename, String url) throws Exception {
        Connection conn1 = null, conn2 = null;
        Map map;
        DOMToDBMS domToDBMS;
        Document doc;
        KeyGeneratorImpl keyGenerator = null;
        try {
            // Connect to the database and get the result set
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn1 = DriverManager.getConnection (url, "rsi", "rsi");
            conn2 = DriverManager.getConnection (url, "rsi", "rsi");
            // Create and initialize a key generator
            keyGenerator = new KeyGeneratorImpl(conn1);
            keyGenerator.initialize();
            // Create the Map object.
            map = createMap(mapFilename, conn1);
            //Use a user-defined function to create a DOM tree over employee_out.xml
            doc = openDocument(xmlFilename);
            System.out.println ("MAP IS " + map);
            // Create a new DOMTODBMS object and store the data.
            domToDBMS = new DOMTODBMS(map, keyGenerator, new NQ_Oracle2()) :
            domToDBMS.storeDocument(doc) ;
        } finally {
            // Close the connection
            if (conn1 != null) conn1.close();
            if (conn2 != null) conn2.close();
        }

    }
    /***********************************************************************
    General
    utility
    methods
    ***********************************************************************/

    static Map createMap (String mapFilename, Connection conn1) throws Exception {
        MapFactory_MapDocument factory;
        // Create a new map factory and create the Map.
        factory = new MapFactory_MapDocument (conn1, getSAXParser()) ;
        return factory.createMap(new InputSource (get FileURL (mapFilename) ));
    }

    static String getFileURL (String fileName) {
        File file;
        file = new File (fileName);
        return "file:///" + file.getAbsolutePath();
    }

    /***********************************************************************
    Methods that use the Oracle version 2 parser
    Comment these methods out if you are using a different parser.
    ***********************************************************************/

    static Parser getSAXParser() {
        // WARNING! This code is specific to the Oracle parser.
        SAXParser parser;
        // Instantiate the parser and set various options
        parser = new SAXParser();
        parser.setValidationMode(true);
        return parser;
    }
    static void writeDocument (Document doc, String xmlFilename) throws Exception {
        // WARNING! This code is specific to the Oracle parser.
        FileOutputStream xmlFile;
        // Write the DOM tree to a file.
        xmlFile = new FileOutputStream(xmlFilename);
        ((XMLDocument) doc).print((OutputStream) xmlFile);
        xmlFile.close();
    }
    static Document openDocument (String xmlFilename) throws Exception {
        // WARNING! This code is specific to the Oracle parser.
        DOMParser parser;
        // Instantiate the parser and set various options.
        parser = new DOMParser();
        parser.setValidationMode(false);
        parser.showWarnings(true);
        // Parse the input file
        parser.parse(new InputSource (getFileURL (xmlFilename)));
        // Return the DOM tree
        return parser.getDocument();
    }
}