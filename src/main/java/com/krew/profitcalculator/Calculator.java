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
import com.krew.profitcalculator.dataclasses.Ship;
import com.krew.profitcalculator.dataclasses.profitoption.ProfitOption;

public class Calculator {
	GameData data;
	Map<String, Ship> shipPropertiesInfo;
	Map<String, Island> islandCargoPriceDataTable;
	
	public Calculator() {
		this.data = new GameData();
		this.shipPropertiesInfo = data.getShipPropertiesInfo();
		this.islandCargoPriceDataTable = data.getIslandCargoPriceDataTable();
	}
	
	public List<ProfitOption> calculateListOfProfitOptions(String islandName, String shipName, int... displayNumber) {
		// uses varargs to set up optional parameter and i use another variable to discard the array
		int displayNchoices = 3;
		if (displayNumber.length > 0) {
			displayNchoices = displayNumber[0];
		}
		
		List<ProfitOption> profitOptions = new ArrayList<>();
		Island islandInfo = data.getIslandCargoPriceDataTable().get(islandName);
		Set<String> cargoList = islandInfo.getCargoInfo().keySet();
		Set<String> islandList = data.getIslandCargoPriceDataTable().keySet();
		// TODO: find if there's a way to transform this into a stream
		for (String cargo : cargoList) {
			int cargoBuyPrice = islandInfo.getCargoInfo().get(cargo).getPrice();
			for (String sellIsland : islandList) {
				int cargoSellPrice = data.getIslandCargoPriceDataTable().get(sellIsland).getCargoInfo().get(cargo).getPrice();
				if (cargoSellPrice > cargoBuyPrice) {
					ProfitOption profitOption = profitOptionCalculator(islandName, sellIsland, shipName, cargo);
					profitOptions.add(profitOption);
				}
			}
		}
		return sortAndLimit(profitOptions, displayNchoices);
	}
	
	public List<ProfitOption> calculateListOfProfitOptions(String islandName, String sellIsland, String shipName, int... displayNumber) {
		// TODO: find if there's a way to optimize this and avoid this much of code duplication
		// this overloading answers the question "is there any way to profit from A to B?"
		
		// uses varargs to set up optional parameter and i use another variable to discard the array
		int displayNchoices = 3;
		if (displayNumber.length > 0) {
			displayNchoices = displayNumber[0];
		}
		
		List<ProfitOption> profitOptions = new ArrayList<>();
		Island islandInfo = data.getIslandCargoPriceDataTable().get(islandName);
		Set<String> cargoList = islandInfo.getCargoInfo().keySet();
		// TODO: find if there's a way to transform this into a stream
		for (String cargo : cargoList) {
			int cargoBuyPrice = islandInfo.getCargoInfo().get(cargo).getPrice();
			int cargoSellPrice = data.getIslandCargoPriceDataTable().get(sellIsland).getCargoInfo().get(cargo).getPrice();
			if (cargoSellPrice > cargoBuyPrice) {
				ProfitOption profitOption = profitOptionCalculator(islandName, sellIsland, shipName, cargo);
				profitOptions.add(profitOption);
			}
		}
		return sortAndLimit(profitOptions, displayNchoices);
	}
	
	private List<ProfitOption> sortAndLimit(List<ProfitOption> profitOptions, int displayNchoices) {
		profitOptions = profitOptions.stream()
				.sorted(Comparator
						.comparing(ProfitOption::getProfitPerSec).reversed()
						.thenComparing(Comparator.comparing(ProfitOption::getProfit).reversed()))
				.limit(displayNchoices)
				.collect(Collectors.toList());
		return profitOptions;
	}
	
	
	public List<ProfitOption> calculateListOfProfitOptions(String currentIsland, int... displayNumber) {
		
		// this method answer the question "if i wanna pvp and i'm in island A, from which
		// islands ppl will be coming so that i take the route to intercept them?
		// this method will calculate from the most profitable route from all islands to
		// island A and the most profitable route from all the islands will be the 
		// best route to attack because that route will have a higher chance of encountering
		// lucrative traders.
		
		// obs: this option only affects how many possible attack routes the method will show from each
		// island to the current island there can be only one profit option bc the objective is just to
		// find the top profit option from all islands to the current island and rank those options
		// so that the most profitable options will be the most problable routes traders are gonna take
		// so those are the routes i'm gonna intercept
		int displayNchoices = 3;
		if (displayNumber.length > 0) {
			displayNchoices = displayNumber[0];
		}
		
		String defaultShip = "trader 1"; // most common profit target. this is the goal.
		Set<String> islandList = data.getIslandCargoPriceDataTable().keySet();
		List<ProfitOption> routesToAttack = new ArrayList<>();
		for (String targetIsland : islandList) {
			routesToAttack.addAll(calculateListOfProfitOptions(targetIsland, currentIsland, defaultShip, 1));
		}
		
		return sortAndLimit(routesToAttack, displayNchoices);
	}
	
	
	public ProfitOption profitOptionCalculator(String buyIsland, String sellIsland, String shipName, String cargoName) {
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
		// there in 4 minutes 32 seconds. if the distance between spain to malaysia is aprox 272, then the speed pixel/second
		// is d/time(s), d/272 = speed(pixel/second), to make the conversion factor, i'll need to use the rule of three
		// if 6.5 speed is d/272 (pixel/second), then 1 speed is x.
		// putting into a conversion factor, x = 1/6.5;, so, we just gotta divide the speed by 6.5 and we'll have
		// an estimate about how much time it'll take
		
		Double[] startingCoordinate = data.getIslandCargoPriceDataTable().get(startingIsland).getCoordinatesXY();
		Double[] destinationCoordinate = data.getIslandCargoPriceDataTable().get(destinationIsland).getCoordinatesXY();
		
		// formula to distance between two points in a cartesian plane
		double distanceBetweenIslands = sqrt(pow(destinationCoordinate[0] - startingCoordinate[0], 2) + pow(destinationCoordinate[1] - startingCoordinate[1], 2));
		
		//dividing by 6.5 to get the speed in pixel/second by rule of three with distance in pixels (empirically measured)
		double boatSpeed = (data.getShipPropertiesInfo().get(shipName).getSpeed())/6.5;
		double time = distanceBetweenIslands/boatSpeed;
		
		return time;
	}
}