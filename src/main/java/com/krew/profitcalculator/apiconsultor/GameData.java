package com.krew.profitcalculator.apiconsultor;

import java.util.Map;

import com.krew.profitcalculator.dataclasses.Island;
import com.krew.profitcalculator.dataclasses.Ship;

public class GameData {
	private Map<String, Island> islandCargoPriceDataTable;
	private Map<String, Ship> shipPropertiesInfo;
	private DataManager manager;
	
	public GameData() {
		this.manager = new DataManager();
		this.islandCargoPriceDataTable = manager.buildIslandCargoPriceDataTable();
		this.shipPropertiesInfo = manager.buildShipPropertiesInfo();
	}

	public Map<String, Island> getIslandCargoPriceDataTable() {
		return islandCargoPriceDataTable;
	}
	public Map<String, Ship> getShipPropertiesInfo() {
		return shipPropertiesInfo;
	}
}
