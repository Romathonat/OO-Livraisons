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
 *
 * @author Nicolas
 */
public class VueEnsembleLivraisons{
    private List<VueFenetreLivraison> listVueFenetreLivraison;
    private EnsembleLivraisons ensembleLivraison;
    private Vue vue;
    
    public VueEnsembleLivraisons(Vue vue, EnsembleLivraisons ensembleLivraisons){
        this.vue = vue;
        this.ensembleLivraison = ensembleLivraisons;
        this.listVueFenetreLivraison = new ArrayList<>();
        
        if(this.ensembleLivraison != null){
            Iterator<FenetreLivraison> it_fenetre = this.ensembleLivraison.getFenetresLivraison();
            Iterator<DemandeLivraison> it_demande = null;

            while (it_fenetre.hasNext()) {
                FenetreLivraison fenetreLivraison = it_fenetre.next();
                VueFenetreLivraison fenetreLivraisonVue = new VueFenetreLivraison(fenetreLivraison, this.vue.getGenerateurCouleur().getCouleurSuivante());
                this.listVueFenetreLivraison.add(fenetreLivraisonVue);

                it_demande = fenetreLivraison.getDemandesLivraison();
                while (it_demande.hasNext()) {
                    fenetreLivraisonVue.addVueDemandeLivraison(new VueDemandeLivraison(fenetreLivraisonVue, it_demande.next()));
                }
            }
        }
    }
    
    protected void clearDemandeLivraisons(){
        for (VueFenetreLivraison vueFenetreLivraison : this.listVueFenetreLivraison) {
            vueFenetreLivraison.clearDemandesLivraisonVue();
        }
    }

    /**
     * @return the listFenetresLivraisonVue
     */
    public Iterator<VueFenetreLivraison> getListVueFenetresLivraison() {
        List constList = Collections.unmodifiableList(this.listVueFenetreLivraison);
        return constList.iterator();
    }

    /**
     * @return the ensembleLivraison
     */
    public EnsembleLivraisons getEnsembleLivraison() {
        return ensembleLivraison;
    }
    
}