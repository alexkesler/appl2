package org.kesler.appl2.dao;

import java.util.List;

import org.kesler.appl2.logic.Service;

public interface ServiceDAO extends GenericDAO<Service> {
	public void addService(Service service);
	public void updateService(Service service);
	public Service getServiceById(Long id);
	public List<Service> getAllServices();
	public List<Service> getActiveServices();
	public void removeService(Service service);
}