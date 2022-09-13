package com.krew.profitcalculator;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.krew.profitcalculator.apiconsultor.GameData;
import com.krew.profitcalculator.dataclasses.Cargo;
import com.krew.profitcalculator.dataclasses.Island;
import com.krew.profitcalculator.dataclasses.ProfitOption;
import com.krew.profitcalculator.dataclasses.Ship;

public class Calculator {
	GameData data;
	Map<String, Ship> shipPropertiesInfo;
	Map<String, Island> islandCargoPriceDataTable;
	
	public Calculator() {
		this.data = new GameData();
		this.shipPropertiesInfo = data.getShipPropertiesInfo();
		this.islandCargoPriceDataTable = data.getIslandCargoPriceDataTable();
	}
	
	public List<ProfitOption> calculateProfitOptionsOfIslandWithCurrentBoat(String islandName, String shipName) {
		List<ProfitOption> profitOptions = new ArrayList<>();
		Island islandInfo = data.getIslandCargoPriceDataTable().get(islandName);
		Set<String> cargoList = islandInfo.getCargoInfo().keySet();
		Set<String> islandList = data.getIslandCargoPriceDataTable().keySet();
		for (String cargo : cargoList) {
			int cargoBuyPrice = islandInfo.getCargoInfo().get(cargo).getPrice();
			for (String sellIsland : islandList) {
				int cargoSellPrice = data.getIslandCargoPriceDataTable().get(sellIsland).getCargoInfo().get(cargo).getPrice();
				if (cargoSellPrice > cargoBuyPrice) {
					ProfitOption profitOption = profitOptionCalculator(shipName, cargo, islandName, sellIsland);
					profitOptions.add(profitOption);
				}
			}
		}
		profitOptions = profitOptions.stream()
				.sorted(Comparator.comparing(ProfitOption::getProfit).thenComparing(Comparator.comparing(ProfitOption::getProfitPerSec)))
				.collect(Collectors.toList());
		return profitOptions;
	}
	
	public ProfitOption profitOptionCalculator(String shipName, String cargoName, String buyIsland, String sellIsland) {
		int maxBoatCapacity = shipPropertiesInfo.get(shipName).getMaxCargoSize();
		Cargo cargoBuyInfo = islandCargoPriceDataTable.get(buyIsland).getCargoInfo().get(cargoName);
		Cargo cargoSellInfo = islandCargoPriceDataTable.get(sellIsland).getCargoInfo().get(cargoName);
		
		int cargoSize = cargoBuyInfo.getSize();
		int buyPrice = cargoBuyInfo.getPrice();
		int sellPrice = cargoSellInfo.getPrice();
		
		int cargoQtty = maxBoatCapacity/cargoSize;
		int profit = cargoQtty*(sellPrice - buyPrice);
		double time = travelTimeCalculator(shipName, buyIsland, sellIsland);
		return new ProfitOption(cargoName, sellIsland, time, profit);
	}
	
	private double travelTimeCalculator(String shipName, String startingIsland, String destinationIsland) {
		// i timed the trip from spain to malaysia straight line with raft 1 with 6.5 speed and i got
		// there in 4 minutes 30 seconds
		
		Double[] startingCoordinate = data.getIslandCargoPriceDataTable().get(startingIsland).getCoordinatesXY();
		Double[] destinationCoordinate = data.getIslandCargoPriceDataTable().get(destinationIsland).getCoordinatesXY();
		
		// formula to distance between two points in a cartesian plane
		double distanceBetweenIslands = sqrt(pow(destinationCoordinate[0] - startingCoordinate[0], 2) + pow(destinationCoordinate[1] - startingCoordinate[1], 2));
		
		double boatSpeed = data.getShipPropertiesInfo().get(shipName).getSpeed();
		double time = distanceBetweenIslands/boatSpeed;
		
		return time;
	}
}