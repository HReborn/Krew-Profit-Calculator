package com.krew.profitcalculator.dataclasses;

public class ProfitOption {
	private String cargoName;
	private String destinationIsland;
	private double timeSpent;
	private int profit;
	private int profitPerSec;
	
	public ProfitOption(String cargoName, String destinationIsland, double timeSpent, int profit) {
		this.cargoName = cargoName;
		this.destinationIsland = destinationIsland;
		this.timeSpent = timeSpent;
		this.profit = profit;
		this.profitPerSec = (int) Math.round(Double.valueOf(profit)/timeSpent);
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
		
		String profit = String.valueOf(this.profit);
		// format profit to 15k or keep value if less than 1000
		if (this.profit > 1000) {
			profit = String.valueOf(this.profit/1000) + "k";
		} 
		
		// format time from double seconds to string minutes and seconds
		String timeSpent = String.valueOf(((int) this.timeSpent/60) + "m" + Math.round(this.timeSpent%60) + "s");
		
		// capitalize destination island and cargo name
		String destinationIsland = this.destinationIsland.substring(0, 1).toUpperCase() + this.destinationIsland.substring(1);
		String cargoName = this.cargoName.substring(0, 1).toUpperCase() + this.cargoName.substring(1);
		
		return "\nDestination Island: " + destinationIsland +
			   "\n             Cargo: " + cargoName +
			   "\n Profit To Be Made: " + "$" + profit +
			   "\n       Travel Time: " + timeSpent + 
			   "\n Profit per Second: " + "$" + profitPerSec + "/s" + "\n";
	}
}