package org.hennet.androidproject;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class ParserXML extends DefaultHandler {

	// nom des tags XML
	private final String HOLE = "hole";
	private final String PLATFORM = "platform";
	
	public class Entity{
		//hole=0 platform=1
		public int _type;
		public int _xBegin;
		public int _xEnd;
		public int _yBegin;
		public int _yEnd;
		
		public Entity(int type, int xBegin, int xEnd, int yBegin, int yEnd){
			_type = type;
			_xBegin = xBegin;
			_xEnd = xEnd;
			_yBegin = yBegin;
			_yEnd = yEnd;
		}
		
	}
	
	// Array list d'articles	
	private ArrayList<Entity> datas;
	
	// Boolean permettant de savoir si nous sommes à l'intérieur d'un item
	private boolean inItem;
	
	// Feed courant
	private Entity currentEntity;
	
	// Buffer permettant de contenir les données d'un tag XML
	private StringBuffer buffer;
	
	@Override
	public void processingInstruction(String target, String data) throws SAXException {		
		super.processingInstruction(target, data);
	}

	public ParserXML() {
		super();		
	}
	
	
	// * Cette méthode est appelée par le parser une et une seule  
	// * fois au démarrage de l'analyse de votre flux xml. 
	// * Elle est appelée avant toutes les autres méthodes de l'interface,  
	// * à l'exception unique, évidemment, de la méthode setDocumentLocator. 
	// * Cet événement devrait vous permettre d'initialiser tout ce qui doit 
	// * l'être avant ledébut du parcours du document.
	 
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		datas = new ArrayList<Entity>();
		
	}

	/* 
	 * Fonction étant déclenchée lorsque le parser trouve un tag XML
	 * C'est cette méthode que nous allons utiliser pour instancier un nouveau feed
 	*/
	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		// Nous réinitialisons le buffer a chaque fois qu'il rencontre un item
		buffer = new StringBuffer();		
		
		// Ci dessous, localName contient le nom du tag rencontré
		
		// Nous avons rencontré un tag ITEM, il faut donc instancier un nouveau feed
		if (localName.equalsIgnoreCase(HOLE)){			
			inItem = true;
			//this.currentEntity = new Entity(0, xB, xE, 0, 80);
			Log.i("hole attributes ",""+attributes);
		}
		
		// Vous pouvez définir des actions à effectuer pour chaque item rencontré
		if (localName.equalsIgnoreCase(PLATFORM)){
			inItem = true;
			//this.currentEntity = new Entity(0, xB, xE, yB, yE);
			Log.i("platform attributes ",""+attributes);
		}
	}
	
	 
	// * Fonction étant déclenchée lorsque le parser à parsé 	
	// * l'intérieur de la balise XML La méthode characters  
	// * a donc fait son ouvrage et tous les caractère inclus 
	// * dans la balise en cours sont copiés dans le buffer 
	// * On peut donc tranquillement les récupérer pour compléter
	// * notre objet currentFeed
	
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {		
		
		if (localName.equalsIgnoreCase(HOLE) || localName.equalsIgnoreCase(PLATFORM)){		
			datas.add(currentEntity);
			inItem = false;
		}
	}

	
	// cette méthode nous permettra de récupérer les données
	public ArrayList<Entity> getData() {
		return this.datas;
	}
}
