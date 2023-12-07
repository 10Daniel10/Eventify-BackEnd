package com.integrador.evently.products.service;

import com.integrador.evently.booking.model.Booking;
import com.integrador.evently.booking.repository.BookingRepository;
import com.integrador.evently.categories.repository.CategoryRepository;
import com.integrador.evently.productFeature.model.ProductFeature;
import com.integrador.evently.productFeature.repository.ProductFeatureRepository;
import com.integrador.evently.products.dto.ProductDTO;
import com.integrador.evently.products.dto.ProductPostDTO;
import com.integrador.evently.products.interfaces.IProductService;
import com.integrador.evently.products.model.Product;
import com.integrador.evently.products.repository.ProductRepository;
import com.integrador.evently.providers.repository.ProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;
    private final ProductFeatureRepository productFeatureRepository;
    private final BookingRepository bookingRepository;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper,
                          CategoryRepository categoryRepository,
                          ProviderRepository providerRepository,
                          ProductFeatureRepository productFeatureRepository,
                          BookingRepository bookingRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.providerRepository = providerRepository;
        this.productFeatureRepository = productFeatureRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        ProductDTO productDTOResponse;
        if (product != null) {
            productDTOResponse = modelMapper.map(product, ProductDTO.class);
        } else {
            return null;
        }
        List<Booking> bookings= bookingRepository.findAll();
        List<LocalDate> bookedDates = new ArrayList<>();

        bookings.forEach(booking -> {
            boolean flag = false;
            for (Product pr: booking.getProducts()) {
                if (!flag && id.equals(pr.getId())){
                    bookedDates.add(booking.getStartDateTime().toLocalDate());
                    flag = true;
                }
            }
        });
        productDTOResponse.setBookedDates(bookedDates);

        return productDTOResponse;
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long id) {
        List<Product> products = productRepository.findByCategoryId(id);
        return (!products.isEmpty()) ? products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList()) : null;
    }

    @Override
    public List<ProductDTO> getProductsByProviderId(Long id) {
        List<Product> products = productRepository.findByProviderId(id);
        return (!products.isEmpty()) ? products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList()) : null;
    }

    @Override
    public ProductDTO saveProduct(ProductPostDTO productPostDTO) {
        Product product = new Product();
        product.setName(productPostDTO.getName());
        product.setPrice(productPostDTO.getPrice());
        product.setShortDescription(productPostDTO.getShortDescription());
        product.setDescription(productPostDTO.getDescription());
        product.setLocation(productPostDTO.getLocation());
        product.setCategory(categoryRepository.findById(productPostDTO.getCategoryId()).orElse(null));
        product.setProvider(providerRepository.findById(productPostDTO.getProviderId()).orElse(null));
        product.setImageUrls(productPostDTO.getPhotos());
        product = productRepository.save(product);

        Product finalProduct = product;
        productPostDTO.getFeatures().forEach(feature -> {
            ProductFeature productFeature = new ProductFeature();
            productFeature.setProduct(finalProduct);
            productFeature.setDescription(feature);
            productFeatureRepository.save(productFeature);
        });

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(productDTO.getName());
            Product updatedProduct = productRepository.save(existingProduct);
            return modelMapper.map(updatedProduct, ProductDTO.class);
        }

        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
