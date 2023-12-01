package com.integrador.evently.pdfReport.controller;

import com.integrador.evently.pdfReport.service.PdfService;
import com.integrador.evently.pdfReport.service.ProfitPerMonthService;
import com.integrador.evently.providers.dto.ProviderDTO;
import com.integrador.evently.providers.service.ProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class PdfController {
    @Autowired
    private ProfitPerMonthService profitPerMonthService;

    @Autowired
    private ProviderService providerService;

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/{providerId}")
    public ResponseEntity<byte[]> generatePdf(
        @PathVariable Long providerId
    ) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ProviderDTO provider= providerService.getProviderById(providerId);
            String providerName = provider.getName();
            Map<YearMonth, Double> profitsPerMonth = profitPerMonthService.calculateProfitsPerMonth(providerId);
            outputStream = pdfService.createPdf(providerName, profitsPerMonth);
        }catch (Exception e){
            return ResponseEntity
                    .status(500)
                    .body("Error generating PDF".getBytes());
        }

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