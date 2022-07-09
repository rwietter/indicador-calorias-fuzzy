package fuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rwietter
 */
public class MaquinaInferencia {

    private BaseConhecimento baseConhecimento;
    private HashMap<String, ArrayList<String>> outputTable;
    private double S;

    public MaquinaInferencia(BaseConhecimento base) {
        this.baseConhecimento = base;
        this.outputTable = new HashMap<>();
    }

    // sistema fuzzy
    public void fuzzify(String height, String weight) {
        System.out.println("------------ FUZZIFY ------------");
        this.createSyntacticRules(height, weight);
        HashMap<Double, Double> R = this.createUnionBetweenSets(height, weight);
        this.calculateCenterOfArea(R);
    }

    // Cria as possibilidades de pertinência e cria os conjuntos Fuzzy Ai, Bi e Ci
    public void createSyntacticRules(String height, String weight) {
        System.out.println("Create Syntactic Rules");
        Map<String, Map<String, Double>> fuzzyHeight = this.baseConhecimento.getHeight(); // conjunto de pertinências da
                                                                                          // questão 1
        Map<String, Map<String, Double>> fuzzyWeight = this.baseConhecimento.getWeight(); // conjunto de pertinências da
                                                                                          // questão 2

        // Regra Ri da base de regras R = Ai + Bi + Ci
        ArrayList<String> Ai = this.filterSyntacticRulesWhereRelevanceIsGreaterThanZero(fuzzyHeight, height); // conjunto
                                                                                                              // fuzzy
                                                                                                              // de
                                                                                                              // entrada
                                                                                                              // Q1
        ArrayList<String> Bi = this.filterSyntacticRulesWhereRelevanceIsGreaterThanZero(fuzzyWeight, weight); // conjunto
                                                                                                              // fuzzy
                                                                                                              // de
                                                                                                              // entrada
                                                                                                              // Q2
        ArrayList<String> Ci = this.createOutputSet(Ai, Bi); // cria conjunto de saída baseado nas entradas

        // Adiciona os conjuntos ao Universo de Saída
        this.outputTable.put("Ai", Ai);
        this.outputTable.put("Bi", Bi);
        this.outputTable.put("Ci", Ci);

        System.out.println("UNIVERSO DE SAÍDA: " + this.outputTable.toString());
    }

    public ArrayList<String> createOutputSet(ArrayList<String> Ai, ArrayList<String> Bi) {
        ArrayList<String> Ci = new ArrayList<String>(); // conjunto fuzzy de saída

        for (int i = 0; i < Ai.size(); i++) {
            for (int j = 0; j < Bi.size(); j++) {
                String prop1 = (String) Ai.get(i);
                String prop2 = (String) Bi.get(j);
                String rule = this.baseConhecimento.fuzzyRules(prop1, prop2);
                Ci.add(rule);
            }
        }
        return Ci;
    }

    // Verifica as possibilidades de inferência onde a pertinência de M da questão é
    // maior que 0.
    public ArrayList<String> filterSyntacticRulesWhereRelevanceIsGreaterThanZero(Map<String, Map<String, Double>> qG,
            String x) {
        System.out.println("Filter Syntactic Rules Where Relevance Is Greater Than Zero");
        ArrayList<String> G = new ArrayList<>();
        qG.forEach((String key, Map<String, Double> pertinencia) -> {
            System.out.println("pertinencia: " + pertinencia);
            System.out.println("Filter: " + x);
            if (pertinencia.get(x) > 0.0) {
                G.add(key);
            }
        });
        return G;
    }

    // Cria a união entre os conjunto Ai e Bi (Q1 e Q2) por meio do Math.min()
    public HashMap<Double, Double> createUnionBetweenSets(String height, String weight) {
        System.out.println("Create Union Between Sets");
        ArrayList<HashMap<Double, Double>> relationships = new ArrayList<HashMap<Double, Double>>();

        int index = 0;
        for (int i = 0; i < this.outputTable.get("Ai").size(); i++) {
            double mapAi = this.baseConhecimento.getHeight().get(this.outputTable.get("Ai").get(i)).get(height);

            for (int j = 0; j < this.outputTable.get("Bi").size(); j++) {
                double mapBi = this.baseConhecimento.getWeight().get(this.outputTable.get("Bi").get(j)).get(weight);

                Map<Double, Double> output = this.baseConhecimento.getVariaveisSaida()
                        .get(this.outputTable.get("Ci").get(index));
                System.out.println("OUTPUT CI INDEX: " + output);

                HashMap<Double, Double> relation = new HashMap<Double, Double>();
                output.forEach((Double key, Double value) -> {
                    relation.put(key, Math.min(Math.min(mapAi, mapBi), value));
                });
                relationships.add(relation);
                index++;
            }
        }

        HashMap<Double, Double> relationship = this.mergeRelationship(relationships);

        return relationship;
    }

    public HashMap<Double, Double> mergeRelationship(ArrayList<HashMap<Double, Double>> relationships) {
        HashMap<Double, Double> mergedRelationship = new HashMap<Double, Double>();

        relationships.forEach((HashMap<Double, Double> relationship) -> {
            relationship.forEach((Double key, Double value) -> {
                if (!mergedRelationship.containsKey(key) || mergedRelationship.get(key) < value) {
                    mergedRelationship.put(key, value);
                }
            });
        });
        return mergedRelationship;
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

        this.S = S;
    }

    // -------------------------------------------------------------
    // ------------------------- Getters ---------------------------
    // -------------------------------------------------------------
    public double getS() {
        return this.S;
    }
}
