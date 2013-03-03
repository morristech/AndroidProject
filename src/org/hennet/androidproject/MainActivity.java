package org.hennet.androidproject;

import org.hennet.androidproject.jeu.Jeu;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;


public class MainActivity extends AndroidApplication {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Flag pour l'utilisation d'OpenGL ES 2.0
		boolean useOpenGLES2 = false;       
		// Création et lancement du jeu
		initialize( new Jeu(), useOpenGLES2 );    
	}
}