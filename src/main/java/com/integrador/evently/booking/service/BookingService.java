package com.integrador.evently.booking.service;

import com.integrador.evently.booking.dto.BookingDTO;
import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.repository.BookingRepository;
import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.products.model.Product;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository , UserRepository userRepository , ModelMapper modelMapper) {
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

        User userOptional = userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        Optional<List<ProductDTO>> productsInConflict = checkAvailability(booking);

        if(productsInConflict.isPresent()){
            throw new Exception("The following products are already booked " + productsInConflict);
        }

        Booking bookingEntity = modelMapper.map(booking, Booking.class);
        bookingEntity.setId(null);
        return bookingRepository.save(bookingEntity);
    }

    public Optional<List<BookingDTO>> getBookingsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime){
        List<BookingDTO> bookings = new java.util.ArrayList<>(bookingRepository.findAll().stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class)).toList());

        List<BookingDTO> bookingsInRange = new ArrayList<>();

        bookings.forEach(booking ->  {
            if ((startDateTime.isAfter(booking.getStartDateTime()) || startDateTime.isEqual(booking.getStartDateTime()))
                    && (startDateTime.isBefore(booking.getEndDateTime()) || startDateTime.isEqual(booking.getEndDateTime()))){
                bookingsInRange.add(booking);
            }else if ((endDateTime.isAfter(booking.getStartDateTime()) || endDateTime.isEqual(booking.getStartDateTime()))
                    && (endDateTime.isBefore(booking.getEndDateTime()) || endDateTime.isEqual(booking.getEndDateTime()))){
                bookingsInRange.add(booking);
            };
        });

        if (!bookingsInRange.isEmpty()) {
            return Optional.of(bookingsInRange);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<ProductDTO>> checkAvailability(BookingDTO booking){
        Optional<List<BookingDTO>> bookings = getBookingsByDateRange(booking.getStartDateTime(), booking.getEndDateTime());

        if (bookings.isEmpty()){
            return Optional.empty();
        }

        List<ProductDTO> productsInConflict = new java.util.ArrayList<>();
        bookings.get().forEach(existingBooking -> {
            existingBooking.getProducts().forEach(product -> {
                    if(booking.getProducts().contains(product)){
                        productsInConflict.add(product);
                    }
            });
        });

        if (!productsInConflict.isEmpty()) {
            return Optional.of(productsInConflict);
        }else {
            return Optional.empty();
        }
    }

    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) throws Exception {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);

        if (existingBooking != null) {
            Optional<List<ProductDTO>> productsInConflict = checkAvailability(bookingDTO);

            if(productsInConflict.isPresent()){
                throw new Exception("The following products are already booked" + productsInConflict);
            }

            List<Product> updatedProducts = new java.util.ArrayList<>(bookingDTO.getProducts().stream()
                    .map(productDTO -> modelMapper.map(productDTO, Product.class)).toList());
            existingBooking.setProducts(updatedProducts);
            existingBooking.setStartDateTime(bookingDTO.getStartDateTime());
            existingBooking.setEndDateTime(bookingDTO.getEndDateTime());

            Booking updatedBooking = bookingRepository.save(existingBooking);
            return modelMapper.map(updatedBooking, BookingDTO.class);
        }

        return null;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
