package com.wamad.ecomerce.api.controller.order;

import com.wamad.ecomerce.model.ShopOrder;
import com.wamad.ecomerce.model.User;
import com.wamad.ecomerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get-orders")
    public List<ShopOrder> getAllOrders(@AuthenticationPrincipal User user) {
        return orderService.getOrders(user);
    }
}
