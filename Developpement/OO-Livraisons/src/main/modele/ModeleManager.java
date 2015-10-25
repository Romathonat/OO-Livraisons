/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author Kilian
 */
public class ModeleManager {
    
    private Plan plan;
    
    public ModeleManager(){
        plan = new Plan();
    }
    
    public Plan getPlan(){
        return plan;
    }
}
