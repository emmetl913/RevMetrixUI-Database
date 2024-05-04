package edu.ycp.cs320.RevMetrix.model;

public class stat {
	String date, league, lanes;
	int series, totalGame;
	double avg;
	Integer games[];
	
	public stat() {
		
	}
	
	public stat(String date, String league, String lanes, Integer games[], int series, int totalGame, double avg) {
		this.date = date;
		this.league = league;
		this.lanes = lanes;
		this.series = series;
		this.totalGame = totalGame;
		this.avg = avg;
		this.games = games;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setLeague(String league) {
		this.league = league;
	}
	
	public String getLeague() {
		return league;
	}
	
	public void setLanes(String lanes) {
		this.lanes = lanes;
	}
	
	public String getLanes() {
		return lanes;
	}
	
	public void setSeries(int series) {
		this.series = series;
	}
	
	public int getSeries() {
		return series;
	}
	
	public void setTotalGame(int totalGame) {
		this.totalGame = totalGame;
	}
	
	public int getTotalGame() {
		return totalGame;
	}
	
	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	public double getAvg() {
		return avg;
	}
	
	public void setGames(Integer games[]) {
		this.games = games;
	}
	
	public Integer[] getGames() {
		return games;
	}
	
	public int getGames1() {
		return games[0];
	}
	
	public int getGames2() {
		return games[1];

	}
	
	public int getGames3() {
		return games[2];

	}
	
}
	
