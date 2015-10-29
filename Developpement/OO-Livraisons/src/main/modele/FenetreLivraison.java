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
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author tfavrot
 */
public class FenetreLivraison {

    // Attributs
    private Date heureDebut;
    private Date heureFin;
    private List<DemandeLivraison> listDemandesLivraison;

    // Methodes
    public FenetreLivraison(Date heureDebut, Date heureFin) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.listDemandesLivraison = new ArrayList<DemandeLivraison> ();
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
        
        // TBD : verifier que le point de livraison <> Entrepot.

        DemandeLivraison demande = new DemandeLivraison(inter, this);
        this.listDemandesLivraison.add(demande);
        return demande;
    }

    /**
     * Retourne un iterator sur les demandes de Livraison.
     *
     * @return un iterator sur les demandes de Livraison.
     */
    public Iterator<DemandeLivraison> getDemandesLivraison() {
        Collection constCollection = Collections.unmodifiableCollection(listDemandesLivraison);
        return constCollection.iterator();
    }
}
