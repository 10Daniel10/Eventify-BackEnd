package com.integrador.evently.booking.service;

import com.integrador.evently.booking.dto.BookingDTO;
import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.repository.BookingRepository;
import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.users.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository , UserRepository userRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookingDTO> getUserBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

    public Booking createBooking(BookingDTO booking) throws Exception {
        if(booking.getEndDateTime().isBefore(booking.getStartDateTime())){
            throw new Exception("Booking startDate is after endDate");
        }

        userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        Booking bookingEntity = modelMapper.map(booking, Booking.class);
        return bookingRepository.save(bookingEntity);
    }

    private Boolean isLocalDateBetweenLocalDates(LocalDate date, LocalDate start, LocalDate end) {
        return (date.isAfter(start) || date.isEqual(start)) && (date.isBefore(end) || date.isEqual(end));
    }

    public List<BookingDTO> getBookingsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime){
        List<BookingDTO> bookings = new java.util.ArrayList<>(bookingRepository.findAll().stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class)).toList());

        return bookings.stream()
                .filter(booking -> {
                    LocalDate bookingDate = booking.getStartDateTime().toLocalDate();
                    LocalDate start = startDateTime.toLocalDate();
                    LocalDate end = endDateTime.toLocalDate();
                    return isLocalDateBetweenLocalDates(bookingDate, start, end);
                })
                .collect(Collectors.toList());
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<BookingDTO> findBookingsByDateRangeAndProvider(LocalDateTime startDate, LocalDateTime endDate,
        Long providerId
    ) {
        List<BookingDTO> bookings = getBookingsByDateRange(startDate, endDate);

        final List<BookingDTO> bookingsByMonthAndProvider = new ArrayList<>();
        if (!bookings.isEmpty()) {
            for (BookingDTO booking : bookings) {

                for(ProductDTO product: booking.getProducts()){
                    if (product.getProvider().getId().equals(providerId)){
                        bookingsByMonthAndProvider.add(booking);
                    }

                }
            }
        }
        return bookingsByMonthAndProvider;
    }


}