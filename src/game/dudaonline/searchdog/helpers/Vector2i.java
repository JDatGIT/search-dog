package game.dudaonline.searchdog.helpers;

public class Vector2i {

	private int x, y;
	
	public Vector2i(){
		set(0,0);
	}

	public Vector2i(int x, int y) {
		set(x,y);
	}
	
	public Vector2i(Vector2i vector){
		set(vector.x, vector.y);
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void test(){
		Vector2i player_position = new Vector2i(80, 40).setX(15);
		Vector2i mob_position = new Vector2i(player_position).setX(50);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}
	
	public Vector2i add(Vector2i v){
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector2i subtract(Vector2i v){
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public static double getDistance(Vector2i v0, Vector2i v1) {
		double x = v0.getX() - v1.getX();
		double y = v0.getY() - v1.getY();
		return Math.sqrt(x * x + y * y);
	}
	
	//override
	public boolean equals(Object object) {
		if (!(object instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == this.getX() && vec.getY() == this.getY()) return true;
		return false;
	}
}
