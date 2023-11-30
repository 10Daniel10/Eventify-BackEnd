package com.integrador.evently.productFeature.model;

import com.integrador.evently.products.model.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
