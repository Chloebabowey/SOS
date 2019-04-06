package packets;

public class GameRestartPacket extends Packet {
	private static final long serialVersionUID = 11;
	private boolean hostTurn;
	
	@Override
	public PacketHeader getPacketHeader() {
		return PacketHeader.GameRestart;
	}

	public GameRestartPacket(boolean HostTurn) {
		super();
		hostTurn = HostTurn;
	}
	
	public boolean isHostTurn() {
		return hostTurn;
	}

	@Override
	public String toString() {
		return "GameRestartPacket [hostTurn=" + hostTurn + "]";
	}

}
