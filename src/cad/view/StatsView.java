package cad.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cad.model.Model;

public class StatsView extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private Model mod;
	private JLabel life,life_IA,strategy,age;
	private int tmp;
	
	public StatsView(Model model){
		this.mod = model;
		draw();
		this.mod.addObserver(this);
	}

	//affichage des infos concernant le jeux
	private void draw() {
		this.tmp = this.mod.getLife();
		this.life = new JLabel("Life : " + tmp +" / " + tmp);
		this.life.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.life_IA = new JLabel("Life IA : " + tmp +" / " + tmp);
		this.life_IA.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.strategy = new JLabel("Strategie IA : " + mod.getContext().getNameStrategy());
		this.strategy.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.age = new JLabel("Age : " + mod.getAge().getAgeName());
		this.add(life);
		this.add(life_IA);	
		this.add(strategy);
		this.add(age);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.life.setText("Life : " + this.mod.getLife() +" / " + tmp);
		this.life_IA.setText("Life IA: " + this.mod.getLife_ia() +" / " + tmp);
		this.strategy.setText("Strategie IA : " + mod.getContext().getNameStrategy());
	}

}
