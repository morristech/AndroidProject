package org.hennet.androidproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.hennet.androidproject.ParserXML.Entity;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

public class ContainerData {	
	
	static public Context context;
	
	public ContainerData() {

	}

	public static ArrayList<Entity> getDatas(){
		// On passe par une classe factory pour obtenir une instance de sax
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parseur = null;
		ArrayList<Entity> datas = null;
		
		try {
			// On "fabrique" une instance de SAXParser
			parseur = fabrique.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		// On défini l'url du fichier XML
		URL url = null;
		
		try {
			url = new URL("C:/Users/Julien/Imac/Android/Workspace/AndroidProject/src/org/hennet/androidproject/map.xml");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		/* 
		 * Le handler sera gestionnaire du fichier XML c'est à dire que c'est lui qui sera chargé
		 * des opérations de parsing. On vera cette classe en détails ci après.
		*/
		DefaultHandler handler = new ParserXML();
		
		try {
			// On parse le fichier XML
			parseur.parse(url.openConnection().getInputStream(), handler);
			
			// On récupère directement la liste des feeds
			datas = ((ParserXML) handler).getData();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// On la retourne l'array list
		return datas;
	}

}
