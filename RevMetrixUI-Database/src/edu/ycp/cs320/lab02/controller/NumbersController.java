package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.Numbers;

public class NumbersController {
	private Numbers model;
	
	public void setModel(Numbers model) {
		this.model = model;
	}
	
	public void setDoubles(double first, double second, double third) {
		model.setFirst(first);
		model.setSecond(second);
		model.setThird(third);
	}
	
	public Double add() {
		return model.getFirst() + model.getSecond() + model.getThird();
	}
	
	public Double mult() {
		return model.getFirst() * model.getSecond();
	}
	
}
