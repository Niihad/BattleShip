package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cad.BattleShip;
import cad.model.Model;
import cad.model.Model.Etat;
import cad.view.CellView;

public class GameController implements ActionListener {
	private int x,y;
	private CellView cellView;
	private Model model;
	
	public GameController(Model model, CellView view, int i, int j) {
		this.cellView = view; 
		this.model = model;
		this.x = i;
		this.y = j;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(model.getEtat() == Etat.PLAYER && model.isEnd_game() == false){
			if(cellView.isPlayer()){
				model.setShoot(x, y);
			}else{
				model.setShoot(x, y);
			}
		}
		
		if(model.isEnd_game()){
			//game.setVisible(false);
			//bs.setEndScreen();
		}
	}

}