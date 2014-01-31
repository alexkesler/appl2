package org.kesler.appl2.logic.document;

import java.util.List;

import javax.persistence.*;

import org.kesler.appl2.dao.AbstractEntity;

/**
 * Сущность для хранения документа с доступными типами
 */
@Entity
@Table(name="DocumentsWithTypes")
public class DocumentWithTypes extends AbstractEntity {

	@Column(name="Name")
	private Document document;

	// @OneToMany (fetch = FetchType.EAGER, mappedBy="reception")
	// @Cascade ({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	// @Fetch(FetchMode.SUBSELECT)
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<DocumentType> allowedTypes;

	public DocumentWithTypes() {

    }

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

    public List<DocumentType> getAllowedTypes() {return allowedTypes;}

    public void setAllowedTypes(List<DocumentType> allowedTypes) {this.allowedTypes = allowedTypes;}

}