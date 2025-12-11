
import java.awt.Graphics;
import java.awt.Color;// for using Graphics library to draw strings, lines, etc.
import java.awt.Font;// for assigning different as per client requirement to various boxes, lines, words.
import java.lang.String;// for using a fixed Font and style with all the text in the graph area. for string manupulation classes.
import java.util.Date; // for creating date arrays from string data.
import java.util.GregorianCalendar; // for date operations.
import java.util.StringTokenizer; // for working with collection of Strings seperated by a special symbol.

public class Graph {
    private double values[];
    private int arrDN_X[]; // holds the input data from the ASP page through PARAM tag.
    // X Cords of DN graph
    private int arrDN_Y[]:// Y Cords of DN graph
    private int arrConexp_X[];// X Cords of Constrained EX-POST graph
    private int arrConexp_Y[]; // Y Cords of Constrained EX-POST graph
    
    private int arrConff_X[]; // X Cords of Constrained 50/50 graph
    private int arrConff_Y[]; // Y Cords of Constrained 50/50 graph
    private int arrConexa_X[]; //X Cords of Constrained EX-ANTE graph
    private int arrConexa_Y[]; // Y Cords of Constrained EX-ANTE graph
    private int arrSP_X[]; // across X Axis for S & P BARRA GROWTH.
    private int arrSP_Y[]; // across Y Axis for S & P BARRA GROWTH.
    private int intColumns; // holds the value in columns parameter of PARAM tag on the ASP page.
    private int intGraphs; // holds the value in columns parameter of PARAM tag on the ASP page.
    private int intNegative; // contains the number of negative values in the input data.
    private int intPositive; // contains the number of positive values in the input data.
    private int intPixels; // contains the number of pixels for each unit on X and Y Axes.
    private int intTotal; // sum of least negative value and greatest positive value
    private int intMidpoint; // mid point of graph across Y Axis where a horizontal line is drawn
    private int intNextCord; // number of coordinates across Y Axis.
    private double dblNextIncr; // increment value across Y Axis
    private int intNextX; // Location where next index point on X Axis is drawn
    private int intNextDate; // increment in days upon which next date is printed on X Axis
    private int intXCord_1; // X Cordinate of left corner of graph outline
    private int intYCord_1;// Y Cordinate of top corner of graph outline
    private int intXCord_2; // X Cordinate of right corner of graph outline
    private int intYCord_2: // Y Cordinate of bottom corner of graph outline
    private int intYear; // contains year in YYYY format for any date.
    private int intMonth; // contains month in MM format for any date.
    private int tmpMonth; // contains month in MM format for any date.
    private int intDay; // contains day in DD format for any date.
    private int intDays; // contains number of days between any two gives dates.
    private double max; // contains maximum positive value
    private double dblMinval; // contains least negative value
    private double dblMaxval; // contains greatest positive value
    private Font titlefont; // variable of Font type throughout the graph.
    private Font copyfont; // variable of Font type for copyrights line at bottom of graph.
    private Font hdrfont; // variable of Font type for heading line at top of graph.
    private Color colorDn; // variable of Color type for color of DN graph.
    private Color colorConexp; // variable of Color type for color of CONSTRAINED EX-POST graph.
    private Color colorConff; // variable of Color type for color of CONSTRAINED 50/50 graph.
    private Color colorConexa; // variable of Color type for color of CONSTRAINED EX-ANTE graph.
    private Color colorSpbg; // variable of Color type for color of S&P BARRA GROWTH graph
    private Color colorLabels; // variable of Color type for color of S&P BARRA GROWTH graph.
    private Color colorPlus; // variable of Color type for color of S&P BARRA GROWTH graph.
    private Date labels[]; // array of Date object to hold input dates from ASP page.
    private String dates[]; // for the purpose of debugging
    private String strStart; // string containing start date of printing the graph.
    private String strEnd; // string containing start date of printing the graph.
    private GregorianCalendar objGc; // variable for performing Date operations.
    private Date dteToday; // contains start date converted to Date object type.
    private String strPort1;

    private String strPort2;
    private String strPort3;
    private String strPort4;
    private String strPortS;

    /* This overloaded constructor is used to instantiate this class by constrainedGraph1 class for drawing the Multiple
    portfolio DN vs. Constrained graph. Coordinates for X and Y axis passed statically. */

    public Graph(int columns, int intX1,int intX2,int intY1,int intY2,int pixs,String strDate,int intLines) {

        intColumns = columns; // number of dates between portfolio construct and view dates.
        intGraphs = intLines;// number of lines in the graph
        values = new double[intColumns*intLines]; // input data.
        labels = new Date[intColumns];// input dates.
        dates = new String[intColumns];// for the purpose of debugging.
        arrDN_X = new int[intColumns];// across X Axis for DN.
        arDN_Y = new int[intColumns];// across Y Axis for DN.
        arrConexp_X = new int[intColumns];// across X Axis for CONSTRAINED EX-POST.
        arrConexp_Y = new int[intColumns];// across Y Axis for CONSTRAINED EX-POST.
        if (intLines > 2) { // if the graph contains 4, 5 lines then initialize the 3rd, 4th arrays.
            arrConff_X = new int[intColumns]; // across X Axis for CONSTRAINED 50/50.
            arrConff_Y = new int[intColumns];// across Y Axis for CONSTRAINED 50/50.
            arrConexa_X = new int[intColumns];// across X Axis for CONSTRAINED EX-ANTE.
            arrConexa_Y = new int[intColumns];// across Y Axis for CONSTRAINED EX-ANTE.
        }
        if (intLines > 4) { // if the graph contains 5 lines then initialize the 5th array.
            arrSP_X = new int[intColumns];// across X Axis for S & P BARRA GROWTH.
            arrSP_Y = new int[intColumns]; // across Y Axis for S & P BARRA GROWTH.
            intXCord_1 = intX1; // initialize the xCo-ordinate where the Horizontal line starts
            intXCord_2 = intX2; // initialize the xCo-ordinate where the Horizontal line ends
            intYCord_1 = intY1; // initialize the YCo-ordinate where the Vertical line starts
            intYCord_2 = intY2; // initialize the YCo-ordinate where the Vertical line ends
            intPixels = pixs; // number of pixels for each unit across Y Axis.
        }
        titlefont = new Font("Arial",Font.BOLD,12):
        // Arial font with bold face of size 12 px
        copyfont = new Font("Arial",Font.BOLD,10); // Arial font with bold face of size 10 px
        hdrfont = new Font("Arial",Font.BOLD,15);// Arial font with bold face of size 10 px
        colorDn = new Color (0,0,255);// color for lines and cord points of DN graph
        colorConff = new Color(0,255,0);// color for lines and cord points of Conff graph
        colorConexa = new Color(255,0,0);// color for lines and cord points of Conexa graph
        colorSpbg = new Color(0,240,234);// color for lines and cord points of Spbg graph
        colorLabels = new Color(64,0,128);
        colorPlus = new Color(0,153,51);
        strStart = new String(strDate);

    }

    /* The following set/get methods initialize the names of graphs in multiple portfolio tracking depending on number
    of lines they have i.e 4 or 5. */
    public void setPort1 (String vValue)
    { strPort1 = vValue; )

    private String getPort1()
    { return strPort1; }

    public void setPort2(String vValue)
    { strPort2 = vValue; )

    private String getPort2()
    { return strPort2; }

    public void setPort3 (String vValue)
    { strPort3 = vValue; }

    private String getPort3()
    { retum strPort3: }

    public void setPort4 String vValue)
    { strPort4 = vValue; }

    private String getPort4()
    {retum strPort4;}

    public void setPortS(String vValue)
    { strPort5 = vValue: }

    private String getPort5()
    { return strPortS; }

    /** This method is called to return the date in Date(year,month,day) format from String(date). */
    private Date setDate(String date) {
        // split the date string into individual units based on / seperator
        StringTokenizer strinput = new StringTokenizer(date,"/");
        for (int i=1;i<=3;i++) { // loop through each of unit of date.
            //convert the String object type data in the token into Integer object type
            Integer intData=Integer.valueOf(strInput.nextToken);
            switch(i) {
                case 1:
                    intMonth=intData.intValue();// if 1st token then store as month
                    // correct month number bec' for date arithmetic GC store months starting from 0 ending at 11 (Jan-Dec)
                    tmpMonth=intData.intValue()-1;
                case 2: 
                    intDay=intData.intValue(); // if 2nd token then store as day
                case 3:
                    intYear=intData.intValue(); // if 3rd token then store as year
            }
        }
        // return a new Date object to the caller based on above derived date units.
        return new Date(intYear-1900,intMonth-1,intDay);
    }

    /* This method is called to print the heading based on start date, end date and description of graph. */
    private void drawHeading(Graphics g) {
        int yVal=0;
        g.setColor(colorLabels);
        8.setFont(hdrfont);
        yVal = (intYCord_1-30)-25;
        g.drawString ("Cumulative Returns "+strStart+"-"+strEnd,160,yVal);
        yVal = (intYCord_1-30)-10;
        if (intGraphs==2)
            g.drawString(getPort1() + " vs. " + getPort2(),200,yVal);
        else if(intGraphs = 4)
            g.drawString(getPort1() + " vs. " + getPort4(),200,yVal);
        else
            g.drawString(getPort1() + " vs." + getPort5(),200,yVal);
    }

    /* This method provides access to private data member in order to populate the same.It populates a double array
    with input data. */
    public void setValues(double vValue, int intLoc)
    { values[intLoc]=vValue; }

    /* This method provides access to private data member in order to populate the same. It populates a Date array with
    input date in String object type by calling setDate function.*/
    public void setLabels(String date, int intLoc) {
        labels[intLoc]= setDate (date);
        dates[intLoc]= date;
        if (intLoc == (intColumns-1)) // the last date in input is taken as end date.
            strEnd = date;
    }

    /* This method is called by setMaxMin to calculate count of negative and positive values.*/

    private void setCount(int numGraphs)    { // number of entries in the input data is got by multiplying number of days and number of graphs.
        for(int i=0; i < (intColumns*numGraphs); i++)
        if values[i]<0)
            intNegative++; // If the input is negative add 1 to negative counter
        else
            intPositive++; // If the input is negative add 1 to positive counter
    }

    /* This method calculates maximum and minimum value from the input data.*/
    public void setMaxMin(double arrTemp[],int numEntries,int numGraphs) {
        setCount(numGraphs); // if constrained graph call setCount with 5 else 2.
        for(int i=0;i<(intColumns * numGraphs);i++)
        { // for deriving the maximum positive and negative values.
            if(intNegative == (numEntries)) { // if input contains all negative values.
                if(Math.abs(values[i])>Math.abs(max)) {
                    max=Math.abs(values[i]);
                    dbIMaxval=values[i];
                } else { // if input contains all positive values and all other conditions other than above.
                    if(values[i] > max) {
                        max=values[i];
                    }
                }
            }
        }
        if (intNegative != 0 && intPositive != 0) { // if input data contains both positive and negative values
            dblMinval = arTemp[0]; // first value in sorted input array will be least negative value
            dblMaxval = arrTemp[(numEntries)-1]; // last value in sorted input array will be greatest positive value
        }
    }


    /** ToDo upwards from here */

    /* This method is the starting point for drawing graph called by singleGraphl and multipleGraph classes…*/
    public void drawGraph(Graphics g, int numGraphs) {
        drawHeading(g);
        g.setColor(Color.black); // set the color of lines to white.
        g.setFont(titlefont); // set the font for printing units across axes.
        // draw the X (HORIZONTAL-BOTTOM) Axis line
        g.drawLine(intXCord_1,intYCord_2+30,intXCord_2,intYCord_2+30);
        // draw the Y (VERTICAL-LEFT) Axis line
        drawLine(intXCord_1,intYCord_1-30,intXCord_1,intYCord_2+30);
        // draw the X (HORIZONTAL-TOP) Axis line
        g.drawLine(intXCord_1,intYCord_1-30,intXCord_2,intYCord_1-30);
        // draw the Y (VERTICAL-RIGHT) Axis line
        g.drawLine(intXCord_2,intYCord_1-30,intXCord_2,intYCord_2+30);
        drawGrid(g);
        drawYIndices(g); // for drawing index pointers and indices across y axis.
        drawXIndices (g); // for drawing index pointers and indices across x axis.
        getCoordinates(g,numGraphs); //for calculating co-ordinate and drawing the lines of graph.
        switch (numGraphs)
        case 2: // for a single tracking graph with 2 line graphs
            drawLegend(g,2);
            break;
        case 4: // for a multiple tracking graph with 4 line graphs
            drawLegend (g,3); // for printing legend box at the bottom of graph.
            break;
        case 5: // for a multiple tracking graph with 5 line graphs
            drawLegend(g,4); // for printing legend box at the bottom of graph.
            drawCopyrights(g,numGraphs); // for printing copyrights line after legend box.
    }

    /* This overloaded method derives (x,y) coordinate values using a formula based on input data in values array.
    Based on location in values array it stores the (x,y) coordinates in 10 different arrays for each of five graphs. This
    method calls drawLinechart method using the final elements in x,y coordinates arrays, graph name, color to be used
    for the lines and coordinate points...*/
    private void getCoordinates(Graphics g, int numGraphs) {
        int k=0:
        int 1=0;
        int m=0;
        int n=0;
        int o=0;

        double dblXwidth=0.0;
        dblXwidth=(intXCord_2 - intXCord_1)/5;

        for(int i=0; i<(intColumns * numGraphs); i++) { // compute x,y coordinates for DN chart based on every 5th value in input data starting from value O.
            if (i % numGraphs==0) {

                /*X CORDINATE = (NO. OF PIXELS FOR EACH DATE / NO. OF DAYS TO BE ADDED TO GET NEXT
                DATE ON X-AXIS) * DIFFERENCE BETWEEN START DATE AND CURRENT DATE) + POINT WHERE X
                AXIS LINE STARTS. */
                
                arrDN_X[k] = (int)((double)(dblXwidth/intNextDate)*(funDiffdate(dteToday,labels[i/numGraphs]))+intXCord_1);
                arrDN_Y[k] = deriveYCoord(values[i]); // for returning the value of Y Coordinate for this input value
                k++;
                // compute x,y coordinates for CON-EX POST chart based on every 6th value in input data starting from value 1
            } else if (i % numGraphs==1) {
                // X cordinate becomes same as there will be multiple input values on a particular date.
                arrConexp_X[l]= arrDN_X[l];
                arrConexp_Y[l] = deriveYCoord (values (i));
                l++;
                // compute x, y coordinates for CON 50/50 chart based on every 7th value in input data starting from value 2.
            } else if (i % numGraphs==2 && numGraphs > 3) {
                arrConff_X[m] = arrConexp_X[m];
                arConff_Y[m] = deriveYCoord (values[i]);
                m++;
               // compute x,y coordinates for CON-EX ANTE chart based on every 8th value in input data starting from value 3.
            } else if (¡ % numGraphs==3 && numGraphs > 3) {
                arConexa_X[n] = arrConff_X[n];
                arrConexa_Y[n] = deriveYCoord(values[i]):
                n++;
                // compute x,y coordinates for S&P BARRA GROWTH chart based on every 9th value in input data starting from value 4
            } else if (i % numGraphs==4 && numGraphs > 4) {
                arrSP_X[o] = arrConexa_X[o];
                arrSP_Y[o] = deriveYCoord (values[i]);
                o++;
            }
            // call drawLinechart for each of five graphs with last coordinate value from respective arrays as argument.
            if (numGraphs<3) {
                drawLinechart(g,arrDN_X[k-1],arrDN_Y[k-1],k,1); // FOR (Single)
                drawLinechart(g,arrConexp_X[1-1],arrConexp_Y[1-1],1,2);//FORS&P500(Single)
            } else {
                drawLinechart(g,arrDN_X[k-1],arrDN_Y[k-1],k,3);//FORDNgraph
                drawLinechart(g,arrConexp_X[1-1],arrConexp_Y[1-1],1,4);//FOREX-POST
            }
            if (numGraphs > 3) {
                drawLinechart(g,arrConff X[m-1],arrConff Y[m-1],m,5); // FOR EX-50/50
                drawLinechart(g,arrConexa_X[n-1],arrConexa_Y[n-1],n,6);//FOREX-ANTE
            }
            if (numGraphs > 4)
                drawLinechart(g,arrSP_X[o-1],arrSP_Y[o-11,0,7);//FOR S&P 500
        }
    }

    /* This method returns the Y Cordinate of input value which is passed as argument. The formula used is : (Mid
    point of Y Axis - (No. of pixels/Increment for next value to printed on Y Axis)) * input value) */
    private int deriveYCoord (double dblVal)
    { return (int)(intMidpoint-(intPixels/Math.abs(dbINextIncr))*dblVal);}
        
    /* This method returns the difference between start date and current date in days. The primitive double result is
    casted to primitive int type as days are integers. The formula used is :
    ((current date - start date) + 6000086400000 */

    private int funDiffdate (Date dteStart, Date dteEnd)    { 
        return ((int) Math.round((double)(dteEnd.getTime() - dteStart.getTime()) + 60000L) / 86400000D)));
    }
        
    /* This method returns the input double value rounded to single decimal place.
    The formula used is : (10 * input value)/10 */

    private double funRounddouble(double dblVal)
    { return (double)Math.round(10D * dblVal) / 10D; }

    private int funFixspace(int xCord, double dblVal) {
        int xPos=0;
        if (dblVal >= 0.0 && dblVal < 10.0)
            xPos = xCord + 4:
        if (dblVal > 9.9) // for all positive value > 9.9 like 10.0 and so on
            xPos = xCord - 7;
        if (dblVal <-1.0 && dblVal > -9.9) // for all negative values < -1.0 like -2.0 and so on.
            xPos = xCord + 1;
        if (dblVal <-9.9) // for all negative values <-9.9 like -10.0 and so on.
            xPos = ×Cord - 7; 
        return xPos;
    }


    /* This method will draw the line graph between the (x,y) coordinates in the parameter list Based on the string in
    strGraph, it uses different colors for lines and different shapes for co-ordinate points.. This method is called by
    drawLinechart method. */

    private void drawLine(Graphics g,int x1,int y1, int x2,int y2, Color color,int intGraph) {
        g.setFont(titlefont); // set the font to title font.
        g.drawLine(x1,y1,x2,y2); // draw a line between the pair of x, y points.
        switch(intGraph) {
            case 3: //DN
                g.setColor(color);
                drawShape(g,x1,y1,4);
                break;
            case 4: //CON-EXPOST
                g.setColor(color);
                g.drawRect(x1,y1,6,6;
                g.fillRect(x1,y1+1,5,5);
                break:
            case 5: //CON-50/50
                g.setColor(color);
                drawShape(g,x1,y1,3);
                break;
            case 6: //CON-EXANTE
                g.setColor(color.brighter));
                g.drawString("X", (x1-4), (y1+2));
                break;
            case 7: //SPBG
                g.setColor(color);
                g.drawOval(x1-5,y1-5,5,5);
                g.fillOval(x1-5,y1-5,4,4);
                break;
            default: // SP 500
                g.setColor(color):
                g.drawOval(x1-1,y1-5,5,5);
                g.fillOval(x1-1,y1-5,4,4);
        }
    }

    /* This method is called by drawLine to draw the coordinates of the graph in different shapes like rhombus and
    triangle depending on the numSides value in parameter list.. */

    private void drawShape(Graphics g, int x1, int y1,int numSides) {
        int x[] = new int[numSides]; // allocate memory for array of × cords as per number of sides of polygon.
        int y[] = new int[numSides]; // allocate memory for array of y cords as per number of sides of polygon.
        if (numSides>3)  {// build the arrays for x,y cord with values in method arguments.
            x[0] = x[1]-5; // derived fromxl.
            y[0] = y[1]+2; // derived from yl.
            x[1] = x[0]+5;
            y[1] = y[0]-5;
            x[2] = x[0]+10;
            y[2] = y[0];
            *[3] = x[1];
            y[3] = y[2]+5;

        } else {// FOR CREATING A RHOMBUS shape.

            x[0] = x[1]-5; // derived from xl.
            y[0] = y[1]+2; // derived from yl.
            x[1] = x[0]+5;
            y[1] = y[0]-10;
            x[2] = x[0]+10;
            y[2] = y[0];
        }
        //draw the shape by using above initialized arrays of x,y cords.
        // fill the shape with solid block of color set in drawLine method.
        g.drawPolygon(x,y,numSides);
        g.fillPolygon(x,y,numSides);

    }
    /* This method calls draw Line method using the derived coordinates in the array for each of graph. Based on the
    string in strGraph it uses appropriate global arrays for (x,y) points and color for each of graphs.*/

    private void drawLinechart(Graphics g, int lastval_1,int lastval_2,int index,int intGraph) {
        Color color = new Color(0,0,0);
        for (int m=0;m port_2)
            max = port_1;
        else
            max = port 2;
        if (port_2> port_3)
            max = port_2;
        else
            max = port_3;
        if (port_3 > port_1)
            max = port_3;
        else
            max = port_1;
        return max;
    }
    /* This method returns the maximum input parameter by comparing in a linear fashion for a 5 line graph. The length
    is used to fix the width of legend box in drawLegend method. */
    private int maxLength(int port_1, int port_2, int port_3, int port_4) {
        int max = 0:
        if (port_1 > port_2)
            max = port_1;
        else
            max = port_2;
        if (port_2 > port_3)
            max = port_2;
        else
            max = port_3;
        if (port 3 > port_4)
            max = port_3;
        else
            max = port_4;
        if (port_4 > port_1)
            max = port_4;
        else
            max = port_1;
        return max;
    }

    private int maxLength(int port_1, int port_2, int port_3, int port_4, int port_5) {
        int max = 0:
        if (port_1 > port_2)
            max = port_1;
        else
            max = port_2;
        if (port_2 > port_3)
            max = port_2;
        else
            max = port_3;
        if (port_3 > port_4)
            max = port_3;
        else
            max = port_4:
        if (port_4 > port_5)
            max = port_4;
        else
            max = port_5;
        if (port_5 > port_1)
            max = port_5:
        else
            max = port_1;
        return max;
    }

    /* This method draws the legend box for both Single and Multiple Pf graphs at the bottom of the same. The string
    used for representing portfolio graph is passed through argument in method call. */

    private void drawLegend (Graphics g,int track) {
        int xCordText=130; // set the x cord for text in legend
        int xCordImg=110; // set the x cord for images in legend
        int tmpLen=105; 
        int intLen=O; // derive the width used to print the string of first graph
        g.setFont(titlefont); // set the font for printing strings in legend box.

        if (intGraphs <= 2)
            tmpLen = getPort20.length; // get the length of string for graph.
        else if (intGraphs <= 4) // get the length of string for graph.
            tmpLen = maxLength(getPort1().length().getPort2().length(),getPort3().length();
        else if (intGraphs > 4)
            tmpLen = maxLength(getPort1().length().getPort2().length().getPort3().length().getPort4().length().getPort5().length());
        // get the length of string for graph.
        intLen = Math.round((tmpLen+7)*intPixels)/titlefont.getSize()) * 3;
        g.setColor(Color.white); // set the color for drawing outline of legend box to white
        switch(track) {
        case 2: // draw a legend box for single tracking portfolio graph with 2 lines
            // draw a rectangle at X,Y and width and height of WH
            g.draw3DRect(90, (intYCord 2+(intPixels*2)),intLen,45, true);
            drawOval(g,Color.red,xCordImg,intYCord_2+(intPixels*2),9,9,12);
            // draw the string "" at X,Y
            g.drawString (getPort1(),xCordText, (intYCord_2+ (intPixels*2)+20));
            drawOval (g, Color.blue,xCordImg,intYCord_2+(intPixels*3),9,9,0);
            // draw the string "S&P 500" at X
            g.drawString (getPort2(), CordText, (intYCord_2+(intPixels*3)+10));
            break;
        case 3: // draw a legend box for multiple tracking portfolio graph with 4 lines
            // draw a rectangle at X,Y of width W and height H
            g.draw3DRect(90, (intYCord_2+ (intPixels*2)),intLen,85,true);
            g.setColor(colorDn); // set the color of DN graph shape to pre-designed color.
            // call drawShape to draw rhombus for DN grap at x,y.
            drawShape(g,xCordImg,(intYCord_2+(intPixels*2)+13),4);
            // print the string in method argument list for DN graph
            g.drawString(getPort1(),xCordText,(intYCord_2+(intPixels*2)+20));
            drawBullet(g,Color.magenta,×Cordimg,intYCord_2+(intPixels*2),11,9,26);

            // print the string Ex-Post for Ex-Post graph at supplied x,y cords
            g.drawString(getPort2(), xCordText, (intYCord_2+(intPixels*2)+37));
            g.setColor(colorConff); // set the color of CON. 50/50 graph shape to pre-designed color.
            // call drawShape to draw triangle for Ex 50/50 grap at x,y.
            drawShape(g,xCordImg,(intYCord_2+(intPixels*2)+50)+2,3);
            // print the string Ex 50/50 for Ex 50/50 graph at supplied x,y cords
            g.drawString(getPort3(),xCordText,(intYCord_2+(intPixels*2)+55));
            drawOval(g.colorSpbg, xCordImg, ((intYCord_2+(intPixels*2)+63)42),8,8,0);
            // print the string S&P 500 for S&P 500 graph at supplied x,y cords
            g.drawString(getPort4(),xCordText,(intYCord2+(intPixels*2)+73));
            break;
        case 4: // draw a legend box for multiple tracking portfolio graph with 5 lines
            // draw a rectangle at X,Y of width W and height H
            g.draw3DRect(90, (intYCord_2+(intPixels*2)), intLen, 92, true);
            g.setColor(colorDn); // set the color of DN graph shape to pre-designed color.
            // call drawShape to draw rhombus for DN grap at x,y.
            drawShape(g,xCordImg,(intYCord_2+(intPixels*2)+13),4);
            // print the string in method argument list for DN graph
            g.drawString(getPort1(),xCordText,(intYCord2+(intPixels*2)+20)):
            drawBullet(g,Color.magenta,xCordImg,intYCord_2+(intPixels*2),11,9,26);
            // print the string Ex-Post for Ex-Post graph at supplied x,y cords
            g.drawString(getPort2(),xCordText,(intYCord_2+(intPixels*2)+37));
            g.setColor(colorConf); // set the color of CON. 50/50 graph shape to pre-designed color.
            // call draw Shape to draw triangle for Ex 50/50 grap at x,y.
            drawShape(g,xCordImg,(intYCord_2+(intPixels*2)+50)+2,3);
            // print the string Ex 50/50 for Ex 50/50 graph at supplied x,y cords
            g.drawString(getPort3(),xCordText,(intYCord_2+(intPixels*2)+53));
            g.setColor(colorConexa); // set the color of CON. EX-ANTE graph shape to pre-designed color.
            // print the string X for Ex Ante graph at supplied x,y cords
            g.drawString("X",(xCordImg-4), ((intYCord_2+(intPixels*2)+68)+2));
            // print the string Ex-Ante for Ex-Ante graph at supplied x, y cords
            g.drawString(getPort4(),xCordText, (intYCord_2+(intPixels*2)+70));
            drawOval(g,colorSpbg,xCordImg,intYCord_2+(intPixels*2),8,8,80);
            // print the string S&P 500 for S&P 500 graph at supplied x,y cords
            g.drawString(getPort5(),xCordText,(intYCord_2+(intPixels*2)+88));

        }
    }

    private void drawOval(Graphics g, Color color,int xCordImg, int yCordImg,int width,int height,int incr) {
        g.setColor(color); // set the color to Red for graph shape.
        // draw a oval for representing graph at x,y of width W and height H
        B.drawOval(xCordImg-5,yCordImg+incr,width,height);
        // fill the oval created earlier with red color for graph shape.
        g. fillOval(xCordImg-4,yCordImg+incr,width-1,height-1);
    }

    private void drawBullet(Graphics g, Color color,int xCordImg,int yCordImg, int width,int height, int incr) {
        g.setColor(color); // set the color of CON. EX-POST graph shape to magenta.
        // draw a rectangle at X,Y of width W and height H
        g.drawRect(xCordImg-5),yCordImg+incr,width,height);
        // fill the above drawn rectangle with magenta color
        g.fillRect((xCordImg-4),yCordImg+incr,width-1,height);
    }

    /* This method prints the values across Y Axis by calling 3 different methods for each of following cases.
    funAlIPositive - for input containing all positive values. funAlINegative - for input containing all negative values.
    funNegativeNpositive - for input containing both positive and negative values. */

    private void drawYIndices(Graphics g) { // Draw the indices across the Y-Axis along with index indicators.

        if (intPositivel=0 && intNegative==0) { // FOR ALL POSITIVE VALUES
            funAllPositive(g,35,intMidpoint);//45
            if (intNegative!=0 && intPositive==0) { // FOR ALL NEGATIVE VALUES
                funAlINegative(g,3 5,intMidpoint, Color.red); //Red color is used to print the negative values
                if (intNegative!=0 & & intPositive!=0) { // FOR POSITIVE AND NEGATIVE VALUES
                    funNegativeNpositive(g,35,intMidpoint,Color.red); // Red color is used to print the negative values
                }
            }
        }
    }

    /* This set method sets the no. of pixels at which next label is printed across X Axis using following equation: No.
    of pixels for each label across X Axis = (X Cord 2 of X Axis - X Cord 1 of X Axis) / 5 */

    private void setNextX0 {
    intNextX = (int)( (intXCord_2-intXCord_1)/5); }

    /* This get method gets the no. of pixels for each label across X Axis which is computed in setNextX method. */
    private int getNextX()
    { return intNextX; }

    /* This method prints the labels i.e. dates across X Axis using dates derived dynamically based on start date and end
    date. It uses the GregorianCalendar class to derive the next date to be printed by adding number of days. Different
    formulae are used as follows: No. of days = difference in days between start date and final date in input date array.
    No. of days to be incremented for printing the next date = no. of days / 5. Next date printed across x axis = current
    day + no. of days to be incremented for printing the next date. */

    private void drawXIndices(Graphics g) {
        String dteLabel;
        g.setColor(colorLabels); // set the color of date labels to bright yellow
        int intX1=intXCord_1-15; // store X Cord 1 in a local variable for future calculations.
        dteToday=setDate(strStart); // call setDate method to store the start date in Date(year,month,day) format.
        // calculate no. of days between start date and last date in input
        intDays = funDiffdate(dteToday,labels[intColumns-1);
        intNextDate = (int) (Math.ceil (double)intDays/5)); // Increment upon which next date is printed on X Axis
        // Instantiate a GC object with start date whose individual elements are set in seDate method.
        objGc = new GregorianCalendar(intYear,tmpMonth,intDay);
        for (int i=0; ¡<=intDays; i+=intNextDate) { // loop number of days times
        
            if (i==0) // for origin print the start date in in MM/DD/YY format at x, y cordinates.
                dteLabel = (intMonth+"/"+intDay+"/"+(intYear+"").substring (2,4));
            else /* for all other indices calculate next date to be printed by calling addDate method.*/
                dteLabel = addDate();
             /*if (check Weekend (dteLabel)) (*/
            g.drawString(dteLabel,intX1,intYCord_2+47); 
            intX1+=getNextX(); // increment the x cordinate by no. of pixels derived in setNextX.
            //}
        }
        dteLabel = addDate();
        //if (checkWeekend(dteLabel))
        g.drawString (dteLabel,intX1,intYCord_2+47); // print the final date.
    }

    private boolean checkWeekend (String currentDate) {
        Date current = new Date(currentDate);
        boolean flag = false;
        for (int i=O; i = dblMaxval; i += dblNextIncr)  ( // loop until the Least negative input value by increment as per above formula.
            g.setColor(color); // set the color to red as in argument for printing negative values.
            if (¡==0.0) {
                g.setColor(colorPlus); // set the color to bright yellow
                // draw a horizontal line for value 0.0 to distinguish all negative values.
                g.drawLine(intXCord1-5),yCordinate,intXCord_2-7,yCordinate);
                // print the index string by rounding to single decimal place.
                g.drawString(i+"",funFixspace(×Cordinate,i),yCordinate);
            } else {
                dblRounded = funRounddouble(i);
                g.drawString(dblRounded+"",funFixspace(×Cordinate,dblRounded),yCordinate);
            }   
            yCordinate+=intPixels; // increment the Y Cord 1 by no. of pixels to get next y cordinate.
        }
        dblRounded = funRounddouble(1);
        // print the index string by rounding to single decimal place.
        g.drawString (dblRounded+"",funFixspace(xCordinate,dblRounded),yCordinate);
    }
    

    /* This method prints values across Y Axis when the input contains both positive and negative values. The values to
    be printed are derived dynamically in this method based on following equation: increment to be added to print next
    value = (sum of maximum positve value and least negative value/no. of cords across Y Axis); */

    private void funNegativeNpositive(Graphics g,int xCordinate,int yCordinate, Color color) {
        double dblOrigin,i; 
        int intYcordinate; 
        double dblRounded=0.0;
        int xPos=0;

        // increment mid point by no. of pixels for printing negative coordinates of graph.
        intYcordinate = yCordinate+intPixels;

        /* define a loop that starts at 0.0 and moves in a forward direction for POSITIVE until it reaches
        MAXIMUM INPUT VALUE */

        g.setColor(colorPlus);
        // set the color to bright yellow for drawing horizontal middle line and printing cord values
        dblOrigin = 0.0;
        for (i=dblOrigin; i<=dblMaxval; i+=dbINextIncr)  { // loop until the maximum positive input value by increment as per above formula
                if (i==0.0) // draw a line that divides positive and negative values on Y-axis
                    g.drawLine((intXCord_1),yCordinate,intXCord_2,yCordinate);
                dblRounded = funRounddouble(i);
                // print the index string by rounding to single decimal place.
                g.drawString(dblRounded+"",funFixspace(xCordinate,dblRounded),yCordinate);
                yCordinate=yCordinate-intPixels;// decrement the Mid point by no. of pixels to get next y cordinate.
        }
        dblRounded = funRounddouble(i);
        // print the index string by rounding to single decimal place.
        g.drawString(dblRounded+"",funFixspace(xCordinate,dblRounded),yCordinate);

        /* Define a loop that starts at -1.0 and moves in a backward direction for NEGATIVE until it reaches
        LEAST NEGATIVE VALUE */

        g.setColor(color); // set the color to red for printing negative values.
        dblOrigin = (dblNextincr * -1.0); // calculate the origin as -1.0 for continuing printing negative values.
        for (i = dblOrigin; i >= dblMinval; i -= dblNextincr) { // loop until the least negative input value by increment as per above formula.
                dblRounded = funRounddouble(i);
                // print the index string by rounding to single decimal place.
                g.drawString(dblRounded+"",funFixspace(×Cordinate,dblRounded),intYcordinate);
                // increment the Mid point by no. of pixels to get next y cordinate.
                intYcordinate=intYcordinate+intPixels;
        }
        dblRounded = funRounddouble(i);

        // print the index string by rounding to single decimal place.

        g.drawString(dbIRounded+"",funFixspace(xCordinate,dblRounded),intYcordinate);
    }

    /* This method draws a grid in the graph area for accurate coordinate verification by user. */
    private void draw Grid(Graphics g) {

        setNextX(); // call this to derive no. of x cordinates.
        g.setColor(Color.black); // set the color to light gray for drawing grid lines.
        //loop through until Y Cord 2 for drawing HORIZONTAL lines at each of pixels fixed in class constructor.
        for (int i=intYCord_1; i<=intYCord_2; i+=intPixels) 
            //draw a horizontal line with newly derived y cordinates between X Cord 1 and X Cord 2.
            g.drawLine(intXCord_1,i,intXCord_2,i);
            // loop until least negative value to find out number of negative values.
            for (int i = intXCord_1+getNextX(); i-=dblNextIncr)
                j++; // contains number of indices for negative values.
            intMidpoint=(int)Math.abs(intYCord_2-(j*intPixels))+intPixels;
            // Location on Y Axis where the horizontal line that divides the graph into 2 is drawn
        else {
            if (intPositive != 0 && intNegative == 0)  { // FOR ALL POSITIVE VALUES
                // Increment upon which next input value is printed on Y Axis
                dblNextIncr=(double)(max/intNextCord);
                // Location on Y Axis where the horizontal line that divides the graph into 2 is drawn
                intMidpoint = intYCord_2;
            }
            if (intNegative!=0 && intPositive==0) { // FOR ALL NEGATIVE VALUES
                dblNextIncr=(double)((double)dblMaxval/intNextCord);
                // Increment upon which next input value is printed on Y Axis
                intMidpoint = intYCord_1; // Location on Y Axis where the horizontal line that divides the graph into 2 is drawn
            }
        }
    }

}