package vue;

import java.awt.Color;
import modele.FenetreLivraison;

/**
 *
 * @author Guillaume Kheng
 */
public class FenetreLivraisonVue {

    private FenetreLivraison fenetre;
    private Color couleur;

    FenetreLivraisonVue(FenetreLivraison fenetre) {
        this.fenetre = fenetre;
        this.couleur = null;
    }

    public FenetreLivraison getFenetre() {
        return fenetre;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

}
