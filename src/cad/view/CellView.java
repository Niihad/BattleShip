package cad.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;

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
		
		this.setFocusPainted(false);
		//this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(width,width)); 
		this.setBorder(BorderFactory.createMatteBorder( 0,0,1,1, Color.black));
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
	
	public void update(Observable arg0, Object arg1) {
		if(this.player){
			Cell cell = model.getBoardPlayer()[ord][abs];
			if(cell.getShip() != null){
				this.setBackground(Color.blue);
			}
		}else{ 
			Cell cell = model.getBoardAI()[ord][abs];
			if(cell != null){
				this.setBackground(Color.blue);
			}
		}
	}

}