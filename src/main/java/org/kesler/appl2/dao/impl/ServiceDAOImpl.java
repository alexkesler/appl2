package org.kesler.appl2.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import org.kesler.appl2.util.HibernateUtil;

import org.kesler.appl2.dao.ServiceDAO;
import org.kesler.appl2.logic.Service;
import org.kesler.appl2.dao.DAOState;


public class ServiceDAOImpl extends GenericDAOImpl<Service> implements ServiceDAO {


	public ServiceDAOImpl() {
		super(Service.class);
	}

	public void addService(Service service) {
		addItem(service);
	}

	public void updateService(Service service) {
		updateItem(service);
	}

	public Service getServiceById(Long id) {
		return getItemById(id);
	}

	public List<Service> getAllServices() {
		return getAllItems();
	}

	public List<Service> getActiveServices() {
		Session session = null;
		List<Service> services = new ArrayList<Service>();
		try {
			log.info("Reading active services");
			notifyListeners(DAOState.CONNECTING);
			session = HibernateUtil.getSessionFactory().openSession();
			notifyListeners(DAOState.READING);
			services = session.createCriteria(Service.class)
							  .add(Restrictions.eq("enabled",new Boolean(true)))
							  .list();
			log.info("Complete reading active services");				  
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			System.err.println("Error while reading services");
			log.error("Error reading active services", he);
			notifyListeners(DAOState.ERROR);			
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}				
		}
		return  services;
	}

	public void removeService(Service service) {
		removeItem(service);
	}



}