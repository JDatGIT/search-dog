package game.dudaonline.searchdog.graphics;

import game.dudaonline.searchdog.R;
import game.dudaonline.searchdog.helpers.App;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class SpriteSheet {
	
	private static final String TAG = SpriteSheet.class.getSimpleName();
	
	public static SpriteSheet voidTileSheet;
	public static SpriteSheet blobTileSet;
	static {
		Resources res = App.getContext().getResources();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		blobTileSet = new SpriteSheet(BitmapFactory.decodeResource(res, R.drawable.tileset7color3, opts),256,256);
		voidTileSheet = new SpriteSheet(BitmapFactory.decodeResource(res, R.drawable.voidtile2, opts), 64, 64);
	}
	private Bitmap image;
	public final int WIDTH, HEIGHT;	
	
	private Bitmap[] sprites;
	
	public SpriteSheet(Bitmap image, int w, int h) {
		this.image = image;
		WIDTH = w;
		HEIGHT = h;		
	}
	
	public Bitmap[] cutSheet(int startCellX, int startCellY, int numCells, int cellWidth, int cellHeight, boolean flipH){
		//be careful bitmap is not automatically resized by android
		sprites = new Bitmap[numCells];
		//Log.d(TAG, image.getWidth() + ", " + image.getHeight());
		int w = image.getWidth();
		int h = image.getHeight();
		for(int i = 0; i < numCells; i++){				
			//Log.d(TAG, startCellX + ", " + startCellY + ", " + numCells + ", " + cellWidth + ", " + cellHeight);	
			Bitmap subSheet;
			if (flipH) {
				Matrix m = new Matrix();
			    m.preScale(-1, 1);
				subSheet = Bitmap.createBitmap(image, startCellX*cellWidth, startCellY*cellHeight, cellWidth, cellHeight, m, false);
			}
			else subSheet = Bitmap.createBitmap(image, startCellX*cellWidth, startCellY*cellHeight, cellWidth, cellHeight);
			startCellX++; //do startCellY for vertical
			sprites[i] = subSheet;
		}
		return sprites;
	}
	
	/*
	public Bitmap[] cutSheet(int startCellX, int startCellY, int numCells, int cellWidth, int cellHeight, boolean flipH){		
		return cutSheet(startCellX, startCellY, numCells, cellWidth, cellHeight);
	}*/
	
	public Bitmap[] getSprites(){		
		return sprites;
	}
	
	public Bitmap getSheet() {
		return this.image;
	}
	
	public Bitmap getCell(int xCell, int yCell, int xWidth, int yHeight){
		return Bitmap.createBitmap(image, xCell*xWidth, yCell*yHeight, xWidth, yHeight);
	}
	
	public Bitmap getScaledCell(int xCell, int yCell, int xWidth, int yHeight, int scale){
		Bitmap b = Bitmap.createBitmap(image, xCell*xWidth, yCell*yHeight, xWidth, yHeight);
		return Bitmap.createScaledBitmap(b, xWidth*scale, yHeight*scale, false);
	}
	
	//Create a solid colored rectangular sprite
	public static Bitmap createSprite(int w, int h, int color){
		Bitmap sprite = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		sprite.eraseColor(color);
		return sprite;
	}
}
