package cad.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cad.model.Cell;
import cad.model.Model;

public class CellView extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	protected Model model;
	protected Cell cell;
	protected int abs, ord, width;
	
	public CellView(Model model, int x, int y, int width){
		super();
		this.model=model;
		this.abs = y;
		this.ord = x;
		this.width = width;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(width,width)); 
		this.model.addObserver(this);
	}
	
	public CellView(Model model, int x, int y, Cell cell, int width){
		super();
		this.model=model;
		this.cell = cell;
		this.abs = y;
		this.ord = x;
		this.width = width;
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

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	}

}