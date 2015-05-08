package game.dudaonline.searchdog.helpers;

import java.util.concurrent.TimeUnit;

import android.util.Log;

public class Timer {

	private static long startTime = 0;
	private static long endTime = 0;
	private static long duration = 1; //to avoid division by 0 errors
	
	private final static float TIME_LIMIT_FACTOR = 1;
	private static long timeLimit = 0;
	
	private static boolean countdownMode = false;
	
	private static boolean paused = true;
	
	public Timer(){
		
	}
	
	public static void start(){
		if (paused) startTime = System.currentTimeMillis();
		paused = false;
	}
	
	public static void pause(){
		if (!paused) {
			paused = true;
			duration += System.currentTimeMillis() - startTime;
		}
	}
	
	public static void reset(){
		startTime = 0;
		endTime = 0;
		duration = 1;
		paused = true;
	}
	
	public static int getMinutes(){
		long elapsedTime = duration;
		if (!paused) elapsedTime += System.currentTimeMillis() - startTime;
		elapsedTime = !isCountdownMode() ? elapsedTime : timeLimit - elapsedTime;
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
		return minutes;
	}
	public static int getMinutes(long duration){
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(duration);
		return minutes;
	}
	
	public static int getSeconds(){
		long elapsedTime = duration;
		if (!paused) elapsedTime += System.currentTimeMillis() - startTime;
		elapsedTime = !isCountdownMode() ? elapsedTime : timeLimit - elapsedTime;
		int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
		return seconds % 60;
	}
	
	public static int getSeconds(long duration){
		int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(duration);
		return seconds % 60;
	}
	
	public static int getHundredths(){
		long elapsedTime = duration;
		if (!paused) elapsedTime += System.currentTimeMillis() - startTime;
		int hundredths = (int) elapsedTime % 100;
		return hundredths;
	}
	
	public static String toTime(){
		return String.format("%02d m %02d s", getMinutes(), getSeconds());
	}
	
	public static long getDuration(){
		return duration;
	}
	
	public static void setTimeLimit(int seconds) {
		timeLimit = (long) (seconds * TIME_LIMIT_FACTOR * 1000) + 100; //add 100 to give slight extra
	}
	
	public static void increaseTimeLimit(int seconds) {
		timeLimit += seconds * 1000; 
	}
	
	public static boolean isTimeUp(){
		long elapsedTime = duration;
		if (!paused) elapsedTime += System.currentTimeMillis() - startTime;
		return (timeLimit - elapsedTime) < 0 ? true : false;
	}

	public static boolean isCountdownMode() {
		return countdownMode;
	}

	public static void setCountdownMode(boolean countdownMode) {
		Timer.countdownMode = countdownMode;
	}
	
	
	/*
	private static double lastScore = 0;
	public static float highScore = 0; 
	public static void setScore(int numCoins){		
		//if (numCoins != 0) {
			long elapsedTime = duration;
			if (!paused) elapsedTime += System.currentTimeMillis() - startTime;
			Log.d("time", "" + numCoins + " / " + elapsedTime);
			double score = ((double)numCoins) / elapsedTime;
			score = score * 1000 * 60;
			lastScore = score;
			if (lastScore > highScore) highScore = (float) lastScore;
		//}
	}
	
	public static String getScoreString(){			
		return String.format("%.1f", lastScore);
	}
	
	public static String getHighScoreString(){			
		return String.format("%.1f", highScore);
	}
	*/
}
