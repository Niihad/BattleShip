package model;

import java.io.Serializable;

public class Ship implements Cloneable, Serializable {
	
	private String name, pathImage, pathImageV;
	private boolean rotation = false; // false horizontal - true vertical
	private int lengthShip, life;
	
	public Ship(String name, String pathImage, String pathImageV, int lengthShip, int life){
		this.name = name;
		this.pathImage = pathImage;
		this.pathImageV = pathImageV;
		this.lengthShip = lengthShip;
		this.life = life;
	}

	
	/***********************************************************/
	/********************* GETTER / SETTER *********************/
	/***********************************************************/
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public String getPathImageV() {
		return pathImageV;
	}

	public void setPathImageV(String pathImageV) {
		this.pathImageV = pathImageV;
	}

	public boolean isRotation() {
		return rotation;
	}

	public void setRotation(boolean rotation) {
		this.rotation = rotation;
		String tmp = this.pathImage;
		this.pathImage = this.pathImageV;
		this.pathImageV = tmp;
	}

	public int getLengthShip() {
		return lengthShip;
	}

	public void setLengthShip(int lengthShip) {
		this.lengthShip = lengthShip;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	
	/***********************************************************/
	/************************* FONCTION ************************/
	/***********************************************************/
	
	public Object clone() {
		Ship ship = null;
	    try {
	    	// On récupère l'instance à renvoyer par l'appel de la méthode super.clone()
	    	ship = (Ship) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	// Ne devrait jamais arriver car nous implémentons l'interface Cloneable
	      	cnse.printStackTrace(System.err);
	    }
	    // on renvoie le clone
	    return ship;
	}

}
