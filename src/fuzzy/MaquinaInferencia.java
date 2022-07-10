package fuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rwietter (Mauricio Witter)
 */
public class MaquinaInferencia {

	private BaseConhecimento baseConhecimento;
	private HashMap<String, ArrayList<String>> outputTable;
	private double S;

	public MaquinaInferencia(BaseConhecimento base) {
		this.baseConhecimento = base;
		this.outputTable = new HashMap<>();
	}

	// --------------------------------------------------------------
	// Cria o cojunto universo e as possibilidades dado a fuzificação
	// --------------------------------------------------------------
	public void createInputAndOutputSets(String height, String weight) {
		Map<String, Map<String, Double>> fuzzyHeight = this.baseConhecimento.getHeight(); // conjunto de pertinências
		Map<String, Map<String, Double>> fuzzyWeight = this.baseConhecimento.getWeight(); // conjunto de pertinências

		// Regra Ri da base de regras R = Ai + Bi + Ci
		ArrayList<String> Ai = this.createLinguisticTerms(fuzzyHeight, height); // conjunto Altura [Medio]
		ArrayList<String> Bi = this.createLinguisticTerms(fuzzyWeight, weight); // conjunto Peso [Normal, Leve]
		ArrayList<String> Ci = this.createOutputSet(Ai, Bi); // cria conjunto de saída baseado nas entradas

		// Adiciona os conjuntos ao Universo de Saída
		this.outputTable.put("Ai", Ai);
		this.outputTable.put("Bi", Bi);
		this.outputTable.put("Ci", Ci);
	}

	// -------------------------------------------------------------
	// - Cria o set T(x) filtrando as pertinências do set Ai e Bi --
	// -------------------------------------------------------------
	public ArrayList<String> createLinguisticTerms(Map<String, Map<String, Double>> fuzzySet, String x) {
		ArrayList<String> G = new ArrayList<>();
		fuzzySet.forEach((String key, Map<String, Double> pertinencia) -> {
			if (pertinencia.get(x) > 0.0) {
				G.add(key); // HEIGHT: {MÉDIO: 0.5} | WEIGHT: {NORMAL: 0.5, LEVE: 0.5}
			}
		});
		return G;
	}

	// -------------------------------------------------------------
	// - Filtra e cria as regras de saída. Ex: Ai + Bi = [NN, NN] --
	// -------------------------------------------------------------
	public ArrayList<String> createOutputSet(ArrayList<String> Ai, ArrayList<String> Bi) {
		ArrayList<String> Ci = new ArrayList<String>(); // conjunto fuzzy de saída
		for (int i = 0; i < Ai.size(); i++) {
			for (int j = 0; j < Bi.size(); j++) {
				String heightTx = (String) Ai.get(i); // [Medio, Medio]
				String weightTx = (String) Bi.get(j); // [Normal, leve]
				String rule = this.baseConhecimento.fuzzyRules(heightTx, weightTx); // Infere a regra de saída, baseado nos
																																						// conjuntos T(x)
				Ci.add(rule); // [NORMAL, NORMAL]
			}
		}
		return Ci;
	}

	// -------------------------------------------------------------
	// -- Cria a união entre os conjunto Ai e Bi (PESO e ALTURA) ---
	// -------------------------------------------------------------
	public HashMap<Double, Double> createUnionBetweenSets(String height, String weight) {
		System.out.println("Create Union Between Sets");
		HashMap<Double, Double> unionRelationship = new HashMap<Double, Double>();

		ArrayList<String> AiSet = this.outputTable.get("Ai");
		ArrayList<String> BiSet = this.outputTable.get("Bi");
		ArrayList<String> CiSet = this.outputTable.get("Ci");

		for (int i = 0; i < AiSet.size(); i++) {
			String fuzzyHeight = AiSet.get(i); // BAIXO, MEDIO, ALTO
			double fuzzyHeightValue = this.baseConhecimento.getHeight().get(fuzzyHeight).get(height); // pertinência altura

			for (int j = 0; j < BiSet.size(); j++) {
				String variableWeight = BiSet.get(j); // LEVE, NORMAL, PESADO
				double fuzzyWeightValue = this.baseConhecimento.getWeight().get(variableWeight).get(weight); // pertinência peso

				Map<String, Map<Double, Double>> calories = this.baseConhecimento.getCalories();

				calories.get(CiSet.get(j)).forEach((Double key, Double value) -> {
					unionRelationship.put(key, Math.min(Math.min(fuzzyHeightValue, fuzzyWeightValue), value));
				});
			}
		}
		return unionRelationship;
	}

	// -------------------------------------------------------------
	// ---------------- CÁLCULO DO CENTRO DA ÁREA ------------------
	// -------------------------------------------------------------
	private void calculateCenterOfArea(HashMap<Double, Double> R) {
		System.out.println("Calculate Center of Area");
		double sumOfElements = 0, divider = 0;

		for (Map.Entry<Double, Double> entry : R.entrySet()) {
			// S = (sum (multiply between relevance and key)) / (sum of relevance)
			sumOfElements += entry.getValue() * entry.getKey();
			divider += entry.getValue();
		}

		// resultado final do cálculo de centro da área
		double S = sumOfElements / divider;

		this.baseConhecimento.setInferenceResult(S);
	}

	// --------------------------------------------------------------
	// ----------------------- Fuzzy System -------------------------
	// --------------------------------------------------------------
	public void fuzzify(String height, String weight) {
		System.out.println("------------ FUZZIFY ------------");

		System.out.println("Create Syntactic Rules");
		this.createInputAndOutputSets(height, weight);

		System.out.println("Create Union Between Sets");
		HashMap<Double, Double> R = this.createUnionBetweenSets(height, weight);

		System.out.println("Calculate Center Of Area");
		this.calculateCenterOfArea(R);
	}
}
