package game.dudaonline.searchdog.overlays;

import game.dudaonline.searchdog.helpers.Score;
import game.dudaonline.searchdog.helpers.ScoreCountdown;
import game.dudaonline.searchdog.helpers.Timer;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameStats extends Overlay{

	private Paint p;
	private int currentLevel = 1;
	private int coinsCollected = 0;
	private int numCoins = 0;
	
	
	public GameStats(){
		setupPaints();
	}
	private void setupPaints() {
		p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(50);
		
	}
	@Override
	public void update() {
		
	}

	@Override
	public void render(Canvas canvas) {
		p.setColor(Color.BLACK);
		canvas.drawText("Level: " + currentLevel, 41, 61, p);
		p.setColor(Color.WHITE);
		canvas.drawText("Level: " + currentLevel, 40, 60, p);
		p.setColor(Color.BLACK);
		canvas.drawText("Coins: " + numCoins, 241, 61, p);
		p.setColor(Color.WHITE);
		canvas.drawText("Coins: " + numCoins, 240, 60, p);
		p.setColor(Color.BLACK);
		canvas.drawText(Timer.toTime(), 481, 61, p);
		p.setColor(Color.WHITE);
		canvas.drawText(Timer.toTime(), 480, 60, p);
		p.setColor(Color.BLACK);
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
		}
		
	}
	
	public void increaseLevel(){
		this.currentLevel++;
	}
	
	public void setLevel(int levelNum){
		this.currentLevel = levelNum;
	}
	
	public void increaseCoins(){
		this.numCoins++;
	}
	
	public void setCoins(int numCoins){
		this.numCoins = numCoins;
	}

	public void reset(){
		numCoins = 0;
		currentLevel = 1;
	}
}
