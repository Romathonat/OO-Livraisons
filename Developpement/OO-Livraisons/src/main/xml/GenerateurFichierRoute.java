/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Kilian
 */
public class GenerateurFichierRoute {// Singleton
    
    private static GenerateurFichierRoute instance = null;

    private GenerateurFichierRoute() {
    }

    public static GenerateurFichierRoute getInstance() {
        if (instance == null) {
            instance = new GenerateurFichierRoute();
        }
        return instance;
    }
    
    public static File ouvreFichier() {
        int returnVal;
        JFileChooser jFileChooserXML = new JFileChooser();
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
        returnVal = jFileChooserXML.showSaveDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
    }
}
