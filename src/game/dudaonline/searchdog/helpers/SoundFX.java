package game.dudaonline.searchdog.helpers;

import game.dudaonline.searchdog.MainActivity;

import java.io.IOException;
import java.util.HashMap;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public enum SoundFX implements SoundPool.OnLoadCompleteListener {
		
	INSTANCE;

	public static final int MAX_STREAMS = 8;
	
	protected SoundPool pool = new SoundPool( MAX_STREAMS, AudioManager.STREAM_MUSIC, 0 );
	
	protected HashMap<Object, Integer> ids = new HashMap<Object, Integer>();
	
	private boolean enabled = true;
	
	public void reset() {

		pool.release();
		
		pool = new SoundPool( MAX_STREAMS, AudioManager.STREAM_MUSIC, 0 );
		pool.setOnLoadCompleteListener( this );
		
		ids.clear();
	}
	
	public void pause() {
		if (pool != null) {
			pool.autoPause();
		}
	}
	
	public void resume() {
		if (pool != null) {
			pool.autoResume();
		}
	}
	
	public void load( String... assets ) {
		
		AssetManager manager = MainActivity.instance.getAssets();
		
		for (int i=0; i < assets.length; i++) {
			
			String asset = assets[i];
			
			if (!ids.containsKey( asset )) {
				try {
					AssetFileDescriptor fd = manager.openFd( asset );
					int streamID = pool.load( fd, 1 ) ;
					ids.put( asset, streamID );
					fd.close();
				} catch (IOException e) {
				}
			}
			
		}
	}
	
	public void unload( Object src ) {
		
		if (ids.containsKey( src )) {
			
			pool.unload( ids.get( src ) );
			ids.remove( src );
		}
	}
	
	public int play( Object id ) {
		return play( id, 1, 1, 1 );
	}
	
	public int play( Object id, float volume ) {
		return play( id, volume, volume, 1 );
	}
	
	public int play( Object id, float leftVolume, float rightVolume, float rate ) {
		if (enabled && ids.containsKey( id )) {
			return pool.play( ids.get( id ), leftVolume, rightVolume, 0, 0, rate );
		} else {
			return -1;
		}
	}
	
	public void enable( boolean value ) {
		enabled = value;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void onLoadComplete( SoundPool soundPool, int sampleId, int status ) {
	}
}