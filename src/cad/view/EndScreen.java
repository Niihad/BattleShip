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



	public EndScreen(BattleShip battleShip, Model model) {
		this.bs = battleShip;
		this.mod = model;
		draw();
	}


	private void draw() {
		this.setBackground(Color.yellow);
		if(mod.getLife() == 0)
			this.win = new Label("YOU LOSE");
		else
			this.win = new Label("YOU WIN");
		this.add(win);
		
		this.play = new JButton("Play");
		this.exit = new JButton("Exit");
		
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
