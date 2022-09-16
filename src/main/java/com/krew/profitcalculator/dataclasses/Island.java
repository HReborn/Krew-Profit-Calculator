package com.krew.profitcalculator.dataclasses;

import java.util.Map;

public class Island {
	
	private String islandName;
	private Double[] coordinatesXY = new Double[2];
	private Map<String, Cargo> islandCargoPrices;
	
	public Island(Double[] coordinatesXY, Map<String, Cargo> islandCargoPrices, String islandName) {
		this.coordinatesXY = coordinatesXY;
		this.islandCargoPrices = islandCargoPrices;
		this.islandName = islandName;
	}

	public Double[] getCoordinatesXY() {
		return coordinatesXY;
	}
	public Map<String, Cargo> getIslandCargoPrices() {
		return islandCargoPrices;
	}
	public Cargo getCargo(String cargoName) {
		return islandCargoPrices.get(cargoName);
	}
	public String getIslandName() {
		return islandName;
	}
	
}
