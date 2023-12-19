package com.project.angleace.controller;

import com.project.angleace.entity.Product;
import com.project.angleace.model.request.*;
import com.project.angleace.model.response.Response;
import com.project.angleace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) Double startPrice,
            @RequestParam(required = false) Double endPrice
    ) {
        GetProductRequest request = new GetProductRequest();

        request.setName(name);
        request.setType(type);
        request.setManufacturer(manufacturer);
        request.setStartPrice(startPrice);
        request.setEndPrice(endPrice);

        List<Product> product = productService.getProducts(request);

        return new Response<List<Product>>("200", product).response();
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<String>> createProduct(
            @ModelAttribute CreateProductRequest createProductRequest

    ) throws IOException {

        String message = productService.createProduct(createProductRequest);

        return new Response<String>("200", message).response();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Response<Product>> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        return new Response<Product>("200", product).response();
    }

    @PostMapping(value = "product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<String>> editProductById(
            @PathVariable Integer id,
            @ModelAttribute EditProductRequest editProductRequest) throws IOException {
        String editedProduct = productService.editProductById(id, editProductRequest);
        return new Response<String>("200", editedProduct).response();

    }

    @PostMapping("/product-remove")
    public ResponseEntity<Response<String>> removeProduct(
            @RequestBody RemoveProductRequest removeProductRequest
    ) {
        String message = productService.removeProducts(removeProductRequest);
        return new Response<String>("200", message).response();
    }

}






