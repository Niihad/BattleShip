package cad.model;

import java.io.Serializable;

public class Cell implements Cloneable, Serializable{
	protected int x, y, part;
	protected boolean state, isTouch, shoot;
	protected Ship ship;
	
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		this.shoot = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isTouch() {
		return isTouch;
	}

	public void setTouch(boolean isTouch) {
		this.isTouch = isTouch;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public boolean isShoot() {
		return shoot;
	}

	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Object cloneShip(){
		return (Ship) ship.clone();
	}
	
	public Object clone() {
	    Cell cell = null;
	    try {
	    	// On récupère l'instance à renvoyer par l'appel de la méthode super.clone()
	    	cell = (Cell) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	// Ne devrait jamais arriver car nous implémentons l'interface Cloneable
	      	cnse.printStackTrace(System.err);
	    }
	    // On clone l'attribut de type Patronyme qui n'est pas immuable.
	    //cell.ship = (Ship) ship.clone();
	    // on renvoie le clone
	    return cell;
	}

}
