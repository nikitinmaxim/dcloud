package org.dclou.example.demogpb.order.data.jpa.repository;

import org.dclou.example.demogpb.order.data.jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
