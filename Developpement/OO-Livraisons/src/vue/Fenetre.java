/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author romain
 */
public class Fenetre extends JFrame{
    protected JMenuBar barreMenus;
    
    protected JMenu fichier;
    protected JMenuItem chargerPlan;
    protected JMenuItem chargerTournee;
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
    protected VueGraphique vueGraphique;
    
    protected JSplitPane panelSeparationGauche;//contient le gauche et le centre
    protected JSplitPane panelSeparationDroit;//contient le droit et le panelSeparationGauche
    
    protected JButton ajouterLivraison;
    protected JButton supprimerLivraison;
    protected JButton echangerLivraison;
    protected JButton calculerTournee;
    
    public Fenetre()
    {
        barreMenus = new JMenuBar();
        
        //----Fichier-----
        fichier = new JMenu("Fichier");
        chargerPlan = new JMenuItem("Charger Plan");
        chargerTournee = new JMenuItem("Charger Tournée");
        quitter = new JMenuItem("Quitter");
        
        fichier.add(chargerPlan);
        fichier.add(chargerTournee);
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
        
        
        
        
        panelGauche = new JPanel();
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.PAGE_AXIS));
        panelGauche.add(panelBoutons);
        
        panelDroit = new JPanel();
        
        
        panelSeparationGauche = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, panelGauche, vueGraphique);
        panelSeparationGauche.setOneTouchExpandable(true);
        panelSeparationGauche.setDividerLocation(150);
        
        
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
}
