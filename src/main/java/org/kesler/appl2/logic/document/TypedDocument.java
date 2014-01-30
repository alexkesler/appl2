package org.kesler.appl2.logic.document;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import org.kesler.appl2.dao.AbstractEntity;

@Entity
@Table(name="TypedDocuments")
public class TypedDocument extends AbstractEntity {

	@Column(name="Name")
	private Document document;

	// @OneToMany (fetch = FetchType.EAGER, mappedBy="reception")
	// @Cascade ({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	// @Fetch(FetchMode.SUBSELECT)
	private List<DocumentType> allowedTypes;

	public TypedDocument() {}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}