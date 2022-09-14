package com.krew.profitcalculator.dataclasses;

import java.text.DecimalFormat;

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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String profit = String.valueOf(this.profit);
		// format profit to 15k or keep value if less than 1000
		if (this.profit > 1000) {
			profit = String.valueOf(this.profit/100) + "k";
		} 
		
		// format travel time and profit per second to two decimal spots
		DecimalFormat formatter = new DecimalFormat("#.00");
		
		// capitalize destination island and cargo name
		String destinationIsland = this.destinationIsland.substring(0, 1).toUpperCase() + this.destinationIsland.substring(1);
		String cargoName = this.cargoName.substring(0, 1).toUpperCase() + this.cargoName.substring(1);
		
		return "\nDestination Island: " + destinationIsland +
			   "\n             Cargo: " + cargoName +
			   "\n Profit To Be Made: " + "$" + profit +
			   "\n       Travel Time: " + formatter.format(timeSpent) + 
			   "\n Profit per Second: " + "$" + formatter.format(profitPerSec) + "/s" + "\n";
	}
}
