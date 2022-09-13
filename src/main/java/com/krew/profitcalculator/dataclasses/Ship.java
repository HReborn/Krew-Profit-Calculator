package com.krew.profitcalculator.dataclasses;

import java.util.List;

public class Ship {
	private String name;
	private String type;
	private int price;
	private List<String> availableAt;
	private List<String> unavailableAt;
	private int hp;
	private double turnSpeed;
	private double speed;
	private int maxCrewSize;
	private int maxCargoSize;
	private int regenaration;
	
	public Ship(String name, String type, int price, List<String> availableAt, List<String> unavailableAt, int hp,double turnSpeed, double speed, int maxCrewSize, int maxCargoSize, int regenaration) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.availableAt = availableAt;
		this.unavailableAt = unavailableAt;
		this.hp = hp;
		this.turnSpeed = turnSpeed;
		this.speed = speed;
		this.maxCrewSize = maxCrewSize;
		this.maxCargoSize = maxCargoSize;
		this.regenaration = regenaration;
	}

	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public int getPrice() {
		return price;
	}
	public List<String> getAvailableAt() {
		return availableAt;
	}
	public List<String> getUnavailableAt() {
		return unavailableAt;
	}
	public int getHp() {
		return hp;
	}
	public double getTurnSpeed() {
		return turnSpeed;
	}
	public double getSpeed() {
		return speed;
	}
	public int getMaxCrewSize() {
		return maxCrewSize;
	}
	public int getMaxCargoSize() {
		return maxCargoSize;
	}
	public int getRegenaration() {
		return regenaration;
	}
	
}
