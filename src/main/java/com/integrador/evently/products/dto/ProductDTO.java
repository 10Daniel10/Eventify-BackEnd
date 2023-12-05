package com.integrador.evently.products.dto;

import com.integrador.evently.categories.dto.CategoryDTO;
import com.integrador.evently.providers.dto.SimpleProviderDto;
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
    private CategoryDTO category;
    private SimpleProviderDto provider;
    private List<LocalDate> bookedDates;
    private List<String> imageUrls;
}
