package org.dclou.example.demogpb.order.service;

import org.dclou.example.demogpb.order.data.jpa.entity.Order;
import org.dclou.example.demogpb.order.data.jpa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public OrderServiceImpl() {
	}

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(long id) {
        return orderRepository.findOne(id);
    }

    @Override
    @Transactional
	public Order saveOrder(Order order) {
		if (order.getOrderLine().size() == 0) {
			throw new IllegalArgumentException("No order lines!");
		}

		return orderRepository.save(order);
	}

    @Override
    @Transactional
    public void deleteOrder(long id) {
        orderRepository.delete(id);
    }
}
