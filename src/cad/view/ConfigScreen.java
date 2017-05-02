package cad.view;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cad.BattleShip;
import cad.controller.ConfigListener;
import cad.model.Aleatoire;
import cad.model.Context;
import cad.model.Diagonale;
import cad.model.Intelligent;
import cad.model.Model;
import cad.model.Ship;

public class ConfigScreen extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private String[] strategy,age;

	private JComboBox tirOrdinateur,epoque;
	private String strategy1,strategy2,strategy3;
	private Label tirIa,eq;
	private JButton play;
	private BattleShip bs;
	private Model mod;
	private Context context,context2,context3;

	public ConfigScreen(BattleShip battleShip, Model model) {
		this.bs = battleShip;
		this.mod = model;
		
		this.context = new Context(new Aleatoire());
		this.strategy1 = context.getNameStrategy();
		//On affecte la strategie alea de base si y on touche pas les combobox
		this.mod.setContext(context);
		
		
		context2 = new Context(new Diagonale());
		this.strategy2 = context2.getNameStrategy();
		
		context3 = new Context(new Intelligent());
		this.strategy3 = context3.getNameStrategy();
		
		drawConfig();
		controller();
	}

	private void controller() {
		play.addActionListener(new ConfigListener(bs,this));
	}


	private void drawConfig() {
		//Ajouter le nom de la strategy recup dans le controlleur dans le tableau 
		// et ajouter un cas dans la fct itemStateChanged
		this.strategy = new String[]{strategy1,strategy2,strategy3};
		this.tirOrdinateur = new JComboBox(strategy);
		tirOrdinateur.addItemListener(this);
		
		this.age = chargementNomEpoque();
		// Initialisation de l'�poque
		this.mod.selectionEpoque(age[0], chargementEpoque(0));
		this.epoque = new JComboBox(age);
		epoque.addItemListener(this);
		
		this.tirIa = new Label("Strategie tir IA");
		this.eq = new Label("Epoque");
		this.play = new JButton("Play");
		
		this.setBackground(Color.GREEN);
		Box panneauBouton = Box.createVerticalBox();
		panneauBouton.add(tirIa);
		panneauBouton.add(tirOrdinateur);
		panneauBouton.add(eq);
		panneauBouton.add(epoque);
	    panneauBouton.add(Box.createVerticalStrut(60));
	    panneauBouton.add(play);
		this.add(panneauBouton);		
	}
	
	
	/**
	 * Chargement du nom de chacune des �poques disponibles depuis le fichier XML/epoques.xml
	 * 
	 * @return on retourne les noms des �poques dans un tableau
	 */
	public String[] chargementNomEpoque() {
	    int k = 0;
	    String[] nomEpoques;
	    String[] nomEpoquesTemporaires = new String[]{"Aucune Epoque"};;
		
         // Etape 1 : r�cup�ration d'une instance de la classe "DocumentBuilderFactory"
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	
        try {
             // Etape 2 : cr�ation d'un parseur
            DocumentBuilder builder = factory.newDocumentBuilder();
			// Etape 3 : cr�ation d'un Document
		    Document document = builder.parse(new File("XML/epoques.xml"));
		    // Etape 4 : r�cup�ration de l'Element racine
		    Element epoques = document.getDocumentElement();
		    // Etape 5 : r�cup�ration de tous les noeuds
		    NodeList noeuds = epoques.getChildNodes();
		    nomEpoquesTemporaires = new String[noeuds.getLength()];
		    
		    for (int i = 0; i < noeuds.getLength(); i++) {
		    	if(noeuds.item(i).getNodeType() == Node.ELEMENT_NODE && noeuds.item(i).getNodeName().equals("epoque")) {
			    	Element epoque = (Element) noeuds.item(i);
			    	nomEpoquesTemporaires[Integer.parseInt(epoque.getAttribute("id"))] = epoque.getAttribute("nom");
			    	k++;
		    	}
		    }
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        
        // On rassemble les noms d'�poque
        if(k != 0) {
    		nomEpoques = new String[k];
            for(int i = 0; i < k; i++)
            	nomEpoques[i] = nomEpoquesTemporaires[i];
        }
        else 
    		nomEpoques = nomEpoquesTemporaires;
        	
			
		return nomEpoques;
	}
	
	
	/**
	 * Chargement d'une �poque constitu�e de plusieurs bateaux
	 * @return on retourne le nom des attributs avec leurs valeurs
	 */
	public Ship[] chargementEpoque(int numEpoque) {
	    Ship[] shipsForModel = new Ship[5];
	    int n = 0;
		
         // Etape 1 : r�cup�ration d'une instance de la classe "DocumentBuilderFactory"
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	
        try {
             // Etape 2 : cr�ation d'un parseur
            DocumentBuilder builder = factory.newDocumentBuilder();
			// Etape 3 : cr�ation d'un Document
		    Document document = builder.parse(new File("XML/epoques.xml"));
		    // Etape 4 : r�cup�ration de l'Element racine
		    Element epoques = document.getDocumentElement();
		    // Etape 5 : r�cup�ration de tous les noeuds
		    NodeList noeuds = epoques.getChildNodes();
		    
		    for (int i = 0; i < noeuds.getLength(); i++) {
		    	if(noeuds.item(i).getNodeType() == Node.ELEMENT_NODE && noeuds.item(i).getNodeName().equals("epoque")) {
		    		// On r�cup�re les donn�es concernant une �poque
			    	Element epoque = (Element) noeuds.item(i);
			    	if(epoque.getAttribute("id").equals(Integer.toString(numEpoque))) {
			    		//On r�cup�re les donn�es sur tous les bateaux
			    		NodeList shipsNode = epoque.getChildNodes();
					    for (int j = 0; j < shipsNode.getLength(); j++) {
					    	if(shipsNode.item(j).getNodeType() == Node.ELEMENT_NODE && shipsNode.item(j).getNodeName().equals("ships")) {
					    		// On r�cup�re les donn�es concernant un seul bateau
					    		NodeList shipNode = ((Element) shipsNode.item(j)).getChildNodes();
							    for (int k = 0; k < shipNode.getLength(); k++) {
							    	if(shipNode.item(k).getNodeType() == Node.ELEMENT_NODE) {
							    		NodeList attributsNode = ((Element) shipNode.item(k)).getChildNodes();
									    boolean reconnaissanceBateau = false;
									    String nom = "";
									    String image = "";
									    int longueur = 0;
							    		for (int l = 0; l < attributsNode.getLength(); l++) {
									    	if(attributsNode.item(l).getNodeType() == Node.ELEMENT_NODE) {
										    	Element attribut = (Element) attributsNode.item(l);
										    	if(attribut.getNodeName().equals("nom"))
										    		nom = attribut.getTextContent();
										    	if(attribut.getNodeName().equals("image"))
										    		image = attribut.getTextContent();
										    	if(attribut.getNodeName().equals("longueur"))
										    		longueur = Integer.parseInt(attribut.getTextContent());
										    	reconnaissanceBateau = true;
									    	}
									    }
							    		if(reconnaissanceBateau) {
							    			shipsForModel[n] = new Ship(nom, image, longueur, longueur);
							    			n++;
							    		}
							    	}
							    }
					    	}
					    }
			    	}
		    	}
		    }
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
			
		return shipsForModel;
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getSource() == tirOrdinateur){
			int tmp = tirOrdinateur.getSelectedIndex();
			switch (tmp){
			  case 0:
				  this.mod.setContext(context);
				  break;  
			  case 1:
				  this.mod.setContext(context2);
				  break;  
			  case 2:
				  this.mod.setContext(context3);
				  break; 
			  default:
			}
		}

		if(arg0.getSource() == epoque){
			int tmp = epoque.getSelectedIndex();
			switch (tmp){
			  case 0:
				  this.mod.selectionEpoque(age[0], chargementEpoque(0));
				  break;  
			  case 1:
				  this.mod.selectionEpoque(age[1], chargementEpoque(1));
				  break;  
			  case 2:
				  this.mod.selectionEpoque(age[2], chargementEpoque(2));
				  break; 
			  default:
			}
		}
	
	}

}
