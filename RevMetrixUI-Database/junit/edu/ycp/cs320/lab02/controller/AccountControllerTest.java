package edu.ycp.cs320.lab02.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02.controller.AccountController;
import edu.ycp.cs320.lab02.model.Account;

public class AccountControllerTest {
	private Account model;
	private AccountController controller;
	
	@Before
	public void setUp() {
		model = new Account(null,null,null);
		controller = new AccountController();
		controller.setModel(model);
		controller.signUp("Kevin","Kevin1","KevinsGmail@gmail.com");

	}
	
	@Test
	public void testSignUp() {
		controller.signUp("Kevin","Kevin1","KevinsGmail@gmail.com");

		assertTrue(model.getUsername().equals("Kevin"));
		assertTrue(model.getPassword().equals("Kevin1"));
		assertTrue(model.getEmail().equals("KevinsGmail@gmail.com"));
	}
	
	
	@Test
	public void testValidLogin() {
		controller.signUp("Kevin","Kevin1","KevinsGmail@gmail.com");

		boolean testSuccess = controller.getValidLogin("Kevin","Kevin1");
		assertTrue(testSuccess == true);
	}
	
}
