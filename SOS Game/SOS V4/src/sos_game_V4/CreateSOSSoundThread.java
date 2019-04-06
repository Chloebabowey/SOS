package sos_game_V4;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class CreateSOSSoundThread {
	private String soundFile = "src/media/sos_clip.mp3";
	private Media sosSound = new Media(new File(soundFile).toURI().toString());
	private MediaPlayer sosSoundPlayer;

	public void createSOSThread(int SOSMatches) {
		Thread sound = new Thread(new SOSSound(sosSound, sosSoundPlayer, SOSMatches));
		sound.start();

	}
}


