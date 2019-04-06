package packets;

public class PlayerMovePacket extends Packet{
	private static final long serialVersionUID = 7;
	private int row;
	private int column;
	private char symbol;
	
	@Override
	public PacketHeader getPacketHeader() {
		return PacketHeader.PlayerMove;
	}


	@Override
	public String toString() {
		return "MovePacket [row=" + row + ", column=" + column + ", symbol=" + symbol + "]";
	}

	public PlayerMovePacket(int Row, int Column, char Symbol) {
		super();
		row = Row;
		column = Column;
		symbol = Symbol;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}


	public char getSymbol() {
		return symbol;
	}


	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	
	
	
}
