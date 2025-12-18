import java.util.*; 
import java.lang.*; 
import java.io.*;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class dataFrame extends JFrame {

    JTextArea resultXML = new JTextArea();
    JTextField txtRoot = new JTextField();
    JTextField txtSub = new JTextField();
    JTextField txtCSVname = new JTextField();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JTextField txtMapname = new JTextField();
    JLabel jLabel4 = new JLabel();
    JButton tranButton = new JButton();
    String nextLine = null;
    String nextElement = null;
    String nextWord = null;
    String xmlStr = null;
    String tagName = null;
    int tokenCount = 0;
    int tagNo=0;
    /**
    *  for holding next record from CSV flat file for holding next record from CSV flat file no of words in each record
    */

    JLabel jLabel5 = new JLabel();
    JScrollPane jScrollPanel = new JScrollPane();
    public dataFrame () {
        super();
        try { 
            jbInit();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    private void jbInit () throws Exception {

        resultXML.setLineWrap(true);
        resultXML.setWrapStyleWord(true);
        resultXML.setDoubleBuffered(true);
        resultXML.setBackground(Color.orange);
        resultXML.setForeground(Color.red);
        resultXML.setFont(new Font("Serif", 0, 12));
        resultXML.setText(" ");

        txtRoot.setBackground(Color.green);
        txtRoot.setText(" ");
        txtRoot.setBounds(new Rectangle (161, 68, 122, 23));

        txtSub.setBackground(Color.cyan); 
        txtSub.setText(" ");
        txtSub.setBounds(new Rectangle (160, 107, 122, 22));

        txtSVname.setBackground(Color.orange);
        txtSVname.setText(" ");
        txtCSVname.setBounds(new Rectangle (159, 138, 125, 23));

        txtMapname.setBackground(Color.pink);
        txtMapname.setText(" ");
        txtMapname.setBounds(new Rectangle (159, 171, 127, 24));

        jLabel4.setText("Map");
        jLabel4.setBounds(new Rectangle (127, 173, 29, 17));

        tranButton.setBackground(Color.yellow); 
        tranButton.setText("Generate XML");
        tranButton.setForeground(Color.magenta);
        tranButton.setBounds(new Rectangle (69, 209, 210, 27));

        jLabe15.setText("Converting CSV flat file format into XMI Document format");
        jLabel5.setForeground(Color.blue);
        jLabel5.setFont(new Font("Serif", 3,12));
        jLabe15.setBounds(new Rectangle (5, 30, 352, 17));

        jScrollPanel.setBounds(new Rectangle (40, 286, 272, 263));
        tranButton.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                tranButton_actionPerformed(e);
            }
        });

        jLabel1.setText("Root Element");
        jLabel1.setBounds(new Rectangle (60, 73, 83, 17));
        jLabel2.setText("Sub Element");
        jLabe12.setBounds(new Rectangle (68, 111, 76, 17));
        jLabel3.setText("Filename of CSV file");
        jLabel3.setBounds(new Rectangle (33, 145, 116, 17));

        this.setSize(new Dimension (364, 584));
        this.getContentPane().setLayout (null);
        this.setTitle ("XML Application using SWING components");

        this.getContentPane().add(resultXML, null);
        this.getContentPane().add(txtRoot, null);
        this.getContentPane().add(txtSub, null);
        this.getContentPane().add(txtCSVname, null);
        this.getContentPane().add(jLabel1,null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add (txtMapname, null);
        this.getContentPane().add(jLabel4, null);
        this.getContentPane().add(tranButton, null);
        this.getContentPane().add(jLabel5, null);
        this.getContentPane().add(jScrollPanel, null);

        jScrollPanel.getViewport().add(resultXML,null);
        this.getContentPane().add(resultXML, null);
        jScrollPanel.getViewport().add(resultXML, null);
        show();
    }

    void tranButton_actionPerformed (ActionEvent e) {
        try {
            BufferedReader bfrdr = new BufferedReader (new FileReader(txtCSVname.getText())); // for reading CSV flat file
            BufferedReader elementRdr = new BufferedReader (new FileReader(txtMapname.getText())); // for reading Map file
            ArrayList elementList = new ArrayList(); // for storing tags to be associated with data from CSV file
            while ((nextElement = elementRdr.readLine()) != null) {
                tagName = nextElement;
                elementList.add(tagNo, tagName);
                tagNo++;
            }
            xmlStr = "<?xml version = \'1.0\' ?>" + "\n" ;
            xmlStr = xmlStr + "<" + txtRoot.getText() + ">" + "\n" ;

            while ((nextLine = bfrdr.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(nextLine, ",");
                tokenCount = st.countTokens();
                xmlStr = xmlStr + " <" + txtSub.getText() + ">" + "In";
                while (st.hasMoreTokens()) {
                    for (int j=0; j<elementList.size(); j++) {
                        nextWord = st.nextToken();
                        if (nextWord != null) {
                            xmlStr = xmlStr + " <" + elementList.get(j) + ">";
                            xmlStr = xmlStr + nextWord;
                            xmlStr = xmlStr + "</" + elementList.get(j) + ">" + "\n" ;
                        }
                        if (nextWord == null) {
                            xmlStr = xmlStr + " <" + elementList.get(j) + ">";
                            xmlStr = xmlStr + " ";
                            xmlStr = xmlStr + "</" + elementList.get(j) + ">" + "\n" ;
                        }
                    }
                }
                xmlStr = xmlStr + " </" + txtSub.getText() + ">" + "\n" ;
                i++;
            }
            xmlStr = xmlStr + "</" + txtRoot.getText() + ">" ;
            resultXML.append(xmlStr);
        } catch (FileNotFoundException fnfe) {
            System.out.println("The specified file is not found in location");
        } catch (IOException ioe ) {
            System.out.println("The reading of file contents is completed"); 
        } catch (IndexOutOfBoundsException iobe) { 
            iobe.printStackTrace(); 
        } catch (IllegalArgumentException iae) {
            System.out.printin ("Usage: java genericCsvXml rootElement subElement csvFileName xm]FileName tagFileName") ;
            iae.printStackTrace();
        } catch (NoSuchElementException nsee) {
            System.out.println("There are not enough tokens");
            nsee.printStackTrace() ; 
        }
    }

    public static void main (String args []) {
        final dataFrame app = new dataFrame();
        app.addWindowlistener (new WindowAdapter () {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}




