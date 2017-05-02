package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import cad.BattleShip;
import cad.model.Model;
import cad.model.Model.Etat;
import cad.view.ConfigScreen;

public class ConfigListener implements ActionListener {
	private ConfigScreen cScreen;
	private BattleShip bs;
	private Model mod;

	public ConfigListener(BattleShip bs, ConfigScreen configScreen) {
		this.cScreen = configScreen;
		this.bs = bs;
		this.mod = bs.getModel();
	}

	public void alea(int choix){
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(2);
		if(choix == nombreAleatoire)
			mod.setEtat(Etat.PLAYER);
		else
			mod.setEtat(Etat.IA);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		cScreen.setVisible(false);
		this.mod.setContext(mod.getStrategie().get(cScreen.getChoixStrategie()));
		alea(cScreen.getChoixTirage());
		mod.selectionEpoque(mod.getEpoqueName()[cScreen.getChoixEpoque()], mod.chargementEpoque(cScreen.getChoixEpoque(), "epoques"));
		bs.setPlacementScreen();	}

}
