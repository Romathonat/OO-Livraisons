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

    public Intersection getIntersection() {
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
    
    public Date setHeureLivraison(Date heureLivraison){
        return this.heureLivraison = heureLivraison;
    }
    
}
