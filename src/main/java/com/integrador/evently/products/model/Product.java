package com.integrador.evently.products.model;

import com.integrador.evently.categories.model.Category;
import com.integrador.evently.providers.model.Provider;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String shortDescription;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ElementCollection
    @CollectionTable(name = "product_image_url", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "url")
    private List<String> imageUrls;

}