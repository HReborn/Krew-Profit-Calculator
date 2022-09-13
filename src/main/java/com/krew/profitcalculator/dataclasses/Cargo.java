package com.krew.profitcalculator.dataclasses;

public class Cargo {
	private int price;
	private int size;
	
	public Cargo(int price, int size) {
		this.price = price;
		this.size = size;
	}
	public int getPrice() {
		return price;
	}
	public int getSize() {
		return size;
	}
}
