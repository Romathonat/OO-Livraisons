/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Elément graphique de status d'une vue. 
 * @author Nicolas
 */
public class VueStatus extends JPanel {
    
    
    /**
     * La vue dans laquelle s'inscrit la VueLegende.
     */
    private Vue vue;
    
    /**
     * Message de status de droite de la barre de status.
     */
    private JLabel statusRigth;
    
    /**
     * Message de status de gauche de la barre de status.
     */
    private JLabel statusLeft;
    
    /**
     * Constructeur d'une VueStatus.
     * @param vue La vue dans laquelle s'inscrit la VueLegende.
     */
    public VueStatus(Vue vue){
        super();
        this.vue = vue;
        this.setLayout(new BorderLayout());
        this.statusRigth = new JLabel("OO-Livraison");
        this.statusLeft = new JLabel("");
        this.add(statusRigth, BorderLayout.WEST);
        this.add(statusLeft, BorderLayout.EAST);
    }
    
    /**
     * Change le message côté droit de la barre de status
     * @param status Le messase de status à afficher dans la barre de status.
     */
    public void updateStatusDroit(String status) {
        this.statusRigth.setText(status);
    }
}
