package vue;

import java.awt.Color;
import modele.FenetreLivraison;

/**
 *
 * @author Guillaume Kheng
 */
public class VueFenetreLivraison {

    private FenetreLivraison fenetre;
    private Color couleur;

    VueFenetreLivraison(FenetreLivraison fenetre, Color couleur) {
        this.fenetre = fenetre;
        this.couleur = couleur;
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
