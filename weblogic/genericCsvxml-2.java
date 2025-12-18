/*
This java class takes input from a Comma Seperated File in flat file format and 
reads each line of that file at the same time converting into XML Elements 
by breaking each of field contents into xmi text elements.
*/
import java.util.*; 
import java.lang.*; 
import java.io.*;
public class genericCsvxml {
    public static void main (String[] argv) {
        String nextLine = null; // for holding next record from CSV flat file
        String nextElement = null;// for holding next record CSV flat file
        String tagName = null; // for holding next record from Element properties flat file
        String xmlStr = null; // for holding XML document

        //if (argv.length ! = 1) throw new IllegalArgumentException () ;

        String rootElement = argv[0]; // Root Element for XML document
        String subElement = argv[1];// Sub Element for each of data elements
        String csvFileName = argv[2]; // File Name of CSV flat file
        String xmlFileName = argv[3]; // File Name of output XML Document
        String tagFileName = argv[4];// File Name of Map Document

        FileOutputStream xmlFile; // for writing the resulting XML document

        int tokenCount = 0; // no of words in each record

        int tagNo=0;
        int i=1;

        try {
            BufferedReader bfrdr = new BufferedReader(new FileReader(csvFileName));
            BufferedReader elementRdr = new BufferedReader(new FileReader (tagFileName));
            ArrayList elementList = new ArrayList();

            while ((nextElement = elementRdr.readLine()) != null) {
                tagName = nextElement;
                elementList.add(tagNo, tagName);
                tagNo++;
            }

            System.out.println ("The no. of tags: " + elementList.size());
            xmlFile = new FileOutputStream (xmlFileName);
            xmlStr = "<?xml version = \'1.0\' ?>" + "\n";
            xmlStr = xmlStr + "<" + rootElement + ">" + "\n";

            while ((nextLine = bfrdr.readLine()) ! = null) {
                StringTokenizer st = new StringTokenizer(nextLine, ",");
                tokenCount = st.countTokens();
                xmlStr = xmlStr + "\t" + "<" + subElement + ">" + "\n" ;

                while (st.hasMoreTokens()) {
                    for (int j=0; j<elementList.size(); j++) {
                        xmlStr = xmlStr + "\t" + "<" + elementList.get(j) + ">";
                        xmlStr = xmlStr + st.nextToken();
                        xmlStr = xmlStr + "</" + elementList.get(j) + ">" + "\n" ;
                    }
                }
                xmlStr = xmlStr + "</" + subElement + ">" + "\n" ;
                i++;
            }

            xmlStr = xmlStr + "</" + rootElement + ">";
            byte[] xmlPrt = xmlStr.getBytes();
            xmlFile.write(xmlPrt);
            System.out.println ("No. of records read : " + i);
        } catch (FileNotFoundException fnfe) {
            System.out.println("The specified file is not found in location");
        } catch ( IOException ioe ) {
            System.out.println ("The reading of file contents is completed");
        } catch (IndexOutOfBoundsException iobe) {
            iobe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            System.out.printin ("Usage : genericCsvXmi rootElement subElement CSV filename");
            iae.printStackTrace();
        }
    }
}