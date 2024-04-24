package edu.ycp.cs320.RevMetrix.model;

public class Ball {
	private String name, color1, color2, color3, brand, material;
	private boolean rightHanded;
	private float weight; 
	private int ballId;
	private int accountId;
	
	
	 public Ball(String name) { 
		 this.name = name; 
		 }
	 
	public Ball(int accountId, float weight, String name, boolean rightHand, String brand, String color1, String color2, String color3, String material) {
		//this.ballId = ballID;
		this.accountId = accountId;
		this.name = name;
		this.rightHanded = rightHand;
		this.color1 = color1;
		this.weight = weight;
		this.brand = brand;
		this.color2 = color2;
		this.color3 = color3;
		this.material = material;
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
	public String getColor1() {
		return color1;
	}
	public void setColor1(String color) {
		this.color1 =color;
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

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	public String getColor3() {
		return color3;
	}

	public void setColor3(String color3) {
		this.color3 = color3;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
}
