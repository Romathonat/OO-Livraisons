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
 *
 * @author Nicolas
 */
public class VueTournee {
    private List<VueChemin> listCheminVue;
    private Tournee tournee;
    private Vue vue;
    
    public VueTournee(Vue vue, Tournee tournee){
        this.vue = vue;
        this.tournee = tournee;
        listCheminVue = new ArrayList<>();
        if (this.tournee != null){
            Iterator<Chemin> it_chemin = this.tournee.getChemins();

            while (it_chemin.hasNext()) {
                Chemin chemin = it_chemin.next();
                VueFenetreLivraison fenetreLivraisonVue = this.vue.getFenetreCorrespondante(chemin.getLivraisonArrivee());

                if (fenetreLivraisonVue == null) {
                    continue;
                }

                this.listCheminVue.add(new VueChemin(fenetreLivraisonVue, chemin));
                fenetreLivraisonVue.addVueDemandeLivraison(new VueDemandeLivraison(fenetreLivraisonVue, chemin.getLivraisonArrivee()));
            }
        }
    }

    /**
     * @return the listCheminVue
     */
    public Iterator<VueChemin> getListCheminVue() {
        List constList = Collections.unmodifiableList(this.listCheminVue);
        return constList.iterator();
    }

    /**
     * @return the tournee
     */
    public Tournee getTournee() {
        return tournee;
    }
}
