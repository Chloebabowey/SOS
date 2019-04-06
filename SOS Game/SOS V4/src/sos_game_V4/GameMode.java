package sos_game_V4;

import javafx.stage.Stage;

public interface GameMode {
	GameManager getGameManager();
	void initializeGameMode(Stage stage);
	String getGameModeText();
	Barricade getBarricade();
	GamePlayGui getGamePlayGui();
	PacketSender getPacketSender();
	void setPacketSender(PacketSender sender);
}
