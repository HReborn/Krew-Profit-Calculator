package com.krew.profitcalculator;

import com.krew.profitcalculator.apiconsultor.DataExtractor;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Krew here");
		DataExtractor extractor = new DataExtractor();
		System.out.println(extractor.getPriceTable());
		System.out.println(extractor.getAllCargoSizes());
		System.out.println(extractor.getAllIslandsCoordinates());
		System.out.println(extractor.getShipPropertiesInfoTable());
	}
}
