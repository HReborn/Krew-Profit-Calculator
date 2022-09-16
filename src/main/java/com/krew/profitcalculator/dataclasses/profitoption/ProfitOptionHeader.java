package com.krew.profitcalculator.dataclasses.profitoption;

import com.krew.profitcalculator.dataclasses.Island;
import com.krew.profitcalculator.dataclasses.Ship;

public class ProfitOptionHeader {
	private Island buyIsland; 
	private String cargoName; 
	private Island sellIsland;
	private Ship ship;
	
	public Island getBuyIsland() {
		return buyIsland;
	}
	public String getCargoName() {
		return cargoName;
	}
	public Island getSellIsland() {
		return sellIsland;
	}
	public Ship getShip() {
		return ship;
	}
	public void setBuyIsland(Island buyIsland) {
		this.buyIsland = buyIsland;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public void setSellIsland(Island sellIsland) {
		this.sellIsland = sellIsland;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	public ProfitOptionHeader(Island buyIsland, String cargoName, Island sellIsland, Ship ship) {
		super();
		this.buyIsland = buyIsland;
		this.cargoName = cargoName;
		this.sellIsland = sellIsland;
		this.ship = ship;
	}
	
	@Override
	public String toString() {
		// capitalize ship, island and cargo name
		String sellIslandName = sellIsland.getIslandName().substring(0, 1).toUpperCase() + sellIsland.getIslandName().substring(1);
		String buyIslandName = buyIsland.getIslandName().substring(0, 1).toUpperCase() + buyIsland.getIslandName().substring(1);
		String cargoName = this.cargoName.substring(0, 1).toUpperCase() + this.cargoName.substring(1);
		String shipName = ship.getName().substring(0, 1).toUpperCase() + ship.getName().substring(1);
		
		return  "\n" + buyIslandName + "(" + cargoName + ")" + " -> " + sellIslandName + 
				"\n" + shipName;
	}
}
