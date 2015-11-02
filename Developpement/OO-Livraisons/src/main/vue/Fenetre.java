/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import java.awt.BorderLayout;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    protected VueTextuelle vueTextuelle;
    protected JPanel panelBoutons;
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
        
        panelGauche = new JPanel();
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.PAGE_AXIS));
        panelGauche.add(panelBoutons);
        
        
        // Tests avec la scrollbar
        vueTextuelle = new VueTextuelle();
        vueTextuelle.setLayout(new BoxLayout(vueTextuelle, BoxLayout.PAGE_AXIS));
        panelDroit = new JPanel();
        JScrollPane scrollPane = new JScrollPane(vueTextuelle);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setBounds(0, 0, 300, 600);
        
        //panelDroit.add(scrollPane);
        //panelDroit.setPreferredSize(new Dimension(300,600));
        
        panelSeparationGauche = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, panelGauche, vueGraphique);
        panelSeparationGauche.setEnabled(false);
        //panelSeparationGauche.setOneTouchExpandable(true);
        panelSeparationGauche.setDividerLocation(150);
        
        panelSeparationDroit = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, panelSeparationGauche, scrollPane);
        panelSeparationDroit.setEnabled(false);
        //panelSeparationDroit.setOneTouchExpandable(true);
        panelSeparationDroit.setDividerLocation(700);
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(barreMenus, BorderLayout.NORTH);
        panelPrincipal.add(panelSeparationDroit, BorderLayout.CENTER);
        
        this.add(panelPrincipal);
        
        //les parametres de la JFrame
        this.setSize(1000,600);
        //this.setMinimumSize(new Dimension(600, 400));
        this.setResizable(false);
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
            
            int a = JOptionPane.showConfirmDialog(frameParent, "Le plan courant va être écrasé, continuer ?", "Charger un plan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (a == JOptionPane.YES_OPTION) {
                Plan monPlan = controleur.chargerPlan(); 
                vueGraphique.drawPlan(monPlan);
                revalidate();
                repaint();
            }
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
            
            vueTextuelle.writeLivraisons(livraisons);
            
            revalidate();
            repaint();
        }
    }
}
