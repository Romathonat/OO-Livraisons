/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import io.SauvegardeFichierTxt;
import java.io.File;
import java.io.IOException;
import xmlModele.SerialiseurXML;

/**
 *
 * @author Kilian
 */
public class EtatTourneeCalculee extends EtatSelection {

    @Override
    protected void activerFonctionnalites() {
        super.activerFonctionnalites();
        Controleur.fenetre.activerGenererFeuilleRoute(true);
        Controleur.fenetre.activerAnnuler(true);
        Controleur.fenetre.activerRetablir(true);
    }

    @Override
    public void genererFeuilleRoute() {
        try {
            File file = SauvegardeFichierTxt.getInstance().ouvreFichier();
            if (file != null) {
                SerialiseurXML.exporterTournee(file, Controleur.modeleManager.getTournee());
            }
        } catch (IOException ex) {
            Controleur.fenetre.afficherMessage("L'exportation a échoué : " + ex);
        }
    }

    @Override
    public void undo() {
        Controleur.listeCommandes.undo();
    }

    @Override
    public void redo() {
        Controleur.listeCommandes.redo();
    }
}
