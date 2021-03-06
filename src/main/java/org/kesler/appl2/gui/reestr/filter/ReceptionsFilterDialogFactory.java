package org.kesler.appl2.gui.reestr.filter;

import javax.swing.JFrame;

import org.kesler.appl2.logic.reception.filter.ReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.ReceptionsFiltersEnum;

import org.kesler.appl2.logic.reception.filter.OpenDateReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.ByRecordReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.FilialReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.StatusReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.ServiceReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.OperatorReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.ToIssueDateReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.ResultInMFCReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.FLReceptionsFilter;
import org.kesler.appl2.logic.reception.filter.ULReceptionsFilter;


public abstract class ReceptionsFilterDialogFactory {

    /**
     * Создает диалог создания нового фильтра
     * @param view родительский вид
     * @param filterEnum тип диалога
     * @return  диалог создания фильтра
     */
	public static ReceptionsFilterDialog createDialog(JFrame view, ReceptionsFiltersEnum filterEnum) {
		ReceptionsFilterDialog receptionsFilterDialog = null;
		/// Создаем диалог на основнии сведений о необходимом типе диалога
		switch (filterEnum) {
			case OPEN_DATE:
				receptionsFilterDialog = new OpenDateReceptionsFilterDialog(view);
			break;
            case FL:
                receptionsFilterDialog = new FLReceptionsFilterDialog(view);
                break;
            case UL:
                receptionsFilterDialog = new ULReceptionsFilterDialog(view);
                break;
			case FILIAL:
				receptionsFilterDialog = new FilialReceptionsFilterDialog(view);
			break;
			case BY_RECORD:
				receptionsFilterDialog = new ByRecordReceptionsFilterDialog(view);
			break;
			case STATUS:
				receptionsFilterDialog = new StatusReceptionsFilterDialog(view);	
			break;
			case SERVICE:
				receptionsFilterDialog = new ServiceReceptionsFilterDialog(view);	
			break;
			case OPERATOR:
				receptionsFilterDialog = new OperatorReceptionsFilterDialog(view);	
			break;
			case TO_ISSUE_DATE:
				receptionsFilterDialog = new ToIssueDateReceptionsFilterDialog(view);	
			break;
			case RESULT_IN_MFC:
				receptionsFilterDialog = new ResultInMFCReceptionsFilterDialog(view);	
			break;
			default:
				break;
		}

		return receptionsFilterDialog;
	}

    /**
     * Создает диалог редактирования фильтра
     * @param view
     * @param receptionsFilter
     * @return
     */
	public static ReceptionsFilterDialog createDialog(JFrame view, ReceptionsFilter receptionsFilter) {

		ReceptionsFilterDialog receptionsFilterDialog = null;

		// По классу фильтра определяем какой диалог создавать
		if (receptionsFilter instanceof OpenDateReceptionsFilter) { // Фильтр по дате открытия

			OpenDateReceptionsFilter openDateReceptionsFilter = (OpenDateReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new OpenDateReceptionsFilterDialog(view, openDateReceptionsFilter);

		} else if (receptionsFilter instanceof ByRecordReceptionsFilter) { // Фильтр по предв записи

			ByRecordReceptionsFilter byRecordReceptionsFilter = (ByRecordReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new ByRecordReceptionsFilterDialog(view, byRecordReceptionsFilter);

		} else if (receptionsFilter instanceof FilialReceptionsFilter) { // Фильтр по филиалу

			FilialReceptionsFilter filialReceptionsFilter = (FilialReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new FilialReceptionsFilterDialog(view, filialReceptionsFilter);

        } else if (receptionsFilter instanceof FLReceptionsFilter) { // Фильтр по филиалу

            FLReceptionsFilter flReceptionsFilter = (FLReceptionsFilter) receptionsFilter;
            receptionsFilterDialog = new FLReceptionsFilterDialog(view, flReceptionsFilter);

        } else if (receptionsFilter instanceof ULReceptionsFilter) { // Фильтр по филиалу

            ULReceptionsFilter ulReceptionsFilter = (ULReceptionsFilter) receptionsFilter;
            receptionsFilterDialog = new ULReceptionsFilterDialog(view, ulReceptionsFilter);

        } else if (receptionsFilter instanceof StatusReceptionsFilter) { // Фильтр по состоянию

			StatusReceptionsFilter statusReceptionsFilter = (StatusReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new StatusReceptionsFilterDialog(view, statusReceptionsFilter);

		} else if (receptionsFilter instanceof ServiceReceptionsFilter) { // Фильтр по услуге

			ServiceReceptionsFilter serviceReceptionsFilter = (ServiceReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new ServiceReceptionsFilterDialog(view, serviceReceptionsFilter);

		} else if (receptionsFilter instanceof OperatorReceptionsFilter) { // Фильтр по оператору

			OperatorReceptionsFilter operatorReceptionsFilter = (OperatorReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new OperatorReceptionsFilterDialog(view, operatorReceptionsFilter);

		} else if (receptionsFilter instanceof ToIssueDateReceptionsFilter) { // Фильтр по дате не выдачу

			ToIssueDateReceptionsFilter toIssueDateReceptionsFilter = (ToIssueDateReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new ToIssueDateReceptionsFilterDialog(view, toIssueDateReceptionsFilter);

		} else if (receptionsFilter instanceof ResultInMFCReceptionsFilter) { // Фильтр по дате не выдачу

			ResultInMFCReceptionsFilter resultInMFCReceptionsFilter = (ResultInMFCReceptionsFilter) receptionsFilter;
			receptionsFilterDialog = new ResultInMFCReceptionsFilterDialog(view, resultInMFCReceptionsFilter);

		} 

		return receptionsFilterDialog;

	}



}