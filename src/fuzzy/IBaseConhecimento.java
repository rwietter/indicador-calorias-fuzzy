/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fuzzy;

/**
 *
 * @author rwietter
 */
public interface IBaseConhecimento {

    public void setInitialVariables();

    public void setWeight();

    public void setHeight();

    public void setOutputVariables();

    public String fuzzyRules(String n1, String n2);
}
