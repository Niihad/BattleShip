package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import controller.GameController;
import main.BattleShip;
import model.Cell;
import model.Model;

public class CellView extends JButton implements Observer {
	
	private static final long serialVersionUID = 1L;
	private Model model;
	private int abs, ord, width;
	private boolean player;
	private ImageIcon icon;
	private BattleShip bs;
	private GameScreen gs;
	
	public CellView(BattleShip bs, GameScreen gameScreen, int x, int y, int width, boolean player){
		super();
		this.model= bs.getModel();
		this.bs = bs;
		this.gs = gameScreen;
		this.ord = x;
		this.abs = y;
		this.width = width;
		this.player = player;
		this.icon = new ImageIcon(new ImageIcon("assets/s1.png").getImage().getScaledInstance(width, width, Image.SCALE_DEFAULT));

		;
		/*this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setOpaque(false);*/
		this.setBackground(new Color(0,0,0,0));
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(width,width)); 
		this.setBorder(BorderFactory.createMatteBorder( 0,0,1,1, Color.black));

		this.addActionListener(new GameController(bs,gs,model,this,x,y));
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