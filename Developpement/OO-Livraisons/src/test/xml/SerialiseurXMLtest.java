/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import modele.Chemin;
import modele.Plan;
import modele.Tournee;
import org.junit.Test;

/**
 *
 * @author Guillaume Kheng
 */
public class SerialiseurXMLtest {
    
    @Test
    public void testSerialisation() {
        
        
        Tournee tournee = new Tournee();
        
        Chemin chemin1 = new Chemin();
        Chemin chemin2 = new Chemin();
        Chemin chemin3 = new Chemin();
        
        tournee.AjouterChemin(chemin1);
        tournee.AjouterChemin(chemin2);
        tournee.AjouterChemin(chemin3);
        
        try {
            
            SerialiseurXML.exporterTournee(tournee);
        } catch (ParserConfigurationException parserConfigurationException) {
        } catch (TransformerFactoryConfigurationError transformerFactoryConfigurationError) {
        } catch (TransformerException transformerException) {
        } catch (ExceptionXML exceptionXML) {
        }
        
        
    }
}
