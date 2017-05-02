package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cad.BattleShip;
import cad.model.Model;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		cScreen.setVisible(false);
		this.mod.selectionEpoque(cScreen.getChoixNomEpoque(), this.mod.chargementEpoque(cScreen.getChoixEpoque()));
		bs.setGameScreen();
	}

}
