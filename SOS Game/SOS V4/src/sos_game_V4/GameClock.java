package sos_game_V4;

import javafx.scene.control.Label;

public class GameClock implements Runnable {
	private String time;
	private boolean current = true;
	private long hours = 0, mins = 0, seconds = 0, millis = 0, startTime = 0;
	boolean sos = true;
	private Label clockDisplay = new Label();

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
	
		while(current == true) {		
			millis = (System.currentTimeMillis() - startTime);
			seconds = (millis/1000)%60;
			mins = ((millis/1000)/60)%60;
			
			if(seconds == 60) {
				seconds = 0;
			}
			if(mins == 60) {
				mins = 0;
			}
			time = ((((mins/10) == 0) ? "0" : "") + mins + ":"
					 + (((seconds/10) == 0) ? "0" : "") + seconds + ":" 
						+ ((((millis/100) == 0) ? "0" : "")) + (millis/100)%10);
			clockDisplay.setText(time);
			setTime(time);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	
	public Label getClockDisplay() {
		return clockDisplay;
	}
	
	public void setTime(String Time) {
		time = Time;
	}
	
	public String getTime() {
		return time;
	}
	
}

