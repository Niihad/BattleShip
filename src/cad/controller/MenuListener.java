package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cad.BattleShip;
import cad.model.Model;
import cad.model.Sauvegarde;

public class MenuListener implements ActionListener {
	private Model mod;
	private int choix;
	private BattleShip bs;
	private String[] strategy;

	public MenuListener(int i, BattleShip bs) {
		this.bs = bs;
		this.mod = bs.getModel();
		this.choix = i;
		this.strategy = new String[3];
	}

	public void actionPerformed(ActionEvent arg0) {
		switch (choix) {
		case 0:
			System.out.println(mod);
			new Sauvegarde("Thomas", mod);
			break;
		case 1:
			for (int i = 0; i <= mod.getStrategie().size() - 1; i++)
				strategy[i] = mod.getStrategie().get(i).getNameStrategy();
			Integer choix =  JOptionPane.showOptionDialog(null, "Changer la strategie en", "Strategie",
			        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
			        null, strategy, strategy[0]);
			if(choix != null)
				mod.setContext(mod.getStrategie().get(choix));
			break;
		case 2:
			System.exit(0);
			break;
		default:
		}

	}

}
