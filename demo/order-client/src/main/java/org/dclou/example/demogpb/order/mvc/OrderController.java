package org.dclou.example.demogpb.order.mvc;

import org.dclou.example.demogpb.order.domain.CatalogItem;
import org.dclou.example.demogpb.order.domain.Customer;
import org.dclou.example.demogpb.order.domain.Order;
import org.dclou.example.demogpb.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by msnikitin on 20.04.2017.
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "catalog", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> getCatalog() {
        return orderService.getCatalog();
    }

    @GetMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getCustomers() {
        return orderService.getCustomers();
    }

    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveOrder(@RequestBody Order order) {
        return "\"" + orderService.saveOrder(order) + "\"";
    }

    @DeleteMapping(value = "orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteOrder(@PathVariable("id") long id) {
        orderService.deleteOrder(id);
        return "\"OK\"";
    }
}
