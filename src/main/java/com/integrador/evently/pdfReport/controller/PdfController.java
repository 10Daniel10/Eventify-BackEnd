package com.integrador.evently.pdfReport.controller;

import com.integrador.evently.pdfReport.service.PdfService;
import com.integrador.evently.pdfReport.service.ProfitPerMonthService;
import com.integrador.evently.providers.dto.ProviderDTO;
import com.integrador.evently.providers.service.ProviderService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class PdfController {
    private final ProfitPerMonthService profitPerMonthService;

    private final ProviderService providerService;
    private final PdfService pdfService;

    public PdfController(PdfService pdfService, ProfitPerMonthService profitPerMonthService, ProviderService providerService) {
        this.pdfService = pdfService;
        this.profitPerMonthService = profitPerMonthService;
        this.providerService = providerService;
    }

    @GetMapping("/{providerId}")
    public ResponseEntity<byte[]> generatePdf(
        @PathVariable Long providerId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
        ProviderDTO provider = providerService.getProviderById(providerId);
        String providerName = provider.getName();
        Map<YearMonth, Double> profitsPerMonth = profitPerMonthService.calculateProfitsPerMonth(providerId, startDate, endDate);
        Map<YearMonth, Integer> bookingsPerMonth = profitPerMonthService.calculateBookingsPerMonth(providerId, startDate, endDate);
        outputStream = pdfService.createPdf(providerName, profitsPerMonth, bookingsPerMonth);
    } catch (Exception e) {
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
