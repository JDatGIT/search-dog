package game.dudaonline.searchdog.world;

import android.graphics.Bitmap;

public class WallTile extends Tile{

	public WallTile(Bitmap sprite) {
		super(sprite);
	}

	public boolean solid() {
		return true;
	}
}
