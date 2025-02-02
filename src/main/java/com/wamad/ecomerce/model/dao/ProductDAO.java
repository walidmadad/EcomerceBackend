package com.wamad.ecomerce.model.dao;

import com.wamad.ecomerce.model.Product;
import com.wamad.ecomerce.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
