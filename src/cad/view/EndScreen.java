package cad.view;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.model.Model;

public class EndScreen extends JPanel{
	private Model mod;
	private BattleShip bs;
	private Label win;
	private JButton play,exit;



	public EndScreen(BattleShip battleShip) {
		this.bs = battleShip;
		this.mod = bs.getModel();
		draw();
	}


	private void draw() {
		this.setBackground(Color.yellow);
		if(mod.getLife() == 0)
			this.win = new Label("Vous avez perdu");
		else
			this.win = new Label("Vous avez gagné");
		this.add(win);
		
		this.play = new JButton("Retour ecran d'accueil");
		this.exit = new JButton("Quitter");
		
		Box panneauBouton = Box.createVerticalBox();
		panneauBouton.add(play);
	    panneauBouton.add(Box.createVerticalStrut(20));
		panneauBouton.add(exit);
		this.add(panneauBouton);
		play.addActionListener(new ActionListener(){  	
	        public void actionPerformed(ActionEvent event){
	        	setVisible(false);
	        	bs.replay();}});	
		
		exit.addActionListener(new ActionListener(){  	
	        public void actionPerformed(ActionEvent event){
	        	System.exit(0);}});		
	}

}
