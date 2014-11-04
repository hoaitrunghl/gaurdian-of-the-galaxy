package uit.gaurdianofthegalaxy;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends BaseGameActivity implements
IOnSceneTouchListener {

 
 private static  int CAMERA_WIDTH ;
 private static int CAMERA_HEIGHT ;


 private Camera mCamera;
 private Scene scene;
 //Khai báo biến background
 private Texture mAutoParallaxBackgroundTexture;
 private TextureRegion mParallaxLayerBack;
 //Khai báo biến sprite animate
 private Texture mTextureAirplanet;
 private TiledTextureRegion mTextureRegionAirplanet;
 private AnimatedSprite Player;
 //Khai báo biến sprite
 private Texture mTexturePlanets; 
 private TextureRegion mTextureRegionPlanets;
 private Texture mTexturePlanets2; 
 private TextureRegion mTextureRegionPlanets2;
 private Texture mTexturePlanets3; 
 private TextureRegion mTextureRegionPlanets3;
 private Texture mTexturePlanets4; 
 private TextureRegion mTextureRegionPlanets4;
 private Texture mTexturePlanets5; 
 private TextureRegion mTextureRegionPlanets5;
 //Khai báo biến Meteors.
 private Texture mTextureMeteors;
 private TextureRegion mTextureRegionMeteors;
 private Sprite SpriteMeteors;
 private LinkedList<Sprite> targetLLMeteors;
 private LinkedList<Sprite> TargetsToBeAddedMeteors;
 // Khai báo biến va chạm
 private Texture mTextureVacham;
 private TiledTextureRegion mTiledTextureRegionVacham;
 private AnimatedSprite Vacham;

 //Khao báo biến music
 private Music backgroundMusic;
 private Sound Boom;
 QuyDaoBay quydaobay=new QuyDaoBay();
 float ty, py;
 int d;
 Boolean check1=false;
 Boolean check2=false;
 Boolean check3=false;
 Boolean check4=false;
 Boolean check5=false;
 @Override
 public Engine onLoadEngine() {
	 //load thông số màn hình của thiết bị
	 DisplayMetrics dm = new DisplayMetrics();
	  getWindowManager().getDefaultDisplay().getMetrics(dm);
	 int width = dm.widthPixels;
	  int height = dm.heightPixels;
	  CAMERA_WIDTH = width;
	  CAMERA_HEIGHT = height;
	  ///////////////////////////////////////
  this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
  final Engine engine = new Engine(new EngineOptions(true,
		    ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
		      CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera).setNeedsMusic(true).setNeedsSound(true));
  return engine;
 }

 @Override
 public void onLoadResources() {
	 
	
  //====================================== Load Background ===========================================================//
  this.mAutoParallaxBackgroundTexture = new Texture(2048, 1024, TextureOptions.DEFAULT); 
  this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture
    , this, "gfx/Background4.jpg", 0,0);
  this.mEngine.getTextureManager().loadTextures( this.mAutoParallaxBackgroundTexture);
  
   //=====================================  Load sprite animate ===================================================//
  	TextureRegionFactory.setAssetBasePath("gfx/");
  	this.mTextureAirplanet = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    this.mTextureRegionAirplanet = TextureRegionFactory.createTiledFromAsset(this.mTextureAirplanet, this, "animatesprite2.png",0, 0, 1, 1);
    this.mEngine.getTextureManager().loadTexture(this.mTextureAirplanet);
   
    //====================================== Load sprite Planets ==================================================//
    this.mTexturePlanets = new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionPlanets = TextureRegionFactory.createFromAsset(this.mTexturePlanets, this, "planets1.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets);
    
    this.mTexturePlanets2 = new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionPlanets2 = TextureRegionFactory.createFromAsset(this.mTexturePlanets2, this, "1.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets2);
    
    this.mTexturePlanets3 = new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionPlanets3 = TextureRegionFactory.createFromAsset(this.mTexturePlanets3, this, "11.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets3);
    
    this.mTexturePlanets4 = new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionPlanets4 = TextureRegionFactory.createFromAsset(this.mTexturePlanets4, this, "2.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets4);
    
    this.mTexturePlanets5 = new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionPlanets5 = TextureRegionFactory.createFromAsset(this.mTexturePlanets5, this, "5.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets5);
    //====================================== Load Meteors ========================================================//
    this.mTextureMeteors = new Texture(512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionMeteors = TextureRegionFactory.createFromAsset(this.mTextureMeteors, this, "meteors1.png", 0, 0);
    //this.mTextureRegionMeteors = TextureRegionFactory.createTiledFromAsset(this.mTextureMeteors, this, "meteors1.png", 0, 0, 1, 1);
    this.mEngine.getTextureManager().loadTexture(this.mTextureMeteors);
    
    //======================================= Load Vacham ========================================================//
    this.mTextureVacham = new Texture(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
	this.mTiledTextureRegionVacham = TextureRegionFactory.createTiledFromAsset(this.mTextureVacham, this, "burst.png", 0, 0,7,7);
	mEngine.getTextureManager().loadTexture(mTextureVacham);

        
    /////Music
    MusicFactory.setAssetBasePath("mfx/");
    try {
    	   backgroundMusic = MusicFactory
    	     .createMusicFromAsset(mEngine.getMusicManager(), this,"music_back.mp3");
    	   backgroundMusic.setLooping(true);
    	  } catch (IllegalStateException e) {
    	   e.printStackTrace();
    	  } catch (IOException e) {
    	   e.printStackTrace();
    	  }
    SoundFactory.setAssetBasePath("mfx/");
    try {
		Boom = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "Boom.mp3");
				//.createSoundFromAsset(mEngine.getSoundManager(), this, "Boom.mp3");
    }catch (IllegalStateException e) {
 	   e.printStackTrace();
 	  } catch (IOException e) {
 	   e.printStackTrace();
 	  }

 }

 @Override
 public Scene onLoadScene() {
  
	 ///===================================*Load background*=============================================//
  this.mEngine.registerUpdateHandler(new FPSLogger());
  final Scene scene = new Scene();
  
  final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
  autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-50.0f, new Sprite(0, CAMERA_HEIGHT - 
    this.mParallaxLayerBack.getHeight()
    , this.mParallaxLayerBack)));
  scene.setBackground(autoParallaxBackground);
  //========================================*Load sprite animate*========================================//
    
  //Vị trí khởi tạo AnimatedSprite
  final int PlayerX = 0;
  final int PlayerY = (CAMERA_HEIGHT - this.mTextureRegionAirplanet.getHeight()) / 2;
  //Tạo đối tượng AnimatedSprite
  Player = new AnimatedSprite(PlayerX, PlayerY, this.mTextureRegionAirplanet);

  final PhysicsHandler physicsHandler = new PhysicsHandler(Player);
  Player.registerUpdateHandler(physicsHandler);
  scene.attachChild(Player);
  Player.setScale((float) 0.7);
  
  //========================================*Load sprite Planets*=========================================//
  final Sprite SpritePlanets =  new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionPlanets);
  scene.attachChild(SpritePlanets);
  SpritePlanets.setScale(1);
  
 final Sprite SpritePlanets2 =  new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionPlanets2);
  scene.attachChild(SpritePlanets2);
  SpritePlanets2.setScale((float) 0.75);
  
  final Sprite SpritePlanets3 =  new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionPlanets3);
  scene.attachChild(SpritePlanets3);
  SpritePlanets3.setScale(1);
  
  final Sprite SpritePlanets4 =  new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionPlanets4);
  scene.attachChild(SpritePlanets4);
  SpritePlanets4.setScale(1);
  
  final Sprite SpritePlanets5 =  new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionPlanets5);
  scene.attachChild(SpritePlanets5);
  SpritePlanets5.setScale(1);
  //======================================= Load Va chạm =================================================
  Vacham = new AnimatedSprite((float) (-CAMERA_WIDTH), 100, this.mTiledTextureRegionVacham);
	scene.attachChild(Vacham);
	Vacham.setScale((float) 2f);
	Vacham.setRotation(0);
	Vacham.animate(new long[] { 200, 200, 200,200},0, 3, true);
	Vacham.setVisible(false);
  //=================================== * Meteors and move * ===========================================================//
	SpriteMeteors =  new Sprite((float) (-CAMERA_WIDTH), CAMERA_HEIGHT/2, mTextureRegionMeteors);
	scene.attachChild(SpriteMeteors);
	SpriteMeteors.setScale((float) (0.2));
	
	//quydaobay.setXY(SpriteMeteors.getX(), SpriteMeteors.getY());
	//quydaobay.moveDirection(0);
	//quydaobay.getX();
	//quydaobay.getY();
	//targetLLMeteors = new LinkedList();
	//TargetsToBeAddedMeteors = new LinkedList();
	IUpdateHandler detect = new IUpdateHandler() {
	    @Override
	    public void reset() {
	    	
	    }
	    public void onUpdate(float pSecondsElapsed) { 
	    	try {
	    		//========== Move Metoers ==============//
	    		if ((SpriteMeteors.getX()<-50))
	    		{
	    		Random rand = new Random();
	    		int minY = 5;
	    		int maxY = CAMERA_HEIGHT- mTextureRegionMeteors.getWidth();
	    		int rangeY = maxY - minY;
	    		ty = rand.nextInt(rangeY) + minY;
	    		double x = CAMERA_WIDTH;
	    		SpriteMeteors.setPosition(CAMERA_WIDTH, ty);
	    		quydaobay.setXY(ty,ty);
	    		d =rand.nextInt(3);
	    		}
	    		if (d==0) SpriteMeteors.setPosition(SpriteMeteors.getX()-20,ty);
	    		else 
	    		{
	    		
	    		quydaobay.setXY(SpriteMeteors.getX(),0);	
				quydaobay.moveDirection(d);
				ty= quydaobay.getY();
				SpriteMeteors.setPosition(SpriteMeteors.getX()-20,ty);
	    		}
	    		//================ Move Player ====================//
	    		Player.setPosition(Player.getX(),Player.getY()+20);
	    		Thread.sleep(50);
	    		//================ Move Planets ===================//
	    		if (check1==false)
	    		{
	    			Random rand = new Random();
		    		int minY = mTexturePlanets.getHeight();
		    		int maxY = CAMERA_HEIGHT;
		    		int rangeY = maxY - minY;
		    		int py1 = rand.nextInt(rangeY);	
		    		SpritePlanets.setPosition(CAMERA_WIDTH, py1);
		    		check1=true;
	    		}
	    		if (SpritePlanets.getX()>0-SpritePlanets.getWidth())
	    			SpritePlanets.setPosition(SpritePlanets.getX()-10, SpritePlanets.getY());
	    		//===================================================================//
	    		if (SpritePlanets.getX()<0-SpritePlanets.getWidth()&&(check2==false))
	    		{
	    			Random rand = new Random();
		    		int minY = mTexturePlanets2.getHeight();
		    		int maxY = CAMERA_HEIGHT;
		    		int rangeY = maxY - minY;
		    		int py2 = rand.nextInt(rangeY);	
		    		SpritePlanets2.setPosition(CAMERA_WIDTH, py2);
		    		check2=true;
	    		}
	    		if ((SpritePlanets2.getX()>0-SpritePlanets2.getWidth()*2)&&(check2==true))
	    			SpritePlanets2.setPosition(SpritePlanets2.getX()-10, SpritePlanets2.getY());
	    		//====================================================================//
	    		if (SpritePlanets2.getX()<0-SpritePlanets2.getX()&&(check3==false))
	    		{
	    			Random rand = new Random();
		    		int minY = mTexturePlanets3.getHeight();
		    		int maxY = CAMERA_HEIGHT;
		    		int rangeY = maxY - minY;
		    		int py3 = rand.nextInt(rangeY);	
		    		SpritePlanets3.setPosition(CAMERA_WIDTH, py3);
		    		check3=true;
	    		}
	    		if ((SpritePlanets3.getX()>0-SpritePlanets3.getWidth())&&(check3==true))
	    			SpritePlanets3.setPosition(SpritePlanets3.getX()-10, SpritePlanets3.getY());
	    		//====================================================================//
	    		if (SpritePlanets3.getX()<0-SpritePlanets3.getX()&&(check4==false))
	    		{
	    			Random rand = new Random();
		    		int minY = mTexturePlanets4.getHeight();
		    		int maxY = CAMERA_HEIGHT;
		    		int rangeY = maxY - minY;
		    		int py4 = rand.nextInt(rangeY);	
		    		SpritePlanets4.setPosition(CAMERA_WIDTH, py4);
		    		check4=true;
	    		}
	    		if ((SpritePlanets4.getX()>0-SpritePlanets4.getWidth())&&(check4==true))
	    			SpritePlanets4.setPosition(SpritePlanets4.getX()-10, SpritePlanets4.getY());
	    		//====================================================================//
	    		if (SpritePlanets4.getX()<0-SpritePlanets4.getX()&&(check5==false))
	    		{
	    			Random rand = new Random();
		    		int minY = mTexturePlanets5.getHeight();
		    		int maxY = CAMERA_HEIGHT;
		    		int rangeY = maxY - minY;
		    		int py5 = rand.nextInt(rangeY);	
		    		SpritePlanets5.setPosition(CAMERA_WIDTH, py5);
		    		check5=true;
	    		}
	    		if ((SpritePlanets5.getX()>0-SpritePlanets5.getWidth())&&(check5==true))
	    			SpritePlanets5.setPosition(SpritePlanets5.getX()-10, SpritePlanets5.getY());
	    		
	          if (SpriteMeteors.collidesWith(Player)) {
	        	  	Vacham.setVisible(true);
			  		Vacham.setPosition(Player.getX(), Player.getY());
			  		//removeSprite(_target, targets);
			  		scene.detachChild(Player);
			  		Boom.play();
//			  		return ;
			  		finish();// Kết thúc ứng dụng.
            }
	        
	    	
	      
	   } 
		   catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}}
	  
	    };
	    
	    	scene.registerUpdateHandler(detect);
	    	scene.setOnSceneTouchListener(this);
  
  //==============================================*Load music*================================================//
  //this.backgroundMusic.play(); 
  return scene; 
 }
 public void removeSprite(final Sprite _sprite, Iterator it) {
	    runOnUpdateThread(new Runnable() {
	 
	        @Override
	        public void run() {
	            scene.detachChild(_sprite);
	        }
	    });
	    it.remove();
	}

@Override
 public void onLoadComplete() {
   
 }

@Override
public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	// TODO Auto-generated method stub
	if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
		final float touchX = Player.getX();
		final float touchY = 150;
		
        touchProjectile(touchX,touchY);
        return true;
    }
	return false;
}

private void touchProjectile(final float pX, final float pY) {
	// TODO Auto-generated method stub
	Player.setPosition(Player.getX(), Player.getY()-pY);
	
}
 
}
