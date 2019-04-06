package sos_game_V4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import deprecatedClasses.DisconnectPacket;
import javafx.application.Platform;
import javafx.stage.Stage;
import packets.ClientNamePacket;
import packets.GameDetailsPacket;
import packets.GameRestartPacket;
import packets.Packet;
import packets.PacketHeader;
import packets.PlayerMovePacket;

public class Client implements PacketSender {
	private Socket socket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private GameMode GUI;

	public Client(Stage primaryStage, Barricade barricade) {
		try {
			socket = new Socket(barricade.getIp(), barricade.getPort());
			writer = new ObjectOutputStream(socket.getOutputStream());
			writer.flush();
			reader = new ObjectInputStream(socket.getInputStream());

			sendPacket(new ClientNamePacket(barricade.getPlayerTwoName()));

			Thread thread = new Thread(() -> {
				while (true) {
					try {
						Packet packet = (Packet) reader.readObject();
						if (packet != null) {
							processPacket(packet, primaryStage, barricade);
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

	private void processPacket(Packet packet, Stage primaryStage, Barricade barricade) {
		if (packet.getPacketHeader() == PacketHeader.PlayerMove) {
			PlayerMovePacket movePacket = (PlayerMovePacket) packet;
			barricade.setMoveRow(movePacket.getRow());
			barricade.setMoveColumn(movePacket.getColumn());
			barricade.setMove(movePacket.getSymbol());
			Platform.runLater(() -> {
				if (GUI.getGameModeText().equals("Normal"))
					GUI.getGamePlayGui().classicModeMove(primaryStage, movePacket.getRow(), movePacket.getColumn(),
							movePacket.getSymbol());
				else if (GUI.getGameModeText().equals("Extreme"))
					GUI.getGamePlayGui().extremeModeMove(primaryStage, movePacket.getRow(), movePacket.getColumn(),
							movePacket.getSymbol());
				else if (GUI.getGameModeText().equals("Combat"))
					GUI.getGamePlayGui().combatModeMove(primaryStage, movePacket.getRow(), movePacket.getColumn(),
							movePacket.getSymbol());
			});
			System.out.println(movePacket);
		} else if (packet.getPacketHeader() == PacketHeader.ClientName) {
			ClientNamePacket clientNamePacket = (ClientNamePacket) packet;
			System.out.println(clientNamePacket);
		} else if (packet.getPacketHeader() == PacketHeader.GameRestart) {
			GameRestartPacket gameRestartPacket = (GameRestartPacket) packet;
			System.out.println(gameRestartPacket);

			Platform.runLater(() -> {
				GUI.initializeGameMode(primaryStage);
				GUI.getGameManager().setPlayerTurn(gameRestartPacket.isHostTurn());
				GUI.getGamePlayGui().updateCurrentPlayerLabel();
			});

		} else if (packet.getPacketHeader() == PacketHeader.GameDetails) {
			GameDetailsPacket gameDetailsPacket = (GameDetailsPacket) packet;
			System.out.println(gameDetailsPacket);

			barricade.setGridSize(gameDetailsPacket.getBoardSize());
			barricade.setPlayerOneName(gameDetailsPacket.getHostName());
			Platform.runLater(() -> {
				if (gameDetailsPacket.getGameMode().equals("Normal")) {
					GUI = new ClassicMode(barricade);
				} else if (gameDetailsPacket.getGameMode().equals("Extreme")) {
					GUI = new ExtremeMode(barricade);
				} else if (gameDetailsPacket.getGameMode().equals("Combat")) {
					GUI = new CombatMode(barricade);
				}
				GUI.setPacketSender(this);
			});
		}
	}

}