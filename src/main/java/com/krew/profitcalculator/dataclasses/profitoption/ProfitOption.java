package com.krew.profitcalculator.dataclasses.profitoption;

public class ProfitOption {
	
	// maybe transform into 3 different classes
	// header, simple and detailed and each one with
	// it's tostring method like:
	
	private ProfitOptionHeader header;
	private ProfitOptionSimple simple;
	private ProfitOptionDetailed detailed;
	
	// the to string from this class would be
	// return header.toString + simple.toString + detailed.toString
	// if any of those is null, the tostring would return an empty string.
	
	// maybe use inheritance? the three classes would extend ProfitOption
	
	// maybe linked inheritance since simple can't exist without header and
	// detailed can't exist without simple and header.
	
	// 
	
	// level of detail to be shown
	private enum levelOfDetail {HEADER, SIMPLE, DETAILED};
	
	// header unique identifyier
	private String currentIsland; 
	private String cargoName; 
	private String destinationIsland;
	private String boatName;
	
	// most important info only
	private double timeSpent;
	private int profit;
	private int profitPerSec; 
	
	// full detail info if the user so wishes
	private int maxStorage;
	private double distance;
	private int speed;
	
	private int buyPrice;
	private int sellPrice;
	private int unitSize;
	
	private int totalSpent;
	private int totalSold;
	private int unitsBought;
	
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