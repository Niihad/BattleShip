package cad.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cad.model.Cell;
import cad.model.Model;

public class CellView extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	protected Model model;
	protected int abs, ord, width;
	
	public CellView(Model model, int x, int y, int width){
		super();
		this.model=model;
		this.ord = x;
		this.abs = y;
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
	
	public int getAbsConvert(){
		return (this.abs>Model.getWidth()) ? this.abs-Model.getWidth()-3 : this.abs;
	}

	public void update(Observable arg0, Object arg1) {
	}

}