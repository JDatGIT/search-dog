package game.dudaonline.searchdog.entity;

import game.dudaonline.searchdog.graphics.AnimatedSprite;
import android.graphics.Canvas;

public abstract class Item extends Entity {

	protected AnimatedSprite animatedSprite;
	
	public abstract void update();
	public abstract void render(Canvas canvas);
}
