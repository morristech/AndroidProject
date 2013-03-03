package org.hennet.androidproject.jeu;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SplashScreen extends AbstractScreen {
	private Texture splashTexture;
	private TextureRegion splashTextureRegion;
	int cpt = 0;

	public SplashScreen( Jeu game ) {
		super( game );
	}

    @Override
    public void show() {
        super.show();

        // Charge l'image splash et construit la zone comme texture
        splashTexture = new Texture( "map.png" );

        // Positionnement linéaire pour un meilleur étirement
        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );

        // Démarrage à 0,0 (coin gauche) et taille à 512x320
        splashTextureRegion = new TextureRegion( splashTexture, 0, 0, 800, 480 );
    }

    @Override
    public void render( float delta ) {
        super.render( delta );
        if (cpt < 3296){
        	cpt+=3;
        	splashTextureRegion = new TextureRegion( splashTexture, cpt, 0, 800, 480 );
        }
        
       
        // Nous sommes sur une texture de type 2D
        batch.begin();

        // On dessine...
        batch.draw( splashTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

        // Fin de l'action de dessiner
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        splashTexture.dispose();
    }
}

