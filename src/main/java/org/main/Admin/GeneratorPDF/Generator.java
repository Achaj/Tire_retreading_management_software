package org.main.Admin.GeneratorPDF;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class Generator {
    /**
     * Chosing the file name and path to saved
     * @author Jonh
     * @version 1.0
     * @return string absolute path to file
     */
    static String getPathFileSaved(String name) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(name+" "+ LocalDate.now());

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
        File file = fileChooser.showSaveDialog(new Stage());
        return file.getAbsolutePath();
    }
    /**
     * Open file from path
     * @param path
     * @author Jonh
     * @version 1.0
     */
    static void openPDFAfterSave(String path){
        try {

            if ((new File(path)).exists()) {

                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler "+path);
                p.waitFor();

            } else {

                System.out.println("File is not exists");

            }
            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
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
     static Cell getHeaderTextCellVaue(String text) {
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
     static Cell getCell10Left(String text, Boolean isBoolean) {
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
