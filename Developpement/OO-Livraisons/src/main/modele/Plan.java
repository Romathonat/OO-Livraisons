package modele;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import org.junit.runner.RunWith;

/**
 *
 * @author tfavrot
 */
public class Plan {
    // Attributs
    private Intersection entrepot;
    private Map<Integer, Intersection> intersections;
    private Collection<Troncon> troncons;
    
    // Methodes
    public Plan() {
        entrepot = null;
        intersections = new TreeMap();
    }
    
    protected void ajouterIntersection(Intersection intersection) {
        intersections.put(intersection.getId(), intersection);
    }
    
    protected void ajouterTroncon(Troncon troncon) {
        //On ajoute le troncon dans l'intersection de depart du troncon.
        
    }
    
    public Intersection getIntersection(int idIntersection) {
        return intersections.get(idIntersection);
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
