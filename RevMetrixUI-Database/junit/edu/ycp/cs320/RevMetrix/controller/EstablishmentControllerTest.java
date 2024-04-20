package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.EstablishmentArray;

public class EstablishmentControllerTest{
	private EstablishmentRegController controller;

	@Before
	public void setUp() {
		EstablishmentArray model = new EstablishmentArray(1);
		controller = new EstablishmentRegController();
		controller.setModel(model);
	}
	
	@Test
	public void testGetEstaFromAccount() {
		List<Establishment> testEsta = controller.getAllEstablishmentsForAccount(1);
		
		System.out.print(testEsta.get(0).getEstablishmentName());
		assertTrue(testEsta.get(0).getEstablishmentName().equals("Colony Park Lanes & Games"));
		assertTrue(testEsta.get(1).getEstablishmentName().equals("Suburban Bowlerama"));

		
	}

}