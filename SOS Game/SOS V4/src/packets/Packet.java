package packets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Packet implements Serializable
{
    public abstract PacketHeader getPacketHeader();
    
    private void writeObject(ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(getPacketHeader());
        stream.defaultWriteObject();
    }
    
    private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException
    {
        PacketHeader header = (PacketHeader) stream.readObject();
        if(header != getPacketHeader())
            throw new IllegalArgumentException("Incompatible packet header. Received: " + header + " and expected " + getPacketHeader());
        stream.defaultReadObject();
    }
    
    private static final long serialVersionUID = 4266486469091725702L;
}