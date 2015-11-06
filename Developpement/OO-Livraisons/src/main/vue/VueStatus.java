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
 *
 * @author Nicolas
 */
public class VueStatus extends JPanel {
    private JLabel statusRigth;
    private JLabel statusLeft;
    
    public VueStatus(){
        super();
        this.setLayout(new BorderLayout());
        this.statusRigth = new JLabel("OO-Livraison");
        this.statusLeft = new JLabel("");
        this.add(statusRigth, BorderLayout.WEST);
        this.add(statusLeft, BorderLayout.EAST);
    }
    
    /**
     * Change le message côté droit de la barre de status
     * @param status 
     */
    public void changerStatus(String status) {
        this.statusRigth.setText(status);
    }
}
