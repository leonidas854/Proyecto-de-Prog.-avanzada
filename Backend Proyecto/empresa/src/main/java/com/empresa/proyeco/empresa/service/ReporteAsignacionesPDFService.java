package com.empresa.proyeco.empresa.service;

import com.empresa.proyeco.empresa.model.Asignacion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReporteAsignacionesPDFService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font DATA_FONT = new Font(Font.FontFamily.HELVETICA, 12);

    public ByteArrayInputStream generarReporteAsignaciones(List<Asignacion> asignaciones) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

         
            Paragraph titulo = new Paragraph("Reporte de Asignaciones", TITLE_FONT);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

        
            PdfPTable table = new PdfPTable(4); 
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 4, 2, 2});

         
            agregarCeldaEncabezado(table, "Sucursal");
            agregarCeldaEncabezado(table, "Cliente");
            agregarCeldaEncabezado(table, "Cantidad");
            agregarCeldaEncabezado(table, "Costo");

         
            for (Asignacion asignacion : asignaciones) {
                agregarCeldaDato(table, asignacion.getSucursal().getNombre());
                agregarCeldaDato(table, asignacion.getCliente().getNombre());
                agregarCeldaDato(table, String.valueOf(asignacion.getCantidad()));
                agregarCeldaDato(table, String.valueOf(asignacion.getCosto()));
            }

            document.add(table);
            document.close();
        } catch (DocumentException ex) {
            throw new RuntimeException("Error al generar el reporte en PDF", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void agregarCeldaEncabezado(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, HEADER_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void agregarCeldaDato(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, DATA_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }
}
