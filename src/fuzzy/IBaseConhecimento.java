package fuzzy;

/**
 * @author rwietter (Mauricio Witter)
 */
public interface IBaseConhecimento {

    public void fuzzification();

    public void setWeight();

    public void setHeight();

    public void setCalories();

    public String fuzzyRules(String n1, String n2);
}
