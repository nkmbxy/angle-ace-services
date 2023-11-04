package com.project.angleace.controller;

import com.project.angleace.model.response.Response;
import com.project.angleace.service.ProductService;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class CustomerOrderController {

    @Autowired
    private ProductService productService;

    @GetMapping("/sales-summary")
    public ResponseEntity<Response<Double>> getSalesSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        // Use startDate and endDate to calculate sales summary for the specified date range
        Double salesSummary = productService.calculateSalesSummary(startDate, endDate);

        return new Response<Double>("200", salesSummary).response();
    }
}

//@GetMapping("/summarize") //สรุปยอด ****ทำแระ
//@PostMapping("/customer-order") //ลูกค้าซื้อของ

