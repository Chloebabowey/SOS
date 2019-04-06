package sos_game_V4;

import javafx.scene.text.Text;

public class UpdateGameClockLabel implements Runnable{
	private GameClock gameClock;
	private Text clock = new Text();

	public UpdateGameClockLabel(GameClock GameClock, Text clockDisplay) {
		gameClock = GameClock;
		clock = clockDisplay;
	}

	@Override
	public void run() {
		while(true) {
			clock.setText("Game Clock: " + gameClock.getTime());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
