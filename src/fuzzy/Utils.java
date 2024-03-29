package fuzzy;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author rwietter (Mauricio Witter)
 */
public class Utils {

    protected Map<String, Map<String, Double>> height;
    protected Map<String, Map<String, Double>> weight;

    protected Map<String, Double> leve;
    protected Map<String, Double> normal;
    protected Map<String, Double> pesado;

    protected Map<String, Double> baixo;
    protected Map<String, Double> medio;
    protected Map<String, Double> alto;

    protected Map<Double, Double> PC;
    protected Map<Double, Double> NN;
    protected Map<Double, Double> CC;

    protected Map<String, Map<Double, Double>> calories;

		public Utils() {
			this.leve = new Hashtable<String, Double>();
			this.normal = new Hashtable<String, Double>();
			this.pesado = new Hashtable<String, Double>();

			this.baixo = new Hashtable<String, Double>();
			this.medio = new Hashtable<String, Double>();
			this.alto = new Hashtable<String, Double>();

			this.height = new Hashtable<String, Map<String, Double>>();
			this.weight = new Hashtable<String, Map<String, Double>>();

			this.PC = new Hashtable<Double, Double>();
			this.NN = new Hashtable<Double, Double>();
			this.CC = new Hashtable<Double, Double>();

			this.calories = new Hashtable<String, Map<Double, Double>>();
		}

		public void setHeightRelevance(String[] keys, double[] relevance, Map<String, Double> map) {
			for (int i = 0; i < keys.length; i++) {
				map.put(keys[i], relevance[i]);
			}
		}

		public void setCaloriesRelevance(double[] keys, double[] relevance, Map<Double, Double> map) {
			for (int i = 0; i < keys.length; i++) {
				map.put(keys[i], relevance[i]);
			}
		}

		public void setWeightRelevance(int key, double[] relevance, Map<String, Double> map) {
			for (int i = 0; i < relevance.length; i++) {
				map.put(String.valueOf(key), relevance[i]);
				key += 10;
			}
		}

    public void printData() {
        System.out.println(this.leve.entrySet().toString());
        System.out.println(this.normal.entrySet().toString());
        System.out.println(this.pesado.entrySet().toString());
        System.out.println(this.height.entrySet().toString());
        System.out.println(this.weight.entrySet().toString());
        System.out.println(this.calories.entrySet().toString());
    }
}
