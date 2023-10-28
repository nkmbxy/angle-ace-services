package com.project.angleace.service;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


        Pageable pageable = PageRequest.of(request.getPage(), request.getPerPage()); // ใช้สำหรับการเปลี่ยนหน้าตาราง

        logger.info("request: {}", request);

        Page<Product> result = productRepository.findAll(Specification.allOf(query), pageable);

        logger.info("result: {}", result.toList());

        return result.toList();
    }


    public String createProduct(CreateProductRequest request) {
        Optional<Manufacturer> manufacturerRepo = manufacturerRepository.findByName(request.getManufacturer());
        Optional<Product> productRepo = productRepository.findByCode(request.getCode());

       if(productRepo.isPresent()) {
           throw new Exception();
       }

        Product product = new Product();
        product.setCode(request.getCode())
                .setName(request.getName())
                .setType(request.getType())
                .setAmount(0)
                .setCost(request.getCost())
                .setDetail(request.getDetail())
                .setPathImage(request.getPathImage())
                .setSellPrice(request.getSellPrice())
                .setSize(request.getSize());
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
}
