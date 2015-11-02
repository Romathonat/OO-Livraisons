/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author tfavrot
 */
public class Tournee {

    // Attributs
    private List<Chemin> chemins;

    // Methodes
    public Tournee() {
        chemins = new ArrayList<>();
    }

    public void CalculerHeuresDemandesLivraisons() {

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
    public double getTempsDeLivraison() {
        Iterator<Chemin> it_chemin = Collections.unmodifiableList(chemins).iterator();
        double dureeTotale = 0;
        
        while (it_chemin.hasNext())
        {
            dureeTotale += it_chemin.next().getDuree();
        }
        
        return dureeTotale;
    }
}
