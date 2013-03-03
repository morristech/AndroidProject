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
	
	// Boolean permettant de savoir si nous sommes � l'int�rieur d'un item
	private boolean inItem;
	
	// Feed courant
	private Entity currentEntity;
	
	// Buffer permettant de contenir les donn�es d'un tag XML
	private StringBuffer buffer;
	
	@Override
	public void processingInstruction(String target, String data) throws SAXException {		
		super.processingInstruction(target, data);
	}

	public ParserXML() {
		super();		
	}
	
	
	// * Cette m�thode est appel�e par le parser une et une seule  
	// * fois au d�marrage de l'analyse de votre flux xml. 
	// * Elle est appel�e avant toutes les autres m�thodes de l'interface,  
	// * � l'exception unique, �videmment, de la m�thode setDocumentLocator. 
	// * Cet �v�nement devrait vous permettre d'initialiser tout ce qui doit 
	// * l'�tre avant led�but du parcours du document.
	 
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		datas = new ArrayList<Entity>();
		
	}

	/* 
	 * Fonction �tant d�clench�e lorsque le parser trouve un tag XML
	 * C'est cette m�thode que nous allons utiliser pour instancier un nouveau feed
 	*/
	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		// Nous r�initialisons le buffer a chaque fois qu'il rencontre un item
		buffer = new StringBuffer();		
		
		// Ci dessous, localName contient le nom du tag rencontr�
		
		// Nous avons rencontr� un tag ITEM, il faut donc instancier un nouveau feed
		if (localName.equalsIgnoreCase(HOLE)){			
			inItem = true;
			//this.currentEntity = new Entity(0, xB, xE, 0, 80);
			Log.i("hole attributes ",""+attributes);
		}
		
		// Vous pouvez d�finir des actions � effectuer pour chaque item rencontr�
		if (localName.equalsIgnoreCase(PLATFORM)){
			inItem = true;
			//this.currentEntity = new Entity(0, xB, xE, yB, yE);
			Log.i("platform attributes ",""+attributes);
		}
	}
	
	 
	// * Fonction �tant d�clench�e lorsque le parser � pars� 	
	// * l'int�rieur de la balise XML La m�thode characters  
	// * a donc fait son ouvrage et tous les caract�re inclus 
	// * dans la balise en cours sont copi�s dans le buffer 
	// * On peut donc tranquillement les r�cup�rer pour compl�ter
	// * notre objet currentFeed
	
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {		
		
		if (localName.equalsIgnoreCase(HOLE) || localName.equalsIgnoreCase(PLATFORM)){		
			datas.add(currentEntity);
			inItem = false;
		}
	}

	
	// cette m�thode nous permettra de r�cup�rer les donn�es
	public ArrayList<Entity> getData() {
		return this.datas;
	}
}
