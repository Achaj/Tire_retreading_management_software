package org.main.Admin.GeneratorPDF;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.main.Entity.SemiProducts;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;
import org.main.Entity.Temporaty.TireDepartmentTime;
import org.main.Utils.Temporary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static com.itextpdf.io.font.PdfEncodings.CP1250;

public class GenerateStockLevel extends Generator{
    /**
     * Generate PDF
     * @author Jonh
     * @version 1.0
     * @throws FileNotFoundException
     */
    public static void generatePDFStocktTires(List<TireDepartmentTime> tireDepartmentTimeList, String departmentName) throws FileNotFoundException {
        try {
            String DEST = getPathFileSaved("Stan Magazynowy");
            //  String DEST = "Faktura Vat NR_" + tmpOrder.getIdZamowienia() + ".pdf";
            PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            pdf.getCatalog().put(PdfName.Lang, new PdfString("PL"));
            Document document = new Document(pdf);
            pdf.setDefaultPageSize(PageSize.A4);
            pdf.getDocumentInfo().setAuthor(Temporary.getWorkers().getFirstName() + " " + Temporary.getWorkers().getLastName());
            pdf.getDocumentInfo().setTitle("Stan Magazynowy Opon");
            pdf.getDocumentInfo().setKeywords("keywords, pdf, iText 7");


            String pathtofont = "/fonts/Anonymous_Pro.ttf";
            String fontname = GenerateListOfHoursWorked.class.getResource(pathtofont).toString();

            String pathtofontBold = "/fonts/Anonymous_Pro_B.ttf";
            String fontnameBold = GenerateListOfHoursWorked.class.getResource(pathtofontBold).toString();

            PdfFont font = PdfFontFactory.createFont(fontname, CP1250);
            PdfFont fontBold = PdfFontFactory.createFont(fontnameBold, CP1250);

            float col = 285f;
            float threcoll = 190f;
            float fiveColl = 110f;
            float columnWidth[] = {col + 150f, col};
            float fullWidth[] = {threcoll * 3};
            Paragraph oneSp = new Paragraph("\n");

            Table table = new Table(columnWidth);
            table.addCell(new Cell().add(new Paragraph("RFID SOMS")).setFont(fontBold).setBorder(Border.NO_BORDER));
            Table neastedTTable = new Table(new float[]{col / 2, col / 2});

            neastedTTable.addCell(getHeaderTextCellVaue("Stan Magzynowy w").setFont(font));
            if(departmentName.equals("")){
                neastedTTable.addCell(getHeaderTextCellVaue("Wszystkiech Wydziałach")).setFont(font);
            }else {
                neastedTTable.addCell(getHeaderTextCellVaue(departmentName)).setFont(font);
            }
            neastedTTable.addCell(getHeaderTextCellVaue("Data Geneowania")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(String.valueOf(new Timestamp(System.currentTimeMillis())))).setFont(font);

            table.addCell(new Cell().add(neastedTTable).setBorder(Border.NO_BORDER));

            Color gray = new DeviceCmyk(0,0,0,62);
            Color black = new DeviceCmyk(	0, 0, 0, 100);
            Color white = new DeviceCmyk(	0	,0	,0  ,0);
            Border gb = new SolidBorder(gray, 0.5f);

            Table diver = new Table(fullWidth);
            diver.setBorder(gb);

            document.add(table);
            document.add(oneSp);
            document.add(diver);
            document.add(oneSp);

            Table tableDelivery = new Table(fullWidth);
            Border dbgb = new DashedBorder(gray, 0.5f);
            document.add(tableDelivery.setBorder(dbgb));


            float fiveTableColumn[] = {30f,30f,30f,30f, 125f, 125f, 125f, 125f,125f};
            Table headerTable = new Table(fiveTableColumn);
            headerTable.addCell(new Cell().add(new Paragraph("Id")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Wysokość")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Szerokość")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Średnica")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Tag")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Nośność")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Prędkość")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Wydział")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Czas")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));

            for (TireDepartmentTime tire : tireDepartmentTimeList) {

                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(tire.getIdTire()))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf((tire.getHeight())))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf((tire.getWidth())))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf((tire.getDiameter())))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(tire.getTag())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(tire.getLoadIndex())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(tire.getSpeedIndex())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(tire.getDepartmentName())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(tire.getLastDateTime()))).setFont(font).setFontSize(8f));

            }
            document.add(headerTable);

            document.close();
            openPDFAfterSave(DEST);
        } catch (NullPointerException exception) {
            exception.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate PDF
     * @author Jonh
     * @version 1.0
     * @throws FileNotFoundException
     */
    public static void generatePDFStocktSemiProduct(List<SemiProducts> semiProductsList) throws FileNotFoundException {
        try {
            String DEST = getPathFileSaved("Prefabrykaty");
            //  String DEST = "Faktura Vat NR_" + tmpOrder.getIdZamowienia() + ".pdf";
            PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            pdf.getCatalog().put(PdfName.Lang, new PdfString("PL"));
            Document document = new Document(pdf);
            pdf.setDefaultPageSize(PageSize.A4);
            pdf.getDocumentInfo().setAuthor(Temporary.getWorkers().getFirstName() + " " + Temporary.getWorkers().getLastName());
            pdf.getDocumentInfo().setTitle("Stan Magazynowy prefabrykatów");
            pdf.getDocumentInfo().setKeywords("keywords, pdf, iText 7");


            String pathtofont = "/fonts/Anonymous_Pro.ttf";
            String fontname = GenerateListOfHoursWorked.class.getResource(pathtofont).toString();

            String pathtofontBold = "/fonts/Anonymous_Pro_B.ttf";
            String fontnameBold = GenerateListOfHoursWorked.class.getResource(pathtofontBold).toString();

            PdfFont font = PdfFontFactory.createFont(fontname, CP1250);
            PdfFont fontBold = PdfFontFactory.createFont(fontnameBold, CP1250);


            float col = 285f;
            float threcoll = 190f;
            float fiveColl = 110f;
            float columnWidth[] = {col + 150f, col};
            float fullWidth[] = {threcoll * 3};
            Paragraph oneSp = new Paragraph("\n");

            Table table = new Table(columnWidth);
            table.addCell(new Cell().add(new Paragraph("RFID SOMS")).setFont(fontBold).setBorder(Border.NO_BORDER));
            Table neastedTTable = new Table(new float[]{col / 2, col / 2});

            neastedTTable.addCell(getHeaderTextCellVaue("Prefabrykaty").setFont(font));
            neastedTTable.addCell(getHeaderTextCellVaue("Produktów")).setFont(font);

            neastedTTable.addCell(getHeaderTextCellVaue("Data Geneowania")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(String.valueOf(new Timestamp(System.currentTimeMillis())))).setFont(font);
            table.addCell(new Cell().add(neastedTTable).setBorder(Border.NO_BORDER));

            Color gray = new DeviceCmyk(0,0,0,62);
            Color black = new DeviceCmyk(	0, 0, 0, 100);
            Color white = new DeviceCmyk(	0	,0	,0  ,0);
            Border gb = new SolidBorder(gray, 0.5f);

            Table diver = new Table(fullWidth);
            diver.setBorder(gb);

            document.add(table);
            document.add(oneSp);
            document.add(diver);
            document.add(oneSp);

            Table tableDelivery = new Table(fullWidth);
            Border dbgb = new DashedBorder(gray, 0.5f);
            document.add(tableDelivery.setBorder(dbgb));


            float fiveTableColumn[] = {50f, 150f, 150f, 150f, 150f};
            Table headerTable = new Table(fiveTableColumn);
            headerTable.addCell(new Cell().add(new Paragraph("Id.")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Nazwa")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("kategoria")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Tag")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Ilość")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));

            for (SemiProducts products : semiProductsList) {
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(products.getIdSemiProduct()))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph((products.getName()))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(products.getCategory())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(products.getTag())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(products.getAmount()))).setFont(font).setFontSize(8f));
            }
            document.add(headerTable);

            document.close();
            openPDFAfterSave(DEST);
        } catch (NullPointerException exception) {
            exception.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
