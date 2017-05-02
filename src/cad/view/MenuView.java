package cad.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cad.BattleShip;
import cad.controller.MenuListener;
import cad.model.Model;

public class MenuView extends JMenuBar{
	private JMenu menu;
	private JMenuItem sauvegarder,quitter,strategie;
	private BattleShip bs;
	private Model mod;

	public MenuView(BattleShip bs) {
		this.menu = new JMenu("Reglage");
		this.sauvegarder = new JMenuItem("Sauvegarder");
		this.quitter = new JMenuItem("Quitter");
		this.strategie = new JMenuItem("Changer la strategieIA");
		sauvegarder.addActionListener(new MenuListener(0,bs));
		strategie.addActionListener(new MenuListener(1, bs));
		quitter.addActionListener(new MenuListener(2, bs));
		menu.add(sauvegarder);
		menu.add(strategie);
		menu.add(quitter);
		add(menu);
		}



}
