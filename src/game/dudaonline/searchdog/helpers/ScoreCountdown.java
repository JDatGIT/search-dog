package game.dudaonline.searchdog.helpers;

import java.util.ArrayList;

import android.util.Log;

public class ScoreCountdown {
	private static ArrayList<ScoreCountdown> highScores = new ArrayList<ScoreCountdown>();
	private static ScoreCountdown lastScore;
	
	private int numCoins = 0;
	private long duration = 0;
	private double score = 0;
	private int level = 1;
	
	public ScoreCountdown(int numCoins, long duration, int level){		
		this.numCoins = numCoins;
		this.duration = duration;
		this.score = calculateScore();
		this.level = level;
		addHighScore();
		trim();
		lastScore = this;
	}
	
	private double calculateScore() {
		if (duration == 0) return 0;
		double score = ((double)numCoins) / duration;
		score = score * 1000 * 60;
		return score;	
	}
	
	public String getScoreString(){			
		return String.format("%.1f", this.score);
	}
	
	public static String getHighScoreString(){
		if (highScores.isEmpty()) return "";
		return Integer.toString(highScores.get(0).getLevel());
	}
	
	public static void clearHighScores(){
		highScores.clear();
	}
	
	public static String printHeader(){
		return "Time      Coins    Rate   Level";
	}
	
	public String toString(){
		//     				 "Time      Coins    Rate"
		//					 "    3    12m08s     23.4"
		return String.format("%2d' %02d\"%5d%9s%5d", Timer.getMinutes(duration), Timer.getSeconds(duration), numCoins, getScoreString(),this.level);
	}
	
	public String getTimeString(){
		return String.format("%d' %02d\"", Timer.getMinutes(duration), Timer.getSeconds(duration));
	}

	private boolean isHighScore(){
		if (highScores.isEmpty()) return true;
		if (highScores.get(highScores.size()-1).score < this.score) return true;
		return false;
	}
	
	private void addHighScore() {
		Log.d("Score", "Adding high Countdown Score " + this.toString());
		if (highScores.isEmpty()) {
			highScores.add(this);
			return;
		}
		boolean added = false;
		for (int i = 0; i < highScores.size(); i++) {
			if (this.level > highScores.get(i).level) {
				highScores.add(i, this);
				added = true;
				break;
			}
		}
		if (!added) highScores.add(this);
		
	}
	
	private static void trim(){
		while (highScores.size() > 10) highScores.remove(highScores.size() - 1);
	}
	
	public int getLevel() {
		return level;
	}
	public int getNumCoins() {
		return numCoins;
	}
	public void setNumCoins(int numCoins) {
		this.numCoins = numCoins;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	public static ArrayList<ScoreCountdown> getHighScores(){
		return highScores;
	}
	public static void setHighScores(ArrayList<ScoreCountdown> highScoresList){
		highScores = highScoresList;
	}

	public static ScoreCountdown getLastScore() {
		return lastScore;
	}
}
