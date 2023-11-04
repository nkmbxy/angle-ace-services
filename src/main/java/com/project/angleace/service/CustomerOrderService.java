package com.project.angleace.service;

import com.project.angleace.entity.CustomerOrder;
import com.project.angleace.entity.Product;
import com.project.angleace.model.request.CreateProductOrderRequest;
import com.project.angleace.repository.CustomerOrderRepository;
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
public class CustomerOrderService {
    private final Logger logger = LoggerFactory.getLogger(CustomerOrderService.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;


    public String createCustomerOrder(CreateProductOrderRequest request) {


        List<Specification<Product>> query = new ArrayList<>();

        if (request.getProduct_id() != null) {
            query.add(ProductSpecification.hasProductID(request.getProduct_id()));
        }

        Optional<Product> product = productRepository.findOne(Specification.<Product>allOf(query));
        if (product.isPresent()) {
            Integer newAmount = product.get().getAmount() - request.getAmount();

            // ลดจำนวนสินค้า
            product.get().setAmount(newAmount);
            productRepository.save(product.get());

            // create_order
            Object CreateCustomerOrderRequest;
            CustomerOrder customerOrder = new CustomerOrder()
                    .setProduct_id(request.getProduct_id())
                    .setCount(request.getAmount())
                    .setProfit(product.get().getSellPrice() - product.get().getCost()); // คำนวณกำไร
            // save_order
            customerOrderRepository.save(customerOrder);

        }


        logger.info("request: {}", request);
        return "Create order success";
    }

}
