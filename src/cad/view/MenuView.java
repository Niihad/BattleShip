package cad.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cad.controller.MenuListener;
import cad.model.Model;

public class MenuView extends JMenuBar{
	private JMenu menu;
	private JMenuItem sauvegarder,quitter,strategie;
	private Model mod;

	public MenuView(Model model) {
		this.menu = new JMenu("Reglage");
		this.sauvegarder = new JMenuItem("Sauvegarder");
		this.quitter = new JMenuItem("Quitter");
		this.strategie = new JMenuItem("Changer la strategieIA");
		sauvegarder.addActionListener(new MenuListener(0,mod));
		strategie.addActionListener(new MenuListener(1, mod));
		quitter.addActionListener(new MenuListener(2, mod));
		menu.add(sauvegarder);
		menu.add(strategie);
		menu.add(quitter);
		add(menu);
		}



}
