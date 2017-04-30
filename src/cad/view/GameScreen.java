package cad.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import cad.controller.GameController;
import cad.model.Model;

public class GameScreen extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	protected Model model;
	private JButton[][] boardPlayer, boardAI;
	private JPanel panelBoardPlayer, panelBoardAI;

	public GameScreen(Model model) {
		this.model = model;
		this.boardPlayer = new JButton[11][11];
		this.drawBoard(this.panelBoardPlayer, this.boardPlayer, 50, 50, true);
		this.boardAI = new JButton[11][11];
		this.drawBoard(this.panelBoardAI, this.boardAI, 50, 50, false);
		this.model.addObserver(this);
	}
	
	private JPanel drawJPanel(int x, int y, int w, int h){
		JPanel panel = new JPanel();
		panel.setBounds(x,y, w, h);
		this.add(panel);
		return panel;
	}
	
	
	/***********************************************************/
	/************************** Board **************************/
	/***********************************************************/
	
	// build board game 
	private void drawBoard(JPanel jp, JButton[][] board, int x, int y, boolean player){
		jp = this.drawJPanel(x, y, 450, 450);
		char c = 'A';
		jp.setLayout(new GridLayout(10 + 1,10 + 1,1,1));
		for(int i = 0;i <= 10;i++){
			for(int j = 0;j <= 10;j++){
				if(i == 0){
					if(j != 0)
						board[i][j] = new JButton("" + j);
					else	
						board[i][j] = new JButton();
					board[i][j].setEnabled(false);
				}else{
					if(j == 0){
						board[i][j] = new JButton("" + c);
						c += 1;						
						board[i][j].setEnabled(false);
					}else{
						board[i][j] = new JButton();
						board[i][j].setEnabled(!player);
					}
				}
				board[i][j].setPreferredSize(new Dimension(50,50));
				if(player && i < 11 && j < 11 && i > 0 && j > 0 && model.getBoardPlayer()[i][j].getShip() != null)
					board[i][j].setBackground(Color.blue.darker());
				
				if(!player && i < 11 && j < 11 && i > 0 && j > 0){
					boolean collision = false;
					if(model.getBoardAI()[i][j].getShip() != null)
						collision = true;
					boardAI[i][j].addMouseListener(new GameController(this,collision,i,j,model));
				}				
				jp.add(board[i][j]);
			}
		}
	}
	
	//en rouge si on touche pas et en vert si on touche
	public void setCouleur(int x,int y,boolean collision){
		this.boardAI[x][y].setEnabled(false);
		if(collision)
			boardAI[x][y].setBackground(Color.green.darker());
		else
			boardAI[x][y].setBackground(Color.red.darker());
	}


	@Override
	public void update(Observable arg0, Object arg1) {
	}
}
