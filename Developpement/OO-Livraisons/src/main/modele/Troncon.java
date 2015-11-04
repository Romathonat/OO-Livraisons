package modele;

/**
 * Un tronçon est une route empruntable entre deux intersections. 
 * @author tfavrot
 */
public class Troncon {
    
    
    
    /**
     * La longueur physique d'un tronçon, en mètres.
     */
    private double longueur;
    
    /**
     * La vitesse moyenne du livreur sur un tronçon, en mètres par seconde.
     */
    private double vitesse;
    
    /**
     * Le nom de la rue à laquelle appartient un tronçon. 
     */
    private String nom;
    
    /**
     * L'intersection sur laquelle arrive un tronçon.
     */
    private Intersection intersectionArrivee;
    
    /**
     * L'intersection sur laquelle arrive un tronçon.
     */
    private Intersection intersectionDepart;
    
   
    /**
     * Contructeur d'un tronçon.
     * @param intersectionDepart L'intersection sur laquelle arrive le tronçon.
     * @param intersectionArrivee L'intersection sur laquelle arrive le tronçon.
     * @param nom Le nom de la rue à laquelle appartient le tronçon. 
     * @param longueur La longueur physique du tronçon.
     * @param vitesse La vitesse moyenne du livreur sur le tronçon.
     */
    public Troncon(Intersection intersectionDepart, Intersection intersectionArrivee, String nom, double longueur, double vitesse) {
        this.longueur = longueur;
        this.vitesse = vitesse;
        this.nom = nom;
        this.intersectionArrivee = intersectionArrivee;
        this.intersectionDepart = intersectionDepart;
    }

    /**
     * Retourne la longeur la longeur d'un tronçon
     * @return La longeur d'un tronçon
     */
    public double getLongueur() {
        return longueur;
    }

    /**
     * Retourne la vitesse d'un tronçon
     * @return La vitesse d'un tronçon
     */
    public double getVitesse() {
        return vitesse;
    }
    
    /**
     * Retourne la durée (temps) de parcours d'un tronçon, calculée en fonction 
     * de la longeur du tronçon et de la vitesse moyenne de parcours du tronçon. 
     * @return La durée de parcours du tronçon, en secondes.
     */
    public double getDuree() {
        return longueur / vitesse;
    }

    /**
     * Retourne le nom associé à un tronçon
     * @return Le nom associé à un tronçon
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne l'intersection d'arrivée d'un tronçon
     * @return L'intersection d'arrivée d'un tronçon
     */
    public Intersection getIntersectionArrivee() {
        return intersectionArrivee;
    }

    /**
     * Retourne l'intersection de départ d'un tronçon
     * @return L'intersection de départ d'un tronçon
     */
    public Intersection getIntersectionDepart() {
        return intersectionDepart;
    }
}
