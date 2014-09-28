package uit.gaurdianofthegalaxy;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
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

import android.util.DisplayMetrics;

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
 //Khai báo biến Meteors.
private Texture mTextureMeteors;
private TextureRegion mTextureRegionMeteors;
 //Khao báo biến music
 private Music backgroundMusic;

 
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
 
  
  this.mAutoParallaxBackgroundTexture = new Texture(2048, 2048, TextureOptions.DEFAULT); 
  this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture
    , this, "gfx/background1.jpg", 0, 0);
  this.mEngine.getTextureManager().loadTextures( this.mAutoParallaxBackgroundTexture);
  
    TextureRegionFactory.setAssetBasePath("gfx/");
    // load sprite animate
    this.mTextureAirplanet = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    this.mTextureRegionAirplanet = TextureRegionFactory.createTiledFromAsset(this.mTextureAirplanet, this, "airplanet.png",0, 0, 4, 4);
    this.mEngine.getTextureManager().loadTexture(this.mTextureAirplanet);
    //Load sprite
    this.mTexturePlanets = new Texture(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionPlanets = TextureRegionFactory.createFromAsset(this.mTexturePlanets, this, "planets1.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTexturePlanets);
    // Load Meteors
    this.mTextureMeteors = new Texture(512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    mTextureRegionMeteors = TextureRegionFactory.createFromAsset(this.mTextureMeteors, this, "meteors1.png", 0, 0);
    this.mEngine.getTextureManager().loadTexture(this.mTextureMeteors);
        
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
 }

 @Override
 public Scene onLoadScene() {
  
	 ///Load background
  this.mEngine.registerUpdateHandler(new FPSLogger());
  final Scene scene = new Scene();
  final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
  
  autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, CAMERA_HEIGHT - 
    this.mParallaxLayerBack.getHeight()
    , this.mParallaxLayerBack)));
  //Load sprite animate
  
  scene.setBackground(autoParallaxBackground);
  final AnimatedSprite animatedSprite =  new AnimatedSprite(0, CAMERA_HEIGHT/2, mTextureRegionAirplanet);
  scene.attachChild(animatedSprite);
  animatedSprite.setScale((float) 1);// tăng kích thước sprite x lần
  //animatedSprite.animate(new long[] { 200, 200, 200 },0,2, true);
  //Load sprite
  final Sprite SpritePlanets =  new Sprite(CAMERA_WIDTH, CAMERA_HEIGHT/2, mTextureRegionPlanets);
  scene.attachChild(SpritePlanets);
  SpritePlanets.setScale(1);
  //SpritePlanets.setPosition(pX, pY)
  /// Meteors
  final Sprite SpriteMeteors =  new Sprite((float) (CAMERA_WIDTH/1.5), CAMERA_HEIGHT/8, mTextureRegionMeteors);
  scene.attachChild(SpriteMeteors);
  SpriteMeteors.setScale((float) (0.2));
  //Load music
  this.backgroundMusic.play();
  
  
  return scene;
 }

@Override
 public void onLoadComplete() {
   
 }



}
