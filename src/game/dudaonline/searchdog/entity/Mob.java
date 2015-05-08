package game.dudaonline.searchdog.entity;

import game.dudaonline.searchdog.graphics.AnimatedSprite;
import game.dudaonline.searchdog.graphics.ScreenOffset;
import game.dudaonline.searchdog.world.Tile;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;


public abstract class Mob extends Entity{

	private static final String TAG = Mob.class.getSimpleName();
	
	protected boolean moving = false;
	protected boolean walking = false;
	protected boolean followPath = false;
	protected AnimatedSprite animatedSprite;
	
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}	
	protected Direction dir;	
	
	public void move(double xDistance, double yDistance) {
		if(xDistance != 0 && yDistance != 0) {
			move(xDistance,0);
			move(0,yDistance);
			return;
		}
		if (xDistance > 0) dir = Direction.RIGHT;
		if (yDistance > 0) dir = Direction.DOWN;
		if (xDistance < 0) dir = Direction.LEFT;
		if (yDistance < 0) dir = Direction.UP;
		
		while (xDistance != 0) {
			//check if xa is fractional
			if (Math.abs(xDistance) > 1) {
				if (!collision(abs(xDistance), yDistance)){
					this.x += abs(xDistance);
				} 
				xDistance -= abs(xDistance);
			} else {
				if (!collision(abs(xDistance), yDistance)){
					this.x += xDistance;
				}
				xDistance = 0;
			}
		}
		
		while (yDistance != 0) {
			//check if ya is fractional
			if (Math.abs(yDistance) > 1) {
				if (!collision(xDistance, abs(yDistance))){
					this.y += abs(yDistance);
				}
				yDistance -= abs(yDistance);
			} else {
				if (!collision(xDistance, abs(yDistance))){
					this.y += yDistance;
				}
				yDistance = 0;
			}
		}
	}
	
	private boolean collision(double newX, double newY) {
		boolean solid = false;
		
		double tempX = x;
		double tempY = y;
		for (int corner = 0; corner < 4; corner++){
			switch(corner) {
			case 0:
				tempX -= 20;
				tempY -= 10;
				break;
			case 1:
				tempX += 30;
				tempY -= 10;
				break;
			case 2:
				tempX -= 20;
				tempY += 40;
				break;
			case 3:
				tempX += 30;
				tempY += 40;
				break;
				default:
			}
			double xTile = ((tempX + newX) - corner % 2 * Tile.TILE_SIZE) / Tile.TILE_SIZE; 
			double yTile = ((tempY + newY) - corner / 2 * Tile.TILE_SIZE) / Tile.TILE_SIZE; 
			int ix = (int) Math.ceil(xTile);
			int iy = (int) Math.ceil(yTile);
			//if (corner % 2 == 0) ix = (int) Math.floor(xTile);
			//if (corner / 2 == 0) iy = (int) Math.floor(yTile);
			//Log.d(TAG, "Collision checking tile: " + ix + ", " + iy + ", " + corner);
			if (level.getTile(ix, iy).solid()) solid = true;
		}		
		
		//if(level.getTileByPixel((int)(x+newX), (int)(y+newY)).solid()) solid = true;
		
		return solid;
	}
	
	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}
	
	public RectF location(){
		//return new RectF((float)(x - Tile.TILE_SIZE/2), (float)(y -  Tile.TILE_SIZE/2),
		//				(float)(x +  Tile.TILE_SIZE/2), (float)(y +  Tile.TILE_SIZE/2));
		double xTemp = x - ScreenOffset.x;
		double yTemp = y - ScreenOffset.y;
		return new RectF((float)xTemp,(float)yTemp, (float)xTemp+Tile.TILE_SIZE,(float)yTemp+Tile.TILE_SIZE);
	}
	
	public abstract void update();
	public abstract void render(Canvas canvas);
}
