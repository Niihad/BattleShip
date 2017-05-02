package cad.view;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.model.Aleatoire;
import cad.model.Context;
import cad.model.Diagonale;
import cad.model.Intelligent;
import cad.model.Model;

public class ConfigScreen extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private String[] strategy;

	private JComboBox tirOrdinateur,epoque,tiragealea;
	private String strategy1,strategy2,strategy3;
	private int choixEpoque,choixStrategie,choixtirage;
	private Label tirIa,eq,choix;
	private JButton play;
	private BattleShip bs;
	private Model mod;
	private Context context,context2,context3;

	public ConfigScreen(BattleShip battleShip) {
		this.bs = battleShip;
		this.mod = battleShip.getModel();
		this.choixEpoque = 0;
		
		this.context = new Context(new Aleatoire());
		this.strategy1 = context.getNameStrategy();
		//On affecte la strategie alea de base si y on touche pas les combobox
		this.mod.setContext(context);
	
		context2 = new Context(new Diagonale());
		this.strategy2 = context2.getNameStrategy();
		
		context3 = new Context(new Intelligent());
		this.strategy3 = context3.getNameStrategy();
		
		drawConfig();
	}

	private void drawConfig() {
		//Ajouter le nom de la strategy recup dans le controlleur dans le tableau 
		// et ajouter un cas dans la fct itemStateChanged
		this.strategy = new String[]{strategy1,strategy2,strategy3};
		this.tirOrdinateur = new JComboBox<Object>(strategy);
		tirOrdinateur.addItemListener(this);
		
		this.epoque = new JComboBox<Object>(mod.getEpoqueName());
		epoque.addItemListener(this);
		
		this.tiragealea = new JComboBox(new String[]{"Pile","Face"});
		this.tirIa = new Label("Strategie tir IA");
		this.eq = new Label("Epoque");
		this.play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				mod.selectionEpoque(mod.getEpoqueName()[choixEpoque], mod.chargementEpoque(choixEpoque, "epoques"));
				bs.setPlacementScreen();
			}
		});
		this.choix = new Label("Faire votre choix");
		
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
			choixtirage = tiragealea.getSelectedIndex();
	
	}

	public Context getChoixStrategie() {
		if( choixStrategie == 0)
			   return context;
		if( choixStrategie == 1)
			   return context2;
		if( choixStrategie == 2)
			   return context3;
		   return context;
	}
	
}
