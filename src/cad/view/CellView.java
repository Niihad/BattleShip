package cad.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import cad.controller.GameController;
import cad.model.Cell;
import cad.model.Model;

public class CellView extends JButton implements Observer{
	
	private static final long serialVersionUID = 1L;
	private Model model;
	private int abs, ord, width;
	private boolean player;
	
	public CellView(Model model, int x, int y, int width, boolean player){
		super();
		this.model=model;
		this.ord = x;
		this.abs = y;
		this.width = width;
		this.player = player;
		
		/*this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setOpaque(false);*/
		
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(width,width)); 
		this.setBorder(BorderFactory.createMatteBorder( 0,0,1,1, Color.black));

		this.addActionListener(new GameController(model,this,x,y));
		this.model.addObserver(this);
	}

	public int getAbs() {
		return abs;
	}

	public void setAbs(int abs) {
		this.abs = abs;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}
	
	public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean player) {
		this.player = player;
	}

	public void update(Observable arg0, Object arg1) {
		ImageIcon icon = new ImageIcon(new ImageIcon("assets/s1.png").getImage().getScaledInstance(width, width, Image.SCALE_DEFAULT));
		Cell cell;
		if(this.player)
			cell = model.getBoardPlayer()[ord][abs];
		else
			cell = model.getBoardAI()[ord][abs];

		if(cell.getShip() != null && cell.isShoot()){
			this.setBackground(Color.red); // bateau toucher
		}else if(cell.isShoot()){
			this.setBackground(Color.black);
		}
			
	}

}