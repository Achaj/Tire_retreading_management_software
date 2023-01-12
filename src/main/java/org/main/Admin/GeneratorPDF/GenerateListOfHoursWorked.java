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


import com.itextpdf.layout.properties.TextAlignment;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.itextpdf.io.font.PdfEncodings.CP1250;


public class GenerateListOfHoursWorked {
    /**
     * Chosing the file name and path to saved
     * @author Jonh
     * @version 1.0
     * @return string absolute path to file
     */
    private String getPathFileSaved() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Przepracowane_Godziny_" + LocalDate.now());

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
        File file = fileChooser.showSaveDialog(new Stage());
        return file.getAbsolutePath();
    }
    /**
     * Generate PDF BILL
     * @author Jonh
     * @version 1.0
     * @throws FileNotFoundException
     */
    public void generatePDF(List<EmployeesOverworkedTime> employeesOverworkedTimes) throws FileNotFoundException {
        try {
            String DEST = getPathFileSaved();
            //  String DEST = "Faktura Vat NR_" + tmpOrder.getIdZamowienia() + ".pdf";
            PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            pdf.getCatalog().put(PdfName.Lang,new PdfString("PL"));
            Document document = new Document(pdf);
            pdf.setDefaultPageSize(PageSize.A4);


            PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/Anonymous_Pro.ttf", CP1250);
            PdfFont fontBold = PdfFontFactory.createFont("src/main/resources/fonts/Anonymous_Pro_B.ttf", CP1250);
            PdfFont fontBoldItalic = PdfFontFactory.createFont("src/main/resources/fonts/Anonymous_Pro_BI.ttf", CP1250);

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
            neastedTTable.addCell(getHeaderTextCellVaue("DATA ")).setFont(font);
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
            Paragraph productParagraph = new Paragraph("Produkty");

            float sevenCollWidth[] = {30f, 100f, 100f, 100f, 100f, 100f,100f};
            Table headerTable = new Table(sevenCollWidth);
            headerTable.setBackgroundColor(black, 0.7f);
            headerTable.addCell(new Cell().add(new Paragraph("Lp.")).setFont(font).setBold().setFontColor(white).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Imię")).setFont(font).setBold().setFontColor(white).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Nazwisko")).setFont(font).setBold().setFontColor(white).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Email")).setFont(font).setBold().setFontColor(white).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Czas Od")).setFont(font).setBold().setFontColor(white).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Czas Do")).setFont(font).setBold().setFontColor(white).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Przepracowane Godziny")).setFont(font).setBold().setFontColor(white).setBorder(Border.NO_BORDER));

            Table detailsTable = new Table(sevenCollWidth);
            int i=1;
            for (EmployeesOverworkedTime employee : employeesOverworkedTimes) {
                detailsTable.addCell(new Cell().add(new Paragraph(String.valueOf(i))).setFont(font).setFontSize(10f));
                detailsTable.addCell(new Cell().add(new Paragraph((employee.getFirstName()))).setFont(font).setFontSize(10f));
                detailsTable.addCell(new Cell().add(new Paragraph(employee.getLastName())).setFont(font).setFontSize(10f));
                detailsTable.addCell(new Cell().add(new Paragraph(employee.getEmail())).setFont(font).setFontSize(10f));
                detailsTable.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getStartDate()))).setFont(font).setFontSize(10f));
                detailsTable.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getStopDate()))).setFont(font).setFontSize(10f));
                detailsTable.addCell(new Cell().add(new Paragraph(String.valueOf(employee.getWorkingHours()))).setFont(font).setFontSize(10f));
                i++;
            }
            document.add(headerTable);
            document.add(detailsTable);
            document.close();
            System.out.println("Awesome PDF just got create :");
        } catch (NullPointerException exception) {
            exception.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Formating Cell header
     *
     * @param text
     * @return Cell formated
     * @author Jonh
     * @version 1.0
     */
    Cell getHeaderTextCellVaue(String text) {
        return new Cell().add(new Paragraph(text)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    /**
     * Formating Cell
     *
     * @param text
     * @return Cell formated
     * @author Jonh
     * @version 1.0
     */
    Cell getCell10Left(String text, Boolean isBoolean) {
        Cell myCell = new Cell().add(new Paragraph(text)).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBoolean ? myCell.setBold() : myCell;
    }

    /**
     * Formating Cell header
     *
     * @param value
     * @return double rounded price to two place by ,
     * @author Jonh
     * @version 1.0
     */
    static double roundTo2DecimalPlace(double value) {

        return Math.round(value * 100.0) / 100.0;
    }
}
