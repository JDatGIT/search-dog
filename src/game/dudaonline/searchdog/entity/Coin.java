package game.dudaonline.searchdog.entity;

import game.dudaonline.searchdog.graphics.ScreenOffset;
import game.dudaonline.searchdog.world.Tile;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Coin extends Entity{

	private float time = 0;
	
	public Coin(int x, int y, Bitmap sprite) {
		super(x,y,sprite);
	}
	
	@Override
	public void update(){
		time++;
		if (time > 360) time = 0;
	}
	
	@Override
	public void render(Canvas canvas){			
		if (!removed){
			int xPosition = (int) x * Tile.TILE_SIZE + 15;//center it a little better
			int yPosition = (int) y * Tile.TILE_SIZE + 15;
			xPosition -= ScreenOffset.x;
			yPosition -= ScreenOffset.y;
			canvas.save();
			canvas.rotate(time, xPosition + sprite.getWidth()/2, yPosition + sprite.getHeight()/2);
			canvas.drawBitmap(this.sprite, xPosition, yPosition, null);
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			canvas.restore();
			canvas.drawText(xPosition + "," + yPosition, xPosition-15, yPosition-15, p);
			canvas.drawText(x + "," + y, xPosition, yPosition-5, p);
		}
	}
	
}
