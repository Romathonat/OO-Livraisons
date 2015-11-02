/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import modele.EnsembleLivraisons;
import modele.Plan;

/**
 *
 * @author romain
 */

public class Fenetre extends JFrame{
    
    protected JMenuBar barreMenus;
    
    protected JMenu fichier;
    protected JMenuItem chargerPlan;
    protected JMenuItem chargerDemandesLivraisons;
    protected JMenuItem quitter;
    
    protected JMenu edition;
    protected JMenuItem annuler;
    protected JMenuItem retablir;
    
    protected JMenu apropos;
    protected JMenuItem descriptionProjet;
    
    protected JPanel panelPrincipal;
    protected JPanel panelGauche;
    protected JPanel panelDroit;
    protected JPanel panelBoutons;
    protected JPanel legende;
    protected VueGraphique vueGraphique;
    
    protected JSplitPane panelSeparationGauche;//contient le gauche et le centre
    protected JSplitPane panelSeparationDroit;//contient le droit et le panelSeparationGauche
    
    protected JButton ajouterLivraison;
    protected JButton supprimerLivraison;
    protected JButton echangerLivraison;
    protected JButton calculerTournee;
    
    private Controleur controleur;
    
   
    public Fenetre(Controleur c)
    {
        controleur = c;
        
        barreMenus = new JMenuBar();
        
        //----Fichier-----
        
        fichier = new JMenu("Fichier");
        chargerPlan = new JMenuItem("Charger Plan");
        chargerPlan.addActionListener(new ChargerPlan(this));
        chargerDemandesLivraisons = new JMenuItem("Charger demandes livraisons");
        chargerDemandesLivraisons.addActionListener(new ChargerTournee());
        quitter = new JMenuItem("Quitter");
        
        fichier.add(chargerPlan);
        fichier.add(chargerDemandesLivraisons);
        fichier.add(quitter);
        
        barreMenus.add(fichier);
        
        //--------Edition--------
        edition = new JMenu("Edition");
        annuler = new JMenuItem("Annuler");
        retablir = new JMenuItem("Retablir");
        
        edition.add(annuler);
        edition.add(retablir);
        
        barreMenus.add(edition);
        
        //----A propos-----
        apropos = new JMenu("A propos");
        descriptionProjet = new JMenuItem("A propos");
        apropos.add(descriptionProjet);
        
        barreMenus.add(apropos);
        
        //---------creation des boutons
        ajouterLivraison = new JButton("Ajouter Livraison");
        supprimerLivraison = new JButton("Supprimer Livraison");
        echangerLivraison = new JButton("Echanger Livraison");
        calculerTournee = new JButton("Calculer Tournée");
        
        //------Organisation des Pannels
        vueGraphique = new VueGraphique();
        vueGraphique.setLayout(null);
        
        panelBoutons = new JPanel();
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.PAGE_AXIS));
        int ecartBoutons = 15;
        
        panelBoutons.add(Box.createRigidArea(new Dimension(0,ecartBoutons)));
        panelBoutons.add(ajouterLivraison);
        panelBoutons.add(Box.createRigidArea(new Dimension(0,ecartBoutons)));
        panelBoutons.add(supprimerLivraison);
        panelBoutons.add(Box.createRigidArea(new Dimension(0,ecartBoutons)));
        panelBoutons.add(echangerLivraison);
        panelBoutons.add(Box.createRigidArea(new Dimension(0,ecartBoutons)));
        panelBoutons.add(calculerTournee);
        panelBoutons.add(Box.createRigidArea(new Dimension(0,ecartBoutons)));
        
        Dimension tailleBouton = new Dimension(175,30);
        
        ajouterLivraison.setAlignmentX(Component.CENTER_ALIGNMENT);
        ajouterLivraison.setMinimumSize(tailleBouton);
        ajouterLivraison.setMaximumSize(tailleBouton);
        supprimerLivraison.setAlignmentX(Component.CENTER_ALIGNMENT);
        supprimerLivraison.setMinimumSize(tailleBouton);
        supprimerLivraison.setMaximumSize(tailleBouton);
        echangerLivraison.setAlignmentX(Component.CENTER_ALIGNMENT);
        echangerLivraison.setMinimumSize(tailleBouton);
        echangerLivraison.setMaximumSize(tailleBouton);
        calculerTournee.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculerTournee.setMinimumSize(tailleBouton);
        calculerTournee.setMaximumSize(tailleBouton);
        
        //----------LEGENDE----------
        legende = new JPanel();
        
        JLabel titre = new JLabel("Legende:");
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        legende.setLayout(new BoxLayout(legende, BoxLayout.PAGE_AXIS));
        int ecartLegende = 15;
        Dimension tailleEltLegende = new Dimension(210,30);
        
        ElementLegende neutre = new ElementLegende(Color.LIGHT_GRAY, "Intersection après chargement");
        ElementLegende demandeF1 = new ElementLegende(Color.BLUE, "Demande Fenetre 1");
        ElementLegende demandeF2 = new ElementLegende(Color.MAGENTA, "Demande Fenetre 2");
        ElementLegende demandeF3 = new ElementLegende(Color.ORANGE, "Demande Fenetre 3");
        
        legende.add(titre);
        
        legende.add(Box.createRigidArea(new Dimension(0,ecartLegende)));
        legende.add(neutre);
        neutre.setMinimumSize(tailleEltLegende);
        neutre.setMaximumSize(tailleEltLegende);
        
        legende.add(Box.createRigidArea(new Dimension(0,ecartLegende)));
        legende.add(demandeF1);
        demandeF1.setMinimumSize(tailleEltLegende);
        demandeF1.setMaximumSize(tailleEltLegende);
        
        legende.add(Box.createRigidArea(new Dimension(0,ecartLegende)));
        legende.add(demandeF2);
        demandeF2.setMinimumSize(tailleEltLegende);
        demandeF2.setMaximumSize(tailleEltLegende);
        
        legende.add(Box.createRigidArea(new Dimension(0,ecartLegende)));
        legende.add(demandeF3);
        demandeF3.setMinimumSize(tailleEltLegende);
        demandeF3.setMaximumSize(tailleEltLegende);
               
        legende.add(Box.createRigidArea(new Dimension(0,300))); //on ajoute de l'espace à la fin pour que la legende ne prenne pas toute la place
        
        panelGauche = new JPanel();
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.PAGE_AXIS));
        panelGauche.add(panelBoutons);
        panelGauche.add(legende);
        
        panelDroit = new JPanel();
        
        
        panelSeparationGauche = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, panelGauche, vueGraphique);
        panelSeparationGauche.setOneTouchExpandable(true);
        panelSeparationGauche.setDividerLocation(210);
        
        
        panelSeparationDroit = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, panelSeparationGauche, panelDroit);
        panelSeparationDroit.setOneTouchExpandable(true);
        panelSeparationDroit.setDividerLocation(700);
        
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(barreMenus, BorderLayout.NORTH);
        panelPrincipal.add(panelSeparationDroit, BorderLayout.CENTER);
        
        
        this.add(panelPrincipal);
        
        
        //les parametres de la JFrame
        this.setSize(900,600);
        this.setMinimumSize(new Dimension(600, 400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("OO-Livraisons");
        this.setVisible(true);
    }
 
    private class ChargerPlan implements ActionListener {
        
        JFrame frameParent;
        public ChargerPlan(JFrame frameParent)
        {
            this.frameParent = frameParent;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
                Plan monPlan = controleur.chargerPlan(); 
                vueGraphique.removeAll();
                vueGraphique.drawPlan(monPlan);
                revalidate();
                repaint();
        }
    }
    private class ChargerTournee implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            EnsembleLivraisons livraisons = controleur.chargerLivraisons();
            
            try { //au cas ou un point n'est pas dejà dessiné
                vueGraphique.drawLivraisons(livraisons);
            } catch (Exception ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
            revalidate();
            repaint();
        }
    }
}
