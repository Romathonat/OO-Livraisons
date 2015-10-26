/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import modele.*;
import org.xml.sax.SAXException;
import xml.*;

/**
 *
 * @author Kilian
 */
public class EtatInitial extends EtatDefaut {

    @Override
    public void chargerPlan(Plan plan){
        try {
            DeserialiseurXML.chargerPlan(plan);
            Controleur.setEtatCourant(Controleur.etatPlanCharge);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionXML ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
