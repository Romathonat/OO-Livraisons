/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * Élément graphique d'affichage textuel de la vue.
 *
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
     *
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
     * Mets à jour la VueTextuelle concernant la liste de livraisons affichées.
     * Plus precisement, rafraichit completement la liste des livraisons
     * affichées.
     */
    public void mettreAJourListeDemandes() {

        this.removeAll();
        
        
        Iterator<VueFenetreLivraison> it_fenetreVue = this.vue.getVueEnsembleLivraisons().getListVueFenetresLivraison();
        while (it_fenetreVue.hasNext())
        {
            VueFenetreLivraison currentFenetreVue = it_fenetreVue.next();
            this.add(currentFenetreVue);
            this.add(Box.createRigidArea(new Dimension(0, this.ecartDemandesLivraisons)));
            Iterator<VueDemandeLivraison> it_vueDemandeLivraison = currentFenetreVue.getVueDemandeLivraisonList();
            while (it_vueDemandeLivraison.hasNext()) {
                this.add(it_vueDemandeLivraison.next());
                this.add(Box.createRigidArea(new Dimension(0, this.ecartDemandesLivraisons)));
            }
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Mets à jour la VueTextuelle. Plus precisement, rafraichit la liste des
     * livraisons.
     */
    public void mettreAjourLivraisonsSelectionnees() {

        // On commence par deselectionner toutes les livraisons.
        Iterator<VueFenetreLivraison> it_fenetreVue = this.vue.getVueEnsembleLivraisons().getListVueFenetresLivraison();
        while (it_fenetreVue.hasNext()) {
            Iterator<VueDemandeLivraison> it_vueDemandeLivraison = it_fenetreVue.next().getVueDemandeLivraisonList();
            while (it_vueDemandeLivraison.hasNext()) {
                it_vueDemandeLivraison.next().Selectionner(-1);
            }
        }

        Iterator<Integer> it_DLSelectionnee = vue.getInterSelectionne();
        if (!it_DLSelectionnee.hasNext()) { // inutile de tester l'ensemble des Demandes de livraison si aucune intersection est selectionnee.
            return;
        }

        it_fenetreVue = this.vue.getVueEnsembleLivraisons().getListVueFenetresLivraison();
        while (it_fenetreVue.hasNext()) {
            Iterator<VueDemandeLivraison> it_vueDemandeLivraison = it_fenetreVue.next().getVueDemandeLivraisonList();
            while (it_vueDemandeLivraison.hasNext()) {
                // Reperage des demandes de livraison selectionnee.
                VueDemandeLivraison vueDemandeLivraison = it_vueDemandeLivraison.next();
                it_DLSelectionnee = vue.getInterSelectionne();
                while (it_DLSelectionnee.hasNext() && !(vueDemandeLivraison.Selectionner(it_DLSelectionnee.next())));
            }
        }

        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Iterator<VueFenetreLivraison> it_fenetreVue = this.vue.getVueEnsembleLivraisons().getListVueFenetresLivraison();
        while (it_fenetreVue.hasNext()) {
            Iterator<VueDemandeLivraison> it_vueDemandeLivraison = it_fenetreVue.next().getVueDemandeLivraisonList();
            while (it_vueDemandeLivraison.hasNext()) {
                it_vueDemandeLivraison.next().repaint();
            }
        }
    }

    
    
}
