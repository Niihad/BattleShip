package cad;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cad.model.Model;
import cad.view.ConfigScreen;
import cad.view.GameScreen;
import cad.view.MenuScreen;
import cad.view.MenuView;
import cad.view.PlacementScreen;
import cad.view.StatsView;

public class BattleShip extends JFrame {

	private static final long serialVersionUID = 1L;
	private MenuScreen menu;
	private GameScreen game;
	private PlacementScreen placement;
	private StatsView stats;
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
		frame.setJMenuBar(new MenuView(model));
		this.stats = new StatsView(model);
		this.placement = new PlacementScreen(model);
		this.game = new GameScreen(model);
		frame.add(stats, BorderLayout.NORTH);
		//frame.add(placement, BorderLayout.CENTER);
		frame.add(game, BorderLayout.CENTER);
	}
	
	public void setConfigScreen() {
		this.config = new ConfigScreen(this,model);
		frame.add(config, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		new BattleShip();
	}

	public Model getModel() {
		return model;
	}
	
	
}