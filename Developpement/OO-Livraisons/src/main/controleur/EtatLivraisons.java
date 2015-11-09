/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import io.ExceptionXML;

/**
 * Cet Etat représente l'ensemble des Etats pouvant effectuer l'action "chargerLivraisons".
 * @author tfavrot
 */
public class EtatLivraisons extends EtatPlan{
    
    @Override
    protected void activerFonctionnalites(){
        super.activerFonctionnalites();
        Controleur.fenetre.activerChargerDemandesLivraisons(true);
    }
    
    @Override
    public void chargerLivraisons(File file){
        try {
            Controleur.modeleManager.chargerEnsembleLivraisons(file);
            Controleur.fenetre.getVue().supprimerInterSelectionee();
            Controleur.setEtatCourant(Controleur.etatLivraisonChargee);
        } catch (ParserConfigurationException | SAXException | IOException | ExceptionXML | ParseException ex) {
            Controleur.fenetre.afficherMessage(ex.getMessage());
        }
    }
}
