package game.dudaonline.searchdog.entity;

import game.dudaonline.searchdog.graphics.ScreenOffset;
import game.dudaonline.searchdog.world.Level;
import game.dudaonline.searchdog.world.Tile;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Entity {

	protected double x, y;
	protected Bitmap sprite;
	protected Level level;
	protected boolean fixed = false;
	protected boolean removed = false;	
	protected final Random random = new Random();
	
	public Entity(){
	}
	
	public Entity(int x, int y, Bitmap sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void update() {
	}
	
	public void render(Canvas canvas) {
		
		if (!removed){
			int xPosition = (int) x * Tile.TILE_SIZE;
			int yPosition = (int) y * Tile.TILE_SIZE;
			if (fixed) canvas.drawBitmap(this.sprite, xPosition, yPosition, null);
			else {
				xPosition -= ScreenOffset.x;
				yPosition -= ScreenOffset.y;
				canvas.drawBitmap(this.sprite, xPosition, yPosition, null);
			}
		}
	}
	
	public void remove(){
		//remove from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public Bitmap getSprite(){
		return sprite;
	}
	
	public void init(Level level){
		this.level = level;
	}
}
