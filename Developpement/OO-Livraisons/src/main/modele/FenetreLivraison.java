/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author tfavrot
 */
public class FenetreLivraison {
    
    // Attributs
    
    private Date heureDebut;
    private Date heureFin;
    private Collection<DemandeLivraison> collectionDemandeLivraison;
    
    // Methodes

    public FenetreLivraison(Date heureDebut, Date heureFin) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }
    
    
}
