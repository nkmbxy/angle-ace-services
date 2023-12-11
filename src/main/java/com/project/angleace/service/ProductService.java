package com.project.angleace.service;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.project.angleace.entity.Manufacturer;
import com.project.angleace.entity.Product;
import com.project.angleace.exception.Exception;
import com.project.angleace.model.request.CreateProductRequest;
import com.project.angleace.model.request.EditProductRequest;
import com.project.angleace.model.request.GetProductRequest;
import com.project.angleace.repository.ManufacturerRepository;
import com.project.angleace.repository.ProductRepository;
import com.project.angleace.repository.specification.ProductSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

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

        if (request.getStartPrice() != null && request.getEndPrice() != null) {
            query.add(ProductSpecification.hasBetweenStartPriceAndEndPrice(request.getStartPrice(), request.getEndPrice()));
        }

        if (request.getStartPrice() != null && request.getEndPrice() == null) {
            query.add(ProductSpecification.hasStartPrice(request.getStartPrice()));
        }

        if (request.getStartPrice() == null && request.getEndPrice() != null) {
            query.add(ProductSpecification.hasEndPrice(request.getEndPrice()));
        }

        Sort sort;
        sort = Sort.by(Sort.Order.desc("createdAt"));
        query.add(ProductSpecification.hasOrderBy(sort));

        logger.info("request: {}", request);

        List<Product> result = productRepository.findAll(Specification.allOf(query));

        logger.info("result: {}", result);

        return result;
    }


    public String createProduct(CreateProductRequest request) throws IOException {

        Optional<Manufacturer> manufacturerRepo = manufacturerRepository.findByName(request.getManufacturer());
        Optional<Product> productRepo = productRepository.findByCode(request.getCode());

        if (productRepo.isPresent()) {
            logger.info("exist product in database");
            throw new Exception();
        }

        if (request.getFile() == null) {
            logger.info("request file empty");
            throw new Exception();
        }

        Product product = new Product();
        product.setCode(request.getCode())
                .setName(request.getName())
                .setType(request.getType())
                .setAmountS(0)
                .setAmountM(0)
                .setAmountL(0)
                .setAmountXL(0)
                .setDetail(request.getDetail());

        Bucket bucket = StorageClient.getInstance().bucket();
        String fileName = request.getFile().getOriginalFilename() + "-" + UUID.randomUUID();
        String contentType = request.getFile().getContentType();
        InputStream inputStream = request.getFile().getInputStream();
        bucket.create(fileName, inputStream, contentType);
        String fileUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), fileName);
        product.setPathImage(fileUrl);

        if (manufacturerRepo.isEmpty()) {
            Manufacturer manufacturer = new Manufacturer().setName(request.getManufacturer());
            manufacturerRepository.save(manufacturer);
            product.setManufacturer(manufacturer);
        } else {
            product.setManufacturer(manufacturerRepo.get());
        }

        logger.info("request: {}", request);
        productRepository.save(product);
        logger.info("result: {}", product);
        return "create product success";
    }


    public Product getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            logger.info("product is empty");
            throw new Exception();
        }
        return product.get();
    }


    public String editProductById(Integer id, EditProductRequest request) throws IOException {
        logger.info("{}", request);
        Optional<Product> productRepo = productRepository.findById(id);
        if (productRepo.isPresent()) {
            productRepo.get()
                    .setSellPrice(request.getSellPrice())
                    .setCost(request.getCost())
                    .setDetail(request.getDetail());
            if (request.getFile() != null) {
                Bucket bucket = StorageClient.getInstance().bucket();
                String fileName = request.getFile().getOriginalFilename() + "-" + UUID.randomUUID();
                String contentType = request.getFile().getContentType();
                InputStream inputStream = request.getFile().getInputStream();
                bucket.create(fileName, inputStream, contentType);
                String fileUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), fileName);
                productRepo.get().setPathImage(fileUrl);
            }
            productRepository.save(productRepo.get());
        }
        if (productRepo.isEmpty()) {
            logger.info("cannot find product in database");
            throw new Exception();
        }
        logger.info("request: {}", request);
        return "edit product success";
    }


}

