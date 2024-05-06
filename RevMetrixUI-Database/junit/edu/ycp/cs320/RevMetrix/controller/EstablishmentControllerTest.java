package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Establishment;

public class EstablishmentControllerTest{
	private EstablishmentRegController controller;

	@Before
	public void setUp() {
		Establishment model = new Establishment();
		controller = new EstablishmentRegController(1);
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
		controller.insertNewEstablishment(1,1, "name", "","address",1);
		
		List<Establishment> testEsta = controller.getAllEstablishmentsForAccount(1);
		
		System.out.println("Test Add Establishment:");
		for(Establishment esta : testEsta) {
			System.out.println("This is what youre looking for part 2: "+esta.getEstablishmentName());
		}
		System.out.println("Test Add Establishment part 2: " +testEsta.get(testEsta.size()-1).getEstablishmentName());
		assertTrue(testEsta.get(testEsta.size()-1).getEstablishmentName().equals("Suburban Bowlerama"));

		
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
	
	@Test
	public void testgetEstablishmentByAccountAndEstablishmentID() {
		
		Establishment testEsta = controller.getEstablishmentByAccountAndEstablishmentID(1, 1);
		System.out.println("Test Get Establishment by Account and Estab ID part 3:");
		System.out.println(testEsta.getEstablishmentName());
		
		assertTrue(testEsta.getEstablishmentName().equals("Colony Park Lanes & Games"));
		
	}

}