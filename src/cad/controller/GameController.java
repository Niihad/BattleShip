package cad.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import cad.model.Model;
import cad.view.GameScreen;

public class GameController implements MouseListener {
	private boolean collision;
	private int x,y;
	private GameScreen game;
	
	public GameController(GameScreen gameScreen, boolean collision, int i, int j) {
		this.game = gameScreen; 
		this.collision = collision;
		this.x = i;
		this.y = j;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(collision)
			this.game.setCouleur(x,y,true);
		else
			this.game.setCouleur(x,y,false);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
