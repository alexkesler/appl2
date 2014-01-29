package org.kesler.appl2.dao;

import org.kesler.appl2.dao.impl.ServiceDAOImpl;	
import org.kesler.appl2.dao.impl.ReceptionDAOImpl;
import org.kesler.appl2.dao.impl.GenericDAOImpl;

import org.kesler.appl2.logic.Operator;
import org.kesler.appl2.logic.FL;
import org.kesler.appl2.logic.IP;
import org.kesler.appl2.logic.UL;
import org.kesler.appl2.logic.reception.ReceptionStatus;
import org.kesler.appl2.util.Counter;

public class DAOFactory {
	private static ServiceDAO serviceDAO = null;
	private static GenericDAO<Operator> operatorDAO = null;
	private static ReceptionDAO receptionDAO = null;
	private static GenericDAO<FL> flDAO = null;
	private static GenericDAO<IP> ipDAO = null;
	private static GenericDAO<UL> ulDAO = null;
	private static GenericDAO<ReceptionStatus> receptionStatusDAO = null;
	private static GenericDAO<Counter> counterDAO = null;
	
	private static DAOFactory instance = null;

	public static synchronized DAOFactory getInstance() {
		if (instance == null) {	
			instance = new DAOFactory();
		}
		return instance;
	}

	public ServiceDAO getServiceDAO() {
		if (serviceDAO == null) {
			serviceDAO = new ServiceDAOImpl();
		}
		return serviceDAO;
	}


	public GenericDAO<Operator> getOperatorDAO() {
		if (operatorDAO == null) {
			operatorDAO = new GenericDAOImpl<Operator>(Operator.class);
		}
		return operatorDAO;
	}

	public ReceptionDAO getReceptionDAO() {
		if (receptionDAO == null) {
			receptionDAO = new ReceptionDAOImpl();
		}
		return receptionDAO;
	}

	public GenericDAO<FL> getFLDAO() {
		if (flDAO == null) {
			flDAO = new GenericDAOImpl<FL>(FL.class);
		}
		return flDAO;
	}

	public GenericDAO<IP> getIPDAO() {
		if (ipDAO == null) {
			ipDAO = new GenericDAOImpl<IP>(IP.class);
		}
		return ipDAO;
	}

	public GenericDAO<UL> getULDAO() {
		if (ulDAO == null) {
			ulDAO = new GenericDAOImpl<UL>(UL.class);
		}
		return ulDAO;
	}

	public GenericDAO<ReceptionStatus> getReceptionStatusDAO() {
		if (receptionStatusDAO == null) {
			receptionStatusDAO = new GenericDAOImpl<ReceptionStatus>(ReceptionStatus.class);
		}
		return receptionStatusDAO;
	}

	public GenericDAO<Counter> getCounterDAO() {
		if (counterDAO == null) {
			counterDAO = new GenericDAOImpl<Counter>(Counter.class);
		}
		
		return counterDAO;
	}


}