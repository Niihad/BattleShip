package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Age implements Serializable {

	protected String age;
	protected ArrayList<Ship> ships;
	
	public Age(String age){
		this.age = age;
		this.ships = new ArrayList<Ship>();
	}
	

	/***********************************************************/
	/********************* GETTER / SETTER *********************/
	/***********************************************************/
	
	public String getAgeName() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}
	
	
	/***********************************************************/
	/************************* FONCTION ************************/
	/***********************************************************/
	
	public void addShip(Ship ship){
			this.ships.add(ship);
	}
	
	public void deleteShip(Ship ship){
		this.ships.remove(ship);
	}
	
}
