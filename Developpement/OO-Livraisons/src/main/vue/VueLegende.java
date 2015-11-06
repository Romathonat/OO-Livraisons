/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Iterator;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nicolas
 */
public class VueLegende extends JPanel {
    private Vue vue;
    protected int ecartLegende;
    protected Dimension tailleEltLegende;
    
    public VueLegende(Vue vue){
        this.vue = vue;
        this.ecartLegende = 15;
        this.tailleEltLegende = new Dimension(210, 20);
        
        
    }
    
    public void updateLegende(int etat) {
        this.removeAll();

        JLabel titre = new JLabel("Legende:");
        Font font = titre.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titre.setFont(font.deriveFont(attributes));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(titre);

        // on a chargé le plan.
        if (etat > 0) {
            // Legende des intersections.
            VueElementLegende neutre = new VueElementLegende(Color.LIGHT_GRAY, "Intersection");
            this.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
            this.add(neutre);
            neutre.setMinimumSize(tailleEltLegende);
            neutre.setMaximumSize(tailleEltLegende);
        }

        // on a chargé les livraisons.
        if (etat > 1) {

            // legende de l'entrepot
            VueElementLegende legendeFenetre = new VueElementLegende(GenerateurCouleur.getCouleurEntrepot(), "Entrepot");
            this.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
            this.add(legendeFenetre);
            legendeFenetre.setMinimumSize(tailleEltLegende);
            legendeFenetre.setMaximumSize(tailleEltLegende);

            // Legende des fenetres de livraison.
            Iterator<VueFenetreLivraison> it_flv = this.vue.vueEnsembleLivraisons.listFenetresLivraisonVue.iterator();
            int i = 0;
            while (it_flv.hasNext()) {
                i++;
                legendeFenetre = new VueElementLegende(it_flv.next().getCouleur(), "Demande Fenetre " + Integer.toString(i));
                this.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
                this.add(legendeFenetre);
                legendeFenetre.setMinimumSize(tailleEltLegende);
                legendeFenetre.setMaximumSize(tailleEltLegende);
            }

            this.add(Box.createRigidArea(new Dimension(0, 300)));
        }

        this.validate();
        this.repaint();

    }
    
}
