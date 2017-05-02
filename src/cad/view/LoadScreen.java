package cad.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import cad.BattleShip;
import cad.controller.LoadListener;
import cad.model.Model;

@SuppressWarnings("serial")
public class LoadScreen extends JPanel {


	private JList listProfil;
	private Label profil;
	private JButton load;
	private BattleShip bs;

	public LoadScreen(BattleShip battleShip) {
		this.bs = battleShip;
		
		drawLoad();
		controller();
	}

	private void controller() {
		load.addActionListener(new LoadListener(bs, this));
	}


	private void drawLoad() {

		this.profil = new Label("Profil");		
		this.load = new JButton("Charger");
		this.listProfil = new JList(chargerProfils());
		
		this.setBackground(Color.GREEN);
		Box panneauBouton = Box.createVerticalBox();
		panneauBouton.add(profil);
		panneauBouton.add(listProfil);
		panneauBouton.add(load);
		this.add(panneauBouton);		
	}
	
	/**
	 * On charge chacun des modèles avec leur nom (de fichier)
	 */
	public Vector chargerProfils() {

        File folder = new File("./profils");
        File[] files = folder.listFiles();
        
		Vector profils = new Vector();

        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile() && files[i].getName().endsWith(".save") && !files[i].isHidden()) {
                try {
                    FileInputStream fis = new FileInputStream(files[i]);
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    Model profile = (Model) ois.readObject();
                    profils.add(files[i].getName().substring(0,files[i].getName().indexOf('.')));
                    ois.close();
                    fis.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return profils;
	}

	public JList getListProfil() {
		return listProfil;
	}
	

	public void paintComponent(Graphics g){
         try{
	        Image img = ImageIO.read(new File("./assets/image/Menu.jpg"));
            int height = this.getSize().height;
            int width = this.getSize().width;
            g.drawImage(img, 0, 0, width, height, this);
	    } catch(IOException e){
	        e.printStackTrace();
	    }
    }
	


}
