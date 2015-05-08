package game.dudaonline.searchdog.helpers;

public class Node {

	public Vector2i tile;
	public Node parent;
	public double fCost, gCost, hCost;

	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost; //actual cost from start to this node
		this.hCost = hCost; //heuristic (estimated) cost from this node to finish
		this.fCost = this.gCost + this.hCost; //estimated total cost from start to goal through this node
	}
}
