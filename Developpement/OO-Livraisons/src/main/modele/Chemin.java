package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Un chemin modélise le parcours entre deux livraisons.  
 * @author mgaillard
 */
public class Chemin {
    
    
    /**
     * La durée temporelle du parcours d'un chemin en secondes.
     */
    private double duree;
    
    /**
     * La livraison vers laquelle le chemin se dirige.
     */
    private DemandeLivraison livraisonArrivee;
    
    /**
     * La liste des troncons qui composent un chemin.
     */
    private List<Troncon> troncons;
    
    
    /**
     * Constructeur d'un chemin.
     */
    public Chemin() {
        this.duree = 0;
        this.livraisonArrivee = null;
        this.troncons = new ArrayList<>();
    }

    /**
     * Retourne la durée nécessaire pour emprunter le chemin.
     * Si le chemin ne comporte aucun troncon, retourne 0.
     * @return La durée nécessaire pour emprunter le chemin.
     */
    public double getDuree() {
        return duree;
    }
    
    /**
     * Définit la livraison d'arrivée du chemin.
     * La livraison d'arrivée doit être sur la même intersection que l'intersection d'arrivée.
     * @param livraison La demande de livraison située à l'arrivée du chemin.
     * @return True si la livraison d'arrivée a bien été définie.
     */
    public boolean setLivraisonArrivee(DemandeLivraison livraison) {
        //On verifie que l'intersection d'arrivee du chemin est egale à l'intersection de la livaison.
        Intersection arrivee = getIntersectionArrivee();
        if (arrivee != null && arrivee.equals(livraison.getIntersection())) {
            livraisonArrivee = livraison;
            return true;
        }
        return false;
    }
    
    /**
     * Retourne la livraison d'arrivée du chemin. Si celle-ci n'est pas définie, retourne null.
     * @return La livraison d'arrivée du chemin, ou bien null.
     */
    public DemandeLivraison getLivraisonArrivee() {
        return livraisonArrivee;
    }
    
    /**
     * Ajoute un troncon au début du chemin.
     * Vérifie si le chemin est bien constitué des troncons bout à bout.
     * Modifie la durée du Chemin.
     * @param troncon Le troncon à ajouter au chemin.
     * @return True si le troncon a été ajouté, false sinon.
     */
    public boolean ajouterTronconDebut(Troncon troncon) {
        //On verifie si l'intersection de depart est bien l'intersection d'arrivee du nouveau troncon.
        //ou bien s'il n'y a pas encore d'intersection de depart.
        Intersection depart = getIntersectionDepart();
        if (depart == null || depart.equals(troncon.getIntersectionArrivee())) {
            //On ajoute le troncon à la liste.
            troncons.add(0, troncon);
            //On met à jour la durée.
            duree += troncon.getDuree();
            //On signifie que le troncon a bien été ajouté.
            return true;
        }
        //Le troncon n'a pas pu etre ajouté.
        return false;
    }
    
    /**
     * Retourne un itérateur constant vers la liste des troncons.
     * @return Un itérateur constant vers la liste des troncons.
     */
    public Iterator<Troncon> getTroncons() {
        List constList = Collections.unmodifiableList(troncons);
        return constList.iterator();
    }
    
    /**
     * Retourne l'intersection de départ du chemin.
     * Si le chemin ne comporte aucun troncon, retourne null.
     * @return Une Intersection, ou bien null.
     */
    public Intersection getIntersectionDepart() {
        Intersection depart = null;
        if (troncons.size() > 0) {
            depart = troncons.get(0).getIntersectionDepart();
        }
        return depart;
    }

    /**
     * Retourne l'intersection d'arrivée du chemin.
     * Si le chemin ne comporte aucun troncon, retourne null.
     * @return Une Intersection, ou bien null.
     */
    public Intersection getIntersectionArrivee() {
        Intersection arrivee = null;
        if (troncons.size() > 0) {
            arrivee = troncons.get(troncons.size()-1).getIntersectionArrivee();
        }
        return arrivee;
    }
}
