package com.krew.profitcalculator.apiconsultor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krew.profitcalculator.dataclasses.Cargo;
import com.krew.profitcalculator.dataclasses.Island;
import com.krew.profitcalculator.dataclasses.Ship;

public class DataManager {
	private DataExtractor extractor;
	
	public DataManager() {
		this.extractor = new DataExtractor();
	}
	
	public Map<String, Island> buildIslandCargoPriceDataTable() {
		
		Map<String, Double[]> islandCoordinates = extractor.getAllIslandsCoordinates();
		Map<String, Integer> cargoSizes = extractor.getAllCargoSizes();
		Map<String, Map<String, Integer>> priceTable = extractor.getPriceTable();
		
		Set<String> cargoNames = cargoSizes.keySet();
		Set<String> islandNames = islandCoordinates.keySet();
		
		Map<String, Island> islandCargoPriceDataTable = new HashMap<>();
		for (String islandName : islandNames) {
			Map<String, Cargo> cargoInfos = new HashMap<>();
			for (String cargoName : cargoNames) {
				int cargoPrice = priceTable.get(islandName).get(cargoName);
				int cargoSize = cargoSizes.get(cargoName);
				Cargo cargoInfo = new Cargo(cargoPrice, cargoSize);
				cargoInfos.put(cargoName, cargoInfo);
			}
			Double[] coordinatesXY = islandCoordinates.get(islandName);
			Island islandInfo = new Island(coordinatesXY, cargoInfos);
			islandCargoPriceDataTable.put(islandName, islandInfo);
		}
		return islandCargoPriceDataTable;
	}
	
	public Map<String, Ship> buildShipPropertiesInfo() {
		Map<String, Map<String, Object>> shipPropertiesInfoTable = extractor.getShipPropertiesInfoTable();
		Set<String> shipNames = shipPropertiesInfoTable.keySet();
		Map<String, Ship> shipPropertiesInfo = new HashMap<>();
		for (String shipName : shipNames) {
			Map<String, Object> properties = shipPropertiesInfoTable.get(shipName);
			@SuppressWarnings("unchecked")
			Ship shipProperties = new Ship(
					(String) properties.get("name"), 
					(String) properties.get("type"), 
					(int) properties.get("price"), 
					(List<String>) properties.get("availableAt"), 
					(List<String>) properties.get("unavailableAt"), 
					(int) properties.get("hp"), 
					(double) properties.get("turnSpeed"), 
					(double) properties.get("speed"), 
					(int) properties.get("maxCrewSize"), 
					(int) properties.get("maxCargoSize"), 
					(int) properties.get("regeneration"));
			shipPropertiesInfo.put(shipName, shipProperties);
					
		}
		return null;
	}
}
