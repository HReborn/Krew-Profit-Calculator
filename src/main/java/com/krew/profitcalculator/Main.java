package com.krew.profitcalculator;

import java.util.List;

import com.krew.profitcalculator.dataclasses.ProfitOption;

public class Main {
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		List<ProfitOption> options1 = calc.calculateListOfProfitOptions("guinea","night wind", 2);
		List<ProfitOption> options2 = calc.calculateListOfProfitOptions("malaysia","fortune trader", 2);
		//List<ProfitOption> options = calc.calculateListOfProfitOptions("brazil", "labrador" ,"baby fancy", 30);
		System.out.println(options1);
		System.out.println(options2);
	}
}
