package com.wamad.ecomerce.service;

import com.wamad.ecomerce.model.Product;
import com.wamad.ecomerce.model.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
}
