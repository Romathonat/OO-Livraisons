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
 *
 * @author Kilian
 */
public class VueTextuelle extends JPanel {

    private int ecartDemandesLivraisons;

    public VueTextuelle() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.white);

        this.ecartDemandesLivraisons = 5;
    }

    public void UpdateVueTextuelle(Iterator<VueFenetreLivraison> it_fenetreVue) {

        // on raz la fenetre
        this.removeAll();

        while (it_fenetreVue.hasNext())//pour toutes les fenetres, on change la coloration
        {
            for (VueDemandeLivraison vueDemandeLivraison : it_fenetreVue.next().listDemandesLivraisonVue){

                this.add(vueDemandeLivraison);
                this.add(Box.createRigidArea(new Dimension(0, this.ecartDemandesLivraisons)));
            }
        }

        this.revalidate();
        this.repaint();
    }

}
