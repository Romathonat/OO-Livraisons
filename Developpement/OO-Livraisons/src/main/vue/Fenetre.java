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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import modele.Intersection;
import modele.Plan;
import modele.Tournee;
import xml.OuvreurFichierXML;
import xmlModele.SerialiseurXML;

/**
 * La fenêter graphique de l'application.
 * @author romain
 */
public class Fenetre extends JFrame {

    protected JMenuBar barreMenus;

    protected JMenu fichier;
    protected JMenuItem chargerPlan;
    protected JMenuItem chargerDemandesLivraisons;
    protected JMenuItem genererFeuilleDeRoute;
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

    protected JSplitPane panelSeparationGauche;//contient le gauche et le centre
    protected JSplitPane panelSeparationDroit;//contient le droit et le panelSeparationGauche

    protected JButton ajouterLivraison;
    protected JButton supprimerLivraison;
    protected JButton echangerLivraison;
    protected JButton calculerTournee;

    /**
     * Le controleur de l'application.
     */
    protected Controleur controleur;
    
    /**
     * La vue courante de l'application
     */
    protected Vue vue;

    /**
     * Constructeur d'une fenêtre.
     * @param controleur Le controleur de l'application.
     */
    public Fenetre(Controleur controleur) {
        this.controleur = controleur;
                
        this.vue = new Vue(this);

        barreMenus = new JMenuBar();

        //----Fichier-----
        fichier = new JMenu("Fichier");
        chargerPlan = new JMenuItem("Charger Plan");
        chargerPlan.addActionListener(new ChargerPlan(this));
        chargerDemandesLivraisons = new JMenuItem("Charger demandes livraisons");
        chargerDemandesLivraisons.addActionListener(new ChargerDemandesLivraisons(this));
        genererFeuilleDeRoute = new JMenuItem("Generer feuille de route");
        genererFeuilleDeRoute.addActionListener(new GenererFeuilleRoute());
        quitter = new JMenuItem("Quitter");

        fichier.add(chargerPlan);
        fichier.add(chargerDemandesLivraisons);
        fichier.add(genererFeuilleDeRoute);
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
        ajouterLivraison.addActionListener(new AjouterLivraison(this));
        supprimerLivraison = new JButton("Supprimer Livraison");
        echangerLivraison = new JButton("Echanger Livraison");
        calculerTournee = new JButton("Calculer Tournée");
        calculerTournee.addActionListener(new CalculerTournee(this));

        //------Organisation des Pannels

        panelBoutons = new JPanel();
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.PAGE_AXIS));
        int ecartBoutons = 15;

        panelBoutons.add(Box.createRigidArea(new Dimension(0, ecartBoutons)));
        panelBoutons.add(ajouterLivraison);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, ecartBoutons)));
        panelBoutons.add(supprimerLivraison);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, ecartBoutons)));
        panelBoutons.add(echangerLivraison);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, ecartBoutons)));
        panelBoutons.add(calculerTournee);
        panelBoutons.add(Box.createRigidArea(new Dimension(0, ecartBoutons)));

        Dimension tailleBouton = new Dimension(175, 30);

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
        panelGauche.add(vue.vueLegende);

        // Tests avec la scrollbar
        
        
        panelDroit = new JPanel();
        JScrollPane scrollPane = new JScrollPane(this.vue.vueTextuelle);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setBounds(0, 0, 300, 600);

        //panelDroit.add(scrollPane);
        //panelDroit.setPreferredSize(new Dimension(300,600));
        panelSeparationGauche = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, this.vue.vueGraphique);
        panelSeparationGauche.setOneTouchExpandable(true);
        panelSeparationGauche.setDividerLocation(210);

        panelSeparationDroit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelSeparationGauche, scrollPane);
        panelSeparationDroit.setEnabled(false);
        //panelSeparationDroit.setOneTouchExpandable(true);
        panelSeparationDroit.setDividerLocation(700);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(barreMenus, BorderLayout.NORTH);
        panelPrincipal.add(panelSeparationDroit, BorderLayout.CENTER);
        panelPrincipal.add(vue.vueStatus, BorderLayout.SOUTH);

        this.add(panelPrincipal);

        //les parametres de la JFrame
        this.setSize(1000, 600);
        //this.setMinimumSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("OO-Livraisons");
        this.setVisible(true);

    }


    /**
     * Retourne la vue courante de l'application.
     * @return La vue courante de l'application.
     */
    public Vue getVue(){
        return this.vue;
    }

    /**
     * Affiche une popup contenant un message particulier.
     *
     * @param message Le message à afficher dans la popup.
     */
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    // ---- Methodes d'activation/desactivation des fonctionnalites ----
    
    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité de chargement du plan.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerChargerPlan(boolean activer) {
        chargerPlan.setEnabled(activer);
    }

    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité de chargement de demandes de livraisons.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerChargerDemandesLivraisons(boolean activer) {
        chargerDemandesLivraisons.setEnabled(activer);
    }

    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité de génération de feuille de route.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerGenererFeuilleRoute(boolean activer) {
        genererFeuilleDeRoute.setEnabled(activer);
    }
    
    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité d'annulation d'une modification de la tournée.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerAnnuler(boolean activer) {
        annuler.setEnabled(activer);
    }
    
    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité de rétablissement d'une modification de la tournée.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerRetablir(boolean activer) {
        retablir.setEnabled(activer);
    }
    
    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité d'ajout de livraison à la tournée.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerAjouterLivraison(boolean activer) {
        ajouterLivraison.setEnabled(activer);
    }
    
    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité de suppression de livraison à la tournée.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerSupprimerLivraison(boolean activer) {
        supprimerLivraison.setEnabled(activer);
    }
    
    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité d'échange de livraisons entre deux livraison de la tournée.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerEchangerLivraison(boolean activer) {
        echangerLivraison.setEnabled(activer);
    }
    
    /**
     * Selon le paramètre <code>activer</code>, active ou désactive graphiquement la 
     * fonctionnalité de calcul de la tournée.
     * @param activer true pour activer la fonctionnalité, 
     * false pour la désactiver.
     */
    public void activerCalculerTournee(boolean activer) {
        calculerTournee.setEnabled(activer);
    }

    // --- Activables uniquement ---
    
    /**
     * Active graphiquement la fonctionnalité de fermeture de l'application.
     * Cette fonctionnalité n'est pas désactivable.
     */
    public void activerQuitter() {
        quitter.setEnabled(true);
    }
    
    /**
     * Active graphiquement la fonctionnalité d'affichage de l'à propos de 
     * l'application.
     * Cette fonctionnalité n'est pas désactivable.
     */
    public void activerAPropos() {
        descriptionProjet.setEnabled(true);
    }

    /**
     * Désactive graphiquement l'ensemble des fonctionnalités désactivables de
     * l'application.
     */
    public void toutDesactiver() {
        this.activerChargerPlan(false);
        this.activerChargerDemandesLivraisons(false);
        this.activerGenererFeuilleRoute(false);
        this.activerAnnuler(false);
        this.activerRetablir(false);
        this.activerAjouterLivraison(false);
        this.activerSupprimerLivraison(false);
        this.activerEchangerLivraison(false);
        this.activerCalculerTournee(false);
    }
        
    // ------ ActionsListeners ------
    private class ChargerPlan implements ActionListener {

        Fenetre fenetre;

        public ChargerPlan(JFrame frameParent) {
            this.fenetre = (Fenetre) frameParent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            File file = OuvreurFichierXML.getInstance().selectionFichierXML(true);
            if (file == null) {
                return;
            }
            Plan nouveauPlan = controleur.chargerPlan(file);
            this.fenetre.vue.updateVuePlan(nouveauPlan);
            revalidate();
            repaint();
        }
    }

    private class ChargerDemandesLivraisons implements ActionListener {

        Fenetre fenetre;

        public ChargerDemandesLivraisons(JFrame frameParent) {
            this.fenetre = (Fenetre) frameParent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            File file = OuvreurFichierXML.getInstance().selectionFichierXML(true);
            if (file == null) {
                return;
            }
            EnsembleLivraisons nouvelEnsembleLivraisons = controleur.chargerLivraisons(file);

            this.fenetre.vue.updateVueEnsembleLivraisons(nouvelEnsembleLivraisons);

            revalidate();
            repaint();
        }
    }

    private class CalculerTournee implements ActionListener {

        Fenetre fenetre;

        public CalculerTournee(JFrame frameParent) {
            this.fenetre = (Fenetre) frameParent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Tournee nouvelleTournee = controleur.calculerTournee();

            this.fenetre.vue.updateVueTournee(nouvelleTournee);
            
            revalidate();
            repaint();
        }
    }

    private class AjouterLivraison implements ActionListener {

        Fenetre fenetre;

        public AjouterLivraison(JFrame frameParent) {
            this.fenetre = (Fenetre) frameParent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fenetre.controleur.demandeAjoutPoint();
            while (fenetre.controleur.isEtatRemplirInformations()) {//tant qu'on est dans l'etat remplir information, on y reste
                DemandeLivraison maDemande = afficherPopUp(); 

                fenetre.controleur.ajouterLivraison(maDemande); //passe dans l'etat suivant si les infos sont bonnes
            }
        }

        public DemandeLivraison afficherPopUp() {
            Integer idInter = vue.getPremiereInterSelectionnee();
            Intersection monInter = vue.getVuePlan().getPlan().getIntersection(idInter);

            DialogInfosDemande dialog = new DialogInfosDemande(fenetre, monInter);
            DemandeLivraison maDemande = dialog.showDialog();

            return maDemande;
        }
    }

    private class GenererFeuilleRoute implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SerialiseurXML.exporterTournee(vue.getVueTournee().getTournee());
            } catch (IOException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                showMessageDialog(null, "L'exportation a échoué.");
            }
        }
    }

}
