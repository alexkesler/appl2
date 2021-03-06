package org.kesler.appl2.logic.reception;

import java.util.*;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import org.kesler.appl2.logic.Reception;
import org.kesler.appl2.dao.DAOFactory;
import org.kesler.appl2.dao.DAOListener;
import org.kesler.appl2.dao.DAOState;
import org.kesler.appl2.logic.reception.filter.ReceptionsFiltersModel;
import org.kesler.appl2.util.OptionsUtil;
import org.kesler.appl2.logic.reception.filter.ReceptionsFilter;
import org.kesler.appl2.logic.ModelState;

public class ReceptionsModel implements DAOListener{
    
    private static final Logger log = Logger.getLogger(ReceptionsModel.class);

    private ReceptionsFiltersModel filtersModel;
	private List<Reception> allReceptions;
	private List<Reception> filteredReceptions;
    private List<Reception> lastReceptions;

	private List<ReceptionsModelStateListener> listeners;

	public ReceptionsModel() {
		allReceptions = new ArrayList<Reception>();
		filteredReceptions = new ArrayList<Reception>();
        lastReceptions = new ArrayList<Reception>();

		listeners = new ArrayList<ReceptionsModelStateListener>();
		DAOFactory.getInstance().getReceptionDAO().addDAOListener(this);

        filtersModel = new ReceptionsFiltersModel();
	}

    public ReceptionsFiltersModel getFiltersModel() {
        return filtersModel;
    }

	public void addReceptionsModelStateListener(ReceptionsModelStateListener listener) {
		listeners.add(listener);
	}

//	public static synchronized ReceptionsModel getInstance() {
//		if (instance == null) {
//			instance = new ReceptionsModel();
//		}
//		return instance;
//	}


	public List<Reception> getAllReceptions() {
		return allReceptions;
	}

	public List<Reception> getFilteredReceptions() {
		return filteredReceptions;
	}

    public List<Reception> getLastReceptions() {
        return lastReceptions;
    }


	// читаем данные из БД
	public void readReceptions() {
        Date fromOpenDate = filtersModel.getFromOpenDate();
        Date toOpenDate = filtersModel.getToOpenDate();
        if (fromOpenDate != null || toOpenDate != null) {
            allReceptions = DAOFactory.getInstance().getReceptionDAO().getReceptionsByOpenDate(fromOpenDate,toOpenDate);
        } else {
            allReceptions = DAOFactory.getInstance().getReceptionDAO().getAllReceptions();
        }
        System.out.println("----Receptions in model----" + allReceptions.size());
		notifyListeners(ModelState.UPDATED);
	}

	public void readReceptionsInSeparateThread() {
		Thread readerThread = new Thread(new Runnable() {
			public void run() {
				readReceptions();
			}
		});
		readerThread.start();
	}

	// применяем фильтры
	public void applyFilters() {
        List<ReceptionsFilter> filters = filtersModel.getFilters();
		filteredReceptions = new ArrayList<Reception>();
		notifyListeners(ModelState.FILTERING);
		for (Reception reception: allReceptions) {
			boolean fit = true;		
			for (ReceptionsFilter filter: filters) {
				if (!filter.checkReception(reception)) {
                    fit = false;
                    break;
                }

			}
			if (fit) filteredReceptions.add(reception);
		}
		notifyListeners(ModelState.FILTERED);

	}

    /**
     * Применяет фильтры в отдельном потоке
     */
	public void applyFiltersInSeparateThread() {
		Thread filterThread = new Thread(new Runnable() {
			public void run() {
//				readReceptions();
				applyFilters();
			}
		});
		filterThread.start();
	}

    /**
     * Читает список приемов и применяет фильтры в отдельном потоке
     */
    public void readReceptionsAndApplyFiltersInSeparateThread() {
        Thread readerFiltererThread = new Thread(new Runnable() {
            public void run() {
				readReceptions();
                applyFilters();
            }
        });
        readerFiltererThread.start();
    }

    /**
     * Метод для последовательного применения фильтров
     * @param filters Фильтры, которые необходимо применить
     */
    public void applyFiltersSequently(List<ReceptionsFilter> filters) {
        List<Reception> newFilteredReceptions = new ArrayList<Reception>();
        notifyListeners(ModelState.FILTERING);
        for (Reception reception: filteredReceptions) {
            boolean fit = true;
            for (ReceptionsFilter filter: filters) {
                if (!filter.checkReception(reception)) fit = false;
            }
            if (fit) newFilteredReceptions.add(reception);
        }
        filteredReceptions = newFilteredReceptions;
        notifyListeners(ModelState.FILTERED);
    }

    /**
     * Применяет фильтры последовательно в отдельном потоке
     * @param filters Фильтры, которые необходимо применить
     */
    public void applyFiltersSequentlyInSeparateThread(final List<ReceptionsFilter> filters) {
        Thread filterThread = new Thread(new Runnable() {
            public void run() {
                applyFiltersSequently(filters);
            }
        });
        filterThread.start();
    }


    /**
     *
     */
    public void readLastReceptions() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR, -2);
        Date fromDate = calendar.getTime();
        Date toDate = new Date();
        lastReceptions = DAOFactory.getInstance().getReceptionDAO().getReceptionsByOpenDate(fromDate, toDate);
    }

    /**
     * Сохраняет новый прием
     * @param reception  Сохраняемый прием
     */
	public void addReception(Reception reception) {
		// Назначаем для приема начальный статус
        reception.setStatus(ReceptionStatusesModel.getInstance().getInitReceptionStatus());

		DAOFactory.getInstance().getReceptionDAO().addReception(reception);
//		allReceptions.add(reception);
	}

    /**
     * Сохраняет новый прием в отдельном потоке
      * @param reception сохраняемый прием
     */
    public void addReceptionInSeparateThread(final Reception reception) {
        Thread adderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                addReception(reception);
            }
        });
        adderThread.start();
    }

    /**
     * Сохраняет существующий прием
     * @param reception существующий прием, который необходимо сохранить
     */
	public void updateReception(Reception reception) {
		DAOFactory.getInstance().getReceptionDAO().updateReception(reception);
	}

    /**
     * Сохраняет существующий прием в отдельном потоке
     * @param reception  существующий прием, который необходимо сохранить
     */
    public void updateReceptionInSeparateThread(final Reception reception) {
        Thread updaterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateReception(reception);
            }
        });
        updaterThread.start();
    }

    /**
     * Удаляет прием
     * @param reception прием, который необходимо удалить
     */
	public void removeReception(Reception reception) {
		DAOFactory.getInstance().getReceptionDAO().removeReception(reception);
		allReceptions.remove(reception);
		filteredReceptions.remove(reception);
	}

    /**
     * Удаляет прием в отдельном потоке
     * @param reception прием, который необходимо удалить
     */
    public void removeReceptionInSeparateThread(final Reception reception) {
        Thread removerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                removeReception(reception);
            }
        });
        removerThread.start();
    }

	/**
	* Реализует интерфейс {@link org.kesler.appl2.dao.DAOListener}
	*/
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
		for (ReceptionsModelStateListener listener: listeners) {
			listener.receptionsModelStateChanged(state);
		}
	} 

}

