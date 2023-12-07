package com.integrador.evently.products.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductPostDTO {
    private Long id;
    private String name;
    private Double price;
    private String shortDescription;
    private String description;
    private String location;
    private Long categoryId;
    private Long providerId;
    private List<String> features;
    private List<String> photos;
}
