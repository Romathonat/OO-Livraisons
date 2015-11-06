package vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import modele.FenetreLivraison;

/**
 *
 * @author Guillaume Kheng
 */
public class VueFenetreLivraison {

    protected FenetreLivraison fenetreLivraison;
    protected List<VueDemandeLivraison> listDemandesLivraisonVue;
    private Color couleur;

    VueFenetreLivraison(FenetreLivraison fenetre, Color couleur) {
        this.fenetreLivraison = fenetre;
        this.couleur = couleur;
        
        listDemandesLivraisonVue = new ArrayList<>();
    }

    public FenetreLivraison getFenetre() {
        return fenetreLivraison;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }
    
    protected void clearDemandesLivraison(){
        this.listDemandesLivraisonVue.clear();
    }

}
