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
        //this.setSize(width, height);
        this.setOpaque(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);

        this.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, this.fenetreLivraisonVue.getCouleur()));

        this.jLabelClient = new JLabel("Client : random");
        this.jLabelClient.setBorder(new EmptyBorder(0, 5, 0, 0));

        this.jLabelAdresse = new JLabel("Adresse : x = " + this.demandeLivraison.getIntersection().getX() + ", y = " + this.demandeLivraison.getIntersection().getY());
        this.jLabelAdresse.setBorder(new EmptyBorder(0, 5, 0, 0));

        /*DateFormat df = new SimpleDateFormat("HH:mm:ss");
         String heureDebutString = df.format(this.demandeLivraison.getFenetreLivraison().getHeureDebut());
         String heureFinString = df.format(this.demandeLivraison.getFenetreLivraison().getHeureFin());
         this.jLabelHeure = new JLabel("Heure de livraison : entre " + heureDebutString + " et " + heureFinString);
         this.jLabelHeure.setBorder(new EmptyBorder(0, 5, 0, 0));*/
        this.add(this.jLabelClient);
        this.add(this.jLabelAdresse);
        //this.add(this.jLabelHeure);
    }

    public void changeHeure(DemandeLivraison demandeLivraison) {
        this.demandeLivraison = demandeLivraison;
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String heureString = df.format(this.demandeLivraison.getHeureLivraison());
        this.jLabelHeure = new JLabel("Heure de livraison : " + heureString);

        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        /*float[] RGB = {0,0,0};
        RGB[0] = (this.couleur.getRed()/ 255.0f)*1.2f;
        RGB[1] = (this.couleur.getGreen()/ 255.0f)*1.2f;
        RGB[2] = (this.couleur.getBlue()/ 255.0f)*1.2f;
        Color color = new Color(RGB[0], RGB[1], RGB[2]);*/
        
        g2d.setColor(Color.WHITE);
        g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
