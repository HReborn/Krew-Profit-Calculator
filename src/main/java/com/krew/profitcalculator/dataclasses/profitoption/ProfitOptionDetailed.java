package com.krew.profitcalculator.dataclasses.profitoption;

public class ProfitOptionDetailed {
	private int maxStorage;
	private double distance;
	private double speed;
	
	private int buyPrice;
	private int sellPrice;
	private int unitSize;
	
	private int totalSpent;
	private int totalSold;
	private int unitsBought;
	
	public int getMaxStorage() {
		return maxStorage;
	}
	public double getDistance() {
		return distance;
	}
	public double getSpeed() {
		return speed;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public int getUnitSize() {
		return unitSize;
	}
	public int getTotalSpent() {
		return totalSpent;
	}
	public int getTotalSold() {
		return totalSold;
	}
	public int getUnitsBought() {
		return unitsBought;
	}
	public void setMaxStorage(int maxStorage) {
		this.maxStorage = maxStorage;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public void setUnitSize(int unitSize) {
		this.unitSize = unitSize;
	}
	public void setTotalSpent(int totalSpent) {
		this.totalSpent = totalSpent;
	}
	public void setTotalSold(int totalSold) {
		this.totalSold = totalSold;
	}
	public void setUnitsBought(int unitsBought) {
		this.unitsBought = unitsBought;
	}
	
	@Override
	public String toString() {
		String totalSpent = String.valueOf(this.totalSpent);
		// format profit to 15k or keep value if less than 1000
		if (this.totalSpent > 1000) {
			totalSpent = String.valueOf(this.totalSpent/1000) + "k";
		}
		String totalSold = String.valueOf(this.totalSold);
		// format profit to 15k or keep value if less than 1000
		if (this.totalSold > 1000) {
			totalSold = String.valueOf(this.totalSold/1000) + "k";
		}
		
		return  
				"\n Ship's max storage: " + maxStorage + " slots" +
				"\n       Ship's speed: " + speed + " in game speed units" +
				"\n    Travel distance: " + Math.round(distance) + "m" + //it's pixels, but let it be meters to facilitate
				"\n    Cargo buy price: $" + buyPrice + "/unit" +
				"\n   Cargo sell price: $" + sellPrice + "/unit" +
				"\n    Cargo unit size: " + unitSize + " slots/unit" +
				"\n        Total spent: $" + totalSpent + 
				"\n         Total sold: $" + totalSold + 
				"\n       Units bought: " + unitsBought + " units\n";
	}
}
