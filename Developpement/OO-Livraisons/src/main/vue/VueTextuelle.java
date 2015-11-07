/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * Élément graphique d'affichage textuel de la vue.
 * @author Kilian
 */
public class VueTextuelle extends JPanel {

    /**
     * La vue dans laquelle s'inscrit la vue textuelle.
     */
    private Vue vue;
    
    /**
     * L'écart entre deux éléments de la vue textuelle.
     */
    private int ecartDemandesLivraisons;

    /**
     * Constructeur d'une VueTextuelle.
     * @param vue La vue dans laquelle s'inscrit la vue textuelle.
     */
    public VueTextuelle(Vue vue) {
        super();
        
        this.vue = vue;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.white);

        this.ecartDemandesLivraisons = 5;
    }

    /**
     * Mets à jour la VueTextuelle. 
     */
    public void UpdateVueTextuelle() {
        this.removeAll();
        Iterator<VueFenetreLivraison> it_fenetreVue = this.vue.getVueEnsembleLivraisons().getListVueFenetresLivraison();
        while (it_fenetreVue.hasNext())//pour toutes les fenetres, on change la coloration
        {
            Iterator<VueDemandeLivraison> it_vueDemandeLivraison = it_fenetreVue.next().getVueDemandeLivraisonVue();
            while(it_vueDemandeLivraison.hasNext()){

                this.add(it_vueDemandeLivraison.next());
                this.add(Box.createRigidArea(new Dimension(0, this.ecartDemandesLivraisons)));
            }
        }

        this.revalidate();
        this.repaint();
    }

}
