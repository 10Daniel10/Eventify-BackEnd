package com.integrador.evently.products.interfaces;

import com.integrador.evently.products.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductController {

    List<ProductDTO> getAllProducts();
    ResponseEntity<ProductDTO> getProductById(Long id);
    ResponseEntity<ProductDTO> saveProduct(ProductDTO productDTO);
    ResponseEntity<ProductDTO> updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);

}
