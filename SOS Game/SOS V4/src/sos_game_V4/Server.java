package sos_game_V4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import deprecatedClasses.DisconnectPacket;
import javafx.application.Application;
import javafx.stage.Stage;
import packets.ClientNamePacket;
import packets.GameDetailsPacket;
import packets.GameRestartPacket;
import packets.Packet;
import packets.PacketHeader;
import packets.PlayerMovePacket;

public class Server {
	private ServerSocket server;

	public Server(Stage primaryStage, GameMode gameMode) {
		try {
			Barricade barricade = gameMode.getBarricade();

			server = new ServerSocket(barricade.getPort());
			Thread thread = new Thread(() -> {
				while (true) {
					try {
						Socket socket = server.accept();
						ServerClient client = new ServerClient(socket, primaryStage, gameMode);
						client.sendPacket(new GameDetailsPacket(barricade.getGridSize(), gameMode.getGameModeText(),
								barricade.getPlayerOneName()));
						gameMode.setPacketSender(client);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			thread.setDaemon(true);
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
