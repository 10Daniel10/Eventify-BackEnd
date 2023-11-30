package com.integrador.evently.products.interfaces;



import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.products.dto.ProductPostDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    List<ProductDTO> getProductsByCategoryId(Long id);
    List<ProductDTO> getProductsByProviderId(Long id);
    ProductDTO saveProduct(ProductPostDTO productPostDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
