package org.dclou.example.demogpb.order.mvc;

import org.dclou.example.demogpb.order.data.jpa.entity.Order;
import org.dclou.example.demogpb.order.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller to handle customer orders
 */
@RestController
@RequestMapping("/api")
class OrderController {

	@Autowired
	private OrderServiceImpl orderService;

	private OrderController() {
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Order> getOrders() {
		return orderService.getOrders();
	}

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
        return "OK";
    }

	@GetMapping(value = "/{id}")
	public Order getOrder(@PathVariable("id") long id) {
		return orderService.getOrder(id);
	}

	@DeleteMapping(value = "/{id}")
	public String deleteOrder(@PathVariable("id") long id) {
		orderService.deleteOrder(id);
		return "OK";
	}
}
