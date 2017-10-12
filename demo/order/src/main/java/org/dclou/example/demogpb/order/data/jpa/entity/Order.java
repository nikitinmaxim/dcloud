package org.dclou.example.demogpb.order.data.jpa.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERTABLE")
public class Order {

	@Id
	@GeneratedValue
	private long id;

	private long customerId;

	@OneToMany(cascade = CascadeType.ALL)
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
