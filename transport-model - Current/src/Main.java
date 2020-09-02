import com.ideabytes.Constants;
import com.ideabytes.TextPlacement;
import com.ideabytes.transport.entities.TemplateDataRow;
import com.ideabytes.transport.entities.TextCell;
import com.ideabytes.tse.Parameter;
import com.ideabytes.tse.Result;
import com.ideabytes.tse.TSEHandler;
import com.ideabytes.util.DBHandler;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.hibernate.SessionFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
    static String setVerbose;
    static boolean verbose = false;
    static boolean doPageCalc = true;

    static TSEHandler tseHandler = null;
    private static SessionFactory sessionFactory;


    private static final String TEMPLATE_DOC = Constants.TEMPLATE_PATH;
    private static final String OUTPUT_DOC = Constants.OUTPUT_PATH;

    private static PdfDocument pdf = null;
    private static Document document = null;


    public static void main(String args[]) throws FileNotFoundException, MalformedURLException {
        Scanner sc = new Scanner(System.in);
        setVerbose = sc.next();
        if (setVerbose.equals("verbose")) {
            verbose = true;
        }
        tseHandler = new TSEHandler("Transport_Model", "jdbc:sqlite:C:\\Databases\\TSEOutput.db", verbose);
        tseHandler.addMethod("Main", "main", Arrays.asList(new Parameter("args", args)));
        OutputStream fos = new FileOutputStream(OUTPUT_DOC);

        DBHandler dbHandler = new DBHandler();
        // get data
        // Get template metadata for target document template
        List<TextCell> lTextCells = dbHandler.getTextCells();
        // Get shippment data
        List<TemplateDataRow> lShippmentDataRows = dbHandler.getTemplateDataRows();
        //

        try {
            pdf = new PdfDocument(new PdfReader(TEMPLATE_DOC), new PdfWriter(fos));
        } catch (Exception e) {
            System.out.println(e);
        }
        document = new Document(pdf);

        int numMultiRows = 0;
        if (lTextCells != null && lShippmentDataRows != null) {
            // Loop over document template
            for (TextCell cell : lTextCells) {
                // Loop over shipment data
                for (TemplateDataRow shippingRow : lShippmentDataRows) {
                    // Deal with common data that needs to go to every page
                    if (cell.getName().equals(shippingRow.getName()) &&
                            !shippingRow.isMultirow()) {
                        if (cell.getStrike() && shippingRow.isStrikeThrough()) {
                            // call function to make line
                            TextPlacement shipper = new TextPlacement(cell.getX1(), cell.getY1(), cell.getX2(), pdf, document, "", null, false, 1, cell.getFont(), cell.getFontSize(),tseHandler);
                            shipper.makeStrikeThrough();


                        } else if (cell.getImage()) {
                            // call function to make image
                            String lImageString = shippingRow.getTextData();
                            TextPlacement shipper = new TextPlacement(cell.getX1(), cell.getY1(), cell.getX2(), pdf, document, lImageString, null, false, 1, cell.getFont(), cell.getFontSize(),tseHandler);
                            shipper.addImage(cell.getHeight(), cell.getWidth());
                        } else if (cell.getCenteredText() == Constants.CENTERED_TEXT) {
                            String lBoxString = shippingRow.getTextData();
                            if (lBoxString != null) {
                                TextPlacement shipper = new TextPlacement(cell.getX1(), cell.getY1(), cell.getX2(), pdf, document, lBoxString, cell.getFontcolor(), cell.getBold(), 1, cell.getFont(), cell.getFontSize(),tseHandler);
                                try {
                                    shipper.addCenteredText();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        } else {
                            String lBoxString = shippingRow.getTextData();
                            if (lBoxString != null) {
                                TextPlacement shipper = new TextPlacement(cell.getX1(), cell.getY1(), cell.getX2(), pdf, document, lBoxString, cell.getFontcolor(), cell.getBold(), 1, cell.getFont(), cell.getFontSize(),tseHandler);
                                shipper.addText();
                            }
                        }
                        break;
                    } else if (cell.getName().equals(shippingRow.getName()) && shippingRow.isMultirow()) {
                        numMultiRows++;
                    }
                }  // END Loop over shipment data
            } // END Loop over document template

            // See if we need to add any new pages based on multi row data for shippment
            int rowsPerBox = getRowsMultiBox(lTextCells, lShippmentDataRows);
            int extra = getExtraPages(numMultiRows, rowsPerBox);
            if (extra != 0) {
                addPagesToPdf(extra);
            }

            // Now write multirow items to appropriate pages

            // Loop over document template
            for (TextCell cell : lTextCells) {
                // Loop over shipment data
                for (TemplateDataRow shippingRow : lShippmentDataRows) {
                    // Deal with common data that needs to go to every page
                    if (cell.getName().equals(shippingRow.getName()) &&
                            shippingRow.isMultirow()) {
                        int rowNum = shippingRow.getRowNum();
                        int page = rowNum / rowsPerBox + 1;
                        String lRowString = shippingRow.getTextData();
                        TextPlacement shipper = new TextPlacement(cell.getX1(), cell.getY1() - rowNum % rowsPerBox * Constants.SHIPPER_SPACE_MULTIROWS, cell.getX2(), pdf, document, lRowString, cell.getFontcolor(), cell.getBold(), page, cell.getFont(), cell.getFontSize(),tseHandler);
                        shipper.addText();
                    }
                }  // END Loop over shipment data
            } // END Loop over document template

        }

        // Fill in the Page and PagesOf fields in the document once we know how many pages there are in total
        int numOfPages = pdf.getNumberOfPages();
        for (int pageNum = 1; pageNum <= numOfPages; pageNum++) {
            for (TextCell cell : lTextCells) {
                if (cell.getName().equals(Constants.PAGENUM)) {
                    Integer pageAsInteger = new Integer(pageNum);
                    TextPlacement shipper = new TextPlacement(cell.getX1(), cell.getY1(), cell.getX2(), pdf, document, pageAsInteger.toString(), cell.getFontcolor(), cell.getBold(), pageNum, cell.getFont(), cell.getFontSize(),tseHandler);
                    shipper.addText();
                } else if (cell.getName().equals(Constants.PAGEOF)) {
                    Integer numPagesAsInteger = new Integer(numOfPages);
                    TextPlacement shipper = new TextPlacement(cell.getX1(), cell.getY1(), cell.getX2(), pdf, document, numPagesAsInteger.toString(), cell.getFontcolor(), cell.getBold(), pageNum, cell.getFont(), cell.getFontSize(),tseHandler);
                    shipper.addText();
                }
            }
        }

        tseHandler.setReturnForResult("Main","main",new Result("Main method","void"));
        document.close();
        try {
            tseHandler.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.exit(0);
    }

    /**
     * Calculates number of rows
     *
     * @param textCells         List of text cells
     * @param shippmentDataRows Data going into cells
     * @return
     */
    private static int getRowsMultiBox(List<TextCell> textCells, List<TemplateDataRow> shippmentDataRows) {
        tseHandler.addMethod("Main", "getRowsMultiBox",
                Arrays.asList(new Parameter("textCells", textCells),
                        new Parameter("shippmentDataRows", shippmentDataRows)));
        // Loop over document template
        int rowHeight = 0;
        int calc = 0;

        for (TextCell cell : textCells) {
            // Loop over shipment data
            for (TemplateDataRow shippingRow : shippmentDataRows) {
                if (cell.getName().equals(shippingRow.getName()) &&
                        shippingRow.isMultirow()) {
                    rowHeight = cell.getFontsize();
                    break;
                }
                if (rowHeight != 0) {
                    break;
                }
            }  // END Loop over shipment data
        } // END Loop over document template
        calc = rowHeight + Constants.SHIPPER_SPACE_MULTIROWS;
        tseHandler.setReturnForResult("Main", "getRowsMultiBox", new Result("Amount of rows per box", rowHeight));
        return rowHeight == 0 ? 1 : Constants.SHIPPER_SPACE_MULTIBOX / calc;
    }

    /**
     * Determines amount of pages needed
     *
     * @param numRows     Total number of rows
     * @param numRowInBox Number of rows which fit in box
     * @return Number of pages
     */
    private static int getExtraPages(int numRows, int numRowInBox) {
        tseHandler.addMethod("Main", "getExtraPages",
                Arrays.asList(new Parameter("numRows", numRows),
                        new Parameter("numRowInBox", numRowInBox)));
        int normalizedRows = numRows / Constants.SHIPPER_NUM_COLUMNS_MULTIBOX;
        int makePages = 0;
        if (normalizedRows % numRowInBox != 0) {
            makePages = normalizedRows / numRowInBox;
        } else {
            makePages = normalizedRows / numRowInBox - 1;
        }
        tseHandler.setReturnForResult("Main", "getExtraPages", new Result("Calculates number of extra pages", makePages));
        return makePages;
    }

    /**
     * Creates cloned pages
     *
     * @param pageNum how Many new page numbers to add
     */
    private static void addPagesToPdf(int pageNum) {
        tseHandler.addMethod("Main", "addPagesToPdf", Arrays.asList(new Parameter("pageNum", pageNum)));
        System.out.println("addPagesTo doc ");
        try {
            String tmpPath = "C:\\Users\\luka\\Documents\\ItextOutput\\tmp2Box.pdf";
            OutputStream tmpOs = new FileOutputStream(tmpPath);
            PdfDocument tmpPdf = new PdfDocument(new PdfWriter(tmpOs));

            // close original doc and read it into a copy version with reader only
            pdf.close();
            document.close();

            PdfDocument origPdf = new PdfDocument(new PdfReader(OUTPUT_DOC));
            int lastPage = origPdf.getNumberOfPages();
            // copy existing pages
            for (int pgCnt = 1; pgCnt <= lastPage; pgCnt++) {
                PdfPage origPage = origPdf.getPage(pgCnt);
                tmpPdf.addPage(origPage.copyTo(tmpPdf));
            }
            // create new pages up to requested number
            for (int pgCnt = lastPage + 1; pgCnt <= lastPage + pageNum; pgCnt++) {
                PdfPage origPage = origPdf.getPage(1);
                tmpPdf.addPage(origPage.copyTo(tmpPdf));
            }
            origPdf.close();
            tmpPdf.close();
            // Now change pdf to be a copy of newly created one but with both writer and reader
            // so we can continue creating as needed
            pdf = new PdfDocument(new PdfReader(tmpPath), new PdfWriter(OUTPUT_DOC));
            document = new Document(pdf);
        } catch (Exception e) {
            System.out.println("Exception in addPageToPdf " + e.getMessage());
        }
        tseHandler.setReturnForResult("Main", "addPagesToPdf", new Result("Adds cloned pages", "void"));


    }
}






