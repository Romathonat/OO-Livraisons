/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


/**
 *
 * @author tfavrot
 */
public class Intersection {

    // Attributs
    
    private int idIntersection;
    private int x;
    private int y;
    private Collection<Troncon> tronconsSortants;
    
    // Methodes
    
    public Intersection(int id, int x, int y) {
        idIntersection = id;
        this.x = x;
        this.y = y;
        
        // TODO : choisir structure de donnees pour tronconsSortants
        // tronconsSortants = new 
    }
    
    protected void ajouterTronconSortant(Troncon troncon){
        tronconsSortants.add(troncon);
    }
    
    public Iterator<Troncon> getTronconsSortants(){
        Collection constCollection = Collections.unmodifiableCollection(tronconsSortants);
        return constCollection.iterator();
    }
    
    public int getId(){
        return idIntersection;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
