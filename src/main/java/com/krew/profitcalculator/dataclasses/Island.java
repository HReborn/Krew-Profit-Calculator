package com.krew.profitcalculator.dataclasses;

import java.util.Map;

public class Island {
	
	private Double[] coordinatesXY = new Double[2];
	private Map<String, Cargo> cargoInfo;
	
	public Island(Double[] coordinatesXY, Map<String, Cargo> cargoInfo) {
		this.coordinatesXY = coordinatesXY;
		this.cargoInfo = cargoInfo;
	}

	public Double[] getCoordinatesXY() {
		return coordinatesXY;
	}
	public Map<String, Cargo> getCargoInfo() {
		return cargoInfo;
	}
	
}
