package com.integrador.evently.products.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String shortDescription;
    private String description;
    private String location;
    private Long categoryId;
    private Long providerId;
    private List<LocalDate> bookedDates;
}
