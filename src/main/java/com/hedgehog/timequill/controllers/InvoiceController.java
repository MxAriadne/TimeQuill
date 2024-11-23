package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.entities.AssignmentEntity;
import com.hedgehog.timequill.entities.ProjectEntity;
import com.hedgehog.timequill.entities.TimeTableEntity;
import com.hedgehog.timequill.repo.AssignmentRepository;
import com.hedgehog.timequill.repo.ProjectRepository;
import com.hedgehog.timequill.repo.TimeTableRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Set;

import static java.lang.Integer.parseInt;

@RestController
public class InvoiceController {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private TimeTableRepository timeTableRepo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    // post invoices, generates a PDF of invoice
    @PostMapping("/generate")
    public void generateInvoice(@RequestParam(value = "projectId") String projectId, HttpServletResponse response) {

        ProjectEntity project = projectRepo.findById(parseInt(projectId)).get();
        Set<AssignmentEntity> assignmentSet = assignmentRepo.findByProject(project);

        for (AssignmentEntity task : assignmentSet) {
            System.out.print(task.getDescription());
        }

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");

            PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(pdfWriter));

            document.add(new Paragraph("Invoice").setBold().setFontSize(20));
            document.add(new Paragraph("Client Name: " + project.getClientName()));
            document.add(new Paragraph("Project Details: " + project.getDescription()));
            document.add(new Paragraph("\n"));

            float[] columnWidths = { 200, 100, 100, 100 };
            Table table = new Table(columnWidths);
            table.addHeaderCell(new Cell().add("Task"));
            table.addHeaderCell(new Cell().add("Hours"));
            table.addHeaderCell(new Cell().add("Rate"));
            table.addHeaderCell(new Cell().add("Amount"));

            double totalAmount = 0;
            for (AssignmentEntity task : assignmentSet) {
                Set<TimeTableEntity> timeTable = timeTableRepo.findAllByAssignment(task);
                if (timeTable.isEmpty()) {
                    table.addCell(new Cell().add(task.getDescription()));
                    table.addCell(new Cell().add("0"));
                    table.addCell(new Cell().add("$" + String.valueOf(task.getRate() != null ? task.getRate() : 0.00)));
                    table.addCell(new Cell().add("$0"));
                } else {
                    for (TimeTableEntity entry : timeTable) {
                        table.addCell(new Cell().add(task.getDescription()));
                        table.addCell(new Cell().add(String.valueOf(entry.getWorkedHours())));
                        table.addCell(new Cell().add("$" + task.getRate()));
                        double amount = entry.getWorkedHours() * task.getRate();
                        table.addCell(new Cell().add("$" + amount));
                        totalAmount += amount;
                    }
                }

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
