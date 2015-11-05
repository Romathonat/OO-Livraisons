/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modele.DemandeLivraison;

/**
 *
 * @author Kilian
 */
public class VueDemandeLivraison extends JPanel {

    private int height = 100;
    protected DemandeLivraison demandeLivraison;
    protected VueFenetreLivraison fenetreLivraisonVue;
    public Date heure;
    protected Fenetre fenetre;

    JLabel jLabelClient;
    JLabel jLabelAdresse;
    JLabel jLabelHeure;

    public VueDemandeLivraison(Fenetre fenetreParent, VueFenetreLivraison fenetreLivraisonVue, DemandeLivraison demandeLivraison) {
        super();
        
        this.fenetre = fenetreParent;
        this.fenetreLivraisonVue = fenetreLivraisonVue;
        this.demandeLivraison = demandeLivraison;

        this.jLabelClient = new JLabel("Client : " + Integer.toString(demandeLivraison.getIdClient()));
        this.jLabelClient.setBorder(new EmptyBorder(0, 5, 0, 0));

        this.jLabelAdresse = new JLabel("Adresse : x = " + this.demandeLivraison.getIntersection().getX() + ", y = " + this.demandeLivraison.getIntersection().getY());
        this.jLabelAdresse.setBorder(new EmptyBorder(0, 5, 0, 0));

        this.jLabelHeure = null;

        this.add(this.jLabelClient);
        this.add(this.jLabelAdresse);

        // si l'heure de livraison est disponible, on la rajoute.
        if (demandeLivraison.getHeureLivraison() != null) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            String heureString = df.format(this.demandeLivraison.getHeureLivraison());
            this.jLabelHeure = new JLabel("Heure de livraison : " + heureString);
            this.jLabelHeure.setBorder(new EmptyBorder(0, 5, 0, 0));
            this.add(this.jLabelHeure);
        }

        // ComposantGraphique.
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        
        addMouseListener(new MouseAdapter() {
            private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
                background = getBackground();
                setBackground(new Color(113, 142, 164));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(background);
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBorder(BorderFactory.createMatteBorder(1, 4, 1, 1, this.getCouleur()));
    }

    public Color getCouleur() {
        boolean test = this.demandeLivraison.RespecteFenetreLivraison();

        if (this.demandeLivraison.getHeureLivraison() == null || this.demandeLivraison.RespecteFenetreLivraison()) {
            return this.fenetreLivraisonVue.getCouleur();
        }
        return Color.RED;
    }

}
