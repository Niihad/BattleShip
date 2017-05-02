package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cad.BattleShip;
import cad.model.Model;
import cad.model.Model.Etat;
import cad.view.GameScreen;

public class GameController implements ActionListener {
	private int x,y;
	private GameScreen game;
	private Model model;
	private BattleShip bs;
	
	public GameController(BattleShip bs, GameScreen gameScreen, int i, int j) {
		this.game = gameScreen; 
		this.bs = bs;
		this.model = bs.getModel();
		this.x = i;
		this.y = j;
	}

	public void actionPerformed(ActionEvent arg0) {
		if(model.getEtat() == Etat.PLAYER && model.isEnd_game() == false){
			if(model.getBoardAI()[x][y].getShip() != null){
				this.model.setLife_ia();
				this.game.setCouleur(x,y,true);
			}else{
				this.game.setCouleur(x,y,false);
			}
			this.model.setEtat(Etat.IA);
			this.model.IA_play();
			this.game.updateBoardPlayer();
		}	
		
		if(model.isEnd_game()){
			game.setVisible(false);
			bs.setEndScreen();
		}
	}

}