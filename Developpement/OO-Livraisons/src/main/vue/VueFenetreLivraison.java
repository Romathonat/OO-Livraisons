package vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import modele.FenetreLivraison;

/**
 *
 * @author Guillaume Kheng
 */
public class VueFenetreLivraison {
    
    /**
     * La fenêtre de livraison représentée par la VueFenetrelivraison.
     */
    private FenetreLivraison fenetreLivraison;
    
    /**
     * La liste de vueDemandeLivraison contenues.
     */
    private List<VueDemandeLivraison> listDemandesLivraisonVue;
    
    /**
     * La couleur associée à la fenêtre de livraison.
     */
    private Color couleur;
    
    /**
     * Constructeur d'une VueFenetreLivraison
     * @param fenetre La fenêtre de livraison représentée par la VueFenetrelivraison.
     * @param couleur La couleur associée à la fenêtre de livraison.
     */
    VueFenetreLivraison(FenetreLivraison fenetre, Color couleur) {
        this.fenetreLivraison = fenetre;
        this.couleur = couleur;
        this.listDemandesLivraisonVue = new ArrayList<>();
    }

    /**
     * Retourne la fenêtre de livraison que représente la VueFenetreLivraison
     * @return La fenêtre de livraison que représente la VueFenetreLivraison
     */
    protected FenetreLivraison getFenetre() {
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
    protected void clearDemandesLivraisonVue(){
        this.listDemandesLivraisonVue.clear();
    }
    
    /**
     * Retourne un itérateur sur la liste de vue de demandes de livraison.
     * @return Un itérateur sur la liste de vue de demandes de livraison.
     */
    protected Iterator<VueDemandeLivraison> getVueDemandeLivraisonVue(){
        List constList = Collections.unmodifiableList(this.listDemandesLivraisonVue);
        return constList.iterator();
    }
    
    /**
     * Ajoute une VueDemandeLivraison à la liste de vue de demandes de livraison.
     * @param vueDemandeLivraison La VueDemandeLivraison à ajouter à la liste.
     */
    protected void addVueDemandeLivraison(VueDemandeLivraison vueDemandeLivraison){
        this.listDemandesLivraisonVue.add(vueDemandeLivraison);
    }

}
