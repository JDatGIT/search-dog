package game.dudaonline.searchdog.world;

import game.dudaonline.searchdog.R;
import game.dudaonline.searchdog.entity.Coin;
import game.dudaonline.searchdog.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LevelLoader {

	//idea: use colors to represent tiles and transparency to represent entities?
	//idea: use 1XX to represent tile, X1X to represent entity, XX1 to represent ___
	public static int[] load(Bitmap tileMap){
		int w = tileMap.getWidth();
		int h = tileMap.getHeight();
		int[] tilesInt = new int[w*h];
		tileMap.getPixels(tilesInt, 0, w, 0, 0, w, h);
		
		//add enemies
		
		return tilesInt;
	}
	
	public static int[] loadTestMap(){
		//20 x 20
		int[] tilesInt = {
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,
			
			0,0,0,0,0,2,1,1,1,1,1,1,1,2,0,0,0,0,0,0,
			0,0,0,2,2,2,2,2,2,2,2,2,1,2,0,0,0,0,0,0,
			0,2,2,2,1,2,2,2,2,2,2,2,1,2,0,0,0,0,0,0,
			0,2,1,1,2,2,2,2,2,2,1,1,1,2,0,0,0,0,0,0,
			0,2,1,1,2,2,2,2,2,2,2,2,1,2,0,0,0,0,0,0,
			0,2,2,1,1,2,1,2,2,2,2,2,1,2,0,0,0,0,0,0,
			
			0,0,2,2,2,2,1,2,2,2,2,2,1,2,0,0,0,0,0,0,
			0,0,0,0,0,2,1,2,2,2,2,2,1,2,0,0,0,0,0,0,
			0,0,0,0,0,2,1,1,1,2,2,1,1,2,0,0,0,0,0,0,
			0,0,0,0,0,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
		};
		return tilesInt;
	}
	
	public static List<Entity> loadTestMapEntities(Context c){
		//this is to prevent android from automatically rescaling the sprites
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(new Coin(10,12,BitmapFactory.decodeResource(c.getResources(), R.drawable.coin, opts)));
		entities.add(new Coin(10,10,BitmapFactory.decodeResource(c.getResources(), R.drawable.coin, opts)));
		entities.add(new Coin(5,8,BitmapFactory.decodeResource(c.getResources(), R.drawable.coin, opts)));
		return entities;
	}
	
	public static int[] createTrueRandom(int w, int h){
		Random r = new Random();
		int[] tilesInt = new int[w * h];
		for (int i = 0; i < tilesInt.length; i++){
			tilesInt[i] = r.nextInt(2); //0 and 1
		}
		return tilesInt;
	}
	
	public static int[] createRandom(int w, int h){
		LevelGenerator l = new LevelGenerator(w, h);
		return l.getLevel();
	}
	
	public static List<Entity> loadRandomEntities(Context c, int[] mapTiles, int width){
		
		final int coinPercent = 10;
		
		
		Random r = new Random();
		//TODO use mapTiles to make sure coins aren't on walls
		//this is to prevent android from automatically rescaling the sprites
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		List<Entity> entities = new ArrayList<Entity>();
		while (entities.size() < 2) { //make sure there is at least 2 coins in case 1 lands on the player!
			for (int i = 0; i < mapTiles.length; i++) {
				if (mapTiles[i] == 0) //grassTile
					if (r.nextInt(100) < coinPercent)
						entities.add(new Coin(i%width,i/width,BitmapFactory.decodeResource(c.getResources(), R.drawable.coin, opts)));
			}
		}
		return entities;
	}
	
}
