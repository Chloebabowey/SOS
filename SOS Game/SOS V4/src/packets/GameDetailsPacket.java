package packets;

public class GameDetailsPacket extends Packet {
	private static final long serialVersionUID = 8L;
	private int boardSize;
	private String gameMode;
	private String hostName;
	
	public GameDetailsPacket(int BoardSize, String GameMode, String HostName) {
		super();
		boardSize = BoardSize;
		gameMode = GameMode;
		hostName = HostName;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int BoardSize) {
		boardSize = BoardSize;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String GameMode) {
		gameMode = GameMode;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String HostName) {
		hostName = HostName;
	}

	@Override
	public PacketHeader getPacketHeader() {
		return PacketHeader.GameDetails;
	}

	@Override
	public String toString() {
		return "GameDetailsPacket [boardSize=" + boardSize + ", gameMode=" + gameMode + ", hostName=" + hostName + "]";
	}

	

}
