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
import org.main.Entity.Temporaty.TireDepartmentTime;
import org.main.Entity.Works;
import org.main.Utils.Temporary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.itextpdf.io.font.PdfEncodings.CP1250;

public class GenerateSumaryOfWork extends Generator{
    /**
     * Generate PDF
     * @author Jonh
     * @version 1.0
     * @throws FileNotFoundException
     */
    public static void generatePDF(List<Works> works, String departmentName) throws FileNotFoundException {
        try {
            String DEST = getPathFileSaved("Wykonane Zadania");
            //  String DEST = "Faktura Vat NR_" + tmpOrder.getIdZamowienia() + ".pdf";
            PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            pdf.getCatalog().put(PdfName.Lang, new PdfString("PL"));
            Document document = new Document(pdf);
            pdf.setDefaultPageSize(PageSize.A4);
            pdf.getDocumentInfo().setAuthor(Temporary.getWorkers().getFirstName() + " " + Temporary.getWorkers().getLastName());
            pdf.getDocumentInfo().setTitle("Wykonane Zadania");
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

            neastedTTable.addCell(getHeaderTextCellVaue("Wykonano w").setFont(font));
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
            headerTable.addCell(new Cell().add(new Paragraph("Nazwa")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Status")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Data Od")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Data Do")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Czas\nWykonania")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Wydział")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Tag Pracowniak")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Tag Opony")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));

            for (Works work : works) {

                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(work.getIdWork()))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(work.getName())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(work.getStatus())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf((work.getDateStart())))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(work.getDateStop()))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(dataFiff(work.getDateStart(),work.getDateStop()))).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(work.getDepartments().getName())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(work.getWorkers().getTag())).setFont(font).setFontSize(8f));
                headerTable.addCell(new Cell().add(new Paragraph(work.getTires().getTag())).setFont(font).setFontSize(8f));


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
    static String dataFiff(LocalDateTime start, LocalDateTime end)
    {

        Duration duration = Duration.between(start, end);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        StringBuilder sB = new StringBuilder();
        if(days>0){
            sB.append(days + " days ");
        }
        if(hours>0){
            sB.append(hours + " hours ");
        }
        if(minutes>0){
            sB.append(minutes + " minutes ");
        }
        if(seconds>0){
            sB.append(seconds + " seconds");
        }
        return sB.toString();
    }
}


