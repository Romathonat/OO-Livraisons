/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Kilian
 */
public class SauvegardeFichierTxt {// Singleton
    
    private static SauvegardeFichierTxt instance = null;

    private SauvegardeFichierTxt() {
    }

    public static SauvegardeFichierTxt getInstance() {
        if (instance == null) {
            instance = new SauvegardeFichierTxt();
        }
        return instance;
    }
    
    public File ouvreFichier() {
        int returnVal;
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        returnVal = jFileChooser.showSaveDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".txt");
    }
}
