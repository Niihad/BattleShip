package cad.model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

public class Model extends Observable implements Runnable {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	private HashMap<Integer, Age> ages;
	private int keyage = 0;

	private Cell[][] boardPlayer, boardAi;
	private Point selectShipPLace = null;
	private Ship chooseShip;
	private Age age;
	private int life,life_ia;

	public Model() {
		//a virer de la
		//code en dure pour les test
		age = new Age("Moderne");
		age.addShip(new Ship("porte-avion", "assets/s1.png", 5, 5));
		age.addShip(new Ship("croiseur", "assets/s1.png", 4, 4));
		age.addShip(new Ship("contre-torpilleur", "assets/s1.png", 3, 3));
		age.addShip(new Ship("sous-marins", "assets/s1.png", 3, 3));
		age.addShip(new Ship("torpilleur", "assets/s1.png", 2, 2));
		//doit etre calculer aussi par rapport aux epoques
		life = 5 + 4 + 3 + 3 + 2;
		life_ia =  5 + 4 + 3 + 3 + 2;
		
		this.boardPlayer = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardPlayer);
		this.boardAi = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardAi);
		this.aleaPlace(this.boardAi);
		this.exemplePlace(this.boardPlayer);
	}
	
	private void buildBoards(Cell[][] board){
		
		for(int i=0; i<=WIDTH; i++){
			for(int j=0; j<=HEIGHT; j++){
				board[i][j] = new Cell(i, j, null);
			}
		}
	}

	private void aleaPlace(Cell[][] boardAi) {

		Random r = new Random();
		int x, y, i = 0;

		for (Ship ship : this.age.getShips()) {
			// 0 - Verticale -- 1 - Horizonale
			int nb = (int) (Math.random() * 2);

			if (nb == 0) {// Vert
				do {
					x = 1 + r.nextInt(10 - 1);
					do {// depassement du tableau vers le bas
						y = 1 + r.nextInt(10 - 1);
					} while (ship.getLengthShip() + y > 11);
				} while (test_collision(x, y, ship.getLengthShip(), boardAi, true) == true);
				for (int j = 0; j < ship.getLengthShip(); j++) {
					this.setShipCell(boardAi, x, y + j, ship);
				}
			} else {// Horizontale
				do {
					y = 1 + r.nextInt(10 - 1);
					do {// depassement du tableau vers la droite
						x = 1 + r.nextInt(10 - 1);
					} while (ship.getLengthShip() + x > 11);
				} while (test_collision(x, y, ship.getLengthShip(), boardAi, false) == true);
				for (int j = 0; j < ship.getLengthShip(); j++) {
					this.setShipCell(boardAi, x + j, y, ship);
				}
			}
			i++;

		}

	}

	private boolean test_collision(int x, int y, int size, Cell[][] boardAi2, boolean vert) {
		if (vert) {
			for (int i = 0; i < size; i++) {
				if (boardAi[x][y + i].getShip() != null)
					return true;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (boardAi[x + i][y].getShip() != null)
					return true;
			}
		}
		return false;
	}

	private void exemplePlace(Cell[][] board){
		//int position[][] = {{1,1}, {3,1}, {3,5}, {5,1}, {5,4}, {5,7}, {7,1}, {7,3}, {7,5}, {7,7}};
		int position[][] = { { 1, 1 }, { 4, 4 }, { 6, 6 }, { 8, 1 }, { 10, 9 } };
		int i = 0;
		for (Ship ship : this.age.getShips()) {
			for (int j = 0; j < ship.getLengthShip(); j++) {
				this.setShipCell(board, position[i][0], position[i][1] + j, ship);
			}
			i++;
		}
	}

	private void print() {
		for (int i = 1; i < WIDTH + 1; i++) {
			System.out.println("---------------------");
			for (int j = 1; j < HEIGHT + 1; j++) {
				if (this.boardAi[i][j].getShip() != null)
					System.out.print("|X");
				else
					System.out.print("| ");
			}
			System.out.print("|\n");
		}
		System.out.println("---------------------");
	}

	/***********************************************************/
	/********************* GETTER / SETTER *********************/
	/***********************************************************/
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
	
	public HashMap<Integer, Age> getAges() {
		return ages;
	}

	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	public Point getSelectShipPLace() {
		return selectShipPLace;
	}

	public void setSelectShipPLace(Point selectShipPLace) {
		this.selectShipPLace = selectShipPLace;
	}

	public Ship getChooseShip() {
		return chooseShip;
	}

	public void setChooseAge(Ship ship) {
		this.chooseShip = ship;
	}

	public Cell[][] getBoardPlayer() {
		return boardPlayer;
	}

	public void setBoardPlayer(Cell[][] boardPlayer) {
		this.boardPlayer = boardPlayer;
	}

	public Cell[][] getBoardAi() {
		return boardAi;
	}

	public void setBoardAi(Cell[][] boardAi) {
		this.boardAi = boardAi;
	}

	public int getLife() {
		return life;
	}

	public void setLife() {
		this.life--;
		setChanged();
		notifyObservers(this);	
	}

	public int getLife_ia() {
		return life_ia;
	}

	public void setLife_ia() {
		this.life_ia--;
		setChanged();
		notifyObservers(this);	
	}

	/***********************************************************/
	/********************** ShipPLcaeView **********************/
	/***********************************************************/

	public synchronized void setShipCell(Cell[][] cells, int x, int y, Ship ship) {
		cells[x][y].setShip(ship);
		this.mettreAjour();
	}

	public synchronized void moveShip(int x, int y) {
		this.mettreAjour();
	}

	/***********************************************************/
	/************************ GameScreen ***********************/
	/***********************************************************/

	@Override
	public void run() {
		this.mettreAjour();
	}

	public void mettreAjour() {
		setChanged();
		notifyObservers();
	}

}
