package model;

import java.io.Serializable;

public class Cell implements Cloneable, Serializable{
	protected int x, y, part;
	protected boolean shoot;
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
}
