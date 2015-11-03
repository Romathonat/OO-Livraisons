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
import java.awt.Point;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import modele.Intersection;
import modele.Plan;
import modele.Troncon;

/**
 *
 * @author Kilian
 */
public class VueTextuelle extends JPanel {
    
    private Collection<DemandeLivraisonVue> mesDemandesLivraisons;
    private int ecartDemandesLivraisons = 5;
    
    public VueTextuelle()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.white);
    }
    
    public void writeLivraisons(EnsembleLivraisons livraisons)
    {
        Iterator<FenetreLivraison> it = livraisons.getFenetresLivraison();
        Color[] mesCouleurs = new Color[4];
        mesCouleurs[0] = Color.BLUE;
        mesCouleurs[1] = Color.MAGENTA;
        mesCouleurs[2] = Color.ORANGE;
        mesCouleurs[3] = Color.GREEN;
        int k = 0;
        int compteurDemandesLivraisons = 0;
        
        while(it.hasNext())//pour toutes les fenetres, on change la coloration
        {   
            Color CouleurCourante = mesCouleurs[k++%4];
            FenetreLivraison maFenetre = it.next();
            Iterator<DemandeLivraison> itDemandes = maFenetre.getDemandesLivraison();
            while(itDemandes.hasNext())//pour toutes les demandes de cette fenetre
            {
                DemandeLivraisonVue demandeLivraisonVue = new DemandeLivraisonVue(itDemandes.next(), CouleurCourante);
                System.out.println(demandeLivraisonVue);
                //this.mesDemandesLivraisons.add(demandeLivraisonVue);            
                this.add(demandeLivraisonVue);
                this.add(Box.createRigidArea(new Dimension(0,this.ecartDemandesLivraisons)));
            }
        }
        this.revalidate();
        this.repaint();
    }
}
