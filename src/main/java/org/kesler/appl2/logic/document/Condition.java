package org.kesler.appl2.logic.document;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Column;

import org.kesler.appl2.dao.AbstractEntity;

/**
* Класс Жизненной ситуации
*/
@Entity
@Table(name="Conditions")
public class Condition extends AbstractEntity {

	@Column(name="Name", length=255)
	private String name;

	@ManyToMany()
	private List<DocumentWithTypes> documentsWithTypes;

	public Condition() {}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

}