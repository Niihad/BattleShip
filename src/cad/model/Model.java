package cad.model;

import java.awt.Point;
import java.util.Observable;
import java.util.Random;

public class Model extends Observable implements Runnable {

	public static int WIDTH = 10;
	public static int HEIGHT = 10;

	private Cell[][] boardPlayer, boardAi;
	private Point selectShipPLace = null;
	private Ship chooseShip;
	private Age age;

	public Model() {
		age = new Age("Moderne");
		age.addShip(new Ship("porte-avion", "assets/s1.png", 5, 5));
		age.addShip(new Ship("croiseur", "assets/s1.png", 4, 4));
		age.addShip(new Ship("contre-torpilleur", "assets/s1.png", 3, 3));
		age.addShip(new Ship("sous-marins", "assets/s1.png", 3, 3));
		age.addShip(new Ship("torpilleur", "assets/s1.png", 2, 2));

		this.boardPlayer = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardPlayer);
		this.boardAi = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardAi);

		 this.exemplePlace(this.boardPlayer);
		this.aleaPlace(this.boardAi);
		// this.print();
	}

	private void aleaPlace(Cell[][] boardAi) {
		
		Random r = new Random();
		int x, y, i = 0;
		
		for (Ship ship : this.age.getShips()) {
			// 0 - Horizonale -- 1 - Verticale
			int nb = (int) (Math.random() * 2);
			
			if (nb == 0) {//hori
				x = 1 + r.nextInt(10 - 1);
				do {
					y = 1 + r.nextInt(10 - 1);
				} while (ship.getLengthShip() + y > 11);

				for (int j = 0; j < ship.getLengthShip(); j++) {
					this.setShipCell(boardAi, x, y + j, ship);
				}
			}else{//vert
				y = 1 + r.nextInt(10 - 1);
				do {
					x = 1 + r.nextInt(10 - 1);
				} while (ship.getLengthShip() + x > 11);

				for (int j = 0; j < ship.getLengthShip(); j++) {
					this.setShipCell(boardAi, x + j, y, ship);
				}
			}
			i++;

		}

	}

	private void buildBoards(Cell[][] board) {
		for (int i = 1; i < WIDTH + 1; i++) {
			for (int j = 1; j < HEIGHT + 1; j++) {
				board[i][j] = new Cell(i, j, null);
			}
		}
	}

	private void exemplePlace(Cell[][] board) {
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
				if (this.boardPlayer[i][j].getShip() != null)
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
