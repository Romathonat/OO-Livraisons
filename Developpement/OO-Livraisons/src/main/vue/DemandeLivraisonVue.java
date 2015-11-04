/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modele.DemandeLivraison;

/**
 *
 * @author Kilian
 */
public class DemandeLivraisonVue extends JPanel {

    private int height = 100;
    protected DemandeLivraison demandeLivraison;
    protected FenetreLivraisonVue fenetreLivraisonVue;
    public Date heure;

    JLabel jLabelClient;
    JLabel jLabelAdresse;
    JLabel jLabelHeure;

    public DemandeLivraisonVue(FenetreLivraisonVue fenetreLivraisonVue, DemandeLivraison demandeLivraison) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setOpaque(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);

        this.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, this.getCouleur()));

        Graphics2D g2d = (Graphics2D) g;

        // change here to put color less fluo.
        g2d.setColor(Color.WHITE);
        g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    
    public Color getCouleur()
    {
        boolean test = this.demandeLivraison.RespecteFenetreLivraison();
        
        if (this.demandeLivraison.getHeureLivraison() == null || this.demandeLivraison.RespecteFenetreLivraison())
        {
            return this.fenetreLivraisonVue.getCouleur();
        }
        return Color.RED;
    }
}
