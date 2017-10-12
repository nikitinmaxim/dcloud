package org.dclou.example.demogpb.order.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;
    private Long customerId;
    private List<OrderLine> orderLine = new ArrayList<>();

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<OrderLine> orderLine) {
        this.orderLine = orderLine;
    }
}
