/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author tfavrot
 */
public class Chemin {
    
    // Attributs
    
    private double duree;
    private List<Troncon> troncons;
    private Collection<DemandeLivraison> livraisonArrivee;
    
    // Methodes

    public Chemin(double duree, List<Troncon> troncons) {
        this.duree = duree;
        this.troncons = troncons;
    }

    public double getDuree() {
        return duree;
    }

    public Collection<Troncon> getTroncons() {
        return troncons;
    }
    
    public Intersection getIntersectionDepart() {
        return troncons.get(0).getIntersectionDepart();
    }

    public Intersection getIntersectionArrivee() {
        return troncons.get(troncons.size()-1).getIntersectionArrivee();
    }
    
    
}
