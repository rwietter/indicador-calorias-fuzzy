package fuzzy;

/**
 * @author rwietter
 */
public interface IBaseConhecimento {

    public void fuzzification();

    public void setWeight();

    public void setHeight();

    public void setOutputVariables();

    public String fuzzyRules(String n1, String n2);
}
