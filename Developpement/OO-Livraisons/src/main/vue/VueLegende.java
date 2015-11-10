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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Élement graphique de légende d'une vue.
 *
 * @author Nicolas
 */
public class VueLegende extends JPanel {

    /**
     * La vue dans laquelle s'inscrit la légende.
     */
    private Vue vue;

    /**
     * L'ecart entre deux éléments de la légende.
     */
    private final int ecartLegende = 15;

    /**
     * La taille d'un élément de légende.
     */
    private final Dimension tailleEltLegende = new Dimension(210, 20);

    /**
     * Contructeur d'une VueLegende.
     *
     * @param vue
     */
    public VueLegende(Vue vue) {
        super();
        this.vue = vue;
    }

    /**
     * Mets à jour la légende à partir de la vue.
     */
    public void updateLegende() {
        this.removeAll();

        JLabel titre = new JLabel("Legende:");
        Font font = titre.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titre.setFont(font.deriveFont(attributes));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(titre);

        if (this.vue.getVuePlan().getPlan() != null) {
            VueElementLegende neutre = new VueElementLegende(Color.LIGHT_GRAY, "Intersection");
            this.add(Box.createRigidArea(new Dimension(0, this.ecartLegende)));
            this.add(neutre);
            neutre.setMinimumSize(this.tailleEltLegende);
            neutre.setMaximumSize(this.tailleEltLegende);
        }

        if (this.vue.getVueEnsembleLivraisons().getEnsembleLivraison() != null) {

            VueElementLegende legendeFenetre = new VueElementLegende(GenerateurCouleur.getCouleurEntrepot(), "Entrepot");
            this.add(Box.createRigidArea(new Dimension(0, this.ecartLegende)));
            this.add(legendeFenetre);
            legendeFenetre.setMinimumSize(this.tailleEltLegende);
            legendeFenetre.setMaximumSize(this.tailleEltLegende);

            DateFormat df = new SimpleDateFormat("HH:mm:ss");

            Iterator<VueFenetreLivraison> it_flv = this.vue.getVueEnsembleLivraisons().getListVueFenetresLivraison();
            int i = 0;
            while (it_flv.hasNext()) {
                VueFenetreLivraison currentVfl = it_flv.next();
                i++;
                String heureDebutString = df.format(currentVfl.getFenetreLivraison().getHeureDebut());
                String heureFinString = df.format(currentVfl.getFenetreLivraison().getHeureFin());
                legendeFenetre = new VueElementLegende(currentVfl.getCouleur(), "Livraison Fenetre " + heureDebutString + "-" + heureFinString);
                this.add(Box.createRigidArea(new Dimension(0, this.ecartLegende)));
                this.add(legendeFenetre);
                legendeFenetre.setMinimumSize(this.tailleEltLegende);
                legendeFenetre.setMaximumSize(this.tailleEltLegende);
            }

        }

        if (this.vue.getVueTournee().getTournee() != null) {
            VueElementLegende legendeFenetre = new VueElementLegende(
                    GenerateurCouleur.getCouleurDemandeHorsHoraire(),
                    "Livraison Hors Horaire");
            this.add(Box.createRigidArea(new Dimension(0, this.ecartLegende)));
            this.add(legendeFenetre);
            legendeFenetre.setMinimumSize(this.tailleEltLegende);
            legendeFenetre.setMaximumSize(this.tailleEltLegende);
        }

        this.add(Box.createRigidArea(new Dimension(0, 300)));

        this.validate();
        this.repaint();

    }

}
