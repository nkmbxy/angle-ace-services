package com.project.angleace.controller;

import com.project.angleace.model.request.CreateProductOrderRequest;
import com.project.angleace.model.response.Response;
import com.project.angleace.service.CustomerOrderService;
import com.project.angleace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/sales-summary")
    public ResponseEntity<Response<Double>> getSalesSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        Double salesSummary = productService.calculateSalesSummary(startDate, endDate);

        return new Response<Double>("200", salesSummary).response();
    }

    @PostMapping("/customer-order") //ลูกค้าซื้อของ
    public ResponseEntity<Response<String>> createCustomerOrder(
            @RequestBody CreateProductOrderRequest CreateProductOrderRequest
    ) {

        String message = customerOrderService.createCustomerOrder(CreateProductOrderRequest);

        return new Response<String>("200", message).response();
    }
}


