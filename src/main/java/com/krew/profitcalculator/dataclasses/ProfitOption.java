package com.krew.profitcalculator.dataclasses;

public class ProfitOption {
	private String cargoName;
	private String destinationIsland;
	private double timeSpent;
	private int profit;
	private double profitPerSec = profit/timeSpent;
	
	public ProfitOption(String cargoName, String destinationIsland, double timeSpent, int profit) {
		this.cargoName = cargoName;
		this.destinationIsland = destinationIsland;
		this.timeSpent = timeSpent;
		this.profit = profit;
	}

	public String getCargoName() {
		return cargoName;
	}
	public String getDestinationIsland() {
		return destinationIsland;
	}
	public double getTimeSpent() {
		return timeSpent;
	}
	public int getProfit() {
		return profit;
	}
	public double getProfitPerSec() {
		return profitPerSec;
	}
	
}
