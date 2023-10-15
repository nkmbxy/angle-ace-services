package com.project.angleace.service;

import com.project.angleace.entity.Product;
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

    @Autowired
    private ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Product> getProducts(GetProductRequest request) {
        List<Specification<Product>> query = new ArrayList<>();

        if (StringUtils.hasText(request.getName())) {
            query.add(ProductSpecification.hasNameLike(request.getName()));
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getPerPage());

        logger.info("request: {}", request);

        Page<Product> result = productRepository.findAll(Specification.allOf(query), pageable);

        logger.info("count: {}", result.getNumber());

        logger.info("result: {}", result.toList());

        return result.toList();
    }


}
