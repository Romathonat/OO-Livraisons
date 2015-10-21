/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author tfavrot
 */
public class Tournee {
    
    // Attributs
    
    private List<Chemin> chemins;
    
    // Methodes

    public Tournee() {
        
    }
    
    public void CalculerHeuresDemandesLivraisons(){
        
    }
    
    public Iterator<Chemin> getChemins(){
        List<Chemin> constList = Collections.unmodifiableList(chemins);
        return constList.iterator();
    }
    
}
