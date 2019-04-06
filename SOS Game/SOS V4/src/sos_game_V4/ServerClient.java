package sos_game_V4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import packets.ClientNamePacket;
import packets.GameDetailsPacket;
import packets.GameRestartPacket;
import packets.Packet;
import packets.PacketHeader;
import packets.PlayerMovePacket;

public class ServerClient implements PacketSender {
	private Socket socket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	public ServerClient(Socket sock, Stage primaryStage, GameMode gameMode) {
		try {
			socket = sock;
			writer = new ObjectOutputStream(socket.getOutputStream());
			writer.flush();
			reader = new ObjectInputStream(socket.getInputStream());

			Thread thread = new Thread(() -> {
				while (true) {
					try {
						Packet packet = (Packet) reader.readObject();
						if (packet != null) {
							processPacket(packet, primaryStage, gameMode);

						}

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			thread.setDaemon(true);
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendPacket(Packet packet) {
		try {
			writer.writeObject(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processPacket(Packet packet, Stage primaryStage, GameMode gameMode) {
		if (packet.getPacketHeader() == PacketHeader.PlayerMove) {
			PlayerMovePacket movePacket = (PlayerMovePacket) packet;
			Barricade barricade = gameMode.getBarricade();
			barricade.setMoveRow(movePacket.getRow());
			barricade.setMoveColumn(movePacket.getColumn());
			barricade.setMove(movePacket.getSymbol());
			Platform.runLater(() -> {
				if (gameMode.getGameModeText().equals("Normal"))
					gameMode.getGamePlayGui().classicModeMove(primaryStage, movePacket.getRow(), movePacket.getColumn(),
							movePacket.getSymbol());
				else if (gameMode.getGameModeText().equals("Extreme"))
					gameMode.getGamePlayGui().extremeModeMove(primaryStage, movePacket.getRow(), movePacket.getColumn(),
							movePacket.getSymbol());
				else if (gameMode.getGameModeText().equals("Combat"))
					gameMode.getGamePlayGui().combatModeMove(primaryStage, movePacket.getRow(), movePacket.getColumn(),
							movePacket.getSymbol());
			});
			System.out.println(movePacket);
		} else if (packet.getPacketHeader() == PacketHeader.ClientName) {
			ClientNamePacket clientNamePacket = (ClientNamePacket) packet;
			Barricade barricade = gameMode.getBarricade();

			barricade.setPlayerTwoName(clientNamePacket.getName());
			Platform.runLater(() -> {
				gameMode.initializeGameMode(primaryStage);
				GameManager gameManager = gameMode.getGameManager();
				System.out.println(gameManager);
				sendPacket(new GameRestartPacket(gameManager.isPlayerOneTurn()));
			});
			System.out.println(clientNamePacket);
		}
	}
}