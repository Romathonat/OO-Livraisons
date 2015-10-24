package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mgaillard
 */
public class Chemin {
    
    // Attributs
    
    private double duree;
    private DemandeLivraison livraisonArrivee;
    private List<Troncon> troncons;
    
    // Methodes

    public Chemin() {
        this.duree = 0;
        this.livraisonArrivee = null;
        this.troncons = new ArrayList<>();
    }

    /**
     * Retourne la duree necessaire pour emprunter le chemin.
     * Si le chemin ne comporte aucun troncon, retourne 0.
     * @return la duree necessaire pour emprunter le chemin.
     */
    public double getDuree() {
        return duree;
    }
    
    /**
     * Definit la livraison d'arrivee du chemin.
     * La livraison d'arrivee doit être sur la même intersection que l'intersection d'arrivee.
     * @param livraison La demande de livraison situé à l'arrivée du chemin.
     * @return True si la livraison d'arrivee a bien été définie.
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
     * Retourne la livraison d'arrivee du chemin. Si celle-ci n'est pas définie, retourne null.
     * @return La livraison d'arrivee du chemin, ou bien null.
     */
    public DemandeLivraison getLivraisonArrivee() {
        return livraisonArrivee;
    }
    
    /**
     * Ajoute un troncon au chemin.
     * Verifie si le chemin est bien constitué des troncons bout à bout.
     * Modifie la duree du Chemin.
     * @param troncon Le troncon a ajouter au chemin.
     * @return True si le troncon a été ajouté, false sinon.
     */
    public boolean ajouterTroncon(Troncon troncon) {
        //On verifie si l'intersection d'arrivee est bien l'intersection de depart du nouveau troncon.
        //ou bien s'il n'y a pas encore d'intersection d'arrivee.
        Intersection arrivee = getIntersectionArrivee();
        if (arrivee == null || arrivee.equals(troncon.getIntersectionDepart())) {
            //On ajoute le troncon à la liste.
            troncons.add(troncon);
            //On met à jour la durée.
            duree += troncon.getDuree();
            //On signifie que le troncon a bien été ajouté.
            return true;
        }
        //Le troncon n'a pas pu etre ajouté.
        return false;
    }
    
    /**
     * Retourne un iterateur constant vers la liste des troncons.
     * @return Un iterateur constant vers la liste des troncons.
     */
    public Iterator<Troncon> getTroncons() {
        List constList = Collections.unmodifiableList(troncons);
        return constList.iterator();
    }
    
    /**
     * Retourne l'intersection de depart du chemin.
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
     * Retourne l'intersection d'arrivee du chemin.
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
