package game.dudaonline.searchdog.helpers;

import game.dudaonline.searchdog.world.Level;
import game.dudaonline.searchdog.world.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PathFinder {

	public static Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return +1;
			if (n1.fCost > n0.fCost)
				return -1;
			return 0;
		}
	};
	
	//A* Star search algorithm
	public static List<Node> findPath(Vector2i start, Vector2i goal, Level level) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			//NEW
			Tile[] localTiles = new Tile[9];
			for (int i = 0; i < 9; i++) {
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1; //get direction
				int yi = (i / 3) - 1;
				localTiles[i] = level.getTile(x + xi, y + yi);
			}
			for (int i = 0; i < 9; i++) { //current node is middle of 3 x 3 grid
				if (i == 4) continue;				
				if (localTiles[i] == null) continue;
				if (localTiles[i].solid()) continue;
				if (i == 0)  //no corner movement
					if (localTiles[1] != null && localTiles[3] != null)
						if (localTiles[1].solid() && localTiles[3].solid()) continue;
				if (i == 2)  //no corner movement
					if (localTiles[1] != null && localTiles[5] != null)
						if (localTiles[1].solid() && localTiles[5].solid()) continue;
				if (i == 6)  //no corner movement
					if (localTiles[3] != null && localTiles[7] != null)
						if (localTiles[3].solid() && localTiles[7].solid()) continue;
				if (i == 8)  //no corner movement
					if (localTiles[5] != null && localTiles[7] != null)
						if (localTiles[5].solid() && localTiles[7].solid()) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1; //get direction
				int yi = (i / 3) - 1;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95); //value diagonal before straight --> more human like
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
			//OLD
			/*
			for (int i = 0; i < 9; i++) { //current node is middle of 3 x 3 grid
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1; //get direction
				int yi = (i / 3) - 1;
				Tile at = level.getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95); //value diagonal before straight --> more human like
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
			*/
		}
		closedList.clear();
		return null;
	}
	
	private static double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance; // > 1 ? 0.95 : 1;
	}
	
	private static boolean vecInList(List<Node> list, Vector2i vector) { // check if vector exists in a list of node
		for (Node n : list) {
			if (n.tile.equals(vector)) return true; //overriden in Vector2i
		}
		return false;
	}
}
