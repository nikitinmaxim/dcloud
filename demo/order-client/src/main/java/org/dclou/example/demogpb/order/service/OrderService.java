package org.dclou.example.demogpb.order.service;

import org.dclou.example.demogpb.order.domain.CatalogItem;
import org.dclou.example.demogpb.order.domain.Customer;
import org.dclou.example.demogpb.order.domain.Order;

import java.util.List;

public interface OrderService {

    List<CatalogItem> getCatalog();

    List<Customer> getCustomers();

    List<Order> getOrders();

    String saveOrder(Order order);

    void deleteOrder(long orderId);
}
