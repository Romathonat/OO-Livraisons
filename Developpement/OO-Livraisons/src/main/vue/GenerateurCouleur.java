package vue;

import java.awt.Color;
import xml.OuvreurFichierXML;

/**
 *
 * @author Guillaume Kheng
 */
public class GenerateurCouleur {

    private Color couleurs[];

    private int nextColor;
    
    private int nombreCouleur;
    
    private static GenerateurCouleur instance = null;
    
    public static GenerateurCouleur getInstance() {
           if (instance == null) {
            instance = new GenerateurCouleur();
        }
        return instance;
    }

    private GenerateurCouleur() {

        nombreCouleur = 6;
        
        this.couleurs = new Color[nombreCouleur];
        this.couleurs[0] = Color.BLUE;
        this.couleurs[1] = Color.MAGENTA;
        this.couleurs[2] = Color.ORANGE;
        this.couleurs[3] = Color.PINK;
        this.couleurs[4] = Color.YELLOW;
        this.couleurs[5] = Color.CYAN;

        this.nextColor = 0;
    }

    public Color getCouleurSuivante() {
        Color result = this.couleurs[nextColor];
        nextColor = (nextColor + 1) % nombreCouleur;
        return result;
    }

    public Color getCouleurAt(int i) {
        return this.couleurs[i % nombreCouleur];
    }
    
    public static Color getCouleurEntrepot(){
        return new Color (16,96,34);
    }
    
    public static Color getCouleurFenetreHorsHoraire(){
        return Color.RED;
    }
}
