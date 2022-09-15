package com.krew.profitcalculator;

import java.util.List;

import com.krew.profitcalculator.dataclasses.ProfitOption;

public class Main {
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		List<ProfitOption> options1 = calc.calculateListOfProfitOptions("malaysia", 2);
		System.out.println(options1);
	}
}
