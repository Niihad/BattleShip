package cad.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import cad.BattleShip;
import cad.controller.MenuListener;

@SuppressWarnings("serial")
public class MenuView extends JMenuBar{
	private JMenu menu;
	private JMenuItem sauvegarder,quitter,strategie;

	//barre de menu
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
