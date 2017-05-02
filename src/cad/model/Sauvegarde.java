package cad.model;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Sauvegarde {

	public Sauvegarde(String pseudo, Model model) {
		
		ArrayList<Ship> shipsList = model.getAge().getShips();
		
		if(pseudo.equals("")) 
			pseudo = "sauvegarde";
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		System.out.println("on passe par la sauvegarde");
	    try {
		// Création d'un nouveau DOM
					DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
					DocumentBuilder constructeur = fabrique.newDocumentBuilder();
					Document document = constructeur.newDocument();
		 
					// Propriétés du DOM
					document.setXmlVersion("1.0");
					document.setXmlStandalone(true);
		 
					// Création de l'arborescence du DOM
					Element racine = document.createElement("epoques");
		 
					Element epoque = document.createElement("epoque");
					epoque.setAttribute("nom","Moderne");
					epoque.setAttribute("id","0");
					racine.appendChild(epoque);

					Element ships = document.createElement("ships");
					epoque.appendChild(ships);
					for(int i = 0; i < shipsList.size(); i++) {
						Element ship = document.createElement("ship");
						ships.appendChild(ship);
						
						Ship shipModel = shipsList.get(i);
						
						// nom du bateau
						Element nom = document.createElement("nom");
						nom.setTextContent(shipModel.getName());
						ship.appendChild(nom);
						
						// lien image bateau
						Element image = document.createElement("image");
						image.setTextContent(shipModel.getPathImage());
						ship.appendChild(image);

						// longueur du bateau
						Element longueur = document.createElement("longueur");
						longueur.setTextContent("" + shipModel.getLengthShip());
						ship.appendChild(longueur);

						// vie du bateau
						Element vie = document.createElement("vie");
						vie.setTextContent("" + shipModel.getLife());
						ship.appendChild(vie);
						
					}
					
		 
					document.appendChild(racine);
		 
					//Sauvegarde du DOM dans un fichier XML
					transformerXml(document, "./XML/" + pseudo + ".xml");
					System.out.println("on finit la sauvegarde");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	    
	}
	
	
	public void transformerXml(Document document, String fichier) {
        try {
        	
            // Création de la source DOM
            Source source = new DOMSource(document);
 
            // Création du fichier de sortie
            File file = new File(fichier);
            Result resultat = new StreamResult(fichier);
 
            // Configuration du transformer
            TransformerFactory fabrique = TransformerFactory.newInstance();
            Transformer transformer = fabrique.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
 
            // Transformation
            transformer.transform(source, resultat);
        }catch(Exception e){
        	e.printStackTrace();	
        }
    }
	
}
