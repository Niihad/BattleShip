package cad.view;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.controller.ShipBrdPanelListener;
import cad.model.Cell;
import cad.model.Model;

public class PlacementScreen extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private static final int CELL_WIDTH = 50;
	private BattleShip battleShip;
	private Model model;
	private JPanel[][] board;
	private JPanel panelBoard;

	public PlacementScreen(BattleShip bs){
		super();
		this.battleShip = bs;
		this.model = battleShip.getModel();
		
		this.board = new JPanel[Model.getHeight()+1][Model.getWidth()*2+4];
		this.panelBoard = new JPanel();
		this.drawBoard(this.panelBoard, this.board, this.model.getBoardPlayer().length);
		this.add(this.panelBoard);

		ShipBrdPanelListener shipListener = new ShipBrdPanelListener(this.model, this);
		this.panelBoard.addMouseListener(shipListener);
		this.panelBoard.addMouseMotionListener(shipListener);
		this.drawShipsBoard(model.getBoardAI());
		
		JButton play = new JButton("PLAY");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.print(model.getBoardAI());
				if(model.verificationBeginGame()){
					model.aleaPlace(model.getBoardAI());
					setVisible(false);
					battleShip.setGameScreen();
				}else{
					javax.swing.JOptionPane.showMessageDialog(null,"Tous les bateaux ne sont pas placé"); 
				}
			}
		});
		this.add(play);
		this.model.addObserver(this);
	}
	
	public JPanel[][] getBoard() {
		return board;
	}

	public void setBoard(JPanel[][] board) {
		this.board = board;
	}


	/***********************************************************/
	/************************** Board **************************/
	/***********************************************************/
	
	// build board game 
	private void drawBoard(JPanel jp, JPanel[][] board, int size){
		char c = 'A', d = 'A';
		int length = size*2+2;
		jp.setLayout(new GridLayout(size, length,0,0));
		for(int i=0; i<size; i++){
			for(int j=0; j<length; j++){
				if(i==0 && j==0){
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
				}else if(i==0 && j==13){
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
				}else if(j>10 && j<13){
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
					board[i][j].setBorder(null);
				}else if(i==0 && j>0 && j < size){
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
					board[i][j].add(new JLabel(""+j));
				}else if(i>0 && j==0){
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
					board[i][j].add(new JLabel(""+c));
					c++;
				}else if(i==0 && j>10){
					int m = j-size-2;
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
					board[i][j].add(new JLabel(""+m));
				}else if(i>0 && j==13){
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
					board[i][j].add(new JLabel(""+d));
					d++;
				}else if(j < size){
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
				}else {
					board[i][j] = new CellView(this.model,i,j,CELL_WIDTH);
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
					this.drawShipCut(cell.getShip().getPathImage(), cell.getShip().getLengthShip(), cell.getPart(),i,j+13);
				}
			}
		}
	}
	
	public void drawShipCut(String path, int length, int cut, int i, int j){
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
		
	public void update(Observable arg0, Object arg1) {
	}
	
}