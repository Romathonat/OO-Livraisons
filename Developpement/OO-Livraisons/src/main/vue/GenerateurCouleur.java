package vue;

import java.awt.Color;

/**
 *
 * @author Guillaume Kheng
 */
public class GenerateurCouleur {

    private Color couleurs[];

    private int nextColor;

    public GenerateurCouleur() {

        this.couleurs = new Color[8];
        this.couleurs[0] = Color.BLUE;
        this.couleurs[1] = Color.MAGENTA;
        this.couleurs[2] = Color.ORANGE;
        this.couleurs[3] = Color.PINK;
        this.couleurs[4] = Color.RED;
        this.couleurs[5] = Color.GREEN;
        this.couleurs[6] = Color.YELLOW;
        this.couleurs[7] = Color.CYAN;

        this.nextColor = 0;
    }

    public Color getCouleurSuivante() {
        Color result = this.couleurs[nextColor];
        nextColor = (nextColor + 1) % 8;
        return result;
    }

    public Color getCouleurAt(int i) {
        return this.couleurs[i];
    }

}
