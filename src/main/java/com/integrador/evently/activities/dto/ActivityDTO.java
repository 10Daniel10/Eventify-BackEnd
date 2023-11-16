package com.integrador.evently.activities.dto;

import com.integrador.evently.categories.dto.CategoryDTO;
import com.integrador.evently.photo.dto.PhotoDTO;
import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.users.dto.UserDto;
import com.integrador.evently.users.model.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActivityDTO {
    private Long id;
    private String name;
    private String information;
    private Date creationDate;
    private Date updateDate;
    private String address;
    private double price;
    private CategoryDTO category;
    private List<PhotoDTO> photos;
    private List<ProductDTO> products;
    private UserDto user;
}
