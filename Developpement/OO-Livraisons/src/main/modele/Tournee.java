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
 *
 * @author tfavrot
 */
public class Tournee {

    // Attributs
    private List<Chemin> chemins;
    long tempsDeLivraison; // en secondes.

    // Methodes
    public Tournee() {
        chemins = new ArrayList<Chemin>();
        tempsDeLivraison = -1;
    }

    /**
     * Calcule les horaires auxquels les points de livraisons seront atteints.
     * Il convient d'avoir calculé la tournée auparavant.
     */
    protected void CalculerHeuresDemandesLivraisons() {
        Iterator<Chemin> it_chemin = this.getChemins();

        // initialisation
        Date instantCourant = (Date) this.chemins.get(0).getLivraisonArrivee().getFenetreLivraison().getHeureDebut().clone();

        while (it_chemin.hasNext()) {
            // une telle implémentation n'est pas optimale mais le code de Date ne permet pas de faire autrement.
            Chemin cheminCourant = it_chemin.next();
            instantCourant.setTime(instantCourant.getTime() + (long) (cheminCourant.getDuree() * 1000));

            // on regarde si on se trouve dans la fenetre de la prochaine livraison.
            if (cheminCourant.getLivraisonArrivee().getFenetreLivraison().getHeureDebut().compareTo(instantCourant) < 0) { // si c'est le cas, on ivre.
                instantCourant.setTime(instantCourant.getTime() + cheminCourant.getLivraisonArrivee().getTempsArret()*1000);
            } else { // sinon on attend le debut de fenetre puis on livre avant de continuer
                instantCourant.setTime(cheminCourant.getLivraisonArrivee().getFenetreLivraison().getHeureDebut().getTime() + cheminCourant.getLivraisonArrivee().getTempsArret()*1000);
            }
            cheminCourant.getLivraisonArrivee().setHeureLivraison(instantCourant);
        }

        // on met à jour le temps de livraison en secondes.
        this.tempsDeLivraison = (instantCourant.getTime() - this.chemins.get(0).getLivraisonArrivee().getFenetreLivraison().getHeureDebut().getTime()) / 1000;
    }

    public Iterator<Chemin> getChemins() {
        List<Chemin> constList = Collections.unmodifiableList(chemins);
        return constList.iterator();
    }

    /**
     * Ajoute un chemin à la collection de chemins. Cette fonction vérifie que
     * le chemin passé en paramètre n'est pas "null".
     *
     * @param chemin le chemin à ajouter à la collection
     * @return Le chemin si les conditions d'ajout sont respectées, null sinon.
     */
    public Chemin AjouterChemin(Chemin chemin) {
        if (chemin != null) {
            this.chemins.add(chemin);
            return chemin;
        }
        return null;
    }

    /**
     * Calcule puis retourne le temps total de livraison estimé.
     *
     * @return La durée en seconde du temps de livraison.
     */
    public long getTempsDeLivraison() {
        return this.tempsDeLivraison;
    }
}
