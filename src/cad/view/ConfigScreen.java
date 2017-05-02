package cad.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cad.BattleShip;
import cad.controller.ConfigListener;

import cad.model.Aleatoire;
import cad.model.Context;
import cad.model.Diagonale;
import cad.model.Intelligent;
import cad.model.Model;
import cad.model.Ship;

public class ConfigScreen extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;

	private String[] strategy,age;
	private JComboBox tirOrdinateur,epoque,tiragealea;
	private int choixEpoque,choixStrategie,choixTirage;
	private JLabel tirIa,eq,choix,pseudo;
	private JButton play;
	private JTextField pseudoField;
	private BattleShip bs;
	private Model mod;

	public ConfigScreen(BattleShip battleShip) {
		this.bs = battleShip;
		this.mod = bs.getModel();
		this.choixEpoque = 0;
		this.choixTirage = 0;	
		this.choixStrategie = 0;
		this.strategy = new String[3];
		drawConfig();
		controller();
	}

	private void controller() {
		play.addActionListener(new ConfigListener(bs,this));
	}


	private void drawConfig() {
		//recuperation des diff strategie
		for(int i = 0; i <= mod.getStrategie().size()-1;i++)
			strategy[i] = mod.getStrategie().get(i).getNameStrategy();
		this.tirOrdinateur = new JComboBox(strategy);
		tirOrdinateur.addItemListener(this);
		
		//recuperation des diff epoques
		this.age = this.mod.chargementNomEpoque();
		this.epoque = new JComboBox(age);
		epoque.addItemListener(this);
		
		this.tiragealea = new JComboBox(new String[]{"Pile","Face"});
		this.tirIa = new JLabel("Strategie tir IA");
		this.tirIa.setForeground(Color.WHITE);
		this.eq = new JLabel("Epoque");
		this.eq.setForeground(Color.WHITE);
		this.play = new JButton("Play");
		this.choix = new JLabel("Faire votre choix");
		this.choix.setForeground(Color.WHITE);
		this.pseudo = new JLabel("Pseudo :");
		this.pseudo.setForeground(Color.WHITE);
		this.pseudoField = new JTextField("pseudo");

		play.addActionListener(new ConfigListener(bs,this));

		Box panneauBouton = Box.createVerticalBox();
	    panneauBouton.add(Box.createVerticalStrut(10));
		panneauBouton.add(pseudo);
		panneauBouton.add(pseudoField);
		panneauBouton.add(tirIa);
		panneauBouton.add(tirOrdinateur);
		panneauBouton.add(eq);
		panneauBouton.add(epoque);
		panneauBouton.add(choix);
		panneauBouton.add(tiragealea);
	    panneauBouton.add(Box.createVerticalStrut(20));
	    panneauBouton.add(play);
		this.add(panneauBouton);		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		//recuperation du choix de strategie
		if(arg0.getSource() == tirOrdinateur)
			choixStrategie = tirOrdinateur.getSelectedIndex();
		
		// Récupération du paramètre de l'époque
		if(arg0.getSource() == epoque)
			choixEpoque = epoque.getSelectedIndex();
		
		//recuperation du choix pour le tirage alea pour savoir qui commence
		if(arg0.getSource() == tiragealea)
			choixTirage = tiragealea.getSelectedIndex();
	
	}

	public int getChoixTirage() {
		return choixTirage;
	}

	public int getChoixStrategie() {
		   return choixStrategie;
	}
	
	public int getChoixEpoque() {
  		return choixEpoque;
  	}		  	

	public void paintComponent(Graphics g){
        try{
	        Image img = ImageIO.read(new File("./assets/image/Config.jpg"));
           int height = this.getSize().height;
           int width = this.getSize().width;
           g.drawImage(img, 0, 0, width, height, this);
	    } catch(IOException e){
	        e.printStackTrace();
	    }
   }
		  	
	public String getChoixNomEpoque() {
		return age[choixEpoque];
	}
	

	public JTextField getPseudoField() {
		return pseudoField;
	}
	
	
	

}
