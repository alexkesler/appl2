package org.kesler.appl2.logic.applicator;

import java.util.List;
import java.util.ArrayList;

import org.kesler.appl2.dao.DAOFactory;
import org.kesler.appl2.dao.DAOListener;
import org.kesler.appl2.dao.DAOState;
import org.kesler.appl2.logic.ModelState;

import org.kesler.appl2.logic.IP;

public class IPModel implements DAOListener{
	private List<IP> ipList;
	private List<IP> filteredIPList;
	private String filterString;
	private static IPModel instance = null;

	private List<IPModelStateListener> listeners;

	public static synchronized IPModel getInstance() {
		if (instance == null) {
			instance = new IPModel();
		}
		return instance;
	}

	private IPModel() {

		ipList = new ArrayList<IP>();
		filteredIPList = new ArrayList<IP>();
		filterString = "";
		listeners = new ArrayList<IPModelStateListener>();

	}

	public void addIPModelStateListener(IPModelStateListener listener) {
		listeners.add(listener);
	}

	public List<IP> getAllIPs() {

		return ipList;

	}

	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

	public void filterIPs() {
		
		// если строка фильтра не пустая - пересоздаем фильтрованный список
		if (!filterString.isEmpty()) {
			filteredIPList = new ArrayList<IP>();
			for (IP ip: ipList) {
				if (ip.getFullName().toLowerCase().indexOf(filterString.toLowerCase(),0) != -1) {
					filteredIPList.add(ip);
				}
			}
		} else {
			filteredIPList = ipList;
		}
		notifyListeners(ModelState.FILTERED);
	}

	public void filterIPsInSeparateThread() {
		Thread filterer = new Thread(new Runnable() {
			public void run() {
				filterIPs();
			}
		});
		filterer.start();
	}

	public List<IP> getFilteredIPs() {

		return filteredIPList;
		
	}

	public int addIP(IP ip) {
		Long id = DAOFactory.getInstance().getIPDAO().addItem(ip);
		if (id != null) {
			ipList.add(ip);
			return ipList.size()-1;
		} else {
			return -1;
		}
		
	}

	public void updateIP(IP ip) {
		DAOFactory.getInstance().getIPDAO().updateItem(ip);
	}

	public void deleteIP(IP ip) {
		DAOFactory.getInstance().getIPDAO().removeItem(ip);
		ipList.remove(ip);
	}

	public void readIPs() {
		ipList = DAOFactory.getInstance().getIPDAO().getAllItems();
		notifyListeners(ModelState.UPDATED);
	}

	public void readIPsInSeparateThread() {
		Thread reader = new Thread(new Runnable() {
			public void run() {
				readIPs();
				filterIPs();
			}
		});

		reader.start();
	}

	@Override
	public void daoStateChanged(DAOState state) {
		switch (state) {
			case CONNECTING:
				notifyListeners(ModelState.CONNECTING);
			break;

			case READING:
				notifyListeners(ModelState.READING);
			break;

			case WRITING:
				notifyListeners(ModelState.WRITING);
			break;

			case READY:
				notifyListeners(ModelState.READY);		
			break;

			case ERROR:
				notifyListeners(ModelState.ERROR);
			break;
		}
	}

	private void notifyListeners(ModelState state) {
		for (IPModelStateListener listener : listeners) {
			listener.ipModelStateChanged(state);			
		}
	}


}