package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cad.model.Model;

public class MenuListener implements ActionListener {
	private Model mod;
	private int choix;

	public MenuListener(int i, Model mod) {
		this.mod = mod;
		this.choix = i;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (choix) {
		case 0:
			break;	
		case 1:
			break;
		case 2:
			System.exit(0);
			break;
		default:
		}

	}

}