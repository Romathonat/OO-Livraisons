/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import modele.Chemin;
import modele.Tournee;

/**
 * Représente graphiquement une tournée.
 *
 * @author Nicolas
 */
public class VueTournee {

    /**
     * La liste de VueChemin.
     */
    private List<VueChemin> VuelistChemin;

    /**
     * La tournée associée à la VueTournee.
     */
    private Tournee tournee;

    /**
     * La vue dans laquelle s'inscrit la VueTournee.
     */
    private Vue vue;

    /**
     * Constructeur d'une VueTournee.
     *
     * @param vue La vue dans laquelle s'inscrit la VueTournee.
     * @param tournee La tournée associée à la VueTournee.
     */
    protected VueTournee(Vue vue, Tournee tournee) {
        this.vue = vue;
        this.tournee = tournee;
        VuelistChemin = new ArrayList<>();

        if (this.tournee != null) {
            this.vue.getVueEnsembleLivraisons().clearDemandeLivraisons(); //On les enlève pour les remmettre dans l'ordre
            Iterator<Chemin> it_chemin = this.tournee.getChemins();
            while (it_chemin.hasNext()) {
                Chemin chemin = it_chemin.next();

                VueFenetreLivraison fenetreLivraisonVue = this.vue.getFenetreCorrespondante(chemin.getLivraisonArrivee());

                if (fenetreLivraisonVue == null) {//cas de l'entrepôt
                    this.VuelistChemin.add(new VueChemin(new VueFenetreLivraison(chemin.getLivraisonArrivee().getFenetreLivraison(), null, 0), chemin));
                    continue;
                }

                this.VuelistChemin.add(new VueChemin(fenetreLivraisonVue, chemin));
                fenetreLivraisonVue.addVueDemandeLivraison(new VueDemandeLivraison(fenetreLivraisonVue, chemin.getLivraisonArrivee()));
            }
        }
    }

    /**
     * Retourne un itérateur sur la liste de VueChemin.
     *
     * @return Un itérateur sur la liste de VueChemin.
     */
    protected Iterator<VueChemin> getListVueChemin() {
        List constList = Collections.unmodifiableList(this.VuelistChemin);
        return constList.iterator();
    }

    /**
     * Retourne la tournée associée à la VueTournee.
     *
     * @return La tournée associée à la VueTournee.
     */
    protected Tournee getTournee() {
        return tournee;
    }
}
