package com.integrador.evently.pdfReport.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.repository.BookingRepository;
import com.integrador.evently.products.model.Product;
import com.integrador.evently.products.repository.ProductRepository;
import com.integrador.evently.providers.repository.ProviderRepository;

@Service
public class ProfitPerMonthService {
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Map<YearMonth, Double> calculateProfitsPerMonth(Long providerId) {
        Map<YearMonth, Double> profitsPerMonth = new HashMap<>();

        List<Booking> bookings = bookingRepository.findAll();
        
        double totalBookingProfits = 0.0;

        for (Booking booking : bookings) {
            for(Product product: booking.getProducts()){
                double currentBookingProfit = 0.0;
                if(product.getProvider().getId() == providerId){

                double productPrice = product.getPrice();
                currentBookingProfit += productPrice;
                totalBookingProfits += currentBookingProfit;
            }
            
            LocalDateTime bookingDate = booking.getStartDateTime();
            YearMonth yearMonth = YearMonth.from(bookingDate);

            profitsPerMonth.put(yearMonth, profitsPerMonth.getOrDefault(yearMonth, 0.0) + totalBookingProfits);
        }}

    return profitsPerMonth;
}
}
