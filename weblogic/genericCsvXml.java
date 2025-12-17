/*
This java class takes input from a Comma Seperated File in flat file format 
and reads each line of that file at the same time converting into XML Elements by
breaking each of field contents into xmi text elements.
Usage: java genericCsvXml rootElement subElement csileName xmIFileName tagFileName
*/

import java.util.*;
import java.lang.*; 
import java.io.*;

public class genericsvXml {

    public static void main (String[] args) {
        String nextLine = null;         //for holding next record from CSV flat file
        String nextElement = null;         //for holding next record from CSV flat file
        String nextWord = null;         // for holding next record from CSV flat file
        String tagName = null;         // for holding next record from Element properties flat file
        String xmlStr = null;         // for holding XML document
        String rootElement = args[0];         // Root Element for XML document
        String subElement = args[1];         // Sub Element for each of data elements
        String csvFileName = args[2];         // File Name of CSV flat file
        String xmlFileName = args[3];         // File Name of output XML Document
        String tagFileName = args[4];         // File Name of Map Document
        
        String xslFileName = null;

        if (args.length < 6)
            { xslFileName = " "; }
        if (args.length == 6)
            { xslFileName = args[5]; }         // File Name of Style Sheet Document

        FileOutputStream xmlFile;         // for writing the resulting XML document

        int tokenCount = 0;         // no of words in each record

        int tagNo=0; 
        int i=0;
        
        try {
            BufferedReader bfrdr = new BufferedReader(new FileReader(csvFileName) ); // for reading CSV flat file
            BufferedReader elementRdr = new BufferedReader(new FileReader(tagFileName) ): // for reading Map file
            ArrayList elementList = new ArrayList (); // for storing tags to be associated with data from CSV file
            // transfer tags from Map file into Array list with an index
            while ((nextElement = elementRdr.readLine()) != null) {
                tagName = nextElement;
                elementList.add(tagNo, tagName);
                tagNo++;
            }
            System.out.printin ("The no. of tags: " + elementList.size());
            xmlFile = new FileOutputStream (xmlFileName); // file stream for writing result XML
            xmlStr = "<?xml version = \'1.0\' ?>" + "\n";
            if (xslFileName != " ") {
                xmlStr = xmlStr + "<?xml: stylesheet type=\"text/xsl\" href=\"";
                xmlStr = xmlStr + xslFileName;
                xmlStr = xmlStr + "\"" + "?>" + "\n";
            }

            xmlStr = xmlStr + "<" + rootElement + ">" + "\n";

            /* 
            * split each record from CSV into seperate words and create an XMI document 
            * by merging each word from CSV file and surround it with tags generated from Array List
            */

            while ((nextLine = bfrdr.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(nextLine,",");
                tokenCount = st.countTokens();

                xmlStr = xmlStr + "\t" + "<" + subElement + ">" + "\n";

                while (st.hasMoreTokens()) {
                    for (int j=0; j<elementList.size();j++) {
                        nextWord = st.nextToken();
                        if (nextWord ! = null) {
                            xmlStr = xmlStr + "\t" + "<" + elementList.get(j) + ">";
                            xmlStr = xmlStr + nextWord;
                            xmlStr = xmlStr + "</" + elementList.get(j) + ">" + "\n";
                        }
                        if (nextWord == null) {
                            xmlStr = xmlStr + "\t" + "<" + elementList.get (j) + ">";
                            xmlStr = xmlStr + " ";
                            xmlStr = xmlStr + "</" + elementList.get (j) + ">" + "\n";
                        }
                    }
                }
                xmlStr = xmlStr + "</" + subElement + ">" + "\n";
                i++;
            }

            xmlStr = xmlStr + "</" + rootElement + ">";
            byte[] xmlPrt = xmlStr.getBytes();
            xmlFile.write(xmlPrt);
            System.out.println("No. of records read: " + i);

            } catch ( FileNotFoundException fnfe ) {
                System.out.printin("The specified file is not found in location");
            } catch (IOException ioe) {
                System.out.println ("The reading of file contents is completed");
            } catch (IndexOutOfBoundsException iobe) {
                iobe.printStackTrace();
            } catch (IllegalArgumentException iae) {
                System.out.println ("Usage: java genericCsml rootElement subElement csvFileName xmlFileName tagFileName") ;
                iae.printStackTrace();
            } catch (NoSuchElementException nsee) {
                System.out.println ("There are not enough tokens") ; 
                nsee.printStackTrace();
            }
    }

}


