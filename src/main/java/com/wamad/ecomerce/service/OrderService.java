package com.wamad.ecomerce.service;

import com.wamad.ecomerce.model.ShopOrder;
import com.wamad.ecomerce.model.User;
import com.wamad.ecomerce.model.dao.OrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public List<ShopOrder> getOrders(User user) {
        return orderDAO.findByUser(user);
    }
}
