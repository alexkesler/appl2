package org.kesler.appl2.logic;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import org.hibernate.annotations.Proxy;

import org.kesler.appl2.dao.AbstractEntity;

@Entity
@Table (name = "Filial")
public class Filial extends AbstractEntity {

	@Column(name="Name", length=1024)
	private String name;

	@Column(name="ShortName", length=255)
	private String shortName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}