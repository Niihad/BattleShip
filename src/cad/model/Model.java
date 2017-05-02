package cad.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Model extends Observable implements Runnable {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	private Age age;
	private Cell[][] boardPlayer, boardAI;
	private String[] epoqueName;
	private Ship cloneShip;
	private int life,life_ia;
	private Context context;
	private boolean end_game = false;
	private Etat etat;
	private ArrayList<Context> strategie;
	protected String pseudo;


	public enum Etat {
		WAIT, PLAYER, IA
	}

	public Model() {
		this.boardPlayer = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardPlayer);
		this.etat = Etat.WAIT;
		this.epoqueName = this.chargementNomEpoque();
		this.strategie = new ArrayList<Context>();
		// Initialisation de l'�poque
		this.selectionEpoque(this.chargementNomEpoque()[0], this.chargementEpoque(0, "epoques"));
		creationStrategie();

	}
	
	private void creationStrategie() {
		Context context = new Context(new Aleatoire());
		Context context2 = new Context(new Diagonale());
		Context context3 = new Context(new Intelligent());
		strategie.add(context);
		strategie.add(context2);
		strategie.add(context3);
	}
	
	/***********************************************************/
	/*********************** SAUVEGARDE ************************/
	/***********************************************************/
	/**
	 * Sauvegarde du profil grace à l'implémentation de l'interface Serializable des différents objects concernés
	 * Stock l'objet dans un fichier dont le nom est crée à partir du pseudo
	 */
	public void saveProfile() {
        String file = "./profils/" + pseudo + ".save";

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
	
	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
		
	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
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

	public String[] getEpoqueName() {
		return epoqueName;
	}

	public void setEpoqueName(String[] epoque) {
		this.epoqueName = epoque;
	}

	public int getLife() {
		return life;
	}

	public void setLife() {
		if(life >= 1)
			this.life--;
		if(life == 0)
			this.end_game = true;
		this.mettreAjour();	
	}

	public int getLife_ia() {
		return life_ia;
	}

	public void setLife_ia() {
		if(life_ia >= 1)
			this.life_ia--;
		if(life_ia == 0)
			this.end_game = true;
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
	
	private void buildBoards(Cell[][] board){
		for(int i=0; i<=WIDTH; i++){
			for(int j=0; j<=HEIGHT; j++){
				board[i][j] = new Cell(i, j);
			}
		}
	}
	
	
	/***********************************************************/
	/*********************** Config Game ***********************/
	/***********************************************************/
	
	/**
	 * Chargement du nom de chacune des epoques disponibles depuis le fichier XML/epoques.xml
	 * @return on retourne les noms des epoques dans un tableau
	 */
	public String[] chargementNomEpoque() {
	    int k = 0;
	    String[] nomEpoques;
	    String[] nomEpoquesTemporaires = new String[]{"Aucune Epoque"};;
		
         // Etape 1 : recuperation d'une instance de la classe "DocumentBuilderFactory"
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	
        try {
             // Etape 2 : creation d'un parseur
            DocumentBuilder builder = factory.newDocumentBuilder();
			// Etape 3 : creation d'un Document
		    Document document = builder.parse(new File("XML/epoques.xml"));
		    // Etape 4 : recuperation de l'Element racine
		    Element epoques = document.getDocumentElement();
		    // Etape 5 : recuperation de tous les noeuds
		    NodeList noeuds = epoques.getChildNodes();
		    nomEpoquesTemporaires = new String[noeuds.getLength()];
		    
		    for (int i = 0; i < noeuds.getLength(); i++) {
		    	if(noeuds.item(i).getNodeType() == Node.ELEMENT_NODE && noeuds.item(i).getNodeName().equals("epoque")) {
			    	Element epoque = (Element) noeuds.item(i);
			    	nomEpoquesTemporaires[Integer.parseInt(epoque.getAttribute("id"))] = epoque.getAttribute("nom");
			    	k++;
		    	}
		    }
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        
        // On rassemble les noms d'epoque
        if(k != 0) {
    		nomEpoques = new String[k];
            for(int i = 0; i < k; i++)
            	nomEpoques[i] = nomEpoquesTemporaires[i];
        }
        else 
    		nomEpoques = nomEpoquesTemporaires;
        	
			
		return nomEpoques;
	}
	
	
	/**
	 * Chargement d'une �poque constitu�e de plusieurs bateaux
	 * @param numEpoque : num�ro de l'�poque
	 * @param nomFichier : nom du fichier a t�l�charger
	 * @return on retourne le nom des attributs avec leurs valeurs
	 */
	public Ship[] chargementEpoque(int numEpoque, String nomFichier) {
	    Ship[] shipsForModel = new Ship[5];
	    int n = 0;
		
         // Etape 1 : r�cup�ration d'une instance de la classe "DocumentBuilderFactory"
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	
        try {
             // Etape 2 : cr�ation d'un parseur
            DocumentBuilder builder = factory.newDocumentBuilder();
			// Etape 3 : cr�ation d'un Document
		    Document document = builder.parse(new File("XML/" + nomFichier + ".xml"));
		    // Etape 4 : r�cup�ration de l'Element racine
		    Element epoques = document.getDocumentElement();
		    // Etape 5 : r�cup�ration de tous les noeuds
		    NodeList noeuds = epoques.getChildNodes();
		    
		    for (int i = 0; i < noeuds.getLength(); i++) {
		    	if(noeuds.item(i).getNodeType() == Node.ELEMENT_NODE && noeuds.item(i).getNodeName().equals("epoque")) {
		    		// On r�cup�re les donn�es concernant une �poque
			    	Element epoque = (Element) noeuds.item(i);
			    	if(epoque.getAttribute("id").equals(Integer.toString(numEpoque))) {
			    		//On r�cup�re les donn�es sur tous les bateaux
			    		NodeList shipsNode = epoque.getChildNodes();
					    for (int j = 0; j < shipsNode.getLength(); j++) {
					    	if(shipsNode.item(j).getNodeType() == Node.ELEMENT_NODE && shipsNode.item(j).getNodeName().equals("ships")) {
					    		// On r�cup�re les donn�es concernant un seul bateau
					    		NodeList shipNode = ((Element) shipsNode.item(j)).getChildNodes();
							    for (int k = 0; k < shipNode.getLength(); k++) {
							    	if(shipNode.item(k).getNodeType() == Node.ELEMENT_NODE) {
							    		NodeList attributsNode = ((Element) shipNode.item(k)).getChildNodes();
									    boolean reconnaissanceBateau = false;
									    String nom = "";
									    String image = "";
									    int longueur = 0;
									    int vie = 0;
							    		for (int l = 0; l < attributsNode.getLength(); l++) {
									    	if(attributsNode.item(l).getNodeType() == Node.ELEMENT_NODE) {
										    	Element attribut = (Element) attributsNode.item(l);
										    	if(attribut.getNodeName().equals("nom"))
										    		nom = attribut.getTextContent();
										    	if(attribut.getNodeName().equals("image"))
										    		image = attribut.getTextContent();
										    	if(attribut.getNodeName().equals("longueur"))
										    		longueur = Integer.parseInt(attribut.getTextContent());
										    	if(attribut.getNodeName().equals("vie"))
										    		vie = Integer.parseInt(attribut.getTextContent());
										    	reconnaissanceBateau = true;
									    	}
									    }
							    		if(reconnaissanceBateau) {
							    			shipsForModel[n] = new Ship(nom, image, longueur, vie);
							    			n++;
							    		}
							    	}
							    }
					    	}
					    }
			    	}
		    	}
		    }
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
			
		return shipsForModel;
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
		
		this.life = 0;
		for (Ship ship : this.age.getShips())
			life += ship.getLife();
		life_ia =  life;

		this.boardAI = new Cell[WIDTH + 1][HEIGHT + 1];
		this.buildBoards(this.boardAI);
		this.initialPlaceShip(this.boardAI);
	}
	
	private Age addAge(String name, Ship[] ships){
		Age age = new Age(name);
		for(int i=0; i<ships.length; i++)	
			age.addShip(ships[i]);
		return age;
	}
	
	private void initialPlaceShip(Cell[][] board){
		int position[][] = { { 2, 2 }, { 5, 2 }, { 8, 7 }, { 8, 2 }, { 5, 8 } };
		int i = 0;
		for (Ship ship : this.age.getShips()) {
			for (int j = 0; j < ship.getLengthShip(); j++) {
				this.setShipCell(board, position[i][0], position[i][1] + j, ship, j);
			}
			i++;
		}
	}

	public void aleaPlace(Cell[][] boardAi) {
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
	
	/*
	 * Teste de verification permettant de savoir si la partie peu commencer apres placement de tout les bateaux
	 */
	public boolean verificationBeginGame(){
		for(int i=0; i<this.boardAI.length; i++){
			for(int j=0; j<this.boardAI.length; j++){
				if(this.boardAI[i][j].getShip() != null)
					return false;
			}
		}
		return true;
	}

	/***********************************************************/
	/************************ GameScreen ***********************/
	/***********************************************************/

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
		if(etat == Etat.IA && end_game == false){
			context.executeStrategy(this);
		}	
	}

	public ArrayList<Context> getStrategie() {
		return strategie;
	}
}
