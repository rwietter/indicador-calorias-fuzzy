package fuzzy;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author rwietter (Mauricio Witter)
 */

public class BaseConhecimento extends Utils implements IBaseConhecimento {
	private double S;

	public BaseConhecimento() {
		super();
		this.setWeight();
		this.setHeight();
		this.fuzzification();
		this.setCalories();
	}

	public void setWeight() {
		double[] leve = { 0.5, 1.0, 0.5, 0.0, 0.0, 0.0, 0.0 };
		double[] normal = { 0.0, 0.0, 0.5, 1.0, 5.0, 0.0, 0.0 };
		double[] pesado = { 0.0, 0.0, 0.0, 0.0, 0.5, 1.0, 0.5 };

		super.setWeightRelevance(40, leve, super.leve);
		super.setWeightRelevance(40, normal, super.normal);
		super.setWeightRelevance(40, pesado, super.pesado);
	}

	public void setHeight() {
		String keys[] = { "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2.0", "2.1" };

		double baixo[] = { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		double medio[] = { 0.0, 0.0, 0.0, 0.5, 0.1, 0.5, 0.0, 0.0, 0.0 };
		double alto[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 };

		super.setHeightRelevance(keys, baixo, super.baixo);
		super.setHeightRelevance(keys, medio, super.medio);
		super.setHeightRelevance(keys, alto, super.alto);
	}

	// --------------------------------------------------------------
	// ------------------------ OUTPUT SET --------------------------
	// --------------------------------------------------------------
	// Fuzificação das saídas em calorias
	public void setCalories() {
		double keys[] = { 900.0, 1000.0, 1100.0, 1200.0, 1300.0, 1400.0, 1500.0 };
		double pc[] = { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		double nn[] = { 0.0, 0.0, 0.5, 1.0, 0.5, 0.0, 0.0 };
		double cc[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 1.0 };

		super.setCaloriesRelevance(keys, pc, super.PC);
		super.setCaloriesRelevance(keys, nn, super.NN);
		super.setCaloriesRelevance(keys, cc, super.CC);

		/**
		 * PC => Pouco Calórica
		 * NC => Caloria Normal
		 * CC => Calórica
		 */
		super.calories.put("PC", super.PC);
		super.calories.put("NN", super.NN);
		super.calories.put("CC", super.CC);
	}

	// 1. Faz a fuzificação das entradas para PESO E ALTURA
	public void fuzzification() {
		super.weight.put("LEVE", super.leve);
		super.weight.put("NORMAL", super.normal);
		super.weight.put("PESADO", super.pesado);

		super.height.put("BAIXO", super.baixo);
		super.height.put("MEDIO", super.medio);
		super.height.put("ALTO", super.alto);
	}

	// --------------------------------------------------------------
	// ------------------------ FUZZY RULES -------------------------
	// --------------------------------------------------------------
	/**
	 * PC => Pouco Calórica
	 * NC => Caloria Normal
	 * CC => Calórica
	 */
	// 1. Regras do sistema fuzzy
	public String fuzzyRules(String altura, String peso) {
		switch (peso) {
			case "LEVE":
				switch (altura) {
					case "BAIXO":
						return "NN";
					case "MEDIO":
						return "NN";
					case "ALTO":
						return "CC";
					default:
				}
			case "NORMAL":
				switch (altura) {
					case "BAIXO":
						return "NN";
					case "MEDIO":
						return "NN";
					case "ALTO":
						return "NN";
					default:
				}
			case "PESADO":
				switch (altura) {
					case "BAIXO":
						return "PC";
					case "MEDIO":
						return "NN";
					case "ALTO":
						return "NN";
					default:
				}
			default:
		}
		return "NULL";
	}

	// --------------------------------------------------------------
	// --------------------------- GETTERS --------------------------
	// --------------------------------------------------------------
	public Map<String, Map<String, Double>> getHeight() {
		return super.height;
	}

	public Map<String, Map<String, Double>> getWeight() {
		return super.weight;
	}

	public Map<String, Map<Double, Double>> getCalories() {
		return super.calories;
	}

	public double getInferenceResult() {
		return this.S;
	}

	public void setInferenceResult(double S) {
		this.S = S;
	}
}
