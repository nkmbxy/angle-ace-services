package com.project.angleace.service;

import com.project.angleace.entity.CustomerOrder;
import com.project.angleace.entity.Product;
import com.project.angleace.exception.Exception;
import com.project.angleace.model.request.CreateCustomerOrderRequest;
import com.project.angleace.model.request.GetSummaryProductRequest;
import com.project.angleace.model.response.SummaryModel;
import com.project.angleace.repository.CustomerOrderRepository;
import com.project.angleace.repository.ProductRepository;
import com.project.angleace.repository.specification.CustomerOrderSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerOrderService {
    private final Logger logger = LoggerFactory.getLogger(CustomerOrderService.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    public String createCustomerOrder(Integer id, CreateCustomerOrderRequest request) {

        Optional<Product> productRepo = productRepository.findById(id);
        if (productRepo.isPresent()) {
            switch (request.getSize()) {
                case "s": {
                    Integer newAmount = productRepo.get().getAmountS() - request.getAmount();
                    productRepo.get().setAmountS(newAmount);
                    break;
                }
                case "m": {
                    Integer newAmount = productRepo.get().getAmountM() - request.getAmount();
                    productRepo.get().setAmountM(newAmount);
                    break;
                }
                case "l": {
                    Integer newAmount = productRepo.get().getAmountL() - request.getAmount();
                    productRepo.get().setAmountL(newAmount);
                    break;
                }
                case "xl": {
                    Integer newAmount = productRepo.get().getAmountXL() - request.getAmount();
                    productRepo.get().setAmountXL(newAmount);
                    break;
                }
            }
            productRepository.save(productRepo.get());

            CustomerOrder customerOrder = new CustomerOrder()
                    .setProduct_id(id)
                    .setSize(request.getSize())
                    .setAmount(request.getAmount())
                    .setProfit(productRepo.get().getSellPrice() - productRepo.get().getCost());

            customerOrderRepository.save(customerOrder);
        }

        if (productRepo.isEmpty()) {
            logger.info("cannot find product in database");
            throw new Exception();
        }

        logger.info("request: {}", request);
        return "create customer order success";
    }

    public List<SummaryModel> getProfitSummary(GetSummaryProductRequest request) {
        List<Specification<CustomerOrder>> query = new ArrayList<>();

        if (request.getStartDate() != null && request.getEndDate() == null) {
            query.add(CustomerOrderSpecification.hasStartDate(request.getStartDate()));
        }
        if (request.getEndDate() != null && request.getStartDate() == null) {
            query.add(CustomerOrderSpecification.hasEndDate(request.getEndDate()));
        }
        if (request.getStartDate() != null && request.getEndDate() != null) {
            query.add(CustomerOrderSpecification.hasStartDateAndEndDate(request.getStartDate(), request.getEndDate()));
        }

        List<CustomerOrder> result = customerOrderRepository.findAll(Specification.allOf(query));

        ZoneId ictZone = ZoneId.of("Asia/Bangkok");
        Map<LocalDate, Double> profitSumByDate = result.stream()
                .collect(Collectors.groupingBy(order -> {
                    Instant createdAtInstant = order.getCreatedAt().toInstant();
                    OffsetDateTime ictTimestamp = createdAtInstant.atZone(ictZone).toOffsetDateTime();
                    return ictTimestamp.toLocalDate();
                }, Collectors.summingDouble(CustomerOrder::getProfit)));

        List<SummaryModel> summarizedData = profitSumByDate.entrySet().stream()
                .map(entry -> {
                    SummaryModel summaryModel = new SummaryModel();
                    summaryModel.setDate(entry.getKey());
                    summaryModel.setProfit(entry.getValue());
                    return summaryModel;
                })
                .collect(Collectors.toList());

        logger.info("request: {}", summarizedData);
        logger.info("result: {}", result);

        return summarizedData;
    }


}
