package edu.ycp.cs320.RevMetrix.model;


public class Account {
	private String userName, password, email;
	private Ball currentBall;
	private Event currentEvent;
	private Session currentSession;
	private Game currentGame;
	//private Frame currentFrame;
	private Shot currentShot;
	
	
	public Account(String Username, String Password, String Email) {
		this.userName = Username;
		this.password = Password;
		this.email = Email;
	}
	
	public Ball getCurrentBall()
	{
		return this.currentBall;
	}
	
	public void setCurrentBall(Ball ball)
	{
		this.currentBall = ball;
	}
	
	public String getUsername() {
		return userName;
	}
	
	public void setUsername(String Username) {
		this.userName = Username;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String Password) {
		this.password = Password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String Email) {
		this.email = Email;
	}
	

	
}