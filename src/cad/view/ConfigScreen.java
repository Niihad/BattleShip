package cad.view;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.controller.ConfigListener;
import cad.model.Aleatoire;
import cad.model.Context;
import cad.model.Model;

public class ConfigScreen extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private String[] strategy,age;

	private JComboBox tirOrdinateur,epoque;
	private String strategy1,strategy2;
	private Label tirIa,eq;
	private JButton play;
	private BattleShip bs;
	private Model mod;
	private Context context,context2;

	public ConfigScreen(BattleShip battleShip, Model model) {
		this.bs = battleShip;
		this.mod = model;
		
		this.context = new Context(new Aleatoire());
		this.strategy1 = context.getNameStrategy();
		//On affecte la strategie alea de base si y on touche pas les combobox
		this.mod.setContext(context);
		/*
		context2 = new Context(new Aleatoire());
		this.strategy2 = context2.getNameStrategy();*/
		
		drawConfig();
		controller();
	}

	private void controller() {
		play.addActionListener(new ConfigListener(bs,this));
	}


	private void drawConfig() {
		//Ajouter le nom de la strategy recup dans le controlleur dans le tableau 
		// et ajouter un cas dans la fct itemStateChanged
		this.strategy = new String[]{strategy1};
		this.tirOrdinateur = new JComboBox(strategy);
		tirOrdinateur.addItemListener(this);
		
		this.age = new 	String[]{"Moderne","Pirate","Romaine"}; // a recup
		this.epoque = new JComboBox(age);
		epoque.addItemListener(this);
		
		this.tirIa = new Label("Strategie tir IA");
		this.eq = new Label("Epoque");
		this.play = new JButton("Play");
		
		this.setBackground(Color.GREEN);
		Box panneauBouton = Box.createVerticalBox();
		panneauBouton.add(tirIa);
		panneauBouton.add(tirOrdinateur);
		panneauBouton.add(eq);
		panneauBouton.add(epoque);
	    panneauBouton.add(Box.createVerticalStrut(60));
	    panneauBouton.add(play);
		this.add(panneauBouton);		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getSource() == tirOrdinateur){
		  int tmp = epoque.getSelectedIndex();
		switch (tmp){
		  case 0:
			  this.mod.setContext(context);
			  break;  
		  case 1:
			  this.mod.setContext(context2);
			  break;  
		  default:
			  this.mod.setContext(context);
			}
		}
	}

}
