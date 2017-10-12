package org.dclou.example.demogpb.order.service;

import org.dclou.example.demogpb.order.data.jpa.entity.Order;

import java.util.List;

/**
 * Created by max on 18.07.17.
 */
public interface OrderService {

    List<Order> getOrders();

    Order getOrder(long id);

    Order saveOrder(Order order);

    void deleteOrder(long id);
}
