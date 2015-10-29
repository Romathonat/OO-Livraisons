/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Iterator;

/**
 *
 * @author Kilian
 */
public class ModeleManager {

    private Plan plan;
    private EnsembleLivraisons ensembleLivraisons;
    private Tournee tournee;
    private long tempsDerniereTourneeCalculee;

    public ModeleManager() {
        this.plan = new Plan();
        this.ensembleLivraisons = null;
        this.tournee = null;
        this.tempsDerniereTourneeCalculee = Long.MAX_VALUE;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public EnsembleLivraisons getEnsembleLivraisons() {
        return this.ensembleLivraisons;
    }

    public Tournee getTournee() {
        return this.tournee;
    }

    public void setEnsembleLivraisons(EnsembleLivraisons ensembleLivraisons) {
        this.ensembleLivraisons = ensembleLivraisons;
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }

    public double getPremiereTournee() {
        /*
         * Calcul de la tournee selon le principe suivant:
         * On prend séquentiellement les points de livraison en respectant l'ordre
         * des fenetres de livraison.
         * On établit le plus court chemin entre deux points successifs
         * On somme.
         */

        /*
         * REMARQUE: cette méthode peut poser problème.
         * admettons qu'elle prenne un temps t1 sur 5 fenetre. mais ne respecte
         * pas la fenetre 4.
         * Peut-etre que par suite, on trouvera un itineraire qui prend un temps
         * t2 mais qui respecte la fenetre 4.
         * ... Bref on s'asseoit dessus pour le momment, on ajoutera une heuristique
         * qui veille à respecter les fenetres temporelle avant de s'inquieter du temps.
         */
        Iterator<FenetreLivraison> it_fenetre = ensembleLivraisons.getFenetresLivraison();

        // deux premières intersections : Entrepot et premiere demande de la premiere fenetre.
        Intersection interPrecedente = ensembleLivraisons.getEntrepot();
        Intersection interCourante = null;

        // premier chemin donc.
        Chemin chemin = null;

        /*
         * boucle centrale de "l'algorithme".
         * on récupère la prochaine fenetre.
         * on récupère la prochaine demande de la fenetre en cours.
         * tant qu'elle a un suivant (au sens de l'iterateur) on calcule les plus
         * courts chemin de la demande précédente à la demande en cours.
         * quand il n'y a plus de "demande suivante", on passe à la fenetre suivante
         * quand il n'y a plus de "fenetre suivante" on relie la derniere demande à l'entrepot.
         */
        while (it_fenetre.hasNext()) {

            FenetreLivraison fenetre = it_fenetre.next();

            // permet de verifier que la fenetre en cours a au moins une demande de livraison.
            if (!fenetre.getDemandesLivraison().hasNext()) {
                continue;
            }

            Iterator<DemandeLivraison> it_demande = fenetre.getDemandesLivraison();

            while (it_demande.hasNext()) {
                // récupération de l'intersection Courante.
                interCourante = it_demande.next().getIntersection();

                // calcul et stockage du plus court chemin entre les deux points.
                chemin = plan.calculerPlusCourtChemin(interPrecedente, interCourante);
                tournee.AjouterChemin(chemin);

                // l'intersection courante devient l'intersection précédente.
                interPrecedente = interCourante;
            }
        }

        // on retourne à l'entrepot
        chemin = plan.calculerPlusCourtChemin(interPrecedente, ensembleLivraisons.getEntrepot());
        tournee.AjouterChemin(chemin);

        return tournee.getTempsDeLivraison() ;
    }
}
