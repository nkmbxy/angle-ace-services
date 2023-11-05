package com.project.angleace.service;

import com.project.angleace.entity.CustomerOrder;
import com.project.angleace.entity.Product;
import com.project.angleace.exception.Exception;
import com.project.angleace.model.request.CreateCustomerOrderRequest;
import com.project.angleace.model.request.GetSummaryProductRequest;
import com.project.angleace.model.response.SummaryModel;
import com.project.angleace.repository.CustomerOrderRepository;
import com.project.angleace.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        Date startDateConverted = null;
        Date endDateConverted = null;
        ZoneId zoneId = ZoneId.of("Asia/Bangkok");
        if (request.getStartDate() != null) {
            startDateConverted = Date.from(request.getStartDate().atStartOfDay(zoneId).toInstant());
        }
        if (request.getEndDate() != null) {
            endDateConverted = Date.from(request.getEndDate().atStartOfDay(zoneId).toInstant());
        }
        logger.info("startDate {}", startDateConverted);
        Pageable pageable = PageRequest.of(request.getPage(), request.getPerPage()); // ใช้สำหรับการเปลี่ยนหน้าตาราง

        List<SummaryModel> result = customerOrderRepository.findProfitSummary(startDateConverted, endDateConverted);
        logger.info("request: {}", request);

        logger.info("result: {}", result);

        return result;
    }


}
