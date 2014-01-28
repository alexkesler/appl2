package org.kesler.appl2.logic.applicator;

import static org.junit.Assert.*;
import org.junit.Test;

import org.kesler.appl2.logic.Applicator;


public class TestApplicatorUL {

	@Test
	public void testCreate() {
		ApplicatorUL applicatorUL = new ApplicatorUL();
		assertNotNull("Cannot create ApplicatorUL", applicatorUL);
	}

	@Test
	public void testCreateByApplicator() {
		Applicator applicatorUL = new ApplicatorUL();
		assertNotNull("Error extending Applicator", applicatorUL);
	}

} 