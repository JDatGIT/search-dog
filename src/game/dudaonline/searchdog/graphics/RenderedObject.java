package game.dudaonline.searchdog.graphics;

import android.graphics.Bitmap;

public class RenderedObject {

	public Bitmap image;
	public int xCoord, yCoord;
	
	public RenderedObject(Bitmap image, int xCoord, int yCoord){
		this.image = image;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
}
