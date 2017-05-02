package cad.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import cad.BattleShip;

//fenetre menu pour les choix entre jouer - charger - quitters
public class MenuScreen extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BattleShip bs;
	private JButton play,load,exit;

	public MenuScreen(BattleShip battleShip) {
		this.bs = battleShip;
		drawMenu();
		controller();
	}

	private void controller() {
		//on passe a l ecran de jeux
		play.addActionListener(new ActionListener(){  	
	        public void actionPerformed(ActionEvent event){
	        	setVisible(false);
	        	bs.setConfigScreen();}});
		
		//on passe a l ecran de chargement
		load.addActionListener(new ActionListener(){  	
	        public void actionPerformed(ActionEvent event){
	        	setVisible(false);
	        	bs.setLoadScreen();
	        	}});
		 
		//on quitte
		exit.addActionListener(new ActionListener(){  	
	        public void actionPerformed(ActionEvent event){
	        	System.exit(0);}});		
	}

	private void drawMenu() {
		this.play = new JButton("Jouer");
		this.load = new JButton("Charger");
		this.exit = new JButton("Quitter");
		this.setBackground(Color.BLUE);
		Box panneauBouton = Box.createVerticalBox();
		panneauBouton.add(play);
	    panneauBouton.add(Box.createVerticalStrut(20));
		panneauBouton.add(load);
	    panneauBouton.add(Box.createVerticalStrut(20));
		panneauBouton.add(exit);
		this.add(panneauBouton);
	}
	
	public void paintComponent(Graphics g){
         try{
	        Image img = ImageIO.read(new File("./assets/s1.png"));
            int height = this.getSize().height;
            int width = this.getSize().width;
            g.drawImage(img, 0, 0, width, height, this);
	    } catch(IOException e){
	        e.printStackTrace();
	    }
    }
	
}
