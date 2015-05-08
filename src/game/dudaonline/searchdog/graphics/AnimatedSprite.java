package game.dudaonline.searchdog.graphics;

import android.graphics.Bitmap;

public class AnimatedSprite {

	private Bitmap[] spriteFrames;
	private int frame;
	private Bitmap sprite;
	private int rate = 5;
	private int animLength = -1;
	private int time = 0;
	
	public AnimatedSprite(Bitmap[] bitmapAnim) {
		this.animLength = bitmapAnim.length;
		this.spriteFrames = bitmapAnim;
		sprite = spriteFrames[0]; 
		//if (animLength > sheet.getSprites().length) System.err.println("Error! Lenght of animation is too long");
	}
	
	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= animLength - 1) frame = 0;
			else frame++;
			sprite = spriteFrames[frame];
		}
		//System.out.println(sprite + ", Frame:" + frame);
	}
	
	public Bitmap getSprite(){
		return sprite;
	}
	
	public void setFrameRate(int framesperseond){
		rate = framesperseond;
	}

	public void setFrame(int index) {
		if (index > spriteFrames.length - 1) {
			System.err.println("Index out of bounds in " + this);
			return;
		}
		sprite = spriteFrames[index];		
	}	
	
}
