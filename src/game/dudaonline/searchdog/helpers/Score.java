package game.dudaonline.searchdog.helpers;

import java.util.ArrayList;

import android.util.Log;

public class Score {

	private static ArrayList<Score> highScores = new ArrayList<Score>();
	private static Score lastScore;
	
	private int numCoins = 0;
	private long duration = 0;
	private double score = 0;
	
	public Score(){
		
	}
	
	public Score(int numCoins, long duration){		
		this.numCoins = numCoins;
		this.duration = duration;
		this.score = calculateScore();
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
		return highScores.get(0).getScoreString();
	}
	
	public static void clearHighScores(){
		highScores.clear();
	}
	
	public static String printHeader(){
		return "Coins      Time    Score";
	}
	
	public String toString(){
		//     				 "Coins      Time    Score"
		//					 "    3    12m08s     23.4"
		//return String.format("%5d%6dm%02ds%9s", numCoins, Timer.getMinutes(duration), Timer.getSeconds(duration), getScoreString());
		//String coins = String.format("%-7d", numCoins);
		String coins = String.format("%d", numCoins);
		while (coins.length() < 4) coins = " " + coins;
		String times = String.format("%-10d' %02d\"", Timer.getMinutes(duration), Timer.getSeconds(duration));
		String score = String.format("%9s", getScoreString());
		
		return "  " + coins + times + score;
		//return String.format("%-7d%-10d' %02d\"%9s", numCoins, Timer.getMinutes(duration), Timer.getSeconds(duration), getScoreString());
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
		Log.d("Score", "Adding high Score " + this.toString());
		if (highScores.isEmpty()) {
			highScores.add(this);
			return;
		}
		boolean added = false;
		for (int i = 0; i < highScores.size(); i++) {
			if (this.score > highScores.get(i).score) {
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
	
	public static ArrayList<Score> getHighScores(){
		return highScores;
	}
	public static void setHighScores(ArrayList<Score> highScoresList){
		highScores = highScoresList;
	}

	public static Score getLastScore() {
		return lastScore;
	}

	
}
