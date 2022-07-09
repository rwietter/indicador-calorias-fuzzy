/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuzzy;

import java.util.HashMap;
import fuzzy.IBaseConhecimento;

/**
 *
 * @author rwietter
 */
public class BaseConhecimento extends Utils implements IBaseConhecimento {

    public BaseConhecimento() {
        super();
        this.setWeight();
        this.setHeight();
        this.setInitialVariables();
        this.setOutputVariables();
    }

    public void setInitialVariables() {
        super.weight.put("LEVE", super.leve);
        super.weight.put("NORMAL", super.normal);
        super.weight.put("PESADO", super.pesado);

        super.height.put("BAIXO", super.baixo);
        super.height.put("MEDIO", super.medio);
        super.height.put("ALTO", super.alto);
    }

    public void setWeight() {
        super.leve.put("40", 0.5);
        super.leve.put("50", 1.0);
        super.leve.put("60", 0.5);
        super.leve.put("70", 0.0);
        super.leve.put("80", 0.0);
        super.leve.put("90", 0.0);
        super.leve.put("100", 0.0);

        super.normal.put("40", 0.0);
        super.normal.put("50", 0.0);
        super.normal.put("60", 0.5);
        super.normal.put("70", 1.0);
        super.normal.put("80", 0.5);
        super.normal.put("90", 0.0);
        super.normal.put("100", 0.0);

        super.pesado.put("40", 0.0);
        super.pesado.put("50", 0.0);
        super.pesado.put("60", 0.0);
        super.pesado.put("70", 0.0);
        super.pesado.put("80", 0.5);
        super.pesado.put("90", 1.0);
        super.pesado.put("100", 0.5);
    }

    public void setHeight() {
        super.baixo.put("1.3", 0.0);
        super.baixo.put("1.4", 0.0);
        super.baixo.put("1.5", 1.0);
        super.baixo.put("1.6", 0.0);
        super.baixo.put("1.7", 0.0);
        super.baixo.put("1.8", 0.0);
        super.baixo.put("1.9", 0.0);
        super.baixo.put("2.0", 0.0);
        super.baixo.put("2.1", 0.0);

        super.medio.put("1.3", 0.0);
        super.medio.put("1.4", 0.0);
        super.medio.put("1.5", 0.0);
        super.medio.put("1.6", 0.5);
        super.medio.put("1.7", 1.0);
        super.medio.put("1.8", 0.5);
        super.medio.put("1.9", 0.0);
        super.medio.put("2.0", 0.0);
        super.medio.put("2.1", 0.0);

        super.alto.put("1.3", 0.0);
        super.alto.put("1.4", 0.0);
        super.alto.put("1.5", 0.0);
        super.alto.put("1.6", 0.0);
        super.alto.put("1.7", 0.0);
        super.alto.put("1.8", 0.0);
        super.alto.put("1.9", 1.0);
        super.alto.put("2.0", 0.0);
        super.alto.put("2.1", 0.0);
    }

    // --------------------------------------------------------------
    // ------------------------ OUTPUT SET --------------------------
    // --------------------------------------------------------------
    public void setOutputVariables() {
        super.poucoCalorica.put(900.0, 1.0);
        super.poucoCalorica.put(1000.0, 0.0);
        super.poucoCalorica.put(1100.0, 0.0);
        super.poucoCalorica.put(1200.0, 0.0);
        super.poucoCalorica.put(1300.0, 0.0);
        super.poucoCalorica.put(1400.0, 0.0);
        super.poucoCalorica.put(1500.0, 0.0);

        super.caloriaNormal.put(900.0, 0.0);
        super.caloriaNormal.put(1000.0, 0.0);
        super.caloriaNormal.put(1100.0, 0.5);
        super.caloriaNormal.put(1200.0, 1.0);
        super.caloriaNormal.put(1300.0, 0.5);
        super.caloriaNormal.put(1400.0, 0.0);
        super.caloriaNormal.put(1500.0, 0.0);

        super.calorica.put(900.0, 0.0);
        super.calorica.put(1000.0, 0.0);
        super.calorica.put(1100.0, 0.0);
        super.calorica.put(1200.0, 0.0);
        super.calorica.put(1300.0, 0.0);
        super.calorica.put(1400.0, 0.5);
        super.calorica.put(1500.0, 1.0);

        /**
         * PC => Pouco Calórica NC => Caloria Normal CC => Calórica
         */
        super.outputVariables.put("PC", super.poucoCalorica);
        super.outputVariables.put("NN", super.caloriaNormal);
        super.outputVariables.put("CC", super.calorica);
    }

    // --------------------------------------------------------------
    // ------------------------ FUZZY RULES -------------------------
    // --------------------------------------------------------------
    /**
     * PC => Pouco Calórica NC => Caloria Normal CC => Calórica
     */
    public String fuzzyRules(String altura, String peso) {
        System.out.println("P: " + peso + " A: " + altura);
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
        return "unknow";
    }

    // --------------------------------------------------------------
    // --------------------------- GETTERS --------------------------
    // --------------------------------------------------------------
    public HashMap<String, HashMap<String, Double>> getHeight() {
        return super.height;
    }

    public HashMap<String, HashMap<String, Double>> getWeight() {
        return super.weight;
    }

    public HashMap<String, HashMap<Double, Double>> getVariaveisSaida() {
        return super.outputVariables;
    }
}
