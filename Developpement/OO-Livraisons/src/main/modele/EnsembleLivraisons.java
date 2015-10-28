/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author gkheng
 */
public class EnsembleLivraisons {

    private Map<Date, FenetreLivraison> fenetresLivraison;
    private Intersection Entrepot;

    
    /**
     * Constructeur standard de la classe EnsembleLivraison.
     */
    public EnsembleLivraisons() {
        this.fenetresLivraison = new TreeMap<Date, FenetreLivraison>();
        this.Entrepot = null;
    }

    /**
     * Retourne l'Intersection où est situé l'entrepôt.
     *
     * @return l'Intersection où est situé l'entrepôt.
     */
    public Intersection getEntrepot() {
        return Entrepot;
    }

    /**
     * Définis l'Intersection où est situé l'entrepôt.
     *
     * @return L'intersection où est situé l'entrepot.
     */
    public Intersection setEntrepot(Intersection Entrepot) {
        return this.Entrepot = Entrepot;
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
        for (Entry<Date, FenetreLivraison> entry : this.fenetresLivraison.entrySet()) {
            if (entry.getValue().getHeureDebut().compareTo(heureDebut) < 0) {
                if (entry.getValue().getHeureFin().compareTo(heureDebut) > 0) { // intersection type ABAB ou ABBA
                    return null;
                }
            } else {
                if (entry.getValue().getHeureDebut().compareTo(heureFin) < 0) { // intersection type BABA ou BAAB
                    return null;
                }
            }
        }
        
        FenetreLivraison fenetre = new FenetreLivraison (heureDebut, heureFin);
        this.fenetresLivraison.put(heureDebut, fenetre);
        return fenetre;
    }

}
