package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import cad.model.Model;
import cad.view.GameScreen;

public class GameController implements ActionListener {
	private boolean collision;
	private int x,y;
	private GameScreen game;
	private Model model;
	
	public GameController(GameScreen gameScreen, boolean collision, int i, int j, Model model) {
		this.game = gameScreen; 
		this.model = model;
		this.collision = collision;
		this.x = i;
		this.y = j;
	}

	public void mouseClicked(MouseEvent arg0) {
		/*if(model.getEtat() == Etat.PLAYER){
			if(collision){
				this.model.setLife_ia();
				this.game.setCouleur(x,y,true);
			}else{
				this.game.setCouleur(x,y,false);
			}
			this.model.setEtat(Etat.IA);
			this.model.IA_play();
			this.game.updateBoardPlayer();
		}*/
	}

	public void actionPerformed(ActionEvent arg0) {
		/*if(model.getEtat() == Etat.PLAYER){
			if(collision){
				this.model.setLife_ia();
				this.game.setCouleur(x,y,true);
			}else{
				this.game.setCouleur(x,y,false);
			}
			this.model.setEtat(Etat.IA);
			this.model.IA_play();
		}	*/	
	}

}