package com.integrador.evently.pdfReport.controller;

import com.integrador.evently.pdfReport.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping()
    public ResponseEntity<byte[]> generatePdf() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream = pdfService.createPdf();
        }catch (Exception e){
            return ResponseEntity
                    .status(500)
                    .body("Error generating PDF".getBytes());
        }

        // Prepare the response with the PDF content
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=example.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(outputStream.size())
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }
}