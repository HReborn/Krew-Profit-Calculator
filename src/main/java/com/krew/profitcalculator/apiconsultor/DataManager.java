package com.krew.profitcalculator.apiconsultor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krew.profitcalculator.dataclasses.Cargo;
import com.krew.profitcalculator.dataclasses.Island;
import com.krew.profitcalculator.dataclasses.Ship;

public class DataManager {
	private DataExtractor extractor;
	private Map<String, String> islandCoordinates;
	private Map<String, String> cargoSizes;
	private Map<String, Map<String, String>> priceTable;
	private Map<String, Map<String, String>> shipPropertiesInfoTable;
	
	public DataManager() {
		this.extractor = new DataExtractor();
		islandCoordinates = extractor.getAllIslandsCoordinates();
		cargoSizes = extractor.getAllCargoSizes();
		priceTable = extractor.getPriceTable();
		shipPropertiesInfoTable = extractor.getShipPropertiesInfoTable();
	}
	
	public Map<String, Island> buildIslandCargoPriceDataTable() {
		
		Set<String> cargoNames = cargoSizes.keySet();
		Set<String> islandNames = islandCoordinates.keySet();
		
		Map<String, Island> islandCargoPriceDataTable = new HashMap<>();
		for (String islandName : islandNames) {
			Map<String, Cargo> cargoInfos = new HashMap<>();
			for (String cargoName : cargoNames) {
				Integer cargoPrice = Integer.valueOf(priceTable.get(islandName).get(cargoName));
				Integer cargoSize = Integer.valueOf(cargoSizes.get(cargoName));
				Cargo cargoInfo = new Cargo(cargoPrice, cargoSize);
				cargoInfos.put(cargoName, cargoInfo);
			}
			String[] coordinatesxy = islandCoordinates.get(islandName).split(" ");
			Double[] coordinatesXY = new Double[2];  
			coordinatesXY[0] = Double.valueOf(coordinatesxy[0]);
			coordinatesXY[1] = Double.valueOf(coordinatesxy[1]);
			Island islandInfo = new Island(coordinatesXY, cargoInfos);
			islandCargoPriceDataTable.put(islandName, islandInfo);
		}
		return islandCargoPriceDataTable;
	}
	
	public Map<String, Ship> buildShipPropertiesInfo() {
		
		Set<String> shipNames = shipPropertiesInfoTable.keySet();
		Map<String, Ship> shipPropertiesInfo = new HashMap<>();
		for (String shipName : shipNames) {
			Map<String, String> properties = shipPropertiesInfoTable.get(shipName);
			String[] availableat = properties.get("islands").split(" ");
			List<String> availableAt = Arrays.asList(availableat);
			List<String> unavailableAt = new ArrayList<>();
			Set<String> islandNames = islandCoordinates.keySet();
			for (String islandName : islandNames) {
				if (!availableAt.contains(islandName)) {
					unavailableAt.add(islandName);
				}
			}

			Ship shipProperties = new Ship(
					shipName, 
					properties.get("type"), 
					Integer.valueOf(properties.get("price")), 
					availableAt, 
					unavailableAt, 
					Integer.valueOf(properties.get("hp")), 
					Double.valueOf(properties.get("turnSpeed")), 
					Double.valueOf(properties.get("speed")), 
					Integer.valueOf(properties.get("crewLimit")), 
					Integer.valueOf(properties.get("storageSize")), 
					Integer.valueOf(properties.get("regeneration")));
			shipPropertiesInfo.put(shipName, shipProperties);
					
		}
		return shipPropertiesInfo;
	}
}
