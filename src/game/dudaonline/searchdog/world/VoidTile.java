package game.dudaonline.searchdog.world;

import android.graphics.Bitmap;

public class VoidTile extends Tile{

	public VoidTile(Bitmap sprite) {
		super(sprite);

	}
	
	public boolean solid() {
		return true;
	}

}
