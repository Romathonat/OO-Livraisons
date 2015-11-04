/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import modele.Chemin;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import modele.Tournee;

/**
 *
 * @author Kilian
 */
public class VueTextuelle extends JPanel {

    private Collection<DemandeLivraisonVue> mesDemandesLivraisons;
    private int ecartDemandesLivraisons;

    public VueTextuelle() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.white);

        this.ecartDemandesLivraisons = 5;
    }

    public void UpdateVueTextuelle(Iterator<DemandeLivraisonVue> it_demandeLivraisonVue) {

        // on raz la fenetre
        this.removeAll();

        int compteurDemandesLivraisons = 0;

        while (it_demandeLivraisonVue.hasNext())//pour toutes les fenetres, on change la coloration
        {
            DemandeLivraisonVue demandeLivraisonVue = it_demandeLivraisonVue.next();
            System.out.println(demandeLivraisonVue);
            //this.mesDemandesLivraisons.add(demandeLivraisonVue);
            this.add(demandeLivraisonVue);
            this.add(Box.createRigidArea(new Dimension(0, this.ecartDemandesLivraisons)));
        }

        this.revalidate();
        this.repaint();
    }
}
