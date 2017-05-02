
package cad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import cad.BattleShip;
import cad.model.Model;
import cad.view.LoadScreen;

public class LoadListener implements ActionListener {
	private LoadScreen loadScreen;
	private BattleShip bs;

	public LoadListener(BattleShip bs, LoadScreen lScreen) {
		this.loadScreen = lScreen;
		this.bs = bs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loadScreen.setVisible(false);
		bs.setModel(chargerProfil((String) loadScreen.getListProfil().getSelectedValue()));
		bs.setGameScreen();
		bs.getGame().updateBoardAI();
		bs.getGame().updateBoardPlayer();
		bs.getModel().IA_play();
	}


	/**
	 * On charge le mod�le choisi
	 * @param nomFichier
	 */
	public Model chargerProfil(String nomFichier) {

        File folder = new File("./profils");
        File[] files = folder.listFiles();
        Model model = new Model();
        

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile() && files[i].getName().endsWith(".save") && files[i].getName().equals(nomFichier + ".save") && !files[i].isHidden()) {
                try {
                    FileInputStream fis = new FileInputStream(files[i]);
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    model = (Model) ois.readObject();
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
        
        return model;
        
	}
	
}