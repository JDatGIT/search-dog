package game.dudaonline.searchdog.overlays;

import game.dudaonline.searchdog.MainActivity;
import game.dudaonline.searchdog.helpers.Score;
import game.dudaonline.searchdog.helpers.ScoreCountdown;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

public class GameMenu extends Overlay{
	
	private static final int TITLE_FONT_SIZE = 60;
	
	private int alpha = 0;
	private boolean reverse = false;
	private int level = 1;
	private boolean gameOver = false;
	private boolean welcome = true;
	
	private Score lastScore;
	private ScoreCountdown lastScoreCountdown;
	
	private boolean countDownMode = false;
	
	private Paint p = new Paint();
		
	public GameMenu() {
		setupPaints();
	}

	public void update(){
		if (reverse) alpha--; else alpha++;
		if (alpha>150) reverse = true;
		if (alpha<100) reverse = false;
		//if (level>GameSurface.MAX_LEVEL) gameOver = true;
	}
	
	public void render(Canvas canvas){
		this.screenWidth = canvas.getWidth();
		this.screenHeight = canvas.getHeight();
		p.setTextSize(TITLE_FONT_SIZE);
		p.setColor(Color.RED);
		p.setAlpha(alpha);
		
		if (welcome) {
			showWelcome(canvas);
			if (countDownMode) showHighScoresCountdown(canvas);
			else showHighScores(canvas);
		} else if (!gameOver) {
			showNextLevel(canvas);
		} else {
			showGameOver(canvas);
			if (countDownMode) showHighScoresCountdown(canvas);
			else showHighScores(canvas);		
		}
		
	}
	
	private void setupPaints() {
		//kenpixel_future_square
		Typeface tf = Typeface.createFromAsset(MainActivity.instance.getAssets(), "kenpixel_future_square.ttf");
		p = new Paint();
		p.setTypeface(tf);
		p.setColor(Color.RED);
		p.setAlpha(alpha);
		//canvas.drawRect(0+10, 0+10, screenWidth-10, screenHeight-10, p);
		p.setTextSize(TITLE_FONT_SIZE);		
		p.setTextAlign(Align.CENTER);
	}
	
	private void showWelcome(Canvas canvas){
		p.setTextSize(TITLE_FONT_SIZE-15);
		canvas.drawText("Welcome to", screenWidth/2, screenHeight/2 - 300, p);
		p.setTextSize(TITLE_FONT_SIZE);
		canvas.drawText("SEARCH DOG", screenWidth/2, screenHeight/2 - 200, p);
		p.setTextSize(TITLE_FONT_SIZE-25);
		canvas.drawText("Click here to start!", screenWidth/2, screenHeight/2 - 100, p);
		p.setTextSize(TITLE_FONT_SIZE-42);
		canvas.drawText("Tip: Double-tap then long press to end game.", screenWidth/2, screenHeight - 100, p);
		canvas.drawText("Tip: Double-tap 3 times to reset all scores.", screenWidth/2, screenHeight - 80, p);
	}
	
	private void showNextLevel(Canvas canvas){
		p.setTextSize(TITLE_FONT_SIZE);	
		p.setColor(Color.BLACK);
		//p.setAlpha(alpha);
		canvas.drawText("Level " + this.level, screenWidth/2, screenHeight/2 - 97, p);			
		p.setColor(Color.RED);
		p.setAlpha(alpha);
		canvas.drawText("Level " + this.level, screenWidth/2 - 3, screenHeight/2 - 100, p);
		p.setColor(Color.BLACK);
		//p.setAlpha(alpha);
		canvas.drawText("Start moving!!", screenWidth/2, screenHeight/2 + 3, p);
		p.setColor(Color.RED);
		p.setAlpha(alpha);
		canvas.drawText("Start moving!!", screenWidth/2 - 3, screenHeight/2, p);
	}
	
	private void showGameOver(Canvas canvas){
		p.setARGB(150, 200, 200, 0);
	    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), p);
		p.setColor(Color.BLACK);
		//p.setAlpha(alpha);
		canvas.drawText("Game Over!", screenWidth/2, screenHeight/2 - 147, p);
		p.setColor(Color.RED);
		p.setAlpha(alpha);
		canvas.drawText("Game Over!", screenWidth/2 - 3, screenHeight/2 - 150, p);
		
		if (lastScore != null && !countDownMode){
			p.setTextSize(TITLE_FONT_SIZE-25);
			p.setColor(Color.BLACK);
			//p.setAlpha(alpha);
			canvas.drawText("Next time can you beat", screenWidth/2, screenHeight/2 - 49, p);
			canvas.drawText(lastScore.getScoreString() + " coins/minute?", screenWidth/2, screenHeight/2 - 9, p);
			p.setColor(Color.RED);
			p.setAlpha(alpha);
			canvas.drawText("Next time can you beat", screenWidth/2 - 1, screenHeight/2 - 50, p);
			canvas.drawText(lastScore.getScoreString() + " coins/minute?", screenWidth/2 - 1, screenHeight/2 - 10, p);
		}
		
		if (lastScoreCountdown != null && countDownMode){
			p.setTextSize(TITLE_FONT_SIZE-25);
			p.setColor(Color.BLACK);
			//p.setAlpha(alpha);
			canvas.drawText("Next time can you pass", screenWidth/2, screenHeight/2 - 49, p);
			canvas.drawText("level " + lastScoreCountdown.getLevel() + " ?", screenWidth/2, screenHeight/2 - 9, p);
			p.setColor(Color.RED);
			p.setAlpha(alpha);
			canvas.drawText("Next time can you pass", screenWidth/2 - 1, screenHeight/2 - 50, p);
			canvas.drawText("level " + lastScoreCountdown.getLevel() + " ?", screenWidth/2 - 1, screenHeight/2 - 10, p);
		}
	}
	
	private void showHighScores(Canvas canvas){
		p.setTextSize(TITLE_FONT_SIZE-25);
		p.setColor(Color.WHITE);	
		canvas.drawText("Race Mode", screenWidth/2 - 149, screenHeight/2 + 51, p);
		p.setColor(Color.BLACK);
		canvas.drawText("Race Mode", screenWidth/2 - 150, screenHeight/2 + 50, p);
		
		p.setTextSize(TITLE_FONT_SIZE-35);
		p.setColor(Color.WHITE);			
		p.setTextAlign(Align.RIGHT);
		canvas.drawText("#",        screenWidth/2 - 249, screenHeight/2 + 101, p);
		canvas.drawText("COINS",    screenWidth/2 -  99, screenHeight/2 + 101, p);
		canvas.drawText("TIME",     screenWidth/2 +  51, screenHeight/2 + 101, p);
		canvas.drawText("SCORE",    screenWidth/2 + 201, screenHeight/2 + 101, p);
		p.setColor(Color.BLACK);
		canvas.drawText("#",        screenWidth/2 - 250, screenHeight/2 + 100, p);
		canvas.drawText("COINS",    screenWidth/2 - 100, screenHeight/2 + 100, p);
		canvas.drawText("TIME",     screenWidth/2 +  50, screenHeight/2 + 100, p);
		canvas.drawText("SCORE",    screenWidth/2 + 200, screenHeight/2 + 100, p);
		for (int i = 0; i < Score.getHighScores().size(); i++) {
			Score s = Score.getHighScores().get(i);
			//shadow
			p.setColor(Color.WHITE);
			canvas.drawText((i+1) + ".",                        screenWidth/2 - 249, screenHeight/2 + 131 + i * 30, p);
			canvas.drawText(Integer.toString(s.getNumCoins()),  screenWidth/2 -  99, screenHeight/2 + 131 + i * 30, p);
			canvas.drawText(s.getTimeString(),                  screenWidth/2 +  51, screenHeight/2 + 131 + i * 30, p);
			canvas.drawText(s.getScoreString(),                 screenWidth/2 + 201, screenHeight/2 + 131 + i * 30, p);
			p.setColor(Color.BLACK);
			//text
			if (Score.getHighScores().get(i).equals(Score.getLastScore())) p.setColor(Color.YELLOW);
			canvas.drawText((i+1) + ".",                        screenWidth/2 - 250, screenHeight/2 + 130 + i * 30, p);
			canvas.drawText(Integer.toString(s.getNumCoins()),  screenWidth/2 - 100, screenHeight/2 + 130 + i * 30, p);
			canvas.drawText(s.getTimeString(),                  screenWidth/2 +  50, screenHeight/2 + 130 + i * 30, p);
			canvas.drawText(s.getScoreString(),                 screenWidth/2 + 200, screenHeight/2 + 130 + i * 30, p);
			p.setColor(Color.BLACK);
		}
		p.setTextAlign(Align.CENTER);
	}
	
	private void showHighScoresCountdown(Canvas canvas){
		p.setTextSize(TITLE_FONT_SIZE-25);
		p.setColor(Color.WHITE);	
		canvas.drawText("Countdown Mode", screenWidth/2 - 149, screenHeight/2 + 51, p);
		p.setColor(Color.BLACK);
		canvas.drawText("Countdown Mode", screenWidth/2 - 150, screenHeight/2 + 50, p);
		
		p.setTextSize(TITLE_FONT_SIZE-35);
		p.setColor(Color.WHITE);			
		p.setTextAlign(Align.RIGHT);
		canvas.drawText("#",        screenWidth/2 - 249, screenHeight/2 + 101, p);
		canvas.drawText("TIME",     screenWidth/2 -  99, screenHeight/2 + 101, p);
		canvas.drawText("COINS",    screenWidth/2 +  51, screenHeight/2 + 101, p);
		canvas.drawText("RATE",     screenWidth/2 + 201, screenHeight/2 + 101, p);
		canvas.drawText("LVL",    screenWidth/2 + 301, screenHeight/2 + 101, p);
		p.setColor(Color.BLACK);
		canvas.drawText("#",        screenWidth/2 - 250, screenHeight/2 + 100, p);
		canvas.drawText("TIME",     screenWidth/2 - 100, screenHeight/2 + 100, p);
		canvas.drawText("COINS",    screenWidth/2 +  50, screenHeight/2 + 100, p);
		canvas.drawText("RATE",     screenWidth/2 + 200, screenHeight/2 + 100, p);
		canvas.drawText("LVL",    screenWidth/2 + 300, screenHeight/2 + 100, p);
		
		for (int i = 0; i < ScoreCountdown.getHighScores().size(); i++) {
			ScoreCountdown s = ScoreCountdown.getHighScores().get(i);
			//shadow
			p.setColor(Color.WHITE);
			canvas.drawText((i+1) + ".",                        screenWidth/2 - 249, screenHeight/2 + 131 + i * 30, p);
			canvas.drawText(s.getTimeString(),  				screenWidth/2 -  99, screenHeight/2 + 131 + i * 30, p);
			canvas.drawText(Integer.toString(s.getNumCoins()),  screenWidth/2 +  51, screenHeight/2 + 131 + i * 30, p);
			canvas.drawText(s.getScoreString(),                 screenWidth/2 + 201, screenHeight/2 + 131 + i * 30, p);
			canvas.drawText(Integer.toString(s.getLevel()),     screenWidth/2 + 301, screenHeight/2 + 131 + i * 30, p);
			p.setColor(Color.BLACK);
			//text
			if (ScoreCountdown.getHighScores().get(i).equals(ScoreCountdown.getLastScore())) p.setColor(Color.YELLOW);
			canvas.drawText((i+1) + ".",                        screenWidth/2 - 250, screenHeight/2 + 130 + i * 30, p);
			canvas.drawText(s.getTimeString(),  				screenWidth/2 - 100, screenHeight/2 + 130 + i * 30, p);
			canvas.drawText(Integer.toString(s.getNumCoins()),  screenWidth/2 +  50, screenHeight/2 + 130 + i * 30, p);
			canvas.drawText(s.getScoreString(),                 screenWidth/2 + 200, screenHeight/2 + 130 + i * 30, p);
			canvas.drawText(Integer.toString(s.getLevel()),     screenWidth/2 + 300, screenHeight/2 + 130 + i * 30, p);
			p.setColor(Color.BLACK);
		}
		
		
		
		p.setTextAlign(Align.CENTER);
	}
	
	public void increaseLevel(){
		this.level++;
	}
	
	public void setLevel(int levelNum){
		this.level = levelNum;
	}
	
	public void setGameOver(boolean isOver){
		this.gameOver = isOver;
	}
	
	public void setLastScore(Score score){
		this.lastScore = score;
	}
	
	public void setLastScoreCountdown(ScoreCountdown score){
		this.lastScoreCountdown = score;
	}

	public void setWelcome(boolean welcome) {
		this.welcome = welcome;
	}
	
	public void setCountDownMode(boolean isCountDownMode){
		this.countDownMode = isCountDownMode;
	}
}
