package sos_game_V4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import packets.GameRestartPacket;
import packets.PlayerMovePacket;

public class ExtremeMode extends Application implements GameMode {
	private Pane mainPane = new Pane();
	private Button quitGame = new Button("QUIT");
	private Button restartGame = new Button("RESTART");
	private Button confirmMove = new Button("MAKE MOVE");
	private Button clearMove = new Button("CLEAR MOVE");
	private Barricade barricade;
	private GamePlayGui gamePlay = new GamePlayGui(barricade);
	private PacketSender sender;

	public static void main (String [] args) {	
		launch(args);
	}

	ExtremeMode(Barricade bar){
		barricade = bar;
		gamePlay.setBarricade(bar);
	}

	@Override
	public void start(Stage primaryStage) {

		gamePlay.startGame(quitGame, restartGame, confirmMove, clearMove, barricade);
		gamePlay.addToWindow(primaryStage, quitGame, restartGame, confirmMove, clearMove);

		clearMove.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				gamePlay.clearMove();
			}
		});

		restartGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				ExtremeMode GUI = new ExtremeMode(barricade);
				GUI.setPacketSender(sender);
				GUI.start(primaryStage);
				if(barricade.isMultiplayer() && sender != null) {
					sender.sendPacket(new GameRestartPacket(gamePlay.getGame().getPlayerTwo().equals(gamePlay.getGame().currentPlayer())));
				}
			}
		});

		quitGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				gamePlay.quitGame(primaryStage);
			}
		});

		confirmMove.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				gamePlay.updateMoveFromGUI();
				gamePlay.extremeModeMove(primaryStage, gamePlay.getMoveRow(), gamePlay.getMoveColumn(), gamePlay.getLetterMove());
				if(barricade.isMultiplayer() && sender != null) {
					sender.sendPacket(new PlayerMovePacket(gamePlay.getMoveRow(), gamePlay.getMoveColumn(), gamePlay.getLetterMove()));
				}
			}
		});

	}

	@Override
	public GameManager getGameManager() {
		return gamePlay.getGame();
	}

	@Override
	public void initializeGameMode(Stage stage) {
		start(stage);
	}

	@Override
	public String getGameModeText() {
		return "Extreme";
	}
	
	@Override
	public Barricade getBarricade() {
		return barricade;
	}

	@Override
	public GamePlayGui getGamePlayGui() {
		return gamePlay;
	}
	
	@Override
	public void setPacketSender(PacketSender Sender) {
		sender = Sender;
	}

	@Override
	public PacketSender getPacketSender() {
		return sender;
	}
}

