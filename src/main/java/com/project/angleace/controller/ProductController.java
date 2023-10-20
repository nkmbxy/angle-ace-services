package com.project.angleace.controller;

import com.project.angleace.entity.Product;
import com.project.angleace.model.request.GetProductRequest;


import com.project.angleace.model.response.Response;
import com.project.angleace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Response<List<Product>>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String factory,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage
    ) {
        GetProductRequest request = new GetProductRequest();

        request.setName(name);
        request.setType(type);
        request.setFactory(factory);
        request.setPage(page);
        request.setPerPage(perPage);

        List<Product> product = productService.getProducts(request);

        return new Response<List<Product>>("200",product).response();
    }


}
