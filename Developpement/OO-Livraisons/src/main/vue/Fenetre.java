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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import modele.Chemin;
import modele.DemandeLivraison;
import modele.EnsembleLivraisons;
import modele.FenetreLivraison;
import modele.Plan;
import modele.Tournee;

/**
 *
 * @author romain
 */
public class Fenetre extends JFrame {

    protected JMenuBar barreMenus;

    protected JPanel barreStatus;
    protected JLabel statusRigth;
    protected JLabel statusLeft;

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

    protected Controleur controleur;
    protected GenerateurCouleur generateurCouleur;
    
    protected Plan planCourant; //il nous faut garder ces references pour redessiner le plan quand repaint est appelle
    protected EnsembleLivraisons ensembleLivraisonsCourant;//attention doivent être mise à null si on recharge juste le plan !
    protected Tournee tourneeCourante;

    protected List<FenetreLivraisonVue> listFenetresLivraisonVue;
    protected List<DemandeLivraisonVue> listDemandesLivraisonVue;
    protected List<CheminVue> listCheminVue;

    public Fenetre(Controleur c) {
        controleur = c;

        generateurCouleur = new GenerateurCouleur();
        listFenetresLivraisonVue = new ArrayList<>();
        listDemandesLivraisonVue = new ArrayList<>();
        listCheminVue = new ArrayList<>();

        barreMenus = new JMenuBar();

        barreStatus = new JPanel();
        barreStatus.setLayout(new BorderLayout());
        statusRigth = new JLabel("OO-Livraison");
        statusLeft = new JLabel("");
        barreStatus.add(statusRigth, BorderLayout.WEST);
        barreStatus.add(statusLeft, BorderLayout.EAST);

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
        supprimerLivraison = new JButton("Supprimer Livraison");
        echangerLivraison = new JButton("Echanger Livraison");
        calculerTournee = new JButton("Calculer Tournée");
        calculerTournee.addActionListener(new CalculerTournee(this));

        //------Organisation des Pannels
        vueGraphique = new VueGraphique(this);

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
        this.ecartLegende = 15;
        this.tailleEltLegende = new Dimension(210, 20);

        this.legende = new JPanel();
        this.updateLegende(0);

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
        panelPrincipal.add(barreStatus, BorderLayout.SOUTH);

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

    public void updateLegende(int etat) {
        legende.removeAll();

        JLabel titre = new JLabel("Legende:");
        Font font = titre.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titre.setFont(font.deriveFont(attributes));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        legende.setLayout(new BoxLayout(legende, BoxLayout.PAGE_AXIS));

        legende.add(titre);

        // on a chargé le plan.
        if (etat > 0) {
            // Legende des intersections.
            ElementLegende neutre = new ElementLegende(Color.LIGHT_GRAY, "Intersection");
            legende.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
            legende.add(neutre);
            neutre.setMinimumSize(tailleEltLegende);
            neutre.setMaximumSize(tailleEltLegende);
        }

        // on a chargé les livraisons.
        if (etat > 1) {

            // legende de l'entrepot
            ElementLegende legendeFenetre = new ElementLegende(Color.GREEN, "Entrepot");
            legende.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
            legende.add(legendeFenetre);
            legendeFenetre.setMinimumSize(tailleEltLegende);
            legendeFenetre.setMaximumSize(tailleEltLegende);

            // Legende des fenetres de livraison.
            Iterator<FenetreLivraisonVue> it_flv = this.listFenetresLivraisonVue.iterator();
            int i = 0;
            while (it_flv.hasNext()) {
                i++;
                legendeFenetre = new ElementLegende(it_flv.next().getCouleur(), "Demande Fenetre " + Integer.toString(i));
                legende.add(Box.createRigidArea(new Dimension(0, ecartLegende)));
                legende.add(legendeFenetre);
                legendeFenetre.setMinimumSize(tailleEltLegende);
                legendeFenetre.setMaximumSize(tailleEltLegende);
            }

            legende.add(Box.createRigidArea(new Dimension(0, 300)));
        }

        legende.validate();
        legende.repaint();

    }

    public void changerStatus(String status) {
        this.statusRigth.setText(status);
    }

    /**
     * Affiche une popup contenant un message particulier.
     *
     * @param message Le message à afficher dans la popup.
     */
    public void EnvoyerMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    public void resetEnsembleLivraisons(){
        this.ensembleLivraisonsCourant = null;
        this.resetTournee();
    }
    
    public void resetTournee(){
        this.tourneeCourante = null;
    }
    
    protected void setPlanCourant(Plan plan){
        this.planCourant = plan;
    }
    
    protected void setEnsembleLivraisonsCourant(EnsembleLivraisons ensembleLivraisons){
        this.ensembleLivraisonsCourant = ensembleLivraisons;
    }
    
    protected void setTourneeCourante (Tournee tournee){
        this.tourneeCourante = tournee;
    }
    

    // ---- Methodes d'activation/desactivation des fonctionnalites ----
    // -- Activables / Desactivables ---
    public void activerChargerPlan(boolean activer) {
        chargerPlan.setEnabled(activer);
    }

    public void activerChargerDemandesLivraisons(boolean activer) {
        chargerDemandesLivraisons.setEnabled(activer);
    }

    public void activerGenererFeuilleRoute(boolean activer) {
        genererFeuilleDeRoute.setEnabled(activer);
    }

    public void activerAnnuler(boolean activer) {
        annuler.setEnabled(activer);
    }

    public void activerRetablir(boolean activer) {
        retablir.setEnabled(activer);
    }

    public void activerAjouterLivraison(boolean activer) {
        ajouterLivraison.setEnabled(activer);
    }

    public void activerSupprimerLivraison(boolean activer) {
        supprimerLivraison.setEnabled(activer);
    }

    public void activerEchangerLivraison(boolean activer) {
        echangerLivraison.setEnabled(activer);
    }

    public void activerCalculerTournee(boolean activer) {
        calculerTournee.setEnabled(activer);
    }

    // --- Activables uniquement ---
    public void activerQuitter() {
        quitter.setEnabled(true);
    }

    public void activerAPropos() {
        descriptionProjet.setEnabled(true);
    }

    // Desactivation generale (sauf activables uniquement)
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

            Plan nouveauPlan = controleur.chargerPlan();

            if (nouveauPlan == this.fenetre.planCourant) { // en cas de problème de chargement.
                return;
            }
            this.fenetre.setPlanCourant(nouveauPlan);
            this.fenetre.resetEnsembleLivraisons();
            
            // RAZ des objets graphiques.
            vueGraphique.removeAll();
            vueTextuelle.removeAll();

            // Dessin du plan.
            vueGraphique.drawPlan();

            // Mise à jour de la légende.
            this.fenetre.updateLegende(1);
            this.fenetre.changerStatus("Plan chargé");

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
            EnsembleLivraisons nouvelEnsembleLivraisons = controleur.chargerLivraisons();
            
            if (nouvelEnsembleLivraisons == this.fenetre.ensembleLivraisonsCourant ) { // en cas de problème de chargement.
                return;
            }
            
            this.fenetre.setEnsembleLivraisonsCourant(nouvelEnsembleLivraisons);
            this.fenetre.resetTournee();

            // mise à jour des objets visuels
            listFenetresLivraisonVue.clear();
            listDemandesLivraisonVue.clear();

            Iterator<FenetreLivraison> it_fenetre = this.fenetre.ensembleLivraisonsCourant.getFenetresLivraison();
            Iterator<DemandeLivraison> it_demande = null;

            while (it_fenetre.hasNext()) {
                FenetreLivraison fenetreLivraison = it_fenetre.next();
                FenetreLivraisonVue fenetreLivraisonVue = new FenetreLivraisonVue(fenetreLivraison, generateurCouleur.getCouleurSuivante());
                listFenetresLivraisonVue.add(fenetreLivraisonVue);

                it_demande = fenetreLivraison.getDemandesLivraison();
                while (it_demande.hasNext()) {
                    listDemandesLivraisonVue.add(new DemandeLivraisonVue(fenetreLivraisonVue, it_demande.next()));
                }
            }


            vueGraphique.drawLivraisons();

            this.fenetre.updateLegende(2);
            vueTextuelle.UpdateVueTextuelle(listDemandesLivraisonVue.iterator());

            this.fenetre.changerStatus("Demandes de livraison chargée");
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

            if (nouvelleTournee == this.fenetre.tourneeCourante) { // au cas ou le calcul de la tournee échouerai.
                return;
            }
            this.fenetre.setTourneeCourante(nouvelleTournee);

            // Mise à jour des elements graphiques.
            listDemandesLivraisonVue.clear();
            Iterator<Chemin> it_chemin = this.fenetre.tourneeCourante.getChemins();
            Iterator<DemandeLivraison> it_demande = null;

            while (it_chemin.hasNext()) {
                Chemin chemin = it_chemin.next();
                FenetreLivraisonVue fenetreLivraisonVue = getFenetreCorrespondante(chemin.getLivraisonArrivee());

                if (fenetreLivraisonVue == null) {
                    continue;
                }

                listCheminVue.add(new CheminVue(fenetreLivraisonVue, chemin));
                DemandeLivraisonVue demandeVue = new DemandeLivraisonVue(fenetreLivraisonVue, chemin.getLivraisonArrivee());
                listDemandesLivraisonVue.add(new DemandeLivraisonVue(fenetreLivraisonVue, chemin.getLivraisonArrivee()));
            }

            // mise à jour de la vue textuelle.
            vueTextuelle.UpdateVueTextuelle(listDemandesLivraisonVue.iterator());

            // mise àjour de la vue graphique.
            vueGraphique.drawTournee();
            this.fenetre.changerStatus("Tournée calculée");

            revalidate();
            repaint();

        }

        private FenetreLivraisonVue getFenetreCorrespondante(DemandeLivraison demandeLivraison) {
            Iterator<FenetreLivraisonVue> it_fenetreVue = listFenetresLivraisonVue.iterator();
            while (it_fenetreVue.hasNext()) {

                FenetreLivraisonVue fenetreVue = it_fenetreVue.next();
                if (demandeLivraison.getFenetreLivraison().getHeureDebut().compareTo(fenetreVue.getFenetre().getHeureDebut()) == 0) {
                    return fenetreVue;
                }
            }
            // la fenetre n'a pas été trouvée.
            return null;
        }
    }

    private class GenererFeuilleRoute implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO : Appeler le service de Guillaume
        }
    }

}
