package com.integrador.evently.pdfReport.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.util.Date;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayOutputStream createPdf() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

                try {
            // Create a Thymeleaf context
            Context context = new Context();

            context.setVariable("currentDate", new Date());
            context.setVariable("yourText", "test");

            // Process the Thymeleaf template
            String htmlContent = templateEngine.process("template", context);

            // Generate PDF from HTML
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (com.lowagie.text.DocumentException e) {
            throw new Exception("Error generating PDF");
        }
        return outputStream;
    }
}
