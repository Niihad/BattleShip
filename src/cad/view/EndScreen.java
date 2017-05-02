package cad.view;

import java.awt.Color;

import javax.swing.JPanel;

import cad.BattleShip;
import cad.model.Model;

public class EndScreen extends JPanel{
	private Model mod;
	private BattleShip bs;

	public EndScreen(BattleShip battleShip, Model model) {
		this.bs = battleShip;
		this.mod = model;
		this.setBackground(Color.BLUE);

	}

}
