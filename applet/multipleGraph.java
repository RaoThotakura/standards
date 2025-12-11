import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import java.awt.Graphics;//for using Graphics library to draw strings, lines
import java.awt.Color; // for using different colors to various boxes, lines, labels
import java.awt.Image; // for drawing the graph on a image before transferring it to Applet
import java.awt.image.ImageObserver; // this interface contains the success code whether graph is completed prinung on the image
import java.util.StringTokenizer; //for extracting individual data from the input sorted data string.
import java.lang.String; // for creating Strings using individual data from String Tokenizer.
import java.awt.print.*;
import java.lang.RuntimePermission;

public class multipleGraph extends java.applet.Applet {
    Graph mgraph;
    private Graphics objGrpx; // variable of Graphics for drawing the image dynamically derived.
    private String strStart;// string that holds start date from input.
    private ImageObserver imgObsrvr; // variable of ImageObserver type.
    private Image myImage; // vanable of Image type for drawing the graph
    private double tmpVal[]; // for holding the sorted input values
    private int j=0;
    private int intLines=0;
    private int columns=0; // number of days for which data is present.
    private Properties printprefs = new Properties; // store user preferences
    public String strBrowser;
    private double dblVersion;
    RuntimePermission permission;

    public void addButton(String browser) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5,585)):
        Button b = new Button("Print Graph");
        b.setBackground(Color.black):
        b.setForeground(Color.white);
        if (browser.equals("Netscape")) {
            this.add(b);
            b.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { print(); } });
        }
    }

    public void init () {
        Color color = new Color(235,242,238); // back ground color of applet
        int intWidth=580; // initialize the width of applet area.
        int intHeight=600; // initialize the height of applet area.
        int columns=Integer.parseInt(getParameter("columns")); // value in "columns" parameter in tag
        strBrowser = getParameter("browsername"); // get the name of the browser from element in ASP page
        if (getParameter("email").equals("No")) // if no email then put the print button on chart
            addButton(strBrowser);
        dblVersion=Double.valueOf(getParameter("version")).doubleValue();
        intLines=Integer.parseInt(getParameter("noLines")); //valuein"columns" parameter in tag
        tmpVal = new double[columns * intLines]; // allocate memory for temporary array to hold sorted input data.
        strStart = getParameter("startdate"); // get the start date from startdate parameter in ASP page.
        // Graph(no.of dates,x1,x2,y1,y2,no.of pixels,start date on x-axis).
        mgraph= new Graph(columns,60,520,70,340,30,strStart, intLines);
        int i=0; // for incrementing the index in the array that stores input data.

        for (int k=0; k < columns; k++) { // for getting next parameter from applet tag.
            // call getData which calls set Values method to build the array with input data
            getData("object".getParameter("C"+ (k+1)),"#");
        }
        j = 0;
        // call getData which builds temporary array using sorted data from "stData" in tag of ASP page.
        
        j = getData("local",setParameter("srtData"),",");
        mgraph.setPort1(getParameter("port1"));
        mgraph.setPort2(getParameter("port2"));
        mgraph.setPort3(getParameter("port3"));
        mgraph.setPort4(getParameter("port4"));

        if (intLines > 4) {
            mgraph.setPort5(getParameter("port5"));
        }
    
        mgraph.setMaxMin(tmpVal,intLines); //here 4 is number of line mgraphs, which should be dynamic
        mgraph.setMidpointY();
        setSize(intWidth,intHeight)://Set the size of graph area to 600 pixels height and 550 pixels wide.
        setBackground (color): // set the background color of the graph to White.
        myImage = this.createImage(intWidth, intHeight);
    }

    void print() {
        if (strBrowser.equals("Netscape")) { // if the browser on client computer is Netscape
        // Create a permission to access print resources on client computer using PrivilegeManager class in java40.jar
            if (dblVersion == 7.0 // for Netscape 7.0 follow Java 2 Securty Model
                permission = new java.lang.RuntimePermission("queuePrintJob", "read");
            if (dblVersion == 4.5 || dblVersion == 4.7) { // for Netscape 4.5 & 4.7 follow Netscape Security Model
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalPrintJobAccess");
                } catch (netscape.security.ForbiddenTargetException fte) {
                    System.out.println ("Cannot continue printing in Netscape without permission.");
                }
            }    
        }
        Frame trame = new Frame();
        Toolkit toolkit = frame.getToolkit();
        PrintJob job=toolkit.getPrintJob(frame,"multipleGraph",printprefs);
        if (job = null) return;
        Graphics g = job.getGraphics();
        generateImage(g);
        this.printAll(objGrpx);
        g.dispose();
        job.end();
    }
 
    public void paint(Graphics g) {
        generateImage(g):
    }
    
    private int getData(String varType,String data, String delim) {
        StringTokenizer strinput = new StringTokenizer(data,delim);
        while (strInput.hasMoreTokens()) //for extracting each token and storing in the array.
        { //convert the String object type data in the token into Double object type
            Double dblData = Double.valueOf(strInput.nextToken()):
            if (varType.equals("local")) {
                tmpVal[j] = dblData.doubleValue();
            } else {
                mgraph.setValues(dblData.doubleValue(),j);
                j++;   
            }
        }
        // convart the Double object type into double primitive type and build the array through a public method
      return j;
    }

    private void generateImage(Graphics g) {
        // create an image of specified size over which the graph will be first drawn
        myImage=this.createImage(intWidth,intHeight);
        // call this routine using co-ordinates initialized/computed above to draw the outline of the graph area.
        objGrpx=myImage.getGraphics();
        mgraph.drawGraph(objGrpx,intLines);//here 4 is number of line graphs, which should be dynamic
        g.drawImage(myImage,0,0,imgObsrvr);
    }
}
