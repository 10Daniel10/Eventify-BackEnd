package com.integrador.evently.productFeature.service;

import com.integrador.evently.productFeature.dto.ProductFeatureDTO;
import com.integrador.evently.productFeature.model.ProductFeature;
import com.integrador.evently.productFeature.repository.ProductFeatureRepository;
import com.integrador.evently.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductFeatureService {

    private final ProductFeatureRepository productFeatureRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductFeatureService(ProductFeatureRepository productFeatureRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.productFeatureRepository = productFeatureRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductFeatureDTO> getProductFeaturesByProductId(Long id) {
        List<ProductFeature> productFeatures = productFeatureRepository.findByProductId(id);
        return (!productFeatures.isEmpty()) ? productFeatures.stream()
                .map(productFeature -> modelMapper.map(productFeature, ProductFeatureDTO.class))
                .collect(Collectors.toList()) : null;
    }


    public ProductFeatureDTO saveProductFeature(String description, Long productId) {
        ProductFeature productFeature = new ProductFeature();
        productFeature.setProduct(productRepository.findById(productId).orElse(null));
        productFeature.setDescription(description);
        productFeature = productFeatureRepository.save(productFeature);
        return modelMapper.map(productFeature, ProductFeatureDTO.class);
    }

    public ProductFeatureDTO updateProductFeature(Long id, ProductFeatureDTO productFeatureDTO) {
        ProductFeature existingProductFeature = productFeatureRepository.findById(id).orElse(null);

        if (existingProductFeature != null) {
            existingProductFeature.setDescription(productFeatureDTO.getDescription());
            ProductFeature updatedProductFeature = productFeatureRepository.save(existingProductFeature);
            return modelMapper.map(updatedProductFeature, ProductFeatureDTO.class);
        }

        return null;
    }

    public void deleteProductFeature(Long id) {
        productFeatureRepository.deleteById(id);
    }
}
