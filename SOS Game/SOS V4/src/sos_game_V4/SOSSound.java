package sos_game_V4;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SOSSound implements Runnable{
	private MediaPlayer sosSoundPlayer;
	private Media sosSound;
	private int sosMatches;
	
	public SOSSound(Media SosSound, MediaPlayer SOSSoundPlayer, int SOSMatches) {
		sosSound = SosSound;
		sosSoundPlayer = SOSSoundPlayer;
		sosMatches = SOSMatches;
	}
	
	public static void sleepThread(){
		try {
			Thread.sleep(1700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void run() {
		for(int i=0; i<sosMatches; i++) {
		sosSoundPlayer = new MediaPlayer(sosSound);
		sosSoundPlayer.play();
		sleepThread();
		}	
	}
}
