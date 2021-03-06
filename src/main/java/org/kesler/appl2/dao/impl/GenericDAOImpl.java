package org.kesler.appl2.dao.impl;

import java.util.List;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.apache.log4j.Logger;

import org.kesler.appl2.dao.GenericDAO;
import org.kesler.appl2.dao.AbstractEntity;
import org.kesler.appl2.dao.DAOState;
import org.kesler.appl2.dao.DAOListener;

import org.kesler.appl2.util.HibernateUtil;

public class GenericDAOImpl<T extends AbstractEntity> implements GenericDAO <T> {
	protected final Logger log;

	private List<DAOListener> listeners = new ArrayList<DAOListener>();

	private Class<T> type;

	public GenericDAOImpl(Class<T> type) {
		this.type = type;
		log = Logger.getLogger("GenericDAO<"+ type.getSimpleName() + ">");
	}

	@Override
	public void addDAOListener(DAOListener listener) {
		listeners.add(listener);
	}

	public Long addItem(T item) {
		Long id = null;

		notifyListeners(DAOState.CONNECTING);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			log.debug("Begin to write item");
			notifyListeners(DAOState.WRITING);
			tx = session.beginTransaction();
			session.save(item);
			session.flush();
			session.clear();
			tx.commit();
			log.info("Adding item complete");
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			if (tx != null) tx.rollback();
			log.error("Error writing item", he);
			he.printStackTrace();
			notifyListeners(DAOState.ERROR);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		id = item.getId();

		return id;
	}

	public void updateItem(T item) {

		notifyListeners(DAOState.CONNECTING);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			log.debug("Updating item");
			notifyListeners(DAOState.WRITING);
			tx = session.beginTransaction();
			session.update(item);
			session.flush();
			session.clear();
			tx.commit();
			log.info("Updating item complete");
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			if (tx != null) tx.rollback();
			log.error("Error updating item", he);
			notifyListeners(DAOState.ERROR);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();		
			}
		}		

	}

	public T getItemById(long id) {
		T item = null;

		notifyListeners(DAOState.CONNECTING);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			log.debug("Reading item");
			notifyListeners(DAOState.READING);
			item = (T) session.load(type, id);
			log.info("Reading item complete");
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			log.error("Reading item error", he);
			notifyListeners(DAOState.ERROR);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();				
			}

		}
		return item;
	}

	public List<T> getAllItems() {
		List<T> list = new ArrayList<T>();
		notifyListeners(DAOState.CONNECTING);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			log.debug("Reading items");
			notifyListeners(DAOState.READING);
			list = session.createCriteria(type).list();
			log.info("Readed " + list.size() + " items");
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			log.error("Error reading items", he);
			notifyListeners(DAOState.ERROR);
		} finally {
			if (session!=null && session.isOpen()) {
				session.close();				
			}
		}

		return list;
	}

	public void removeItem(T item) {

		notifyListeners(DAOState.CONNECTING);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			log.debug("Removing item");
			notifyListeners(DAOState.WRITING);
			tx = session.beginTransaction();
			session.delete(item);
			session.flush();
			session.clear();
			tx.commit();
			log.info("Item removed");
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			if (tx != null) tx.rollback();
			log.error("Error removing item", he);
			notifyListeners(DAOState.ERROR);
		} finally {
			if (session!=null && session.isOpen()) {
				session.close();				
			}			
		}

	}

	protected void notifyListeners(DAOState state) {
		for (DAOListener listener: listeners) {
			listener.daoStateChanged(state);
		}
	}

}