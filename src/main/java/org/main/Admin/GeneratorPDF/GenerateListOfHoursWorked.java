package org.main.Admin.GeneratorPDF;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
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


import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;
import org.main.Utils.Temporary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.itextpdf.io.font.PdfEncodings.CP1250;


public class GenerateListOfHoursWorked  extends Generator{

    /**
     * Generate PDF
     * @author Jonh
     * @version 1.0
     * @throws FileNotFoundException
     */
    public static void generatePDFSumMonth(List<EmployeesOverworkedTime> employeesOverworkedTimes,String departmentName) throws FileNotFoundException {
        try {
            String DEST = getPathFileSaved("Przepracoowane Godziny");
            //  String DEST = "Faktura Vat NR_" + tmpOrder.getIdZamowienia() + ".pdf";
            PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            pdf.getCatalog().put(PdfName.Lang, new PdfString("PL"));
            Document document = new Document(pdf);
            pdf.setDefaultPageSize(PageSize.A4);
            pdf.getDocumentInfo().setAuthor(Temporary.getWorkers().getFirstName() + " " + Temporary.getWorkers().getLastName());
            pdf.getDocumentInfo().setTitle("Przepracowane Godziny w miesiącu");
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

            neastedTTable.addCell(getHeaderTextCellVaue("Przpracowane Godziny w").setFont(font));
            if(departmentName.equals("")){
                neastedTTable.addCell(getHeaderTextCellVaue("Wszystkiech Wydziałach")).setFont(font);
            }else {
                neastedTTable.addCell(getHeaderTextCellVaue(departmentName)).setFont(font);
            }
            neastedTTable.addCell(getHeaderTextCellVaue("Data Geneowania")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(String.valueOf(new Timestamp(System.currentTimeMillis())))).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue("Od")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(employeesOverworkedTimes.get(0).getStartDate().toString())).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue("Do")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(employeesOverworkedTimes.get(0).getStopDate().toString())).setFont(font);
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
            headerTable.addCell(new Cell().add(new Paragraph("Lp")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Imię")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Nazwisko")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Email")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Przepracowane\nGodziny")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));

            int i=1;
            for (EmployeesOverworkedTime employee : employeesOverworkedTimes) {
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(i))).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph((employee.getFirstName()))).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph(employee.getLastName())).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph(employee.getEmail())).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getWorkingHours()))).setFont(font).setFontSize(10f));
                i++;
            }
            document.add(headerTable);

            document.close();
            openPDFAfterSave(DEST);
        } catch (NullPointerException exception) {
            exception.getMessage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("NULL POIT" + exception.getMessage());
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("IO" + e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    /**
     * Generate PDF
     * @author Jonh
     * @version 1.0
     * @throws FileNotFoundException
     */
    public static void generatePDFSumMonthByDays(List<EmployeesOverworkedTime> employeesOverworkedTimes) throws FileNotFoundException {
        try {
            String DEST = getPathFileSaved("Przepracoowane Godziny");
            //  String DEST = "Faktura Vat NR_" + tmpOrder.getIdZamowienia() + ".pdf";
            PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            pdf.getCatalog().put(PdfName.Lang, new PdfString("PL"));
            Document document = new Document(pdf);
            pdf.setDefaultPageSize(PageSize.A4);
            pdf.getDocumentInfo().setAuthor(Temporary.getWorkers().getFirstName() + " " + Temporary.getWorkers().getLastName());
            pdf.getDocumentInfo().setTitle("Przepracowane Godziny w miesiącu");
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

            neastedTTable.addCell(getHeaderTextCellVaue("Przpracowane Godziny").setFont(font));
            neastedTTable.addCell(getHeaderTextCellVaue("Z wybranego miesiąca")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue("Data Geneowania")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(String.valueOf(new Timestamp(System.currentTimeMillis())))).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue("Od")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(employeesOverworkedTimes.get(0).getStartDate().toString())).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue("Do")).setFont(font);
            neastedTTable.addCell(getHeaderTextCellVaue(employeesOverworkedTimes.get(employeesOverworkedTimes.size()-1).getStopDate().toString())).setFont(font);
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


            float sixTableColumn[] = {50f, 150f, 150f, 100f,100f,100f};
            Table headerTable = new Table(sixTableColumn);
            headerTable.addCell(new Cell().add(new Paragraph("Lp")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Imię")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Nazwisko")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Email")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Dzień")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));
            headerTable.addCell(new Cell().add(new Paragraph("Przepracowane\nGodziny")).setFont(font).setBold().setFontColor(white).setFontSize(10f).setBackgroundColor(black, 0.4f));

            int i=1;
            float sum=0;
            for (EmployeesOverworkedTime employee : employeesOverworkedTimes) {
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(i))).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph((employee.getFirstName()))).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph(employee.getLastName())).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph(employee.getEmail())).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getStopDate()))).setFont(font).setFontSize(10f));
                headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getWorkingHours()))).setFont(font).setFontSize(10f));
                sum+=employee.getWorkingHours();
                i++;
            }

            headerTable.addCell(new Cell().add(new Paragraph(" -- ")).setFont(font).setFontSize(10f));
            headerTable.addCell(new Cell().add(new Paragraph(" -- ")).setFont(font).setFontSize(10f));
            headerTable.addCell(new Cell().add(new Paragraph(" -- ")).setFont(font).setFontSize(10f));
            headerTable.addCell(new Cell().add(new Paragraph(" -- ")).setFont(font).setFontSize(10f));
            headerTable.addCell(new Cell().add(new Paragraph("Suma ")).setFont(font).setFontSize(10f));
            headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(sum))).setFont(font).setFontSize(10f));
            document.add(headerTable);

            document.close();
            openPDFAfterSave(DEST);
        } catch (NullPointerException exception) {
            exception.getMessage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.show();
            e.printStackTrace();
        }
    }

}
