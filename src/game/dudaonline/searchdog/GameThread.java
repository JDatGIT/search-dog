package game.dudaonline.searchdog;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


public class GameThread extends Thread {

	private static final String TAG = GameThread.class.getSimpleName();	
	
	private final static int 	MAX_FPS = 100; // desired fps			
	private final static int	MAX_FRAME_SKIPS = 5; // maximum number of frames to be skipped			
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS; // the frame period	
	
	private SurfaceHolder surfaceHolder;
	private GameSurface surface;
	private int realFps;
	
	private boolean running; // flag to hold game state
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public GameThread(SurfaceHolder surfaceHolder, GameSurface surface) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.surface = surface;
	}
	
	@Override
	public void run() {
		long tickCount = 0L;
		Canvas canvas;
		Log.d(TAG, "Starting game loop");

		long beginTime;		// the time when the cycle begun
		long timeDiff;		// the time it took for the cycle to execute
		int sleepTime;		// ms to sleep (<0 if we're behind)
		int framesSkipped;	// number of frames being skipped 
		
		long timer = System.currentTimeMillis();
		int frames = 0;
		sleepTime = 0;
		while (running) {
			if(surface.readyToDraw()){
			tickCount++;
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;	// resetting the frames skipped
					this.surface.update(); // update game state 
					if (canvas != null) this.surface.render(canvas); // render state to the screen				
					timeDiff = System.currentTimeMillis() - beginTime; // calculate how long did the cycle take
					
					frames++;
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						realFps = frames;
						frames = 0;
					}
					
					
					sleepTime = (int)(FRAME_PERIOD - timeDiff); // calculate sleep time
						
					if (sleepTime > 0) { // if sleepTime > 0 we're OK
						try {
							// send the thread to sleep for a short period - very useful for battery saving
							Thread.sleep(sleepTime);	
						} catch (InterruptedException e) {}
					}
						
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// we need to catch up so update without rendering
						this.surface.update(); 						
						sleepTime += FRAME_PERIOD; // add frame period to check if in next frame	
						framesSkipped++;
					}
				}
			} finally {
				// in case of an exception the surface is not left in an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			}
		}
		Log.d(TAG, "Game loop executed " + tickCount + " times");				
	}

	public int getFrames() {
		return realFps;
	}
}
