package game.dudaonline.searchdog.world;

import game.dudaonline.searchdog.GameSurface;
import game.dudaonline.searchdog.entity.Coin;
import game.dudaonline.searchdog.entity.Entity;
import game.dudaonline.searchdog.graphics.ScreenOffset;
import game.dudaonline.searchdog.graphics.TileGrabber;
import game.dudaonline.searchdog.helpers.Timer;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Level {
	
	private static final String TAG = Level.class.getSimpleName();

	private int width, height;
	private int[] tilesInt;
	private int[] tilesIntDetailed;
	private List<Entity> entities; 
	
	private static int currentLevel = 0;
	
	Context c;
	
	//loads test map
	public Level(Context c){
		this.width = 20;
		this.height = 20;
		this.c = c;
		tilesInt = LevelLoader.loadTestMap();
		entities = LevelLoader.loadTestMapEntities(c);
	}
	
	//creates true random
	public Level(Context c, int size) {
		this.width = size;
		this.height = size;
		this.c = c;
		tilesInt = LevelLoader.createTrueRandom(width, height);
	}
	
	//creates random maze
	public Level(Context c, int width, int height) {		
		newRandomLevel(c, width, height);
	}
	
	public void newRandomLevel(Context c, int width, int height){
		this.c = c;
		//make sure it is even
		width = (width % 2) == 0 ? width : width - 1;
		height = (height % 2) == 0 ? height : height - 1;
		// +1 to account for extra single tile right and bottom border
		this.width = width+1;
		this.height = height+1;
		tilesInt = LevelLoader.createRandom(width/2, height/2);		
		entities = LevelLoader.loadRandomEntities(c, tilesInt, this.width);		
		tilesIntDetailed = TileGrabber.convertTilesToTileSet(tilesInt, this.width); //get detailed array for tile graphics
		currentLevel++;		
	}
	
	public void reset(){
		currentLevel = 0;
		newRandomLevel(c, GameSurface.START_LEVEL_SIZE, GameSurface.START_LEVEL_SIZE);		
	}
	
	public void update(){		
		if (entities != null) {
			int coinsRemaining = 0;
			for (Entity e : entities){
				e.update();			
				if (e instanceof Coin) 
					if (!e.isRemoved()) coinsRemaining++;
			}
			if (coinsRemaining == 0) {
				if (currentLevel == GameSurface.MAX_LEVEL  && !Timer.isCountdownMode()) currentLevel++; //end the game
				else newRandomLevel(c, width +1, height +1);
			}
		}
	}
	
	private int x0, x1, y0, y1 =0;
	public void render(int xScroll, int yScroll, Canvas canvas) {
		int tileSize = Tile.TILE_SIZE;
		ScreenOffset.x = xScroll;
		ScreenOffset.y = yScroll;
		
		//find top left and bottom right most visible points
		x0 = (xScroll - tileSize) / tileSize;
		x1 = (xScroll + canvas.getWidth() + tileSize) / tileSize;
		y0 = (yScroll - tileSize) / tileSize;
		y1 = (yScroll + canvas.getHeight() + tileSize) / tileSize; 

		//render only tiles on screen visible between the two points
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (tilesIntDetailed != null) getTileGraphic(x, y).render(x, y, canvas);
				else getTile(x, y).render(x, y, canvas);
			}
		}
		
		for (Entity e : entities){
			e.render(canvas);
		}
	}
	
	public Tile getTile(int x, int y) {
		
		//determine what tile to render based on number, change to color in the future
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		if (tilesInt[x + y * width] == 1)
			return Tile.wallTile;
		if (tilesInt[x + y * width] == 0)
			return Tile.grassTile;
		

		return Tile.voidTile;
	}
	
	private Tile getTileGraphic(int x, int y) {
		
		//determine what tile to render based on number, change to color in the future
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		int tileNum = tilesIntDetailed[x+y*width];
		if (tileNum == 0) return Tile.wallTile0; //these are the walls
		if (tileNum == 1) return Tile.grassTile1;
		if (tileNum == 4) return Tile.grassTile4;
		if (tileNum == 5) return Tile.grassTile5;
		if (tileNum == 7) return Tile.grassTile7;
		if (tileNum == 16) return Tile.grassTile16;
		if (tileNum == 17) return Tile.grassTile17;
		if (tileNum == 20) return Tile.grassTile20;
		if (tileNum == 21) return Tile.grassTile21;
		if (tileNum == 23) return Tile.grassTile23;
		if (tileNum == 28) return Tile.grassTile28;
		if (tileNum == 29) return Tile.grassTile29;
		if (tileNum == 31) return Tile.grassTile31;
		if (tileNum == 64) return Tile.grassTile64;
		if (tileNum == 65) return Tile.grassTile65;
		if (tileNum == 68) return Tile.grassTile68;
		if (tileNum == 69) return Tile.grassTile69;
		if (tileNum == 71) return Tile.grassTile71;
		if (tileNum == 80) return Tile.grassTile80;
		if (tileNum == 81) return Tile.grassTile81;
		if (tileNum == 84) return Tile.grassTile84;
		if (tileNum == 85) return Tile.grassTile85;
		if (tileNum == 87) return Tile.grassTile87;
		if (tileNum == 92) return Tile.grassTile92;
		if (tileNum == 93) return Tile.grassTile93;
		if (tileNum == 95) return Tile.grassTile95;
		if (tileNum == 112) return Tile.grassTile112;
		if (tileNum == 113) return Tile.grassTile113;
		if (tileNum == 116) return Tile.grassTile116;
		if (tileNum == 117) return Tile.grassTile117;
		if (tileNum == 119) return Tile.grassTile119;
		if (tileNum == 124) return Tile.grassTile124;
		if (tileNum == 125) return Tile.grassTile125;
		if (tileNum == 127) return Tile.grassTile127;
		if (tileNum == 193) return Tile.grassTile193;
		if (tileNum == 197) return Tile.grassTile197;
		if (tileNum == 199) return Tile.grassTile199;
		if (tileNum == 209) return Tile.grassTile209;
		if (tileNum == 213) return Tile.grassTile213;
		if (tileNum == 215) return Tile.grassTile215;
		if (tileNum == 221) return Tile.grassTile221;
		if (tileNum == 223) return Tile.grassTile223;
		if (tileNum == 241) return Tile.grassTile241;
		if (tileNum == 245) return Tile.grassTile245;
		if (tileNum == 247) return Tile.grassTile247;
		if (tileNum == 253) return Tile.grassTile253;
		if (tileNum == 255) return Tile.grassTile255;

		/*
		0 , 1, 4, 5, 7,
		16,17,20,21,23,
		28,29,31,64,65,
		68,69,71,80,81,
		84,85,87,92,93,
		95,112,113,116,117,
		119,124,125,127,193,
		197,199,209,213,215,
		221,223,241,245,247,
		253,255
		 */
		return Tile.voidTile;
	}

	
	/*
	public Tile getTileAtPixel(int xPixel, int yPixel){
		int xTile = getTileX(xPixel);
		int yTile = getTileY(yPixel);
		
		if (xTile < 0 || yTile < 0 || xTile >= width || yTile >= height)
			return Tile.voidTile;
		
		switch(tilesInt[xTile + yTile * width]){
		case 1:
			return Tile.wallTile;
		case 2:
			return Tile.grassTile;
		default:
			break;
		}
		
		return Tile.voidTile;
	}*/
	
	
	private int centerX, centerY;
	public void setScreenCenter(RectF screen) {
		centerX = (int) screen.width() / 2;
		centerY = (int) screen.height() /2;	
	}
	
	public int getTileX(float xPixel, float scale, int playerXTile){
		int xTile = (int) (((xPixel - centerX) + (Tile.TILE_SIZE/2 * scale)) / (Tile.TILE_SIZE * scale)); 
		xTile += playerXTile;
		xTile--;
		//Log.d(TAG, "xTile: " + xTile);
		return xTile;
	}
	
	public int getTileY(float yPixel, float scale, int playerYTile){
		int yTile = (int) (((yPixel - centerY) + (Tile.TILE_SIZE/2 * scale)) / (Tile.TILE_SIZE * scale)); 
		yTile += playerYTile;
		//int yTile = (int) yPixel / Tile.TILE_SIZE;
		//yTile += y0;
		yTile--;
		//Log.d(TAG, "yTile: " + yTile);
		return yTile;
	}

	public List<Entity> getEntities(){
		return entities;
	}
	
	public int getLevelNumber(){
		return currentLevel;
	}
}
