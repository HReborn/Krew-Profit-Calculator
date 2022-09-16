package com.krew.profitcalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.krew.profitcalculator.apiconsultor.GameData;
import com.krew.profitcalculator.dataclasses.Island;
import com.krew.profitcalculator.dataclasses.Ship;
import com.krew.profitcalculator.dataclasses.profitoption.LevelOfDetail;
import com.krew.profitcalculator.dataclasses.profitoption.ProfitOption;

public class Calculator {
	
	private GameData data;
	private Map<String, Ship> shipPropertiesInfo;
	private Map<String, Island> islandCargoPriceDataTable;
	private LevelOfDetail levelOfDetail;
	
	public Calculator() {
		this.levelOfDetail = LevelOfDetail.CORE;
		this.data = new GameData();
		this.shipPropertiesInfo = data.getShipPropertiesInfo();
		this.islandCargoPriceDataTable = data.getIslandCargoPriceDataTable();
	}
	
	public Calculator(LevelOfDetail levelOfDetail) {
		this();
		this.levelOfDetail = levelOfDetail;
	}
	
	public List<ProfitOption> calculateSellRoutesFromCurrentIsland(String buyIslandName, String shipName, int... displayNumber) {
		// uses varargs to set up optional parameter and i use another variable to discard the array
		int displayNchoices = 3;
		if (displayNumber.length > 0) {
			displayNchoices = displayNumber[0];
		}
		// TODO: find if there's a way to transform this into a stream
		List<ProfitOption> profitOptions = new ArrayList<>();
		Island buyIsland = islandCargoPriceDataTable.get(buyIslandName);
		Collection<Island> sellIslandList = islandCargoPriceDataTable.values();
		Ship ship = shipPropertiesInfo.get(shipName);

		Set<String> cargoList = buyIsland.getIslandCargoPrices().keySet();
		for (String cargoName : cargoList) {
			int cargoBuyPrice = buyIsland.getIslandCargoPrices().get(cargoName).getPrice();
			for (Island sellIsland : sellIslandList) {
				int cargoSellPrice = sellIsland.getIslandCargoPrices().get(cargoName).getPrice();
				if (cargoSellPrice > cargoBuyPrice) {
					profitOptions.add(new ProfitOption(
							buyIsland, cargoName, sellIsland, ship, levelOfDetail));
				}
			}
		}
		return sortAndLimit(profitOptions, displayNchoices);
	}
	
	public List<ProfitOption> calculateBestCargosFromCurrentToTargetIsland(String buyIslandName, String sellIslandName, String shipName, int... displayNumber) {
		// TODO: find if there's a way to optimize this and avoid this much of code duplication
		// this overloading answers the question "is there any way to profit from A to B?"
		
		// uses varargs to set up optional parameter and i use another variable to discard the array
		int displayNchoices = 3;
		if (displayNumber.length > 0) {
			displayNchoices = displayNumber[0];
		}
		
		List<ProfitOption> profitOptions = new ArrayList<>();
		Island buyIsland = islandCargoPriceDataTable.get(buyIslandName);
		Island sellIsland = islandCargoPriceDataTable.get(sellIslandName);
		Ship ship = shipPropertiesInfo.get(shipName);
		Set<String> cargoList = buyIsland.getIslandCargoPrices().keySet();
		// TODO: find if there's a way to transform this into a stream
		for (String cargoName : cargoList) {
			int cargoBuyPrice = buyIsland.getCargo(cargoName).getPrice();
			int cargoSellPrice = sellIsland.getCargo(cargoName).getPrice();
			if (cargoSellPrice > cargoBuyPrice) {
				profitOptions.add(
						new ProfitOption(buyIsland, cargoName, sellIsland, ship, levelOfDetail));
			}
		}
		return sortAndLimit(profitOptions, displayNchoices);
	}
	
	public List<ProfitOption> calculateRoutesToAttackFromCurrentIsland(String currentIslandName, int... displayNumber) {
		
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
		Set<String> islandNameList = islandCargoPriceDataTable.keySet();
		List<ProfitOption> routesToAttack = new ArrayList<>();
		for (String targetIslandName : islandNameList) {
			routesToAttack.addAll(calculateBestCargosFromCurrentToTargetIsland(targetIslandName, currentIslandName, defaultShip, 1));
		}
		
		return sortAndLimit(routesToAttack, displayNchoices);
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
}