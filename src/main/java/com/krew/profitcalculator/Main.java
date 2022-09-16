package com.krew.profitcalculator;

import java.util.List;

import com.krew.profitcalculator.dataclasses.profitoption.LevelOfDetail;
import com.krew.profitcalculator.dataclasses.profitoption.ProfitOption;

public class Main {
	public static void main(String[] args) {
		Calculator calc = new Calculator(LevelOfDetail.DETAILED);
		List<ProfitOption> options1 = calc.calculateSellRoutesFromCurrentIsland("taiwan", "trader 2", 2);
		List<ProfitOption> options2 = calc.calculateBestCargosFromCurrentToTargetIsland("taiwan", "labrador", "trader 1");
		List<ProfitOption> options3 = calc.calculateRoutesToAttackFromCurrentIsland("taiwan");
		System.out.println(options1);
		System.out.println(options2);
		System.out.println(options3);
	}
}
