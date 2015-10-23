package modele;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author mgaillard
 */
public class Plan {
    // Attributs
    private Intersection entrepot;
    private Map<Integer, Intersection> intersections;
    private Collection<Troncon> troncons;
    
    // Methodes
    public Plan() {
        this.entrepot = null;
        this.intersections = new TreeMap();
        this.troncons = new LinkedList<>();
    }
    
    /**
     * Ajoute une intersection au plan.
     * @param id L'identifiant unique de l'intersection.
     * @param x La coordonnée X de l'intersection.
     * @param y La coordonnée Y de l'intersection.
     */
    protected void ajouterIntersection(int id, int x, int y) {
        intersections.put(id, new Intersection(id, x, y));
    }
    
    /**
     * Ajoute un troncon au plan.
     * Pour l'intersection de depart du troncon, la liste des successeurs est mise a jour.
     * Si une des intersections de depart ou d'arrivee n'existe pas, le troncon n'est pas ajouté.
     * @param idIntersectionDepart L'identifiant de l'intersection de départ.
     * @param idIntersectionArrivee L'identifiant de l'intersection d'arrivee.
     * @param nom Le nom du troncon.
     * @param longueur La longueur du troncon.
     * @param vitesse La vitesse moyenne sur le troncon.
     * @return True si le troncon a bien pu etre ajouté, false sinon.
     */
    protected boolean ajouterTroncon(int idIntersectionDepart, int idIntersectionArrivee, String nom, double longueur, double vitesse) {
        //On cherche les deux intersections.
        Intersection depart = getIntersection(idIntersectionDepart);
        Intersection arrivee = getIntersection(idIntersectionArrivee);
        //Si les deux intersections existent, on peut ajouter le troncon.
        if (depart != null && arrivee != null) {
            //On peut ajouter le troncon.
            Troncon nouveauTroncon = new Troncon(depart, arrivee, nom, longueur, vitesse);
            //On ajoute le troncon a la collection.
            troncons.add(nouveauTroncon);
            //On ajoute le troncon comme successeur de l'intersection de depart.
            depart.ajouterTronconSortant(nouveauTroncon);
            //On retourne true pour signaler que le troncon a bien été ajouté.
            return true;
        } else {
            //On retourne false pour signaler que le troncon n'a pas été ajouté.
            return false;
        }
    }
    
    /**
     * Retourne une Intersection par son identifiant.
     * Si l'Intersection n'existe pas, retourne null.
     * @param idIntersection L'identifiant de l'Intersection à retourner.
     * @return Une Intersection, ou bien null.
     */
    public Intersection getIntersection(int idIntersection) {
        return intersections.get(idIntersection);
    }
    
    /**
     * Définis l'Intersection où est situé l'entrepôt par son identifiant.
     * Si l'identifiant de l'intersection n'existe pas, l'entrepot sera definit à null.
     * @param idIntersectionEntrepot L'identifiant où est situé l'entrepôt.
     * @return L'intersection où est situé l'entrepot, ou bien null si cette intersection n'existe pas.
     */
    protected Intersection setEntrepot(int idIntersectionEntrepot) {
        this.entrepot = getIntersection(idIntersectionEntrepot);
        return this.entrepot;
    }
    
    /**
     * Retourne l'Intersection où est situé l'entrepôt.
     * @return l'Intersection où est situé l'entrepôt.
     */
    public Intersection getEntrepot() {
        return entrepot;
    }
    
    public Chemin CalculerPlusCourtChemin(Intersection depart, Intersection arrivee) {
        return null;
    }
}
