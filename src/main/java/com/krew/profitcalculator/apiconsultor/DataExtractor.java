package com.krew.profitcalculator.apiconsultor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unchecked")
public class DataExtractor {
	
	private final String DATA = "src/main/resources/data.json";
	private final String SHIPS = "src/main/resources/ships.json";
	private final JSONObject dataJsonObj;
	private final JSONObject shipsJsonObj;
	
	public DataExtractor() {
		dataJsonObj = getJsonObject(DATA).orElseThrow();
		shipsJsonObj = getJsonObject(SHIPS).orElseThrow();
	}
	
	public Map<String, String> getAllIslandsCoordinates() {
		// while on the json i took a print and used pixel coordinates to locate island position.
		// x and y coordinates goes from 0 to 356 pixels
		// (0,0) starts bottom left and follows normal x, y plane rules.
		return (Map<String, String>) dataJsonObj.get("islandsXYCoordinates");
	}
	public Map<String, String> getAllCargoSizes() {
		return (Map<String, String>) dataJsonObj.get("cargoSize");
	}
	public Map<String, Map<String, String>> getPriceTable() {
		return (Map<String, Map<String, String>>) dataJsonObj.get("islandCargoPriceTable");
	}
	public Map<String, Map<String, String>> getShipPropertiesInfoTable() {
		return (Map<String, Map<String, String>>) shipsJsonObj;
	}
	
	private Optional<JSONObject> getJsonObject(String filePath) {
		FileReader reader;
		JSONObject jsonObj = null;
		try {
			reader = new FileReader(filePath);
			JSONParser parser = new JSONParser();
			jsonObj = (JSONObject) parser.parse(reader);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Optional.ofNullable(jsonObj);
	}
}