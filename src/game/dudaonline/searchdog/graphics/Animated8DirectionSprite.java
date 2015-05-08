package game.dudaonline.searchdog.graphics;

import android.graphics.Bitmap;
import android.util.Log;


//Essentially holds 8 animated sprites and switches them based on direction of travel
public class Animated8DirectionSprite {
	
	//directions are 0-7 with N=1 going clockwise
	private int dir = 4;
	private AnimatedSprite[] sprites = new AnimatedSprite[8];

	//for sprites that don't care about direction
	public Animated8DirectionSprite(AnimatedSprite x){
		//for (AnimatedSprite s : sprites) s = x;
		sprites[0] = x;
		sprites[1] = x;
		sprites[2] = x;
		sprites[3] = x;
		sprites[4] = x;
		sprites[5] = x;
		sprites[6] = x;
		sprites[7] = x;
		this.dir = 4;
	}
	
	public Animated8DirectionSprite(AnimatedSprite n, AnimatedSprite ne, AnimatedSprite e, AnimatedSprite se, 
									AnimatedSprite s, AnimatedSprite sw, AnimatedSprite w, AnimatedSprite nw){
		sprites[0] = n;
		sprites[1] = ne;
		sprites[2] = e;
		sprites[3] = se;
		sprites[4] = s;
		sprites[5] = sw;
		sprites[6] = w;
		sprites[7] = nw;
		this.dir = 4;
	}
	
	public void setDirection(int direction){
		this.dir = direction;
	}
	
	public void setDirection(boolean up, boolean down, boolean left, boolean right){
		int d = 4;
		if(up){
			if (left) d = 7;
			else if (right) d = 1;
			else d = 0;
		} else if (down) {
			if (right) d = 3;
			else if (left) d = 5;
			else d = 4;
		} else {
			if (left) d = 6;
			else if (right) d = 2;
		}
		
		this.dir = d;
	}
	
	public int getDirection(){
		return dir;
	}
	
	public Bitmap getCurrentSprite(){
		return sprites[dir].getSprite();
	}
	
	public void setFrame(int frame){
		sprites[dir].setFrame(1);
	}
	
	public void update() {
		sprites[dir].update();
	}
	
}
