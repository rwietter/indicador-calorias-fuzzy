/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rwietter
 */
public class MaquinaInferencia {

    private BaseConhecimento baseConhecimento;
    private HashMap<String, ArrayList<String>> universoSaida;
    private double S;

    public MaquinaInferencia(BaseConhecimento base) {
        this.baseConhecimento = base;
        this.universoSaida = new HashMap<>();
    }

    // sistema fuzzy
    public void fuzzify(String height, String weight) {
        System.out.println("------------ FUZZIFY ------------");
        this.createSyntacticRules(height, weight);
        HashMap<Double, Double> R = this.createUnionBetweenSets(height, weight);
        System.out.println("Union: " + R);
        this.calculateCenterOfArea(R);
    }

    public double getS() {
        return this.S;
    }

    // Cria as possibilidades de pertinência e cria os conjuntos Fuzzy Ai, Bi e Ci
    public void createSyntacticRules(String height, String weight) {
        System.out.println("Create Syntactic Rules");
        HashMap<String, HashMap<String, Double>> q1 = this.baseConhecimento.getHeight(); // conjunto de pertinências da questão 1
        HashMap<String, HashMap<String, Double>> q2 = this.baseConhecimento.getWeight(); // conjunto de pertinências da questão 2

        // Regra Ri da base de regras R = Ai + Bi + Ci
        ArrayList<String> Ai = this.filterSyntacticRulesWhereRelevanceIsGreaterThanZero(q1, height); // conjunto fuzzy de entrada Q1
        ArrayList<String> Bi = this.filterSyntacticRulesWhereRelevanceIsGreaterThanZero(q2, weight); // conjunto fuzzy de entrada Q2
        ArrayList<String> Ci = this.createConjuntoDeSaidaCi(Ai, Bi); // cria conjunto de saída baseado nas entradas

        // Adiciona os conjuntos ao Universo de Saída
        this.universoSaida.put("Ai", Ai);
        this.universoSaida.put("Bi", Bi);
        this.universoSaida.put("Ci", Ci);

        System.out.println("UNIVERSO DE SAÍDA: " + this.universoSaida.toString());
    }

    public ArrayList<String> createConjuntoDeSaidaCi(ArrayList<String> Ai, ArrayList<String> Bi) {
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

    // Verifica as possibilidades de inferência onde a pertinência de M da questão é maior que 0.
    public ArrayList<String> filterSyntacticRulesWhereRelevanceIsGreaterThanZero(HashMap<String, HashMap<String, Double>> qG, String x) {
        System.out.println("Filter Syntactic Rules Where Relevance Is Greater Than Zero");
        ArrayList<String> G = new ArrayList<>();
        qG.forEach((String key, HashMap<String, Double> pertinencia) -> {
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
        System.out.print("\n\n");
        for (int i = 0; i < this.universoSaida.get("Ai").size(); i++) {
            double mapAi = this.baseConhecimento.getHeight().get(this.universoSaida.get("Ai").get(i)).get(height);

            for (int j = 0; j < this.universoSaida.get("Bi").size(); j++) {
                double mapBi = this.baseConhecimento.getWeight().get(this.universoSaida.get("Bi").get(j)).get(weight);

                HashMap<Double, Double> output = this.baseConhecimento.getVariaveisSaida().get(this.universoSaida.get("Ci").get(index));
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

    // Faz o cálculo do centro da área
    private void calculateCenterOfArea(HashMap<Double, Double> R) {
        System.out.println("Calculate Center of Area");
        double sumOfElements = 0;
        double divider = 0;

        for (Map.Entry<Double, Double> entry : R.entrySet()) {
            // key = 0, 20, 40, 60, 80, 100
            // Value = 0.0, 0.8, 0.0, 1.0, 0.0, 0.0
            // Formula = (Soma (multiplicação entre pertinência e key)) / (soma das pertinências)
            sumOfElements += entry.getValue() * entry.getKey();
            divider += entry.getValue();
        }

        // resultado final
        double S = sumOfElements / divider;

        this.S = S;
        System.out.println("RESULTADO: " + S);
    }
}
