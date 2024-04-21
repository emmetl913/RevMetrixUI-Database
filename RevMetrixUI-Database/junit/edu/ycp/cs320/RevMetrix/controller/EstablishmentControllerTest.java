package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
		
		
		System.out.println("Test get Establishments:");
		for(Establishment esta : testEsta) {
			System.out.println(esta.getEstablishmentName());
		}
		assertTrue(testEsta.get(0).getEstablishmentName().equals("Colony Park Lanes & Games"));
		assertTrue(testEsta.get(1).getEstablishmentName().equals("Suburban Bowlerama"));

		
	}
	
	@Test
	public void testAddEstaFromAccount() {
		controller.insertNewEstablishment(1, "name", "address");
		
		List<Establishment> testEsta = controller.getAllEstablishmentsForAccount(1);
		
		System.out.println("Test Add Establishment:");
		for(Establishment esta : testEsta) {
			System.out.println(esta.getEstablishmentName());
		}
		
		assertTrue(testEsta.get(testEsta.size()-1).getEstablishmentName().equals("name"));

		
	}
	
	@Test
	public void testRemoveEstaFromAccount() {
		controller.removeEstablishment(1, "name");

		List<Establishment> testEsta = controller.getAllEstablishmentsForAccount(1);
		
		System.out.println("Test Add Establishment:");
		for(Establishment esta : testEsta) {
			System.out.println(esta.getEstablishmentName());
		}
		
		assertFalse(testEsta.get(testEsta.size()-1).getEstablishmentName().equals("name"));
		
	}

}