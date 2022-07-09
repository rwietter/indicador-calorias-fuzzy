/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuzzy;

import java.util.HashMap;

/**
 *
 * @author rwietter
 */
public class Utils {

    protected HashMap<String, HashMap<String, Double>> height;
    protected HashMap<String, HashMap<String, Double>> weight;

    protected HashMap<String, Double> leve;
    protected HashMap<String, Double> normal;
    protected HashMap<String, Double> pesado;

    protected HashMap<String, Double> baixo;
    protected HashMap<String, Double> medio;
    protected HashMap<String, Double> alto;

    protected HashMap<Double, Double> poucoCalorica;
    protected HashMap<Double, Double> caloriaNormal;
    protected HashMap<Double, Double> calorica;

    protected HashMap<String, HashMap<Double, Double>> outputVariables;

    public Utils() {
        this.leve = new HashMap<String, Double>();
        this.normal = new HashMap<String, Double>();
        this.pesado = new HashMap<String, Double>();

        this.baixo = new HashMap<String, Double>();
        this.medio = new HashMap<String, Double>();
        this.alto = new HashMap<String, Double>();

        this.height = new HashMap<String, HashMap<String, Double>>();
        this.weight = new HashMap<String, HashMap<String, Double>>();

        this.poucoCalorica = new HashMap<Double, Double>();
        this.caloriaNormal = new HashMap<Double, Double>();
        this.calorica = new HashMap<Double, Double>();

        this.outputVariables = new HashMap<String, HashMap<Double, Double>>();
    }

    public void printData() {
        System.out.println(this.leve.entrySet().toString());
        System.out.println(this.normal.entrySet().toString());
        System.out.println(this.pesado.entrySet().toString());
        System.out.println(this.height.entrySet().toString());
        System.out.println(this.weight.entrySet().toString());
        System.out.println(this.outputVariables.entrySet().toString());
    }
}
