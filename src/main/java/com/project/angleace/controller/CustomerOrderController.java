package com.project.angleace.controller;

import com.project.angleace.model.request.CreateCustomerOrderRequest;
import com.project.angleace.model.response.Response;
import com.project.angleace.service.CustomerOrderService;
import com.project.angleace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ProductService productService;

//    @GetMapping("/sales-summary")
//    public ResponseEntity<Response<List<SummaryModel>>> getSalesSummary(
//            @RequestParam LocalDate startDate,
//            @RequestParam LocalDate endDate
//    ) {
//        GetSummaryProductRequest request = new GetSummaryProductRequest();
//        request.setEndDate(endDate);
//        request.setStartDate(startDate);
//
//        List<SummaryModel> summary = productService.calculateSalesSummary(startDate, endDate);
//
//        return new Response<List<SummaryModel>>("200", summary).response();
//    }

    @PostMapping("/customer-order/{id}") //ลูกค้าซื้อของ
    public ResponseEntity<Response<String>> createCustomerOrder(
            @PathVariable Integer id,
            @RequestBody CreateCustomerOrderRequest createCustomerOrderRequest
    ) {

        String message = customerOrderService.createCustomerOrder(id, createCustomerOrderRequest);

        return new Response<String>("200", message).response();
    }
}


