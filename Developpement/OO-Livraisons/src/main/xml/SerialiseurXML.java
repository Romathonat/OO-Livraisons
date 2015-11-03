package xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.swing.JFileChooser;

import modele.Chemin;
import modele.Tournee;
import modele.Troncon;


public class SerialiseurXML {// Singleton

    static DateFormat SerialiseurXML_df = new SimpleDateFormat("HH:mm:ss");

    /**
     * Ouvre un fichier xml et ecrit dans ce fichier une description xml de plan
     *
     * @param tournee
     */
    public static void exporterTournee(Tournee tournee) throws IOException {
        // récupération du document.
        File fichierTxt = SerialiseurXML.ouvreFichier();
        FileWriter fw = new FileWriter(fichierTxt);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter fichierSortie = new PrintWriter(bw);

        // création de l'arborescence
        if (sauverTournee(fichierSortie, tournee) != 0) {
            bw.close();
            throw new IOException("Erreur de sauvegarde");
        }

        // sauvegarde du fichier.
        bw.close();
    }

    private static int sauverTournee(PrintWriter fichierSortie, Tournee tournee) {

        // sauvegarde des infos de la tournée.
        // Mise en page:
        /*Element racine = document.createElement("Carnet_de_Route");
         creerAttribut(document, racine, "Date_Debut", "ADD method Here");
         creerAttribut(document, racine, "Date_Fin", "ADD method Here");*/
        fichierSortie.println("-----------------");
        fichierSortie.println(" Carnet de Route");
        fichierSortie.println("-----------------");
        fichierSortie.println("");

        fichierSortie.println("Resumé de l'itinéraire :");
        fichierSortie.println("-------------------------");

        Iterator<Chemin> it_chemin = tournee.getChemins();
        int compteLivraisons = 0;
        Chemin dernierChemin = null;
        while (it_chemin.hasNext()) {
            compteLivraisons++;
            dernierChemin = it_chemin.next();
        }
        fichierSortie.println("Vous avez " + Integer.toString(compteLivraisons - 1) + " demande(s) de livraison prévue(s).");
        String str_Depart = "Vous partez de l'entrepot situé au niveau de l'intersection " + Integer.toString(tournee.getChemins().next().getIntersectionDepart().getId());
        str_Depart += " à " + SerialiseurXML_df.format(tournee.getChemins().next().getLivraisonArrivee().getFenetreLivraison().getHeureDebut());
        fichierSortie.println(str_Depart);
        fichierSortie.println("Vous revenez à l'entrepot à " + SerialiseurXML_df.format(dernierChemin.getLivraisonArrivee().getHeureLivraison()));
        fichierSortie.println("");
        fichierSortie.println("");
        fichierSortie.println("Itinéraire");
        fichierSortie.println("-----------");
        fichierSortie.println("");

        int resultatSauvegarde = 0;

        // on sauvegarde chaque chemin dans l'ordre.
        it_chemin = tournee.getChemins();
        while (it_chemin.hasNext()) {
            Chemin chemin = it_chemin.next();
            resultatSauvegarde += sauverChemin(fichierSortie, chemin);
        }
        return 0;
    }

    private static int sauverChemin(PrintWriter fichierSortie, Chemin chemin) {

        // formateur de dates.
        int resultatSauvegarde = 0;

        fichierSortie.println("Itinéraire jusqu'à la prochaine demande de livraison:");
        //fichierSortie.println("  - Intersection de départ:  " + Integer.toString(chemin.getIntersectionDepart().getId()));
        fichierSortie.println("  - point de livraison:  " + Integer.toString(chemin.getIntersectionArrivee().getId()));
        fichierSortie.println("  - Heure estimée d'arrivée: " + SerialiseurXML_df.format(chemin.getLivraisonArrivee().getHeureLivraison()));
        fichierSortie.println("  - Id du client livré:      " + Integer.toString(chemin.getLivraisonArrivee().getIdClient()));
        fichierSortie.println("--------------------------------------------------");

        Iterator<Troncon> it_troncon = chemin.getTroncons();
        while (it_troncon.hasNext()) {
            Troncon troncon = it_troncon.next();
            resultatSauvegarde += sauverTroncon(fichierSortie, troncon);
        }

        fichierSortie.println("--------------------------------------------------");
        fichierSortie.println("");
        return resultatSauvegarde;
    }

    private static int sauverTroncon(PrintWriter fichierSortie, Troncon troncon) {
        
        String str_troncon = "Depuis l'intersection " + Integer.toString(troncon.getIntersectionDepart().getId());
        str_troncon += " suivre le troncon " + troncon.getNom();
        str_troncon += " jusqu'à l'intersection " + Integer.toString(troncon.getIntersectionArrivee().getId());
        fichierSortie.println(str_troncon);
        return 0;
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
