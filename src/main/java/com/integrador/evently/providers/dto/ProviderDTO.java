package com.integrador.evently.providers.dto;

import com.integrador.evently.categories.dto.CategoryDTO;
import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.users.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class ProviderDTO {
    private Long id;
    private String name;
    private String information;
    private String address;
    private List<CategoryDTO> category;
    private List<ProductDTO> products;
    private String imageUrl;
    private UserDto user;
}
