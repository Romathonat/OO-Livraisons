/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Une tournée est un ensemble ordonné de chemins. Elle détermine la totalité du
 * trajet du livreur depuis l'entrepôt jusqu'à l'entrepôt, en passant par les
 * points de livraison.
 *
 * @author tfavrot
 */
public class Tournee {

    /**
     * La liste des chemins qui composent la tournée.
     */
    private List<Chemin> chemins;
    long tempsDeLivraison; // en secondes.

    /**
     * Constructeur d'une tournée.
     */
    public Tournee() {
        chemins = new ArrayList<Chemin>();
        tempsDeLivraison = -1;
    }

    /**
     * Calcule les horaires auxquels les points de livraisons seront atteints.
     * Il convient d'avoir calculé la tournée auparavant.
     */
    public void CalculerHeuresDemandesLivraisons() {
        Iterator<Chemin> it_chemin = this.getChemins();

        // initialisation
        Date instantCourant = new Date(this.chemins.get(0).getLivraisonArrivee().getFenetreLivraison().getHeureDebut().getTime());

        while (it_chemin.hasNext()) {
            // une telle implémentation n'est pas optimale mais le code de Date ne permet pas de faire autrement.
            Chemin cheminCourant = it_chemin.next();
            instantCourant.setTime(instantCourant.getTime() + (long) (cheminCourant.getDuree() * 1000));

            // on regarde si on se trouve dans la fenetre de la prochaine livraison.
            if (cheminCourant.getLivraisonArrivee().getFenetreLivraison().getHeureDebut().compareTo(instantCourant) < 0) { // si c'est le cas, on ivre.
                instantCourant.setTime(instantCourant.getTime() + cheminCourant.getLivraisonArrivee().getTempsArret() * 1000);
            } else { // sinon on attend le debut de fenetre puis on livre avant de continuer
                instantCourant.setTime(cheminCourant.getLivraisonArrivee().getFenetreLivraison().getHeureDebut().getTime() + cheminCourant.getLivraisonArrivee().getTempsArret() * 1000);
            }
            cheminCourant.getLivraisonArrivee().setHeureLivraison(instantCourant);
        }

        // on met à jour le temps de livraison en secondes.
        this.tempsDeLivraison = (instantCourant.getTime() - this.chemins.get(0).getLivraisonArrivee().getFenetreLivraison().getHeureDebut().getTime()) / 1000;
    }

    /**
     * Retourne la liste des chemins qui composent la tournée.
     *
     * @return La liste des chemins qui composent la tournée.
     */
    public Iterator<Chemin> getChemins() {
        List<Chemin> constList = Collections.unmodifiableList(chemins);
        return constList.iterator();
    }

    /**
     * Permet de supprimer chemin de la liste des chemins
     *
     * @param chemin
     */
    public void supprimerChemin(Chemin chemin) {
        this.chemins.remove(chemin);

        // on met à jour les heures de livraison
        this.CalculerHeuresDemandesLivraisons();
    }

    /**
     * Ajoute un chemin à la collection de chemins. Cette fonction vérifie que
     * le chemin passé en paramètre n'est pas "null". Cette fonction ajoute le
     * chemin "au bon endroit"
     *
     * @param chemin le chemin à ajouter à la collection
     * @return Le chemin si les conditions d'ajout sont respectées, null sinon.
     */
    public Chemin AjouterChemin(Chemin chemin) {

        if (chemin == null) {
            return null;
        }

        Iterator<Chemin> it_chemin = this.chemins.iterator();
        int i = 0;
        while (it_chemin.hasNext()) {
            if (it_chemin.next().getIntersectionDepart().getId() == chemin.getIntersectionArrivee().getId()) {
                break;
            }
            i++;
        }
        this.chemins.add(i, chemin);

        // on met à jour les heures de livraison
        this.CalculerHeuresDemandesLivraisons();

        return chemin;
    }

    /**
     * Calcule, puis retourne le temps total de livraison estimé.
     *
     * @return La durée en seconde du temps de livraison.
     */
    public long getTempsDeLivraison() {
        return this.tempsDeLivraison;
    }
}
