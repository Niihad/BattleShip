package cad.view;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cad.controller.ShipBrdPanelListener;
import cad.model.Cell;
import cad.model.Model;

public class PlacementScreen extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private static final int CELL_WIDTH = 50;
	private Model model;
	private JPanel[][] board;
	private JPanel panelBoard;
	private JLabel shipLabel = new JLabel();
	//private boolean vertical = false;

	public PlacementScreen(Model modele){
		super();
		this.model = modele;
		ShipBrdPanelListener shipListener = new ShipBrdPanelListener(this);
		
		this.board = new JPanel[Model.getWidth()+1][Model.getHeight()*2+4];
		this.panelBoard = new JPanel();
		this.drawBoard(this.panelBoard, this.board, this.model.getBoardPlayer(), this.model.getBoardAI());
		this.add(this.panelBoard);
		this.panelBoard.addMouseListener(shipListener);
		this.panelBoard.addMouseMotionListener(shipListener);
		this.drawShipsBoard(model.getBoardAI());

		this.model.addObserver(this);
	}
	
	/***********************************************************/
	/************************** Board **************************/
	/***********************************************************/
	
	// build board game 
	private void drawBoard(JPanel jp, JPanel[][] board, Cell[][] cellsPlayer, Cell[][] cellsAI){
		char c = 'A', d = 'A';
		int length = cellsPlayer.length*2+2;
		jp.setLayout(new GridLayout(cellsPlayer.length, length,0,0));
		for(int i=0; i<cellsPlayer.length; i++){
			for(int j=0; j<length; j++){
				if(i==0 && j==0){
					board[i][j] = new CellView(this.model,cellsPlayer[i][j],CELL_WIDTH);
				}else if(i==0 && j==13){
					board[i][j] = new CellView(this.model,cellsAI[i][j-cellsAI.length-2],CELL_WIDTH);
				}else if(j>10 && j<13){
					board[i][j] = new CellView(this.model, i, j, CELL_WIDTH);
				}else if(i==0 && j>0 && j < cellsPlayer.length){
					board[i][j] = new CellView(this.model,cellsPlayer[i][j],CELL_WIDTH);
					board[i][j].add(new JLabel(""+j));
				}else if(i>0 && j==0){
					board[i][j] = new CellView(this.model,cellsPlayer[i][j],CELL_WIDTH);
					board[i][j].add(new JLabel(""+c));
					c++;
				}else if(i==0 && j>10){
					int m = j-cellsAI.length-2;
					board[i][j] = new CellView(this.model,cellsAI[i][m],CELL_WIDTH);
					board[i][j].add(new JLabel(""+m));
				}else if(i>0 && j==13){
					board[i][j] = new CellView(this.model,cellsAI[i][j-cellsAI.length-2],CELL_WIDTH);
					board[i][j].add(new JLabel(""+d));
					d++;
				}else if(j < cellsPlayer.length){
					board[i][j] = new CellView(this.model,cellsPlayer[i][j],CELL_WIDTH);
				}else {
					board[i][j] = new CellView(this.model,cellsAI[i][j-cellsAI.length-2],CELL_WIDTH);
				}
				jp.add(board[i][j]);
			}
		}
	}
	
	
	/***********************************************************/
	/************************** Ships **************************/
	/***********************************************************/
	
	private void drawShipsBoard(Cell[][] board){
		for(int i=0; i<board.length;i++){
			for(int j=0; j<board[i].length;j++){
				Cell cell = board[i][j];
				if(cell.getShip() != null){
					this.drawShipCut(cell.getShip().getPathImage(), cell.getShip().getLengthShip(), cell.getPart(),i,j);
				}
			}
		}
	}
	
	private void drawShipCut(String path, int length, int cut, int i, int j){
		ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(CELL_WIDTH*length, CELL_WIDTH, Image.SCALE_DEFAULT));
		Image img = icon.getImage();
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bimage.createGraphics();
	    g.drawImage(img, 0, 0, null);
	    g.dispose();
		BufferedImage part = bimage.getSubimage(cut*CELL_WIDTH, 0, CELL_WIDTH, CELL_WIDTH);
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(part));
		this.board[i][j].add(label);
	}
		
	@Override
	public void update(Observable arg0, Object arg1) {
	}
	
}