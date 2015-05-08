package game.dudaonline.searchdog.entity;


import game.dudaonline.searchdog.GameSurface;
import game.dudaonline.searchdog.R;
import game.dudaonline.searchdog.graphics.Animated8DirectionSprite;
import game.dudaonline.searchdog.graphics.AnimatedSprite;
import game.dudaonline.searchdog.graphics.ScreenOffset;
import game.dudaonline.searchdog.graphics.SpriteSheet;
import game.dudaonline.searchdog.helpers.Node;
import game.dudaonline.searchdog.helpers.PathFinder;
import game.dudaonline.searchdog.helpers.SoundFX;
import game.dudaonline.searchdog.helpers.Timer;
import game.dudaonline.searchdog.helpers.Vector2i;
import game.dudaonline.searchdog.world.Tile;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

public class Player extends Mob {
	
	private static final String TAG = Player.class.getSimpleName();
	private final static double DEFAULT_SPEED = 5;
	
	private Point screenCenter;
	private boolean screenTouched = false;
	private int pointerX = 0;
	private int pointerY = 0;
	
	public static int numCoins =0;
	
	int time = 0;
	
	private Animated8DirectionSprite playerSprite;
	private Animated8DirectionSprite pathSprite;
	
	public Player(int x, int y, Bitmap spriteSheet){
		//this.x = x;
		//this.y = y;
		setLocation(x,y);
		SpriteSheet sheet = new SpriteSheet(spriteSheet, 256, 512);
		animatedSprite = new AnimatedSprite(sheet.cutSheet(0, 0, 4, 64, 64, false));
		sprite = animatedSprite.getSprite();
		playerSprite = new Animated8DirectionSprite(animatedSprite);
		pathSprite = new Animated8DirectionSprite(animatedSprite);
	}
	
	public Player(int x, int y, Context c){
		//this.x = x;
		//this.y = y;
		setLocation(x,y);
		initializeDefaultSprite(c);
	}
	
	private void initializeDefaultSprite(Context c) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		//all the 2's below are needed because the sprite is currently 128x128 in size
		opts.inSampleSize = 2;
		SpriteSheet sheet = new SpriteSheet(BitmapFactory.decodeResource(c.getResources(), R.drawable.mydog2, opts),384/2,768/2);
		animatedSprite = new AnimatedSprite(sheet.cutSheet(0, 0, 3, 128/2, 128/2, false));
		playerSprite = new Animated8DirectionSprite(new AnimatedSprite(sheet.cutSheet(0, 4, 3, 128/2, 128/2, false)), //N
													new AnimatedSprite(sheet.cutSheet(0, 3, 3, 128/2, 128/2, false)), //NE
													new AnimatedSprite(sheet.cutSheet(0, 0, 3, 128/2, 128/2, false)), //E
													new AnimatedSprite(sheet.cutSheet(0, 1, 3, 128/2, 128/2, false)), //SE
													new AnimatedSprite(sheet.cutSheet(0, 2, 3, 128/2, 128/2, false)), //S
													new AnimatedSprite(sheet.cutSheet(0, 1, 3, 128/2, 128/2, true)), //SW -flip
													new AnimatedSprite(sheet.cutSheet(0, 0, 3, 128/2, 128/2, true)), //W - flip
													new AnimatedSprite(sheet.cutSheet(0, 3, 3, 128/2, 128/2, true)));//NW - flip
		pathSprite = new Animated8DirectionSprite(new AnimatedSprite(sheet.cutSheet(0, 5, 3, 128/2, 128/2, false)));
	}


	
	@Override
	public void update() {
		time++;
		if (walking) playerSprite.update(); //animatedSprite.update();
		else playerSprite.setFrame(1); //animatedSprite.setFrame(1); //just standing normal
		double speed = DEFAULT_SPEED;
		double xDistance=0, yDistance=0;
		boolean up = false, down = false, left = false, right = false;
		if (screenTouched){ //player is controlling movement
			followPath = false;
			double slope = -1 * (double)(screenCenter.y - pointerY) / (double)(screenCenter.x - pointerX);
			//Log.d(TAG, "screen center at: " + screenCenter.x + ", " + screenCenter.y);
			//Log.d(TAG, "pointer at: " + pointerX + ", " + pointerY);
			//Log.d(TAG, "slope: " + slope);
			if (screenCenter.y > pointerY && (slope < -0.5 || slope > 0.5)) {
				yDistance -= speed;
				//animatedSprite = up;
				up = true;
			} else if (screenCenter.y < pointerY && (slope < -0.5 || slope > 0.5)) {
				yDistance += speed;
				//animatedSprite = down;
				down = true;
			}
			if (screenCenter.x > pointerX && (slope > -2 && slope < 2)) {
				xDistance -= speed;
				//animatedSprite = left;
				left = true;
			} else if (screenCenter.x < pointerX && (slope > -2 && slope < 2)) {
				xDistance += speed;
				//animatedSprite = right;
				right = true;
			}			
		} else if (followPath) { //player single tapped and a path should be followed
			pathSprite.update();
			if (path != null) {
				if (path.size() > 0) {	
					Vector2i v = path.get(path.size() - 1).tile; //only care about first node to move to, because then it gets recalculated
					
					if (x < v.getX() * Tile.TILE_SIZE) {
						down = true;
						xDistance += speed;
					}
					if (x > v.getX() * Tile.TILE_SIZE) {
						up = true;
						xDistance -= speed;
					}
					if (y < v.getY() * Tile.TILE_SIZE) {
						right = true;
						yDistance += speed;
					}
					if (y > v.getY() * Tile.TILE_SIZE) {
						left = true;
						yDistance -= speed;
					}
					
					
					if (time % 20 == 0) setPath(destination); //don't recalculate the path too frequently, otherwise sprite goes berserk
					/*
					if (time % 20 == 0) 
						if (destination != null) {
							Log.d(TAG, "AT: " + getXTile() + ", " + getYTile() + " -> TO: " + v.getX() + ", " + v.getY());
							Log.d(TAG, "Path size: " + path.size() + " | Final: " + destination.getX() + ", " + destination.getY());
							for (Node n : path) {
								Log.d(TAG, n.tile.getX() + ", " + n.tile.getY());
							}
						}*/
				} else followPath = false;
			}
		}
		if (xDistance != 0 || yDistance != 0) {
			move(xDistance,yDistance);
			walking = true;
			playerSprite.setDirection(up, down, left, right);
		} else {
			walking = false;
		}
		
		//check if touching coin
		int px = (int) (x - Tile.TILE_SIZE/2) / Tile.TILE_SIZE;
		int py = (int) (y - Tile.TILE_SIZE/2) / Tile.TILE_SIZE;
		
		for (Entity e : level.getEntities()) {
			if (e instanceof Coin) {
				if (!e.isRemoved()) {
					//if ((px - e.getX() > 10) || (px - e.getX() < -10) || (py - e.getY() > 10) || (py - e.getY() < -10)){
					if (px == e.getX()-1 && py == e.getY()-1) {				
						e.remove();
						SoundFX.INSTANCE.play("coin.mp3");
						setNumCoins(getNumCoins() + 1);
						Timer.increaseTimeLimit(GameSurface.COIN_TIME_INCREASE);
					}
				}
			}
		}
	}

	@Override
	public Bitmap getSprite() {
		//Bitmap temper = playerSprite.getCurrentSprite();
		//if (followPath) {
		//	temper = pathSprite.getCurrentSprite();
		//}			
		//return temper;
		return followPath ? pathSprite.getCurrentSprite() : playerSprite.getCurrentSprite(); //animatedSprite.getSprite();
	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawBitmap(getSprite(), (float) x - ScreenOffset.x, (float) y - ScreenOffset.y, null);
		
		//drawPath(canvas);
	}
	
	private void drawPath(Canvas canvas){
		//test show path
		if (path != null){
			Paint p = new Paint();
			p.setColor(Color.RED);
			p.setStrokeWidth(5);
			p.setTextSize(15);
			Node lastNode = new Node(new Vector2i(getXTile(), getYTile()),null,0,0);
			boolean first = true;
			for (Node n : path) {
				if (!first) {
				int xStart = lastNode.tile.getX()*Tile.TILE_SIZE - ScreenOffset.x;
				int yStart =lastNode.tile.getY()*Tile.TILE_SIZE  - ScreenOffset.y;
				int xEnd = n.tile.getX()*Tile.TILE_SIZE  - ScreenOffset.x;
				int yEnd = n.tile.getY()*Tile.TILE_SIZE  - ScreenOffset.y;
				canvas.drawLine(xStart, yStart, xEnd, yEnd, p);
				canvas.drawText(n.tile.getX() + ", " + n.tile.getY(), xEnd+Tile.TILE_SIZE/2, yEnd+Tile.TILE_SIZE/2, p);
				}
				first = false;
				lastNode = n;
			}
		}
	}
	
	public void setScreenCenter(RectF screen) {
		this.screenCenter = new Point((int) screen.width() / 2, (int) screen.height() /2);	
		//recenter(); //why was this happening?
	}
	
	public void recenter(){
		this.x = screenCenter.x;
		this.y = screenCenter.y;
	}
	
	public void screenTouched(int x, int y) {
		this.pointerX = x;
		this.pointerY = y;
		screenTouched = true;				
	}
	
	public void screenNotTouched(){
		screenTouched = false;
	}

	public int getNumCoins() {
		return numCoins;
	}

	public void setNumCoins(int numCoins) {
		this.numCoins = numCoins;
	}

	public int getXTile(){
		//returns tile that players center is currently on
		//return (int) (this.x + getSprite().getWidth()/2) / Tile.TILE_SIZE ;
		return (int) (x + Tile.TILE_SIZE/2) / Tile.TILE_SIZE;
	}
	
	public int getYTile(){
		//returns tile that players center is currently on
		//return (int) (this.y + getSprite().getHeight()/2) / Tile.TILE_SIZE ;
		return (int) (y + Tile.TILE_SIZE/2) / Tile.TILE_SIZE;
	}
	
	public void setLocation(int xTile, int yTile){
		this.x = xTile * Tile.TILE_SIZE;
		this.y = yTile * Tile.TILE_SIZE;
		followPath = false; //otherwise crashes when level switches in the middle of finding a path
	}
	
	private List<Node> path;
	private Vector2i destination;
	//private boolean followPath = false;
	public void setPath(int xTile, int yTile) {
		Vector2i start = new Vector2i(getXTile(), getYTile());
		destination = new Vector2i(xTile, yTile);
		if (level.getTile(xTile, yTile).solid()) return; //TODO change this to closest available tile??
		path = PathFinder.findPath(start, destination, level);		
		Log.d(TAG, "Found path to " + destination.getX() + "," + destination.getY());
		followPath = true;
	}
	
	public void setPath(Vector2i destination){
		Vector2i start = new Vector2i(getXTile(), getYTile());
		path = PathFinder.findPath(start, destination, level);
		followPath = true;
		Log.d(TAG, "Recalculating path");
	}

}
