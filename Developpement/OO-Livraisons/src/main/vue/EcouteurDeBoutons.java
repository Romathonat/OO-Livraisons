/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
import controleur.Controleur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Kilian
 */
public class EcouteurDeBoutons extends AbstractAction {
    
    private Controleur controleur;
    
    EcouteurDeBoutons(String title, Controleur c) {
        super(title);
        controleur = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Charger Plan" :
                controleur.chargerPlan();
                break;
            case "Charger Ensemble Livraisons" :
                controleur.chargerLivraisons();
                break;
        }
    }
    
}
