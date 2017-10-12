package org.dclou.example.demogpb.order.data.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderLine {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "F_COUNT")
	private int count;

	private long itemId;

	public OrderLine() {
	}

	public OrderLine(int count, long item) {
		this.count = count;
		this.itemId = item;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
}
