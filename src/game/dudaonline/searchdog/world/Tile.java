package game.dudaonline.searchdog.world;

import game.dudaonline.searchdog.graphics.ScreenOffset;
import game.dudaonline.searchdog.graphics.SpriteSheet;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Tile {
	
	public static final int TILE_SIZE = 64; //in pixels
	
	//public static Tile voidTile = new VoidTile(SpriteSheet.createSprite(64, 64, 0xFF1B87e0));
	public static Tile voidTile = new VoidTile(SpriteSheet.voidTileSheet.getSheet());
	
	
	public static Tile grassTile = new GrassTile(SpriteSheet.createSprite(64, 64, 0xff00ff00));
	public static Tile wallTile = new WallTile(SpriteSheet.createSprite(64, 64, 0xff555555));
	
	private static final int TILE_SCALE = 2;
	private static final int TS = 32; //initial tile set size
		
	public static Tile wallTile0 =   new WallTile(SpriteSheet.blobTileSet.getScaledCell(0, 6, TS, TS, TILE_SCALE));
	//public static Tile grassTile0 =   new WallTile(SpriteSheet.blobTileSet.getScaledCell(6, 0, TS, TS, TILE_SCALE));
	//public static Tile grassTile0 =   new WallTile(SpriteSheet.blobTileSet.getScaledCell(7, 7, TS, TS, TILE_SCALE));
	public static Tile grassTile1 =   new GrassTile(SpriteSheet.blobTileSet.getScaledCell(7, 6, TS, TS, TILE_SCALE));
	public static Tile grassTile4 =   new GrassTile(SpriteSheet.blobTileSet.getScaledCell(0, 7, TS, TS, TILE_SCALE));
	public static Tile grassTile5 =   new GrassTile(SpriteSheet.blobTileSet.getScaledCell(0, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile7 =   new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 4, TS, TS, TILE_SCALE));
	public static Tile grassTile16 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(7, 0, TS, TS, TILE_SCALE));
	public static Tile grassTile17 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(0, 1, TS, TS, TILE_SCALE));
	//public static Tile grassTile17 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(0, 3, TS, TS, TILE_SCALE));
	//public static Tile grassTile17 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(0, 4, TS, TS, TILE_SCALE));
	public static Tile grassTile20 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(0, 0, TS, TS, TILE_SCALE));
	public static Tile grassTile21 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(0, 2, TS, TS, TILE_SCALE));
	public static Tile grassTile23 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 2, TS, TS, TILE_SCALE));
	public static Tile grassTile28 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 1, TS, TS, TILE_SCALE));
	public static Tile grassTile29 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 3, TS, TS, TILE_SCALE));
	public static Tile grassTile31 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 4, TS, TS, TILE_SCALE));
	//public static Tile grassTile31 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 6, TS, TS, TILE_SCALE));
	//public static Tile grassTile31 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 3, TS, TS, TILE_SCALE));
	public static Tile grassTile64 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(6, 7, TS, TS, TILE_SCALE));
	public static Tile grassTile65 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(6, 6, TS, TS, TILE_SCALE));
	public static Tile grassTile68 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 0, TS, TS, TILE_SCALE));
	//public static Tile grassTile68 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 0, TS, TS, TILE_SCALE));
	//public static Tile grassTile68 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 0, TS, TS, TILE_SCALE));
	public static Tile grassTile69 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 7, TS, TS, TILE_SCALE));
	public static Tile grassTile71 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 7, TS, TS, TILE_SCALE));
	public static Tile grassTile80 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 0, TS, TS, TILE_SCALE));
	public static Tile grassTile81 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(7, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile84 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 0, TS, TS, TILE_SCALE));
	public static Tile grassTile85 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 6, TS, TS, TILE_SCALE));
	public static Tile grassTile87 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 2, TS, TS, TILE_SCALE));
	public static Tile grassTile92 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 1, TS, TS, TILE_SCALE));
	public static Tile grassTile93 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 3, TS, TS, TILE_SCALE));
	public static Tile grassTile95 =  new GrassTile(SpriteSheet.blobTileSet.getScaledCell(1, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile112 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 3, TS, TS, TILE_SCALE));
	public static Tile grassTile113 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(7, 1, TS, TS, TILE_SCALE));
	public static Tile grassTile116 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 3, TS, TS, TILE_SCALE));
	public static Tile grassTile117 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 1, TS, TS, TILE_SCALE));
	public static Tile grassTile119 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile124 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 1, TS, TS, TILE_SCALE));
	//public static Tile grassTile124 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(6, 1, TS, TS, TILE_SCALE));
	//public static Tile grassTile124 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile125 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 1, TS, TS, TILE_SCALE));
	public static Tile grassTile127 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 4, TS, TS, TILE_SCALE));
	public static Tile grassTile193 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 2, TS, TS, TILE_SCALE));
	public static Tile grassTile197 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 7, TS, TS, TILE_SCALE));
	public static Tile grassTile199 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 7, TS, TS, TILE_SCALE));
	//public static Tile grassTile199 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 7, TS, TS, TILE_SCALE));
	//public static Tile grassTile199 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 2, TS, TS, TILE_SCALE));
	public static Tile grassTile209 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(7, 4, TS, TS, TILE_SCALE));
	public static Tile grassTile213 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(6, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile215 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile221 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 4, TS, TS, TILE_SCALE));
	public static Tile grassTile223 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(5, 2, TS, TS, TILE_SCALE));
	public static Tile grassTile241 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 4, TS, TS, TILE_SCALE));
	//public static Tile grassTile241 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(7, 2, TS, TS, TILE_SCALE));
	//public static Tile grassTile241 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(7, 3, TS, TS, TILE_SCALE));
	public static Tile grassTile245 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(4, 6, TS, TS, TILE_SCALE));
	public static Tile grassTile247 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(6, 4, TS, TS, TILE_SCALE));
	public static Tile grassTile253 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 5, TS, TS, TILE_SCALE));
	public static Tile grassTile255 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(2, 6, TS, TS, TILE_SCALE));
	//public static Tile grassTile255 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(3, 6, TS, TS, TILE_SCALE));
	//public static Tile grassTile255 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(6, 2, TS, TS, TILE_SCALE));
	//public static Tile grassTile255 = new GrassTile(SpriteSheet.blobTileSet.getScaledCell(6, 3, TS, TS, TILE_SCALE));
	
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

	public Bitmap sprite;
	
	public Tile(Bitmap sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Canvas canvas) {
		render(x,y,canvas,false);
	}
	
	public void render(int x, int y, Canvas canvas, boolean fixed){
		int xPosition = x * TILE_SIZE;
		int yPosition = y * TILE_SIZE;
		if (fixed) canvas.drawBitmap(this.sprite, xPosition, yPosition, null);
		else {
			xPosition -= ScreenOffset.x;
			yPosition -= ScreenOffset.y;
			canvas.drawBitmap(this.sprite, xPosition, yPosition, null);
		}
	}
	
	public boolean solid() {
		return false;
	}
}
