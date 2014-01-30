package org.kesler.appl2.logic;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import org.kesler.appl2.dao.AbstractEntity;

@Entity
@Table(name="Documents")
public class Document extends AbstractEntity {

	@Column(name="Name")
	private String name;

	public Document() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}