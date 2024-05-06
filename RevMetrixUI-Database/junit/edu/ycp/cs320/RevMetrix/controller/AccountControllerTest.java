package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.model.Account;

public class AccountControllerTest {
	private Account model;
	private AccountController controller;
	
	@Before
	public void setUp() {
		model = new Account(null,null,null, null, null);
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
		controller.insertAccount("KevinsGmail@gmail.com","Kevin1","Kevin", "Kevin", "Smith");

		boolean testSuccess = controller.getValidLogin("Kevin","Kevin1");
		assertTrue(testSuccess == true);
	}
	@Test
	public void testInsertAccount() {
		controller.insertAccount("BrandonEmail", "password", "username", "Brandon", "Woodward");
		List<Account> accountList = controller.getAccountByEmail("BrandonEmail");
		assertTrue(accountList.get(0).getEmail().equals("BrandonEmail"));
	}
}
