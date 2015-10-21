package modele;

import java.util.Collection;
import java.util.List;
import org.junit.runner.RunWith;

/**
 *
 * @author tfavrot
 */
public class Plan {
    // Attributs
    private Intersection entrepot;
    private Collection<Intersection> intersections;
    private Collection<Troncon> troncons;
    
    // Methodes
    public Plan() {
        //Initialisation des Collections.
        entrepot = null;
    }
    
    protected void ajouterIntersection(Intersection intersection) {
        
    }
    
    protected void ajouterTroncon(Troncon troncon) {
        
    }
    
    public Intersection getIntersection(int idIntersection) {
        return null;
    }
    
    protected void setEntrepot(Intersection unEntrepot) {
        this.entrepot = unEntrepot;
    }
    
    public Intersection getEntrepot() {
        return entrepot;
    }
    
    public Chemin CalculerPlusCourtChemin(Intersection depart, Intersection arrivee) {
        return null;
    }
}
