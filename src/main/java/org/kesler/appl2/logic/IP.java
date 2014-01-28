package org.kesler.appl2.logic;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import org.kesler.appl2.dao.AbstractEntity;


/**
* Класс заявтеля - ИП
*/

@Entity
@Table(name="IP")
@Proxy(lazy=false)
public class IP extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name="FLID")
	private FL fl;

	@Column(name="INN")
	private String inn;

	@Column(name="OGRNIP")
	private String ogrnip;


	public IP () {}

	public FL getFL() {
		return fl;
	}

	public void setFL(FL fl) {
		this.fl = fl;
	}

	public String getINN() {
		return inn;
	}

	public void setINN(String inn) {
		this.inn = inn;
	}

	public String getOGRNIP() {
		return ogrnip;
	}

	public void setOGRNIP(String ogrnip) {
		this.ogrnip = ogrnip;
	}


	public String getShortName() {
		return "ИП " + fl.getShortFIO();
	}

	public String getFullName() {
		return "ИП " + fl.getFIO(); 
	}

	@Override
	public String toString() {
		return getShortName();
	}
}