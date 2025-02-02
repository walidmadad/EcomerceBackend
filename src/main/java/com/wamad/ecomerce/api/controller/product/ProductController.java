package com.wamad.ecomerce.api.controller.product;

import com.wamad.ecomerce.model.Product;
import com.wamad.ecomerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-products")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }
}
