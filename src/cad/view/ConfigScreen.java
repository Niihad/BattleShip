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
import cad.model.Model;

public class ConfigScreen extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private String[] age,strategy;
	private JComboBox tirOrdinateur,epoque,tiragealea;
	private int choixEpoque,choixStrategie,choixTirage;
	private Label tirIa,eq,choix;
	private JButton play;
	private BattleShip bs;
	private Model mod;

	public ConfigScreen(BattleShip battleShip) {
		this.bs = battleShip;
		this.mod = battleShip.getModel();
		this.choixEpoque = 0;
		this.choixTirage = 0;	
		this.choixStrategie = 0;
		this.strategy = new String[3];
		drawConfig();
	}

	private void drawConfig() {
		for(int i = 0; i <= mod.getStrategie().size()-1;i++)
			strategy[i] = mod.getStrategie().get(i).getNameStrategy();
		this.tirOrdinateur = new JComboBox(strategy);

		tirOrdinateur.addItemListener(this);
		
		this.epoque = new JComboBox<Object>(mod.getEpoqueName());
		epoque.addItemListener(this);
		
		this.tiragealea = new JComboBox(new String[]{"Pile","Face"});
		this.tirIa = new Label("Strategie tir IA");
		this.eq = new Label("Epoque");
		this.play = new JButton("Play");
		this.choix = new Label("Faire votre choix");
		play.addActionListener(new ConfigListener(bs,this));

		this.setBackground(Color.GREEN);
		Box panneauBouton = Box.createVerticalBox();
		panneauBouton.add(tirIa);
		panneauBouton.add(tirOrdinateur);
		panneauBouton.add(eq);
		panneauBouton.add(epoque);
		panneauBouton.add(choix);
		panneauBouton.add(tiragealea);
	    panneauBouton.add(Box.createVerticalStrut(60));
	    panneauBouton.add(play);
	    this.add(panneauBouton);
	}

	public void itemStateChanged(ItemEvent arg0) {

		if(arg0.getSource() == tirOrdinateur)
			choixStrategie = tirOrdinateur.getSelectedIndex();
		
		// R�cup�ration du param�tre de l'�poque
		if(arg0.getSource() == epoque)
			choixEpoque = epoque.getSelectedIndex();
		
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
	
}
