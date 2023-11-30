package com.integrador.evently.products.controller;

import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.products.dto.ProductPostDTO;
import com.integrador.evently.products.interfaces.IProductController;
import com.integrador.evently.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController implements IProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return (productDTO != null)
                ? new ResponseEntity<>(productDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long id) {
        List<ProductDTO> productsDTO = productService.getProductsByCategoryId(id);
        return (productsDTO != null)
                ? new ResponseEntity<>(productsDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/provider/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByProviderId(@PathVariable Long id) {
        List<ProductDTO> productsDTO = productService.getProductsByProviderId(id);
        return (productsDTO != null)
                ? new ResponseEntity<>(productsDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductPostDTO productPostDTO) {
        ProductDTO savedProduct = productService.saveProduct(productPostDTO);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody  ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
