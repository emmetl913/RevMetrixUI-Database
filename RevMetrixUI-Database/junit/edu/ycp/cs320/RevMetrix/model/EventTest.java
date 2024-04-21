package edu.ycp.cs320.RevMetrix.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.model.Establishment;

public class EventTest {
	private Event model;
	private Establishment estab = new Establishment("Name","Address");
	private Establishment Newestab = new Establishment("NewName","NewAddress");

	
	@Before
	public void setUp() {
		model = new Event("name","type",1, estab);
	}
	
	@Test
	public void testSetGetName() {
		model.setName("newName");
		assertTrue(model.getEventName().equals("newName"));
	}
	@Test
	public void testSetGetType() {
		model.setType("newType");
		assertTrue(model.getType().equals("newType"));
	}
	@Test
	public void testSetGetStanding() {
		model.setStanding(5);
		assertTrue(model.getStanding() == 5);
	}
	
	@Test
	public void testSetGetEstablishment() {
		model.setEstablishment(Newestab);
		assertTrue(model.getEstablishmentStringName().equals(Newestab.getEstablishmentName()));
	}
	
}
