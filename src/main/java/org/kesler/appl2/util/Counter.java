package org.kesler.appl2.util;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import org.hibernate.annotations.Proxy;

import org.kesler.appl2.dao.AbstractEntity;

@Entity
@Proxy(lazy=false)
@Table(name="Counter")
public class Counter extends AbstractEntity {

	@Column(name="Value")
	private Integer value;

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		if(value == null) return 0;
		else return value;
	}

}