package cad.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.controller.GameController;
import cad.model.Cell;
import cad.model.Model;

public class GameScreen extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private static final int CELL_WIDTH = 50;
	protected Model model;
	private CellView[][] boardPlayer, boardAI;
	private JPanel panelBoardPlayer, panelBoardAI;
	private BattleShip bs;

	public GameScreen(BattleShip battleShip) {
		this.bs = battleShip;
		this.model = bs.getModel();
		this.boardPlayer = new CellView[Model.getWidth()+1][Model.getHeight()+1];
		this.drawBoard(this.panelBoardPlayer, this.boardPlayer, 50,true);
		this.drawShipPlayer(model.getBoardPlayer());
		this.boardAI = new CellView[Model.getWidth()+1][Model.getHeight()+1];
		this.drawBoard(this.panelBoardAI, this.boardAI, 50, false);
		this.model.addObserver(this);
	}
	

	private JPanel drawJPanel(int x, int y, int w, int h){
		JPanel panel = new JPanel();
		panel.setBounds(x,y, w, h);
		this.add(panel);
		return panel;
	}
	
	public void paintComponent(Graphics g){
        try{
        	
	       Image img = ImageIO.read(new File("./assets/image/"+ model.getAge().getAgeName() +".jpg"));
           int height = this.getSize().height;
           int width = this.getSize().width;
           g.drawImage(img, 0, 0, width, height, this);

	    } catch(IOException e){
	        e.printStackTrace();
	    }
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
				board[i][j] = new CellView(bs,this,i,j,size, player);
				if(i == 0 && j == 0)
					board[i][j].setEnabled(false);
				if(j>10 && j<13){
					board[i][j].setBorder(null);
				}else if(i==0 && j>0 && j < size){
					board[i][j].add(new JLabel(""+j));
					board[i][j].setEnabled(false);
				}else if(i>0 && j==0){
					board[i][j].add(new JLabel(""+c));
					board[i][j].setEnabled(false);
					c++;
				}else if(i==0 && j>10){
					int m = j-size-2;
					board[i][j].add(new JLabel(""+m));
				}else if(i>0 && j==13){
					board[i][j].add(new JLabel(""+d));
					d++;
				}
				if(player)
					board[i][j].setEnabled(false);
				jp.add(board[i][j]);
			}
		}
	}
	
	private void drawShipPlayer(Cell[][] board){
		for(int i=0; i<board.length;i++){
			for(int j=0; j<board[i].length;j++){
				Cell cell = board[i][j];
				if(cell.getShip() != null){
					JLabel label = this.drawShipCut(cell.getShip().getPathImage(), cell.getShip().getLengthShip(), cell.getPart(), cell.getShip().isRotation());
					this.boardPlayer[i][j].add(label);
				}
			}
		}
	}
	
	public JLabel drawShipCut(String path, int length, int cut, boolean rotation){
		ImageIcon icon;
		if(!rotation) // horizontal
			 icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(CELL_WIDTH*length, CELL_WIDTH, Image.SCALE_DEFAULT));
		else
			icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(CELL_WIDTH, CELL_WIDTH*length, Image.SCALE_DEFAULT));
		Image img = icon.getImage();
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bimage.createGraphics();
	    g.drawImage(img, 0, 0, null);
	    g.dispose();
	    BufferedImage part;
	    if(!rotation) // horizontal
	    	part = bimage.getSubimage(cut*CELL_WIDTH, 0, CELL_WIDTH, CELL_WIDTH);
	    else
	    	part = bimage.getSubimage(0, cut*CELL_WIDTH, CELL_WIDTH, CELL_WIDTH);
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(part));
		return label;
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
	 * Met � jour le tableau du joueur suite au tir de l'IA
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
	 * Met � jour le tableau de l'IA au chargement d'une partie
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
		for(int x = 0; x <= model.getWidth(); x++) 
			for(int y = 0; y <= model.getHeight(); y++) 
				if(model.getBoardAI()[x][y].isShoot()) 
					boardAI[x][y].setEnabled(false);
	}
}
