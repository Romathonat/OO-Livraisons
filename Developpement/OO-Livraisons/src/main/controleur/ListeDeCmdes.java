/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kilian
 */
public class ListeDeCmdes {

    private List<Commande> liste;
    /**
     * L'index de l'etat courant
     */
    private int index;

    public ListeDeCmdes() {
        liste = new ArrayList<Commande>();
        index = 0;
    }

    public void ajoute(Commande commande) {
        liste.add(commande);
        index++;
    }

    public void undo() {
        if (index >= 0) {
            liste.get(index--).undoCode();
        }
    }

    public void redo() {
        if (index < liste.size()-1) {
            liste.get(++index).doCode();
        }
    }
}
