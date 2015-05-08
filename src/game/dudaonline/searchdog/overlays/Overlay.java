package game.dudaonline.searchdog.overlays;

import android.graphics.Canvas;
import android.graphics.RectF;

public abstract class Overlay {

	protected int screenWidth, screenHeight;
	
	public Overlay(){
		
	}
	
	public abstract void update();	
	public abstract void render(Canvas canvas);
}
