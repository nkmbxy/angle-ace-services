package com.project.angleace.service;

import com.google.cloud.storage.*;
import com.project.angleace.entity.Manufacturer;
import com.project.angleace.entity.Product;
import com.project.angleace.exception.Exception;
import com.project.angleace.model.request.CreateProductRequest;
import com.project.angleace.model.request.GetProductRequest;
import com.project.angleace.model.response.Response;
import com.project.angleace.repository.ManufacturerRepository;
import com.project.angleace.repository.ProductRepository;
import com.project.angleace.repository.specification.ProductSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.google.firebase.cloud.StorageClient;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    private static final String CREATE_SUCCESS = "create product success";


    public List<Product> getProducts(GetProductRequest request) {
        List<Specification<Product>> query = new ArrayList<>();

        if (StringUtils.hasText(request.getName())) {
            query.add(ProductSpecification.hasNameLike(request.getName()));
        }

        if (StringUtils.hasText(request.getType())) {
            query.add(ProductSpecification.hasTypeLike(request.getType()));
        }

        if (StringUtils.hasText(request.getManufacturer())) {
            query.add(ProductSpecification.hasManufacturerLike(request.getManufacturer()));
        }


        Pageable pageable = PageRequest.of(request.getPage(), request.getPerPage()); // ใช้สำหรับการเปลี่ยนหน้าตาราง

        logger.info("request: {}", request);

        Page<Product> result = productRepository.findAll(Specification.allOf(query), pageable);

        logger.info("result: {}", result.toList());

        return result.toList();
    }


    public String createProduct(CreateProductRequest request) throws IOException {

        Optional<Manufacturer> manufacturerRepo = manufacturerRepository.findByName(request.getManufacturer());
        Optional<Product> productRepo = productRepository.findByCode(request.getCode());

       if(productRepo.isPresent()) {
           logger.info("exist product in  database");
           throw new Exception();
       }

        if(request.getFile() == null){
            logger.info("request file empty");
            throw new Exception();
        }

        Product product = new Product();
        product.setCode(request.getCode())
                .setName(request.getName())
                .setType(request.getType())
                .setAmount(0)
                .setCost(request.getCost())
                .setDetail(request.getDetail())
                .setSellPrice(request.getSellPrice())
                .setSize(request.getSize());

           Bucket bucket = StorageClient.getInstance().bucket();

           // Get file information
           String fileName = request.getFile().getOriginalFilename() + "-" + UUID.randomUUID();
           String contentType = request.getFile().getContentType();

           // Get the file input stream
           InputStream inputStream = request.getFile().getInputStream();

           // Upload the file
           bucket.create(fileName, inputStream, contentType);

           String fileUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), fileName);

           product.setPathImage(fileUrl);

        if(manufacturerRepo.isEmpty()){
            Manufacturer manufacturer = new Manufacturer().setName(request.getManufacturer());
            manufacturerRepository.save(manufacturer);
            product.setManufacturer(manufacturer);
        }
        else{
            product.setManufacturer(manufacturerRepo.get());
        }

        logger.info("request: {}", request);
        productRepository.save(product);
        logger.info("result: {}", product);
        return "create product success";
    }


    public Product getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            logger.info("product is empty");
            throw new Exception();
        }
        return  product.get();
    }


}

