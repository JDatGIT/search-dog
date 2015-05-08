package game.dudaonline.searchdog.graphics;

import android.util.Log;

public class TileGrabber {

	final static short N = 0x1; // 00000001
	final static short NE = 0x2; // 00000010
	final static short E = 0x4; // 00000100
	final static short SE = 0x8; // 00001000
	final static short S = 0x10; // 00010000
	final static short SW = 0x20; // 00100000
	final static short W = 0x40; // 01000000
	final static short NW = 0x80; // 10000000 //had to use shorts because in bytes the first bit is used for sign
	/*
	static {
		Log.d("bytes", "N: " + Integer.toBinaryString(N));
		Log.d("bytes", "NE: " + Integer.toBinaryString(NE));
		Log.d("bytes", "E: " + Integer.toBinaryString(E));
		Log.d("bytes", "SE: " + Integer.toBinaryString(SE));
		Log.d("bytes", "S: " + Integer.toBinaryString(S));
		Log.d("bytes", "SW: " + Integer.toBinaryString(SW));
		Log.d("bytes", "W: " + Integer.toBinaryString(W));
		Log.d("bytes", "NW: " + Integer.toBinaryString(NW));
		Log.d("bytes", "NW + NE: " + Integer.toBinaryString(NW | NE));
	}*/
	
	public static int fetchTile(int[] tile9x9){
		if (tile9x9.length != 9) return -1; //voidTile
		if (tile9x9[4] == 1) return 0; //if 1 then it's a plain wall tile

		short grid = 0;
		//flip the bit on (to 1) if it is open
		if (tile9x9[1] == 0) grid |= N;
		if (tile9x9[2] == 0) grid |= NE;
		if (tile9x9[5] == 0) grid |= E;
		if (tile9x9[8] == 0) grid |= SE;
		if (tile9x9[7] == 0) grid |= S;
		if (tile9x9[6] == 0) grid |= SW;
		if (tile9x9[3] == 0) grid |= W;
		if (tile9x9[0] == 0) grid |= NW;

		//Log.d("TileGrabber", "Grid ori int: " + grid + " ---- Grid ori binary: " + Integer.toBinaryString(grid));
		/*& 0xFF basically converts a signed byte to an unsigned integer. 
		For example, -129, like you said, is represented by 11111111111111111111111110000001. 
		In this case, you basically want the first (least significant) 8 bits, 
		so you AND (&) it with 0xFF (00000000000000000000000011111111), effectively cleaning 
		the 1's to the left that we don't care about, leaving out just 10000001
		*/
		
		//If North is not open (i.e. a 0) make NE and NW not open also (i.e. flip them to 0)
		if ((grid & N) == 0) grid = (short) (grid & ~(NE | NW));
		if ((grid & E) == 0) grid = (short) (grid & ~(NE | SE));
		if ((grid & S) == 0) grid = (short) (grid & ~(SE | SW));
		if ((grid & W) == 0) grid = (short) (grid & ~(NW | SW));
		
		//Log.d("TileGrabber", "Grid new int: " + grid + " ---- Grid new binary: " + Integer.toBinaryString(grid));
		
		int tileIndex = grid;

		//http://www.cr31.co.uk/stagecast/wang/blob.html
		
		/*
		North edge = 1 
		NorthEast corner = 2
		East edge = 4
		SouthEast corner = 8
		South edge = 16 
		Sout-West corner = 32
		West edge = 64
		NorthWest corner = 128
		
		1 = wall
		2 = grass		
				
		we are searching for grass tiles
		4 is tile center, go clockwise starting from north
		*/
		
		
		//otherwise calculate what kind of grass tile it is
		/*
		int tileIndex = 0;
		if (tile9x9[1] == 0) tileIndex += 1;
		if (tile9x9[2] == 0) tileIndex += 2;
		if (tile9x9[5] == 0) tileIndex += 4;
		if (tile9x9[8] == 0) tileIndex += 8;
		if (tile9x9[7] == 0) tileIndex += 16;
		if (tile9x9[6] == 0) tileIndex += 32;
		if (tile9x9[3] == 0) tileIndex += 64;
		if (tile9x9[0] == 0) tileIndex += 128;
		*/
		
		//use this index to match the sprite from the tileset
		return tileIndex;
	}
	
	public static int fetchTile(int[] tileArray, int index, int arrayWidth){
		return fetchTile(create9by9(tileArray, index, arrayWidth));
	}
	
	public static int[] create9by9(int[] tileArray, int index, int arrayWidth) {
		if (index < 0) return null;
		int[] tile9x9 = new int[9];
		//watching for arrayindexoutofbounds errors
		tile9x9[0] = index - arrayWidth - 1 < 0 ? 1 : tileArray[index - arrayWidth - 1];
		tile9x9[1] = index - arrayWidth < 0 ? 1 : tileArray[index - arrayWidth];
		tile9x9[2] = index - arrayWidth + 1 < 0 ? 1 : tileArray[index - arrayWidth + 1];
		tile9x9[3] = index - 1 < 0 ? 1 : tileArray[index - 1];
		tile9x9[4] = tileArray[index];
		tile9x9[5] = index + 1 > tileArray.length - 1 ? 1 : tileArray[index + 1];
		tile9x9[6] = index + arrayWidth - 1> tileArray.length - 1 ? 1 : tileArray[index + arrayWidth - 1];
		tile9x9[7] = index + arrayWidth > tileArray.length - 1 ? 1 : tileArray[index + arrayWidth];
		tile9x9[8] = index + arrayWidth + 1 > tileArray.length - 1 ? 1 : tileArray[index + arrayWidth + 1];		
		return tile9x9;
	}
	
	public static int[] convertTilesToTileSet(int[] tileArray, int arrayWidth){
		int[] newArray = new int[tileArray.length];	//create a new array, don't use the reference!	
		for(int i = 0; i < tileArray.length; i++) {
			newArray[i] = fetchTile(tileArray, i, arrayWidth);
		}
				
		System.out.println("tileArrayDetiled: " + newArray.length);
		for (int i = 0; i < newArray.length; i++){
			if (i % arrayWidth == 0) System.out.println();
			System.out.print(newArray[i] + " ");
		}
		System.out.println();
		System.out.println("Done");
		
		
		return newArray;
	}
}
