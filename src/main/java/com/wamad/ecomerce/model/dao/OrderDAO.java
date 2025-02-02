package com.wamad.ecomerce.model.dao;

import com.wamad.ecomerce.model.ShopOrder;
import com.wamad.ecomerce.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderDAO extends ListCrudRepository<ShopOrder, Long> {

    List<ShopOrder> findByUser(User user);
}
