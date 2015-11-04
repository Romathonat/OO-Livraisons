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
    private List<FenetreLivraisonVue> mesFenetresLivraisons;
    private int ecartDemandesLivraisons = 5;
    private Color couleurs[];

    public VueTextuelle() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.white);
        this.mesFenetresLivraisons = new ArrayList<FenetreLivraisonVue>();

        // TBD create a new color generator.
        couleurs = new Color[3];
        couleurs[0] = Color.BLUE;
        couleurs[1] = Color.MAGENTA;
        couleurs[2] = Color.ORANGE;
    }

    public void writeLivraisons(EnsembleLivraisons ensembleLivraisons, Tournee tournee) {

        // on raz la fenetre
        this.removeAll();
        mesFenetresLivraisons.clear();

        Iterator<FenetreLivraison> it_fenetre = ensembleLivraisons.getFenetresLivraison();
        int i = 0;
        /*while (it_fenetre.hasNext()) {
            FenetreLivraisonVue f = new FenetreLivraisonVue(it_fenetre.next());
            f.setCouleur(couleurs[i++]);
        }*/
        
        it_fenetre = ensembleLivraisons.getFenetresLivraison();

        if (tournee == null) {

            int compteurDemandesLivraisons = 0;

            while (it_fenetre.hasNext())//pour toutes les fenetres, on change la coloration
            {
                Color CouleurCourante = couleurs[i++ % 4];
                FenetreLivraison maFenetre = it_fenetre.next();
                Iterator<DemandeLivraison> it_demandes = maFenetre.getDemandesLivraison();

                while (it_demandes.hasNext()) {
                    DemandeLivraisonVue demandeLivraisonVue = new DemandeLivraisonVue(null, it_demandes.next(), CouleurCourante);
                    System.out.println(demandeLivraisonVue);
                    //this.mesDemandesLivraisons.add(demandeLivraisonVue);
                    this.add(demandeLivraisonVue);
                    this.add(Box.createRigidArea(new Dimension(0, this.ecartDemandesLivraisons)));
                }
            }
        } else {

            Iterator<Chemin> it_chemin = tournee.getChemins();

            while (it_chemin.hasNext()) {
        // on récupère la couleur associée.

            }

        }
        this.revalidate();
        this.repaint();
    }
}
