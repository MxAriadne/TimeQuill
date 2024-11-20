package com.example.InvoiceGenerator;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class InvoiceController {

    @PostMapping("/generateInvoice")
    public void generateInvoice(@RequestBody InvoiceRequest invoiceRequest, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");

            PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(pdfWriter));

            document.add(new Paragraph("Invoice").setBold().setFontSize(20));
            document.add(new Paragraph("Client Name: " + invoiceRequest.getClientName()));
            document.add(new Paragraph("Project Details: " + invoiceRequest.getProjectDetails()));
            document.add(new Paragraph("\n"));

            float[] columnWidths = {200, 100, 100, 100};
            Table table = new Table(columnWidths);
            table.addHeaderCell(new Cell().add("Task"));
            table.addHeaderCell(new Cell().add("Hours"));
            table.addHeaderCell(new Cell().add("Rate"));
            table.addHeaderCell(new Cell().add("Amount"));

            double totalAmount = 0;
            for (InvoiceTask task : invoiceRequest.getTasks()) {
                table.addCell(new Cell().add(task.getTaskName()));
                table.addCell(new Cell().add(String.valueOf(task.getHours())));
                table.addCell(new Cell().add("$" + task.getRate()));
                double amount = task.getHours() * task.getRate();
                table.addCell(new Cell().add("$" + amount));
                totalAmount += amount;
            }

            table.addCell(new Cell(1, 3).add("Total").setBold());
            table.addCell(new Cell().add("$" + totalAmount));

            document.add(table);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
