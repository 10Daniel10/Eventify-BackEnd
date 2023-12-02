package com.integrador.evently.pdfReport.controller;

import com.integrador.evently.booking.dto.BookingDTO;
import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.service.BookingService;
import com.integrador.evently.pdfReport.service.CsvService;
import com.integrador.evently.pdfReport.service.PdfService;
import com.integrador.evently.pdfReport.service.ProfitPerMonthService;
import com.integrador.evently.products.model.Product;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class PdfController {
    @Autowired
    private ProfitPerMonthService profitPerMonthService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CsvService csvService;

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
            Map<YearMonth, Integer> bookingsPerMonth = profitPerMonthService.calculateBookingsPerMonth(providerId);
            outputStream = pdfService.createPdf(providerName, profitsPerMonth, bookingsPerMonth);
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



    @GetMapping("/csv/{providerId}/{month}")
    public String generateCsv(@PathVariable Long providerId, @PathVariable int month) {
        LocalDateTime startOfMonth = LocalDateTime.of(LocalDateTime.now().getYear(), Month.of(month), 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusNanos(1);
    

        System.out.println("Start of month: " + startOfMonth);
        System.out.println("End of month: " + endOfMonth);

        Optional<List<BookingDTO>> allBookingsOptional = bookingService.getBookingsByDateRange(startOfMonth, endOfMonth);
    
        if (allBookingsOptional.isPresent()) {
            System.out.println("Number of optional bookings: " + allBookingsOptional.get().size());
        } else {
            System.out.println("No bookings present in the optional.");
        }

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"User Id", "Products", "Booking Start Time", "Booking End Time"});
    
        if (allBookingsOptional.isPresent() && !allBookingsOptional.get().isEmpty()) {
            List<BookingDTO> allBookings = allBookingsOptional.get();
    
            // Log the size of the allBookings list
            System.out.println("Number of bookings: " + allBookings.size());
    
            for (BookingDTO booking : allBookings) {
                boolean isBookingInMonth = booking.getStartDateTime().isAfter(startOfMonth)
                        && booking.getStartDateTime().isBefore(endOfMonth);
    
                // Log details of each booking
                System.out.println("Booking details: " + booking);
    
                if (isBookingInMonth && booking.getProducts().stream().anyMatch(product -> product.getProviderId().equals(providerId))) {
                    String productNames = booking.getProducts().stream().map(product -> product.getName()).reduce((s1, s2) -> s1 + ", " + s2).orElse("");
                    data.add(new String[]{
                            String.valueOf(booking.getUserId()),
                            productNames,
                            booking.getStartDateTime().toString(),
                            booking.getEndDateTime().toString()
                    });
                }
            }
        }

        System.out.println(data.size());
    
        try {
            csvService.writeDataToCsv("output.csv", data);
            return "CSV file generated successfully!";
        } catch (IOException e) {
            return "Error generating CSV file: " + e.getMessage();
        }
    }
    
}