package org.kesler.appl2.logic.reception;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.kesler.appl2.logic.Operator;
import org.kesler.appl2.logic.Applicator;
import org.kesler.appl2.logic.Service;
import org.kesler.appl2.logic.Reception;


public class TestReceptionsModel {

	// @Test
	// public void testCreate() {
	// 	ArrayList<Reception> receptions = new ArrayList<Reception>();
	// 	ReceptionsModel receptionsModel = new ReceptionsModel();
	// 	List<Reception> resultList = receptionsModel.getReceptions();
	// 	assertEquals("Wrong List of receptions", receptions, resultList);
	// }

	@Test
	public void testCreateEmpty() {
		ReceptionsModel receptionsModel = new ReceptionsModel();
		List<Reception> resultList = receptionsModel.getAllReceptions();
		assertNotNull("List is null", resultList);
	}

	@Ignore
	@Test
	public void testAddReception() {
		// готовим данные для приема
		ReceptionsModel receptionsModel = new ReceptionsModel();
		Service service = new Service();
		List<Applicator> applicators = new ArrayList<Applicator>();
		Operator operator = new Operator(); 
		Date date = new Date();
		Reception reception = new Reception();
		reception.setService(service);
		reception.setApplicators(applicators);
		reception.setOperator(operator);
		reception.setOpenDate(date);
		// добавляем
		receptionsModel.addReception(reception);

		// получаем лист приемов и проверяем наличие нашего объекта
		List<Reception> resultList = receptionsModel.getAllReceptions();
		boolean resultBoolean = resultList.contains(reception);
		assertTrue("Reception don't added", resultBoolean);

	}

	@Test
	public void testSaveRead() {
		ReceptionsModel receptionsModel = new ReceptionsModel();
	}

}