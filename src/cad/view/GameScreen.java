package cad.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.model.Cell;
import cad.model.Model;

public class GameScreen extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private static final int CELL_WIDTH = 50;
	protected Model model;
	private JButton[][] boardPlayer, boardAI;
	private JPanel panelBoardPlayer, panelBoardAI;
	private BattleShip bs;

	public GameScreen(BattleShip battleShip) {
		this.bs = battleShip;
		this.model = bs.getModel();
		this.boardPlayer = new JButton[Model.getWidth()+1][Model.getHeight()+1];
		this.drawBoard(this.panelBoardPlayer, this.boardPlayer, 50,true);
		this.drawShipPlayer(model.getBoardPlayer());
		this.boardAI = new JButton[Model.getWidth()+1][Model.getHeight()+1];
		this.drawBoard(this.panelBoardAI, this.boardAI, 50, false);
		this.model.addObserver(this);
	}
	
	
	/***********************************************************/
	/************************** Board **************************/
	/***********************************************************/
	
	// build board game 
	private void drawBoard(JPanel jp, JButton[][] board, int size, boolean player){
		jp = new JPanel();
		this.add(jp);
		char c = 'A', d = 'A';
		jp.setLayout(new GridLayout(Model.getWidth()+1,Model.getHeight()+1,0,0));
		for(int i=0; i<Model.getWidth()+1; i++){
			for(int j=0; j<Model.getHeight()+1; j++){
				board[i][j] = new CellView(this.model,i,j,size, player);
				if(j>10 && j<13){
					board[i][j].setBorder(null);
				}else if(i==0 && j>0 && j < size){
					board[i][j].add(new JLabel(""+j));
				}else if(i>0 && j==0){
					board[i][j].add(new JLabel(""+c));
					c++;
				}else if(i==0 && j>10){
					int m = j-size-2;
					board[i][j].add(new JLabel(""+m));
				}else if(i>0 && j==13){
					board[i][j].add(new JLabel(""+d));
					d++;
				}
				jp.add(board[i][j]);
			}
		}
	}
	
	private void drawShipPlayer(Cell[][] board){
		for(int i=0; i<board.length;i++){
			for(int j=0; j<board[i].length;j++){
				Cell cell = board[i][j];
				if(cell.getShip() != null){
					this.drawShipCut(cell.getShip().getPathImage(), cell.getShip().getLengthShip(), cell.getPart(),i,j);
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
		this.boardPlayer[i][j].add(label);
	}
	
	//en rouge si on touche pas et en vert si on touche
	public void setCouleur(int x,int y,boolean collision){
		this.boardAI[x][y].setEnabled(false);
		if(collision)
			boardAI[x][y].setBackground(Color.green.darker());
		else
			boardAI[x][y].setBackground(Color.red.darker());
	}
	
	/**
	 * Met à jour le tableau du joueur suite au tir de l'IA
	 * si l'IA ne touche pas de bateau
	 * 		alors met la case en noir 
	 * 		sinon met la case en rouge
	 */
	public void updateBoardPlayer(){
		int w = Model.getWidth(), h = Model.getHeight();
		for(int x = 0; x < w; x++) 
			for(int y = 0; y < h; y++) {
				if(model.getBoardPlayer()[x][y].isShoot()) {
					if(model.getBoardPlayer()[x][y].getShip() == null)
						boardPlayer[x][y].setBackground(Color.black.darker());
					else
						boardPlayer[x][y].setBackground(Color.red.darker());
				}
			}
	}
	
	/**
	 * Met à jour le tableau de l'IA au chargement d'une partie
	 */
	public void updateBoardAI() {
		int w = 11, h = 11;
		for(int x = 0; x < w; x++) 
			for(int y = 0; y < h; y++) 
				if(model.getBoardAI()[x][y].isShoot()) 
					setCouleur(x,y,true);
	}
	
	
	public void resetBoardIA(){
		int w = 11, h = 11;
		for(int x = 0; x < w; x++) 
			for(int y = 0; y < h; y++) {
				if(model.getBoardPlayer()[x][y].isShoot()) {
					if(model.getBoardPlayer()[x][y].getShip() == null)
						boardPlayer[x][y].setBackground(Color.black.darker());
					else
						boardPlayer[x][y].setBackground(Color.red.darker());
				}
			}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
	}
}
