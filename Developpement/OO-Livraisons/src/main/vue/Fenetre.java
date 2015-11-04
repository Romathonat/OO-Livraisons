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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import modele.EnsembleLivraisons;
import modele.Plan;
import modele.Tournee;

/**
 *
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
    protected VueTextuelle vueTextuelle;
    protected JPanel panelBoutons;
    protected JPanel legende;
    protected int ecartLegende;
    protected Dimension tailleEltLegende;
    
    protected VueGraphique vueGraphique;

    protected JSplitPane panelSeparationGauche;//contient le gauche et le centre
    protected JSplitPane panelSeparationDroit;//contient le droit et le panelSeparationGauche

    protected JButton ajouterLivraison;
    protected JButton supprimerLivraison;
    protected JButton echangerLivraison;
    protected JButton calculerTournee;

    private Controleur controleur;
    protected GenerateurCouleur generateurCouleur;

    protected List<FenetreLivraisonVue> mesFenetresLivraison;
    
    public Fenetre(Controleur c) {
        controleur = c;
        generateurCouleur = new GenerateurCouleur();

        barreMenus = new JMenuBar();

        //----Fichier-----
        fichier = new JMenu("Fichier");
        chargerPlan = new JMenuItem("Charger Plan");
        chargerPlan.addActionListener(new ChargerPlan(this));
        chargerDemandesLivraisons = new JMenuItem("Charger demandes livraisons");
        chargerDemandesLivraisons.addActionListener(new ChargerTournee());
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
        supprimerLivraison = new JButton("Supprimer Livraison");
        echangerLivraison = new JButton("Echanger Livraison");
        calculerTournee = new JButton("Calculer Tournée");
        calculerTournee.addActionListener(new CalculerTournee());

        //------Organisation des Pannels
        vueGraphique = new VueGraphique();
  

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

        //----------LEGENDE----------
        legende = new JPanel();
        
        this.ecartLegende = 15;
        this.tailleEltLegende = new Dimension(210, 20);
        this.updateLegende();
        
        
        panelGauche = new JPanel();
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.PAGE_AXIS));
        panelGauche.add(panelBoutons);
        panelGauche.add(legende);

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
        panelSeparationGauche = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, vueGraphique);
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
    
    public void updateLegende()
    {
        JLabel titre = new JLabel("Legende:");
        Font font = titre.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titre.setFont(font.deriveFont(attributes));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        legende.setLayout(new BoxLayout(legende, BoxLayout.PAGE_AXIS));
        
        ElementLegende neutre = new ElementLegende(Color.LIGHT_GRAY, "Intersection");
        ElementLegende demandeF1 = new ElementLegende(generateurCouleur.getCouleurSuivante(), "Demande Fenetre 1");
        ElementLegende demandeF2 = new ElementLegende(generateurCouleur.getCouleurSuivante(), "Demande Fenetre 2");
        ElementLegende demandeF3 = new ElementLegende(generateurCouleur.getCouleurSuivante(), "Demande Fenetre 3");

        legende.add(titre);

        legende.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
        legende.add(neutre);
        neutre.setMinimumSize(tailleEltLegende);
        neutre.setMaximumSize(tailleEltLegende);

        legende.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
        legende.add(demandeF1);
        demandeF1.setMinimumSize(tailleEltLegende);
        demandeF1.setMaximumSize(tailleEltLegende);

        legende.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
        legende.add(demandeF2);
        demandeF2.setMinimumSize(tailleEltLegende);
        demandeF2.setMaximumSize(tailleEltLegende);

        legende.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
        legende.add(demandeF3);
        demandeF3.setMinimumSize(tailleEltLegende);
        demandeF3.setMaximumSize(tailleEltLegende);

        legende.add(Box.createRigidArea(new Dimension(0, 300))); //on ajoute de l'espace à la fin pour que la legende ne prenne pas toute la place

    }
    
    
    public void sendMessage(String message){
            JOptionPane.showMessageDialog(null, message);
    }
    // ---- Methodes d'activation/desactivation des fonctionnalites ----
    
        // -- Activables / Desactivables ---
    
    public void activerChargerPlan(boolean activer){
        chargerPlan.setEnabled(activer);
    }
    public void activerChargerDemandesLivraisons(boolean activer){
        chargerDemandesLivraisons.setEnabled(activer);
    }
    public void activerGenererFeuilleRoute(boolean activer){
        genererFeuilleDeRoute.setEnabled(activer);
    }
    public void activerAnnuler(boolean activer){
        annuler.setEnabled(activer);
    }
    public void activerRetablir(boolean activer){
        retablir.setEnabled(activer);
    }
    public void activerAjouterLivraison(boolean activer){
        ajouterLivraison.setEnabled(activer);
    }
    public void activerSupprimerLivraison(boolean activer){
        supprimerLivraison.setEnabled(activer);
    }
    public void activerEchangerLivraison(boolean activer){
        echangerLivraison.setEnabled(activer);
    }
    public void activerCalculerTournee(boolean activer){
        calculerTournee.setEnabled(activer);
    }
    public void activerIntersectionsSelectionnables(boolean activer){
        vueGraphique.activerIntersectionsSelectionnables(activer);
    }
    
        
        // --- Activables uniquement ---
    
    public void activerQuitter(){
        quitter.setEnabled(true);
    }
    public void activerAPropos(){
        descriptionProjet.setEnabled(true);
    }
        
        // Desactivation generale (sauf activables uniquement)
        
    public void toutDesactiver(){
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

        JFrame frameParent;

        public ChargerPlan(JFrame frameParent) {
            this.frameParent = frameParent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Plan monPlan = controleur.chargerPlan();
            vueGraphique.removeAll();
            vueGraphique.drawPlan(monPlan);
        }
    }

    private class ChargerTournee implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EnsembleLivraisons livraisons = controleur.chargerLivraisons();

            try { //au cas ou un point n'est pas dejà dessiné
                vueGraphique.drawLivraisons(livraisons);
            } catch (Exception ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
            vueTextuelle.removeAll();
            vueTextuelle.writeLivraisons(livraisons,null);
            revalidate();
            repaint();
        }
    }

    private class CalculerTournee implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Tournee tournee = controleur.calculerTournee();

            vueGraphique.drawTournee(tournee);

            revalidate();
            repaint();
        }
    }
    
    private class GenererFeuilleRoute implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO : Appeler le service de Guillaume
        }
    }
    
}


