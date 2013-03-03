package org.hennet.androidproject.jeu;

import java.util.ArrayList;

import org.hennet.androidproject.ContainerData;
import org.hennet.androidproject.ParserXML;
import org.hennet.androidproject.ParserXML.Entity;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//interface gesturelistener pour les evenements

public class Jeu extends Game implements ApplicationListener {
	// constant useful for logging
	public static final String LOG = Jeu.class.getSimpleName();

	// a libgdx helper class that logs the current FPS each second
	private FPSLogger fpsLogger;
	
	private ArrayList<Entity> datas;
	
	private SpriteBatch batch;
	private Texture sheetTexture;
	private TextureRegion tabRegion[];
	private static final int largeur_image = 64;
	private static final int hauteur_image = 64;
	private static final int nombre_image = 8;
	private Animation animation[];
	private static final float duree_animation = 1.2f;    
	private float temps;
	private TextureRegion regionCourante, regionInitial;
	private static final int nb_colone = 8;
    private static final int nb_ligne = 2;
    private  int largeur_texture ;
    private  int hauteur_texture;
    private float xPos;
    private float yPos;
    private boolean animation_stop;
    private int type_animation;
    private  float vitesse = 1f;
    private int cpthigh = 0;
    private boolean beginJump = false;
    private boolean beginFall = false;
    private float height = 0f;
    private float lastAccelometer = 0f;
    

	public SplashScreen getSplashScreen() {
		return new SplashScreen( this );
	}

	@Override
	public void create() {    
		//
		Gdx.app.log( Jeu.LOG, "Creating game" );
		fpsLogger = new FPSLogger();
		setScreen( getSplashScreen() );
		
		//chargement du XML
		//datas = ContainerData.getDatas();

		// Initialisation de la texture sous forme de sprite
		batch = new SpriteBatch();
		sheetTexture = new Texture(Gdx.files.internal("trame_obelix.png"));
		// Initialisation du tableau des textures
		largeur_texture = sheetTexture.getWidth();
        hauteur_texture = sheetTexture.getHeight();
        animation_stop = false;
		TextureRegion[][] tmp = TextureRegion.split(sheetTexture, largeur_texture/nb_colone, hauteur_texture/nb_ligne);
		regionInitial = tmp[0][1];
		
		 // Positionner Obelix au milieu
        xPos = Gdx.graphics.getWidth()*0.5f;
        yPos = 68;
		
		tabRegion = new TextureRegion[nombre_image];
		//
		int index = 0;

		for(int j = 0; j < nombre_image; j++) {
			tabRegion[index++] = tmp[0][j];
		}

		// Initialisation de l'animation
		animation = new Animation[1];
        type_animation = 0;
		temps = 0.0f;
		
		// Instancier l'animation
        for(int i=0;i<1;i++)
         animation[i] = new Animation(duree_animation/nombre_image, tmp[i]);
	}	

	@Override
	public void resize(int width, int height ) {
		super.resize( width, height );
		Gdx.app.log( Jeu.LOG, "Resizing game to: " + width + " x " + height );
	}

	@Override
	public void render() {
		super.render();
		
		if (!animation_stop) // si aucune manipulation en cours
        {
         temps += Gdx.graphics.getDeltaTime();                   
         regionCourante = animation[type_animation].getKeyFrame(temps/1, true);  
        }
		
		float diffAcc = Gdx.input.getAccelerometerX() - lastAccelometer;
		
        if(diffAcc <-1 && !beginJump && !beginFall) {
		//if(Gdx.input.isKeyPressed(32) && cpthigh==0) {
        	beginJump = true;
        	
        	if(diffAcc>-1.5)
        		height = 150;
        	else{
        		if(diffAcc>-2)
        			height = 200;
        		else
        			height = 250;
        	}
        		
        	//stoper anim marcher, mettre anim sauter
        }
        
        lastAccelometer = Gdx.input.getAccelerometerX();
        
        if (beginJump){
        	if (cpthigh < height){
        		cpthigh += 6;
        	}
        	else{
        		beginFall = true;
        		beginJump = false;
        	}
    	}
        
        if (beginFall){
        	if (cpthigh > 0){
        		cpthigh -= 6;
        	}
        	else{
        		beginFall = false;
        		//remettre anim marcher
        	}
    	}
        
        
        
         // Dessiner
         batch.begin();
         batch.draw(regionCourante, xPos, yPos+cpthigh);                    
         batch.end();
      
         // Manipuler
         //ManipulerAccelerometre();
	}
	
    private void ManipulerAccelerometre() {
          // Les directions principales gauche/droite
          if(Gdx.input.getAccelerometerY()>1 ) {
                 xPos += vitesse;
                 type_animation=1;
          }
          if(Gdx.input.getAccelerometerY()<-1) {
                 xPos -= vitesse;
                 type_animation=0;
          }
          
          if ( xPos<0 )
        	  xPos = Gdx.graphics.getWidth();
          
          if ( xPos > Gdx.graphics.getWidth() )
        	  xPos = 0;
          
         
          // La direction initiale du Lapin
          if (Gdx.input.getAccelerometerY()<1 && Gdx.input.getAccelerometerY()>-1 ) {
                 animation_stop = true;
                 regionCourante = regionInitial ;
          } else {
                 animation_stop = false;
          }
    }

	@Override
	public void pause() {
		Gdx.app.log( Jeu.LOG, "Pausing game" );
	}

	@Override
	public void resume() {
		Gdx.app.log( Jeu.LOG, "Resuming game" );
	}

	@Override
	public void setScreen( Screen screen ) {
		super.setScreen( screen );
		Gdx.app.log( Jeu.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
	}

	@Override
	public void dispose() {
		Gdx.app.log( Jeu.LOG, "Disposing game" );
	}
}