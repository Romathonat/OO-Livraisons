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
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;

/**
 * Représente graphiquement un ensemble de livraison. Contient une liste de
 * VueFenetreLivraison associées aux fenêtre de livraison de l'ensemble de
 * livraison.
 *
 * @author Nicolas
 */
public class VueEnsembleLivraisons {

    /**
     * La liste des vueFenetreLivraison associées aux fenêtre de livraison de
     * l'ensemble de livraison.
     */
    private List<VueFenetreLivraison> listVueFenetreLivraison;

    /**
     * L'ensemble de livraison associé à la VueEnsembleLivraison.
     */
    private EnsembleLivraisons ensembleLivraison;

    /**
     * Constructeur d'une VueEnsembleLivraison.
     *
     * @param ensembleLivraisons L'ensemble de livraison associé à la
     * VueEnsembleLivraison.
     */
    protected VueEnsembleLivraisons(EnsembleLivraisons ensembleLivraisons) {
        this.ensembleLivraison = ensembleLivraisons;

        this.listVueFenetreLivraison = new ArrayList<>();

        GenerateurCouleur.getInstance().initGenerateur();

        if (this.ensembleLivraison != null) {
            Iterator<FenetreLivraison> it_fenetre = this.ensembleLivraison.getFenetresLivraison();
            Iterator<DemandeLivraison> it_demande = null;
            int nbFenetres = 0;

            while (it_fenetre.hasNext()) {
                FenetreLivraison fenetreLivraison = it_fenetre.next();
                VueFenetreLivraison fenetreLivraisonVue = new VueFenetreLivraison(fenetreLivraison, GenerateurCouleur.getInstance().getCouleur(), ++nbFenetres);
                this.listVueFenetreLivraison.add(fenetreLivraisonVue);

                it_demande = fenetreLivraison.getDemandesLivraison();
                while (it_demande.hasNext()) {
                    fenetreLivraisonVue.addVueDemandeLivraison(new VueDemandeLivraison(fenetreLivraisonVue, it_demande.next()));
                }
            }
        }
    }

    /**
     * Vide la liste des VueFenetreLivraison.
     */
    protected void clearDemandeLivraisons() {
        for (VueFenetreLivraison vueFenetreLivraison : this.listVueFenetreLivraison) {
            vueFenetreLivraison.clearVuesDemandesLivraison();
        }
    }

    /**
     * Retourne un itérateur sur la liste de VueFenetreLivraison.
     *
     * @return Un itérateur sur la liste de VueFenetreLivraison.
     */
    protected Iterator<VueFenetreLivraison> getListVueFenetresLivraison() {
        List constList = Collections.unmodifiableList(this.listVueFenetreLivraison);
        return constList.iterator();
    }

    /**
     * Retourne l'ensemble de livraison associé à la VueEnsembleLivraison.
     *
     * @return L'ensemble de livraison associé à la VueEnsembleLivraison.
     */
    protected EnsembleLivraisons getEnsembleLivraison() {
        return ensembleLivraison;
    }

}
