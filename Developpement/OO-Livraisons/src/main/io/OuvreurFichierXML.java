package io;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

public class OuvreurFichierXML extends FileFilter {// Singleton

    /**
    * Le chemin de fichier par défault de l'application lors d'une sélection de fichier.
    */
    private static File chemimChoixFichier = new File("../FichiersProjet");
    
    private static OuvreurFichierXML instance = null;

    private OuvreurFichierXML() {
    }

    public static OuvreurFichierXML getInstance() {
        if (instance == null) {
            instance = new OuvreurFichierXML();
        }
        return instance;
    }

    public File selectionFichierXML(boolean lecture) {
        int returnVal;
        JFileChooser jFileChooser = new JFileChooser(chemimChoixFichier.getAbsolutePath());
        jFileChooser.setFileFilter(this);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (lecture) {
            returnVal = jFileChooser.showOpenDialog(null);
        } else {
            returnVal = jFileChooser.showSaveDialog(null);
        }
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        String returnPath = jFileChooser.getSelectedFile().getAbsolutePath();
        chemimChoixFichier = new File(returnPath);
        return new File(jFileChooser.getSelectedFile().getAbsolutePath());
    }
    
    @Override
    public boolean accept(File f) {
        if (f == null) {
            return false;
        }
        if (f.isDirectory()) {
            return true;
        }
        String extension = getExtension(f);
        if (extension == null) {
            return false;
        }
        return extension.contentEquals("xml");
    }

    @Override
    public String getDescription() {
        return "Fichier XML";
    }

    private String getExtension(File f) {
        String filename = f.getName();
        int i = filename.lastIndexOf('.');
        if (i > 0 && i < filename.length() - 1) {
            return filename.substring(i + 1).toLowerCase();
        }
        return null;
    }
}
