package cad;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cad.model.Model;
import cad.view.ConfigScreen;
import cad.view.GameScreen;
import cad.view.MenuScreen;
import cad.view.StatsScreen;

public class BattleShip extends JFrame {

	private static final long serialVersionUID = 1L;
	private MenuScreen menu;
	private GameScreen game;
	private StatsScreen stats;
	private ConfigScreen config;
	private JFrame frame;
	private Model model;

	public BattleShip() {
		this.model = new Model();
		frame = new JFrame("Bataille navale");
		this.menu = new MenuScreen(this);
		frame.add(menu, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void setGameScreen() {
		this.stats = new StatsScreen(model);
		this.game = new GameScreen(model);
		frame.add(stats, BorderLayout.NORTH);
		frame.add(game, BorderLayout.CENTER);
	}
	
	public void setConfigScreen() {
		this.config = new ConfigScreen(this);
		frame.add(config, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		new BattleShip();
	}
}