/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kilian
 */
public class ListeDeCmdes {
    
    private LinkedList<Commande> liste;
    private int indiceCurrentCommande;
    
    public ListeDeCmdes(){
        this.liste = new LinkedList<Commande>();
        this.indiceCurrentCommande = -1;
    }
    
    /**
    * Ajout de la commande commande a la liste
    * @param commande la commande a ajouter
    */
    public void ajoute(Commande commande){
        
        for (int i = indiceCurrentCommande+1; i < liste.size(); i++)
            liste.remove(i);
        
	indiceCurrentCommande++;
	liste.add(indiceCurrentCommande, commande);
	commande.doCommande();
    }
    
    /**
    * Annule temporairement la derniere commande ajoutee (cette commande pourra etre remise dans la liste avec redo)
    */
    public void undo(){
        if (indiceCurrentCommande >= 0){
            Commande commande = liste.get(indiceCurrentCommande);
            indiceCurrentCommande--;
            commande.undoCommande();
	}
        Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
    }
    
    /**
    * Remet dans la liste la derniere commande annulee avec undo
    */
    public void redo(){
        if (indiceCurrentCommande < liste.size()-1){
            indiceCurrentCommande++;
            Commande commande = liste.get(indiceCurrentCommande);
            commande.doCommande();
            Controleur.setEtatCourant(Controleur.etatTourneeCalculee);
	}
    }
}
