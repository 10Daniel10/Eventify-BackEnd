package com.integrador.evently.products.interfaces;

import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.products.dto.ProductPostDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductController {

    List<ProductDTO> getAllProducts();
    ResponseEntity<ProductDTO> getProductById(Long id);
    ResponseEntity<ProductDTO> saveProduct(ProductPostDTO productPostDTO);
    ResponseEntity<ProductDTO> updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);

}
