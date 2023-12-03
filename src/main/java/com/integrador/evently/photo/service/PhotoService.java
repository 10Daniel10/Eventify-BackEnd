package com.integrador.evently.photo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.integrador.evently.categories.model.Category;
import com.integrador.evently.categories.repository.CategoryRepository;
import com.integrador.evently.products.repository.ProductRepository;
import com.integrador.evently.providers.model.Provider;
import com.integrador.evently.providers.repository.ProviderRepository;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PhotoService {
    private AmazonS3Client amazonS3Client;

    private final AmazonProperties properties;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;
    private final ProductRepository productRepository;

    public PhotoService(AmazonProperties properties, CategoryRepository categoryRepository, UserRepository userRepository, ProviderRepository providerRepository, ProductRepository productRepository) {
        this.properties = properties;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
        this.productRepository = productRepository;
        AWSCredentials credentials = new BasicAWSCredentials(this.properties.getAccessKey(), this.properties.getSecretKey());
        this.amazonS3Client = new AmazonS3Client(credentials);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        amazonS3Client.putObject(new PutObjectRequest(this.properties.getBucketName(), fileName, file));
    }

public String uploadFile(MultipartFile multipartFile) {
    String fileUrl = "";
    try {
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        fileUrl = this.properties.getEndpointUrl() + "/" + this.properties.getBucketName() + "/" + fileName;
        uploadFileTos3bucket(fileName, file);
        file.delete();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return fileUrl;
    }

    public String uploadCategoryFile(MultipartFile multipartFile, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        String fileUrl = this.uploadFile(multipartFile);
        category.setPhotoUrl(fileUrl);
        categoryRepository.save(category);
        return fileUrl;
    }

    public String uploadUserFile(MultipartFile multipartFile, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileUrl = this.uploadFile(multipartFile);
        user.setPhotoUrl(fileUrl);
        userRepository.save(user);
        return fileUrl;
    }

    public String uploadProviderFile(MultipartFile multipartFile, Long providerId) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        String fileUrl = this.uploadFile(multipartFile);
        provider.setImageUrl(fileUrl);
        providerRepository.save(provider);
        return fileUrl;
    }



    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3Client.deleteObject(new DeleteObjectRequest(this.properties.getBucketName() + "/", fileName));
        return "Successfully deleted";
    }

    public String uploadProductFile(MultipartFile file, Long productId) {
        var product = this.productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String fileUrl = this.uploadFile(file);
        product.getImageUrls().add(fileUrl);
        this.productRepository.save(product);
        return fileUrl;
    }
}
