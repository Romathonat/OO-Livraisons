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
    
    public Intersection(int id, int X, int Y) {
    
    }
    
    protected void ajouterTronconSortant(Troncon troncon){
        
    }
    
    public Iterator<Troncon> getTronconsSortants(){
        Collection constCollection = Collections.unmodifiableCollection(tronconsSortants);
        return constCollection.iterator();
    }
    
}
