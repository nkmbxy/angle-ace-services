package com.project.angleace.controller;

import com.project.angleace.entity.CustomerOrder;
import com.project.angleace.model.request.CreateCustomerOrderRequest;
import com.project.angleace.model.request.CreateProductOrderRequest;
import com.project.angleace.model.response.Response;
import com.project.angleace.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")

public class CustomerOrderController {
    @Autowired
    private CustomerOrderService customerOrderService;

    //@GetMapping("/summarize") //สรุปยอด
    @PostMapping("/customer-order") //ลูกค้าซื้อของ
    public ResponseEntity<Response<String>> createCustomerOrder(
            @RequestBody CreateProductOrderRequest CreateProductOrderRequest
    ) {

        String message = customerOrderService.createCustomerOrder(CreateProductOrderRequest);

        return new Response<String>("200", message).response();
    }
}
