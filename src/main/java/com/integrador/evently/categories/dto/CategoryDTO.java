package com.integrador.evently.categories.dto;

import com.integrador.evently.activities.dto.ActivityDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    //private List<ActivityDTO> services;
}