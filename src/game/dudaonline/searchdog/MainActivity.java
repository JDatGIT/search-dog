package game.dudaonline.searchdog;

import game.dudaonline.searchdog.helpers.Music;
import game.dudaonline.searchdog.helpers.Score;
import game.dudaonline.searchdog.helpers.ScoreCountdown;
import game.dudaonline.searchdog.helpers.SoundFX;
import game.dudaonline.searchdog.helpers.Timer;
import game.dudaonline.searchdog.util.SystemUiHider;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;


// New comment for git testing

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
	
	public static MainActivity instance; //mine
	public static final String PREFS_NAME = "HighScores"; //mine
	
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    
    private GameSurface ourScreen;
    private ZoomableGameSurface view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        

        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        	getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        //}
        
        setContentView(R.layout.activity_main);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        
        // MY ADDED CODE
        instance = this;
        final RelativeLayout background = (RelativeLayout)findViewById(R.id.rlBackground);
        ourScreen = new GameSurface(this);      
		background.addView(ourScreen);		

		// Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        float score = settings.getFloat("highscore", 0.0f);
        
        for (int i = 0; i < 10; i++) {
	    	int numCoins = settings.getInt("highscore" + i + "coins", 0);
	    	long duration = settings.getLong("highscore" + i + "time", 0);
	    	Score sc = new Score(numCoins,duration);
	    }	
        for (int i = 0; i < 10; i++) {
	    	int numCoins = settings.getInt("highscorecountdown" + i + "coins", 0);
	    	long duration = settings.getLong("highscorecountdown" + i + "time", 0);
	    	int level = settings.getInt("highscorecountdown" + i + "level", 0);
	    	ScoreCountdown sc = new ScoreCountdown(numCoins,duration,level);
	    }
		
		//END ADDED CODE
		
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }*/
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
	protected void onStop() {
		super.onStop();
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    
	    editor.clear();
	    for (int i = 0; i < Score.getHighScores().size(); i++) {
	    	editor.putInt("highscore" + i + "coins", Score.getHighScores().get(i).getNumCoins());
	    	editor.putLong("highscore" + i + "time", Score.getHighScores().get(i).getDuration());
	    }
	    for (int i = 0; i < ScoreCountdown.getHighScores().size(); i++) {
	    	editor.putInt("highscorecountdown" + i + "coins", ScoreCountdown.getHighScores().get(i).getNumCoins());
	    	editor.putLong("highscorecountdown" + i + "time", ScoreCountdown.getHighScores().get(i).getDuration());
	    	editor.putInt("highscorecountdown" + i + "level", ScoreCountdown.getHighScores().get(i).getLevel());
	    }
	    editor.commit();
	}

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
        	
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return true;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
        
    
    //MY ADDED CODE BELOW HERE
    @Override
	protected void onPause() {
		super.onPause();
		ourScreen.pause();
		Music.INSTANCE.pause();
		SoundFX.INSTANCE.pause();
		Timer.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ourScreen.resume();
		Music.INSTANCE.resume();
		SoundFX.INSTANCE.resume();
		Timer.start();
	}
    
}
