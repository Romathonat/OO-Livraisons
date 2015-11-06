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
import modele.Plan;
import org.xml.sax.SAXException;
import xmlModele.DeserialiseurXML;
import xml.ExceptionXML;

/**
 * Cette classe représente l'ensemble des états qui peuvent charger un plan.
 * @author tfavrot
 */
public class EtatPlan extends EtatDefaut{
    
    @Override
    protected void activerFonctionnalites(){
        Controleur.fenetre.activerChargerPlan(true);
    }
    
    @Override
    public void chargerPlan(File file){
        try{
            Controleur.modeleManager.chargerPlan(file);
            Controleur.setEtatCourant(Controleur.etatPlanCharge);
        } catch (ParserConfigurationException | SAXException | IOException | ExceptionXML | ParseException ex) {
           Controleur.fenetre.EnvoyerMessage(ex.getMessage());
        }
    }
}
