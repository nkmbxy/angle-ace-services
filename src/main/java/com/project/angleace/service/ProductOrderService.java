package com.project.angleace.service;

import com.project.angleace.entity.Product;
import com.project.angleace.exception.Exception;
import com.project.angleace.model.request.CreateProductOrderRequest;
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

    public String createProductOrder(List<CreateProductOrderRequest> request) {

        for (CreateProductOrderRequest productRequest : request) {
            List<Specification<Product>> query = new ArrayList<>();

            if (productRequest.getProduct_id() != null) {
                query.add(ProductSpecification.hasProductID(productRequest.getProduct_id()));
            }

            Optional<Product> product = productRepository.findOne(Specification.<Product>allOf(query));
            if (product.isPresent()) {
                Integer newAmountS = product.get().getAmountS() + productRequest.getAmountS();
                Integer newAmountM = product.get().getAmountM() + productRequest.getAmountM();
                Integer newAmountL = product.get().getAmountL() + productRequest.getAmountL();
                Integer newAmountXL = product.get().getAmountXL() + productRequest.getAmountXL();

                product.get()
                        .setAmountS(newAmountS)
                        .setAmountM(newAmountM)
                        .setAmountL(newAmountL)
                        .setAmountXL(newAmountXL)
                        .setCost(productRequest.getCost())
                        .setSellPrice(productRequest.getSellPrice());

                productRepository.save(product.get());
            }

            if (product.isEmpty()) {
                logger.info("cannot find product in database");
                throw new Exception();
            }
        }

        logger.info("request: {}", request);
        return "create order success";
    }
}
