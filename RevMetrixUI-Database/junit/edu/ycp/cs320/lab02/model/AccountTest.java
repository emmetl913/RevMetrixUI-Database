package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02.model.Account;

public class AccountTest {
	private Account model;
	
	@Before
	public void setUp() {
		model = new Account("u","p","e");
	}
	
	@Test
	public void testSetUsername() {
		model.setUsername("username!");
		assertTrue(model.getUsername().equals("username!"));
	}
	@Test
	public void testSetPassword() {
		model.setPassword("pass!");
		assertTrue(model.getPassword().equals("pass!"));
	}
	@Test
	public void testSetEmail() {
		model.setEmail("email");
		assertTrue(model.getEmail().equals("email"));
	}
	
}
