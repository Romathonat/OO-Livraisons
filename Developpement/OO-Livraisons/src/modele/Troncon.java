/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Collection;

/**
 *
 * @author tfavrot
 */
public class Troncon {
    
    // Attributs
    
    private int idTroncon;
    private int longueur;
    private int vitesse;
    private String nom;
    private Intersection intersectionArrivee;
    private Intersection intersectionDepart;
    
    // Methodes

    public Troncon(int idTroncon, int longueur, int vitesse, String nom, Intersection intersectionArrivee, Intersection intersectionDepart) {
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

    public int getLongueur() {
        return longueur;
    }

    public int getVitesse() {
        return vitesse;
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
