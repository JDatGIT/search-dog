package game.dudaonline.searchdog.world;

import android.graphics.Bitmap;

public class GrassTile extends Tile{

	public GrassTile(Bitmap sprite) {
		super(sprite);

	}

	public boolean solid() {
		return false;
	}
	

}
