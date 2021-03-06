package vue;

import java.awt.Color;

/**
 * GenerateurCouleur permet de générer des couleurs utilisable pour le dessin
 * des éléments graphique de l'application. Il contient aussi les couleurs
 * standard de certains éléments de l'application. GenerateurCouleur est un
 * singleton.
 *
 * @author Guillaume Kheng
 */
public class GenerateurCouleur {

    /**
     * Le tableau de couleur.
     */
    private Color couleurs[];

    /**
     * L'indice dans le tableau de couleur de la prochaine couleur.
     */
    private int indiceCouleur;

    /**
     * La taille du tableau de couleur.
     */
    private int nombreCouleur;

    /**
     * L'instance actuelle de GenerateurCouleur. GenerateurCouleur étant un
     * singleton, cette instance est toujours unique dans toute l'application.
     */
    private static GenerateurCouleur instance = null;

    /**
     * Retourne l'instance courante de GenerateurCouleur.
     *
     * @return L'instance courante de GenerateurCouleur.
     */
    public static GenerateurCouleur getInstance() {
        if (instance == null) {
            instance = new GenerateurCouleur();
        }
        return instance;
    }

    /**
     * Constructeur d'un GenerateurCouleur.
     */
    private GenerateurCouleur() {

        nombreCouleur = 7;

        this.couleurs = new Color[nombreCouleur];
        this.couleurs[0] = new Color(58, 142, 186); // bleu acier
        this.couleurs[1] = new Color(126, 51, 0);   // caramel
        this.couleurs[2] = new Color(221, 152, 92); // ocre rouge
        this.couleurs[3] = new Color(199, 44, 72);  // framboise
        this.couleurs[4] = new Color(204, 85, 0);   // orange brulé
        this.couleurs[5] = new Color(38, 97, 156);  // lapis-Lazulis
        this.couleurs[6] = new Color(167, 103, 38); // alezan

        this.indiceCouleur = 0;
    }

    protected void initGenerateur() {
        this.indiceCouleur = 0;
    }

    /**
     * Retourne la prochaine couleur du tableau de couleur. Si le tableau a été
     * entièrement parcouru, la prochaine couleur est la première couleur du
     * tableau, et le parcours reprend depuis le début du tableau.
     *
     * @return La prochaine couleur du tableau de couleur.
     */
    public Color getCouleur() {
        Color result = this.couleurs[indiceCouleur];
        indiceCouleur = (indiceCouleur + 1) % nombreCouleur;
        return result;
    }

    /**
     * Retourne la couleur standard associée à l'entrepôt.
     *
     * @return La couleur standard associée à l'entrepôt.
     */
    public static Color getCouleurEntrepot() {
        return new Color(16, 96, 34);
    }

    /**
     * Retourne la couleur standard associée à une demande de livraison hors
     * horaire.
     *
     * @return La couleur standard associée à une demande de livraison hors
     * horaire.
     */
    public static Color getCouleurDemandeHorsHoraire() {
        return Color.RED;
    }
}
