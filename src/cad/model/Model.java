package cad.model;

import java.awt.Point;
import java.util.Observable;
import java.util.Random;

public class Model extends Observable implements Runnable {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	private Age age;
	private Cell[][] boardPlayer, boardAI;
	private Point selectShipPLace = null;
	private Ship chooseShip;
	private int life,life_ia;
	private Context context;
	private boolean end_game = false;
	private Etat etat;
	public enum Etat {
		WAIT, PLAYER, IA
	}

	public Model() {
		Ship[] ships = new Ship[5];
		ships[0] = new Ship("porte-avion", "assets/s1.png", 5, 5);
		ships[1] = new Ship("croiseur", "assets/s1.png", 4, 4);
		ships[2] = new Ship("contre-torpilleur", "assets/s1.png", 3, 3);
		ships[3] = new Ship("sous-marins", "assets/s1.png", 3, 3);
		ships[4] = new Ship("torpilleur", "assets/s1.png", 2, 2);
		age = this.addAge("Moderne", ships);
		
		for (Ship ship : this.age.getShips())
			life += ship.getLife();
			life_ia =  life;
		
		this.boardPlayer = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardPlayer);
		this.boardAI = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardAI);
		this.initialPlaceShip(this.boardPlayer);
		this.etat = Etat.PLAYER;
		this.aleaPlace(this.boardAI);

		this.print();
	}
	
	/***********************************************************/
	/********************** Initiale Game **********************/
	/***********************************************************/
	
	private Age addAge(String name, Ship[] ships){
		Age age = new Age(name);
		for(int i=0; i<ships.length; i++)	
			age.addShip(ships[i]);
		return age;
	}
	
	private void buildBoards(Cell[][] board){
		for(int i=0; i<=WIDTH; i++){
			for(int j=0; j<=HEIGHT; j++){
				board[i][j] = new Cell(i, j);
			}
		}
	}
	
	private void initialPlaceShip(Cell[][] board){
		int position[][] = { { 1, 1 }, { 4, 4 }, { 6, 6 }, { 8, 1 }, { 10, 9 } };
		int i = 0;
		for (Ship ship : this.age.getShips()) {
			for (int j = 0; j < ship.getLengthShip(); j++) {
				this.setShipCell(board, position[i][0], position[i][1] + j, ship, j);
			}
			i++;
		}
	}

	private void aleaPlace(Cell[][] boardAi) {
		Random r = new Random();
		int x, y;
		for (Ship ship : this.age.getShips()) {
			// 0 - Verticale -- 1 - Horizonale
			int nb = (int) (Math.random() * 2);

			if (nb == 0) {// Vert
				do {
					x = 1 + r.nextInt(WIDTH - 1);
					do {// depassement du tableau vers le bas
						y = 1 + r.nextInt(HEIGHT - 1);
					} while (ship.getLengthShip() + y > HEIGHT + 1);
				} while (test_collision(x, y, ship.getLengthShip(), boardAi, true) == true);
				for (int j = 0; j < ship.getLengthShip(); j++) {
					this.setShipCell(boardAi, x, y+j, ship,j);
				}
			} else {// Horizontale
				do {
					y = 1 + r.nextInt(HEIGHT - 1);
					do {// depassement du tableau vers la droite
						x = 1 + r.nextInt(WIDTH - 1);
					} while (ship.getLengthShip() + x > WIDTH + 1);
				} while (test_collision(x, y, ship.getLengthShip(), boardAi, false) == true);
				for (int j = 0; j < ship.getLengthShip(); j++) {
					this.setShipCell(boardAi, x+j, y, ship, j);
				}
			}
		}
	}

	//permet de test si on peux placer le bateau a cette position
	//test si deux bateaux se croisent
	private boolean test_collision(int x, int y, int size, Cell[][] boardAi2, boolean vert) {
		if (vert) {
			for (int i = 0; i < size; i++) {
				if (boardAI[x][y + i].getShip() != null)
					return true;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (boardAI[x + i][y].getShip() != null)
					return true;
			}
		}
		return false;
	}

	private void print() {
		for (int i = 1; i < WIDTH + 1; i++) {
			System.out.println("---------------------");
			for (int j = 1; j < HEIGHT + 1; j++) {
				if (this.boardAI[i][j].getShip() != null)
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

	public Cell[][] getBoardAI() {
		return boardAI;
	}

	public void setBoardAI(Cell[][] boardAi) {
		this.boardAI = boardAi;
	}

	public int getLife() {
		return life;
	}

	public void setLife() {
		if(life >= 1)
			this.life--;
		this.mettreAjour();	
	}

	public int getLife_ia() {
		return life_ia;
	}

	public void setLife_ia() {
		if(life_ia >= 1)
			this.life_ia--;
		this.mettreAjour();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public boolean isEnd_game() {
		return end_game;
	}

	public void setEnd_game(boolean end_game) {
		this.end_game = end_game;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	
	/***********************************************************/
	/********************** ShipPLcaeView **********************/
	/***********************************************************/

	public synchronized void setShipCell(Cell[][] cells, int x, int y, Ship ship, int part) {
		cells[x][y].setShip(ship);
		cells[x][y].setPart(part);
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

	//permet de test si l'ia a deja tirer sur cette case
	public boolean neverShoot(int x, int y) {
		return boardPlayer[x][y].isShoot();
	}

	public void setShoot(int x, int y) {
		boardPlayer[x][y].setShoot(true);
		System.out.println("ia tire en" + x + "-- " + y +"\n") ;
		if(boardPlayer[x][y].getShip() != null){
			setLife();	
			System.out.println("collision");
		}
		etat = Etat.PLAYER;
	}
	
	public void IA_play(){
		if(etat == Etat.IA){
			context.executeStrategy(this);
		}	
	}
}
