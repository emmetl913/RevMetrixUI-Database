package edu.ycp.cs320.RevMetrix.model;


public class Account {
	private String userName, password, email, firstName, lastName;
	private Ball currentBall;
	private Event currentEvent;
	private Session currentSession;
	private Game currentGame;
	private int accountId; 
	private Frame currentFrame;
	private Shot currentShot;
	
	
	public Account(String Username, String Password, String Email, String firstName, String lastName) {
		this.userName = Username;
		this.password = Password;
		this.email = Email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Ball getCurrentBall()
	{
		return this.currentBall;
	}
	
	public void setCurrentBall(Ball ball)
	{
		this.currentBall = ball;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accId) {
		accountId = accId;
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

	public void setCurrentGame(Game currGame) {
		currentGame = currGame;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Shot getCurrentShot() {
		return currentShot;
	}

	public void setCurrentShot(Shot currentShot) {
		this.currentShot = currentShot;
	}

	public Frame getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(Frame currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Game getCurrentGame() {
		return currentGame;
	}	

	
}