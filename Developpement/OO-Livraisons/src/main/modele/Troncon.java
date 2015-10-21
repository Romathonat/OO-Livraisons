package modele;

/**
 *
 * @author tfavrot
 */
public class Troncon {
    
    // Attributs
    
    private int idTroncon;
    private double longueur;
    private double vitesse;
    private String nom;
    private Intersection intersectionArrivee;
    private Intersection intersectionDepart;
    
    // Methodes

    public Troncon(int idTroncon, double longueur, double vitesse, String nom, Intersection intersectionArrivee, Intersection intersectionDepart) {
        this.idTroncon = idTroncon;
        this.longueur = longueur;
        this.vitesse = vitesse;
        this.nom = nom;
        this.intersectionArrivee = intersectionArrivee;
        this.intersectionDepart = intersectionDepart;
    }

    public int getIdTroncon() {
        return idTroncon;
    }

    public double getLongueur() {
        return longueur;
    }

    public double getVitesse() {
        return vitesse;
    }
    
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
