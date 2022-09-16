package com.krew.profitcalculator.dataclasses.profitoption;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import com.krew.profitcalculator.dataclasses.Cargo;
import com.krew.profitcalculator.dataclasses.Island;
import com.krew.profitcalculator.dataclasses.Ship;

public class ProfitOption {
	
	private LevelOfDetail levelOfDetail;
	
	private ProfitOptionHeader header;
	private ProfitOptionCore core;
	private ProfitOptionDetailed detailed;
	
	public int getProfitPerSec() {
		return core.getProfitPerSec();
	}
	
	public int getProfit() {
		return core.getProfit();
	}
	
	public LevelOfDetail getLevelOfDetail() {
		return levelOfDetail;
	}

	public ProfitOptionHeader getHeader() {
		return header;
	}

	public ProfitOptionCore getCore() {
		return core;
	}

	public ProfitOptionDetailed getDetailed() {
		return detailed;
	}

	public ProfitOption(Island buyIsland, String cargoName, Island sellIsland, Ship ship, LevelOfDetail levelOfDetail) {
		this.levelOfDetail = levelOfDetail;
		
		header = new ProfitOptionHeader(buyIsland, cargoName, sellIsland, ship);
		
		detailed = new ProfitOptionDetailed();
		detailed.setMaxStorage(ship.getMaxCargoSize());
		detailed.setSpeed(ship.getSpeed());
		detailed.setBuyPrice(buyIsland.getIslandCargoPrices().get(cargoName).getPrice());
		detailed.setSellPrice(sellIsland.getIslandCargoPrices().get(cargoName).getPrice());
		detailed.setSellPrice(sellIsland.getIslandCargoPrices().get(cargoName).getSize());
		
		// the detailed.totalspent,sold and bought are setted insie calculateProfit
		int profit = calculateProfit();
		// the detailed.setDistance() is setted inside calculateTravelTime
		double travelTime = calculateTravelTime();
		core = new ProfitOptionCore(profit, travelTime);
	}

	private int calculateProfit() {
		int maxBoatCapacity = header.getShip().getMaxCargoSize();
		String cargoName = header.getCargoName();
		Cargo cargoBuyPrice = header.getBuyIsland().getIslandCargoPrices().get(cargoName);
		Cargo cargoSellPrice = header.getSellIsland().getIslandCargoPrices().get(cargoName);
		
		int cargoSize = cargoBuyPrice.getSize();
		int buyPrice = cargoBuyPrice.getPrice();
		int sellPrice = cargoSellPrice.getPrice();
		
		int cargoQtty = maxBoatCapacity/cargoSize;
		
		detailed.setUnitSize(cargoSize);
		detailed.setTotalSpent(buyPrice*cargoQtty);
		detailed.setTotalSold(sellPrice*cargoQtty);
		detailed.setUnitsBought(cargoQtty);
		
		int profit = cargoQtty*(sellPrice - buyPrice);
		return profit;
	}
	
	private double calculateTravelTime() {
		// i timed the trip from spain to malaysia straight line with raft 1 with 6.5 speed and i got
		// there in 4 minutes 32 seconds. if the distance between spain to malaysia is aprox 272, then the speed pixel/second
		// is d/time(s), d/272 = speed(pixel/second), to make the conversion factor, i'll need to use the rule of three
		// if 6.5 speed is d/272 (pixel/second), then 1 speed is x.
		// putting into a conversion factor, x = 1/6.5;, so, we just gotta divide the speed by 6.5 and we'll have
		// an estimate about how much time it'll take
		
		double distanceBetweenIslands = calculateDistance();
		detailed.setDistance(distanceBetweenIslands);
		//dividing by 6.5 to get the speed in pixel/second by rule of three with distance in pixels (empirically measured)
		double boatSpeed = (header.getShip().getSpeed())/6.5;
		double time = distanceBetweenIslands/boatSpeed;
		
		return time;
	}
	
	private double calculateDistance() {
		Double[] startingCoordinate = header.getBuyIsland().getCoordinatesXY();
		Double[] destinationCoordinate = header.getSellIsland().getCoordinatesXY();
		// formula to distance between two points in a cartesian plane
		double distanceBetweenIslands = sqrt(pow(destinationCoordinate[0] - startingCoordinate[0], 2) + pow(destinationCoordinate[1] - startingCoordinate[1], 2));
		return distanceBetweenIslands;
	}
	
	@Override
	public String toString() {
		if (levelOfDetail.toString().equals("DETAILED")) {
			
			return header.toString() + core.toString() + detailed.toString();
			
		} else if (levelOfDetail.toString().equals("HEADER")) {
			
			return header.toString() + "\n";
			
		} else {
			
			return header.toString() + core.toString() + "\n";
		}
	}
}