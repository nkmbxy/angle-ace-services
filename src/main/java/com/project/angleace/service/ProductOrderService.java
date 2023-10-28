package com.project.angleace.service;

import com.project.angleace.entity.Product;
import com.project.angleace.entity.ProductOrder;
import com.project.angleace.model.request.CreateProductOrderRequest;
import com.project.angleace.repository.ProductOrderRepository;
import com.project.angleace.repository.ProductRepository;
import com.project.angleace.repository.specification.ProductSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {
    private final Logger logger = LoggerFactory.getLogger(ProductOrderService.class);
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public String createProductOrder(List<CreateProductOrderRequest> request) {

        for (CreateProductOrderRequest productRequest : request) {
            List<Specification<Product>> query = new ArrayList<>();

            if (productRequest.getProduct_id() != null) {
                query.add(ProductSpecification.hasProductID(productRequest.getProduct_id()));
            }

            Optional<Product> product = productRepository.findOne(Specification.<Product>allOf(query));
            if (product.isPresent()) {
                Integer newAmount = product.get().getAmount() + productRequest.getAmount();
                product.get().setAmount(newAmount);
                productRepository.save(product.get());

                ProductOrder productOrder = new ProductOrder()
                        .setProduct_id(productRequest.getProduct_id())
                        .setName(productRequest.getName())
                        .setAmount(productRequest.getAmount());
                productOrderRepository.save(productOrder);
            }
        }

        logger.info("request: {}", request);
        return "create order success";
    }
}
