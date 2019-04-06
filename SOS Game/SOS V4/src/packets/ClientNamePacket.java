package packets;

public class ClientNamePacket extends Packet {
	private static final long serialVersionUID = 10;
	private String name;
	
	@Override
	public String toString() {
		return "ClientNamePacket [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String Name) {
		name = Name;
	}

	public ClientNamePacket(String Name) {
		super();
		name = Name;
	}

	@Override
	public PacketHeader getPacketHeader() {
		return PacketHeader.ClientName;
	}

}
