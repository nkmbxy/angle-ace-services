package com.project.angleace.controller;

import com.project.angleace.model.request.CreateProductOrderRequest;
import com.project.angleace.model.response.Response;
import com.project.angleace.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class ProductOrderController {
    @Autowired
    private ProductOrderService productOrderService;

    @PostMapping("/product-order")
    public ResponseEntity<Response<String>> createProductOrder(
            @RequestBody List<CreateProductOrderRequest> createProductOrderRequest
    ) {

        String message = productOrderService.createProductOrder(createProductOrderRequest);

        return new Response<String>("200", message).response();
    }
}

