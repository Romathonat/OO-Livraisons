/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import modele.EnsembleLivraisons;
import modele.Plan;
import org.xml.sax.SAXException;
import xml.DeserialiseurXML;
import xml.ExceptionXML;

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
    public void chargerLivraisons(){
        try {
            Controleur.modeleManager.chargerEnsembleLivraisons();
            Controleur.setEtatCourant(Controleur.etatLivraisonChargee);
        } catch (ParserConfigurationException | SAXException | IOException | ExceptionXML | ParseException ex) {
            Controleur.fenetre.EnvoyerMessage(ex.getMessage());
        }
    }
}
