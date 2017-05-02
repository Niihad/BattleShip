package cad.model;

import static org.junit.Assert.*;

import org.junit.Test;

import cad.BattleShip;
import cad.controller.ConfigListener;
import cad.model.Model.Etat;
import cad.view.ConfigScreen;

public class ModelTest {

	//on test si au debut on est bien en att
	@Test
	public void testinit() {
		Model model = new Model();
		assertTrue(model.getEtat() == Etat.WAIT);
	}

	//on test si le tour du jouuer est bien specifier apres le tirage au sort
	@Test
	public void test_start(){
		BattleShip bs = new BattleShip();
		ConfigScreen config = new ConfigScreen(bs);
		ConfigListener configlistener = new ConfigListener(bs,config);
		configlistener.alea(0);
		assertTrue(bs.getModel().getEtat() != Etat.WAIT);
	}
	
	//on test si sa detecte bien le tir
	@Test
	public void test_pas_tirer() {
		Model model = new Model();
		model.setShoot(1,0);
		assertTrue(false == model.neverShoot(1,1));
	}
	
	@Test
	//on test si sa detecte bien qu'il  n y a pas de tir
	public void test_deja_tirer() {
		Model model = new Model();
		model.setShoot(1,1);
		assertTrue(true == model.neverShoot(1,1));
	}
	
	//test si la vie diminue bien lors d'une collision
	@Test
	public void test(){
		Model model = new Model();
		Ship sh = new Ship("test",null,null,1, 1);
		model.setShipCell(model.getBoardPlayer(), 1,1, sh,1);
		int tmp = model.getLife();
		model.setEtat(Etat.IA);
		model.setShoot(1, 1);
		assertTrue(model.getLife()==tmp-1);
	}
	
	
}
