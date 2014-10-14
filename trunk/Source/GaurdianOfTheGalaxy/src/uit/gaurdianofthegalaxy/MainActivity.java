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
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends BaseGameActivity {

 
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
 //Khai báo biến sprite
 private Texture mTexturePlanets; // ctrl +spage để import thư viên :d
 private TextureRegion mTextureRegionPlanets;
 private LinkedList<AnimatedSprite> LLPlanets;
 private LinkedList<AnimatedSprite> LLToBeAddedPlanets;
 //Khai báo biến Meteors.
 private Texture mTextureMeteors;
 private TextureRegion mTextureRegionMeteors;
 private LinkedList<Sprite> targetLLMeteors;
 private LinkedList<Sprite> TargetsToBeAddedMeteors;
 // Khai báo biến va chạm
 private Texture mTextureVacham;
 private TiledTextureRegion mTiledTextureRegionVacham;
 private AnimatedSprite Vacham;

 //Khao báo biến music
 private Music backgroundMusic;
 private Sound Boom;

 
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
    this.mTextureRegionAirplanet = TextureRegionFactory.createTiledFromAsset(this.mTextureAirplanet, this, "animatesprite.png",0, 0, 1, 1);
    this.mEngine.getTextureManager().loadTexture(this.mTextureAirplanet);
   
    //====================================== Load sprite Planets ==================================================//
    this.mTexturePlanets = new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionPlanets = TextureRegionFactory.createFromAsset(this.mTexturePlanets, this, "planets1.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets);
    
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
  autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, CAMERA_HEIGHT - 
    this.mParallaxLayerBack.getHeight()
    , this.mParallaxLayerBack)));
  scene.setBackground(autoParallaxBackground);
  //========================================*Load sprite animate*========================================//
    
  /*final AnimatedSprite animatedSprite =  new AnimatedSprite(0, CAMERA_HEIGHT/2, mTextureRegionAirplanet);
  scene.attachChild(animatedSprite);
  animatedSprite.setScale(1);//tăng kích thước sprite x lần
  //animatedSprite.animate(new long[] { 200, 200, 200 },0,2, true);*/
  //Vị trí khởi tạo AnimatedSprite
  final int centerX = 0;
  final int centerY = (CAMERA_HEIGHT - this.mTextureRegionAirplanet.getHeight()) / 2;
  //Tạo đối tượng AnimatedSprite
  final AnimatedSprite face = new AnimatedSprite(centerX, centerY, this.mTextureRegionAirplanet);

  final PhysicsHandler physicsHandler = new PhysicsHandler(face);
  face.registerUpdateHandler(physicsHandler);
  scene.attachChild(face);
  
  //========================================*Load sprite Planets*=========================================//
  final Sprite SpritePlanets =  new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionPlanets);
  scene.attachChild(SpritePlanets);
  SpritePlanets.setScale(1);
  //Move sprite Planets
  MoveXModifier modifierPlanets = new MoveXModifier(80, (float) (CAMERA_WIDTH/2), 0-SpritePlanets.getX());
  SpritePlanets.registerEntityModifier(modifierPlanets);
  
  
  
  //======================================= Load Va chạm =================================================
  Vacham = new AnimatedSprite((float) (-CAMERA_WIDTH), 100, this.mTiledTextureRegionVacham);
	scene.attachChild(Vacham);
	Vacham.setScale(2f);
	Vacham.setRotation(0);
	Vacham.animate(new long[] { 200, 200, 200,200},0, 3, true);
	Vacham.setVisible(false);
  //=================================== * Meteors and move * ===========================================================//
	//final Sprite SpriteMeteors =  new Sprite(-CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionMeteors);
	//scene.attachChild(SpriteMeteors);
	//SpriteMeteors.setScale((float) (0.2));
	targetLLMeteors = new LinkedList();
	TargetsToBeAddedMeteors = new LinkedList();
	IUpdateHandler detect = new IUpdateHandler() {
	    @Override
	    public void reset() {
	    	
	    }
	    //int dem=0;
	    public void onUpdate(float pSecondsElapsed) { 
	    	try {
	    		
				Random rand = new Random();
			  int minY = 5;
			  int maxY = CAMERA_HEIGHT- mTextureRegionMeteors.getWidth();
			  int rangeY = maxY - minY;
			  int y = rand.nextInt(rangeY) + minY;
			  double x = CAMERA_WIDTH;
			  
			  final Sprite SpriteMeteors = new Sprite((float) x, y, mTextureRegionMeteors);
			 // if (dem <5){
			  scene.attachChild(SpriteMeteors);
			  SpriteMeteors.setScale((float) (0.2));//}
	 
			  int minDuration = 3;// tốc độ vật bay chậm nhất
			  int maxDuration = 5;// tốc độ vật bay nhanh nhất
			  int rangeDuration = maxDuration - minDuration;
			  int actualDuration = rand.nextInt(rangeDuration) + minDuration;
			  MoveXModifier mod = new MoveXModifier(actualDuration, SpriteMeteors.getX(), 0-SpriteMeteors.getX());
			  Thread.sleep(0);
	        Iterator<Sprite> targets = targetLLMeteors.iterator();
	        Sprite _target;
	 
	        while (targets.hasNext()) {
	            _target = targets.next();
	           // dem++;
	          if (_target.collidesWith(face)) {
	        	  	Vacham.setVisible(true);
			  		Vacham.setPosition(face.getX(), face.getY());
			  		//removeSprite(_target, targets);
			  		scene.detachChild(face);
			  		Boom.play();
			  		Vacham.setVisible(false);
            }
 
	        }
	        SpriteMeteors.registerEntityModifier(mod);
	        TargetsToBeAddedMeteors.add(SpriteMeteors);	
	        targetLLMeteors.addAll(TargetsToBeAddedMeteors);
	       // TargetsToBeAddedMeteors.clear();
	   } 
		   catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}}
	    };
	    	scene.registerUpdateHandler(detect);
  
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
 
}
