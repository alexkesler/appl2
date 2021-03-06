package org.kesler.appl2.dao;

import java.util.List;
import java.util.Date;

import org.kesler.appl2.logic.Reception;

public interface ReceptionDAO extends DAOObservable{
	public void addReception(Reception r);
	public void updateReception(Reception r);
	public Reception getReceptionById(Long id);
	public List<Reception> getAllReceptions();
	public List<Reception> getReceptionsByOpenDate(Date from, Date to);
	public void removeReception(Reception r);
}