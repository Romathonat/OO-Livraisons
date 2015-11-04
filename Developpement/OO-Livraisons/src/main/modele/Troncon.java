package modele;

/**
 *
 * @author tfavrot
 */
public class Troncon {
    
    // Attributs
    
    //La longueur en mètres.
    private double longueur;
    //La vitesse en mètres par seconde.
    private double vitesse;
    private String nom;
    private Intersection intersectionArrivee;
    private Intersection intersectionDepart;
    
    // Methodes

    public Troncon(Intersection intersectionDepart, Intersection intersectionArrivee, String nom, double longueur, double vitesse) {
        this.longueur = longueur;
        this.vitesse = vitesse;
        this.nom = nom;
        this.intersectionArrivee = intersectionArrivee;
        this.intersectionDepart = intersectionDepart;
    }

    public double getLongueur() {
        return longueur;
    }

    public double getVitesse() {
        return vitesse;
    }
    
    /**
     * Calcule le temps nécessaire pour traverser le tronçon.
     * @return La durée en secondes.
     */
    public double getDuree() {
        return longueur / vitesse;
    }

    public String getNom() {
        return nom;
    }

    public Intersection getIntersectionArrivee() {
        return intersectionArrivee;
    }

    public Intersection getIntersectionDepart() {
        return intersectionDepart;
    }
}
