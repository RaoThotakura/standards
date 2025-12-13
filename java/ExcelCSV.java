package excelcsv;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import jxl.*;
/**
 * This class will read an excel spreadsheet file and generate CSV file for each
spreadsheet.The CSV file are named as {Sheet Name}.CV. 
A seperate row at the top of each CSV file indicates the column names in the file.
*/

public class ExcelCSV {

    private Workbook wkDBSchema; // for Workbook.
    private Sheet sheet; //For individual Sheets in the Workbook.
    private String [] sheetNameArray; // for names of individual sheets.
    private Cell cell; // individual cell at a column, row position in the current sheet. 
    private File excelFile; // file to read the input excel spreadsheet file
    private File [] csvOutputFiles; // array of files to generate a seperate cs file for each of sheets
    private String inputFileName; // physical file name supplied as parameter
    private FileOutputStream excelOutFile;

    public ExcelCSV (String inputFile) { // constructor
        inputFileName = new String (inputFile);
    }
    public void loadDBSchemaConfigFile() {
        try {
            excelFile = new File(inputFileName); // for accessing the excel file on a target file system wkDBSchema
            Workbook.getWorkbook (excelFile); // for reading the contents of an excel workbook
        } catch (IOException ioe) {
            System.out.println("Error during file creation");
            ioe.printStackTrace ();
        } catch (jxl.read.biff.BiffException be) {
            System.out.println ("Error during creating Excel Workbook object") ;
            be.printStackTrace();
        }
        finally {
            System.out.println("OKAY");
        }
    }

    public void getIndividualSheetData () { // Processing to be done for each of individual sheets.

        int intNoSheets;  // Total number of sheets.
        int noRows; // Number of rows in each sheet.
        int noColumns; // Number of columns in each sheet.

        String strCellContents = null; // The contents of the current cell at column, row index.
        String strRowContents = null; // The contents of the current cell at column, row index.
        Cell[] columnArray = null;

        intNoSheets = wkDBSchema.getNumberOfSheets(); // Get the total number of sheets.
        csvOutputFiles = new File [intNoSheets]; // Create array of file objects for each sheet in the workbook

        sheetNameArray = wkDBSchema.getSheetNames() ; // Get the sheet names.

        // Create individual File objects with file name coming from {sheetname}.csv

        try {
            // Create array of File objects for each of sheets based on sheet names from above.
            for (int x=0; x <= intNoSheets-1; x++) 
                 csvOutputFiles [x] = new File (sheetNameArray[x].toString()+ ". CSV");
            // Create array of Sheet objects number of sheets from above.
            for (int i =0; i<= intNoSheets - 1; i++) {
                sheet = wkDBSchema.getSheet(i); // get the individual sheet at the index sequentially.
                sheet.getRows(); // for each sheet object from above, access all row locations.
                strRowContents = new String();

                for (int j=0; j <= noRows - 1; j++) {
                    columnArray = sheet.getRow(j);
                    // pertaining to a row.
                    noColumns = columnArray.length;
                    strCellContents = new String();

                    for (int k = 0; k <= noColumns - 1; k++) {                      
                        cell = sheet.getCell(k,j); // Create a cell object at the current column, row index.
                        if (strCellContents.equals("")) 
                            strCellContents = strCellContents + cell.
                            getContents (); // Do not add a comma if it is 2
                        else 
                            strCellContents = strCellContents + "," + cell.
                            getContents () ;
                    }
                    // Add a comma after each field starting from first field
                    strRowContents = strRowContents + strCellContents + "n" ; // Add a newline after the last cell content to continue on next row. strCellContents
                    strRowContents = null; // Release the cell contents object for columns in the next
                }
                saveSheetAsCSV (csvOutputFiles[i], strRowContents);
                // Release the row object for next sheet
                csvOutputFiles [i] = null;
            }
                // Release the storage of the file object which is already created
        } catch (IOException ioe) {
            System.out.println ("Error during file creation I/0 Exception ! ") ;
            ioe.printStackTrace ();
        }
    }

    private void saveSheetAsCSV (File csvFile, String strRowContents) throws IOException,FileNotFoundException {
        ///File with file named as {sheetName}. csv
        // Save the resulting CSV

        try {
            excelOutFile = new FileOutputStream (csvFile);
            byte [] bytXML = strRowContents.getBytes (); // convert to byte array for saving to File excelOut File. write (bytXML) ;
        } catch (FileNotFoundException fnfe) {
            System.out.println ("The specified file is not found in location ") ;
            fnfe.printStackTrace () ;
        }
    }

    public void releasebjects () {
        excelOutFile = null;
        WkDBSchema = null;
        sheet = null;
        sheetNameArray = null;
        cell = null;
    };

    public static void main (String[argv]) {
        ExcelSV excelsv = new ExcelCSV(argv[0]);
        //file location as a command line parameter string
        excelsv.loadDBSchemaConfigFile();
        excelCsv.getIndividualSheetData();
        excelsv.releaseObjects();
    }

}

