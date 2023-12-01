package com.integrador.evently.pdfReport.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.time.YearMonth;
import java.util.Map;


@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayOutputStream createPdf(String providerName, Map<YearMonth, Double> profitsPerMonth) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

                try {
            Context context = new Context();

            context.setVariable("providerName", providerName);
            context.setVariable("profitsPerMonth", profitsPerMonth);

            String htmlContent = templateEngine.process("template", context);

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (com.lowagie.text.DocumentException e) {
            throw new Exception("Error generating PDF");
        }
        return outputStream;
    }
}
