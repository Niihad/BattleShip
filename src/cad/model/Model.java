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
	private Ship chooseShip, cloneShip;
	private int life,life_ia;
	private Context context;
	private boolean end_game = false;
	private Etat etat;
	public enum Etat {
		WAIT, PLAYER, IA
	}

	public Model() {
		this.boardPlayer = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardPlayer);
		this.boardAI = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardAI);
		//this.initialPlaceShip(this.boardAI);
		this.etat = Etat.PLAYER;
		//this.aleaPlace(this.boardAI);

		//this.print(boardPlayer);
		this.print(boardAI);
		this.etat = Etat.PLAYER;
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
		mettreAjour();
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
	
	public Cell getCell(int i, int j, boolean player){
		return (player) ? this.boardPlayer[i][j] : this.boardAI[i][j];
	}
	
	public Cell[][] getBoardConvert(int val){
		return (val > Model.WIDTH) ? this.boardAI : this.boardPlayer;
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
	
	/**
	 * Initialise les bateaux
	 * @param nomEpoque : nom de l'�poque choisie
	 * @param shipsEpoque : liste des bateaux pour une �poque choisie
	 */
	public void selectionEpoque(String nomEpoque, Ship[] shipsEpoque) {
		Ship[] ships = shipsEpoque;

		for(int i = 0; i < shipsEpoque.length; i++) 
			System.out.println(ships[i].getName());
		
		age = this.addAge(nomEpoque, ships);
		
		for (Ship ship : this.age.getShips())
			life += ship.getLife();
		life_ia =  life;
		
		this.initialPlaceShip(this.boardPlayer);
		this.etat = Etat.PLAYER;
		this.aleaPlace(this.boardAI);
		
		this.print(boardAI);
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

	public void print(Cell[][] board) {
		for (int i = 1; i < WIDTH + 1; i++) {
			System.out.println("---------------------");
			for (int j = 1; j < HEIGHT + 1; j++) {
				if (board[i][j].getShip() != null)
					System.out.print("|X");
				else
					System.out.print("| ");
			}
			System.out.print("|\n");
		}
		System.out.println("---------------------");
		System.out.println("----------------------------------------------------------------------------");
	}
	
	
	/***********************************************************/
	/********************** ShipPLaceView **********************/
	/***********************************************************/

	public synchronized void setShipCell(Cell[][] cells, int x, int y, Ship ship, int part) {
		cells[x][y].setShip(ship);
		cells[x][y].setPart(part);
		this.mettreAjour();
	}

	public synchronized void moveShip(int x, int y) {
		this.mettreAjour();
	}
	
	public void affectCloneShip(Cell cell){
		this.cloneShip = (Ship) cell.cloneShip();
	}
	
	public void movePlacementShipBoard(Cell cell, int val, int i, int part){
		boolean rotation = this.cloneShip.isRotation();
		int x = cell.getX();
		int y = cell.getY();
		if(!rotation)// horizontal
			y = y + i - part; 
		else // verticale
			x = x + i - part;
		this.getBoardConvert(val)[x][y].setShip(this.cloneShip);
		this.getBoardConvert(val)[x][y].setPart(i);
		this.mettreAjour();
	}
	
	public void replacePlacementShipBoard(Cell cell, int val, int i){
		int x = cell.getX();
		int y = cell.getY();
		this.getBoardConvert(val)[x][y].setShip(this.cloneShip);
		this.getBoardConvert(val)[x][y].setPart(i);
		this.mettreAjour();
	}
	
	public void rotationShipPlacement(Cell cell, int val, int part){
		int length = cell.getShip().getLengthShip();
		boolean rotation = cell.getShip().isRotation();
		int middle = part - length/2;
		for(int i=0; i<length; i++){
			if(!rotation){ // horizontal
				Cell origin = this.getBoardConvert(val)[cell.getX()][cell.getY()+i-part];
				Cell newCell = this.getBoardConvert(val)[cell.getX()+i-length/2][cell.getY()-middle];
				newCell.setShip(this.cloneShip);
				newCell.setPart(i);
				newCell.getShip().setRotation(!rotation);
				if(origin.getX() != newCell.getX() || origin.getY() != newCell.getY())
					origin.setShip(null);
			}else{
				Cell origin = this.getBoardConvert(val)[cell.getX()+i-part][cell.getY()];
				Cell newCell = this.getBoardConvert(val)[cell.getX()-middle][cell.getY()+i-length/2];
				newCell.setShip(this.cloneShip);
				newCell.setPart(i);
				newCell.getShip().setRotation(!rotation);
				if(origin.getX() != newCell.getX() || origin.getY() != newCell.getY())
					origin.setShip(null);
			}
		}
	}
	
	public boolean verificationPlacementShip(Cell cell, int val, int length, boolean rotation, int part){
		int x = cell.getX();
		int y = cell.getY();
		for(int i=0; i<length; i++){
			if(!rotation){ // horizontal
				if(y-part<1 || y+length-part>Model.WIDTH+1){ // permet d'eviter le deplacer du plateau
					return false;
				}else if(this.getBoardConvert(val)[x][y+i-part].getShip() != null){ // permet d'eviter les collision entre ship
					return false;
				}
			}else{ // vertical
				if(x-part<1 || x+length-part>Model.HEIGHT+1){
					return false;
				}else if(this.getBoardConvert(val)[x+i-part][y].getShip() != null){ 
					return false;	
				}
			}
		}
		return true;
	}
	
	public boolean verificationRotationShip(Cell cell, int val, int length, boolean rotation, int part){
		int x = cell.getX();
		int y = cell.getY();
		for(int i=0; i<length; i++){
			if(!rotation){ //rotation horizontal
				int tmpX  = x - part;
				int tmpY = y + i - length/2;
				if(tmpY<1 || tmpY>Model.HEIGHT){
					return false;
				}else if(this.getBoardConvert(val)[tmpX][tmpY].getShip() != null && tmpY != y){ 
					return false;	
				}
			}else{ // rotation vertical
				int tmpX  = x + i - length/2;
				int tmpY = y - part;
				if(tmpX<1 || tmpX>Model.HEIGHT){
					return false;
				}else if(this.getBoardConvert(val)[tmpX][tmpY].getShip() != null && tmpX != x){ 
					return false;	
				}
			}
		}
		return true;
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
		if(boardPlayer[x][y].getShip() != null){
			setLife();	
		}
		etat = Etat.PLAYER;
	}
	
	public void IA_play(){
		if(etat == Etat.IA){
			context.executeStrategy(this);
		}	
	}
}
