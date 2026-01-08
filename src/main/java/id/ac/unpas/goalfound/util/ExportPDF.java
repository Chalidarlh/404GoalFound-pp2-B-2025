/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.JTable;
import java.io.FileOutputStream;
/**
 *
 * @author Asus
 */
public class ExportPDF {

    public static void exportTable(JTable table, String fileName) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(fileName));
        doc.open();

        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

        for (int i = 0; i < table.getColumnCount(); i++) {
            pdfTable.addCell(table.getColumnName(i));
        }

        for (int r = 0; r < table.getRowCount(); r++) {
            for (int c = 0; c < table.getColumnCount(); c++) {
                pdfTable.addCell(table.getValueAt(r, c).toString());
            }
        }

        doc.add(pdfTable);
        doc.close();
    }

    public static void exportJadwalPertandingan(JTable table, String fileName) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(fileName));
        doc.open();

        Paragraph title = new Paragraph("JADWAL PERTANDINGAN", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);
        doc.add(new Paragraph(" "));

        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

        for (int i = 0; i < table.getColumnCount(); i++) {
            pdfTable.addCell(table.getColumnName(i));
        }

        for (int r = 0; r < table.getRowCount(); r++) {
            for (int c = 0; c < table.getColumnCount(); c++) {
                pdfTable.addCell(table.getValueAt(r, c).toString());
            }
        }

        doc.add(pdfTable);
        doc.close();
    }
}
