/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Collection;

/**
 *
 * @author tfavrot
 */
public class Plan {

    // Attributs
    
    private Intersection entrepot;
    private Collection<Intersection> intersections;
    private Collection<Troncon> troncons;
    private Collection<DemandesLivraisons> demandesLivraisons;
    
    
    // Methodes
    
    public Plan() {
        
    }
    
    protected void ajouterIntersection(Intersection intersection){
        
    }
    
    protected void ajouterTroncon(Troncon troncon){
        
    }
    
    public Intersection getIntersection(int idIntersection){
        return null;
    }
    
    protected void setEntrepot(Intersection unEntrepot){
        this.entrepot = unEntrepot;
    }
    
    public Intersection getEntrepot(){
        return entrepot;
    }
    
    public Chemin calculerPlusCourtChemin(Intersection depart, Intersection arrivee){
        return null;
    }
   
    
    
}
