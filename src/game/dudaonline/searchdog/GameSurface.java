package game.dudaonline.searchdog;

import game.dudaonline.searchdog.entity.Player;
import game.dudaonline.searchdog.helpers.Music;
import game.dudaonline.searchdog.helpers.Score;
import game.dudaonline.searchdog.helpers.ScoreCountdown;
import game.dudaonline.searchdog.helpers.SoundFX;
import game.dudaonline.searchdog.helpers.Timer;
import game.dudaonline.searchdog.overlays.GameMenu;
import game.dudaonline.searchdog.overlays.GameStats;
import game.dudaonline.searchdog.overlays.ModeButtons;
import game.dudaonline.searchdog.world.Level;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GameSurface.class.getSimpleName();	
	
	public static final int MAX_LEVEL = 10;
	public static final int START_LEVEL_SIZE = 6;
	public static final int COIN_TIME_INCREASE = 1;
	
	private SurfaceHolder ourHolder;
	private GameThread thread = null;
	private Context c;
	
	private boolean canDraw = false;
	private boolean configChanged = false;		
	private RectF surfaceBounds;
	
	private Level level;
	private Player player;
	private GameMenu menu;
	private int currentLevel = 1;
	private GameStats stats;
	
	private ModeButtons buttons;
	
	private boolean initialized = false;
	
	private String eventCoords = "";
	private int lastClickedTileX = 0;
	private int lastClickedTileY = 0;
	private boolean fingerReleasedOnce = false;
	
	private int doubleTap = 0; //3 doubletaps in a row will reset the high score
	
	private enum GameState {
		STARTSCREEN, PLAYING, LEVELCHANGE, GAMEOVER
	}
		private GameState currentState;
	
	public GameSurface(Context context) {
		super(context);
		this.c = context;
		ourHolder = getHolder();
		ourHolder.addCallback(this);
		thread = new GameThread(ourHolder, this);
		setFocusable(true);
		gestureDetector = new GestureDetector(getContext(), gestureListener);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}

	private void initialSetup() {
		if (!initialized) {
			player = new Player(1,1,c);
			//player = new Player(10,10,c);
			
			player.setScreenCenter(surfaceBounds);
			
			level = new Level(c,START_LEVEL_SIZE,START_LEVEL_SIZE); //loads test map //Level(40,40);
			//level = new Level(c);
			level.setScreenCenter(surfaceBounds);
			player.init(level); //add player to level
			menu = new GameMenu();
			stats = new GameStats();
			
			buttons = new ModeButtons(c);
			buttons.setShowing(true);
			
			Music.INSTANCE.play("game2.mp3", true);
			Music.INSTANCE.volume(1f);
			SoundFX.INSTANCE.load("coin.mp3");
			
			Timer.setCountdownMode(buttons.isCountDownMode());
			Timer.setTimeLimit(5);
			Timer.pause();
			
			currentState = GameState.STARTSCREEN;
			initialized = true;
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		surfaceBounds = new RectF(0, 0, getWidth(), getHeight());
		if(!canDraw) {
			initialSetup();
		}
		canDraw = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		surfaceBounds = new RectF(0, 0, getWidth(), getHeight());
		if (configChanged) {
			player.setScreenCenter(surfaceBounds);
			level.setScreenCenter(surfaceBounds);
			configChanged = false;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		canDraw = false;
	}
	
	public boolean readyToDraw(){
		return canDraw;
	}
	
	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		//newConfig.orientation		
		configChanged = true;
		super.onConfigurationChanged(newConfig);
	}
	
	public void pause() {
		thread.setRunning(false);
		while(true){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}
	
	public void resume() {
		thread = new GameThread(ourHolder, this);
		thread.setRunning(true);
		thread.start();	
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		eventCoords = "Touched at: " + event.getX() + ", " + event.getY();		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} //gives a Framerate of about 20 FPS		
		
		// Let the ScaleGestureDetector inspect all events.
	    mScaleDetector.onTouchEvent(event);
		
		//Log.d(TAG, "+ onTouchEvent(event:" + event + ")");
        gestureDetector.onTouchEvent(event);
        //Log.d(TAG, "- onTouchEvent()");		
		
        float x = event.getX();
		float y = event.getY();

        if (buttons.isShowing()) {
			buttons.setTouchedXY(x, y);
			Timer.setCountdownMode(buttons.isCountDownMode());
			menu.setCountDownMode(buttons.isCountDownMode());
		}
        
        if (currentState == GameState.STARTSCREEN && y < 300) return true;
        
		if (player.getXTile() != 1 || player.getYTile() != 1) Timer.start();	
		//else Timer.pause();
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if (currentState == GameState.PLAYING) player.screenTouched((int)x, (int)y);	
			if (currentState == GameState.GAMEOVER || currentState == GameState.LEVELCHANGE)  fingerReleasedOnce = true; //if you just pressed down you must have released your finger, right?
			break;
		case MotionEvent.ACTION_UP:
			switch (currentState) {
			case STARTSCREEN:
				menu.setWelcome(false);
				buttons.setShowing(false);
				currentState = GameState.PLAYING;
				break;
			case PLAYING:
				player.screenNotTouched();
				break;
			case LEVELCHANGE:
				if (fingerReleasedOnce) {
					currentState = GameState.PLAYING;
					fingerReleasedOnce = false;
				} //else fingerReleasedOnce = true;
				break;
			case GAMEOVER:				
				if (fingerReleasedOnce) {
					newGame();
					currentState = GameState.STARTSCREEN;
					fingerReleasedOnce = false;
				} //else fingerReleasedOnce = true;
				break;
			default: break;
			}			
			break;		
		case MotionEvent.ACTION_MOVE:
			if (currentState == GameState.PLAYING) player.screenTouched((int)x, (int)y);
			break;
		}	
		return true;//super.onTouchEvent(event);
	}

	private void newGame() {
		currentState = GameState.STARTSCREEN;
		menu.setGameOver(false);
		menu.setWelcome(true);
		currentLevel = 1;		
		menu.setLevel(1);
		stats.reset();
		level.reset();
		player.setNumCoins(0);		
		Timer.reset();		
		Timer.setTimeLimit(5); //currentLevel*START_LEVEL_SIZE);
		buttons.setShowing(true);
		player.setLocation(1, 1);
	}
	
	private void nextLevel(){
		currentState = GameState.LEVELCHANGE;
		Timer.pause();		
		currentLevel++;
		menu.increaseLevel();
		stats.increaseLevel();
		menu.setGameOver(false);
		player.setLocation(1, 1);
	}
	
	private void gameOver(){
		currentState = GameState.GAMEOVER;
		Timer.pause();
		menu.setGameOver(true);
		if (Timer.isCountdownMode()) menu.setLastScoreCountdown(new ScoreCountdown(player.getNumCoins(), Timer.getDuration(), currentLevel));
		else menu.setLastScore(new Score(player.getNumCoins(), Timer.getDuration()));
		//TODO: adjust score for countdown mode
	}

	
	public void update() {
		
		switch (currentState) {
		case STARTSCREEN:
			player.screenNotTouched();
			break;
		case PLAYING:
			if(Timer.isTimeUp() && Timer.isCountdownMode()) gameOver();
			if (level.getLevelNumber() > currentLevel) nextLevel();
			break;
		case LEVELCHANGE:
			player.screenNotTouched();
			if (currentLevel > MAX_LEVEL && !Timer.isCountdownMode()) gameOver();
			break;
		case GAMEOVER:
			player.screenNotTouched();
			break;
		default: break;
		}
		level.update();
		player.update();
		stats.setCoins(player.getNumCoins());
		menu.update();
		buttons.update();
	}

	public void render(Canvas canvas) {		
		canvas.drawRGB(0, 0, 0); //black
		canvas.save();
	    canvas.scale(mScaleFactor, mScaleFactor, surfaceBounds.width()/2, surfaceBounds.height()/2);
	    
		double xScroll = player.getX() - surfaceBounds.width()/2;
		double yScroll = player.getY() - surfaceBounds.height()/2;
		
		//render underlying level tiles
		level.render((int)xScroll, (int)yScroll, canvas);
		
		
		player.render(canvas);	
				
		canvas.restore();
		
		if (currentState != GameState.PLAYING) menu.render(canvas);
		if (currentState == GameState.STARTSCREEN) buttons.render(canvas);
		
		stats.render(canvas);
		
		
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(50);
		
		/*
		canvas.drawText("FPS: " + thread.getFrames(), 200, 100, p);
		canvas.drawText("Player tile: " + player.getXTile() + ", " + player.getYTile(), 200, 150, p);
		canvas.drawText(eventCoords, 200, 200, p);
		canvas.drawText("Last tile: " + lastClickedTileX + "," + lastClickedTileY, 200, 250, p);
		canvas.drawText("Scale: " + mScaleFactor, 200, 300, p);
		*/
		
		//things to keep
		/*
		p.setColor(Color.BLACK);
		canvas.drawText("Level: " + currentLevel, 41, 61, p);
		p.setColor(Color.WHITE);
		canvas.drawText("Level: " + currentLevel, 40, 60, p);
		p.setColor(Color.BLACK);
		canvas.drawText("Coins: " + player.getNumCoins(), 241, 61, p);
		p.setColor(Color.WHITE);
		canvas.drawText("Coins: " + player.getNumCoins(), 240, 60, p);
		p.setColor(Color.BLACK);
		canvas.drawText(Timer.toTime(), 481, 61, p);
		p.setColor(Color.WHITE);
		canvas.drawText(Timer.toTime(), 480, 60, p);
		p.setColor(Color.BLACK);
		//canvas.drawText("High Score: " + Timer.getHighScoreString(), 41, 111, p);
		if(Timer.isCountdownMode()) {
			canvas.drawText("High Level: " + ScoreCountdown.getHighScoreString(), 41, 111, p);
			p.setColor(Color.WHITE);
			canvas.drawText("High Level: " + ScoreCountdown.getHighScoreString(), 40, 110, p);
			p.setColor(Color.BLACK);
		} else {
			canvas.drawText("High Score: " + Score.getHighScoreString(), 41, 111, p);
			p.setColor(Color.WHITE);
			canvas.drawText("High Score: " + Score.getHighScoreString(), 40, 110, p);
			p.setColor(Color.BLACK);
		}*/
		
		//canvas.drawText("Player: " + player.getX() + ", " + player.getY(), 200, 300, p);
		//p.setAlpha(120);
		//canvas.drawRect(player.location(), p);
	}

	private GestureDetector gestureDetector;// = new GestureDetector(getContext(), gestureListener);
	private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        private static final String TAG2 = "GestureListener";

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            //Log.d(TAG, "+ onSingleTapConfirmed(event:" + event + ")");
            //singleTapDetected = true;
        	//player.setPath(level.getTileX(event.getX(), mScaleFactor), level.getTileY(event.getY(), mScaleFactor));
        	if(currentState != GameState.PLAYING) return true;
        	
        	lastClickedTileX = level.getTileX(event.getX(), mScaleFactor, player.getXTile());
            lastClickedTileY = level.getTileY(event.getY(), mScaleFactor, player.getYTile());
        	
            Log.d(TAG + "." + TAG2, "- onSingleTapConfirmed() - Attempting path to: " + lastClickedTileX + "," + lastClickedTileY);
        	player.setPath(lastClickedTileX, lastClickedTileY);  
        	doubleTap = 0;
            return true;
        }

        @Override
		public void onLongPress(MotionEvent event) {
			// TODO Auto-generated method stub
			//super.onLongPress(event);
        	if (doubleTap == 1) gameOver();
        	
		}

		@Override
        public boolean onDoubleTap(MotionEvent event) {
            //Log.d(TAG, "+ onDoubleTap(event:" + event + ")");
            //doubleTapDetected = true;
            Log.d(TAG + "." + TAG2, "- onDoubleTap()");
            doubleTap++;
            if (doubleTap >= 3) {
            	//Timer.highScore = 0.0f;
            	Score.clearHighScores();
            	ScoreCountdown.clearHighScores();            	
            	doubleTap = 0;
            }
            return true;
        }
    };

    

    
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;    
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
		    mScaleFactor *= detector.getScaleFactor();
		    Log.d(TAG, "scaling has been detected");
		    // Don't let the object get too small or too large.
		    mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 2.0f)); //1f was 0.1f
		
		    invalidate();
		    return true;
		}
	}
    
}
