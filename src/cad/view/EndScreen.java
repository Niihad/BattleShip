package cad.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.model.Model;

public class EndScreen extends JPanel{
	private Model mod;
	private BattleShip bs;
	private JLabel win;
	private JButton play,exit;



	public EndScreen(BattleShip battleShip) {
		this.bs = battleShip;
		this.mod = bs.getModel();
		draw();
	}
	
	public void paintComponent(Graphics g){
        try{
        	Image img;
        	if(mod.getLife() == 0)
        		img = ImageIO.read(new File("./assets/image/GameOver.jpg"));	
        	else
        		img = ImageIO.read(new File("./assets/image/Win.jpg"));
           int height = this.getSize().height;
           int width = this.getSize().width;
           g.drawImage(img, 0, 0, width, height, this);
	    } catch(IOException e){
	        e.printStackTrace();
	    }
   }

	private void draw() {
		this.setBackground(Color.yellow);
		//on regarde par rapport au nbre du vie
		if(mod.getLife() == 0) {
			this.win = new JLabel("Vous avez perdu");
			this.win.setForeground(Color.RED);
		}
		else {
			this.win = new JLabel("Vous avez gagné");
			this.win.setForeground(Color.GREEN);
		}
		
		this.play = new JButton("Retour ecran d'accueil");
		this.exit = new JButton("Quitter");
		
		Box panneauBouton = Box.createVerticalBox();
		panneauBouton.add(win);
	    panneauBouton.add(Box.createVerticalStrut(20));
		panneauBouton.add(play);
	    panneauBouton.add(Box.createVerticalStrut(20));
		panneauBouton.add(exit);
		this.add(panneauBouton);
		
		//rejouer
		play.addActionListener(new ActionListener(){  	
	        public void actionPerformed(ActionEvent event){
	        	setVisible(false);
	        	bs.replay();}});	
		
		//quitter
		exit.addActionListener(new ActionListener(){  	
	        public void actionPerformed(ActionEvent event){
	        	System.exit(0);}});		
	}

}
