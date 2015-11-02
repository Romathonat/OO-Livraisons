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
    public void chargerLivraisons(Plan plan, EnsembleLivraisons ensembleLivraisons){
        try {
            DeserialiseurXML.chargerDemandesLivraisons(plan, ensembleLivraisons);
            Controleur.setEtatCourant(Controleur.etatLivraisonChargee);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionXML ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EtatInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
