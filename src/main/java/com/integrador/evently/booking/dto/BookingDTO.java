package com.integrador.evently.booking.dto;

import com.integrador.evently.products.dto.ProductDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    @NotNull
    private Long userId;
    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    private LocalDateTime endDateTime;
    @NotNull
    private List<ProductDTO> products;

}
