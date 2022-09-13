package com.krew.profitcalculator;

import java.util.List;

import com.krew.profitcalculator.dataclasses.ProfitOption;

public class Main {
	
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		List<ProfitOption> options = calc.calculateProfitOptionsOfIslandWithCurrentBoat("brazil", "baby fancy");
		System.out.println(options);
	}
}
