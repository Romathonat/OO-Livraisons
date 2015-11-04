/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;
import modele.Plan;
import org.xml.sax.SAXException;
import xml.DeserialiseurXML;
import xml.ExceptionXML;

/**
 * Cette classe représente l'ensemble des états qui peuvent charger un plan.
 * @author tfavrot
 */
public class EtatPlan extends EtatDefaut{
    
    @Override
    public void chargerPlan(Plan plan){
        try {
            DeserialiseurXML.chargerPlan(plan);
            Controleur.setEtatCourant(Controleur.etatPlanCharge);
        } catch (ParserConfigurationException | SAXException | IOException | ExceptionXML | ParseException ex) {
           Controleur.fenetre.sendMessage(ex.getMessage());
        }
    }
}
