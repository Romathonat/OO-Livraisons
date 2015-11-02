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
import java.util.Map.Entry;
import java.util.Observable;

/**
 *
 * @author gkheng
 */
public class EnsembleLivraisons extends Observable{

    private List<FenetreLivraison> fenetresLivraison;
    private Intersection entrepot;

    
    /**
     * Constructeur standard de la classe EnsembleLivraison.
     */
    public EnsembleLivraisons() {
        this.fenetresLivraison = new ArrayList<FenetreLivraison>();
        this.entrepot = null;
    }

    /**
     * Retourne l'Intersection où est situé l'entrepôt.
     *
     * @return l'Intersection où est situé l'entrepôt.
     */
    public Intersection getEntrepot() {
        return entrepot;
    }

    /**
     * Définis l'Intersection où est situé l'entrepôt.
     *
     * @return L'intersection où est situé l'entrepot.
     */
    public Intersection setEntrepot(Intersection entrepot) {
        return this.entrepot = entrepot;
    }
    
    /**
     * Ajoute une fenetre temporelle de livraison à l'ensemble des fenêtres. En
     * accord avec le cahier des charges, cette fonction vérifie que les
     * différentes fenêtres de livraisons ne se chevauchent pas. La fenêtre doit
     * être "bien formée" càd heureDébut avant heureFin.
     *
     * @param heureDebut L'heure de début de la fenetre temporelle à ajouter.
     * @param heureFin L'heure de fin de la fenetre temporelle à ajouter.
     * @return La fenetre si les conditions d'ajout sont respectées, null sinon.
     */
    public FenetreLivraison ajouteFenetreDeLivraison(Date heureDebut, Date heureFin) {
        // Test de cohérence.
        if (heureDebut.compareTo(heureFin) >= 0) {
            return null;
        }

        // Detection d'intersections éventuelles entre les plages horaires.
        for (int i = 0; i < fenetresLivraison.size(); i++ ) {
            FenetreLivraison fen = fenetresLivraison.get(i);
            if (fen.getHeureDebut().compareTo(heureDebut) < 0) {
                if (fen.getHeureFin().compareTo(heureDebut) > 0) { // intersection type ABAB ou ABBA
                    return null;
                }
            } else {
                if (fen.getHeureDebut().compareTo(heureFin) < 0) { // intersection type BABA ou BAAB
                    return null;
                }
            }
        }
        
        FenetreLivraison fenetre = new FenetreLivraison (heureDebut, heureFin);
        this.fenetresLivraison.add(fenetre);
        return fenetre;
    }

    /**
     * Retourne un iterator sur les fenetres de Livraison.
     *
     * @return un iterator sur les fenetres de Livraison.
     */
    public Iterator<FenetreLivraison> getFenetresLivraison() {
        Collection constCollection = Collections.unmodifiableCollection(fenetresLivraison);
        return constCollection.iterator();
    }
}
