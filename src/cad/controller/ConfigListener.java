package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cad.BattleShip;
import cad.view.ConfigScreen;

public class ConfigListener implements ActionListener {
	private ConfigScreen cScreen;
	private BattleShip bs;

	public ConfigListener(BattleShip bs, ConfigScreen configScreen) {
		this.cScreen = configScreen;
		this.bs = bs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cScreen.setVisible(false);
		this.bs.getModel().selectionEpoque(cScreen.getChoixNomEpoque(), this.bs.getModel().chargementEpoque(cScreen.getChoixEpoque(), "epoques"));
		bs.setGameScreen();
	}

}
