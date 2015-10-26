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
    private EnsembleLivraisons ensembleLivraisons;
    private Tournee tournee;
    
    public ModeleManager(){
        this.plan = new Plan();
        this.ensembleLivraisons = null;
        this.tournee = null;
    }
    
    public Plan getPlan(){
        return this.plan;
    }
    
    public EnsembleLivraisons getEnsembleLivraisons(){
        return this.ensembleLivraisons;
    }
    
    public Tournee getTournee(){
        return this.tournee;
    }
}
