package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modele.FenetreLivraison;

/**
 *
 * @author Guillaume Kheng
 */
public class VueFenetreLivraison extends JPanel {
    
    /**
     * La fenêtre de livraison représentée par la VueFenetrelivraison.
     */
    private FenetreLivraison fenetreLivraison;
    
    /**
     * La liste de vueDemandeLivraison contenues.
     */
    private List<VueDemandeLivraison> listVueDemandesLivraison;
    
    /**
     * La couleur associée à la fenêtre de livraison.
     */
    private Color couleur;
    
    /**
     * Numero de la fenetre temporelle
     */
    private int numeroFenetre;
    
    /**
     * Le texte annoncant le numero de la fenetre sur la vue textuelle
     */
    private JLabel intituleLivraison;
    
    /**
     * Le texte annoncant les bornes horaires sur la vue textuelle
     */
    private JLabel heuresLimitesFenetre;
    
    /**
     * Constructeur d'une VueFenetreLivraison
     * @param fenetre La fenêtre de livraison représentée par la VueFenetrelivraison.
     * @param couleur La couleur associée à la fenêtre de livraison.
     */
    VueFenetreLivraison(FenetreLivraison fenetre, Color couleur, int numeroFenetre) {
        this.fenetreLivraison = fenetre;
        this.couleur = couleur;
        this.numeroFenetre = numeroFenetre;
        this.listVueDemandesLivraison = new ArrayList<>();
        
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String heureDebutString = df.format(this.fenetreLivraison.getHeureDebut());
        String heureFinString = df.format(this.fenetreLivraison.getHeureFin());
        
        this.intituleLivraison = new JLabel("Fenetre #" + this.numeroFenetre);
        this.intituleLivraison.setBorder(new EmptyBorder(0, 5, 0, 0));

        this.heuresLimitesFenetre = new JLabel("Plage horaire : " + heureDebutString + "-" + heureFinString);
        this.heuresLimitesFenetre.setBorder(new EmptyBorder(0, 5, 0, 0));

        this.add(this.intituleLivraison);
        this.add(this.heuresLimitesFenetre);
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setVisible(true);
    }

    /**
     * Retourne la fenêtre de livraison que représente la VueFenetreLivraison
     * @return La fenêtre de livraison que représente la VueFenetreLivraison
     */
    protected FenetreLivraison getFenetreLivraison() {
        return this.fenetreLivraison;
    }

    /**
     * Retourne la couleur associée à la fenêtre de livraison
     * @return La couleur associée à la fenêtre de livraison.
     */
    protected Color getCouleur() {
        return this.couleur;
    }
    
    /**
     * Retourne la couleur associée à la fenêtre de livraison.
     * @param La couleur associée à la fenêtre de livraison.
     */
    protected void setCouleur(Color couleur) {
        this.couleur = couleur;
    }
    
    /**
     * Vide la liste de DemandeLivraisonVue. 
     */
    protected void clearVuesDemandesLivraison(){
        this.listVueDemandesLivraison.clear();
    }
    
    /**
     * Retourne un itérateur sur la liste de vue de demandes de livraison.
     * @return Un itérateur sur la liste de vue de demandes de livraison.
     */
    protected Iterator<VueDemandeLivraison> getVueDemandeLivraisonList(){
        List constList = Collections.unmodifiableList(this.listVueDemandesLivraison);
        return constList.iterator();
    }
    
    /**
     * Ajoute une VueDemandeLivraison à la liste de vue de demandes de livraison.
     * @param vueDemandeLivraison La VueDemandeLivraison à ajouter à la liste.
     */
    protected void addVueDemandeLivraison(VueDemandeLivraison vueDemandeLivraison){
        this.listVueDemandesLivraison.add(vueDemandeLivraison);
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, this.couleur));
    }

}
