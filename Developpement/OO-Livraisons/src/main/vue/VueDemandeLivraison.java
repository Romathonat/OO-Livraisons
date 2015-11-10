/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modele.DemandeLivraison;

/**
 * Représente graphiquement une demande de livraison.
 *
 * @author Kilian
 */
public class VueDemandeLivraison extends JPanel {

    /**
     * Renseigne si la demande de livraison est selectionnee ou non.
     */
    private boolean estSelectionnee;

    /**
     * La demande de livraison associée à la VueDemandeLivraison.
     */
    private DemandeLivraison demandeLivraison;

    /**
     * La vue de la fenêtre de livraison associée à la demande de livraison.
     */
    private VueFenetreLivraison VueFenetreLivraison;

    JLabel jLabelClient;
    JLabel jLabelAdresse;
    JLabel jLabelHeure;

    /**
     * Constructeur d'une VueDemandeLivraison.
     *
     * @param VuefenetreLivraison La vue de la fenêtre de livraison associée à
     * la demande de livraison.
     * @param demandeLivraison
     */
    protected VueDemandeLivraison(VueFenetreLivraison VuefenetreLivraison, final DemandeLivraison demandeLivraison) {
        super();

        this.VueFenetreLivraison = VuefenetreLivraison;
        this.demandeLivraison = demandeLivraison;

        this.jLabelClient = new JLabel("Client : " + Integer.toString(demandeLivraison.getIdClient()));
        this.jLabelClient.setBorder(new EmptyBorder(0, 5, 0, 0));

        this.jLabelAdresse = new JLabel("Adresse : " + this.demandeLivraison.getIntersection().getId());
        this.jLabelAdresse.setBorder(new EmptyBorder(0, 5, 0, 0));

        this.jLabelHeure = null;

        this.add(this.jLabelClient);
        this.add(this.jLabelAdresse);

        
        if (demandeLivraison.getHeureLivraison() != null) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            String heureString = df.format(this.demandeLivraison.getHeureLivraison());
            this.jLabelHeure = new JLabel("Heure de livraison : " + heureString);
            this.jLabelHeure.setBorder(new EmptyBorder(0, 5, 0, 0));
            this.add(this.jLabelHeure);
        }

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setVisible(true);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                Fenetre.controleur.selectionerIntersection(demandeLivraison.getIntersection());
                // TO DO appeler controleur.selectionnerIntersection(this.getDemandeLivraison().getIntersection())
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, this.getCouleur()));

        if (this.estSelectionnee) {
            this.setBackground(new Color(113, 142, 164));
        } else {
            this.setBackground(Color.WHITE);
        }
    }

    /**
     * Retourne la couleur associée à la demande de livraison.Si l'heure de
     * livraison de la tournée a été renseignée et que la demande de livraison
     * n'est pas dans sa fenêtre horaire prévue, retourne la couleur standard
     * d'une demande de livraison hors horaire.
     *
     * @return La couleur associée à la demande de livraison.
     */
    protected Color getCouleur() {

        if (this.getDemandeLivraison().getHeureLivraison() == null || this.getDemandeLivraison().RespecteFenetreLivraison()) {
            return this.VueFenetreLivraison.getCouleur();
        } else {
            return GenerateurCouleur.getCouleurDemandeHorsHoraire();
        }
    }

    /**
     * Retourne la demande de livraison associée à la VueDemandeLivraison.
     *
     * @return La demande de livraison associée à la VueDemandeLivraison.
     */
    protected DemandeLivraison getDemandeLivraison() {
        return this.demandeLivraison;
    }

    /**
     * Permet de selectionner une DemandeLivraison en fonction de l'Id de
     * l'intersection qui lui est associée.
     *
     * @return True si la demande est effectivement selectionnee. False sinon.
     */
    protected boolean Selectionner(int idIntersection) {
        return this.estSelectionnee = this.demandeLivraison.getIntersection().getId() == idIntersection;
    }
}
