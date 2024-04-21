package edu.ycp.cs320.RevMetrix.model;

public class Ball {
	private String name, color, brand;
	private boolean rightHanded;
	private float weight; 
	private int ballId;
	private int accountId;
	
	
	 public Ball(String name) { 
		 this.name = name; 
		 }
	 
	public Ball(int accountId, float weight, String name, boolean rightHand, String brand, String color) {
		//this.ballId = ballID;
		this.accountId = accountId;
		this.name = name;
		this.rightHanded = rightHand;
		this.color = color;
		this.weight = weight;
		this.brand = brand;
	}
	public Ball() {
		// TODO Auto-generated constructor stub
	}

	public void setAccountId(int accId) {
		accountId = accId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public Integer getBallId() {
		return ballId;
	}
	public void setBallId(int ballId) {
		this.ballId = ballId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String nBrand) {
		this.brand=nBrand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color =color;
	}
	public boolean getRightHanded() {
		return rightHanded;
	}
	public void setRightHanded(boolean newVal) {
		this.rightHanded= newVal;
	}
	public float getWeight() {
		return this.weight;
	}
	public void setWeight(float newWeight) {
		this.weight=newWeight;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
