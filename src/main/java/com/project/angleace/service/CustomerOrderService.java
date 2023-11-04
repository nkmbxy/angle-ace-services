package com.project.angleace.service;

import com.project.angleace.entity.CustomerOrder;
import com.project.angleace.model.request.CreateProductRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.slf4j.Logger;

import com.project.angleace.model.request.CreateProductOrderRequest;
import org.springframework.data.jpa.domain.Specification;
import com.project.angleace.entity.Product;
import com.project.angleace.repository.specification.ProductSpecification;
import com.project.angleace.repository.ProductRepository;
import com.project.angleace.repository.CustomerOrderRepository;

@Service
public class CustomerOrderService {
    private final Logger logger = LoggerFactory.getLogger(CustomerOrderService.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;


    public String createCustomerOrder(List<CreateProductOrderRequest> request) {

        for (CreateProductOrderRequest productRequest : request) {
            List<Specification<Product>> query = new ArrayList<>();

            if (productRequest.getProduct_id() != null) {
                query.add(ProductSpecification.hasProductID(productRequest.getProduct_id()));
            }

            Optional<Product> product = productRepository.findOne(Specification.<Product>allOf(query));
            if (product.isPresent()) {
                Integer newAmount = product.get().getAmount() - productRequest.getAmount();

                    // ลดจำนวนสินค้า
                    product.get().setAmount(newAmount);
                    productRepository.save(product.get());

                    // create_order
                Object CreateCustomerOrderRequest;
                CustomerOrder customerOrder = new CustomerOrder()
                        .setProduct_id(productRequest.getProduct_id())
                        .setCount(productRequest.getAmount())
                        .setProfit(product.get().getSellPrice() - product.get().getCost()); // คำนวณกำไร
                    // save_order
                    customerOrderRepository.save(customerOrder);

            }
        }

        logger.info("request: {}", request);
        return "Create order success";
    }

}
