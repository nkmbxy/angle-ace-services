package com.project.angleace.controller;

import com.project.angleace.model.request.CreateCustomerOrderRequest;
import com.project.angleace.model.request.GetSummaryProductRequest;
import com.project.angleace.model.response.Response;
import com.project.angleace.model.response.SummaryModel;
import com.project.angleace.service.CustomerOrderService;
import com.project.angleace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/profit-summary")
    public ResponseEntity<Response<List<SummaryModel>>> getProfitSummary(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage
    ) {
        GetSummaryProductRequest request = new GetSummaryProductRequest();
        request.setEndDate(endDate);
        request.setStartDate(startDate);
        request.setPage(page);
        request.setPerPage(perPage);

        List<SummaryModel> summary = customerOrderService.getProfitSummary(request);

        return new Response<List<SummaryModel>>("200", summary).response();
    }

    @PostMapping("/customer-order/{id}") //ลูกค้าซื้อของ
    public ResponseEntity<Response<String>> createCustomerOrder(
            @PathVariable Integer id,
            @RequestBody CreateCustomerOrderRequest createCustomerOrderRequest
    ) {

        String message = customerOrderService.createCustomerOrder(id, createCustomerOrderRequest);

        return new Response<String>("200", message).response();
    }
}


