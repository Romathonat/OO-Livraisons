/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;

/**
 *
 * @author tfavrot
 */
public class DemandeLivraison {
    
    // Attributs
    
    private Intersection intersection;
    private Date heureLivraison;
    private FenetreLivraison fenetreLivraison;
    private static int tempsArret;
    
    // Methodes

    public DemandeLivraison(Intersection lieuLivraison, FenetreLivraison fenetreLivraison) {
        this.intersection = lieuLivraison;
        this.fenetreLivraison = fenetreLivraison;
    }

    public Intersection getintersection() {
        return intersection;
    }

    public static int getTempsArret() {
        return tempsArret;
    }
    
    public Date getHeureLivraison(){
        return heureLivraison;
    }
    
    public FenetreLivraison getFenetreLivraison(){
        return fenetreLivraison;
    }
    
    
    
}
