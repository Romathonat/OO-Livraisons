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
        tronconsSortants = new ArrayList<>();
    }
    
    protected void ajouterTronconSortant(Troncon troncon){
        tronconsSortants.add(troncon);
    }
    
    public Iterator<Troncon> getTronconsSortants() {
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
