package org.kesler.appl2.logic.document;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import org.kesler.appl2.dao.AbstractEntity;

/**
 *  Класс определяет тип документа
 */

@Entity
@Table(name="DocumentTypes")
public class DocumentType extends AbstractEntity {

	@Column(name="Name")
	private String name;

	public DocumentType() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}