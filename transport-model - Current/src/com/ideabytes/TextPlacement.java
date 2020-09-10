package com.ideabytes;

import com.ideabytes.tse.Parameter;
import com.ideabytes.tse.Result;
import com.ideabytes.tse.TSEHandler;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.IRenderer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TextPlacement {
    LayoutResult result;
    int numFontSize;
    int textLeft;
    int textBottom;
    int textRight;
    int listTop;
    TSEHandler tseHandler;
    String font;
    PdfDocument pdf;
    Document document;
    String inputText;
    String textColour;
    boolean bold;

    ArrayList<Integer> listItemVerticalPosition;
    ArrayList<String> inputList;
    int pageCount;

    /**
     * Constructor
     * @param textLeft Placement of paragraph starting on left
     * @param textBottom Placement of paragraph bottom
     * @param textRight Placement of the right side of paragraph
     * @param pdf Pdf being created
     * @param document Document being written on
     * @param inputText Inputted text
     * @param textColour Colour of text
     * @param bold True if text is bold, false if if not
     * @param pgCount Number of pages
     * @param font Name of font
     * @param numFontSize Font size
     *
     */
    public TextPlacement(int textLeft, int textBottom, int textRight, PdfDocument pdf, Document document, String inputText, String textColour, boolean bold, int pgCount, String font, int numFontSize, TSEHandler tseHandler){
        this.tseHandler = tseHandler;
        this.textLeft = textLeft;
        this. textBottom = textBottom;
        this.textRight = textRight;
        this.pdf = pdf;
        this.document = document;
        this.inputText = inputText;
        this.textColour = textColour;
        this.bold = bold;
        this.pageCount = pgCount;
        this.font = font;
        this.numFontSize = numFontSize;
    }






    /**
     * Adds text to document
     */
    public void addText() {
        tseHandler.addMethod("TextPlacement","addText", Collections.emptyList());
        if (inputText == null)
            return;

        Paragraph par = new Paragraph(inputText);
        par.setPageNumber(pageCount);

        //System.out.println("Page reference for pageCount " + pageCount + " is " + pdf.getPage(pageCount));


        Style normal = new Style();
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(Constants.FONT_PATH+this.font+".ttf");
        } catch (Exception e){
            System.out.println(e);
        }
        par.setFont(font).addStyle(normal);
        normal.setFont(String.valueOf(font)).setFontSize(this.numFontSize);
        String array1[]= textColour.split(",");
        if (array1.length!=3){
            System.out.println("Error, color requires 3 values ");
        }
        int r = Integer.parseInt(array1[0]),g = Integer.parseInt(array1[1]),b = Integer.parseInt(array1[2]);

        Color myColor = new DeviceRgb(r, g, b);
        par.setFontColor(myColor);

        if (bold) {
            normal.setBold();
        }


        par.setFixedLeading(this.numFontSize);
        par.setFixedPosition(textLeft, textBottom-getTextHeight(par), textRight - textLeft-10);
        document.add(par);
        tseHandler.setReturnForResult("TextPlacement","addText",new Result("Adds input text to pdf","void"));
    }

    /**
     * Adds centered text to document
     * @throws IOException
     */
    public void addCenteredText() throws IOException {
        tseHandler.addMethod("TextPlacement","addCenteredText",Collections.emptyList());
        if (inputText == null)
            return;

        Paragraph par = new Paragraph(inputText);
        par.setPageNumber(pageCount);

        //System.out.println("Page reference for pageCount " + pageCount + " is " + pdf.getPage(pageCount));

        Style normal = new Style();
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(Constants.FONT_PATH+this.font+".ttf");
        } catch (Exception e){
            System.out.println(e);
        }
        par.setFont(font).addStyle(normal);
        normal.setFont(String.valueOf(font)).setFontSize(this.numFontSize);
        String array1[]= textColour.split(",");
        if (array1.length!=3){
            System.out.println("Error, color requires 3 values ");
        }
        int r = Integer.parseInt(array1[0]),g = Integer.parseInt(array1[1]),b = Integer.parseInt(array1[2]);

        Color myColor = new DeviceRgb(r, g, b);
        par.setFontColor(myColor);
        if (bold) {
            normal.setBold();
        }


        par.setFixedLeading(this.numFontSize);
        par.setFixedPosition(textLeft-getWidth(), textBottom-getTextHeight(par), textRight - textLeft-getWidth());
        document.add(par);
        tseHandler.setReturnForResult("TextPlacement","addCenteredText", new Result("Adds centered text", "void"));
    }

    /**
     * adds line to document
     */
    public void makeStrikeThrough() {
        tseHandler.addMethod("TextPlacement","makeStrikeThrough",Collections.emptyList());
        PdfPage currentPage = pdf.getPage(pageCount);
        PdfCanvas canvas = new PdfCanvas(currentPage);
        canvas.setLineWidth(2).moveTo(textLeft, textBottom)
                .lineTo(textRight, textBottom)
                .closePathStroke();
        tseHandler.setReturnForResult("Textplacement","makeStrikeThrough",new Result("Adds line to document","void"));
    }

    /**
     * adds image to document
     * @param height Height of image
     * @param width Width of image
     * @throws MalformedURLException Image URL
     *
     */
    public void addImage( int height, int width) throws MalformedURLException {
        tseHandler.addMethod("TextPlacement","addImage",
                Arrays.asList(new Parameter("height",height),
                        new Parameter("width",width)));
        PdfPage currentPage = pdf.getPage(pageCount);
        ImageData imageData = ImageDataFactory.create(inputText);
        Image image = new Image(imageData);
        image.setWidth(width);
        image.setHeight(height);
        image.setFixedPosition(textLeft,textBottom);
        document.add(image);
        tseHandler.setReturnForResult("TextPlacement","addImage",new Result("Adds image to pdf","void"));

    }

    /**
     * Gets height of text
     * @param par Paragraph
     * @return Height of paragraph
     */
    public int getTextHeight(Paragraph par)  {
        tseHandler.addMethod("TextPlacement","getTextHeight",Arrays.asList(new Parameter("par",par)));
        int textHeight = 0;

        IRenderer paragraphRenderer = par.createRendererSubTree();
        result = paragraphRenderer.setParent(document.getRenderer()).
                layout(new LayoutContext(new LayoutArea(pageCount, new Rectangle(textRight-textLeft, 10000000))));
        if (result.getOccupiedArea() == null)
            return 0;
        textHeight = (int) result.getOccupiedArea().getBBox().getHeight();

        tseHandler.setReturnForResult("TextPlacement","getTextHeight",new Result("Gets height of paragraph", textHeight));
        return textHeight;
    }

    /**
     * Gets width of input text
     * @return Width of text
     * @throws IOException Font URL
     */
    private float getWidth() throws IOException {
        tseHandler.addMethod("TextPlacement","getWidth",Collections.emptyList());
        PdfFont font = PdfFontFactory.createFont(Constants.FONT_PATH+this.font+".ttf");
        int i = 0;
        int stringWidth = 0;
        int stringCode = 0;

        //when the length of the string gets close enough to the border, exits loop and returns the amount of characters that were able to fit
        for(i = 0;i<inputText.length();i++){
            stringCode = inputText.codePointAt(i);
            stringWidth = stringWidth + font.getWidth(stringCode) * this.numFontSize / FontProgram.UNITS_NORMALIZATION;
            i++;
        }
        tseHandler.setReturnForResult("TextPlacement","getWidth",new Result("Gets width of text",stringWidth));
        return stringWidth;
    }

}
