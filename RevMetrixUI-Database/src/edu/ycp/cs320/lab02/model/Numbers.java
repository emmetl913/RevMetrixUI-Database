package edu.ycp.cs320.lab02.model;


public class Numbers {
	private double first, second, third;
	private String Sfirst, Ssecond, Sthird;

	
	public Numbers(String first, String second) {
		this.Sfirst = first;
		this.Ssecond = second;
	}
	
public Numbers(String first, String second, String third) {
		this.Sfirst = first;
		this.Ssecond = second;
		this.Sthird = third;
	}
	
	public String getStringFirst() {
		return Sfirst;
	}
	
	public String getStringSecond() {
		return Ssecond;
	}
	
	public String getStringThird() {
		return Sthird;
	}
	
	public void setFirst(double first) {
		this.first = first;
	}
	
	public double getFirst() {
		return first;
	}
	
	public void setSecond(double second) {
		this.second = second;
	}
	
	public double getSecond() {
		return second;
	}
	public void setThird(double third) {
		this.third = third;
	}
	
	public double getThird() {
		return third;
	}
	
	public double getResultAdd() {
		return first+second+third;
	}
	
	public double getResultMultiply() {
		return first*second;
	}
	
	
}