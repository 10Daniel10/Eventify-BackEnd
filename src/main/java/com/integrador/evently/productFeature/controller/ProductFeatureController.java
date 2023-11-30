package com.integrador.evently.productFeature.controller;

import com.integrador.evently.productFeature.dto.ProductFeatureDTO;
import com.integrador.evently.productFeature.service.ProductFeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productFeature")
public class ProductFeatureController {

    private final ProductFeatureService productFeatureService;

    public ProductFeatureController(ProductFeatureService productFeatureService) {
        this.productFeatureService = productFeatureService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductFeatureDTO>> getProductFeaturesByProductId(@PathVariable Long productId) {
        List<ProductFeatureDTO> productFeaturesDTO = productFeatureService.getProductFeaturesByProductId(productId);
        return (productFeaturesDTO != null)
                ? new ResponseEntity<>(productFeaturesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductFeatureDTO> updateProductFeature(@PathVariable Long id, @RequestBody ProductFeatureDTO productFeatureDTO) {
        ProductFeatureDTO updatedProductFeature = productFeatureService.updateProductFeature(id, productFeatureDTO);

        if (updatedProductFeature != null) {
            return ResponseEntity.ok(updatedProductFeature);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productFeatureService.deleteProductFeature(id);
    }
}
