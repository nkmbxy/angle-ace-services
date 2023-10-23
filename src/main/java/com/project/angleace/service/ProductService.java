package com.project.angleace.service;

import com.project.angleace.entity.Product;
import com.project.angleace.model.request.CreateProductRequest;
import com.project.angleace.model.request.GetProductRequest;
import com.project.angleace.repository.ProductRepository;
import com.project.angleace.repository.specification.ProductSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(GetProductRequest request) {
        List<Specification<Product>> query = new ArrayList<>();

        if (StringUtils.hasText(request.getName())) {
            query.add(ProductSpecification.hasNameLike(request.getName()));
        }

        if (StringUtils.hasText(request.getType())) {
            query.add(ProductSpecification.hasTypeLike(request.getType()));
        }

        if (StringUtils.hasText(request.getManufacturer())) {
            query.add(ProductSpecification.hasManufacturerLike(request.getType()));
        }


        Pageable pageable = PageRequest.of(request.getPage(), request.getPerPage()); // ใช้สำหรับการเปลี่ยนหน้าตาราง

        logger.info("request: {}", request);

        Page<Product> result = productRepository.findAll(Specification.allOf(query), pageable);

        logger.info("result: {}", result.toList());

        return result.toList();
    }


    public String createProduct(CreateProductRequest request) {
        Product product = new Product()
                .setName(request.getName())
                .setType(request.getType())
                .setAmount(0)
                .setCost(request.getCost())
                .setDetail(request.getDetail())
                .setManufacturer(request.getManufacturer())
                .setPathImage(request.getPathImage())
                .setSellPrice(request.getSellPrice())
                .setSize(request.getSize());

        logger.info("request: {}", request);
        productRepository.save(product);
        logger.info("result: {}", product);
        return "create product success";
    }
}
