/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tfavrot
 */
public class FenetreLivraison {

    // Attributs
    private Date heureDebut;
    private Date heureFin;
    private List<DemandeLivraison> listeDemandesLivraison;

    // Methodes
    public FenetreLivraison(Date heureDebut, Date heureFin) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.listeDemandesLivraison = new ArrayList<DemandeLivraison> ();
    }

    /**
     * Retourne l'heure de début de la Fenetre temporelle de livraison.
     */
    public Date getHeureDebut() {
        return heureDebut;
    }

    /**
     * Retourne l'heure de fin de la Fenetre temporelle de livraison.
     */
    public Date getHeureFin() {
        return heureFin;
    }

    /**
     * Ajoute un point de livraison à la fenêtre en cours. Cette fonction
     * vérifie que l'intersection passée en paramètre est valide: En plus de ne
     * pas être "null", celle-ci doit être différente de l'entrepot.
     *
     * @param id L'id de la livraison. (cette information n'est pas utilisé par
     * la suite pour le moment)
     * @param client L'id du client concerné par cette livraison. (cette
     * information n'est pas utilisé par la suite pour le moment)
     * @param inter L'intersection à laquelle doit avoir lieu la livraison.
     * @return La demande de livraison si les conditions d'ajout sont
     * respectées, null sinon.
     */
    public DemandeLivraison ajouterDemandeLivraison(int id, int client, Intersection inter) {
        if (inter == null) {
            return null;
        }

        DemandeLivraison demande = new DemandeLivraison(inter, this);
        this.listeDemandesLivraison.add(demande);
        return demande;
    }

    /**
     * Retourne un iterator sur les demandes de Livraison.
     *
     * @return un iterator sur les demandes de Livraison.
     */
    public Iterator<DemandeLivraison> getDemandesLivraison() {
        Collection constCollection = Collections.unmodifiableCollection(listeDemandesLivraison);
        return constCollection.iterator();
    }
    
    /**
     * Retourne l'ensemble des id des intersections des demandes de livraisons
     * de la fenetre de livraison.
     * @return l'ensemble des id des intersections des demandes de livraisons.
     */
    public Set<Integer> getIdIntersectionsLivraisons() {
        Set<Integer> idIntersections = new HashSet<>();
        Iterator<DemandeLivraison> itDemande = listeDemandesLivraison.iterator();
        while(itDemande.hasNext()) {
            DemandeLivraison livraison = itDemande.next();
            idIntersections.add(livraison.getIntersection().getId());
        }
        return idIntersections;
    }
}
