package game.dudaonline.searchdog.overlays;

import game.dudaonline.searchdog.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class ModeButtons extends Overlay {
	
	private final float BUTTON1_LEFT = 100;
	private final float BUTTON1_TOP = 150;
	private final float BUTTON2_LEFT = 400;
	private final float BUTTON2_TOP = 150;
	
	private boolean isShowing = true;
	private boolean isTouched = false;
	private boolean isCountDownMode = false;	
	
	private float touchedX = 0;
	private float touchedY = 0;
	
	private Paint p;

	private Bitmap button_g_up;
	private Bitmap button_y_up;
	private Bitmap button_g_down;
	private Bitmap button_y_down;
	
	public ModeButtons(Context c) {
		p = new Paint();
		p.setTextSize(30);
		p.setTextAlign(Align.CENTER);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		Bitmap original = BitmapFactory.decodeResource(c.getResources(), R.drawable.button_g_up, opts);
		button_g_up = Bitmap.createScaledBitmap(original, (int)(original.getWidth()*1.5), (int)(original.getHeight()*1.5), false);
		original = BitmapFactory.decodeResource(c.getResources(), R.drawable.button_y_up, opts);
		button_y_up = Bitmap.createScaledBitmap(original, (int)(original.getWidth()*1.5), (int)(original.getHeight()*1.5), false);
		original = BitmapFactory.decodeResource(c.getResources(), R.drawable.button_g_down, opts);
		button_g_down = Bitmap.createScaledBitmap(original, (int)(original.getWidth()*1.5), (int)(original.getHeight()*1.5), false);
		original = BitmapFactory.decodeResource(c.getResources(), R.drawable.button_y_down, opts);
		button_y_down = Bitmap.createScaledBitmap(original, (int)(original.getWidth()*1.5), (int)(original.getHeight()*1.5), false);
	}
	
	public void setTouchedXY(float touchedX, float touchedY) {
		this.touchedX = touchedX;
		this.touchedY = touchedY;
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}

	public boolean isCountDownMode() {
		return isCountDownMode;
	}

	public void setCountDownMode(boolean isCountDownMode) {
		this.isCountDownMode = isCountDownMode;
	}

	@Override
	public void update() {
		if (isShowing) {
			if (touching(button_g_down, BUTTON1_LEFT, BUTTON1_TOP)) {
				isCountDownMode = false;
			}
			if (touching(button_y_down, BUTTON2_LEFT, BUTTON2_TOP)) {
				isCountDownMode = true;
			}
		}
		
	}

	private boolean touching(Bitmap b, float left, float top) {
		int x = (int) touchedX;
		int y = (int) touchedY;
		if (x < left) return false;
		if (x > left + b.getWidth()) return false;
		if (y < top) return false;
		if (y > top + b.getHeight()) return false;
		return true;
	}

	@Override
	public void render(Canvas canvas) {
		if (isShowing) {
			this.screenWidth = canvas.getWidth();
			this.screenHeight = canvas.getHeight();
			if (isCountDownMode) {
				canvas.drawBitmap(button_g_up, BUTTON1_LEFT, BUTTON1_TOP, p);
				canvas.drawBitmap(button_y_down, BUTTON2_LEFT, BUTTON2_TOP, p);
				canvas.drawText("How many levels before running out of time?", screenWidth/2, BUTTON1_TOP + button_g_up.getHeight() + 30, p);
			} else {
				canvas.drawBitmap(button_g_down, BUTTON1_LEFT, BUTTON1_TOP, p);
				canvas.drawBitmap(button_y_up, BUTTON2_LEFT, BUTTON2_TOP, p);
				canvas.drawText("How fast can you get through level 10?", screenWidth/2, BUTTON1_TOP + button_g_up.getHeight() + 30, p);
			}
			canvas.drawText("Race", BUTTON1_LEFT + button_g_up.getWidth()/2, BUTTON1_TOP + button_g_up.getHeight()/2, p);
			canvas.drawText("Countdown", BUTTON2_LEFT + button_g_up.getWidth()/2, BUTTON2_TOP + button_g_up.getHeight()/2, p);
		}
		
	}

}
