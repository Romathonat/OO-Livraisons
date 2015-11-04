/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


/**
 * Une Intersection modélise le croisement de deux ou plusieurs tronçons. 
 * Une intersection peut  correspondre à un point de livraison.
 * @author tfavrot
 */
public class Intersection {

    /**
     * L'id identifiant une intersection.
     */    
    private int idIntersection;
    
    /**
     * La coordonnée spatiale d'abscisse d'une intersection sur le plan.
     */
    private int x;
    
    /**
     * La coordonnée spatiale d'ordonnée d'une intersection sur le plan. 
     */
    private int y;
    
    /**
     * La collection des tronçons qui quittent une intersection.
     */
    private Collection<Troncon> tronconsSortants;
    
    /**
     * Constructeur d'une intersection.
     * @param id L'id identifiant une intersection.
     * @param x La coordonnée spatiale d'ordonnée d'une intersection sur le plan. 
     * @param y La coordonnée spatiale d'abscisse d'une intersection sur le plan..
     */
    public Intersection(int id, int x, int y) {
        idIntersection = id;
        this.x = x;
        this.y = y;
        tronconsSortants = new ArrayList<>();
    }
    
    /**
     * Ajoute un troncon à la collection de tronçons sortant de l'intersection.
     * @param troncon Le tronçon à ajouter à la collection.
     */
    protected void ajouterTronconSortant(Troncon troncon){
        tronconsSortants.add(troncon);
    }
    
    /**
     * Retourne la collection de tronçons sortant de l'intersection.
     * @return La collection de tronçons sortant de l'intersection.
     */
    public Iterator<Troncon> getTronconsSortants() {
        Collection constCollection = Collections.unmodifiableCollection(tronconsSortants);
        return constCollection.iterator();
    }
    
    /**
     * Retourne l'id identifiant une intersection.
     * @return L'id identifiant une intersection.
     */
    public int getId(){
        return idIntersection;
    }
    
    /**
     * Retourne la coordonnée spatiale d'ordonnée d'une intersection sur le plan. 
     * @return La coordonnée spatiale d'ordonnée d'une intersection sur le plan.
     */
    public int getX(){
        return x;
    }
    
    /**
     * Retourne la coordonnée spatiale d'abscisse d'une intersection sur le plan. 
     * @return La coordonnée spatiale d'abscisse d'une intersection sur le plan. 
     */
    public int getY(){
        return y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Intersection other = (Intersection)obj;
        return idIntersection == other.idIntersection && x == other.x && y == other.y;
    }
}
