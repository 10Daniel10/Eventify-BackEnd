package com.integrador.evently.pdfReport.service;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.integrador.evently.booking.dto.BookingDTO;
import com.integrador.evently.booking.service.BookingService;
import com.integrador.evently.products.dto.ProductDTO;

@Service
public class ProfitPerMonthService {

    private final BookingService bookingService;

    public ProfitPerMonthService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Map<YearMonth, Double> calculateProfitsPerMonth(Long providerId, LocalDateTime startDate, LocalDateTime endDate) {
    Map<YearMonth, Double> profitsPerMonth = new HashMap<>();

    List<BookingDTO> bookings = bookingService.findBookingsByDateRangeAndProvider(startDate, endDate, providerId);

    for (BookingDTO booking : bookings) {
        double totalBookingProfits = 0.0;
        for (ProductDTO product : booking.getProducts()) {
            if (product.getProvider().getId().equals(providerId)) {
                double productPrice = product.getPrice();
                totalBookingProfits += productPrice;
            }
        }
        System.out.println(totalBookingProfits);
        LocalDateTime bookingDate = booking.getStartDateTime();
        YearMonth yearMonth = YearMonth.from(bookingDate);

        profitsPerMonth.put(yearMonth, profitsPerMonth.getOrDefault(yearMonth, 0.0) + totalBookingProfits);
    }
    System.out.println(profitsPerMonth);
    return profitsPerMonth;
}

public Map<YearMonth, Integer> calculateBookingsPerMonth(Long providerId, LocalDateTime startDate, LocalDateTime endDate) {
    Map<YearMonth, Integer> bookingsPerMonth = new HashMap<>();

    List<BookingDTO> bookings = bookingService.findBookingsByDateRangeAndProvider(startDate, endDate, providerId);

    for (BookingDTO booking : bookings) {
        Integer totalBookings = 0;
        for (ProductDTO product : booking.getProducts()) {
            if (product.getProvider().getId().equals(providerId)) {
                totalBookings++;
            }
        }
        System.out.println(totalBookings);
        LocalDateTime bookingDate = booking.getStartDateTime();
        YearMonth yearMonth = YearMonth.from(bookingDate);

        bookingsPerMonth.put(yearMonth, bookingsPerMonth.getOrDefault(yearMonth, 0) + totalBookings);
    }
    System.out.println(bookingsPerMonth);
    return bookingsPerMonth;
    }
}