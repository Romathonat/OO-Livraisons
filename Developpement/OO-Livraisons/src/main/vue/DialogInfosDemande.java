/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modele.DemandeLivraison;
import modele.FenetreLivraison;
import modele.Intersection;

/**
 *
 * @author romain
 */
public class DialogInfosDemande extends JDialog {
    JPanel idDemande;
    JPanel idClient;
    JPanel listeFenetres;
    JPanel fin;
    
    JTextField monIdClient;
    JTextField monIdDemande;
    
    JComboBox MaListeFenetres;
    JButton ok;
    
    Intersection interCourante; 
    
    /**
     * affiche la fenetre et recupere les infos pour creer un DemandeLiraison
     * @return la demande de livraison instanciée
     */
    public DemandeLivraison showDialog() {
        setVisible(true);
        
        int idClientInt = 0;
        int idDemandeInt = 0;
        
        if(!monIdClient.getText().isEmpty() && !monIdDemande.getText().isEmpty()){
            idClientInt = Integer.parseInt(monIdClient.getText());
            idDemandeInt = Integer.parseInt(monIdDemande.getText());
        }

        DemandeLivraison retour = new DemandeLivraison(idClientInt,idDemandeInt, this.interCourante, (FenetreLivraison) MaListeFenetres.getSelectedItem());
        return retour;
    }
    
    public DialogInfosDemande(JFrame parent, Intersection inter, Iterator<FenetreLivraison> itFenetre) {
        super(parent);
        this.interCourante = inter;
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        JPanel monPanel = (JPanel)this.getContentPane();
        
        idDemande = new JPanel();
        idClient = new JPanel();
        listeFenetres = new JPanel();
        fin = new JPanel();
        
        monPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        monIdDemande = new JTextField();
        monIdDemande.setColumns(5);
        JLabel lIdDemande = new JLabel("id Demande:");
        idDemande.add(lIdDemande);
        idDemande.add(monIdDemande);
        
        monIdClient = new JTextField();
        monIdClient.setColumns(5);
        JLabel lIdclient = new JLabel("id Client:");
        idClient.add(lIdclient);
        idClient.add(monIdClient);
        
        ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        }); 
        
        fin.add(ok);
        
        MaListeFenetres = new JComboBox();
        JLabel llisteFenetre = new JLabel("Fenetre horaire:");
        
        while(itFenetre.hasNext()){
            MaListeFenetres.addItem(itFenetre.next());
        }
        
        listeFenetres.add(llisteFenetre);
        listeFenetres.add(MaListeFenetres);
        
        this.add(idDemande);
        this.add(idClient);
        this.add(listeFenetres);
        this.add(fin);
        
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        
        setSize(new Dimension(300,250));
    }
    
}
